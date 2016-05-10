package com.puyuntech.ycmall.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * 配送验收 Entity . 
 * Created on 2015-8-28 下午3:01:10 
 * @author 施长成
 * 
 * TODO  配送单耗 商品编号 使用 外键【现在未做关联】
 */
@Entity
@Table(name="t_distribution_check")
public class DistributionCheck extends BaseEntity<Long> {
	private static final long serialVersionUID = 1955879033376483766L;
	
	/** 配送编号 **/
	private Distribution distribution ;
	
	/** 商品编号  **/
	private Product product;
	
	private Admin operator;
	
	private Date date;
	
	private int number;
	
	private String memo;
	
	private Boolean billState;
	
	private Boolean isDifference;
	
	private Set<StockLog> stockLogs = new HashSet<StockLog>();

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_distribution")
	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_product")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_operator")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(name="f_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name="f_number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="f_bill_state")
	public Boolean getBillState() {
		return billState;
	}

	public void setBillState(Boolean billState) {
		this.billState = billState;
	}

	@Column(name="f_is_difference")
	public Boolean getIsDifference() {
		return isDifference;
	}

	public void setIsDifference(Boolean isDifference) {
		this.isDifference = isDifference;
	}

	@ManyToMany
	@JoinTable(name="t_distribution_check_productsn",joinColumns = {@JoinColumn(name = "f_distribution_check", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product_sn", referencedColumnName = "f_id") } )
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}

//	private DistributionCheck() {
//		super();
//	}

}
