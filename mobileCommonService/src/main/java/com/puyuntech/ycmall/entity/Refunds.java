package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 *
 * Entity - 退款单 . 
 * Created on 2015-8-28 下午6:00:46 
 * @author 施长成
 */
@Entity
@Table(name = "t_refunds")
public class Refunds extends BaseEntity<Long> {

    private static final long serialVersionUID = 279203127579721757L;

    /**
     * 方式
     */
    public enum Method {

        /** 在线支付 */
        online,

        /** 线下支付 */
        offline,

        /** 预存款支付 */
        deposit
    }

    public enum Status{
        /** 等待退款 */
        wait,

        /** 退款中 **/
        pedding ,

        /** 退款成功 */
        success,

        /** 退款失败 */
        failure

    }

    /** 编号 */
    private String sn;

    /** 方式 */
    private Refunds.Method method;

    /** 支付方式 */
    private String paymentMethod;

    /** 退款银行 */
    private String bank;

    /** 退款账号 */
    private String account;

    /** 退款金额 */
    private BigDecimal amount;

    /** 收款人 */
    private String payee;

    /** 操作员 */
    private String operator;

    /** 备注 */
    private String memo;

    /** 订单 */
    private Order order;

    /** Ping 退款生成的流水帐号 **/
    private String pingxxSn;

    /** 退款单状态 **/
    private Status status;

    /** 关联售后记录 **/
    private ReturnOrder returnOrder;

    /**
     * 获取编号
     *
     * @return 编号
     */
    @Column(name="f_sn",nullable = false, updatable = false, unique = true)
    public String getSn() {
        return sn;
    }

    /**
     * 设置编号
     *
     * @param sn
     *            编号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取方式
     *
     * @return 方式
     */
    @NotNull
    @Column(name="f_method",nullable = false, updatable = false)
    public Refunds.Method getMethod() {
        return method;
    }

    /**
     * 设置方式
     *
     * @param method
     *            方式
     */
    public void setMethod(Refunds.Method method) {
        this.method = method;
    }

    /**
     * 获取支付方式
     *
     * @return 支付方式
     */
    @Column(name="f_payment_method",updatable = false)
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * 设置支付方式
     *
     * @param paymentMethod
     *            支付方式
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * 获取退款银行
     *
     * @return 退款银行
     */
    @Length(max = 200)
    @Column(name="f_bank",updatable = false)
    public String getBank() {
        return bank;
    }

    /**
     * 设置退款银行
     *
     * @param bank
     *            退款银行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取退款账号
     *
     * @return 退款账号
     */
    @Length(max = 200)
    @Column(name="f_account",updatable = false)
    public String getAccount() {
        return account;
    }

    /**
     * 设置退款账号
     *
     * @param account
     *            退款账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取退款金额
     *
     * @return 退款金额
     */
    @NotNull
    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @Column(name="f_amount",nullable = false, updatable = false, precision = 21, scale = 6)
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置退款金额
     *
     * @param amount
     *            退款金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取收款人
     *
     * @return 收款人
     */
    @Length(max = 200)
    @Column(name="f_payee",updatable = false)
    public String getPayee() {
        return payee;
    }

    /**
     * 设置收款人
     *
     * @param payee
     *            收款人
     */
    public void setPayee(String payee) {
        this.payee = payee;
    }

    /**
     * 获取操作员
     *
     * @return 操作员
     */
    @Column(name="f_operator",nullable = false, updatable = false)
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作员
     *
     * @param operator
     *            操作员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    @Length(max = 200)
    @Column(name="f_memo",updatable = false)
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备注
     *
     * @param memo
     *            备注
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取订单
     *
     * @return 订单
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_orders", nullable = false, updatable = false)
    public Order getOrder() {
        return order;
    }

    /**
     * 设置订单
     *
     * @param order
     *            订单
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * 获取售后订单
     * @return
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_return_orders", nullable = false, updatable = false)
    public ReturnOrder getReturnOrder() {
        return returnOrder;
    }

    /**
     * 设置售后订单
     * @param returnOrder 售后订单
     */
    public void setReturnOrder(ReturnOrder returnOrder) {
        this.returnOrder = returnOrder;
    }

    /**
     * 设置支付方式
     *
     * @param paymentMethod
     *            支付方式
     */
    @Transient
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
    }

    /**
     * 设置操作员
     *
     * @param operator
     *            操作员
     */
    @Transient
    public void setOperator(Admin operator) {
        setOperator(operator != null ? operator.getWebUsername() : null);
    }

    @Column(name="f_pingxx_sn")
    public String getPingxxSn() {
        return pingxxSn;
    }

    public void setPingxxSn(String pingxxSn) {
        this.pingxxSn = pingxxSn;
    }

    /**
     * 获取退款单状态
     * @return
     */
    @Column(name="f_status")
    public Status getStatus() {
        return status;
    }

    /**
     * 设置退款单状态
     * @param status
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}
