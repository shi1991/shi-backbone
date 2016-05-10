package com.puyuntech.ycmall.dao.impl;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.CartDao;
import com.puyuntech.ycmall.entity.Cart;

/**
 * 
 * 购物车. 
 * Created on 2015-9-13 上午11:14:34 
 * @author 严志森
 */
@Repository("cartDaoImpl")
public class CartDaoImpl extends BaseDaoImpl<Cart, Long> implements CartDao {

	@Override
	public boolean getIsDelivery(Long id) {
		return false;
	}

	

}