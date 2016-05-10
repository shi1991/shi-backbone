package com.puyuntech.ycmall.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.entity.Sn;
import com.puyuntech.ycmall.entity.Sn.Type;


/**
 * 
 * Dao 序列号 . 
 * Created on 2015-9-13 下午2:44:57 
 * @author 施长成
 */
@Repository("snDaoImpl")
public class SnDaoImpl implements SnDao , InitializingBean {

	/** 货品编号生成器 */
	private HiloOptimizer goodsHiloOptimizer;

	/** 订单编号生成器 */
	private HiloOptimizer orderHiloOptimizer;

	/** 支付记录编号生成器 */
	private HiloOptimizer paymentLogHiloOptimizer;

	/** 收款单编号生成器 */
	private HiloOptimizer paymentHiloOptimizer;

	/** 退款单编号生成器 */
	private HiloOptimizer refundsHiloOptimizer;

	/** 发货单编号生成器 */
	private HiloOptimizer shippingHiloOptimizer;

	/** 退货单编号生成器 */
	private HiloOptimizer returnsHiloOptimizer;
	
	@PersistenceContext
	private EntityManager entityManager;
	@Value("${sn.goods.prefix}")
	private String goodsPrefix;
	@Value("${sn.goods.maxLo}")
	private int goodsMaxLo;
	@Value("${sn.order.prefix}")
	private String orderPrefix;
	@Value("${sn.order.maxLo}")
	private int orderMaxLo;
	@Value("${sn.paymentLog.prefix}")
	private String paymentLogPrefix;
	@Value("${sn.paymentLog.maxLo}")
	private int paymentLogMaxLo;
	@Value("${sn.payment.prefix}")
	private String paymentPrefix;
	@Value("${sn.payment.maxLo}")
	private int paymentMaxLo;
	@Value("${sn.refunds.prefix}")
	private String refundsPrefix;
	@Value("${sn.refunds.maxLo}")
	private int refundsMaxLo;
	@Value("${sn.shipping.prefix}")
	private String shippingPrefix;
	@Value("${sn.shipping.maxLo}")
	private int shippingMaxLo;
	@Value("${sn.returns.prefix}")
	private String returnsPrefix;
	@Value("${sn.returns.maxLo}")
	private int returnsMaxLo;
	
	/**
	 * 初始化 
	 * <p>Title: afterPropertiesSet.</P>
	 * <p>Description: </P>
	 * @throws Exception
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		goodsHiloOptimizer = new HiloOptimizer(Sn.Type.goods, goodsPrefix, goodsMaxLo);
		orderHiloOptimizer = new HiloOptimizer(Sn.Type.order, orderPrefix, orderMaxLo);
		paymentLogHiloOptimizer = new HiloOptimizer(Sn.Type.paymentLog, paymentLogPrefix, paymentLogMaxLo);
		paymentHiloOptimizer = new HiloOptimizer(Sn.Type.payment, paymentPrefix, paymentMaxLo);
		refundsHiloOptimizer = new HiloOptimizer(Sn.Type.refunds, refundsPrefix, refundsMaxLo);
		shippingHiloOptimizer = new HiloOptimizer(Sn.Type.shipping, shippingPrefix, shippingMaxLo);
		returnsHiloOptimizer = new HiloOptimizer(Sn.Type.returns, returnsPrefix, returnsMaxLo);
	}

	@Override
	public String generate(Type type) {
		switch (type) {
		case goods:
			return goodsHiloOptimizer.generate();
		case order:
			return orderHiloOptimizer.generate();
		case paymentLog:
			return paymentLogHiloOptimizer.generate();
		case payment:
			return paymentHiloOptimizer.generate();
		case refunds:
			return refundsHiloOptimizer.generate();
		case shipping:
			return shippingHiloOptimizer.generate();
		case returns:
			return returnsHiloOptimizer.generate();
		}
		return null;
	}

	/**
	 * 高低位算法生成器
	 */
	private class HiloOptimizer {

		/** 类型 */
		private Sn.Type type;

		/** 前缀 */
		private String prefix;

		/** 最大低位值 */
		private int maxLo;

		/** 低位值 */
		private int lo;

		/** 高位值 */
		private long hi;

		/** 末值 */
		private long lastValue;

		/**
		 * 构造方法
		 * 
		 * @param type
		 *            类型
		 * @param prefix
		 *            前缀
		 * @param maxLo
		 *            最大低位值
		 */
		public HiloOptimizer(Sn.Type type, String prefix, int maxLo) {
			this.type = type;
			this.prefix = prefix != null ? prefix.replace("{", "${") : "";
			this.maxLo = maxLo;
			this.lo = maxLo + 1;
		}

		/**
		 * 生成序列号
		 * 
		 * @return 序列号
		 */
		public synchronized String generate() {
			if (lo > maxLo) {
				lastValue = getLastValue(type);
				lo = lastValue == 0 ? 1 : 0;
				hi = lastValue * (maxLo + 1);
			}
			SimpleDateFormat myFmt=new SimpleDateFormat("yyyyMMdd");
			String	prefixOrder=myFmt.format(new Date());
			return prefixOrder + (hi + lo++);
		}
	}
	
	/**
	 * 获取末值
	 * 
	 * @param type
	 *            类型
	 * @return 末值
	 */
	private long getLastValue(Sn.Type type) {
		String jpql = "select sn from Sn sn where sn.type = :type";
		Sn sn = null;
		try{
			sn = entityManager.createQuery(jpql, Sn.class).setLockMode(LockModeType.PESSIMISTIC_WRITE).setParameter("type", type).getSingleResult();
		}catch (NoResultException e) {
			sn = new Sn();
			sn.setCreateDate(new Date());
			sn.setModifyDate(new Date());
			sn.setVersion(0l);
			sn.setLastValue(200l);
			sn.setType(type);
			entityManager.persist(sn);
		}
		long lastValue = sn.getLastValue();
		sn.setLastValue(lastValue + 1);
		return lastValue;
	}
	
}
