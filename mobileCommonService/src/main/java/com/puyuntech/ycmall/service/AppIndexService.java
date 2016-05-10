package com.puyuntech.ycmall.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.puyuntech.ycmall.entity.AdEntity;
import com.puyuntech.ycmall.entity.Cart;
import com.puyuntech.ycmall.entity.CartItem;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Receiver;
import com.puyuntech.ycmall.entity.ReturnOrder;

/**
 * 
 * Service - 查询APP首页相关信息. 
 * Created on 2015-8-25 下午3:50:01 
 * @author Liaozhen
 */
public interface AppIndexService extends BaseService<AdEntity, Long> {

	/**
	 * 查询APP首页广告.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:22:32
	 * @return
	 */
	List<AdEntity> findAdvertisement();
	
	/**
	 * 根据广告位查询广告内容.
	 * 
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:23:25
	 * @param positionId
	 * @return
	 */
	List<AdEntity> findAdByPosition(Integer positionId);
	
	/**
	 * 神部落首页加载（会员头像、神币余额、积分余额）.
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-2 上午9:49:25
	 * @param userId
	 * @return
	 */
	Map<String,Object> getUserInfo(String userId);
	
	/**
	 * 商品 参数.
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-2 下午15:29:25
	 * @param goodsId
	 * @return
	 */
	Map<String,Object> getParameterText(String goodsId);
	
	/**
	 * 商品 规格.
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-2 下午15:49:25
	 * @param goodsId
	 * @return
	 */
	Map<String,Object> getSpecificationText(String goodsId);
	
	/**
	 * 商品介绍图文详情.
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-2 下午16:43:25
	 * @param goodsId
	 * @return
	 */
	Map<String,Object> getGoodsImageText(String goodsId);
	
	/**
	 * 会员资料信息获取
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-5 下午14:36:25
	 * @param userId
	 * @return
	 */
	Map<String,Object> getUserDetail(String userId);
	
	/**
	 * 会员货币信息加载.
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-5 下午15:10:25
	 * @param userId,getInfoType
	 * @return
	 */
	Map<String,Object> getCurrency(String userId);
	
	/**
	 * 用户消费历史信息加载.
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-5 下午15:44:25
	 * @param userId,getInfoType
	 * @return
	 */
	Map<String,Object> getHistory(String userId,String getInfoType);
	
	
	/**
	 * 商品信息列表检索.
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-6 下午16:32:25
	 * @param selectVaule,selectType,pageLoadType,pageRowsCount,goodsId,page
	 * @return
	 */
	Map<String,Object> getGoodsList(String selectVaule,int pageSize ,int count,Product.OrderType orderType,BigDecimal startPrice, BigDecimal endPrice);
	
	
	/**
	 * 幸运排行榜.
	 * author: 南金豆
	 *   date: 2015-11-12 下午4:35:50
	 * @param luckyNum 
	 * @return
	 */
	 Map<String,Object> getRankingList(int luckyNum);
	
	/**
	 * 
	 * 淘更多 根据标签获取酷炫神器,限量神器.
	 * author: 严志森
	 *   date: 2015-9-21 下午1:44:41
	 * @param goodsType
	 * @return
	 */
	Map<String,Object> listGoodsByTagType(int goodsType);
	
	/**
	 * 
	 * 会员收获地址新增.
	 * author: 严志森
	 *   date: 2015-9-22 上午10:21:12
	 * @param recipientAddress
	 * @param recipientName
	 * @param recipientPhoneNumber
	 * @param postalCode
	 * @param checkedState
	 * @param userId
	 * @param areaId
	 * @return
	 */
	Map<String,Object> addAddress(Receiver receiver,Long areaId,Long userId);
	/**
	 * 
	 * 会员收获地址修改.
	 * author: 南金豆
	 *   date: 2015-9-22 下午14:44:20
	 * @param recipientAddress
	 * @param recipientName
	 * @param recipientPhoneNumber
	 * @param postalCode
	 * @param checkedState
	 * @param userId
	 * @param areaId
	 * @param userId 
	 * @param id 
	 * @return
	 */
	
	Map<String,Object> updateAddress(Receiver receiver,Long areaId, Long userId);
	
	/**
	 * 
	 * 会员收获地址删除.
	 * author: 南金豆
	 *   date: 2015-9-22 下午15:58:20
	 * @param userId
	 * @return
	 */
	Map<String, Object> deleteAddress(Receiver receiver);
	
	/**
	 * 
	 * 获取收货人列表.
	 * author: 严志森
	 *   date: 2015-9-29 下午3:14:12
	 * @param userId
	 * @return
	 */
	Map<String, Object> getAddress(String userId);
	
	/**
	 * 
	 * 会员卡券信息获取.
	 * author: 严志森
	 *   date: 2015-10-14 下午5:50:29
	 * @param userId 用户id
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount 每页加载数据条数
	 * @param couponId 起始卡券编号
	 * @param couponType 卡券使用范围
	 * @param type 卡券类型
	 * @return
	 */
	Map<String, Object> getCoupon(String userId, String couponType,
			String type, String couponId, int pageLoadType,
			int pageRowsCount);
	
	/**
	 * 
	 * 优惠券使用
	 * author: 严志森
	 *   date: 2015-10-14 下午7:08:37
	 * @param userId 用户id
	 * @param couponId 卡券编号
	 * @return
	 */
	Map<String, Object> useCoupon(String userId, String couponId);
	
	/**
	 * 
	 * 购物车列表.
	 * author: 严志森
	 *   date: 2015-10-16 下午2:26:25
	 * @param userId 会员编号 
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount 每页加载数据条数
	 * @param cartItemId 起始购物车项编号
	 * @return
	 */
	Map<String, Object> getCart(String userId);

	/**
	 * 
	 *关注/收藏列表获取.
	 * author: 南金豆
	 *   date: 2015-10-27 下午6:26:09
	 * @param userId
	 * @param pageLoadType
	 * @param pageRowsCount
	 * @param goodsId
	 * @return
	 */
	Map<String, Object> getFavorite(String userId, int pageLoadType,
			int pageRowsCount, String goodsId);
	
	/**
	 * 红包发布历史列表.
	 * author: 南金豆
	 *   date: 2015-10-29 上午10:26:46
	 * @param userId  会员编号
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount  每页加载数据条数
	 * @param packetId   起始红包编号
	 * @return
	 */
	Map<String, Object> getBonusHistroy(String userId, int pageLoadType,
			int pageRowsCount, String packetId);

	/**
	 * 
	 * 商品售后保障介绍. 南金豆
	 *   date: 2015-10-29 下午5:06:41
	 * @param goodsId
	 * @return
	 */
	Map<String, Object> getGoodsAfterSale(String goodsId);
	
	
	/**
	 * 门店列表获取.
	 * author: 南金豆
	 *   date: 2015-11-11 上午10:44:45
	 * @param pageLoadType  分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount  每页加载数据条数
	 * @param shopId 起始门店编号
	 * @param userXCoordinate 会员地理坐标X坐标（必须）
	 * @param userYCoordinate 会员地理坐标y坐标（必须）
	 * @return
	 */
	Map<String, Object> getShop(int pageLoadType, int pageRowsCount, String shopId, Float userXCoordinate, Float userYCoordinate);

	/**
	 * 订单列表获取.
	 * author: 南金豆
	 *   date: 2015-10-30 上午11:15:38
	 * @param userId 用户id
	 * @param ordersType 订单状态【0：全部，1：代付款  :2：待收货（代发货和带收货），3： 待自提 :4：待评价（完成的）】
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount 每页加载数据条数
	 * @param createDate 起始订单日期
	 * @return
	 */
	Map<String, Object> getOrders(String userId, int ordersType,
			int pageLoadType, int pageRowsCount, String createDate);

	/**
	 *
	 *  门店介绍/活动信息.
	 * author: 南金豆
	 *   date: 2015-10-30 下午6:10:26
	 * @param shopId 门店编号
	 * @param shopInfoType 信息类别【1：门店介绍，2：门店活动,  3：店员介绍】
	 * @return
	 */
	Map<String, Object> getShopDetailAndActivity(int shopInfoType, String shopId);

	
	/**
	 *  红包发布申请（货币类及实物类）
	 * author: 南金豆
	 *   date: 2015-11-5 下午4:24:30
	 * @param userId   会员编号
	 * @param packetType 红包类型【1：神币红包，2：积分红包，3：实物红包】
	 * @param packetCredit  红包额度【包括神币额度，积分额度和物品数量】
	 * @param pcaketNumber 红包数量
	 * @param splitType 拆分类型【1：等分，2：随即】
	 * @param packetTitle 红包名称
	 * @param packetInfo 红包介绍
	 * @param packetGoods 红包物品
	 * @param packetCheckTime   预约审核的时间
	 * @param packetOrganization  预约审核的门店
	 * @return
	 */
	Map<String, Object> applyBonus(String userId, int packetType,
			BigDecimal packetCredit, int pcaketNumber, String splitType,
			String packetTitle, String packetInfo, String packetGoods, String packetCheckTime, String packetOrganization);

	/**
	 *  APP首页，今日抢图片及状态加载.
	 * author: 南金豆
	 *   date: 2015-11-6 下午2:33:37
	 * @return
	 */
	Map<String, Object> getTodayRobList();

	
	/**
	 * 【今日抢】判断是否可以抢购
	 * author: 南金豆
	 *   date: 2015-11-9 下午4:24:48
	 * @param grabSeckillId  抢购id
	 * @param robGoodsType  商品类型【1：商品，2：神币类红包 3：积分类红包，4：实物类红包  5：优惠券】
	 * @param startTime 
	 * @param memberId 
	 * @return
	 */
	Map<String, Object> snapJudgment(Long grabSeckillId, int robGoodsType, Long userId, String startTime);

	/**
	 * 【今日抢】点击图标进行抢购
	 * author: 南金豆
	 *   date: 2015-11-9 下午7:09:30
	 * @param robGoodsId 抢购商品编号
	 * @param grabSeckillId 抢购Id
	 * @param robGoodsType 商品类型【1：商品，2：神币类红包 3：积分类红包，4：实物类红包  5：优惠券】
	 * @param userId 会员编号
	 * @return
	 */
	Map<String, Object> shoppingRush(Long grabSeckillId, Long robGoodsId, int robGoodsType,
			Long userId);

	/**
	 * 【今日抢】抢购成功：成功界面展示接口：商品图片、简介+绑定推荐配套商品图片、名称等（绑定推荐等只限定与商品部分】
	 * author: 南金豆
	 *   date: 2015-11-11 下午4:47:07
	 * @param robGoodsId  抢购商品编号
	 * @param grabSeckillId 抢购编号
	 * @param robGoodsType 商品类型【1：商品，2：神币类红包 3：积分类红包，4：实物类红包  5：优惠券】
	 * @param userId 会员编号
	 * @return
	 */
	Map<String, Object> getRushGoodsRecommend(String grabSeckillId,String robGoodsId,
			 int robGoodsType, String userId);
	
	/**
	 * 抢购信息列表.
	 * author: 南金豆
	 *   date: 2015-11-12 下午1:19:03
	 * @param userId
	 * @return
	 */
	Map<String, Object> getShoppingRush(String userId);

	/**
	 * 门店列表ID获取.
	 * author: 南金豆
	 *   date: 2015-11-12 下午3:46:03
	 * @param userXCoordinate 会员地理坐标X坐标（必须）
	 * @param userYCoordinate 会员地理坐标y坐标（必须）
	 * @return
	 */
	Map<String, Object> getShopId(Float userXCoordinate, Float userYCoordinate);

	/**
	 * 收到的红包详情.
	 * author: 南金豆
	 *   date: 2015-11-12 下午12:52:12
	 * @param packetId 红包ID
	 * @return
	 */
	Map<String, Object> getBonusDetail(String packetId);
	
	/**
	 *  商品详情
	 * author: 南金豆
	 *   date: 2015-11-16 上午9:47:36
	 * @param goodsId
	 * @return
	 */
	Map<String, Object> getGoodsDetail(String goodsId,String productSn);

	/**
	 * 商品促销详情
	 * author: 南金豆
	 *   date: 2015-11-16 下午3:40:51
	 * @param goodsId 商品编号
	 * @param promotionType  促销类型【1：满送（满5000赠Iphone），2：买赠（买商品送Ipone），3：组合（几个商品组合在一起卖）
	 * @return
	 */
	Map<String, Object> getGoodsPromotionDetail(String goodsId,
			int promotionType);
	
	/**
	 * 判断是否存在组合商品.
	 * @author:严志森
	 * @date: 2016年4月6日 下午1:33:44 
	 * @param id 商品id
	 * @param specification 商品参数
	 * @param operator 1 非合约  2 购机入网
	 * @return
	 */
	Map<String, Object> idgroup(Long id, String specification, int operator);
	
	/**
	 * 
	 * 设置默认收货地址.
	 * author: 严志森
	 *   date: 2015-11-27 下午5:39:02
	 * @param userId 用户id
	 * @param addressId 收货地址id
	 * @return
	 */
	Map<String, Object> setDefaultAddress(Long userId, Long addressId);
	
	/**
	 * 根据商品id获取相关捆绑商品.
	 * @author:严志森
	 * @date: 2016年4月6日 下午1:36:33 
	 * @param productId 商品id
	 * @return
	 */
	Map<String,Object> getBind(Long productId);

	/**
	 * 
	 * 查看购物车中是否存在该商品 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-2 上午11:09:17
	 * @param cart
	 * @param product
	 * @return
	 */
	List<CartItem> getCartItem(Cart cart, Product product);
	
	/**
	 * 购物车列表.
	 * author: 严志森
	 *   date: 2015-10-16 下午2:26:25
	 * @param userId 会员编号 
	 * @return
	 */
	Map<String, Object> getCartList(Long userId);
	
	/**
	 * 订单详情. author: 严志森 date:2015-12-30 15:53：50
	 * @param orderSn 订单串号
	 * @return
	 */
	Map<String, Object> getOrdersDetail(String orderSn);

	/**
	 * 根据用户id和商品id查询收藏数量.
	 * @author:严志森
	 * @date: 2016年4月6日 下午1:40:48 
	 * @param userId 用户id
	 * @param productId 商品id
	 * @return
	 */
	int findFavorite(Long userId,Long productId);
	
	/**
	 * 添加收藏.
	 * @author:严志森
	 * @date: 2016年4月6日 下午1:44:37 
	 * @param userId 用户id
	 * @param productId 商品id
	 * @return
	 */
	Map<String, Object> addFavorite(Long userId, Long productId);
	
	/**
	 * 删除收藏.
	 * @author:严志森
	 * @date: 2016年4月6日 下午1:44:59 
	 * @param userId 用户id
	 * @param productIds 商品id(数组)
	 * @return
	 */
	Map<String, Object> dellFavorite(Long userId, Long[] productIds);
	
	/**
	 * 
	 * 会员预订商品列表. author: 严志森 date: 2015-8-28 上午11:32:20
	 * 
	 * @param userId 用户id
	 * @return
	 */
	Map<String, Object> getReserve(Long userId);
	
	/**
	 * 
	 * 购物车商品数量.
	 * author: 王凯斌
	 *   date: 2015-12-9 上午10:52:19
	 * @param userId 用户id
	 * @return
	 */
	Map<String, Object> cartCount(Long userId);
	
	/**
	 * 
	 * 预定商品订单确认.
	 * author: 王凯斌
	 *   date: 2015-12-9 上午10:53:25
	 * @param productId 商品id
	 * @param userId 
	 * @return
	 */
	Map<String, Object> preOrderCheck(Long productId, Long userId);
	
	/**
	 * 
	 * 预定订单生成.
	 * author: 王凯斌
	 *   date: 2015-12-9 下午2:09:14
	 * @param productId 商品id
	 * @param userId 用户id
	 * @param invTitle 
	 * @param recommended 
	 * @return
	 */
	Map<String, Object> preOrderCreate(Long productId,Long userId,Long paymentMethodId,Long shippingMethodId,Long receiverId, String invTitle, String recommended);
	
	/**
	 * 遍历门店列表.
	 * @author:严志森
	 * @date: 2016年4月6日 下午1:47:33 
	 * @param list 门店实体集合
	 * @return
	 */
	Map<String, Object> organizationList(List<Organization> list);
	
	/**
	 * 
	 * 抢购订单生成.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午4:24:06
	 * @param productId 抢购商品id
	 * @param userId 会员id
	 * @param grabId 抢购id
	 * @param paymentMethodId 支付id
	 * @param shippingMethodId 配送id
	 * @param receiverId 地址id
	 * @param invTitle 发票抬头
	 * @param recommended 推荐人手机号
     * @param organizationId 自提点id
     * @param date 取货日期
	 * @return
	 */
	Map<String, Object> createGrabOrder(Long productId,Long userId,Long grabId,Long paymentMethodId,Long shippingMethodId,Long receiverId,Long[] bindProductIds,Long bindId,String invTitle,String invCont,String memo,String recommended, Long organizationId, String date, Long grabSeckillLogId);

	/**
	 * 
	 * 预订单详情.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午4:58:15
	 * @param orderSn 订单编号
	 * @return
	 */
	Map<String, Object> preOrderDetail(String orderSn);

	/**
	 * 
	 * 可用神币.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午5:38:21
	 * @param userId 用户id
	 * @return
	 */
	Map<String, Object> godMoneyAvail(Long userId);

	/**
	 * 
	 * 神币记录.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午5:38:21
	 * @param userId 用户id
	 * @return
	 */
	Map<String, Object> godMoneyLog(Long userId);

	/**
	 * 
	 * 积分记录.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午5:38:21
	 * @param userId 用户id
	 * @return
	 */
	Map<String, Object> pointLog(Long userId);

	
	/**
	 * 发送手机短信
	 * author: 南金豆
	 *   date: 2015-12-11 上午10:17:47
	 * @param phoneNumber 手机号
	 * @return
	 */
	Map<String, Object> sendPhoneCode(String phoneNumber);

	/**
	 * 
	 *猜你喜欢列表
	 * author: 南金豆
	 *   date: 2015-12-13 下午1:31:52
	 * @return
	 */
	Map<String, Object> getKeyWordsList();
	
	/**
	 * 
	 * 会员抢购列表.
	 * author: 严志森
	 *   date: 2015-12-16 下午2:39:02
	 * @param userId 会员id
	 * @param pageLoadType
	 *            分页方向【0：刷新，1：加载更多】
	 * @param pageRowsCount
	 *            每页加载数据条数
	 * @param createDate
	 *            起始订单日期(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	Map<String, Object> grabSkillList(Long userId, int pageLoadType, int pageRowsCount, String createDate);
	
	/**
	 * 
	 * 退换货维修.
	 * author: 严志森
	 *   date: 2015-12-11 下午2:24:38
	 * @param itemIds 订单项id
	 * @param typenum 类型编号 1退货 2换货 3维修
	 * @param orderId 订单id
	 * @param organizationId 门店id
	 * @param shippingType 1 物流 2自提
	 * @param time 时间
	 * @param tel 电话
	 * @param shouhuoren 收货人
	 * @param address 联系地址
	 * @return
	 */
	Map<String, Object> returnOrder(Long[] itemIds, String typenum, Order order, Long organizationId, String time, String tel, String shouhuoren, String address, String[] imgs, String memo, int shippingType, String describe);
	
	/**
	 * 收藏.
	 * author: 严志森
	 *   date: 2015-12-4 下午3:29:31
	 * @param userId 用户id
     * @param productIds 商品id 数组
     * @param type 类型 1新增 2删除
	 * @return
	 */
	Map<String, Object> favorite(Long userId, Long[] productIds, int type);
	
	/**
	 * 
	 * 生成购物车订单.
	 * author: 严志森
	 *   date: 2015-12-15 下午4:24:24
	 * @param userId 用户id
	 * @param receiverId 收货id
	 * @param paymentMethodId 支付方式
	 * @param shippingMethodId 配送方式
	 * @param couponCodes 优惠券
	 * @param invoiceTitle 发票抬头
	 * @param godMoneyNum 神币
	 * @param recommended 推荐人手机
     * @param date 取货时间
     * @param organizationId 自提点id
	 * @return
	 */
	Map<String, Object> createCartOrder(Long userId, Long receiverId,
			String cartToken, Long paymentMethodId, Long shippingMethodId, Long[] couponCodes,
			String invoiceTitle, BigDecimal godMoneyNum, String recommended, Long organizationId, String date);
	
	/**
	 * 
	 * 点赞.
	 * author: 严志森
	 *   date: 2015-12-25 上午10:06:23
	 * @param type 类型 1门店 2店员
	 * @param id 门店或者店员id
	 * @return
	 */
	Map<String, Object> praise(int type, Long id);
	
	/**
	 * 
	 * 评论.
	 * author: 严志森
	 *   date: 2015-12-25 上午10:06:23
	 * @param type 类型 1门店 2店员
	 * @param id 门店或者店员id
     * @param userId 用户id
     * @param content 内容
	 * @return
	 */
	Map<String, Object> comment(HttpServletRequest request, int type, Long id,
			Long userId, String content);
	
	/**商品包装售后
	 * author: yanzhisen 
	 * date: 2015-12-28 下午4:40:28
	 * 
	 * @param goodsId 商品id
	 * @return
	 */
	Map<String, Object> getGoodsSaleSupport(String goodsId);
	
	/**
	 * 
	 * 封神榜.
	 * author: 严志森
	 *   date: 2015年12月29日 下午2:11:09
	 * @return
	 */
	Map<String, Object> fengshenbang(int type);
	
	/**
     *
     * 门店，店员评论列表. author: 严志森 date: 2016-01-03 下午15:26:46
     *
     * @param type
     *           类型 1门店 2店员
     * @param pageLoadType
     *            分页方向【0：刷新，1：加载更多】
     * @param pageRowsCount
     *            每页加载数据条数
     * @param id
     *            门店或者店员id
     * @param time
     *            评论时间
     * @return
     */
    Map<String, Object> reviewList(int type, int pageLoadType,int pageRowsCount, String id,String time);

    /**
	 * 积分兑换. author: 南金豆 date: 2015-10-30 下午4:44:39
	 * 
	 * @param userId
	 *            会员编号
	 * @param godCurrencyOut
	 *            神币消耗
	 * @param integralIn
	 *            积分进账
	 * @return
	 */
	Map<String, Object> setCurrency(String userId, BigDecimal godCurrencyOut,
			Long integralIn);

	
	 /**
     * getShopComment 
     * @param commentType 类型 1门店 2店员
     * @param pageLoadType 分页方向【0：刷新，1：加载更多】
     * @param pageRowsCount 每页加载数据条数
     * @param commentId 评论编号
     * @param shopId 门店或店员编号
     * @return
     */
	Map<String, Object> getShopComment(int commentType, int pageLoadType,
			int pageRowsCount, String shopId, String commentId);

	/**
	 * 
	 * 选择完套餐进入订单页面 .
	 * <p>
	 * 		校验选中的商品的库存是否充足 ， 
	 * 			若充足 返回 一组未生成的订单信息 
	 * <br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-3 上午9:18:38
	 * @param userId 用户ID
	 * @param ids 购物车项ID 数组
	 * @return
	 */
	Map<String, Object> packageOrder(String userId, Long id, Long productId);

	/**
	 * 
	 * 合约机商品 订单提交.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 严志森 date: 2016-1-12 上午10:09:38
	 * 
	 * @param productId
	 *            商品ID
	 * @param quantity
	 *            商品购买数量
	 * @param receiverId
	 *            收货地址ID
	 * @param paymentMethodId
	 *            支付方式 【0 在线支付，1 线下支付】
	 * @param shippingMethodId
	 *            物流配送方式 【0 物流 ，1 自提】
	 * @param couponCodes
	 *            优惠码
	 * @param invoiceTitle
	 *            发票抬头
	 * @param godMoneyNum
	 *            神币使用数量
	 * @param contractInfoId
	 *            合约机购买时 ， 用户的信息
	 * @param memo
	 *            备注
	 * @param recommended 
	 * 			     推荐人
     * 	@param organizationId
     * 	                         自提门店
     * @param collectTime
     *            自提时间
     *
	 * @return
	 */
	Map<String, Object> packageOrderCheck(Long userId,Long productId, Long receiverId,
			Long paymentMethodId, Long shippingMethodId, Long[] couponCodes,
			String invoiceTitle, BigDecimal godMoneyNum, Long contractInfoId,
			String memo, String recommended, Long organizationId, String collectTime);
	
	/**
	 * 选择号码.
	 * author: 严志森 date: 2016-1-13 上午10:09:38
	 * @param phoneId 号码id
	 * @param userId 用户id
	 * @return
	 */
	Map<String, Object> selectedPhone(Long phoneId, Long userId);
	
	/**
	 * 售后进度列表.
	 * author: 严志森 date: 2016-1-13 上午11:09:38
	 * @param memberId 用户id
	 * @return
	 */
	Map<String, Object> aftersales(Long memberId, int pageLoadType, int pageRowsCount, String createDate);
	
	/**
	 * 进度查询.
	 * author: 严志森 date: 2016-1-13 上午11:39:38
	 * @param memberId
	 * @return
	 */
	Map<String, Object> returnOrderDetail(Long returnId);
	
	/**
	 * 
	 * 待收货和待自提的退换.
	 * author: 严志森
	 *   date: 2016-2-29 下午2:24:38
	 * @param orderId 订单id
	 * @return
	 */
	Map<String, Object> returnOrders(Long orderId);
	
	/**
	 *  物流公司列表
	 *  author: 严志森
	 *   date: 2016-3-12 15:00:00
	 * @return
	 */
	Map<String, Object> getDeliveryCorpList();
	
	/**
	 * 
	 * 预订单转普通订单.
	 * author: 严志森
	 *   date: 2015-12-18 上午10:46:54
	 * @param preOrderId 预订单id 
	 * @return
	 */
	Map<String, Object> preToOrder(Long preOrderId);
	
	/**
	 * 
	 * 售后物品确认收货.
	 * author: 严志森
	 *   date: 2016-3-24 下午5:07:13
	 * @param orderId 订单串号
	 * @return
	 */
	Map<String, Object> receiverReturnOrder(ReturnOrder returnOrder);

	/**
	 * 
	 * 付款前验证库存.
	 * author: 严志森
	 *   date: 2016-3-25 下午22:47:13
	 * @param orderId 订单串号
	 * @return
	 */
	Map<String, Object> validationStock(String orderId);

	Map<String, Object> findPromotionByProduct(int i, Long id);

	Map<String, Object> createOrder(Long userId, Long productId,
			Integer quantity, Long receiverId, Long paymentMethodId,
			Long shippingMethodId, Long[] couponCodes, String invoiceTitle,
			BigDecimal godMoneyNum, Long contractInfoId, String memo,
			String recommended, Long organizationId, String collectTime);

	Map<String, Object> purchaseAtOnce(String userId, Long id,int quantity,Long[] productBinds);

	Map<String, Object> godMoney(Long userId, BigDecimal godMoney, String orderSn);

	Map<String, Object> payment(Long userId, BigDecimal godMoney, String orderSn);


}
