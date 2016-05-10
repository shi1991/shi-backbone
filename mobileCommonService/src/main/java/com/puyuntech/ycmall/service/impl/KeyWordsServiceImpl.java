package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.dao.KeyWordsDao;
import com.puyuntech.ycmall.entity.KeyWords;
import com.puyuntech.ycmall.service.KeyWordsService;

/**
 * 
 * Service 猜你喜欢 . 
 * Created on 2015-10-12 下午15:55:50 
 * @author 南金豆
 */
@Service("keyWordsServiceImpl")
public class KeyWordsServiceImpl extends BaseServiceImpl<KeyWords, Long> implements KeyWordsService {

	@Resource(name = "keyWordsDaoImpl")
	private KeyWordsDao keyWordsDao;

	@Override
	public Page<KeyWords> findPage() {
		return keyWordsDao.findPage();
	}
	
	
}
