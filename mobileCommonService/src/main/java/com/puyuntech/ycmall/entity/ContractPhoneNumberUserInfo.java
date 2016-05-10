package com.puyuntech.ycmall.entity;

import javax.persistence.*;

/**
 * 
 * 机主 套餐 信息关联实体 . 
 * Created on 2015-10-20 下午3:15:13 
 * @author 施长成
 */
@Entity
@Table(name="t_contract_phonenumber_userinfo")
public class ContractPhoneNumberUserInfo extends BaseEntity<Long> {

	private static final long serialVersionUID = -804923550707239304L;
	/** 机主姓名 **/
	private String userName;
	
	/** 身份证号 **/
	private String cardCode;
	
	/** 证件照正面 **/
	private String cardFrontImg;
	
	/** 证件照反面 **/
	private String cardBackImg;
	
	/** 手机号 **/
	private PhoneNumber phoneNumber;
	
	/** 合约套餐 **/
	private ContractItem contractItem;
	
	/** 购买合约机的会员 **/
	private Member member;
	
	@Column(name="f_user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="f_card_code")
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	@Column(name="f_card_front_img")
	public String getCardFrontImg() {
		return cardFrontImg;
	}

	public void setCardFrontImg(String cardFrontImg) {
		this.cardFrontImg = cardFrontImg;
	}

	@Column(name="f_card_back_img")
	public String getCardBackImg() {
		return cardBackImg;
	}

	public void setCardBackImg(String cardBackImg) {
		this.cardBackImg = cardBackImg;
	}

	@OneToOne
	@JoinColumn(name="f_phone_number")
	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@OneToOne
	@JoinColumn(name="f_contractItem")
	public ContractItem getContractItem() {
		return contractItem;
	}

	public void setContractItem(ContractItem contractItem) {
		this.contractItem = contractItem;
	}

	@OneToOne
	@JoinColumn(name="f_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	} 
	
}
