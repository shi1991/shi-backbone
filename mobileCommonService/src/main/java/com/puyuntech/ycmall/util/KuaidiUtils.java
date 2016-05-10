package com.puyuntech.ycmall.util;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.puyuntech.ycmall.util.kuaidi.HttpRequest;
import com.puyuntech.ycmall.util.kuaidi.JacksonHelper;
import com.puyuntech.ycmall.util.kuaidi.TaskRequest;
import com.puyuntech.ycmall.util.kuaidi.TaskResponse;

/**
 * 
 * 快递100. 
 * Created on 2016-2-1 下午1:50:55 
 * @author 严志森
 */
public class KuaidiUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger( KuaidiUtils.class );
	
	/**
	 * 发送快递100请求，并回调.
	 * @param company 物流公司代码
	 * @param from 发货地
	 * @param to 目标地
	 * @param number 单号
	 * @return Boolean
	 * @throws Exception
	 */
    public static Boolean Express(String company,String from,String to,String number) {
        //向快递100发送订阅请求
        TaskRequest req = new TaskRequest();
        req.setCompany(company);
        req.setFrom(from);
        req.setTo(to);
        req.setNumber(number);
        req.getParameters().put("callbackurl", SystemUtils.getSetting().getKuaidi());
        req.setKey(SystemUtils.getSetting().getKuaidi100Key());

        HashMap<String, String> p = new HashMap<String, String>();
        p.put("schema", "json");
        p.put("param", JacksonHelper.toJSON(req));
        TaskResponse resp = null;
        Boolean b = new Boolean(false);
        try {
            String ret = HttpRequest.postData("http://www.kuaidi100.com/poll", p, "UTF-8");
            resp = JacksonHelper.fromJSON(ret, TaskResponse.class);
            b = resp.getResult();
            if(resp.getResult()){
                logger.debug("订阅成功");
            }else{
                logger.debug("订阅失败"+resp.getMessage());
            }
        } catch (Exception e) {
            logger.error("kuaidi erro in Exception " + e);
        }
        return b;
    }
    
}
