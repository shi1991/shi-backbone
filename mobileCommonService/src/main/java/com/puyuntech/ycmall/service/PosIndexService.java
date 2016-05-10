package com.puyuntech.ycmall.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.pingplusplus.exception.*;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.AdEntity;


/**
 * 
 * Service - 查询POS 首页相关信息.  
 * Created on 2015-11-3 下午7:32:37 
 * @author 南金豆
 */
public interface PosIndexService extends BaseService<AdEntity, Long> {
	
	
	/**
	 * 商品信息检索.
	 * author: 南金豆
	 *   date: 2015-11-2 下午4:43:55
	 * @param selectVaule  检索内容
	 * @param selectType  检索类型【1：商品名称（迷糊查询），2：品牌检索】
	 * @param pageLoadType  分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount  每页加载数据条数(暂定默认10条)
	 * @param goodsId  起始商品编号
	 * @param shopId  门店编号
	 * @return
	 */
	Map<String, Object> goodsSearch(String selectVaule, int selectType,
			int pageLoadType, int pageRowsCount, String goodsId, String shopId);

	


	
	/**
	 * 库存查询
	 * author: 南金豆
	 *   date: 2015-11-3 下午2:43:55
	 * @param selectVaule  检索内容
	 * @param pageLoadType  分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount  每页加载数据条数(暂定默认10条)
	 * @param shopId  起始店面编号
	 * @param content 
	 * @param pageType 检索类型 1：商品名称	 2：类型	 3：门店
	 * @return
	 */
	Map<String, Object> InventorySearch(String selectVaule, int pageLoadType,
			int pageRowsCount, String shopId, String content, int pageType);

	
	
	/**
	 * 订单删除.
	 * author: 南金豆
	 *   date: 2015-11-3 下午2:40:07
	 * @param ordersId 订单编号
	 * @return
	 */
	Map<String, Object> deleteOrders(String ordersId);


	/**
	 * 
	 * POS首页店面会员销售信息
	 * author: 南金豆
	 *   date: 2015-11-3 上午10:58:55
	 * @param adminId  会员编号
	 * @return
	 */
	Map<String, Object> getAdminInfo(String adminId);


	/**
	 * 
	 *红包受理列表（实体类）
	 * author: 南金豆
	 *   date: 2015-11-04 下午18:46:46
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount  每页加载数据条数
	 * @param packetId   起始红包编号
	 * @param shopId 门店编号
	 * @return
	 */
	Map<String, Object> getBonusAccept(int pageLoadType, int pageRowsCount,
			String packetId, String shopId);



	/**
	 * 
	 * 受理红包详情（实体类）.
	 * author: 南金豆
	 *   date: 2015-11-05 下午13:46:46
	 * @param packetId   红包编号
	 * @return
	 */
	Map<String, Object> getBonusDetails(String packetId);


	/**
	 *  红包受理（实物类）.
	 * author: 南金豆
	 *   date: 2015-11-5 下午2:31:30
	 * @param packetId   红包编号
	 * @param checkUser 审核人
	 * @param checkType 审核方式  【1：审核不通过 2：审核通过】
	 * @param refuseSeasonId  拒绝原因编号
	 * @return
	 */
	Map<String, Object> acceptBonus(String packetId, Long checkUser,
			int checkType, String refuseSeasonId);

/**
	 * 商品规格参数介绍
	 * author: 南金豆
	 *   date: 2015-11-17 下午1:54:07
	 * @param goodsId 商品编号
	 * @return
	 */
	Map<String, Object> getGoodsParameter(String goodsId);


	/**
	 *  商品基本信息
	 * author: 南金豆
	 *   date: 2015-11-19 上午9:47:36
	 * @param goodsId
	 * @return
	 */
	Map<String, Object> getGoodsInfo(String goodsId);


	
	/**
	 *	套餐信息
	 * author: 南金豆
	 *   date: 2015-11-20 下午1:29:27
	 * @param goodsId 商品编号
	 * @return
	 */
	Map<String, Object> getOperaInfo(String goodsId);


	/**
	 * 	手机号列表
	 * author: 南金豆
	 *   date: 2015-11-20 下午4:06:39
	 * @param phoneNumId 起始手机编号
	 * @param selectValue  检索内容（根据手机号中的部分进行检索如果为空的话传入""）
	 * @param goodsId 商品编号
	 * @return
	 */
	Map<String, Object> changePhoneNumber(String phoneNumId, String selectValue, String goodsId);



	/**
	 * 手机套餐选择
	 * author: 南金豆
	 *   date: 2015-11-20 下午4:54:39
	 * @param goodsId 商品编号
	 * @param contractTime 合约时间
	 * @return
	 */
	Map<String, Object> changeContractPackage(String goodsId, String contractTime);

	/**
	 * 店面店员列表
	 * author: 南金豆
	 *   date: 2015-11-24 上午10:41:03
	 * @param shopId 店面编号
	 * @param selectValue  检索内容（根据店员的姓名中的部分进行检索）
	 * @return
	 */
	Map<String, Object> getShopAdmin(String shopId, String selectValue);


	/**
	 * 
	 * 查询店员销售信息
	 * author: 南金豆
	 *   date: 2015-11-24 下午1:31:44
	 * @param adminId 员工编号
	 * @param beginTime 开始时间
	 * @param endtime 结束时间
	 * @return
	 */
	Map<String, Object> getAdminSelling(String adminId, String beginTime,
			String endtime);


	/**
	 * 店面销售排行
	 * author: 南金豆
	 *   date: 2015-11-24 上午10:00:21
	 * @param beginTime 开始时间
	 * @param endtime 结束时间
	 * @return
	 */
	Map<String, Object> shopRank(String beginTime, String endtime);


	/**
	 * 本店销售排行
	 * author: 南金豆
	 *   date: 2015-11-24 上午9:48:06
	 * @param shopId 门店编号 
	 * @param beginTime 开始时间
	 * @param endtime 结束时间
	 * @return
	 */
	Map<String, Object> myShopRank(String shopId, String beginTime,
			String endtime);


	/**
	 * 根据颜色和内存重新选择商品
	 * author: 南金豆
	 *   date: 2015-11-25 下午1:00:33
	 * @param goodsId 商品编号
	 * @param goodsColor 商品颜色
	 * @param goodsMemory 商品内存
	 * @return
	 */
	Map<String, Object> getNewGoods(String goodsId, String goodsColor,
			String goodsMemory);



	/**
	 * 发送手机短信
	 * author: 南金豆
	 *   date: 2015-11-27 上午10:17:47
	 * @param phoneNumber 手机号
	 * @param adminId 会员编号
	 * @return
	 */
	Map<String, Object> sendPhoneCode(String phoneNumber, String adminId);


	/**
	 *  订单确认下单
	 * author: 南金豆
	 *   date: 2015-12-7 下午5:13:26
	 * @param promotion 把组合商品的Id逗号区分形成String
	 * @param promotionPrice 组合商品促销总价（将选择的组合商品的促销价格加在一起）
	 * @param goodsType 商品类型【1：合约机  2：非合约机】
	 * @param contractTelfare 套餐预存费用（合约机）
	 * @param memberCardCode 客户身份证
	 * @param contractId	套餐编号
	 * @param memberName 客户姓名
	 * @param phoneNumId 手机号编号（选择的手机号）
	 * @param cardFrontImge 证件照正面
	 * @param cardBackImge 证件照反面
	 * @param phoneNumber 客户手机号
	 * @param adminId 员工编号
	 * @param goodsId 商品编号
	 * @param goodsPrice 商品市场价格
	 * @param goodsNumber 商品数量
	 * @param phoneCode 短信验证码
	 * @return
	 */
	Map<String, Object> orderConfirmation(String phoneNumber, String adminId,String goodsId, BigDecimal goodsMarketPrice, int goodsNumber,
			String phoneCode, String registerIp, String promotion, BigDecimal promotionPrice, int goodsType, BigDecimal contractTelfare,
			String memberCardCode, String contractId, String memberName, String phoneNumId, String cardFrontImge, String cardBackImge);



	/**
	 *  门店订单列表获取.
	 * author: 南金豆
	 *   date: 2015-11-28 下午1:00:08
	 * @param shopId   门店编号
	 * @param ordersType 订单状态【1：未完成，2：已完成】
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount 每页加载数据条数
	 * @param ordersId 起始订单编号
	 * @return
	 */
	Map<String, Object> getShopOrders(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId);



	/**
	 * 
	 * 自提订单列表获取
	 * author: 南金豆
	 *   date: 2015-11-28 下午6:05:39
	 * @param shopId   门店编号
	 * @param ordersType 订单状态【1：未完成，2：已完成】
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount 每页加载数据条数
	 * @param ordersId 起始订单编号
	 * @return
	 */
	Map<String, Object> getTakeOrders(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId);



	/**
	 * 
	 * 分发订单列表获取
	 * author: 南金豆
	 *   date: 2015-11-28 下午6:06:48
	 * @param shopId   门店编号
	 * @param ordersType 订单状态【1：未完成，2：已完成】
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount 每页加载数据条数
	 * @param ordersId 起始订单编号
	 * @return
	 */
	Map<String, Object> getDistributeOrders(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId);

	/**
	 * 订单详情
	 * author: 南金豆
	 *   date: 2015-11-28 下午6:34:18
	 * @param ordersId 订单编号
	 * @return
	 */
	Map<String, Object> getOrdersDetail(String ordersId);



	/**
	 *  确认商品串号
	 * author: 南金豆
	 *   date: 2015-11-30 下午3:42:24
	 * @param adminId 员工编号
	 * @param productSnList 
	 * @param orderId 
	 * @return
	 */
	Map<String, Object> confirmfProductSn(String adminId,List<Map<String,Object>> productSnList, Long orderId);


	/**
	 * 订单付款
	 * author: 南金豆
	 *   date: 2015-12-1 下午7:18:02
	 * @param orderId 订单编号
	 * @param adminId 员工编号
	 * @param payMoney 付款金额
	 * @param channel 支付渠道
     * @param ip 支付ip
     * @return
	 */
	Map<String, Object> orderPayment(String orderId, String adminId,
                                     BigDecimal payMoney, String channel, String ip);

	/**
	 *	库存变动列表
	 * author: 南金豆
	 *   date: 2015-12-3 下午1:18:35
	 * @param shopId 门店编号
	 * @param type 
	 * @param pageable 
	 * @return
	 */
	Map<String, Object> getInventoryChange(String shopId, Pageable pageable, int type);


	/**
	 * 
	 *库存变动详情
	 * author: 南金豆
	 *   date: 2015-12-3 下午1:21:34
	 * @param listId 单编号
	 * @param listType 单类别 1：门店退货单（门店到总库，门店库存减少）    2：门店调拨单（门店到门店 门店发货方）3：调拨门店单（门店到门店 门店收货方）
	 * 										 4：配送单（总店配送到门店） 5：发货单（门店库存减少）
	 * @return
	 */
	Map<String, Object> getInventoryChangeDetail(String listId, int listType);


	/**
	 *售后记录列表
	 * author: 南金豆
	 *   date: 2015-12-4 上午9:46:57
	 * @param shopId 门店编号
	 * @param pageable 
	 * @return
	 */
	Map<String, Object> getAfterSale(String shopId, Pageable pageable);



	
	/**
	 * 店员人员信息
	 * author: 南金豆
	 *   date: 2015-12-5 下午1:54:00
	 * @param shopId 门店编号
	 * @return
	 */
	Map<String, Object> getAdminList(String shopId);


	/**
	 * 店员删除
	 * author: 南金豆
	 *   date: 2015-12-5 下午1:54:00
	 * @param adminId 会员编号 
	 * @return
	 */
	Map<String, Object> deleteAdmin(String adminId);


	/**
	 * 
	 * 店员注册
	 * author: 南金豆
	 *   date: 2015-12-5 下午4:39:39
	 * @param adminName 员工名称
	 * @param adminPassWord 注册密码
	 * @param adminCardCode 员工身份证
	 * @param adminPhone 员工电话
	 * @param adminImage 头像路径
	 * @param adminJobNumber  员工工号
	 * @param shopId 门店编号
	 * @return
	 */
	Map<String, Object> adminRegist(String shop,String adminName, String adminPassWord,
			String adminCardCode, String adminPhone, String adminImage, String adminJobNumber);

	/**
	 * 数据管理
	 * author: 南金豆
	 *   date: 2015-12-5 下午5:54:00
	 * @param adminId 会员编号
	 * @return
	 */
	Map<String, Object> dataManagement(String adminId);

	/**
	 * 咨询中心列表
	 * author: 南金豆
	 *   date: 2015-12-7 上午10:20:24
	 * @param shopId 门店编号
	 * @return
	 */
	Map<String, Object> advisoryCenterList(String shopId,int type);


	/**
	 * 
	 *咨询中心详情
	 * author: 南金豆
	 *   date: 2015-12-7 上午10:45:35
	 * @param shopActivityId 门店活动编号
	 * @return
	 */
	Map<String, Object> getAdvisoryDetail(String shopActivityId);

	/**
	 * 积分赠送通道
	 * author: 南金豆
	 *   date: 2015-12-7 上午11:06:50
	 * @param applerPhone  申请人电话
	 * @param applerName 申请人姓名
	 * @param shopId 门店编号
	 * @param invoiceImage  小票照片地址
	 * @return
	 */
	Map<String, Object> pointReward(String applerPhone, String applerName,
			String shopId, String invoiceImage);

    /**
     * 分发订单邮寄商品
     * author: 南金豆
     *  date: 2015-12-7 下午2:50:54
     * @param adminId 员工编号
     * @param productSnList
     * @param trackingSn 物流单号
     * @param deliveryCorpId 快递公司编号
     * @param freight 物流运费
     * @param orderId
     * @return
     */
	Map<String, Object> sendGoods(String adminId, List<Map<String, Object>> productSnList, String trackingSn, String deliveryCorpId, BigDecimal freight, String orderId);

    /**
     * 自提订单选取
     * author: 南金豆
     *
     * @param shopId 门店编号
     * @param ordersType 订单状态【1：未完成(线上未付款配送方式自提)，2：已完成】
     * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
     * @param pageRowsCount 每页加载数据条数
     * @param ordersId 起始订单编号
     * @param num 检索内容
     * @param type 检索方式【1：手机号 2：顶订单号】
     * @return
     * update 施长成
     */
	Map<String, Object> changeTakeOrders(String shopId, int ordersType,
                                         int pageLoadType, int pageRowsCount, String ordersId, String num, int type);

	/**
	 *  红包受理拒绝列表
	 *  author: 南金豆
	 *   date: 2015-12-31 下午1:43:52
	 * @return
	 */
	Map<String, Object> packetRefuseSeason();

	/**
	 *  物流公司列表
	 *  author: 南金豆
	 *   date: 2015-12-31 下午1:43:52
	 * @return
	 */
	Map<String, Object> getDeliveryCorpList();


	Map<String, Object> selectedPhone(Long phoneId, Long userId);

    /**
     * 微信，支付宝扫码支付完成后，确认支付是否完成
     * @param paymentLogSn
     * @return
     * @throws ChannelException
     * @throws APIException
     * @throws AuthenticationException
     * @throws InvalidRequestException
     * @throws APIConnectionException
     */
    boolean finishedPayment(String paymentLogSn) throws ChannelException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException;





	Map<String, Object> getShopOrdersByCond(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId, String num,
			int type);





	Map<String, Object> orgList();





	Map<String, Object> InventorySearchQuanqu(String selectValue,
			int pageLoadType, int pageRowsCount, String content, int pageType);





	Map<String, Object> updateSendGoods(String adminId,
			List<Map<String, Object>> orderList, List<Map<String, Object>> orderList1, String trackingSn,
			String deliveryCorpId, BigDecimal freight, String orderId);
}
