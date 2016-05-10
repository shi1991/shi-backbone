package com.puyuntech.ycmall.dao;

import java.util.Map;

import com.puyuntech.ycmall.Pageable;

/**
 * 
 * Dao - POS首页相关信息. 
 * Created on 2015-11-3 下午7:24:48 
 * @author 南金豆
 */
public interface PosIndexDao{
	

	
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
	 * @param shopId  起始门店编号
	 * @param content 
	 * @param pageType 检索类型 1：商品名称	 2：类型	 3：门店
	 * @return
	 */
	Map<String, Object> InventorySearch(String selectVaule, int pageLoadType,
			int pageRowsCount, String shopId, String content, int pageType);


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
	 * 红包受理列表（实体类）
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
	 * 
	 *	手机号列表
	 * author: 南金豆
	 *   date: 2015-11-20 下午4:06:39
	 * @param phoneNumId 起始手机编号
	 * @param selectValue 检索内容（根据手机号中的部分进行检索如果为空的话传入""）
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
	Map<String, Object> changeContractPackage(String goodsId,
			String contractTime);



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
	Map<String, Object> getShopAdmin(String adminId, String beginTime,
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
	 *	库存变动列表
	 * author: 南金豆
	 *   date: 2015-12-3 下午1:18:35
	 * @param shopId 门店编号
	 * @return
	 */
	Map<String, Object> getInventoryChange(String shopId);

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
	 *  物流公司列表
	 *  author: 南金豆
	 *   date: 2015-12-31 下午1:43:52
	 * @return
	 */
	Map<String, Object> getDeliveryCorpList();


	Map<String, Object> getShopOrdersByCond(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId, String num,
			int type);


	Map<String, Object> orgList();


	Map<String, Object> InventorySearchQuanqu(String selectValue,
			int pageLoadType, String content, int pageRowsCount, int pageType);
}
