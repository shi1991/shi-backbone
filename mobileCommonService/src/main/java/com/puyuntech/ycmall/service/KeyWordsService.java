package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.entity.KeyWords;

/**
 * 
 * Service 猜你喜欢 . 
 * Created on 2015-10-12 下午15:55:50 
 * @author 南金豆
 */
public interface KeyWordsService extends BaseService<KeyWords, Long> {
	/**
	 * 查找猜你喜欢分页
	 * 
	 * @return猜你喜欢分页
	 */
	Page<KeyWords> findPage();
	
	

}
