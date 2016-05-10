package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import com.puyuntech.ycmall.dao.RefundsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.puyuntech.ycmall.dao.OrderDao;
import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Refunds;
import com.puyuntech.ycmall.entity.Sn;
import com.puyuntech.ycmall.service.RefundsService;

/**
 * 
 * Service - 退款单. 
 * Created on 2015-11-28 下午4:54:19 
 * @author 王凯斌
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl extends BaseServiceImpl<Refunds, Long> implements RefundsService {

	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;

    @Resource(name = "refundsDaoImpl")
    private RefundsDao refundsDao;

	@Override
	@Transactional
	public Refunds save(Refunds refunds) {
		Assert.notNull(refunds);

		refunds.setSn(snDao.generate(Sn.Type.refunds));

		return super.save(refunds);
	}

	@Override
	@Transactional
	public Refunds save(Refunds refunds, Order order) {
		
		Assert.notNull(refunds);

		order.setRefundAmount(order.getRefundAmount().add(refunds.getAmount()));
		orderDao.merge(order);
		
		refunds.setSn(snDao.generate(Sn.Type.refunds));
		refunds.setOrder(order);
		
		return super.save(refunds);
	}

    @Override
    public Refunds findByPingXXSn(String pingxxSn) {
        return refundsDao.findByPingXXSn(pingxxSn);
    }

}