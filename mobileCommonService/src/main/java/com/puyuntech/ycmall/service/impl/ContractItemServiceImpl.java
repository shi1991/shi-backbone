package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.ContractPackageDao;
import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.ContractItem;
import com.puyuntech.ycmall.service.ContractItemService;

/**
 * 
 * ServiceImpl - 套餐内容项 . 
 * Created on 2015-10-14 下午2:14:04 
 * @author 王凯斌
 * 		update 施长成 rename
 */
@Service("contractItemServiceImpl")
public class ContractItemServiceImpl  extends BaseServiceImpl<ContractItem, Long> implements ContractItemService {
	@Resource(name="contractPackageDaoImpl")
	private ContractPackageDao contractPackageDao;
	@Override
	public List<ContractItem> findByContract(Contract contract) {
		return contractPackageDao.findByContract(contract);
	}

}
