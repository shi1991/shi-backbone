package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.Cart;



/**
 * 
 * 购物车. 
 * Created on 2015-9-13 上午11:13:50 
 * @author 严志森
 */
public interface CartDao extends BaseDao<Cart, Long> {

	boolean getIsDelivery(Long id);


}