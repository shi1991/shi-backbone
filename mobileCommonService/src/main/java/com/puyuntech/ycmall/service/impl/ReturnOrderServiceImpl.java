package com.puyuntech.ycmall.service.impl;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.ReturnOrderDao;
import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.ReturnOrder;
import com.puyuntech.ycmall.entity.Sn;
import com.puyuntech.ycmall.service.ReturnOrderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * ServiceImpl - 退单.
 * Created on 2015-10-14 下午2:14:04
 *
 * @author 王凯斌
 */
@Service("returnOrderServiceImpl")
public class ReturnOrderServiceImpl extends BaseServiceImpl<ReturnOrder, Long> implements ReturnOrderService {

    @Resource(name = "snDaoImpl")
    private SnDao snDao;
    
    @Resource(name = "returnOrderDaoImpl")
	private ReturnOrderDao returnOrderDao;

    @Override
    @Transactional
    public ReturnOrder save(ReturnOrder returnOrder) {

        Assert.notNull(returnOrder);
        returnOrder.setSn(snDao.generate(Sn.Type.returns));
        return super.save(returnOrder);
    }

	@Override
	public Page<ReturnOrder> findByOrg(Organization organization,Pageable pageable) {
		return returnOrderDao.findByOrg(organization,pageable);
	}
}
