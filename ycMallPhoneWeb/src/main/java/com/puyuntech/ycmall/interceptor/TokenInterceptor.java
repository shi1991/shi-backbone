package com.puyuntech.ycmall.interceptor;

import java.io.IOException;

import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.service.CacheService;
import com.puyuntech.ycmall.service.MemberService;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token 拦截器 验证用户帐号是否失效，以及用户身份
 *
 * 验证规则：
 *  1.userId 不为空，不为-1 时，验证token 和 userid 是否匹配
 *      不匹配返回失败
 *  2.userid 为空或者为-1 标识为游客身份
 *
 * @author shi.changcheng
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    /**
     * 缓存Service对象
     */
    @Resource(name = "cacheServiceImpl")
    private CacheService cacheService;
    
    /**
	 * 会员Service  
	 */
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{
    	try {
    		String memberId = request.getParameter("userId");
    		//用户为空  游客
    		if(null == memberId || memberId.equals("-1") ){
    			return true;
    		}
        	Member member  = memberService.find(Long.parseLong(memberId));
          	String token = request.getParameter("token");
          	//用户为空  游客
            if(null == member){
    			return true;
            }
            //tocken不匹配  false
            if(member.getSafeKey().getValue().equals(token) && member.getIsEnabled() == true && member.getIsLocked() == false){
    			return true;
            }else{
    			response.sendRedirect(request.getContextPath() + "/APP/forbiddenResult.jhtml" );
    			return false;
            }
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/APP/forbiddenResult.jhtml" );
			return false;
		}
    }
	
	
}
