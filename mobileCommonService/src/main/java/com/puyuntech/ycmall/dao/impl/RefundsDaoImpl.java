package com.puyuntech.ycmall.dao.impl;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.RefundsDao;
import com.puyuntech.ycmall.entity.Refunds;

import javax.persistence.Query;

/**
 * 
 * Dao - 退款单. 
 * Created on 2015-11-28 下午4:57:21 
 * @author 王凯斌
 */
@Repository("refundsDaoImpl")
public class RefundsDaoImpl extends BaseDaoImpl<Refunds, Long> implements RefundsDao {

    @Override
    public Refunds findByPingXXSn(String pingxxSn) {
        String jqhl = "select refunds from Refunds refunds where refunds.pingxxSn=:pingxxSn";
        try{
            return entityManager.createQuery(jqhl , Refunds.class).setParameter("pingxxSn" , pingxxSn).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }
}