package com.puyuntech.ycmall.controller;

import com.puyuntech.ycmall.CommonAttributes;
import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.service.CacheService;
import com.puyuntech.ycmall.service.MemberService;
import com.puyuntech.ycmall.util.CommonParameter;
import com.puyuntech.ycmall.util.SystemUtils;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.vo.FileMetaVO;
import net.sf.ehcache.CacheManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Controller
@RequestMapping("/APP")
public class FileUploadController extends BaseController{
    private Logger LOGGER = Logger.getLogger(FileUploadController.class);

    /** CacheManager */
    private static final CacheManager CACHE_MANAGER = CacheManager.create();

    String code;

    /**
     * 域名
     */
    private static String webSite = "http://www.shenqiliuguo.com/";
//    private static String webSite = "http://58.240.32.170:7074/";

    /**
     * 会员Service
     */
    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "cacheServiceImpl")
    private CacheService cacheService;

    /**
     *
     * 上传图片. author: 严志森 date: 2015-11-19 上午11:28:29
     *
     * @param request
     * @param type
     *            类型 1：头像 2:晒论 3：身份证
     * @return
     */
    @RequestMapping(value = "/photoUpload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> photoUpload(MultipartHttpServletRequest request,
                                           int type) {
        // 定义结果数据结构
        Map<String, Object> result = new HashMap<String, Object>();
        FileMetaVO fileMeta = null;
        MultipartFile mpf = null;
        Map<String, Object> mapResult = new HashMap<String, Object>();
        code = UnivParameter.DATA_CORRECTCODE;
        try {
            Iterator<String> itr = request.getFileNames();
            while (itr.hasNext()) {
                mpf = request.getFile(itr.next());
                String defPath = "";
                switch (type) {
                    case 1:// 头像图片
                        defPath = CommonParameter.DEFAULT_HEAD_PATH;
                        break;
                    case 2:// 晒论图片
                        defPath = CommonParameter.DEFAULT_REVIEW_PATH;
                        break;
                    case 3:// 身份证
                        defPath = CommonParameter.DEFAULT_USERCARD_PATH;
                        break;
                    default:
                }
                String originalFileName = mpf.getOriginalFilename(); // 用户上传图片名称
                LOGGER.debug("图片名：" + originalFileName);
                String subfix = originalFileName.substring(originalFileName
                        .lastIndexOf(".")); // 获取图片后缀
                String fileName = UUID.randomUUID() + subfix; // 图片重命名

                String path = defPath + fileName;// 构建图片保存的目录

                File dirFile = new File(request.getSession()
                        .getServletContext().getRealPath(defPath));
                // 创建目录
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                // 得到图片保存目录的真实路径
                String realpath = request.getSession().getServletContext()
                        .getRealPath(path);

//				String urlPath = request.getScheme() + "://"
//						+ request.getServerName() + ":"
//						+ request.getServerPort() + request.getContextPath(); // url路径

                String urlPath = webSite + request.getContextPath();

                fileMeta = new FileMetaVO();
                fileMeta.setFileName(fileName); // 文件名
                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());
                fileMeta.setFilePath(urlPath + path);
                FileOutputStream fps = null;
                try {
                    fileMeta.setBytes(mpf.getBytes());

                    fps = new FileOutputStream(realpath);

                    FileCopyUtils.copy(mpf.getBytes(), fps);
//					mapResult.put("fileName", fileMeta.getFileName());
                    mapResult.put("realpath", urlPath+defPath+fileName);
                    result.put(UnivParameter.RESULT, mapResult);
                } catch (Exception e) {
                    // 崩溃性逻辑处理错误CODE-0
                    code = UnivParameter.DATA_ERRORCODE;

                    // 加载逻辑处理层崩溃性MESSAGE
                    result.put(UnivParameter.REASON, e.getMessage());

                    // LOG日志文件编写
                    LOGGER.error(e.getMessage(), e);
                } finally {
                    // 关闭文件资源
                    if (null != fps) {
                        try {
                            fps.close();
                        } catch (IOException e) {
                            // LOG日志文件编写
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // 清空结果数据结构体-准备装载逻辑层错误信息
            result.clear();

            // 崩溃性逻辑处理错误CODE-0
            code = UnivParameter.DATA_ERRORCODE;

            // 加载逻辑处理层崩溃性MESSAGE
            result.put(UnivParameter.REASON, e.getMessage());

            // LOG日志文件编写
            LOGGER.error(e.getMessage(), e);
        }
        result.put(UnivParameter.CODE, code);
        return result;
    }

    /**
     * 会员头像上传及更改 author: 严志森
     * date: 2015-8-27 下午15:30:05
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/setUserPhoto", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setUserPhoto(
            MultipartHttpServletRequest request, String userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        Member member = memberService.find(Long.parseLong(userId));

//		String urlPath = request.getScheme() + "://"
//				+ request.getServerName() + ":"
//				+ request.getServerPort() + request.getContextPath(); // url路径

        String urlPath = webSite + request.getContextPath();

        String realpath = null;
        String path = CommonParameter.DEFAULT_HEAD_PATH;// 构建头像保存的目录
        // 默认为正确的逻辑处理CODE-200
        code = UnivParameter.DATA_CORRECTCODE;
        try {
            String fileName = null; // 头像保存名称
            FileMetaVO fileMeta = null;
            String md5 = DigestUtils.md5Hex(UUID.randomUUID().toString());
            // 创建 iterator
            Iterator<String> itr = request.getFileNames();
            // 循环 file
            while (itr.hasNext()) {
                // 获取下一个 MultipartFile
                MultipartFile mpf = request.getFile(itr.next());
                String originalFileName = mpf.getOriginalFilename(); // 用户上传图片名称
                String subfix = originalFileName.substring(originalFileName
                        .lastIndexOf(".")); // 获取图片后缀
                fileName = md5 + subfix;
                // 创建新 fileMeta
                fileMeta = new FileMetaVO();
                fileMeta.setFileName(fileName);
                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());
                fileMeta.setBytes(mpf.getBytes());

                File dirFile = new File(request.getSession()
                        .getServletContext()
                        .getRealPath(CommonParameter.DEFAULT_HEAD_PATH));
                // 创建目录
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                // 得到图片保存目录的真实路径
                realpath = request.getSession().getServletContext()
                        .getRealPath(path+fileName);

                FileOutputStream fps = new FileOutputStream(realpath);
                FileCopyUtils.copy(mpf.getBytes(), fps);
                // 旧头像路径
                String filePath = request
                        .getSession()
                        .getServletContext()
                        .getRealPath(
                                CommonParameter.DEFAULT_HEAD_PATH
                                        + member.getPhotoPath());
                File file = new File(filePath);

                if (file.exists()) { // 删除旧头像
                    file.delete();
                }

            }
            member.setPhotoPath(urlPath+path+fileName);
            memberService.update(member);
//			result.put("fileName", fileName);
            result.put("realpath", urlPath+path+fileName);
        } catch (Exception e) {
            result.clear();

            // 崩溃性逻辑处理错误CODE-0
            code = UnivParameter.DATA_ERRORCODE;

            // 加载逻辑处理层崩溃性MESSAGE
            result.put(UnivParameter.REASON, e.getMessage());

            // LOG日志文件编写
            LOGGER.error(e.getMessage(), e);
        }
        result.put(UnivParameter.CODE, code);
        return result;
    }

    @RequestMapping(value = "/settingUpdate", method = RequestMethod.POST)
    @ResponseBody
    public boolean settingUpdate( String path ) {
        Setting setting = new Setting();
        try {
            File sourceXmlFile = new File( path );
            Document document = new SAXReader().read(sourceXmlFile);
            List<org.dom4j.Element> elements = document.selectNodes("/puyun/setting");
            for (org.dom4j.Element element : elements) {
                try {
                    String name = element.attributeValue("name");
                    String value = element.attributeValue("value");
                    SystemUtils.BEAN_UTILS.setProperty(setting, name, value);
                } catch (IllegalAccessException e) {
                    return false;
                } catch (InvocationTargetException e) {
                    return false;
                }
            }
        } catch (DocumentException e) {
            return false;
        }

        Setting srcSetting = SystemUtils.getSetting();
        if (StringUtils.isEmpty(setting.getSmtpPassword())) {
            setting.setSmtpPassword(srcSetting.getSmtpPassword());
        }

        /* app中的 setting内容 */
        File puyunXmlFile = null;
        try {
            puyunXmlFile = new ClassPathResource(CommonAttributes.puyun_XML_PATH).getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setting.setWatermarkImage(srcSetting.getWatermarkImage());
        if (StringUtils.isEmpty(setting.getSmsSn()) || StringUtils.isEmpty(setting.getSmsKey())) {
            setting.setSmsSn(null);
            setting.setSmsKey(null);
        }
        setting.setIsCnzzEnabled(srcSetting.getIsCnzzEnabled());
        setting.setCnzzSiteId(srcSetting.getCnzzSiteId());
        setting.setCnzzPassword(srcSetting.getCnzzPassword());
        setting.setTheme(srcSetting.getTheme());
        SystemUtils.setSetting(setting);
        cacheService.clear();
        return true;
    }

    @RequestMapping(value = "/getSetting", method = RequestMethod.POST)
    @ResponseBody
    public void getSetting(){
        Setting setting = SystemUtils.getSetting();
        System.out.println( setting.getSiteName() );
    }

}
