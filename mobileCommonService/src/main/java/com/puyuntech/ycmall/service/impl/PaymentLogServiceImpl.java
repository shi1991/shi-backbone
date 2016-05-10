package com.puyuntech.ycmall.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import com.puyuntech.ycmall.entity.*;
import com.puyuntech.ycmall.service.MemberService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.puyuntech.ycmall.dao.PaymentLogDao;
import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.service.GodMoneyLogService;
import com.puyuntech.ycmall.service.OrderService;
import com.puyuntech.ycmall.service.PaymentLogService;
import com.puyuntech.ycmall.service.PointBehaviorService;
import com.puyuntech.ycmall.service.PointLogService;

/**
 *  Service - 支付记录* 
 *  Created on 2015-10-23 下午1:43:44 
 * @author 南金豆
 */

@Service("paymentLogServiceImpl")
public class PaymentLogServiceImpl extends BaseServiceImpl<PaymentLog, Long> implements PaymentLogService {

	@Resource(name = "paymentLogDaoImpl")
	private PaymentLogDao paymentLogDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
    @Resource(name="memberServiceImpl")
    private MemberService memberService;
    @Resource(name="godMoneyLogServiceImpl")
    private GodMoneyLogService  godMoneyLogService;
    @Resource(name="pointBehaviorServiceImpl")
    private PointBehaviorService pointBehaviorService;
    @Resource(name="pointLogServiceImpl")
    private PointLogService pointLogService;
	/**
	 * 根据编号查找支付记录
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 支付记录，若不存在则返回null
	 */
	@Transactional(readOnly = true)
	public PaymentLog findBySn(String sn) {
		return paymentLogDao.findBySn(sn);
	}
	
	/**
	 * 支付处理
	 *  @param paymentLogSn
     *            支付记录
     * @param status
     * @param buyerAcount
     */
	public void handle(String paymentLogSn, PaymentLog.Status status, String buyerAcount) {

        PaymentLog paymentLog = this.findBySn( paymentLogSn );

        //锁定方法
        if (!LockModeType.PESSIMISTIC_WRITE.equals(paymentLogDao.getLockMode(paymentLog))) {
            paymentLogDao.refresh(paymentLog, LockModeType.PESSIMISTIC_WRITE);
        }

        if( status == PaymentLog.Status.failure ){
            paymentLog.setStatus( status );
            paymentLog.setBuyerInfo( buyerAcount );
            return ;
        }
        switch (paymentLog.getType()) {
            case recharge:
                Member member = paymentLog.getMember();
                if (member != null) {
                	BigDecimal godCurrencyIn=paymentLog.getEffectiveAmount();
                	// 修改数据库中member表中神币
    				BigDecimal godMoneyHas = member.getGodMoney()
    						.add(godCurrencyIn);
    				member.setGodMoney(godMoneyHas);
    				memberService.update(member);
    				// 创建一条神币增加记录
    				GodMoneyLog godMoneyLog = new GodMoneyLog();
    				godMoneyLog.setVersion(0L);
    				BigDecimal godMoneyBalance = godMoneyHas;
    				godMoneyLog.setBalance(godMoneyBalance);
    				BigDecimal godMoneyCredit = godCurrencyIn;
    				godMoneyLog.setCredit(godMoneyCredit);
    				godMoneyLog.setDebit(BigDecimal.ZERO);
    				godMoneyLog.setType(GodMoneyLog.Type.recharge);
    				godMoneyLog.setMember(member);
    				godMoneyLogService.save(godMoneyLog);
    				//获得神币充值积分
    				PointBehavior pointBehavior=pointBehaviorService.findByName("神币充值");
    				if(pointBehavior!=null){
    					Long point=pointBehaviorService.addPoint(member, pointBehavior,PointLog.Type.godMoneyRecharge);
    					if(point!=0){
    						member.setPoint(member.getPoint() + point);
    						memberService.update(member);
    						//创建一条积分记录
    						PointLog pointLog = new PointLog();
    						pointLog.setType(PointLog.Type.godMoneyRecharge);
    						pointLog.setCredit(point);
    						pointLog.setDebit( 0L);
    						pointLog.setBalance(member.getPoint());
    						pointLog.setMember(member);
    						pointLogService.save(pointLog);
    					}
    				}
                }
                break;

            //订单支付
            case payment:
                Order order = paymentLog.getOrder();
                if (order != null) {
                    Payment payment = new Payment();
        			payment.setType(Payment.Type.payment);
                    payment.setMethod(Payment.Method.online);
                    payment.setPaymentMethod(paymentLog.getPaymentPluginName());
                    payment.setFee(paymentLog.getFee());
                    payment.setAmount(paymentLog.getAmount());
                    payment.setOrder(order);
                    orderService.payment(order, payment, null);
                     Member one =order.getMember();             
                  //获得订单支付积分
    				PointBehavior pointBehavior=pointBehaviorService.findByName("订单支付");
    				if(pointBehavior!=null){
    					Long point=pointBehaviorService.addPoint(one, pointBehavior,PointLog.Type.payment);
    					if(point!=0){
    						one.setPoint(one.getPoint() + point);
    						memberService.update(one);
    						//创建一条积分记录
    						PointLog pointLog = new PointLog();
    						pointLog.setType(  PointLog.Type.payment);
    						pointLog.setCredit(point);
    						pointLog.setDebit( 0L);
    						pointLog.setBalance(one.getPoint());
    						pointLog.setMember(one);
    						pointLogService.save(pointLog);
    					}
    				}

                }
                break;
        }
        paymentLog.setStatus( status );
        paymentLog.setBuyerInfo( buyerAcount );
    }

    /**
	 * 
	 * 保存支付记录
	 * @param paymentLog
	 * @return
	 * @see com.puyuntech.ycmall.service.impl.BaseServiceImpl#save(com.puyuntech.ycmall.entity.BaseEntity)
	 */
	@Override
	@Transactional
	public PaymentLog save(PaymentLog paymentLog) {
		//验证pagmentLog是否为空		
		Assert.notNull(paymentLog);
		paymentLog.setSn(snDao.generate(Sn.Type.paymentLog));
		return super.save(paymentLog);
	}
	
	/**
	 * 根据pingxxSN查找支付记录
	 * 
	 * @param pingxxSN
	 *            编号(忽略大小写)
	 * @return 支付记录，若不存在则返回null
	 */
	@Transactional(readOnly = true)
	public PaymentLog findByPingxxSN(String pingxxSN) {
		return paymentLogDao.findByPingxxSN(pingxxSN);
	}

}