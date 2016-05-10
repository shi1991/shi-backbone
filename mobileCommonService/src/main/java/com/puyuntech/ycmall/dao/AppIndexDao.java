package com.puyuntech.ycmall.dao;

import java.util.List;
import java.util.Map;

import com.puyuntech.ycmall.entity.AdEntity;
import com.puyuntech.ycmall.entity.Cart;
import com.puyuntech.ycmall.entity.CartItem;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Receiver;

/**
 * 
 * Dao - APP首页相关信息. 
 * Created on 2015-8-25 下午3:52:51 
 * @author Liaozhen
 */
public interface AppIndexDao extends BaseDao<AdEntity, Long> {

	/**
	 * 
	 * 查询APP首页轮播广告.
	 * author: Liaozhen
	 *   date: 2015-8-25 下午3:54:55
	 * @return
	 */
	List<AdEntity> findAdvertisement();
	
	/**
	 * 
	 * 根据广告位查询广告.
	 * TODO 只可以获取到广告ID和图片路径，如果需要其他信息，更改SQL语句.
	 * author: Liaozhen
	 *   date: 2015-8-25 下午3:55:19
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
	 * 商品详情
	 * TODO   暂使用死数据
	 * author: yanzhisen
	 *   date: 2015-9-5 下午16:48:25
	 * @param userId
	 * @return
	 
	Map<String,Object> getGoodsDetail(String userId);
	*/
	
	/**
	 * 商品信息列表检索.
	 * 
	 * author: yanzhisen
	 *   date: 2015-9-6 下午16:32:25
	 * @param selectVaule,selectType,pageLoadType,pageRowsCount,goodsId,page
	 * @return
	 */
	Map<String,Object> getGoodsList(String selectVaule,int pageLoadType,int pageRowsCount,String goodsId);
	
	/**
	 * 
	 * 获取购物车id
	 * author: 严志森
	 *   date: 2015-9-11 下午1:42:55
	 * @param userId
	 * @return
	 */
	Cart getCartId(String userId);
	
	/**
	 * 幸运排行榜.
	 * author: 南金豆
	 *   date: 2015-11-12 下午4:36:17
	 * @param luckyNum
	 * @return
	 */
	Map<String,Object> getRankingList(int luckyNum);
	
	/**
	 * 
	 * 淘更多 获取酷炫神器.
	 * author: 严志森
	 *   date: 2015-9-21 下午1:45:58
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
	 * 获取收货人列表.
	 * author: 严志森
	 *   date: 2015-9-29 下午3:15:04
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
	 * @param ordersId 起始订单编号
	 * @return
	 */
	Map<String, Object> getOrders(String userId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId);
	
	/**
	 *  门店介绍/活动信息.
	 * author: 南金豆
	 *   date: 2015-10-30 下午6:10:26
	 * @param shopId 门店介绍
	 * @param shopInfoType 信息类别【1：门店介绍，2：门店活动,  3：店员介绍】
	 * @return
	 */
	Map<String, Object> getShopDetailAndActivity(int shopInfoType, String shopId);

	/**
	 *【今日抢】判断是否可以抢购
	 * author: 南金豆
	 *   date: 2015-11-9 下午4:24:48
	 * @param robGoodsId  商品编号
	 * @param robGoodsType  商品类型【1：商品，2：神币类红包 3：积分类红包，4：实物类红包  5：优惠券】
	 * @param startTime 
	 * @param memberId  会员编号
	 * @return
	 */
	Map<String, Object> snapJudgment(Long grabSeckillId, int robGoodsType, Long userId, String startTime);

	
	/**
	 * 【今日抢】抢购成功：成功界面展示接口：商品图片、简介+绑定推荐配套商品图片、名称等（绑定推荐等只限定与商品部分】
	 * author: 南金豆
	 *   date: 2015-11-11 下午4:47:07
	 * @param robGoodsId  抢购商品编号
	 * @param robGoodsType 商品类型【1：商品，2：神币类红包 3：积分类红包，4：实物类红包  5：优惠券】
	 * @param userId  会员编号
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
	 * @param productSn 
	 * @return
	 */
	Map<String, Object> getGoodsDetail(String goodsId, String productSn);

	
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
	Map<String, Object> idgroup(Long id, String specification,int operator);
	
	/**
	 * 
	 * 设置默认收货地址.
	 * author: 严志森
	 *   date: 2015-11-27 下午5:39:02
	 * @param userId 用户id
	 * @param addressId 收货地址id
	 * @return
	 */
	Map<String, Object> setDefaultAddress(String userId, String addressId);
	
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
	 * 查看购物车中是否存在该商品.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-2 上午11:10:02
	 * @param cart
	 * @param product
	 * @return
	 */
	List<CartItem> getCartItem(Cart cart, Product product);
	
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
	 * 遍历门店列表.
	 * @author:严志森
	 * @date: 2016年4月6日 下午1:47:33 
	 * @param list 门店实体集合
	 * @return
	 */
	Map<String, Object> organizationList(List<Organization> list);
	
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
	 *   date: 2015年12月29日 下午2:11:32
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
	 * 售后进度列表.
	 * author: 严志森 date: 2016-1-13 上午11:09:38
	 * @param memberId 用户id
	 * @return
	 */
	Map<String, Object> aftersales(Long memberId, int pageLoadType, int pageRowsCount, String createDate);

	List<Object> sql(Long id,String sql);

	Map<String, Object> findPromotionByProduct(int i,Long id);
	
}