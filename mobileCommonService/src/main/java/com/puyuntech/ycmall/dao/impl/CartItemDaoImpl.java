package com.puyuntech.ycmall.dao.impl;


import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.CartItemDao;
import com.puyuntech.ycmall.entity.CartItem;

/**
 * 
 * 购物车项. 
 * Created on 2015-9-13 上午11:18:53 
 * @author 严志森
 */
@Repository("cartItemDaoImpl")
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

}