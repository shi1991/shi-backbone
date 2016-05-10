
package com.puyuntech.ycmall.controller;


import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.puyuntech.ycmall.entity.FriendLink;
import com.puyuntech.ycmall.service.FriendLinkService;

/**
 * 
 * Controller - 友情链接 . 
 * Created on 2015-8-26 下午5:26:06 
 * @author 施长成
 */
@Controller("shopFriendLinkController")
@RequestMapping("/friend")
public class FriendLinkController extends BaseController {

	@Resource(name = "friendLinkServiceImpl")
	private FriendLinkService friendLinkService;

	/**
	 * 首页
	 */
	@RequestMapping(value="/shi",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> index() {
		FriendLink link = new FriendLink();
		link.setLogo("123");
		link.setName("hha");
		link.setUrl("12312312");
		link.setType(FriendLink.Type.image);
		link.setCreateDate(new Date());
		link.setModifyDate(new Date());
		link.setVersion(1l);
		link.setOrder(1);
		
		friendLinkService.saveOne(link);
		System.out.println("shichangc");
		return null;
	}

}