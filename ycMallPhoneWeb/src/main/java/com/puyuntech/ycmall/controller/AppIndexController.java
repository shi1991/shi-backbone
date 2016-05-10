package com.puyuntech.ycmall.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.puyuntech.ycmall.entity.*;
import com.puyuntech.ycmall.service.*;
import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.entity.Member.Gender;
import com.puyuntech.ycmall.entity.value.CartItemBindProductValue;
import com.puyuntech.ycmall.util.CommonParameter;
import com.puyuntech.ycmall.util.RandomValueUtils;
import com.puyuntech.ycmall.util.SystemUtils;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.util.WebUtils;
import com.puyuntech.ycmall.util.kuaidi.ResultItem;
import com.puyuntech.ycmall.vo.Result;

@Controller
@RequestMapping("/APP")
public class AppIndexController extends BaseController {
	private Logger LOGGER = Logger.getLogger(AppIndexController.class);

    /**
     * pingpp 管理平台对应的 API key
     */
    public static String apiKey = "sk_live_i1uLe1aLWTGKCKaT00X9CuPS";
    /**
     * pingpp 管理平台对应的应用 ID
     */
    public static String appId = "app_nDmjDCujHOa9bnDu";

    @Resource(name = "problemClassificationServiceImpl")
    private ProblemClassificationService problemClassificationService;

    @Resource(name = "problemInformationServiceImpl")
    private ProblemInformationService problemInformationService;
	/**
	 * APP相关Service
	 */
	@Resource(name = "appIndexServiceImpl")
	private AppIndexService appIndexService;
	
	/**
	 * 手机号码Service
	 */
	@Resource(name="phoneNumberServiceImpl")
	private PhoneNumberService phoneNumberService;
	
	/**
	 * 合约套餐以及用户信息Service
	 */
	@Resource(name="contractPhoneNumberUserInfoServiceImpl")
	private ContractPhoneNumberUserInfoService contractPhoneNumberUserInfoService;
	
	/**
	 * 支付记录Service
	 */
    @Resource(name = "paymentLogServiceImpl")
    private PaymentLogService paymentLogService;
    
    /**
	 * token
	 */
//	public static Map<String, Member> tokenMap = new HashMap<String, Member>();

	/**
	 * 商品Service
	 */
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	/**
	 * 退单日志Service
	 */
	@Resource(name="returnOrderLogServiceImpl")
    private ReturnOrderLogService returnOrderLogService;
	
	/**
	 * 评论Service
	 */
	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;

	/**
	 * 商品Service
	 */
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	/**
	 * 购物车Service
	 */
	@Resource(name = "cartServiceImpl")
	private CartService cartService;

	/**
	 * 运营商Service
	 */
	@Resource(name = "operatorServiceImpl")
	private OperatorService operatorService;
	
	/**
	 * 套餐内容项Service
	 */
	@Resource(name="contractItemServiceImpl")
	private ContractItemService contractItemService;

	/**
	 * 合约套餐Service
	 */
	@Resource(name = "contractServiceImpl")
	private ContractService contractService;

	/**
	 * 神币Service
	 */
	@Resource(name = "godMoneyLogServiceImpl")
	private GodMoneyLogService godMoneyLogService;
	
	/**
	 * 订单 Service
	 */
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	/**
	 * 积分Service
	 */
	@Resource(name = "pointLogServiceImpl")
	private PointLogService pointLogService;

	/**
	 * 会员Service
	 */
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	/**
	 * 积分获得行为 Service
	 */
	@Resource(name = "pointBehaviorServiceImpl")
	private PointBehaviorService pointBehaviorService;
	
	/**
	 * 发货单Service
	 */
	@Resource(name = "shippingServiceImpl")
	private ShippingService shippingService;
	
	/**
	 * 物流信息Service
	 */
	@Resource(name = "trackingLogServiceImpl")
	private TrackingLogService trackingLogService;
	
	/**
	 * 门店Service
	 */
	@Resource(name="organizationServiceImpl")
	private OrganizationService organizationService;
	
	/**
	 * 日期年月日：时分秒
	 */
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 日期年月日
	 */
	SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Service - 返利
	 */
	@Resource(name="rebatesServiceImpl")
	private RebatesService rebatesService;
	
	/**
	 * 订单项 Service
	 */
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;

	/**
	 * 购物车单项 Service
	 */
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;
	
	/**
	 * 库存记录Service
	 */
	@Resource(name = "stockLogServiceImpl")
	private StockLogService stockLogService;
	
	/**
	 * 退单Service
	 */
	@Resource(name="returnOrderServiceImpl")
	private ReturnOrderService returnOrderService;
	
	/**
	 * 会员等级 Service
	 */
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	
	/**
	 * 退单项Service
	 */
	@Resource(name="returnOrderItemServiceImpl")
	private ReturnOrderItemService returnOrderItemService;
	
	String code;

	/**
	 * APP首页，今日抢图片及状态加载.
     * author: 南金豆 date: 2015-11-6 下午2:33:37
	 * @return
	 */
	@RequestMapping(value = "/getTodayRobList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTodayRobList() {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService.getTodayRobList();
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 检索.
	 * author: 严志森
	 *   date: 2015-12-5 下午2:37:39
	 * @param keyword 关键字
	 * @param pageSize 每页数量
	 * @param count 总数
	 * @return
	 */
	@RequestMapping(value = "/getGoodsList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getGoodsList(String keyword,int pageSize ,int count){
		//定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		List<Object> list=new ArrayList<Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> map=null;
		try {
			//调用数据处理层，并接受结果数据
			//两数相除，如果有余数则取整加1
			int pageNumber = count%pageSize ==0?count/pageSize :count/pageSize +1;
			Pageable pageable = new Pageable(pageNumber , pageSize );
			Page<Product> page=searchService.search(keyword,null,null,Product.OrderType.salesDesc,pageable);
			if(page.getTotal() > 0){
				for (Product product : page.getContent()) {
					map=new HashMap<String, Object>();
					Long reviewCount=reviewService.count(product);
					map.put("goodsId", product.getId());
					map.put("goodsName", product.getName());
					map.put("goodsImagePath", product.getProductImages().get(0).getSource());
					map.put("goodsPrice", product.getPrice());
					map.put("goodsCaption", product.getCaption());
					map.put("reviewCount", reviewCount);
					list.add(map);
				}
			}
			
			//数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT, list);
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
	 * 【今日抢】点击图标进行抢购 author: 南金豆 date: 2015-11-9 下午7:09:30
	 * 
	 * @param grabSeckillId 抢购Id
     * @param robGoodsId 抢购商品编号
	 * @param robGoodsType
	 *            商品类型【1：商品，2：神币类红包 3：积分类红包，4：实物类红包 5：优惠券】
	 * @param userId
	 *            会员编号
	 * @return
	 */
	@RequestMapping(value = "/shoppingRush", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> shoppingRush(Long grabSeckillId,@RequestParam(defaultValue="1111-1-11 11:11:11")String startTime,
			Long robGoodsId, int robGoodsType, Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {

			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult1 = appIndexService.snapJudgment(
					grabSeckillId, robGoodsType, userId,startTime);

			if (UnivParameter.DATA_ERRORCODE.equals(mapResult1.get(
					UnivParameter.DATA).toString()) && UnivParameter.DATA_ERRORCODE.equals(mapResult1.get(
							UnivParameter.CODE).toString())) {
				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.DATA_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult1.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 调用数据处理层，并接受结果数据
				Map<String, Object> mapResult = appIndexService.shoppingRush(
						grabSeckillId, robGoodsId, robGoodsType, userId);

				if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
						UnivParameter.CODE).toString())) {

					// 崩溃性数据处理错误CODE-1
					code = UnivParameter.DATA_ERRORCODE;

					// 加载数据处理层崩溃性MESSAGE
					result.put(UnivParameter.REASON,
							mapResult.get(UnivParameter.ERRORMESSAGE)
									.toString());
				} else if (UnivParameter.LOGIC_COLLAPSECODE.equals(mapResult
						.get(UnivParameter.CODE).toString())) {
					// 崩溃性数据处理错误CODE-1
					code = UnivParameter.LOGIC_COLLAPSECODE;

					// 加载数据处理层崩溃性MESSAGE
					result.put(UnivParameter.REASON,
							mapResult.get(UnivParameter.ERRORMESSAGE)
									.toString());
				} else {
					// 数据处理层正确性结果判断--加载正确的数据结果集
					result.put(UnivParameter.RESULT,
							mapResult.get(UnivParameter.DATA));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
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
	 * 购物车列表.
	 * author: 严志森
	 *   date: 2015-10-16 下午2:26:25
	 * @param userId 会员编号 
	 * @return
	 */
	@RequestMapping(value = "/getCart", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCart(Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService.getCartList( userId );
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 关注/收藏列表获取. author: 南金豆 date: 2015-10-27 下午6:26:46
	 * 
	 * @param userId
	 *            会员编号
	 * @param pageLoadType
	 *            分页方向【0：刷新，1：加载更多】
	 * @param pageRowsCount
	 *            每页加载数据条数
	 * @param goodsId
	 *            起始商品编号
	 * @return
	 */
	@RequestMapping(value = "/getFavorite", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFavorite(String userId, int pageLoadType,
			int pageRowsCount, String goodsId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService.getFavorite(userId,
					pageLoadType, pageRowsCount, goodsId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 神部落首页加载（会员头像、神币余额、积分余额）. 
	 * author: yanzhisen
	 * date: 2015-9-2 上午9:40:40
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserInfo(String userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService.getUserInfo(userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 会员收货地址列表.
     * author: 严志森 date:2015-12-30 14:49:50
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/getAddress", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAddress(String userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			mapResult = appIndexService.getAddress(userId);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 订单列表获取. author: 南金豆 date: 2015-10-30 上午11:15:38
	 * 
	 * @param userId
	 *            用户id
	 * @param ordersType
	 *            订单状态【0：全部，1：代付款 :2：待收货，3： 待自提 :4：待评价（完成的）】
	 * @param pageLoadType
	 *            分页方向【0：刷新，1：加载更多】
	 * @param pageRowsCount
	 *            每页加载数据条数
	 * @param createDate
	 *            起始订单日期(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	@RequestMapping(value = "/getOrders", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrders(String userId, int ordersType,
			int pageLoadType, int pageRowsCount, String createDate) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();

		code = UnivParameter.DATA_CORRECTCODE;
		try {
			mapResult = appIndexService.getOrders(userId, ordersType,
					pageLoadType, pageRowsCount, createDate);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 商品晒论.
	 * author: 严志森
	 *   date: 2015-12-7 下午1:14:50
	 * @param goodSn 商品串号
	 * @param content 内容
	 * @param imgs 图片（数组）
	 * @param orderItemId 订单项id
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/setComment", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setComment(HttpServletRequest request,String goodSn ,Long userId, String content , String[] imgs ,Long orderItemId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;

		Map<String, Object> mapResult = new HashMap<String, Object>();
		try {
			 //调用数据处理层，并接受结果数据
			Product product = productService.findBySn(goodSn);
			OrderItem orderItem=orderItemService.find(orderItemId);
			Member member = memberService.find(userId);
			if( null == product ){
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.RESULT," 商品不能为空");
				return mapResult;
			}
			
			if( null == orderItem ){
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.RESULT," 订单不能为空");
				return mapResult;
			}
			if( null == member ){
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.RESULT,"用户不能为空");
				return mapResult;
			}
			orderItem.setIsReview(true);
			orderItemService.update(orderItem);
			
			List<String> imgList = Arrays.asList( imgs );
			
			
			Review review = new Review();
			
			review.setImages(imgList);
			review.setMember(member);
			review.setProduct( product );
			review.setProductModel( product.getProductModel() );

            String ip = WebUtils.getIpAddr(request);
			review.setIp( ip );
			review.setScore(1);
			review.setIsShow( true );
			review.setContent( content );
			
			reviewService.save(review);
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT,new HashMap<>());
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
	 * 积分兑换. author: 南金豆 date: 2015-10-30 下午4:44:39
	 * 
	 * @param userId
	 *            会员编号
	 * @param godCurrencyOut
	 *            神币消耗
	 * @param integralIn
	 *            积分进账
	 * @return
	 */
	@RequestMapping(value = "/setCurrency", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setCurrency(String userId, BigDecimal godCurrencyOut,Long integralIn) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		if(userId==null||godCurrencyOut==null||integralIn==null){
			// 修改成功的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,
					UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,
					"积分兑换缺少参数");
			return mapResult;
		}
		try {
			mapResult = appIndexService.setCurrency(userId,godCurrencyOut,integralIn);
			// 数据处理层崩溃性结果判断--0
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-0
				code = UnivParameter.DATA_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 第三方卡券使用. author: 严志森 date:2015-12-30 14:50:11
	 * @param userId 用户id
     * @param couponId 卡券id
	 * @return
	 */
	@RequestMapping(value = "/useCoupon", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> useCoupon(String userId, String couponId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.useCoupon(userId, couponId);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 红包发布申请（货币类及实物类） author: 南金豆 date: 2015-11-5 下午4:24:30
	 * 
	 * @param userId
	 *            会员编号
	 * @param packetType
	 *            红包类型【1：神币红包，2：积分红包，3：实物红包】
	 * @param packetCredit
	 *            红包额度【包括神币额度，积分额度和物品数量】
	 * @param pcaketNumber
	 *            红包数量
	 * @param splitType
	 *            拆分类型【0：等分，1：随即】
	 * @param packetTitle
	 *            红包名称
	 * @param packetInfo
	 *            红包介绍
	 * @param packetGoods
	 *            红包物品
	 * @param packetCheckTime
	 *            预约审核的时间
	 * @param packetOrganization
	 *            预约审核的门店
	 * @return
	 */
	@RequestMapping(value = "/applyBonus", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> applyBonus(String userId, int packetType,
			BigDecimal packetCredit, int pcaketNumber, String splitType,
			String packetTitle, String packetInfo, String packetGoods,String packetCheckTime,String packetOrganization) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.applyBonus(userId, packetType,
					packetCredit, pcaketNumber, splitType, packetTitle,
					packetInfo, packetGoods,packetCheckTime,packetOrganization);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.LOGIC_COLLAPSECODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.DATA_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else{
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.ERRORMESSAGE));
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
	 * 
	 * 我的推荐.
	 * author: 严志森
	 *   date: 2015-12-13 下午3:34:39
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/getKickHistroy", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getKickHistroy(Long userId) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		List<Object> list=new ArrayList<Object>();
		Map<String, Object> map=null;
		String string1=null;
		String string2=null;
		try {
			Member member=memberService.find(userId);
			if(null == member){
				mapResult.put(UnivParameter.REASON, "会员不存在");
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				return mapResult;
			}
			List<Rebates> rebates=rebatesService.findList(member);
			if(rebates.size() == 0){
				mapResult.put(UnivParameter.REASON, "暂无推荐信息");
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				return mapResult;
			}
			for (Rebates rebates2 : rebates) {
				char rebatesType=rebates2.getRebatesType();
				int rebatesSpecies=rebates2.getRebatesSpecies().ordinal();
//				String product=rebatesService.findProduct(rebates2);
//				String product=rebates3.getProduct().getName();
				Long rebatesCredits=rebates2.getRebatesCredits();
				map=new HashMap<String, Object>();
				map.put("rebatesId", rebates2.getId());
				map.put("rebatesType", rebatesType);
				map.put("rebatesSpecies", rebatesSpecies);
				map.put("rebatesCredits", rebatesCredits);
//				map.put("product", product);
				if(rebatesType == '1'){
					string1 = "注册";
				}else{
					string1 = "购买商品";
				}
				if(rebatesSpecies == 1){
					string2 = "神币";
				}else if(rebatesSpecies == 2){
					string2 = "积分";
				}
				map.put("describe",string1+"获得"+rebatesCredits+string2);
				map.put("kickbackTime", format2.format(rebates2.getTime()));
				list.add(map);
			}
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			mapResult.clear();

			// 崩溃性逻辑处理错误CODE-0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);

			// 加载逻辑处理层崩溃性MESSAGE
			mapResult.put(UnivParameter.REASON, e.getMessage());

			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		
		mapResult.put(UnivParameter.RESULT, list);
		mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		return mapResult;
	}

	/**
	 * 
	 * 门店介绍/活动信息. author: 南金豆 date: 2015-10-30 下午6:10:26
	 * 
	 * @param shopId
	 *            门店编号
	 * @param shopInfoType
	 *            信息类别【1：门店介绍，2：门店活动, 3：店员介绍】
	 * @return
	 */
	@RequestMapping(value = "/getShopDetailAndActivity", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopDetailAndActivity(String shopId,
			int shopInfoType) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService
					.getShopDetailAndActivity(shopInfoType, shopId);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());

			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));

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
	 * 订单详情. author: 严志森 date:2015-12-30 15:53：50
	 * @param orderSn 订单串号
	 * @return
	 */
	@RequestMapping(value = "/getOrdersDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrdersDetail(String orderSn) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.getOrdersDetail(orderSn);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 收到的红包详情. author: 南金豆 date: 2015-11-12 下午12:52:12
	 * 
	 * @param packetLogId
	 *            红包记录ID
	 * @return
	 */
	@RequestMapping(value = "/getBonusDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBonusDetail(String packetLogId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.getBonusDetail(packetLogId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * APP首页，幸运排行榜 author: 南金豆 date: 2015-11-12 下午4:33:52
	 * 
	 * @param luckyNum
	 *            幸运排行取的数目（一般为5个）
	 * @return
	 */

	@RequestMapping(value = "/getRankingList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRankingList(int luckyNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.getRankingList(luckyNum);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * APP搜索界面，热门推荐标签 author:严志森 date: 2015-8-27 下午14:24:05
	 * @return
	 */
	@RequestMapping(value = "/getSearchMarkList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSearchMarkList() {
		Map<String, Object> mapResult=new HashMap<String, Object>();
		Setting setting=SystemUtils.getSetting();
		String[] string=setting.getHotSearches();
		List<String> list= Arrays.asList(string);
		 mapResult.put(UnivParameter.RESULT, list);
		 mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		return mapResult;
	}
	
	/**
	 * 
	 * 神币积分比例.
	 * author: 严志森
	 *   date: 2015-12-25 下午4:16:20
	 * @return
	 */
	@RequestMapping(value = "/duihuan", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> duihuan() {
		Map<String, Object> mapResult=new HashMap<String, Object>();
		Setting setting=SystemUtils.getSetting();
		String list=setting.getGodMoneyExchangePoint();
		 mapResult.put(UnivParameter.RESULT, list);
		 mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		return mapResult;
	}
	
	/**
	 * 
	 * 人民币神币比例.
	 * author: 严志森
	 *   date: 2015-12-25 下午4:16:46
	 * @return
	 */
	@RequestMapping(value = "/duihuans", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> duihuans() {
		Map<String, Object> mapResult=new HashMap<String, Object>();
		Setting setting=SystemUtils.getSetting();
		String list=setting.getMoneyRechargeGodMoney();
		mapResult.put(UnivParameter.RESULT, list);
		 mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		return mapResult;
	}
	
	/**
	 * 人民币神币充值比例.
	 * author: yanzhisen
	 * 	 date: 2016-2-29 下午13:59:20
	 * @return
	 */
	@RequestMapping(value = "/chongzhi", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> chongzhi() {
		Map<String, Object> mapResult=new HashMap<String, Object>();
		Setting setting=SystemUtils.getSetting();
		String list=setting.getGodMoneyRechargeMoney();
		mapResult.put(UnivParameter.RESULT, list);
		mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		return mapResult;
	}
	
	/**
	 * app版本号.
	 * @author:严志森
	 * @date: 2016年4月12日 上午10:33:04 
	 * @param type 类型 1 Android 2 IOS
	 * @return
	 */
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> version(int type) {
		Map<String, Object> mapResult=new HashMap<String, Object>();
		Setting setting=SystemUtils.getSetting();
		String list = null;
		switch (type) {
		case 1:
			list=setting.getAndriodVersion();
			break;
		case 2:
			list=setting.getiOSVersion();
			break;
		default:
			break;
		}
		mapResult.put(UnivParameter.RESULT, list);
		mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		return mapResult;
	}
	
	/**
	 * 
	 * 获取当前服务器时间.
	 * author: 严志森
	 *   date: 2015-12-17 下午2:51:03
	 * @return
	 */
	@RequestMapping(value = "/getDate", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDate() {
		Map<String, Object> mapResult=new HashMap<String, Object>();
		Date date=new Date(); 
		mapResult.put("date", date);
		mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		return mapResult;
	}

    /**
     *
     * 获取帮助中心分类.
     * author: 严志森
     *   date: 2016-05-05 下午2:51:03
     * @return
     */
    @RequestMapping(value = "/getHelpTree", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHelpTree() {
        Map<String, Object> mapResult=new HashMap<String, Object>();
        Map<String, Object> Object = new HashMap<String, Object>();
        List<Object> list1 = new ArrayList<Object>();
        List<ProblemClassification> list = problemClassificationService.findTree();
        for (ProblemClassification lists : list){
            Object = new HashMap<String, Object>();
            Object.put("treeName",lists.getName());
            Object.put("treeGrade",lists.getGrade());
            if( lists.getParent() == null){
                Object.put("treeParent",0);
            }else{
                Object.put("treeParent",lists.getParent().getId());
            }

            Object.put("treeId",lists.getId());
            list1.add(Object);
        }
        mapResult.put(UnivParameter.DATA, list1);
        mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
        return mapResult;
    }

    /**
     *
     * 获取帮助内容.
     * author: 严志森
     *   date: 2016-05-05 下午3:51:03
     * @return
     */
    @RequestMapping(value = "/getHelpTreeContent", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHelpTreeContent(Long id) {
        Map<String, Object> mapResult=new HashMap<String, Object>();
        Map<String, Object> Object = new HashMap<String, Object>();
        List<Object> list1 = new ArrayList<Object>();
        List<ProblemInformation> list = problemInformationService.findByParent(problemClassificationService.find(id));
        for (ProblemInformation lists : list){
            Object = new HashMap<String, Object>();
            Object.put("contentTitle",lists.getTitle());
            Object.put("contentDesc",lists.getDesc());
            list1.add(Object);
        }
        mapResult.put(UnivParameter.DATA, list1);
        mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
        return mapResult;
    }

	/**
	 * 【今日抢】抢购成功：成功界面展示接口：商品图片、简介+绑定推荐配套商品图片、名称等（绑定推荐等只限定与商品部分】
     * author: 南金豆
	 * date: 2015-11-11 下午4:47:07
	 * 
	 * @param grabSeckillId
	 *            抢购编号
	 * @param robGoodsId
	 *            抢购商品编号
	 * @param robGoodsType
	 *            商品类型【1：商品，2：神币类红包 3：积分类红包，4：实物类红包 5：优惠券】
	 * @param userId
	 *            会员编号
	 * @return
	 */
	@RequestMapping(value = "/getRushGoodsRecommend", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRushGoodsRecommend(String grabSeckillId,
			String robGoodsId, int robGoodsType, String userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			if(robGoodsType==1){
				mapResult=productService.getRushGoodsRecommend(grabSeckillId,
						robGoodsId);
			}else{
				// 调用数据处理层，并接受结果数据
				mapResult = appIndexService.getRushGoodsRecommend(grabSeckillId,
						robGoodsId, robGoodsType, userId);
			}
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 价格核算 author: 严志森 date: 2015-8-27 下午15:30:05
	 * @param userId 用户id
     * @param ids 购物车项ID 数组
	 * @return
	 */
	@RequestMapping(value = "/getPrice", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTreeByParentID(Long userId,Long[] ids) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Cart cart = cartService.getPrice(userId,ids);
            BigDecimal effectivePrice = cartService.getEffectivePrice(cart);
			data.put("effectivePrice",effectivePrice);// 总价
//			data.put("discountPrice", cart.getDiscount());// 折扣
//			data.put("quantity", cart.getProductQuantity());// 总数
//			data.put("effectiveRewardPoint", cart.getRewardPoint());// 赠送总积分
			data.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			data.clear();

			// 崩溃性逻辑处理错误CODE-0
			data.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);

			// 加载逻辑处理层崩溃性MESSAGE
			data.put(UnivParameter.REASON, e.getMessage());

			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		
		return data;
		
	}

	

	/**
	 * 会员资料信息获取 author: 严志森 date: 2015-8-27 下午16:20:05
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserDetail(String userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService
					.getUserDetail(userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 验证旧密码.
	 * author: 严志森  
	 * date: 2016-3-12 下午4:04:54
	 * 
	 * @param userId
	 *            会员编号
	 *@param oldPassword
	 *			    旧密码
	 * @return
	 */

	@RequestMapping(value = "/validateOldPassWord", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> validateOldPassWord(Long userId,String oldPassword) {
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		try {
			Member member=memberService.find(userId);
			if( member == null ){
				// 失败的返回参数CODE--0
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.REASON,"用户不存在");
			}
			//匹配旧密码是否匹配
			if(! member.getPassword().equals(DigestUtils.md5Hex(oldPassword))){
				
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.REASON,"旧密码输入错误");
			}else{
				// 成功
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.RESULT,null);
			}
			
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			mapResult.clear();

			// 崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;

			// 加载逻辑处理层崩溃性MESSAGE
			mapResult.put(UnivParameter.REASON, e.getMessage());

			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		return mapResult;
	}
	
	
	/**
	 * 
	 * 会员性别/昵称/手机号码/邮箱保存 
	 * author: 南金豆 date: 
	 * 2015-10-28 下午12:24:54
	 * 
	 * @param userId
	 *            会员编号
	 * @param changeType
	 *            变更类型【1：性别，2：昵称，3：手机号码，  5：收货地址  6：密码】
	 * @param userSex
	 *            会员性别【0：男，1：女】
	 * @param userNickName
	 *            会员昵称
	 * @param userPhoneNumber
	 *            会员手机号码
	 *@param userAdress
	 *            会员地址            
	 *@param userPassword
	 *            会员密码
	 *@param oldPassword
	 *			    旧密码
	 * @return
	 * 
	 * update 严志森
	 * 2015-12-07 下午2:04:54
	 */

	@RequestMapping(value = "/setuUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setuUserInfo(int changeType, Long userId,int userSex,String userNickName,String userPhoneNumber,
			String userAdress,String userPassword,String oldPassword) {
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Member member=memberService.find(userId);
		try {
			switch (changeType) {
			// 修改性别
			case 1:
				if(userSex == 0){
					member.setGender(Gender.male);
				}else{
					member.setGender(Gender.female);
				}
				
				memberService.update(member);
				// 修改成功的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,
						UnivParameter.DATA_CORRECTCODE);
				break;
			// 修改昵称
			case 2:
				member.setNickname(userNickName);
				memberService.update(member);
				// 修改成功的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,
						UnivParameter.DATA_CORRECTCODE);
				break;
			// 修改手机号码
			case 3:
				Member member2 = memberService.findByPhone(userPhoneNumber);
				if(member2 != null){
					// 修改失败的返回参数CODE--0
					mapResult.put(UnivParameter.CODE,
							UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.REASON,
							"手机号码已经存在");
				}else{
					member.setPhone(userPhoneNumber);
					memberService.update(member);
					// 修改成功的返回参数CODE--1
					mapResult.put(UnivParameter.CODE,
							UnivParameter.DATA_CORRECTCODE);
				}
				
				break;
			// 修改用户名 不可修改
			case 4:
//				member.setUsername(userName);
//				memberService.update(member);
//				// 修改成功的返回参数CODE--1
//				mapResult.put(UnivParameter.CODE,
//						UnivParameter.DATA_CORRECTCODE);
//				break;
			// 修改地址
			case 5:
				member.setAddress(userAdress);
				memberService.update(member);
				// 修改成功的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,
						UnivParameter.DATA_CORRECTCODE);
				break;
			// 修改密码
			case 6:
				member.setPassword(DigestUtils.md5Hex(userPassword));
				memberService.update(member);
				// 修改成功的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				break;
			}
			
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			mapResult.clear();

			// 崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;

			// 加载逻辑处理层崩溃性MESSAGE
			mapResult.put(UnivParameter.REASON, e.getMessage());

			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		return mapResult;
	}

	/**
	 * 商品晒论列表.
	 * TODO
	 * author: 严志森
	 *   date: 2015-12-7 下午3:41:06
	 * @param goodsId 商品id
	 * @param pageSize 每页显示数量
	 * @param count 总数量
	 * @return
	 */
	@RequestMapping(value = "/getComment", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getComment(Long goodsId,int pageSize,int count) {
		//定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		List<Object> list=new ArrayList<Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> map=null;
		try {
			//调用数据处理层，并接受结果数据
			//两数相除，如果有余数则取整加1
			ProductModel model=productService.find(goodsId).getProductModel();
			int pageNumber = count%pageSize ==0?count/pageSize :count/pageSize +1;
			Pageable pageable = new Pageable(pageNumber , pageSize );
			Page<Review> page=reviewService.findPage(model,pageable);
			for (Review review : page.getContent()) {
				map=new HashMap<String, Object>();
				map.put("reviewId", review.getId());
				map.put("content", review.getContent());
				map.put("createDate", review.getCreateDate());
				map.put("imgs", review.getImages());
				list.add(map);
			}
			//数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT, list);
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
	 * 
	 * 订单跟踪信息. author: yanzhisen date: 2015-8-28 上午9:59:33
	 * 
	 * @param ordersId 订单id
	 * @return
	 */
	@RequestMapping(value = "/getOrdersExpress", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrdersExpress(Long ordersId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		try {
			// 调用数据处理层，并接受结果数据
			Order order = orderService.find(ordersId);

			if( null == order ){
				result.put(UnivParameter.REASON, "订单不存在");
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				return result;
			}
			List<Shipping> shippings=shippingService.findByOrder(order);
			if(shippings.size()<1){
				result.put(UnivParameter.REASON, "暂无物流信息");
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				return result;
			}else{
				Shipping shipping = shippings.toArray( new Shipping[shippings.size()] )[0];
				TrackingLog trackingLog=trackingLogService.findByTrackingId(shipping.getTrackingNo());
				List<ResultItem> logistics=trackingLog.getTrackingInfo();
				for (ResultItem resultItem : logistics) {
					map = new HashMap<String, Object>();
					map.put("time", resultItem.getFtime());
					map.put("context", resultItem.getContext());
					list.add(map);
				}
				map2.put("wuliu", list);
				map2.put("nu",shipping.getTrackingNo());
				map2.put("deliveryCorp",shipping.getDeliveryCorp());
				map2.put("orderStatus",order.getStatus().ordinal());
				
			}
			result.put(UnivParameter.CODE , UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.RESULT, map2);
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
		return result;
	}

	/**
	 * 
	 * 商品保障介绍. 南金豆 date: 2015-10-29 下午5:06:41
	 * 
	 * @param goodsId 商品id
	 * @return
	 */
	@RequestMapping(value = "/getGoodsAfterSale", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getGoodsAfterSale(String goodsId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService
					.getGoodsAfterSale(goodsId);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 门店列表获取. author: 南金豆 date: 2015-11-11 上午10:44:45
	 * 
	 * @param pageLoadType
	 *            分页方向【 0：刷新，1：加载更多】
	 * @param pageRowsCount
	 *            每页加载数据条数
	 * @param shopId
	 *            起始门店编号
	 * @param userXCoordinate
	 *            会员地理坐标X坐标（必须）
	 * @param userYCoordinate
	 *            会员地理坐标y坐标（必须）
	 * @return
	 */
	@RequestMapping(value = "/getShop", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShop(int pageLoadType, int pageRowsCount,
			String shopId, Float userXCoordinate, Float userYCoordinate) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService.getShop(
					pageLoadType, pageRowsCount, shopId, userXCoordinate,
					userYCoordinate);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 门店列表ID获取. author: 南金豆 date: 2015-11-12 下午3:46:03
	 * 
	 * @param userXCoordinate
	 *            会员地理坐标X坐标（必须）
	 * @param userYCoordinate
	 *            会员地理坐标y坐标（必须）
	 * @return
	 */
	@RequestMapping(value = "/getShopId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopId(Float userXCoordinate,
			Float userYCoordinate) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService.getShopId(
					userXCoordinate, userYCoordinate);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 红包发布历史. author: 南金豆 date: 2015-10-29 上午10:26:46
	 * 
	 * @param userId
	 *            会员编号
	 * @param pageLoadType
	 *            分页方向【0：刷新，1：加载更多】
	 * @param pageRowsCount
	 *            每页加载数据条数
	 * @param packetId
	 *            起始红包编号
	 * @return
	 */
	@RequestMapping(value = "/getBonusHistroy", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBonusHistroy(String userId, int pageLoadType,
			int pageRowsCount, String packetId) {
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService.getBonusHistroy(
					userId, pageLoadType, pageRowsCount, packetId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 会员预订商品列表. author: 严志森 date: 2015-8-28 上午11:32:20
	 * 
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/getReserve", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReserve(Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.getReserve(userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 会员卡券信息获取. author: 严志森 date: 2015-10-14 下午5:50:29
	 * 
	 * @param userId
	 *            用户id
	 * @param pageLoadType
	 *            分页方向【0：刷新，1：加载更多】
	 * @param pageRowsCount
	 *            每页加载数据条数
	 * @param couponId
	 *            起始卡券编号
	 * @param couponType
	 *            卡券使用范围
	 * @param type
	 *            卡券类型
	 * @return
	 */
	@RequestMapping(value = "/getCoupon", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCoupon(String userId, int pageLoadType,
			int pageRowsCount, String couponId, String couponType, String type) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.getCoupon(userId, couponType, type,
					couponId, pageLoadType, pageRowsCount);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 会员货币信息加载. author: yanzhisen date: 2015-8-28 下午1:13:35
	 * 
	 * @param userId 用户id
	 * @param getInfoType 信息类型 1 神币 2积分
	 * @return
	 */
	@RequestMapping(value = "/getCurrency", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCurrency(String userId, String getInfoType) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult =new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
//			Map<String, Object> mapResult = appIndexService.getCurrency(userId);
			mapResult = appIndexService.getHistory(userId,
					getInfoType);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(UnivParameter.CODE).toString())) {
				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 添加到购物车 author: 严志森 date: 2015-9-11 下午1:40:55
	 * 
	 * @param userId 用户id
	 * @param productId 商品ID
	 * @param count 数量
     * @param productBinds 绑定商品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCart", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> addCart(Long userId, Long productId, int count,
			Long[] productBinds) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			if (count <= 0) {
				code = UnivParameter.DATA_ERRORCODE;
				result.put(UnivParameter.REASON, "数量错误");
				result.put(UnivParameter.CODE, code);
				return result;
			}

			// 调用数据处理层，并接受结果数据
			Product product = productService.find(productId);
			List<Product> bindProducts = new ArrayList<Product>();
			if (ArrayUtils.isNotEmpty(productBinds)) {
				bindProducts = productService.findList(productBinds);
			}

			if (product == null) {
				code = UnivParameter.DATA_ERRORCODE;
				result.put(UnivParameter.REASON, "不存在该商品");
				result.put(UnivParameter.CODE, code);
				return result;
			}
			if (!Product.Type.general.equals(product.getType())) {
				code = UnivParameter.DATA_ERRORCODE;
				result.put(UnivParameter.REASON, "该商品非卖品");
				result.put(UnivParameter.CODE, code);
				return result;
			}
			if (!product.getIsMarketable()) {
				code = UnivParameter.DATA_ERRORCODE;
				result.put(UnivParameter.REASON, "已下架该商品");
				result.put(UnivParameter.CODE, code);
				return result;
			}

			Member member = memberService.find(userId);
			// 当前购物车
			Cart cart = cartService.getCurrent(member.getUsername());

			if (cart != null) {
				// 校验普通商品的库存

				List<CartItem> cartItems = appIndexService.getCartItem(cart,
						product);

				if (cartItems != null && cartItems.size() > 0) {
					// 购物车已经存在该商品
					// 统计已经存在的商品数量
					int productCount = 0;
					for (CartItem tempItem : cartItems) {
						productCount += tempItem.getQuantity();
					}

					if (CartItem.MAX_QUANTITY != null
							&& (productCount + count) > CartItem.MAX_QUANTITY) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出数量");
						result.put(UnivParameter.CODE, code);
						return result;
					}

					if (productCount + count > product.getAvailableStock()) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出库存");
						result.put(UnivParameter.CODE, code);
						return result;
					}
				} else {
					// 购物车不存在该商品
					if (Cart.MAX_CART_ITEM_COUNT != null
							&& cart.getCartItems().size() >= Cart.MAX_CART_ITEM_COUNT) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出数量");
						result.put(UnivParameter.CODE, code);
						return result;
					}
					if (CartItem.MAX_QUANTITY != null
							&& count > CartItem.MAX_QUANTITY) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出数量");
						result.put(UnivParameter.CODE, code);
						return result;
					}
					// 校验商品的库存
					if (count > product.getAvailableStock()) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出库存");
						result.put(UnivParameter.CODE, code);
						return result;
					}
				}
				/**
				 * 校验绑定商品 库存是否
				 */
				for (Product bindProduct : bindProducts) {
					if (count > bindProduct.getAvailableStock()) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, bindProduct.getName()+"绑定商品库存不足");
						result.put(UnivParameter.CODE, code);
						return result;
					}
				}
			}

			/**
			 * 商品添加到购物车
			 */
			Map<String, Object> mapResult = appIndexService.getBind(productId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				List<CartItemBindProductValue> cartItemBindProductValues = (List<CartItemBindProductValue>) mapResult
						.get(UnivParameter.DATA);
				List<CartItemBindProductValue> tempList = new ArrayList<CartItemBindProductValue>();
				if(ArrayUtils.isNotEmpty(productBinds)){
					for (int i = 0; i < productBinds.length; i++) {
						for (int j = 0; j < cartItemBindProductValues.size(); j++) {
							if (cartItemBindProductValues.get(j).getId() == productBinds[i]) {
								tempList.add(cartItemBindProductValues.get(j));
							}
						}
					}
				}
				
				cart = cartService.add(product, count, tempList, userId);
				// 数据处理层崩溃性结果判断--1
				if (StringUtils.isEmpty(cart)) {

					// 崩溃性数据处理错误CODE-1
					code = UnivParameter.LOGIC_COLLAPSECODE;
				} else {
					// 数据处理层正确性结果判断--加载正确的数据结果集
					result.put(UnivParameter.RESULT, cart.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 
	 * 添加到购物车 author: 严志森 date: 2015-9-11 下午1:40:55
	 * 
	 * @param userId 用户id
	 * @param productId 商品ID
	 * @param count 数量
     * @param json 绑定商品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCartIos", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> addCartIos(Long userId, Long productId, int count,
			String json) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			if (count <= 0) {
				code = UnivParameter.DATA_ERRORCODE;
				result.put(UnivParameter.REASON, "数量错误");
				result.put(UnivParameter.CODE, code);
				return result;
			}
			JSONArray jsonArray = JSONArray.fromObject(json);
			Object[] abc =   jsonArray.toArray();
			Long[] productBinds = new Long[abc.length];
			for (int idx = 0; idx < abc.length; idx++) {
				productBinds[idx] = Long.valueOf(abc[idx].toString());
	        }
			// 调用数据处理层，并接受结果数据
			Product product = productService.find(productId);
			List<Product> bindProducts = new ArrayList<Product>();
			if (ArrayUtils.isNotEmpty(productBinds)) {
				bindProducts = productService.findList(productBinds);
			}

			if (product == null) {
				code = UnivParameter.DATA_ERRORCODE;
				result.put(UnivParameter.REASON, "不存在该商品");
				result.put(UnivParameter.CODE, code);
				return result;
			}
			if (!Product.Type.general.equals(product.getType())) {
				code = UnivParameter.DATA_ERRORCODE;
				result.put(UnivParameter.REASON, "该商品非卖品");
				result.put(UnivParameter.CODE, code);
				return result;
			}
			if (!product.getIsMarketable()) {
				code = UnivParameter.DATA_ERRORCODE;
				result.put(UnivParameter.REASON, "已下架该商品");
				result.put(UnivParameter.CODE, code);
				return result;
			}

			Member member = memberService.find(userId);
			// 当前购物车
			Cart cart = cartService.getCurrent(member.getUsername());

			if (cart != null) {
				// 校验普通商品的库存

				List<CartItem> cartItems = appIndexService.getCartItem(cart,
						product);

				if (cartItems != null && cartItems.size() > 0) {
					// 购物车已经存在该商品
					// 统计已经存在的商品数量
					int productCount = 0;
					for (CartItem tempItem : cartItems) {
						productCount += tempItem.getQuantity();
					}

					if (CartItem.MAX_QUANTITY != null
							&& (productCount + count) > CartItem.MAX_QUANTITY) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出数量");
						result.put(UnivParameter.CODE, code);
						return result;
					}

					if (productCount + count > product.getAvailableStock()) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出库存");
						result.put(UnivParameter.CODE, code);
						return result;
					}
				} else {
					// 购物车不存在该商品
					if (Cart.MAX_CART_ITEM_COUNT != null
							&& cart.getCartItems().size() >= Cart.MAX_CART_ITEM_COUNT) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出数量");
						result.put(UnivParameter.CODE, code);
						return result;
					}
					if (CartItem.MAX_QUANTITY != null
							&& count > CartItem.MAX_QUANTITY) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出数量");
						result.put(UnivParameter.CODE, code);
						return result;
					}
					// 校验商品的库存
					if (count > product.getAvailableStock()) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, "超出库存");
						result.put(UnivParameter.CODE, code);
						return result;
					}
				}
				/**
				 * 校验绑定商品 库存是否
				 */
				for (Product bindProduct : bindProducts) {
					if (count > bindProduct.getAvailableStock()) {
						code = UnivParameter.DATA_ERRORCODE;
						result.put(UnivParameter.REASON, bindProduct.getName()+"绑定商品库存不足");
						result.put(UnivParameter.CODE, code);
						return result;
					}
				}
			}

			/**
			 * 商品添加到购物车
			 */
			Map<String, Object> mapResult = appIndexService.getBind(productId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				List<CartItemBindProductValue> cartItemBindProductValues = (List<CartItemBindProductValue>) mapResult
						.get(UnivParameter.DATA);
				List<CartItemBindProductValue> tempList = new ArrayList<CartItemBindProductValue>();
				if(ArrayUtils.isNotEmpty(productBinds)){
					for (int i = 0; i < productBinds.length; i++) {
						for (int j = 0; j < cartItemBindProductValues.size(); j++) {
							if (cartItemBindProductValues.get(j).getId() == productBinds[i]) {
								tempList.add(cartItemBindProductValues.get(j));
							}
						}
					}
				}
				
				cart = cartService.add(product, count, tempList, userId);
				// 数据处理层崩溃性结果判断--1
				if (StringUtils.isEmpty(cart)) {

					// 崩溃性数据处理错误CODE-1
					code = UnivParameter.LOGIC_COLLAPSECODE;
				} else {
					// 数据处理层正确性结果判断--加载正确的数据结果集
					result.put(UnivParameter.RESULT, "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 
	 * 生成购物车订单.
	 * author: 严志森
	 *   date: 2015-12-15 下午4:24:24
	 * @param userId 用户id
	 * @param receiverId 收货id
	 * @param paymentMethodId 支付方式
	 * @param shippingMethodId 配送方式
	 * @param couponCodes 优惠券
	 * @param invoiceTitle 发票抬头
	 * @param godMoneyNum 神币
	 * @param recommended 推荐人手机
     * @param date 取货时间
     * @param organizationId 自提点id
	 * @return
	 */
	@RequestMapping(value = "/createCartOrderIos", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> createCartOrderIos(Long userId, Long receiverId,String cartToken,
			Long paymentMethodId, Long shippingMethodId, String json,String date,
			String invoiceTitle, BigDecimal godMoneyNum, String recommended,Long organizationId){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE; 
		
		try {
			JSONArray jsonArray = JSONArray.fromObject(json);
			Object[] abc =   jsonArray.toArray();
			Long[] couponCodes = new Long[abc.length];
			for (int idx = 0; idx < abc.length; idx++) {
				couponCodes[idx] = Long.valueOf(abc[idx].toString());
	        }
			mapResult = appIndexService.createCartOrder(userId,  receiverId,cartToken,
					 paymentMethodId,  shippingMethodId,  couponCodes,
					 invoiceTitle,  godMoneyNum,  recommended,organizationId,date);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else if(UnivParameter.CART_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())){
				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.CART_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
		}else {
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT,
					mapResult.get(UnivParameter.DATA));
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	
	/**
	 * 
	 * 清空购物车. author: 严志森 date: 2015-11-28 下午1:10:02
	 * 
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/deleteCart", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteCart(String userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			Cart cart = cartService.getCurrent(memberService.find(
					Long.parseLong(userId)).getUsername());
			// 调用数据处理层，并接受结果数据
			cartService.delete(cart);
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT, "删除成功 ");
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
	 * 
	 * 淘更多界面初始化.
	 * author: 严志森
	 *   date: 2015-12-24 上午11:25:48
	 * @param goodsType 类型
	 * @param userId 用户id
	 * @param pageSize 每页数量
	 * @param count 当前显示总数
	 * @return
	 */
	@RequestMapping(value = "/getMorePromotionGoods", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMorePromotionGoods(int goodsType,@RequestParam(defaultValue = "-1")String userId,int pageSize ,int count) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> result1 = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		List<Product> list=new ArrayList<Product>();
		List<Object> list2=new ArrayList<Object>();
		Map<String, Object> map=null;
		String keyword ;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		try {
			if (goodsType == 1) {
				// 调用数据处理层，并接受结果数据
				// Tag tag=tagService.find(2L);
				mapResult = appIndexService.listGoodsByTagType(goodsType);
			}
			if (goodsType == 2) {
				// 调用数据处理层，并接受结果数据
				// Tag tag=tagService.find(1L);
				mapResult = appIndexService.listGoodsByTagType(goodsType);
			}
			if (goodsType == 3) {
				//未登录
				if(userId.equals("-1")){
					keyword=CommonParameter.KEYWORD;
					
				}else{
					keyword=memberService.find(Long.parseLong(userId)).getKeywords();
					//判断用户是否选择了关键字，否，给定默认关键字 iphone
					if(StringUtils.isEmpty(keyword)){
						keyword = CommonParameter.KEYWORD;
					}
				}
				//两数相除，如果有余数则取整加1
				int pageNumber = count%pageSize ==0?count/pageSize :count/pageSize +1;
				Pageable pageable = new Pageable(pageNumber , pageSize );
//				Pageable pageable = new Pageable(null , CommonParameter.MAYBELOVE_PAGESIZE );
				//查询猜你喜欢数据
				Page<Product> products = searchService.search(keyword, null, null,null, pageable);
				list=products.getContent();
				if(list.size()>0){
					for (Product product : list) {
						map=new HashMap<String, Object>();
                        map.put("id", product.getId());
						map.put("goodsId", product.getId());
						map.put("goodsName", product.getName());
						map.put("goodsSubhead", product.getCaption());
						map.put("goodsPrice", product.getPrice());
						map.put("goodsImagePath", product.getProductImages().get(0).getSource());
						
						result1 = appIndexService.findPromotionByProduct(1, product.getId());
						if (UnivParameter.DATA_CORRECTCODE.equals(result1.get(
								UnivParameter.CODE).toString())) {
							map.put("hui", result1.get(UnivParameter.DATA));
						}else{
							result.put(UnivParameter.REASON,
									mapResult.get(UnivParameter.ERRORMESSAGE).toString());
							result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
							return result;
						}
						
						result1 = appIndexService.findPromotionByProduct(2, product.getId());
						if (UnivParameter.DATA_CORRECTCODE.equals(result1.get(
								UnivParameter.CODE).toString())) {
							map.put("zeng", result1.get(UnivParameter.DATA));
						}else{
							result.put(UnivParameter.REASON,
									mapResult.get(UnivParameter.ERRORMESSAGE).toString());
							result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
							return result;
						}
						
						result1 = appIndexService.findPromotionByProduct(3, product.getId());
						if (UnivParameter.DATA_CORRECTCODE.equals(result1.get(
								UnivParameter.CODE).toString())) {
							map.put("cu", result1.get(UnivParameter.DATA));
						}else{
							result.put(UnivParameter.REASON,
									mapResult.get(UnivParameter.ERRORMESSAGE).toString());
							result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
							return result;
						}
						list2.add(map);
					}
				}
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.DATA, list2);
				
			}

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
     * 会员收获地址新增/变更/删除.
     *
     * author: 严志森 date: 2015-9-22 上午10:01:17
     * @param changeType 1:新增 2:变更 3:删除
     * @param receiver
     * @param areaId
     * @param userId
     * @return
     */
	@RequestMapping(value = "/setAddress", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setAddress(int changeType, Receiver receiver,
			Long areaId, Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;

		Map<String, Object> mapResult = null;
		try {
			// 新增
			if (changeType == 1) {
				// 调用数据处理层，并接受结果数据
				mapResult = appIndexService
						.addAddress(receiver, areaId, userId);
			}
			// 修改
			if (changeType == 2) {
				// 调用数据处理层，并接受结果数据
				mapResult = appIndexService.updateAddress(receiver, areaId,userId);
			}
			// 删除
			if (changeType == 3) {
				// 调用数据处理层，并接受结果数据
				mapResult = appIndexService.deleteAddress(receiver);
			}

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,new HashMap<>());
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
	 * 
	 * 设置默认收货地址. author: 严志森 date: 2015-11-27 下午5:39:02
	 * 
	 * @param userId
	 *            用户id
	 * @param addressId
	 *            收货地址id
	 * @return
	 */
	@RequestMapping(value = "/setDefaultAddress", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setDefaultAddress(Long userId, Long addressId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;

		Map<String, Object> mapResult = null;
		
		try {
			mapResult=appIndexService.setDefaultAddress(userId, addressId);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				result.put(UnivParameter.CODE,mapResult.get(UnivParameter.CODE));

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.DATA).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.CODE,mapResult.get(UnivParameter.CODE));
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
			}
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();

			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);

			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());

			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 商品详情 author: 南金豆 date: 2015-11-16 上午9:47:36
	 * 
	 * @param goodsId 商品id
	 * @return
	 */
	@RequestMapping(value = "/getGoodsDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getGoodsDetail(@RequestParam(defaultValue="")String goodsId,@RequestParam(defaultValue="")String productSn) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.getGoodsDetail(goodsId,productSn);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.LOGIC_COLLAPSECODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.DATA_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 商品介绍图文详情 author: yanzhisen date: 2015-8-28 上午10:40:28
	 * 
	 * @param goodsId 商品id
	 * @return
	 */
	@RequestMapping(value = "/getGoodsImageText", method = RequestMethod.GET)
	@ResponseBody
	public Result getGoodsImageText(String goodsId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService
					.getGoodsImageText(goodsId);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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

		return new Result(code, result);
	}
	
	
	/**商品包装售后
	 * author: yanzhisen 
	 * date: 2015-12-28 下午4:40:28
	 * 
	 * @param goodsId 商品id
	 * @return
	 */
	@RequestMapping(value = "/getGoodsSaleSupport", method = RequestMethod.GET)
	@ResponseBody
	public Result getGoodsSaleSupport(String goodsId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService
					.getGoodsSaleSupport(goodsId);

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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

		return new Result(code, result);
	}
	
	/**
	 * 商品规格参数介绍 author: yanzhisen date: 2015-9-2 下午15:25:25
	 * @param goodsId 商品id
     * @param type 类型 1 参数 2 规格
	 * @return
	 */
	@RequestMapping(value = "/getGoodsParameter", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getGoodsParameter(String goodsId, int type) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			if (type == 1) {
				// 调用数据处理层，并接受结果数据
				mapResult = appIndexService.getParameterText(goodsId);
			}
			if (type == 2) {
				// 调用数据处理层，并接受结果数据
				mapResult = appIndexService.getSpecificationText(goodsId);
			}

			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 商品促销详情 (放到商品详情里面一并返回)
     * author: 南金豆 date: 2015-11-16 下午3:40:51
	 * 
	 * @param goodsId
	 *            商品编号
	 * @param promotionType
	 *            促销类型【1：满送（满5000赠Iphone），2：买赠（买商品送Ipone），3：组合（几个商品组合在一起卖）
	 * @return
	 */
	@RequestMapping(value = "/getGoodsPromotionDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getGoodsPromotionDetail(String goodsId,
			int promotionType) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.getGoodsPromotionDetail(goodsId,
					promotionType);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 操作订单.
	 * author: 严志森
	 *   date: 2015-12-3 下午2:07:13
	 * @param orderId 订单串号
	 * @param changeType 类型 1:删除  2:取消  3:确认
	 * @return
	 */
	@RequestMapping(value = "/operationOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> operationOrder(Long orderId,int changeType,Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Order order =new Order();
		try {
			order = orderService.findBySn(orderId.toString());
			if( null ==order ){
				code = UnivParameter.DATA_ERRORCODE;
				// 数据处理层正确性结果判断--加载错误的数据结果集
				result.put(UnivParameter.REASON, "订单不存在");
			}else{
				switch (changeType) {
				case 1://删除
					order.setIsDelete(true);
					orderService.update(order);
					break;
				case 2://取消
					//订单未处于未付款状态
					if( !Order.Status.pendingPayment.equals(order.getStatus()) ){
						code = UnivParameter.DATA_ERRORCODE;
						// 数据处理层正确性结果判断--加载错误的数据结果集
						result.put(UnivParameter.REASON, "已付款无法取消");
					}else{
						orderService.cancel(order);
					}
					break;
				case 3://确认收货
					//订单未处于未收货状态
					if( !Order.Status.pendingReceive.equals(order.getStatus()) ){
						code = UnivParameter.DATA_ERRORCODE;
						// 数据处理层正确性结果判断--加载错误的数据结果集
						result.put(UnivParameter.REASON, "不是待收货订单");
					}else{
						orderService.receiver( order , memberService.find(userId)  );
					}
					break;
				default:
					break;
				}
			}
			result.put(UnivParameter.RESULT, new HashMap<>());
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 运营商列表.
	 * author: 严志森
	 *   date: 2015-12-9 下午3:53:53
	 * @return
	 */
	@RequestMapping(value = "/operator", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> operator() {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> map = null;
		try {
			List<Operator> operator = operatorService.findNoFei();
			if(operator.size()<1){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "无运营商");
			}else{
				//存放运营商
				List<Object> list=new ArrayList<Object>();
				for (Operator operator2 : operator) {
					map=new HashMap<String, Object>();
					map.put("operatorId", operator2.getId());
					map.put("operatorName", operator2.getName());
					list.add(map);
				}
				result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				result.put(UnivParameter.RESULT, list);
			}
			
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();

			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());

			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 根据运营商选择合约套餐.
	 * author: 严志森
	 *   date: 2015-12-9 下午4:00:26
	 * @param operatorId 运营商id默认为2
	 * @return
	 */
	@RequestMapping(value = "/selectContract", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectContract(@RequestParam(defaultValue = "2")String operatorId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		
		Map<String, Object> map = null;
		try {
			Operator operator=operatorService.find(Long.parseLong(operatorId));
			List<Contract> contracts = contractService.findByOperator(operator);
			
			List<ContractItem> contractItem=contractItemService.findByContract(contracts.get(0));
			if(contractItem.size()<1){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "无套餐");
			}else{
				//存放套餐
				List<Object> list=new ArrayList<Object>();
				for (ContractItem contractItem2 : contractItem) {
					map=new HashMap<String, Object>();
					map.put("id", contractItem2.getId());
					map.put("lasttime", contractItem2.getLasttime());
					map.put("monthCost", contractItem2.getMonthCost());
					map.put("desc", contractItem2.getDesc());
					list.add(map);
				}
				result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				result.put(UnivParameter.RESULT, list);
			}
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
	
			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
	
			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		
	}
		return result;
	}
	
	/**
	 * 根据运营商选择合约套餐.
	 * author: 严志森
	 *   date: 2015-12-9 下午4:00:26
=	 * @param contractId 套餐id
	 * @return
	 */
	@RequestMapping(value = "/selectContractByid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectContractByid(String contractId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ContractItem contracts = contractItemService.find(Long.parseLong(contractId));
			if(null == contracts){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "不存在");
			}else{
				//存放套餐
				List<Object> list=new ArrayList<Object>();
				map.put("desc", contracts.getDesc());
				list.add(map);
				result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				result.put(UnivParameter.RESULT, list);
			}
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
	
			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
	
			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		
	}
		return result;
	}
	
	/**
	 * 
	 * 号码列表.
	 * author: 严志森
	 *   date: 2015-12-9 下午5:13:56
	 * @param operatorId 运营商id
	 * @param count 数量
     * @param key 关键字
	 * @return
	 */
	@RequestMapping(value = "/phoneNumberList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> phoneNumberList(Long operatorId,int count,String key) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<PhoneNumber> phoneNumbers = phoneNumberService.findByOperator(operatorService.find(operatorId),count,key);
			
			if(phoneNumbers.size() < 1){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "号码售罄");
			}else{
				//存放套餐
				List<Object> list=new ArrayList<Object>();
				for (PhoneNumber object : phoneNumbers) {
					map=new HashMap<String, Object>();
					map.put("id", object.getId());
					map.put("telFare", object.getTelFare());
					map.put("number", object.getNumber());
					map.put("price", object.getPrice());
					list.add(map);
				}
				
				result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				result.put(UnivParameter.RESULT, list);
			}
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
	
			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
	
			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		
	}
		return result;
	}

    /**
	 * 
	 * 删除购物车项 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-2 下午1:10:43
	 * @param userId 用户id
	 * @param cartItemIds 购物车项id
	 * @return
	 */
	@RequestMapping(value = "/deleteCartItem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String , Object> deleteCartItem( String userId , Long[] cartItemIds ){
		Map<String , Object> result = new HashMap< String , Object >();
		try {
			if( StringUtils.isEmpty( userId ) ){
				result.put( UnivParameter.CODE, UnivParameter.DATA_ERRORCODE );
			}
			
			Map<String , Object> data = cartService.deleteCartItem( userId , cartItemIds );
			
			result.put( UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE );
			result.put( UnivParameter.RESULT , data);
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();

			// 崩溃性逻辑处理错误CODE-0
			result.put( UnivParameter.CODE, UnivParameter.DATA_ERRORCODE );

			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());

			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		
		
		return result;
		
	}
	
	/**
	 * 
	 * 根据 购物车项 ， 检测商品库存是否充足 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-2 下午5:04:09
	 * @param userId 用户id
	 * @param cartItemId 购物车项id
	 * @param quantity 数量
	 * @return
	 */
	@RequestMapping(value = "/editCartItem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String , Object> editCartItem( String userId , Long cartItemId, Integer quantity ){
		Map<String , Object> result = new HashMap< String , Object >();
		try {
			if( StringUtils.isEmpty( userId ) ){
				result.put( UnivParameter.CODE, UnivParameter.DATA_ERRORCODE );
			}
			
			if (null == quantity || quantity < 1) {
				result.put( UnivParameter.CODE, UnivParameter.DATA_ERRORCODE );
				return result;
			}
			
			Map<String , Object> data = cartService.editCartItem( userId , cartItemId ,  quantity);
			if( (boolean) data.get("success") ){
				result.put( UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE );
				
			}else{
				result.put( UnivParameter.CODE, UnivParameter.DATA_ERRORCODE );
				result.put( UnivParameter.RESULT , "库存不足");
			}
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();

			// 崩溃性逻辑处理错误CODE-0
			result.put( UnivParameter.CODE, UnivParameter.DATA_ERRORCODE );

			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());

			// LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		
		
		return result;
	}
	
	/**
	 * 判断是否存在组合商品 author: 严志森 date: 2015-11-25 下午3:40:51
	 * 
	 * @param specification
	 *            商品参数
	 * @param id
	 *            商品id
	 * @param operator
	 * 			  1 非合约  2 购机入网
	 * @return
	 */
	@RequestMapping(value = "/idgroup", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> idgroup(String specification, String id,int operator) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			Product product = productService.find(Long.parseLong(id));
			// 根据 Model 查找到 和该商品匹配的商品
			ProductModel productModel = product.getProductModel();
			// 调用数据处理层，并接受结果数据
			Map<String, Object> mapResult = appIndexService.idgroup(
					productModel.getId(), specification,operator);

			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
					code = UnivParameter.DATA_CORRECTCODE;
					// 数据处理层正确性结果判断--加载正确的数据结果集
					result.put(UnivParameter.RESULT, mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 购物车项 选中 ， 并进入订单页面 .
	 * <p>
	 * 		校验选中的商品的库存是否充足 ， 
	 * 			若充足 返回 一组未生成的订单信息 
	 * <br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-3 上午9:18:38
	 * @param userId 用户ID
	 * @param ids 购物车项ID 数组
	 * @return
	 */
	@RequestMapping(value = "payOrder" , method = RequestMethod.GET )
	@ResponseBody
	public Map<String , Object > payOrder( String userId , Long[] ids ){
		Map<String , Object > result = new HashMap<String , Object >();
		if( StringUtils.isEmpty(userId) || null==ids || ids.length < 1 ){
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			return result;
		}
		
		Map<String , Object> data = null;
		try{
			data = cartService.payOrder(userId , ids  ); 
			
		}catch( Exception e ){
			result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			return result;
		}
		//库存
		if( (boolean) data.get("success") ){
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.RESULT, data);
			return result;
		}else{
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			
			result.put(UnivParameter.RESULT, data);
			return result;
		}
	}
	
	
	/**
	 * 
	 * 选择完套餐进入订单页面 .
	 * <p>
	 * 		校验选中的商品的库存是否充足 ， 
	 * 			若充足 返回 一组未生成的订单信息 
	 * <br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-3 上午9:18:38
	 * @param userId 用户ID
	 * @param ids 购物车项ID 数组
	 * @return
	 */
	@RequestMapping(value = "packageOrder" , method = RequestMethod.GET )
	@ResponseBody
	public Map<String , Object > packageOrder( String userId , Long id , Long productId){
		Map<String , Object > result = new HashMap<String , Object >();
		if( StringUtils.isEmpty(userId) || null==id || null == productId ){
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			return result;
		}
		
		Map<String , Object> data = null;
		try{
			data = appIndexService.packageOrder(userId , id , productId ); 
		}catch( Exception e ){
			result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			return result;
		}
		//库存
		if( (boolean) data.get("success") ){
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.RESULT, data);
			return result;
		}else{
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			result.put(UnivParameter.RESULT, data);
			return result;
		}
	}
	
	/**
	 * 收藏.
	 * author: 严志森
	 *   date: 2015-12-4 下午3:29:31
	 * @param userId 用户id
     * @param productIds 商品id 数组
     * @param type 类型 1新增 2删除
	 * @return
	 */
	@RequestMapping(value = "/favorite", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> favorite( Long userId, Long[] productIds, int type ) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			map=appIndexService.favorite(userId,productIds,type);
			if (UnivParameter.DATA_ERRORCODE.equals(map.get(
					UnivParameter.CODE).toString())) {
				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;
				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
				map.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				code = UnivParameter.DATA_CORRECTCODE;
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT, new HashMap<>());
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
	 * 
	 * 购物车数量.
	 * author: 王凯斌
	 *   date: 2015-12-7 下午3:55:47
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/cartCount", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> cartCount(Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.cartCount(userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 自提点列表.
	 * author: 严志森
	 *   date: 2015-12-9 上午10:52:50
	 * @param type 类型 1：维修点  线上  2:自提点  3：维修点  线下
	 * @return
	 */
	@RequestMapping(value = "/organizationList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> organizationList(int type) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.organizationList(organizationService.findZiTi(type));
			
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 预订单确认.
	 * author: 王凯斌
	 *   date: 2015-12-9 上午10:46:54
	 * @param productId 商品id 
	 * @return
	 */
	@RequestMapping(value = "/preOrderCheck", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> preOrderCheck(Long productId,Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.preOrderCheck(productId,userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 预订单生成.
	 * author: 王凯斌
	 *   date: 2015-12-9 下午2:03:09
	 * @param productId 商品id
	 * @param userId 用户id
     * @param paymentMethodId 支付方式
     * @param shippingMethodId 物流方式
     * @param receiverId 收货地址
     * 
	 * @return
	 */
	@RequestMapping(value = "/preOrderCreate", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> preOrderCreate(Long productId,Long userId,Long paymentMethodId,Long shippingMethodId,Long receiverId,String invTitle,String recommended) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.preOrderCreate(productId,userId,paymentMethodId,shippingMethodId,receiverId,invTitle,recommended);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 预订单转普通订单.
	 * author: 严志森
	 *   date: 2015-12-18 上午10:46:54
	 * @param preOrderId 预订单id 
	 * @return
	 */
	@RequestMapping(value = "/preToOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> preToOrder(Long preOrderId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		try {
			mapResult = appIndexService.preToOrder(preOrderId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 抢购订单生成.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午4:24:06
	 * @param productId 抢购商品id
	 * @param userId 会员id
	 * @param grabId 抢购id
	 * @param paymentMethodId 支付id
	 * @param shippingMethodId 配送id
	 * @param receiverId 地址id
	 * @param invTitle 发票抬头
	 * @param recommended 推荐人手机号
     * @param organizationId 自提点id
     * @param date 取货日期
	 * @return
	 */
	@RequestMapping(value = "/grabOrderCreate", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> grabOrderCreate(Long productId,Long userId,Long grabId,Long paymentMethodId,Long shippingMethodId,
			Long receiverId,String invTitle,String recommended,Long organizationId,String date,Long grabSeckillLogId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			mapResult =appIndexService.createGrabOrder(productId,userId,grabId,paymentMethodId,shippingMethodId,receiverId,null,null,invTitle,null,null,recommended,organizationId,date,grabSeckillLogId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	/** 保存选号信息.
	 * author: 严志森
	 *   date: 2015-12-10 上午11:36:18
	 * @param userName 用户名
	 * @param userId 用户id
	 * @param cardCode 身份证号码
	 * @param cardFrontImg 身份证正面
	 * @param cardBackImg 身份证反面
     * @param phoneNumberId 手机号码id
     * @param contractItemId 套餐id
	 * @return
	 */
	@RequestMapping(value = "/saveMemberPhone", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveMemberPhone(Long userId,String userName,String cardCode,String cardFrontImg,String cardBackImg,Long phoneNumberId,
			Long contractItemId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			Member member=memberService.find(userId);
			if(null == member){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON,"用户错误");
				return result;
			}
			PhoneNumber phoneNumber=phoneNumberService.find(phoneNumberId);
			if(null == phoneNumber || phoneNumber.getIsSold().equals(PhoneNumber.PHONESTATE.sold)){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON,"号码错误");
				return result;
			}
			ContractItem contractItem=contractItemService.find(contractItemId);
			if(null == contractItem){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON,"套餐错误");
				return result;
			}
			ContractPhoneNumberUserInfo info=new ContractPhoneNumberUserInfo();
			info.setCardBackImg(cardBackImg);
			info.setCardFrontImg(cardFrontImg);
			info.setCardCode(cardCode);
			info.setContractItem(contractItem);
			info.setMember(member);
			info.setPhoneNumber(phoneNumber);
			info.setUserName(userName);
			Long id = contractPhoneNumberUserInfoService.save(info).getId();
			// 数据处理层正确性结果判断--加载正确的数据结果集
			map.put("id",id);
			list.add(map);
			result.put(UnivParameter.RESULT, list);
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
	 * 
	 * 注册前验证.
	 * author: 严志森
	 *   date: 2015-12-10 下午6:46:49
	 * @param userName 用户名
	 * @param phone 手机
	 * @param type 类型 1 :用户名 2：手机 
	 * @return
	 */
	@RequestMapping(value = "/yanzheng", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> yanzheng(String userName,String phone,int type) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			switch (type) {
			case 1:
				if(null != memberService.findByUsername(userName)){
					result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					result.put(UnivParameter.REASON,"用户已存在");
					return result;
				}
				break;
			case 2:
				Member member =memberService.findByPhone(phone);
				if(null != member&&member.getSimple().equals("0")){
					result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					result.put(UnivParameter.REASON,"手机号码已存在");
					return result;
				}
				break;

			default:
				break;
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
	 * 
	 * 注册.
	 * author: 严志森
	 *   date: 2015-12-10 下午6:46:49
	 * @param userName 用户名
	 * @param phone 手机
	 * @param password 密码
	 * @param keywords 关键字
	 * @param inviterPhone 推荐人号码
	 * @return
	 */
	@RequestMapping(value = "/registe", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> registe(HttpServletRequest request,String userName,String phone,String password,String keywords,String inviterPhone) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		//定义积分数
		Long point=0l;
		try {
			if(null != memberService.findByUsername(userName)){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON,"用户已存在");
				return result;
			}
			Member inviter =memberService.findByPhone(inviterPhone);
			if(inviter!=null){
				//邀请注册获得积分
				PointBehavior pointBehavior=pointBehaviorService.findByName("邀请注册");
				if(pointBehavior!=null){
					point=pointBehaviorService.addPoint(inviter, pointBehavior,PointLog.Type.registerRecommendation);
					if(point>0){
						inviter.setPoint(inviter.getPoint() + point);
						memberService.update(inviter);
						//创建一条积分记录
						PointLog pointLog = new PointLog();
						pointLog.setType(PointLog.Type.registerRecommendation);
						pointLog.setCredit(point);
						pointLog.setDebit( 0L);
						pointLog.setMemo("推荐返利");
						pointLog.setBalance(inviter.getPoint());
						pointLog.setMember(inviter);
						pointLogService.save(pointLog);
					}
				}
			}
				//注册获得积分			
			PointBehavior	PointBehaviorRegist=pointBehaviorService.findByName("注册");
			Member one =memberService.findByPhone(phone);
			if(null != one&&one.getSimple().equals("1")){
				// 将快捷注册的账号激活
				one.setUsername(userName);
				one.setPassword(password);
				one.setKeywords(keywords);
				one.setNickname(phone);
				one.setPoint(0L);
				one.setAmount(BigDecimal.ZERO);
				one.setIsEnabled(true);
				one.setIsLocked(false);
				one.setLoginFailureCount(0);
                String ip = WebUtils.getIpAddr(request);
				one.setRegisterIp( ip );
				one.setSafeKey(null);
				one.setExperience(0L);
				one.setGodMoney(BigDecimal.ZERO);
				one.setMemberRank(memberRankService.findDefault());
				one.setSimple("0");
				one.setGender(null);
				memberService.update(one);
				if(PointBehaviorRegist!=null){
					point=pointBehaviorService.addPoint(one, PointBehaviorRegist,PointLog.Type.register);
					if(point>0){
						one.setPoint(one.getPoint() + point);
						memberService.update(one);
						//创建一条积分记录
						PointLog pointLog = new PointLog();
						pointLog.setType( PointLog.Type.register);
						pointLog.setCredit(point);
						pointLog.setDebit( 0L);
						pointLog.setBalance(one.getPoint());
						pointLog.setMember(one);
						pointLogService.save(pointLog);
					}
				}
			}else{
				Member member=new Member();
				member.setAmount(BigDecimal.ZERO);
				member.setGodMoney(BigDecimal.ZERO);
				member.setIsEnabled(true);
				member.setIsLocked(false);
				member.setNickname(phone);
				member.setPoint(0L);
				member.setAmount(BigDecimal.ZERO);
				member.setIsEnabled(true);
				member.setIsLocked(false);
				member.setLoginFailureCount(0);
                String ip = WebUtils.getIpAddr(request);
				member.setRegisterIp( ip );
				member.setSafeKey(null);
				member.setExperience(0L);
				member.setGodMoney(BigDecimal.ZERO);
				member.setMemberRank(memberRankService.findDefault());
				member.setSimple("0");
				member.setGender(null);
				member.setPhone(phone);
				member.setMemberRank(memberRankService.findDefault());
				member.setUsername(userName);
				member.setPassword(password);
				member.setKeywords(keywords);
				memberService.save(member);
				if(PointBehaviorRegist!=null){
					point=pointBehaviorService.addPoint(member, PointBehaviorRegist,PointLog.Type.register);
					if(point>0){
						member.setPoint(member.getPoint() + point);
						memberService.update(member);
						//创建一条积分记录
						PointLog pointLog = new PointLog();
						pointLog.setType( PointLog.Type.register);
						pointLog.setCredit(point);
						pointLog.setDebit( 0L);
						pointLog.setBalance(member.getPoint());
						pointLog.setMember(member);
						pointLogService.save(pointLog);
					}
				}
			}
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT,UnivParameter.SUCCESS_MESSAGE);
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			e.printStackTrace();
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
	 * 发送手机短信
	 * author: 南金豆
	 *   date: 2015-12-11 上午10:17:47
	 * @param phoneNumber 手机号 
	 * @param type 1 用户名  2手机号
	 * @return
	 * update author: 严志森
	 *   date: 2016-4-12 下午14:47:47
	 *   增加支持用户名发送手机号码
	 * 
	 */
	@RequestMapping(value = "/sendPhoneCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> sendPhoneCode(String phoneNumber,int type){
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			switch (type) {
			case 1:
				Member member = memberService.findByUsername(phoneNumber);
				if( member == null){
					result.put(UnivParameter.REASON, "用户名不存在");
					result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					return result;
				}
				phoneNumber = member.getPhone();
				break;
			default:
				break;
			}
			Map<String,Object> mapResult = appIndexService.sendPhoneCode(phoneNumber);
			//数据处理层崩溃性结果判断--1
			if(UnivParameter.DATA_ERRORCODE.equals(mapResult.get(UnivParameter.CODE).toString())){
				
				//崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;
				
				//加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else{
				//数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT, mapResult.get(UnivParameter.DATA));
			}
		} catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	} 
	
	
	/**
	 * 
	 * 用户登录
	 * author: 南金豆
	 *   date: 2015-12-11 下午5:52:28
	 * @param userName	用户名
	 * @param password	密码
	 * @return
	 */
	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> memberLogin(String userName,String password){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			// 登陆
			Member member  = memberService.findByUsername(userName);
			if(null == member ){
				result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
				result.put(UnivParameter.REASON,"用户不存在");
				return result;
			}
			if (!member.getIsEnabled()) {
				result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
				result.put(UnivParameter.REASON,"用户未启用");
				return result;
			}
			Setting setting = SystemUtils.getSetting();
			if (member.getIsLocked()) {
				if (ArrayUtils.contains(setting.getAccountLockTypes(), Setting.AccountLockType.member)) {
					int loginFailureLockTime = setting.getAccountLockTime();
					if (loginFailureLockTime == 0) {
						result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
						result.put(UnivParameter.REASON,"用户被锁定");
						return result;
					}
					Date lockedDate = member.getLockedDate();
					Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					if (new Date().after(unlockDate)) {
						member.setLoginFailureCount(0);
						member.setIsLocked(false);
						member.setLockedDate(null);
						memberService.update(member);
					} else {
						result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
						result.put(UnivParameter.REASON,"用户锁定中");
						return result;
					}
				} else {
					member.setLoginFailureCount(0);
					member.setIsLocked(false);
					member.setLockedDate(null);
					memberService.update(member);
				}
			}
			
			if (!password.equals(member.getPassword())) {
				int loginFailureCount = member.getLoginFailureCount() + 1;
				if (loginFailureCount >= setting.getAccountLockCount()) {
					member.setIsLocked(true);
					member.setLockedDate(new Date());
					member.setLoginFailureCount(loginFailureCount);
					memberService.update(member);
					result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
					result.put(UnivParameter.REASON,"密码错误，用户被锁定");
					return result;
				}
				member.setLoginFailureCount(loginFailureCount);
				memberService.update(member);
				result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
				result.put(UnivParameter.REASON,"密码错误，超过"+setting.getAccountLockCount()+"次将被锁定");
				return result;
			}else{
				//随机生成token
				String token = RandomValueUtils.token();
				//保存token到秘钥
				SafeKey safeKey = new SafeKey();
				safeKey.setValue(token);
				member.setSafeKey(safeKey);
				member.setLoginFailureCount( 0 );
				memberService.update(member);
				//获得登录积分
				PointBehavior pointBehavior=pointBehaviorService.findByName("登录");
				if(pointBehavior!=null){
					Long point=pointBehaviorService.addPoint(member, pointBehavior,PointLog.Type.login);
					if(point!=0){
						memberService.addPoint(member, point, PointLog.Type.login, null, null);
					}
				}
				//永远为空
//				for (Map.Entry<String, Member> e : tokenMap.entrySet()) {
//		            if (member.getId().equals(e.getValue().getId())) {
//		            	oldKey =e.getKey();
//		            }
//		        }
//				if (oldKey != null){
//                    tokenMap.remove(oldKey);
//                }
                
//                 保存登陆会话
//                tokenMap.put(token, member);
                mapResult.put("token", token);
                mapResult.put("userId", member.getId());
                mapResult.put("phone", member.getPhone());
                result.put(UnivParameter.RESULT, mapResult);
	        } 
		}catch (Exception e) {
			e.printStackTrace();
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	} 
	
	
	
	/**
	 * 用户退出
	 * author: 南金豆
	 *   date: 2015-12-11 下午5:58:11
	 * @param userName	用户名
	 * @param token 令牌
	 * @return
	 */
	@RequestMapping(value = "/memberRemove", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> memberRemove(String userName,String token){
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			// 登陆
			Member member  = memberService.findByUsername(userName);
			if(null == member ){
				result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
				result.put(UnivParameter.REASON,"用户不存在");
				return result;
			}
			//清除token
			member.setSafeKey(null);
			memberService.update(member);
//			for (Map.Entry<String, Member> e : tokenMap.entrySet()) {
//		            if (member.getId().equals(e.getValue().getId())) {
//		            	oldKey =e.getKey();
//		            }
//		      }
//				if (oldKey.equals(token)) {
//	                    tokenMap.remove(oldKey);
	       result.put(UnivParameter.RESULT, "退出结束");
//	             }else{
//	            	 result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
//	 				result.put(UnivParameter.REASON,"用户和Token不匹配");
//	 				return result;
//	             }
	     }catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	/**
	 * 
	 * 预订单详情.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午4:27:12
	 * @param orderSn 订单编号
	 * @return
	 */
	@RequestMapping(value = "/preOrderDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> preOrdersDetail(String orderSn) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.preOrderDetail(orderSn);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 神币余额.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午4:27:12
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/godMoneyAvail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> godMoneyAvail(Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.godMoneyAvail(userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 神币记录.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午4:27:12
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/godMoneyLog", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> godMoneyLog(Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.godMoneyLog(userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
     *
     * 积分记录.
     * author: 王凯斌
     *   date: 2015-12-10 下午4:27:12
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/pointLog", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pointLog(Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> mapResult = null;
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.pointLog(userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 根据数量修改购物车.
	 * author: 严志森
	 *   date: 2015-12-11 下午4:47:12
	 * @param id 购物车项id
     * @param quantity 数量
	 * @return
	 */
	@RequestMapping(value = "/editCart", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pointLog(Long id, Integer quantity) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (null == quantity || quantity < 1) {
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "数量错误");
				return result;
			}
			CartItem cartItem = cartItemService.find(id);
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "大于购物车最大数量");
				return result;
			}
			
			Product product = cartItem.getProduct();
			if (quantity > product.getAvailableStock()) {
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "大于库存");
				return result;
			}
			
			cartItem.setQuantity(quantity);
			cartItem.setIsSelect(true);
			cartItemService.update(cartItem);
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			
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
		return result;
	}
	
	/**
	 * 
	 * 修改密码前验证.
	 * author: 严志森
	 *   date: 2015-12-10 下午6:46:49
	 * @param userName 用户名
	 * @param phone 手机
	 * @param type 类型 1 :用户名 2：手机 
	 * @return
	 */
	@RequestMapping(value = "/yanzhengs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> yanzhengs(String userName,String phone,int type) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			switch (type) {
			case 1:
				if(null == memberService.findByUsername(userName)){
					result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					result.put(UnivParameter.REASON,"用户不存在");
					return result;
				}
				break;
			case 2:
				Member member =memberService.findByPhone(phone);
				if(null == member){
					result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					result.put(UnivParameter.REASON,"手机号码不存在");
					return result;
				}
				break;
			default:
				break;
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
	 *密码修改
	 * author: 南金豆
	 *   date: 2015-12-13 下午3:58:11
	 * @param userName	用户名
	 * @param newPassword 新密码
	 * @return
	 */
	@RequestMapping(value = "/setUserPassword", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setUserPassword(String userName,String newPassword){
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			// 登陆
			Member member  = memberService.findByUsername(userName);
			if(null == member ){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON,"用户不存在");
				return result;
			}else{
				member.setPassword(DigestUtils.md5Hex(newPassword));
				member.setSafeKey(null);
				memberService.update(member);
				result.put(UnivParameter.RESULT, "密码修改成功");
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	} 
   
	/**
	 *找回密码 验证用户名和手机号是否匹配
	 * author: 严志森
	 *   date: 2016-2-16 上午11:18:11
	 * @param userName	用户名
	 * @param phone 新密码
	 * @return
	 */
	@RequestMapping(value = "/userNamePhone", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> userNamePhone(String userName,String phone){
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			Boolean member  = memberService.userNamePhone(userName,phone);
			if(!member){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON,"手机号与用户名不匹配");
				return result;
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	
	/**
	 * 
	 *猜你喜欢列表
	 * author: 南金豆
	 *   date: 2015-12-13 下午1:31:52
	 * @return
	 */
	@RequestMapping(value = "/getKeyWordsList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getKeyWordsList(){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.getKeyWordsList();
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	/**
	 * 
	 * 生成购物车订单.
	 * author: 严志森
	 *   date: 2015-12-15 下午4:24:24
	 * @param userId 用户id
	 * @param receiverId 收货id
	 * @param paymentMethodId 支付方式
	 * @param shippingMethodId 配送方式
	 * @param couponCodes 优惠券
	 * @param invoiceTitle 发票抬头
	 * @param godMoneyNum 神币
	 * @param recommended 推荐人手机
     * @param date 取货时间
     * @param organizationId 自提点id
	 * @return
	 */
	@RequestMapping(value = "/createCartOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> createCartOrder(Long userId, Long receiverId,String cartToken,
			Long paymentMethodId, Long shippingMethodId, Long[] couponCodes,String date,
			String invoiceTitle, BigDecimal godMoneyNum, String recommended,Long organizationId){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE; 
		
		try {
			mapResult = appIndexService.createCartOrder(userId,  receiverId,cartToken,
					 paymentMethodId,  shippingMethodId,  couponCodes,
					 invoiceTitle,  godMoneyNum,  recommended,organizationId,date);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else if(UnivParameter.CART_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())){
				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.CART_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
		}else {
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT,
					mapResult.get(UnivParameter.DATA));
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	/**
	 * 
	 * 会员抢购列表.
	 * author: 严志森
	 *   date: 2015-12-16 下午2:39:02
	 * @param userId 会员id
	 * @param pageLoadType
	 *            分页方向【0：刷新，1：加载更多】
	 * @param pageRowsCount
	 *            每页加载数据条数
	 * @param createDate
	 *            起始订单日期(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	@RequestMapping(value = "/grabSkillList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> grabSkillList(Long userId,int pageLoadType, int pageRowsCount, String createDate){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.grabSkillList(userId,pageLoadType,pageRowsCount,createDate);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	/**
	 * 
	 * 点赞.
	 * author: 严志森
	 *   date: 2015-12-25 上午10:06:23
	 * @param type 类型 1门店 2店员
	 * @param id 门店或者店员id
	 * @return
	 */
	@RequestMapping(value = "/praise", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> praise(int type , Long id){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.praise(type,id);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						new HashMap<>());
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	/**
	 * 
	 * 评论.
	 * author: 严志森
	 *   date: 2015-12-25 上午10:06:23
	 * @param type 类型 1门店 2店员
	 * @param id 门店或者店员id
     * @param userId 用户id
     * @param content 内容
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> comment(HttpServletRequest request,Long id ,Long userId, String content, int type){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult = appIndexService.comment(request,type,id,userId,content);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						new HashMap<>());
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}

    /**
     *  生成 Ping++ Charge 对象，以供移动端支付
     *
     * @param request
     * @param orderSn
     * @param memberID
     * @param channel channel 支付宝客户端支付 alipay 微信支付 wx 银联
     * @param type type recharge 神币充值 payment支付
     * @param amount 神币重置的金额
     * @return
     *
     * @author shi.changcheng
     */
    @RequestMapping("getCharge")
    @ResponseBody
    public Charge getCharge(HttpServletRequest request ,String orderSn , Long memberID ,String  channel , String type , BigDecimal amount){
        Pingpp.apiKey = apiKey;

        Charge charge = null;
        //通用 charge 参数
        Map<String, Object> chargeMap = new HashMap<String, Object>();

        Member member = memberService.find( memberID );
        Setting setting = SystemUtils.getSetting();
        PaymentLog paymentLog = null ;
        switch (type) {
            case "recharge": {
                if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || member == null || amount.precision() > 15 || amount.scale() > setting.getPriceScale()) {
                    return null;
                }
                paymentLog = new PaymentLog();
                paymentLog.setSn(null);
                paymentLog.setType(PaymentLog.Type.recharge);
                paymentLog.setStatus(PaymentLog.Status.wait);
                paymentLog.setFee(BigDecimal.ZERO);
                paymentLog.setAmount(amount);
                paymentLog.setMember(member);
                paymentLog.setOrder(null);

                chargeMap.put("amount" , amount.multiply(new BigDecimal(100) ).intValue() );//单位为分
                break;
            }
            case "payment": {
//              Order order = orderService.checkOrderBeforePay(orderSn);
//            	if( amount != null ){
//            		appIndexService.godMoney(memberID, amount, orderSn);
//            	}
            	Order order = orderService.findBySn(orderSn);
                if (order == null || !member.equals(order.getMember())) {
                    return null;
                }

                paymentLog = new PaymentLog();
                paymentLog.setSn(null);
                paymentLog.setType(PaymentLog.Type.payment);
                paymentLog.setStatus(PaymentLog.Status.wait);
                paymentLog.setFee(BigDecimal.ZERO);
                paymentLog.setAmount( order.getAmountPayable() );
                paymentLog.setOrder(order);
                paymentLog.setMember(member);
                chargeMap.put("amount" , order.getAmountPayable().multiply(new BigDecimal(100)).intValue() );//单位为分
//                chargeMap.put("amount" , 1 );//单位为分
                break;
            }
            
            case "repair":{
                /*维修单支付*/
                ReturnOrder returnOrder = returnOrderService.find( Long.parseLong( orderSn ) );
                if (returnOrder == null || returnOrder.getRepairPrice().compareTo(BigDecimal.ZERO) <= 0 ) {
                    return null;
                }
                paymentLog = new PaymentLog();
                paymentLog.setSn(null);
                paymentLog.setType(PaymentLog.Type.repair);
                paymentLog.setStatus(PaymentLog.Status.wait);
                paymentLog.setFee(BigDecimal.ZERO);
                paymentLog.setAmount(returnOrder.getRepairPrice());
                paymentLog.setMember(member);
                paymentLog.setOrder(null);
                paymentLog.setReturnOrder( returnOrder );

                chargeMap.put("amount", returnOrder.getRepairPrice().multiply(new BigDecimal(100)).intValue());//单位为分
                break;
            }
        }

        String ip = WebUtils.getIpAddr(request);

        chargeMap.put("client_ip", ip );
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeMap.put("app", app);
        chargeMap.put("currency", "cny");
        String description = "订单支付";
        chargeMap.put("subject", org.apache.commons.lang.StringUtils.abbreviate(description.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", ""), 60));
        chargeMap.put("body", org.apache.commons.lang.StringUtils.abbreviate(description.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", ""), 600));

        chargeMap.put("channel" , channel);
        switch ( channel ){
            case "alipay_wap":{
                System.out.println("支付宝网页支付");
                chargeMap = createAliPayCharge(chargeMap);
                paymentLog.setPaymentPluginId("alipayDualPaymentPlugin");
                paymentLog.setPaymentPluginName("支付宝(网页支付)");
                break;
            }
            case "alipay":{
                System.out.println("支付宝移动支付");
                chargeMap = createAliPayCharge(chargeMap);
                paymentLog.setPaymentPluginId("alipayPaymentPlugin");
                paymentLog.setPaymentPluginName("支付宝(移动支付)");
                break;
            }
            case "wx":{
                chargeMap = createWXPayCharge(chargeMap);
                paymentLog.setPaymentPluginId("wxPaymentPlugin");
                paymentLog.setPaymentPluginName("微信支付");
                break;
            }
            case "upacp": {
                System.out.println("银联");
                chargeMap = createUpmpPayCharge(chargeMap);
                paymentLog.setPaymentPluginId("unionpayPaymentPlugin");
                paymentLog.setPaymentPluginName("银联在线支付");
                break;
            }
        }

        paymentLogService.save(paymentLog);

        chargeMap.put("order_no", paymentLog.getSn());

        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            paymentLog.setPingXXSN( charge.getId() );
            paymentLogService.update( paymentLog );
            
//            Status status = paymentLogService.findByPingxxSN(charge.getId()).getStatus();
//            //支付成功扣除神币并生成相关记录
//            if( status == Status.success ){
//            	
//            }else{
//            //支付失败或者取消则取消本次神币
//            	Order order = orderService.findBySn(orderSn);
//            	order.setAmountPaid(order.getAmountPaid().subtract(amount));
//            	orderService.update(order);
//            }
            
            System.out.println(charge);
        } catch (PingppException e) {
            e.printStackTrace();
        }

        return charge;
    }

    private Map<String , Object> createAliPayCharge( Map<String , Object> chargeMap ){
        return chargeMap;
    }

    private Map<String , Object> createWXPayCharge( Map<String , Object> chargeMap ){
        return chargeMap;
    }

    private Map<String , Object> createUpmpPayCharge( Map<String , Object> chargeMap ){
        /*Map<String , Object> extra = new HashMap<String , Object>();
        extra.put("result_url" , "http://58.240.32.170:7074/phone/testUpmpResponese.jhtml" );
        chargeMap.put("extra" , extra );*/
        return chargeMap;
    }

    @RequestMapping( value = "testUpmpResponese" , method = RequestMethod.POST)
    @ResponseBody
    public void testUpmpResponese( HttpServletRequest request , HttpServletResponse response ){
        Map<String , Object> map = request.getParameterMap();
        String buyerInfo = request.getParameter("accNo");
        String respCode = request.getParameter("respCode");
        System.out.println("shichangcheng");
    }

    @RequestMapping( value = "testUpmpResponese")
    @ResponseBody
    public void testUpmpResponeseA( HttpServletRequest request , HttpServletResponse response ){
        Map<String , Object> map = request.getParameterMap();
        String buyerInfo = request.getParameter("accNo");
        String respCode = request.getParameter("respCode");
        System.out.println("shichangcheng");
    }

    /**
     * 使用 Ping ++ 工具，完成支付后的回到函数，用户确定支付是否成功，
     *      若方法异常，则ping++ 会多次调用该接口，除非该url 失效。
     * @param request
     * @param response
     */
    @RequestMapping( value = "webhooksCharge" , method = RequestMethod.POST)
    @ResponseBody
    public void webhooksCharge( HttpServletRequest request , HttpServletResponse response ){

        Pingpp.apiKey = apiKey;
        // 获得 http body 内容
        BufferedReader reader = null;
        try {
            request.setCharacterEncoding("UTF8");
            //获取头部所有信息
            Enumeration headerNames = request.getHeaderNames();
            reader = request.getReader();
            StringBuffer buffer = new StringBuffer();
            String string;
            while ((string = reader.readLine()) != null) {
                buffer.append(string);
            }
            // 解析异步通知数据
            Event event = Webhooks.eventParse(buffer.toString());

            //获取订单号
            String eventType = event.getType();
            if( null== eventType || !("charge.succeeded".equals(eventType)) ){
                response.setStatus(500);
            }
            Map<String , Object> chargeMap = (Map<String, Object>) event.getData().get("object");
            String paymengLogSn = (String) chargeMap.get("order_no");
            boolean paid = (boolean) chargeMap.get("paid");
            String channel = (String) chargeMap.get("channel");
            //费用支付帐号信息，支付宝帐号或者微信帐号
            String buyerAcount = null ;
            //支付的银行类型 TODO 现在不知道该字段需不需要
            String bank_type = null;
            switch ( channel ){
                case "alipay_wap":{
                    System.out.println("支付宝网页支付");
                    buyerAcount = (String) ((Map<String , Object>)chargeMap.get("extra")).get("buyer_account");
                    System.out.println( buyerAcount );
                    break;
                }
                case "alipay":{
                    System.out.println("支付宝客户端支付");
                    buyerAcount = (String) ((Map<String , Object>)chargeMap.get("extra")).get("buyer_account");
                    System.out.println( buyerAcount );
                    break;
                }
                case "wx":{
                    System.out.println("微信支付");
                    buyerAcount = (String) ((Map<String , Object>)chargeMap.get("extra")).get("open_id");
                    bank_type = (String) ((Map<String , Object>)chargeMap.get("extra")).get("bank_type");
                    System.out.println( buyerAcount  +"--------"+ bank_type);
                    break;
                }
                case "upacp": {
                    System.out.println("银联");
                    buyerAcount = request.getParameter("accNo");
                    String respCode = request.getParameter("respCode");
                    System.out.println( buyerAcount  +"--------"+ respCode);
                    break;
                }
            }

            if( paid ){
                //支付成功
                paymentLogService.handle(paymengLogSn ,PaymentLog.Status.success , buyerAcount );
                response.setStatus(200);
            }else{
                //支付失败
                paymentLogService.handle(paymengLogSn ,PaymentLog.Status.failure , buyerAcount );
                response.setStatus(200);
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(500);
        } finally {
            if( null != reader ){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setStatus(500);
                }
            }
        }
    }
    /**
     * 
     * 封神榜，每日之星.
     * author: 严志森
     *   date: 2015年12月29日 下午2:25:32
     * @param type 类型 1 封神榜  2 每日之星 
     * @return
     */
    @RequestMapping(value = "/fengshenbang", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> fengshenbang(int type) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	Map<String, Object> mapResult = null;
    	code = UnivParameter.DATA_CORRECTCODE;
    	try {
    		// 调用数据处理层，并接受结果数据
    		mapResult = appIndexService.fengshenbang(type);
    		// 数据处理层崩溃性结果判断--1
    		if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
    				UnivParameter.CODE).toString())) {

    			// 崩溃性数据处理错误CODE-1
    			code = UnivParameter.LOGIC_COLLAPSECODE;

    			// 加载数据处理层崩溃性MESSAGE
    			result.put(UnivParameter.REASON,
    					mapResult.get(UnivParameter.ERRORMESSAGE).toString());
    		} else {
    			// 数据处理层正确性结果判断--加载正确的数据结果集

    			result.put(UnivParameter.RESULT,
    					mapResult.get(UnivParameter.DATA));
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
     *
     * 门店，店员评论列表. author: 严志森 date: 2016-01-03 下午15:26:46
     *
     * @param type
     *           类型 1门店 2店员
     * @param pageLoadType
     *            分页方向【0：刷新，1：加载更多】
     * @param pageRowsCount
     *            每页加载数据条数
     * @param id
     *            门店或者店员id
     * @param time
     *            评论时间
     * @return
     */
    @RequestMapping(value = "/reviewList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> reviewList(int type, int pageLoadType,int pageRowsCount, String id,String time) {
        Map<String, Object> result = new HashMap<String, Object>();
        code = UnivParameter.DATA_CORRECTCODE;
        try {
            // 调用数据处理层，并接受结果数据
            Map<String, Object> mapResult = appIndexService.reviewList(
                    type, pageLoadType, pageRowsCount, id,time);
            // 数据处理层崩溃性结果判断--1
            if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
                    UnivParameter.CODE).toString())) {

                // 崩溃性数据处理错误CODE-1
                code = UnivParameter.LOGIC_COLLAPSECODE;

                // 加载数据处理层崩溃性MESSAGE
                result.put(UnivParameter.REASON,
                        mapResult.get(UnivParameter.ERRORMESSAGE).toString());
            } else {
                // 数据处理层正确性结果判断--加载正确的数据结果集
                result.put(UnivParameter.RESULT,
                        mapResult.get(UnivParameter.DATA));
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
     * getShopComment 
     * @param commentType 类型 1门店 2店员
     * @param pageLoadType 分页方向【0：刷新，1：加载更多】
     * @param pageRowsCount 每页加载数据条数
     * @param commentId 评论编号
     * @param shopId 门店或店员编号
     * @return
     */
   @RequestMapping(value = "/getShopComment", method = RequestMethod.GET)
   @ResponseBody
   public Map<String, Object> getShopComment(int commentType, int pageLoadType,int pageRowsCount, String shopId,String commentId) {
       Map<String, Object> result = new HashMap<String, Object>();
       code = UnivParameter.DATA_CORRECTCODE;
       try {
           // 调用数据处理层，并接受结果数据
           Map<String, Object> mapResult = appIndexService.getShopComment(
        		   commentType, pageLoadType, pageRowsCount,shopId,commentId);
           // 数据处理层崩溃性结果判断--1
           if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
                   UnivParameter.CODE).toString())) {

               // 崩溃性数据处理错误CODE-1
               code = UnivParameter.LOGIC_COLLAPSECODE;

               // 加载数据处理层崩溃性MESSAGE
               result.put(UnivParameter.REASON,
                       mapResult.get(UnivParameter.ERRORMESSAGE).toString());
           } else {
               // 数据处理层正确性结果判断--加载正确的数据结果集
               result.put(UnivParameter.RESULT,
                       mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 合约机商品 订单提交.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 严志森 date: 2016-1-12 上午10:09:38
	 * 
	 * @param productId
	 *            商品ID
	 * @param quantity
	 *            商品购买数量
	 * @param receiverId
	 *            收货地址ID
	 * @param paymentMethodId
	 *            支付方式 【0 在线支付，1 线下支付】
	 * @param shippingMethodId
	 *            物流配送方式 【0 物流 ，1 自提】
	 * @param couponCodes
	 *            优惠码
	 * @param invoiceTitle
	 *            发票抬头
	 * @param godMoneyNum
	 *            神币使用数量
	 * @param contractInfoId
	 *            合约机购买时 ， 用户的信息
	 * @param memo
	 *            备注
	 * @param recommended 
	 * 			     推荐人
	 * 	@param organizationId
	 * 	     	     自提门店
	 * @param collectTime
	 *       	     自提时间
	 *
	 * @return
	 */
	@RequestMapping(value = "/packageOrderCheck", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> packageOrderCheck(Long productId,Long userId,
			Long receiverId, Long paymentMethodId, Long shippingMethodId,
			Long[] couponCodes, String invoiceTitle, BigDecimal godMoneyNum,
			Long contractInfoId, String memo , String recommended , Long organizationId , String collectTime) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.packageOrderCheck(userId,productId,receiverId,paymentMethodId,shippingMethodId,couponCodes,invoiceTitle,godMoneyNum,contractInfoId,memo,recommended,organizationId,collectTime);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 选择号码.
	 * author: 严志森 date: 2016-1-13 上午10:09:38
	 * @param phoneId 号码id
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/selectedPhone", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectedPhone(Long phoneId,Long userId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.selectedPhone(phoneId,userId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 退换货维修.
	 * author: 严志森
	 *   date: 2015-12-11 下午2:24:38
	 * @param itemIds 订单项id
	 * @param typenum 类型编号 1退货 2换货 3维修
	 * @param orderId 订单id
	 * @param organizationId 门店id
	 * @param shippingType 1 物流 2自提
	 * @param time 时间
	 * @param tel 电话
	 * @param shouhuoren 收货人
	 * @param address 联系地址
	 * @return
	 */
	@RequestMapping(value = "/returnOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> returnOrder(Long[] itemIds,String typenum,Long orderId,Long organizationId,String time,@RequestParam(value="")String tel,
			@RequestParam(value="")String shouhuoren,@RequestParam(value="")String address,String[] imgs,String memo,int shippingType,String describe) {
		// 定义结果数据结构
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		
		try {
			Order order = orderService.find(orderId);
			if( null == itemIds || itemIds.length == 0 ){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "请选择售后商品");
				return result;
			}
			if( order == null ){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "订单不存在");
				return result;
			}
			mapResult = appIndexService.returnOrder(itemIds,typenum,order,organizationId,time,tel,shouhuoren,address,imgs,memo,shippingType,describe);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 售后进度列表.
	 * author: 严志森 date: 2016-1-13 上午11:09:38
	 * @param memberId 用户id
	 * @return
	 */
	@RequestMapping(value = "/aftersales", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> aftersales(Long memberId,int pageLoadType, int pageRowsCount, String createDate) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.aftersales(memberId,pageLoadType,pageRowsCount,createDate);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 填写快递.
	 * @param deliveryCorp 物流公司
	 * @param trackingNo 快递号
	 * @param returnOrderId 退单id
	 * @return
	 */
	@RequestMapping(value = "/writeKuaidi", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> writeKuaidi(String deliveryCorp,String trackingNo,Long returnOrderId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			ReturnOrder returnOrder = returnOrderService.find(returnOrderId);
			if(returnOrder == null){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "售后订单不能为空");
				return result;
			}
			returnOrder.setStoreDeliveryCorp(deliveryCorp);
			returnOrder.setStoreTrackingNo(trackingNo);
			returnOrder.setStatus(ReturnOrder.Status.pendingCheck);
			returnOrderService.update(returnOrder);
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT,new HashMap<String, Object>());
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
	 * 进度查询.
	 * author: 严志森 date: 2016-1-13 上午11:39:38
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/returnOrderDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> returnOrderDetail(Long returnId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		Map<String, Object> mapResult = null;
		try {
			// 调用数据处理层，并接受结果数据
			mapResult=appIndexService.returnOrderDetail(returnId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
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
	 * 
	 * 待收货和待自提的退换.
	 * author: 严志森
	 *   date: 2016-2-29 下午2:24:38
	 * @param orderId 订单id
	 * @return
	 */
	@RequestMapping(value = "/returnOrders", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> returnOrders(Long orderId) {
		// 定义结果数据结构
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		
		try {
			if( orderId == null ){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.REASON, "订单不存在");
				return result;
			}
			mapResult = appIndexService.returnOrders(orderId);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				// 数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT,
						mapResult.get(UnivParameter.DATA));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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
	 *  物流公司列表
	 *  author: 严志森
	 *   date: 2016-3-12 15:00:00
	 * @return
	 */
	@RequestMapping(value = "/getDeliveryCorpList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDeliveryCorpList(){
		//定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String,Object> mapResult = new HashMap<String, Object>();
		
		code=UnivParameter.DATA_CORRECTCODE;
		try {
			mapResult = appIndexService.getDeliveryCorpList();
			//数据处理层崩溃性结果判断--1
			if(UnivParameter.LOGIC_COLLAPSECODE.equals(mapResult.get(UnivParameter.CODE).toString())){
				
				//崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;
				
				//加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else if(UnivParameter.DATA_ERRORCODE.equals(mapResult.get(UnivParameter.CODE).toString())){
				
				//崩溃性数据处理错误CODE-1
				code = UnivParameter.DATA_ERRORCODE;
				
				//加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else{
				//数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT, mapResult.get(UnivParameter.DATA));
			}
		} catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	/**
	 * 
	 * 售后物品确认收货.
	 * author: 严志森
	 *   date: 2016-3-24 下午5:07:13
	 * @param orderId 订单串号
	 * @return
	 */
	@RequestMapping(value = "/receiverReturnOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> receiverReturnOrder(Long orderId) {
		// 定义结果数据结构
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			ReturnOrder returnOrder = returnOrderService.find(orderId);
			if( null == returnOrder ){
				code = UnivParameter.DATA_ERRORCODE;
				// 数据处理层正确性结果判断--加载错误的数据结果集
				mapResult.put(UnivParameter.REASON, "订单不存在");
			}
			mapResult = appIndexService.receiverReturnOrder(returnOrder);
			//数据处理层崩溃性结果判断--1
			if(UnivParameter.LOGIC_COLLAPSECODE.equals(mapResult.get(UnivParameter.CODE).toString())){
				
				//崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;
				
				//加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else if(UnivParameter.DATA_ERRORCODE.equals(mapResult.get(UnivParameter.CODE).toString())){
				
				//崩溃性数据处理错误CODE-1
				code = UnivParameter.DATA_ERRORCODE;
				
				//加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else{
				//数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT, mapResult.get(UnivParameter.DATA));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 
	 * 付款前验证库存.
	 * author: 严志森
	 *   date: 2016-3-25 下午22:47:13
	 * @param orderId 订单串号
	 * @return
	 */
	@RequestMapping(value = "/validationStock", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> validationStock(String orderId) {
		// 定义结果数据结构
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code = UnivParameter.DATA_CORRECTCODE;
		try {
			Order order = orderService.findBySn(orderId);
			if( null == order ){
				code = UnivParameter.DATA_ERRORCODE;
				// 数据处理层正确性结果判断--加载错误的数据结果集
				mapResult.put(UnivParameter.REASON, "订单不存在");
			}
			mapResult = appIndexService.validationStock(orderId);
			//数据处理层崩溃性结果判断--1
			if(UnivParameter.LOGIC_COLLAPSECODE.equals(mapResult.get(UnivParameter.CODE).toString())){
				
				//崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;
				
				//加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else if(UnivParameter.DATA_ERRORCODE.equals(mapResult.get(UnivParameter.CODE).toString())){
				
				//崩溃性数据处理错误CODE-1
				code = UnivParameter.DATA_ERRORCODE;
				
				//加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else{
				//数据处理层正确性结果判断--加载正确的数据结果集
				result.put(UnivParameter.RESULT, mapResult.get(UnivParameter.DATA));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 
	 * 购物车项 选中 ， 并进入订单页面 .
	 * <p>
	 * 		校验选中的商品的库存是否充足 ， 
	 * 			若充足 返回 一组未生成的订单信息 
	 * <br>标签</p>
	 * <br>
	 * author: 严志森
	 *   date: 2016-4-18 下午15:18:38
	 * @param userId 用户ID
	 * @param id 商品id
	 * @param quantity 数量
	 * @param productBinds 绑定商品ids
	 * @return
	 */
	@RequestMapping(value = "purchaseAtOnce" , method = RequestMethod.GET )
	@ResponseBody
	public Map<String , Object > purchaseAtOnce( String userId , Long id ,int quantity,Long[] productBinds){
		Map<String , Object > result = new HashMap<String , Object >();
		if( StringUtils.isEmpty(userId) || id == null ){
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			return result;
		}
		
		Map<String , Object> data = null;
		try{
			data = appIndexService.purchaseAtOnce(userId , id ,quantity , productBinds ); 
			
		}catch( Exception e ){
			result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			return result;
		}
		//库存
		if( (boolean) data.get("success") ){
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.RESULT, data);
			return result;
		}else{
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			
			result.put(UnivParameter.RESULT, data);
			return result;
		}
	}
	
	
	
	
	/**
	 * 立即购买-生成订. 
	 * @author:严志森
	 * @date: 2016年4月15日 下午2:18:16 
	 * @param userId 用户id
	 * @param productId 商品id
	 * @param quantity 数量
	 * @param receiverId 地址
	 * @param paymentMethodId 支付方式
	 * @param shippingMethodId 配送方式
	 * @param couponCodes 优惠券
	 * @param invoiceTitle 发票
	 * @param godMoneyNum 神币
	 * @param contractInfoId 合约
	 * @param memo 备注
	 * @param recommended 推荐人
	 * @param organizationId 门店id
	 * @param collectTime 提货时间
	 * @return
	 */
	@RequestMapping(value = "/createOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> createOrder(Long userId, Long productId, Integer quantity,
            Long receiverId, Long paymentMethodId, Long shippingMethodId,
            Long[] couponCodes, String invoiceTitle, BigDecimal godMoneyNum,
            Long contractInfoId, String memo , String recommended , Long organizationId , String collectTime){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE; 
		
		try {
			mapResult = appIndexService.createOrder(userId,productId,quantity,receiverId,paymentMethodId,shippingMethodId,
													couponCodes,invoiceTitle,godMoneyNum,contractInfoId,memo,recommended,
													organizationId,collectTime);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else if(UnivParameter.CART_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())){
				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.CART_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
		}else {
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT,
					mapResult.get(UnivParameter.DATA));
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	@RequestMapping(value = "/forbiddenResult", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> forbiddenResult(){
		Map<String , Object> result = new HashMap<String,Object>();
		result.put(UnivParameter.CODE, UnivParameter.TOKEN_ERROR);
		return result;
	}
	
	
	@RequestMapping(value = "/godMoney", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> godMoney(Long userId,BigDecimal godMoney,String orderSn){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE; 
		
		try {
			mapResult = appIndexService.godMoney(userId,godMoney,orderSn);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else if(UnivParameter.CART_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())){
				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.CART_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
		}else {
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT,
					mapResult.get(UnivParameter.DATA));
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> payment(Long userId,BigDecimal godMoney,String orderSn){
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		code=UnivParameter.DATA_CORRECTCODE; 
		
		try {
			mapResult = appIndexService.payment(userId,godMoney,orderSn);
			// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.LOGIC_COLLAPSECODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			}else if(UnivParameter.CART_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())){
				// 崩溃性数据处理错误CODE-1
				code = UnivParameter.CART_ERRORCODE;

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.REASON,
						mapResult.get(UnivParameter.ERRORMESSAGE).toString());
		}else {
			// 数据处理层正确性结果判断--加载正确的数据结果集
			result.put(UnivParameter.RESULT,
					mapResult.get(UnivParameter.DATA));
			}
		}catch (Exception e) {
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			//崩溃性逻辑处理错误CODE-0
			code = UnivParameter.DATA_ERRORCODE;
			
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		result.put(UnivParameter.CODE, code);
		return result;
	}
}
