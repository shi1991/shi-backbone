package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 赠送积分通道 
 * Created on 2015-12-4 下午2:18:28 
 * @author 南金豆
 */
@Entity
@Table(name = "t_point_reward")
public class PointReward extends BaseEntity<Long>{

	
	private static final long serialVersionUID = -845343142768635131L;
	/**申请人电话*/	
	private String phone;
	
	/**操作人*/	
	private Admin operator;
	
	/**赠送积分*/	
	private	Long  rewardPoint;
	
	/**状态  0：未处理  1：已处理  2：拒绝*/	
	private char state;
	
	/**备注*/	
	private String memo;
	
	/**申请人姓名*/	
	private String name;
	
	/**会员编号*/	
	private Member member;	
	
	/**门店编号*/	
	private  Organization 	organization;
	
	/**小票照片*/	
	private String invoiceImage;
	
	/**小票编号*/	
	private String invoiceSn;
	
	/**小票金额*/	
	private BigDecimal invoiceAmount;

	@Column(name = "f_phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_operator", referencedColumnName ="f_id")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(name = "f_reward_point" )
	public Long getRewardPoint() {
		return rewardPoint;
	}

	public void setRewardPoint(Long rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

	@Column(name = "f_state", nullable = false )
	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$")
	@Column(name = "f_name", nullable = false, updatable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_organization", referencedColumnName ="f_id")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Length(max = 200)
	@Column(name = "f_invoice_image",nullable = false)
	public String getInvoiceImage() {
		return invoiceImage;
	}

	public void setInvoiceImage(String invoiceImage) {
		this.invoiceImage = invoiceImage;
	}

	@Length(max = 200)
	@Column(name = "f_invoice_sn",nullable = true)
	public String getInvoiceSn() {
		return invoiceSn;
	}

	public void setInvoiceSn(String invoiceSn) {
		this.invoiceSn = invoiceSn;
	}

	@Column(name = "f_invoice_amount")
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
