package com.puyuntech.ycmall.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.PhoneNumberDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.entity.PhoneNumber;
import com.puyuntech.ycmall.entity.PhoneNumber.PHONESTATE;
import com.puyuntech.ycmall.service.MemberService;
import com.puyuntech.ycmall.service.PhoneNumberService;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.util.WebUtils;

/**
 * 
 * ServiceImpl - 手机号码. 
 * Created on 2015-10-14 下午2:14:04 
 * @author 王凯斌
 */
@Service("phoneNumberServiceImpl")
public class PhoneNumberServiceImpl  extends BaseServiceImpl<PhoneNumber, Long> implements PhoneNumberService {

	@Resource(name="phoneNumberDaoImpl")
	private PhoneNumberDao phoneNumberDao;
	
	@Resource(name="memberServiceImpl")
	private MemberService memberService;

	@Override
	public Page<PhoneNumber> findPage(String phoneNumer,Operator operator, Pageable pageable) {
		return phoneNumberDao.findPage(phoneNumer , operator , pageable);
	}

	
	@Override
	public Map<String, Object> selectedPhone(PhoneNumber phoneNumber, Long userId) {
		Map<String, Object> mapResult = new HashMap<String,Object>();
		try {
			Member current = memberService.find(userId);
	    	
	    	RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = requestAttributes != null ? ((ServletRequestAttributes)requestAttributes).getRequest() : null;
			
			String key = WebUtils.getCookie(request, PhoneNumber.KEY_COOKIE_NAME);
			
			List<PhoneNumber> phoneNums = phoneNumberDao.findListByMemberOrKey(current , key);
			
			for(PhoneNumber phone : phoneNums){
				phone.setExpire(null);
				phone.setMember(null);
				phone.setPhoneKey(null);
				phone.setIsSold(PHONESTATE.unsold);
			}

			Date expire = DateUtils.addSeconds(new Date(), PhoneNumber.TIMEOUT);
			phoneNumber.setExpire(expire);
			phoneNumber.setIsSold(PHONESTATE.locked);
			if(current == null){
				if(StringUtils.isEmpty(key)){
					key = DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30));
				}
				phoneNumber.setPhoneKey(key);
			}else{
				phoneNumber.setMember(current);
			}
			
			phoneNumberDao.merge(phoneNumber);
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
    	
		return mapResult;
		
	}


	@Override
	public void rollback(PhoneNumber phoneNum) {
		phoneNum.setIsSold( PHONESTATE.unsold );
		phoneNum.setPhoneKey( null );
		phoneNum.setExpire( null );
		phoneNum.setMember( null );
		phoneNumberDao.merge(phoneNum);
	}


	@Override
	public PhoneNumber findByNumber(String number) {
		return phoneNumberDao.findByNumber(number);
	}


	@Override
	public List<PhoneNumber> findByOperator(Operator operator,int count,String key) {
		return phoneNumberDao.findByOperator(operator,count,key);
	}
	
	
	
	
}
