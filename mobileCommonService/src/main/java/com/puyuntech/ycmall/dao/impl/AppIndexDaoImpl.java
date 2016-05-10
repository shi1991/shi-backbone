package com.puyuntech.ycmall.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.AppIndexDao;
import com.puyuntech.ycmall.dao.OrderDao;
import com.puyuntech.ycmall.dao.OrderLogDao;
import com.puyuntech.ycmall.dao.OrganizationDao;
import com.puyuntech.ycmall.dao.PaymentLogDao;
import com.puyuntech.ycmall.dao.ProductDao;
import com.puyuntech.ycmall.dao.PromotionDao;
import com.puyuntech.ycmall.dao.ReviewDao;
import com.puyuntech.ycmall.dao.ShippingDao;
import com.puyuntech.ycmall.dao.TrackingLogDao;
import com.puyuntech.ycmall.entity.AdEntity;
import com.puyuntech.ycmall.entity.Cart;
import com.puyuntech.ycmall.entity.CartItem;
import com.puyuntech.ycmall.entity.CouponCode;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.OrderLog;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.PaymentLog;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ProductModel;
import com.puyuntech.ycmall.entity.Receiver;
import com.puyuntech.ycmall.entity.Shipping;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.TrackingLog;
import com.puyuntech.ycmall.entity.value.CartItemBindProductValue;
import com.puyuntech.ycmall.service.AreaService;
import com.puyuntech.ycmall.service.CouponCodeService;
import com.puyuntech.ycmall.service.MemberService;
import com.puyuntech.ycmall.service.OrderService;
import com.puyuntech.ycmall.service.ReceiverService;
import com.puyuntech.ycmall.util.SphericaldistanceUtil;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.vo.BindProductInfoVO;
import com.puyuntech.ycmall.vo.ProductSpecificationVO;
import com.puyuntech.ycmall.vo.Result;

/**
 * 
 * DaoImpl - APP首页相关信息. 
 * Created on 2015-8-25 下午3:53:47 
 * @author yanzhisen
 */
@Repository("appIndexDaoImpl")
public class AppIndexDaoImpl extends BaseDaoImpl<AdEntity, Long> implements AppIndexDao {
	@Resource(name ="areaServiceImpl")
	private AreaService areaService;
	
	@Resource(name ="orderDaoImpl")
	private OrderDao orderDao;
	
	@Resource(name ="paymentLogDaoImpl")
	private PaymentLogDao paymentLogDao;
	/**
	 * 订单 Service
	 */
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Resource(name = "reviewDaoImpl")
	private ReviewDao reviewDao;
	
	@Resource(name="promotionDaoImpl")
	private PromotionDao promotionDao;
	
	@Resource(name="organizationDaoImpl")
	private OrganizationDao organizationDao;
	
	@Resource(name="shippingDaoImpl")
	private ShippingDao shippingDao;
	
	@Resource(name="trackingLogDaoImpl")
	private TrackingLogDao trackingLogDao;
	
	@Resource(name="orderLogDaoImpl")
	private OrderLogDao orderLogDao;
	
	/**
	 * 商品 Dao
	 */
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	
	/**
	 * 
	 * 查询APP首页轮播广告.
	 * TODO 未确定APP首页广告位ID是否为1，如果为其他，更改参数即可.
	 * @return
	 * @see com.puyuntech.ycmall.dao.AppIndexDao#findAdvertisement()
	 */
	@Override
	public List<AdEntity> findAdvertisement() {
		return this.findAdByPosition(1);
	}

	/**
	 * 
	 * 根据广告位ID查询广告.
	 * TODO 只可以获取到广告ID和图片路径，如果需要其他信息，更改SQL语句.此修改会影响到APP_1接口的返回数据.
	 * @param positionId
	 * @return
	 * @see com.puyuntech.ycmall.dao.AppIndexDao#findAdByPosition(java.lang.Integer)
	 */
	@Override
	public List<AdEntity> findAdByPosition(Integer positionId) {
		/**
		 * 查询
		 * 条件一：广告位为positionId
		 * 条件二：开始/结束时间为空 或者 当前时间在开始时间和结束时间之间
		 * 顺序排列
		 **/
		String sql = "SELECT `f_id` as adId,`f_path` as adPath FROM `t_ad` WHERE `f_ad_position` = :positionId AND ( (`f_end_date` IS NULL AND `f_begin_date` IS NULL) OR (SELECT NOW()) BETWEEN `f_begin_date` AND `f_end_date`) ORDER BY `f_orders`;";

		/** 查询 **/
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("positionId", positionId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<AdEntity> result = query.getResultList();
		
		/**
		 * 验证查询数据是否为空
		 */
		if(result.size() == 0){
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getUserInfo(String userId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map4 = new HashMap<String,Object>();
		List<Object> listCount= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		List<Object> list2= new ArrayList<Object>();
		try {
			//SQL语句
			String sql="SELECT t.f_nickname userNickName,t.f_god_money userGodCurrency,t.f_photo_path userPhotoPath,t.f_point userIntegral,"
					+ "COUNT(a.f_id) AS userTicket FROM t_member t LEFT JOIN t_coupon_code a ON t.f_id = a.f_member LEFT JOIN t_coupon  b ON b.f_id = a.f_coupon"
					+ " WHERE t.f_id =:userId AND a.f_is_used = 0 AND b.f_end_date > NOW()";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			
			//SQL语句 待付款
			String daifukuan="SELECT a.f_id id FROM t_order a WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 0 AND a.f_create_date < '9999' AND a.f_type != 2 and (a.f_expire > now() or a.f_expire IS NULL)";
			//执行sql语句
			Query daifukuanquery = entityManager.createNativeQuery(daifukuan);
			daifukuanquery.setParameter("userId", userId);
			//结果集格式化
			daifukuanquery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listCount = new ArrayList<Object>();
			listCount = daifukuanquery.getResultList();
			if( listCount.size() > 0){
				Integer a = 0 ;
				for (Object object : listCount) {
					Map<String, Object> map2=(Map<String, Object>)object;
					String sql2="SELECT COUNT(*) count  FROM t_order_item a LEFT JOIN t_product b ON a.f_product =b.f_id WHERE a.f_orders = :orderId";
					//执行sql语句
					Query query2 = entityManager.createNativeQuery(sql2);
					query2.setParameter("orderId", map2.get("id"));
					String string = query2.getSingleResult().toString();
					if( Integer.valueOf(string) > 0 ){
						a = a + 1;
					}
					
				}
				map.put("daifukuan", a);
			}else{
				map.put("daifukuan", 0);
			}
			
			//SQL语句 待收货
			String daishouhuo="SELECT a.f_id id FROM t_order a LEFT JOIN t_shipping b ON a.f_id = b.f_orders LEFT JOIN t_tracking_log c ON b.f_tracking_no = c.f_tracking_id "
					+ "WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 1 AND a.f_create_date < '9999' AND a.f_type != 2";
			//执行sql语句
			Query daishouhuoquery = entityManager.createNativeQuery(daishouhuo);
			daishouhuoquery.setParameter("userId", userId);
			//结果集格式化
			daishouhuoquery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listCount = new ArrayList<Object>();
			listCount = daishouhuoquery.getResultList();
			if( listCount.size() > 0){
				Integer a = 0 ;
				for (Object object : listCount) {
					Map<String, Object> map2=(Map<String, Object>)object;
					String sql2="SELECT COUNT(*) count  FROM t_order_item a LEFT JOIN t_product b ON a.f_product =b.f_id WHERE a.f_orders = :orderId";
					//执行sql语句
					Query query2 = entityManager.createNativeQuery(sql2);
					query2.setParameter("orderId", map2.get("id"));
					String string = query2.getSingleResult().toString();
					if( Integer.valueOf(string) > 0 ){
						a = a + 1;
					}
				}
				map.put("daishouhuo", a);
			}else{
				map.put("daishouhuo", 0);
			}
			
			//SQL语句 待自提
			String daiziti="SELECT a.f_id id FROM t_order a WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 3 AND a.f_create_date < '9999' AND a.f_type != 2";
			//执行sql语句
			Query daizitiquery = entityManager.createNativeQuery(daiziti);
			daizitiquery.setParameter("userId", userId);
			//结果集格式化
			daizitiquery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listCount = new ArrayList<Object>();
			listCount = daizitiquery.getResultList();
			if( listCount.size() > 0){
				Integer a = 0 ;
				for (Object object : listCount) {
					Map<String, Object> map2=(Map<String, Object>)object;
					String sql2="SELECT COUNT(*) count  FROM t_order_item a LEFT JOIN t_product b ON a.f_product =b.f_id WHERE a.f_orders = :orderId";
					//执行sql语句
					Query query2 = entityManager.createNativeQuery(sql2);
					query2.setParameter("orderId", map2.get("id"));
					String string = query2.getSingleResult().toString();
					if( Integer.valueOf(string) > 0 ){
						a = a + 1;
					}
				}
				map.put("daiziti", a);
			}else{
				map.put("daiziti", 0);
			}
			
			//SQL语句 待评价
			String daipingjia="SELECT  a.f_id id,a.f_deposit deposit,date_format(a.f_create_date,'%Y-%m-%d %T') createDate, a.f_sn ordersSn, a.f_amount buyPrice, a.f_status ordersType FROM t_order a WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 5 AND a.f_create_date < '9999' AND a.f_type != 2";
			//执行sql语句
			Query daipingjiaquery = entityManager.createNativeQuery(daipingjia);
			daipingjiaquery.setParameter("userId", userId);
			//结果集格式化
			daipingjiaquery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listCount = daipingjiaquery.getResultList();
			if( listCount.size() > 0){
				Integer a = 1 ;
				for (Object object : listCount) {
					Map<String, Object> map2=(Map<String, Object>)object;
					String sql2="SELECT COUNT(*) FROM t_order_item a LEFT JOIN t_product b ON a.f_product =b.f_id WHERE a.f_orders = :orderId and a.f_is_review != 1";
					//执行sql语句
					Query query2 = entityManager.createNativeQuery(sql2);
					query2.setParameter("orderId", map2.get("id"));
					//结果集格式化
					a = a + Integer.valueOf(query2.getSingleResult().toString());
				}
				map.put("daipingjia", a-1);
			}else{
				map.put("daipingjia", 0);
			}
			
			//SQL语句 售后
			String shouhou="SELECT count(*) FROM t_return_order a LEFT JOIN t_return_order_item b ON a.f_id = b.f_return_order "
					+ "LEFT JOIN t_product c ON b.f_sn = c.f_sn WHERE a.f_member = :userId  AND a.f_status in (0,3,4,5,6,7,8,9) ";
			//执行sql语句
			Query shouhouquery = entityManager.createNativeQuery(shouhou);
			shouhouquery.setParameter("userId", userId);
			//结果集格式化
			Integer a = Integer.valueOf(shouhouquery.getSingleResult().toString());
			map.put("shouhou", a);
			list2.add(map);
			map4.put("dingdanshu", list2);
			map4.put("jibenxinxi", list);
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, map4);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getParameterText(String goodsId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT t.f_parameter_values parameterValues FROM t_product t WHERE t.f_id =:goodsId";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSpecificationText(String goodsId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT t.f_specification_items specificationItems FROM t_product t WHERE t.f_id =:goodsId";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsImageText(String goodsId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT t.f_introduction introduction FROM t_product t WHERE t.f_id =:goodsId";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUserDetail(String userId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT t.f_nickname nickname,t.f_photo_path photoPath,t.f_username userName,t.f_phone userPhone,t.f_gender gender,t.f_email userEmail FROM t_member t WHERE t.f_id =:userId";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCurrency(String userId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT t.f_god_money godMoney,t.f_point point FROM t_member t WHERE t.f_id =:userId";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getHistory(String userId,String getInfoType) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=new ArrayList<Result>();
		String sql=null;
		try {
			//神币历史
			if(getInfoType.equals("1")){
				//SQL语句
				sql="SELECT date_format(t.f_create_date,'%Y-%m-%d %T') createDate,t.f_type type , t.f_credit credit,t.f_debit debit,t.f_balance balance FROM t_god_money_log t WHERE t.f_member =:userId ORDER BY t.f_create_date desc";
			}
			//积分历史
			if(getInfoType.equals("2")){
				//SQL语句
				sql="SELECT date_format(t.f_create_date,'%Y-%m-%d %T') createDate,t.f_type type , t.f_credit credit,t.f_debit debit,t.f_balance balance FROM t_point_log t WHERE t.f_member =:userId ORDER BY t.f_create_date desc";
			}
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsList(String selectVaule,
			int pageLoadType, int pageRowsCount, String goodsId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		try {
			sql="SELECT date_format(t.f_create_date,'%Y-%m-%d') createDate,t.f_type type , t.f_credit credit,t.f_debit debit,t.f_balance balance FROM t_god_money_log t WHERE t.f_member =:userId ORDER BY t.f_create_date";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", selectVaule);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@Override
	public Cart getCartId(String userId) {
		 String jpql="SELECT cart FROM Cart cart WHERE cart.member =:userId";
		 Cart cart=entityManager.createQuery(jpql, Cart.class).setParameter("userId", userId).getSingleResult();
		 if(cart.getId()==null){
			 String hql="insert into Cart cart(cart.member) values(:userId)";
			 Cart carts=entityManager.createQuery(hql, Cart.class).setParameter("userId", userId).getSingleResult();
			 return carts;
		 }
		 return cart;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getRankingList(int luckyNum) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT b.f_id userId, b.f_photo_path userPhotoPath, b.f_nickname userNickName, c.num  robNumber FROM ( SELECT f_member, count(f_member) AS " +
					"num FROM t_grab_seckill_log GROUP BY f_member ORDER BY num DESC ) c, t_member b WHERE c.f_member = b.f_id LIMIT :luckyNum  ; ";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("luckyNum", luckyNum);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> listGoodsByTagType(int goodsType) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> result1 = new HashMap<String, Object>();
		List<String> images = new ArrayList<String>();
		List<Object> list=null;
		List<Object> list2=new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>(); 
		try {
				String sql="SELECT t.f_id goodsId, t.f_name goodsName, t.f_caption goodsSubhead, t.f_price goodsPrice, t.f_product_images goodsImagePath " +
						"FROM t_product t LEFT JOIN t_product_tag g ON t.f_id = g.f_product LEFT JOIN t_tag a ON g.f_tags = a.f_id WHERE " +
						"1 = 1 AND a.f_id =:goodsType AND t.f_is_marketable =1 ORDER BY t.f_id  LIMIT 0,3";
				//执行sql语句
				Query query = entityManager.createNativeQuery(sql);
				query.setParameter("goodsType", goodsType);
				//结果集格式化
				query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				list= query.getResultList();
				if(list.size()>0){
					for (Object object : list) {
						Map<String, Object> map2=(Map<String, Object>)object;
						map=new HashMap<String, Object>();
                        map.put("id", map2.get("goodsId"));
						map.put("goodsId", map2.get("goodsId"));
						map.put("goodsName", map2.get("goodsName"));
						map.put("goodsSubhead", map2.get("goodsSubhead"));
						map.put("goodsPrice", map2.get("goodsPrice"));
						JSONArray goodsImagePath = JSONArray.fromObject(map2.get("goodsImagePath"));
						for(int i=0;i<goodsImagePath.size();i++){
							images = new ArrayList<String>();
							JSONObject  color= goodsImagePath.getJSONObject(i);
							String defaul=color.getString("source");
							images.add(defaul);
						 }
						map.put("goodsImagePath",images.get(0));
						Long productId =  Long.parseLong(map2.get("goodsId").toString());
						result1 = this.findPromotionByProduct(1, productId);
						if (UnivParameter.DATA_CORRECTCODE.equals(result1.get(
								UnivParameter.CODE).toString())) {
							map.put("hui", result1.get(UnivParameter.DATA));
						}else{
							result.put(UnivParameter.REASON,
									mapResult.get(UnivParameter.ERRORMESSAGE).toString());
							result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
							return result;
						}
						
						result1 = this.findPromotionByProduct(2, productId);
						if (UnivParameter.DATA_CORRECTCODE.equals(result1.get(
								UnivParameter.CODE).toString())) {
							map.put("zeng", result1.get(UnivParameter.DATA));
						}else{
							result.put(UnivParameter.REASON,
									mapResult.get(UnivParameter.ERRORMESSAGE).toString());
							result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
							return result;
						}
						
						result1 = this.findPromotionByProduct(3, productId);
						if (UnivParameter.DATA_CORRECTCODE.equals(result1.get(
								UnivParameter.CODE).toString())) {
							map.put("cu", result1.get(UnivParameter.DATA));
						}else{
							result.put(UnivParameter.REASON,
									mapResult.get(UnivParameter.ERRORMESSAGE).toString());
							result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
							return result;
						}
						list2.add(map);
					}
				}
				
				
				
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list2);
			return mapResult;
	}

	@Override
	public Map<String, Object> addAddress(Receiver receiver,Long areaId,Long userId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		try {
			 receiver.setArea(areaService.find(areaId));
			 Member member=memberService.find(userId);
			 receiver.setAreaName(areaService.find(areaId).getFullName());
			 receiver.setMember(member);
			 receiverService.save(receiver);
			 //存放正确的返回参数CODE--1
			 mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			 mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			 
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			return mapResult;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<String, Object> getAddress(String userId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT a.id, a.address, a.consignee, a.phone, a.zipCode, CASE a.isDefault WHEN 0 THEN 0 ELSE 1 END isDefault, a.quId, a.quName, a.shiId, a.shiName, a.shengId, c.f_name shengName " +
					"FROM ( SELECT a.id, a.address, a.consignee, a.phone, a.zipCode, a.isDefault, a.quId, a.quName, a.shiId,c.f_name shiName, c.f_parent shengId FROM " +
					"( SELECT t.f_id id, t.f_address address, t.f_consignee consignee, t.f_phone phone, t.f_zip_code zipCode, t.f_is_default isDefault, a.f_name quName," +
					"t.f_area quId, a.f_parent shiId FROM t_receiver t LEFT JOIN t_area a ON t.f_area = a.f_id OR a.f_id = a.f_parent WHERE t.f_member = :userId ) a LEFT JOIN " +
					"t_area c ON a.shiId = c.f_id ) a LEFT JOIN t_area c ON a.shengId = c.f_id";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			//存放最终的执行结果
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCoupon(String userId, String couponType,
			String type, String couponId, int pageLoadType,
			int pageRowsCount) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		//SQL语句
		String sqlBeg="SELECT d.f_id couponId,d.f_code couponCode,c.f_name couponName,c.f_count_price countPrice,c.f_introduction couponInfo,c.f_coupon_type " +
				"couponType,c.f_image couponImagePath,c.f_begin_date couponStartDate,c.f_end_date couponEndDate FROM t_coupon c " +
				"LEFT JOIN t_coupon_code d ON c.f_id=d.f_coupon LEFT JOIN t_member m ON m.f_id=d.f_member WHERE m.f_id=:userId " +
				"AND c.f_coupon_type=:couponType AND";
		String sql=null;
		try {
			switch (Integer.parseInt(type) ) {
			//已使用
			case 2:
				sql=sqlBeg+" d.f_is_used=1 AND d.f_id >:couponId LIMIT 0,:pageRowsCount";
				break;
			//已过期
			case 3 :
				sql=sqlBeg+" d.f_is_used !=1 AND c.f_end_date< NOW() AND d.f_id >:couponId LIMIT 0,:pageRowsCount";
				break;
			default:
				sql=sqlBeg+" c.f_end_date> NOW() AND d.f_is_used=0 AND d.f_id >:couponId LIMIT 0,:pageRowsCount";
				break;
			}
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			query.setParameter("couponType", couponType);
			if(pageLoadType==0){
				query.setParameter("couponId", "0");
			}else{
				query.setParameter("couponId", couponId);
			}
			
			query.setParameter("pageRowsCount", pageRowsCount);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			//存放最终的执行结果
			return mapResult;
	}

	@Override
	public Map<String, Object> useCoupon(String userId, String couponId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		CouponCode couponCode=new CouponCode();
		try {
			couponCode.setMember(memberService.find(Long.parseLong(userId)));
			couponCode.setId(Long.parseLong(couponId));
			couponCode.setIsUsed(true);
			couponCodeService.update(couponCode);
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			return mapResult;
		}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCart(String userId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
			try{
			//SQL语句
			String sql="SELECT a.f_id cartItemId,c.f_id goodsId,c.f_name goodsName,c.f_image goodsImagePath,c.f_price goodsPrice,a.f_quantity buyNumber" +
							  ",c.f_stock goodsState FROM t_cart_item a LEFT JOIN t_cart b ON a.f_cart=b.f_id LEFT JOIN t_product c ON a.product=c.f_id WHERE" +
						      " b.member=:userId AND a.f_parent_id=0";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			//存放最终的执行结果
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getFavorite(String userId, int pageLoadType,
			int pageRowsCount, String goodsId) {//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String, Object>();
		List<Object> list=new ArrayList<Object>();
		List<Object> listAll=new ArrayList<Object>();
		List<String> images = new ArrayList<String>();
		String sqlBeg=null;
		String sql=null;
		try {
			//SQL语句
			sqlBeg="select c.f_id goodsId,c.f_price goodsPrice,c.f_name goodsName,c.f_product_images goodsImagePath,c.f_stock goodsState,c.f_caption caption from t_member_favorite_products a  " +
						"LEFT JOIN t_member b on  a.f_favorite_members=b.f_id LEFT JOIN t_product c ON  c.f_id=f_favorite_products WHERE b.f_id=:userId ";
			
			sql=sqlBeg+"AND c.f_id >:goodsId LIMIT 0,:pageRowsCount";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			
			if(pageLoadType==0){
				query.setParameter("goodsId", "0");
			}else{
				query.setParameter("goodsId", goodsId);
			}
			
			query.setParameter("pageRowsCount", pageRowsCount);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			for(int i=0;i<list.size();i++){
				map=new HashMap<String, Object>();
				Map<String,Object> m = (Map<String, Object>) list.get(i);
				Object goodsIdObject =m.get("goodsId");
				Long Id=Long.parseLong(String.valueOf(goodsIdObject));
				
				Object goodsPriceObject =m.get("goodsPrice");
				String goodsPrice=String.valueOf(goodsPriceObject);
				
				Object goodsNameObject =m.get("goodsName");
				String goodsName=String.valueOf(goodsNameObject);
				
				Object goodsImagePathObject =m.get("goodsImagePath");
				JSONArray goodsImagePath = JSONArray.fromObject(goodsImagePathObject);
				for(int j=0;j<goodsImagePath.size();j++){
					images = new ArrayList<String>();
					JSONObject  color= goodsImagePath.getJSONObject(j);
					String defaul=color.getString("thumbnail");
					images.add(defaul);
				 }
				
				Object captionObject =m.get("caption");
				String caption=String.valueOf(captionObject);
				
				Long count =reviewDao.count(productDao.find(Id));
				map.put("reviewCount", count);
				map.put("goodsPrice", goodsPrice);
				map.put("goodsImagePath", images.get(0));
				map.put("caption", caption);
				map.put("goodsName", goodsName);
				map.put("id", Id);
				listAll.add(map);
			}
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, listAll);
			return mapResult;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBonusHistroy(String userId, int pageLoadType,
			int pageRowsCount, String packetId) {//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String	sql=null;
		try {
			//SQL语句
			String sqlBeg="SELECT a.f_id packetId, a.f_bonus_type packetType, a.f_apply_time creatDate,a.f_Bonus_kind aonusKind, a.f_title pcaketName, a.f_state "
					+ "auditState, a.f_content packetInfo, a.f_credit packetCredit, a.f_gross pcaketNumber, a.f_bonus_kind splitType, a.f_memo "
					+ "refuseSeason FROM t_bonus a WHERE a.f_member = :userId   ";
			if(pageLoadType==0){
				sql=sqlBeg+"AND a.f_id >:packetId ORDER BY a.f_create_date desc LIMIT 0,:pageRowsCount";
			}else{
				sql=sqlBeg+"AND a.f_id <:packetId ORDER BY a.f_create_date desc LIMIT 0,:pageRowsCount";
			}
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			if(pageLoadType==0){
				query.setParameter("packetId", "0");
			}else{
				query.setParameter("packetId", packetId);
			}
			query.setParameter("pageRowsCount", pageRowsCount);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();

			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsAfterSale(String goodsId) {
			Map<String,Object> mapResult = new HashMap<String,Object>();
			List<Result> list=null;
			try {
				//SQL语句
				String sql="SELECT  a.f_sale_support  goodGuarantee  from  t_product  a WHERE a.f_id =:goodsId  ";
				//执行sql语句
				Query query = entityManager.createNativeQuery(sql);
				query.setParameter("goodsId", goodsId);
				//结果集格式化
				query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				list= query.getResultList();
				
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			} catch (Exception e) {
				//存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
				return mapResult;
			}
			
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, list);
				return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getShop(int pageLoadType, int pageRowsCount,String shopId,Float userXCoordinate,Float userYCoordinate) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> resultAll= new HashMap<String, Object>();
		List<Object> resultList=new ArrayList<Object>();
		List<Object> list=null;
		String sqlBeg=null;
		String sql=null;
		try {
			//SQL语句
			sqlBeg="SELECT a.f_id shopId, a.f_name shopName, a.f_image shopImagePath, a.f_address shopAddress, b.f_full_name shopAreaFullName, a.f_tel shopTel, " +
					"a.f_latitude shopXCoordinate, a.f_longitude shopYCoordinate FROM t_organization a, t_area b WHERE a.f_area = b.f_orders and a.f_types like '%store%'";
			sql=sqlBeg+"AND a.f_id > :shopId  ORDER BY ( POWER( MOD ( ABS(a.f_latitude - :userXCoordinate), 360 ), 2 ) + " +
						"POWER( ABS(a.f_longitude - :userYCoordinate), 2 )) LIMIT 0, :pageRowsCount";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			if(pageLoadType==0){
				query.setParameter("shopId", "0");
			}else{
				query.setParameter("shopId", shopId);
			}
			
			query.setParameter("pageRowsCount", pageRowsCount);
			query.setParameter("userXCoordinate", userXCoordinate);
			query.setParameter("userYCoordinate", userYCoordinate);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			if(userXCoordinate!=0.0&&userXCoordinate!=0.0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> m = (Map<String, Object>) list.get(i);
					Object shopIdObject =m.get("shopId");
					String Id=String.valueOf(shopIdObject);
					Object shopNameObject =m.get("shopName");
					String shopName=String.valueOf(shopNameObject);
					Object shopImagePathObject =m.get("shopImagePath");
					String shopImagePath=String.valueOf(shopImagePathObject);
					Object shopAddressObject =m.get("shopAddress");
					String shopAddress=String.valueOf(shopAddressObject);
					Object shopAreaFullNameObject =m.get("shopAreaFullName");
					String shopAreaFullName=String.valueOf(shopAreaFullNameObject);
					Object shopXCoordinateObject =m.get("shopXCoordinate");
					String shopXCoordinateStr=String.valueOf(shopXCoordinateObject);
					Float shopXCoordinate=Float.parseFloat(shopXCoordinateStr);
					Object shopYCoordinateObject =m.get("shopYCoordinate");
					String shopYCoordinateStr=String.valueOf(shopYCoordinateObject);
					Float shopYCoordinate=Float.parseFloat(shopYCoordinateStr);
					double dist = SphericaldistanceUtil.GetDistance(shopYCoordinate, shopXCoordinate, userYCoordinate, userXCoordinate);
					Map<String,Object> result= new HashMap<String, Object>();
					result.put("shopId",Id);
					result.put("shopName",shopName);
					result.put("shopImagePath",shopImagePath);
					result.put("shopAddress",shopAddress);
					result.put("shopAreaFullName",shopAreaFullName);
					result.put("shopXCoordinate",shopXCoordinate);
					result.put("shopYCoordinate",shopYCoordinate);
					result.put("shopMemberDistance",dist);
					resultList.add(result);
				}
				resultAll.put("list", resultList);
				resultAll.put("count", list.size());
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, resultAll);
			}else{
				resultAll.put("list", list);
				resultAll.put("count", list.size());
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, resultAll);
			}
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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getShopId(Float userXCoordinate,
			Float userYCoordinate) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		String sql=null;
		List<Object> list=null;
		try {
			//SQL语句
			sql="SELECT a.f_id shopId FROM t_organization a LEFT JOIN t_area b ON a.f_area = b.f_orders where a.f_types NOT LIKE '%headquarters%' AND a.f_id != 100 ORDER BY ( POWER( MOD (ABS(a.f_latitude - :userXCoordinate), 360), 2 ) +" +
					" POWER(ABS(a.f_longitude - :userYCoordinate), 2))";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userXCoordinate", userXCoordinate);
			query.setParameter("userYCoordinate", userYCoordinate);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getOrders(String userId, int ordersType,int pageLoadType, int pageRowsCount, String createDate) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list=new ArrayList<Object>();
		List<Object> list2=new ArrayList<Object>();
		List<Object> list3=new ArrayList<Object>();
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer buffer = new StringBuffer();
		Set<StockLog> logs = new HashSet<StockLog>();
		String sqlBeg=null;
		String sql=null;
		try {
			//SQL语句
			//ordersType 订单状态【0：全部，1：代付款  :2：待收货（不包含待发货），3： 待自提 :4：待评价（完成的）】5 售后
			switch (ordersType) {
			case 0:
				sqlBeg="SELECT  a.f_id id,a.f_organization organizationId, a.f_deposit deposit,date_format(a.f_create_date,'%Y-%m-%d %T') createDate, a.f_sn ordersSn,date_format(a.f_expire,'%Y-%m-%d %T') expire, a.f_amount buyPrice, a.f_status ordersType FROM t_order a WHERE a.f_member = :userId AND f_is_delete != 1 ";
				break;
			case 1:
				sqlBeg="SELECT  a.f_id id,a.f_organization organizationId,a.f_deposit deposit,date_format(a.f_create_date,'%Y-%m-%d %T') createDate,a.f_payment_method_type payType,date_format(a.f_expire,'%Y-%m-%d %T') expire, a.f_sn ordersSn, a.f_amount buyPrice, a.f_status ordersType FROM t_order a WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 0 ";
				break;
			case 2:
				sqlBeg="SELECT  c.f_latest_logistics lastLogistics,a.f_organization organizationId,a.f_deposit deposit,a.f_id id,date_format(a.f_create_date,'%Y-%m-%d %T') createDate, a.f_sn ordersSn, a.f_amount buyPrice, a.f_status ordersType FROM t_order a LEFT JOIN t_shipping b ON a.f_id = b.f_orders "
						+ "LEFT JOIN t_tracking_log c ON b.f_tracking_no = c.f_tracking_id WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 1 ";
				break;
			case 3:
				sqlBeg="SELECT  a.f_id id,a.f_deposit deposit,a.f_organization organizationId,f_organization organization,f_collect_time collectTime,date_format(a.f_create_date,'%Y-%m-%d %T') createDate, a.f_sn ordersSn, a.f_amount buyPrice, a.f_status ordersType FROM t_order a WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 3";
				break;
			case 4:
				sqlBeg="SELECT  a.f_id id,a.f_deposit deposit,a.f_organization organizationId,date_format(a.f_create_date,'%Y-%m-%d %T') createDate, a.f_sn ordersSn, a.f_amount buyPrice, a.f_status ordersType FROM t_order a WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 5";
				break;
			case 5:
				sqlBeg="SELECT  a.f_id id,a.f_deposit deposit,a.f_organization organizationId,date_format(a.f_create_date,'%Y-%m-%d %T') createDate, a.f_sn ordersSn, a.f_amount buyPrice, a.f_status ordersType FROM t_order a WHERE a.f_member = :userId AND f_is_delete != 1 AND a.f_status = 5";
				break;
			}
			sql=sqlBeg+" AND a.f_create_date <:createDate AND a.f_type != 2 AND a.f_type != 1 order BY a.f_create_date desc  LIMIT 0,:pageRowsCount";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			
			if(pageLoadType==0){
				query.setParameter("createDate", "9999");
			}else{
				query.setParameter("createDate", createDate);
			}
			
			query.setParameter("pageRowsCount", pageRowsCount);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			if(list.size()>0){
				for (Object object : list) {
					logs = new HashSet<StockLog>();
					map=new HashMap<String, Object>();
					Map<String, Object> map2=(Map<String, Object>)object;
					Long id = Long.parseLong(map2.get("id").toString());
					OrderLog orderLog = orderLogDao.findByStye(id);
					Order order = orderService.find(Long.parseLong(map2.get("id").toString()));
					if(ordersType == 3){
						Organization organization = organizationDao.find(Long.parseLong(map2.get("organization").toString()));
						map.put("organizationId", organization.getId());
						map.put("organization", organization.getName());
						map.put("organizationAddress", organization.getArea().getName()+organization.getAddress());
						map.put("organizationTel", organization.getTel());
						map.put("collectTime", map2.get("collectTime"));
					}
					if(ordersType == 2){
						for(Shipping shipping:order.getShippings()){
				            for(StockLog stockLog:shipping.getStockLogs()){
				            	if( stockLog.getState().equals("10") || stockLog.getState().equals("5")) {
				            		buffer = buffer.append(stockLog.getId()).append(",");
				            	}
				            }
				            if(buffer.length() > 0){
				            	map.put("stockId",buffer.deleteCharAt(buffer.length()-1));
				            }
				            
				        }
						String string = null ;
						if(map2.get("organizationId") == null){
							string = "1";
						}else{
							string = map2.get("organizationId").toString();	
						}
						Organization organization = organizationDao.find(Long.parseLong(string));
	            		map.put("organizationId",organization.getId());
						if(map2.get("lastLogistics") == null){
							map.put("lastLogistics", "");
						}else{
							map.put("lastLogistics",map2.get("lastLogistics"));
						}
						 
					}
					if(ordersType == 5 ){
					}else{
						String string = null ;
						if(map2.get("organizationId") == null){
							string = "1";
						}else{
							string = map2.get("organizationId").toString();	
						}
						Organization organization = organizationDao.find(Long.parseLong(string));
	            		map.put("organizationId",organization.getId());
						map.put("id", id);
						map.put("createDate", map2.get("createDate"));
						
						if(orderLog == null){
							map.put("shouhuoshijian", null);
						}else{
							map.put("shouhuoshijian", format.format(orderLog.getCreateDate()));
						}
						
						map.put("ordersSn", map2.get("ordersSn"));
						if(map2.get("expire") != null){
							map.put("expire", map2.get("expire"));
						}
						if( order.getDeposit() != null){
							map.put("buyPrice",order.getCashAmountReceivable().subtract(order.getDeposit()));
						}else{
							map.put("buyPrice",order.getCashAmountReceivable());
						}
						
						map.put("ordersType", map2.get("ordersType"));
					}
					
					
					//售后查询发货单
					if(ordersType == 5){
						for(Shipping shipping:order.getShippings()){
							if(shipping.getStatus() != Shipping.Status.returned){
								list3 = new ArrayList<Object>();
								map = new HashMap<String, Object>();
					            for(StockLog stockLog:shipping.getStockLogs()){
					            	logs.add(stockLog);
					            }
							}
						}
						for(StockLog stockLog:logs){
			            	map2 = new HashMap<String, Object>();
			            	if( stockLog.getState().equals("10") || stockLog.getState().equals("5")) {
			            		map2=(Map<String, Object>)object;
				            	map.put("ordersSn", map2.get("ordersSn"));
				            	map.put("createDate", map2.get("createDate"));
				            	map.put("id", id);
								map.put("shouhuoshijian", format.format(order.getCompleteDate()));
								map.put("ordersSn", map2.get("ordersSn"));
								if(map2.get("expire") != null){
									map.put("expire", map2.get("expire"));
								}
								map.put("buyPrice",order.getCashAmountReceivable());
								map.put("ordersType", map2.get("ordersType"));
								map2 = new HashMap<String, Object>();
								Product product = stockLog.getProduct();
			            		map2.put("goodsImagePath", product.getThumbnail());
			            		map2.put("goodsName", product.getName());
			            		map2.put("orderItemId", stockLog.getId());
			            		map2.put("productSn", stockLog.getProductSn());
			            		map2.put("goodsId", product.getSn());
			            		map2.put("id", product.getId());
			            		map2.put("goodsType", product.getType().ordinal());
			            		list3.add(map2);
			            		map.put("orderItem",list3);
			            	}
			            }
						if(map.size() > 0){
							list2.add(map);
						}    
					}else if(ordersType == 4){
						map = new HashMap<String, Object>();
						String sql2="SELECT a.f_id orderItemId,a.f_thumbnail goodsImagePath,a.f_name goodsName,a.f_sn goodsId,b.f_id id,a.f_is_review isReview  FROM t_order_item a LEFT JOIN t_product b ON a.f_product =b.f_id WHERE a.f_orders = :orderId and a.f_is_review != 1";
						//执行sql语句
						Query query2 = entityManager.createNativeQuery(sql2);
						query2.setParameter("orderId", map2.get("id"));
						//结果集格式化
						query2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						list3= query2.getResultList();
						if(list3.size()>0){
							map.put("orderItem", list3);
							map.put("id", map2.get("id"));
							map.put("createDate", map2.get("createDate"));
							map.put("ordersSn", map2.get("ordersSn"));
							map.put("ordersType", map2.get("ordersType"));
						}
						if(order.getStatus() == Order.Status.pendingReceive){
							List<Shipping> shippings=shippingDao.findByOrder(order);
							if(shippings.size()<1){
								map.put("wuliu", null);
							}else{
								Shipping shipping = shippings.toArray( new Shipping[shippings.size()] )[0];
								TrackingLog trackingLog=trackingLogDao.findByTrackingId(shipping.getTrackingNo());
								map.put("wuliu", trackingLog.getLatestLogistics());
							}
						}
						if(order.getStatus() == Order.Status.daiziti){
							Organization organization = organizationDao.find(order.getOrganization().getId());
							map.put("organization", organization.getName());
							map.put("organizationAddress", organization.getArea().getName()+organization.getAddress());
							map.put("organizationTel", organization.getTel());
							map.put("collectTime", order.getCollectTime());
						}
						if(map.size() > 0){
							list2.add(map);
						}
					}else{
						String sql2="SELECT a.f_id orderItemId,a.f_thumbnail goodsImagePath,a.f_name goodsName,b.f_id id,a.f_sn goodsId,a.f_is_review isReview  FROM t_order_item a LEFT JOIN t_product b ON a.f_product =b.f_id WHERE a.f_orders = :orderId";
						//执行sql语句
						Query query2 = entityManager.createNativeQuery(sql2);
						query2.setParameter("orderId", map2.get("id"));
						//结果集格式化
						query2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						list3= query2.getResultList();
						
						
						if(list3.size()>0){
							map.put("orderItem", list3);
						}
						if(order.getStatus() == Order.Status.pendingReceive){
							buffer = new StringBuffer();
							List<Shipping> shippings=shippingDao.findByOrder(order);
							if(shippings.size()<1){
								map.put("wuliu", null);
							}else{
								Shipping shipping = shippings.toArray( new Shipping[shippings.size()] )[0];
								 for(StockLog stockLog:shipping.getStockLogs()){
						            	if( stockLog.getState().equals("10") || stockLog.getState().equals("5")) {
						            		buffer = buffer.append(stockLog.getId()).append(",");
						            	}
						            }
						            if(buffer.length() > 0){
						            	map.put("stockId",buffer.deleteCharAt(buffer.length()-1));
						            }
								TrackingLog trackingLog=trackingLogDao.findByTrackingId(shipping.getTrackingNo());
								if(trackingLog == null ){
									map.put("wuliu", null);
								}else{
									map.put("wuliu", trackingLog.getLatestLogistics());
								}
								
							}
						}else{
							map.put("wuliu", null);
						}
						if(order.getStatus() == Order.Status.daiziti){
							Organization organization = organizationDao.find(order.getOrganization().getId());
							
							map.put("organization", organization.getName());
							map.put("organizationAddress", organization.getArea().getName()+organization.getAddress());
							map.put("organizationTel", organization.getTel());
							map.put("collectTime", order.getCollectTime());
						}
						if(map.size() > 0){
							list2.add(map);
						}
					}
					
				}
			}
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list2);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getShopDetailAndActivity(int shopInfoType,String shopId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		try {
			//SQL语句
			//shopInfoType 信息类别【1：门店介绍，2：门店活动,  3：店员介绍】
			if(shopInfoType==1){
//				sql="SELECT a.f_id shopId, a.f_name shopName, a.f_image shopImagePath,a.f_praise_count shopPraiseCount, a.f_address shopAddress, b.f_full_name shopAreaFullName, a.f_tel shopTel, " +
//						"a.f_latitude shopXCoordinate, a.f_longitude shopYCoordinate, a.f_intro shopIntroduce,case when  c.f_content IS NULL  THEN '' else c.f_content END shopComment,"
//						+ " a.f_opening shopOpening FROM t_organization a LEFT JOIN " +
//						"t_area b ON a.f_area = b.f_orders  LEFT JOIN t_comment c ON c.f_organization = a.f_id  WHERE a.f_id = :shopId GROUP BY a.f_id";
				sql="SELECT a.f_id shopId, a.f_name shopName, a.f_image shopImagePath,a.f_praise_count shopPraiseCount, a.f_address shopAddress, b.f_full_name shopAreaFullName, a.f_tel shopTel, " +
						"a.f_latitude shopXCoordinate, a.f_longitude shopYCoordinate, a.f_intro shopIntroduce,"
						+ " a.f_opening shopOpening FROM t_organization a LEFT JOIN " +
						"t_area b ON a.f_area = b.f_orders  LEFT JOIN t_comment c ON c.f_organization = a.f_id  WHERE a.f_id = :shopId GROUP BY b.f_full_name";
			}else if(shopInfoType==2){
				sql="SELECT a.f_id shopActivityId, a.f_title shopActivityTitle, a.f_content shopActivityContent, a.f_logo shopActivityIogo, Date(a.f_begin_date) shopActivityBeginDate, " +
						"Date(a.f_end_date) shopActivityEndDate FROM t_activity a LEFT JOIN t_organization b ON a.f_active_organization = b.f_id WHERE b.f_id = :shopId  ";
			}else if(shopInfoType==3){
				sql="SELECT a.f_id clerksId, a.f_image clerksPhonePath, a.f_office clerksOffice, a.f_name clerksName, case when  a.f_description IS NULL  THEN '' else a.f_description "
						+ "END clerksIntroduce, a.f_praise_count clerksPraiseCount, case when  c.f_content IS NULL  THEN '' else c.f_content END clerksComment,CASE WHEN date_format(c.f_create_date,'%Y-%m-%d %T')"
						+ " IS NULL THEN '' ELSE date_format(c.f_create_date,'%Y-%m-%d %T') END createDate,CASE WHEN d.f_nickname IS NULL THEN '' ELSE d.f_nickname END nickname,case when  "
						+ "c.f_score IS NULL  THEN 0 else c.f_score END  clerksScore FROM t_admin a LEFT JOIN t_organization b ON a.f_organization = b.f_id LEFT "
						+ "JOIN (SELECT a.f_admin,a.f_member,a.f_content,a.f_create_date,a.f_score FROM t_comment a  WHERE a.f_admin IS NOT NULL GROUP BY a.f_admin ) c ON c.f_admin = a.f_id LEFT JOIN t_member d ON c.f_member =  d.f_id WHERE a.f_organization = :shopId ORDER BY c.f_create_date DESC";
			}
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("shopId", shopId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		if(shopInfoType==1){
			mapResult.put(UnivParameter.DATA, list.get(0));
		}else{
			mapResult.put(UnivParameter.DATA, list);
		}
		return mapResult;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> snapJudgment(Long grabSeckillId,
			int robGoodsType, Long userId,String startTime) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list=null;
		String snapJudgment="0";
		if( robGoodsType ==2 || robGoodsType ==3 || robGoodsType ==4 ){
			robGoodsType=2;
		}else if( robGoodsType == 5 ){
			robGoodsType=3;
		}else{
			robGoodsType=1;
		}
		try {
			if(format.parse(startTime).after(new Date())){
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.DATA, snapJudgment);
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"我们正为您准备惊喜中,请稍后再来!");
				return mapResult;
			}
			//判断该编号会员是否已经进行抢购该商品
			String sql="SELECT b.f_goods_gross kucun,b.f_state sta,count(*) FROM t_grab_seckill_log a LEFT JOIN t_grab_seckill b " +
					" ON a.f_grab_seckills = b.f_id WHERE a.f_member=:userId AND b.f_id=:grabSeckillId AND b.f_goods_type=:robGoodsType";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			
			query.setParameter("userId", userId);
			query.setParameter("grabSeckillId", grabSeckillId);
			query.setParameter("robGoodsType", robGoodsType);
			//结果集格式化
			list= query.getResultList();
			for (int i = 0; i < list.size(); i++) {
				Object[] row = (Object[]) list.get(i);
				if(row[0]==null)
				{
					row[0]=0;
				}
				if(row[1]==null)
				{
					row[1]=0;
				}
				int cukun= (int)row[0];
				int state = (int)row[1]; 
				int num = Integer.parseInt(row[2].toString());
				if(num<=0 && state==1 && cukun>0){
					snapJudgment="1";
					//存放正确的返回参数CODE--1
					mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
				}else{
					if(num > 0){
						//存放错误的返回参数CODE--0
						mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
						mapResult.put(UnivParameter.ERRORMESSAGE,"手气真棒,本轮您已抢到,请稍后再来!");
					}else if( cukun <= 0 ){
						//存放错误的返回参数CODE--0
						mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
						mapResult.put(UnivParameter.ERRORMESSAGE,"我们正为您准备惊喜中,请稍后再来!");
					}else{
						//存放错误的返回参数CODE--0
						mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
						mapResult.put(UnivParameter.ERRORMESSAGE,"就差一点点,请您再接再厉!惊喜还在后面!");
					}
				}
			}
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, snapJudgment);
		return mapResult;
	}

	
	//TODO
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getRushGoodsRecommend(String grabSeckillId,String robGoodsId,
			int robGoodsType,String userId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> listBonus=new ArrayList<Object>();
		List<Object> listCoupon=new ArrayList<Object>();
		String sqlBonus=null;
		String sqlCoupon=null;
		try {
			sqlBonus="SELECT a.f_id robGoodsId, a.f_bonus_type + 1 robGoodsType, a.f_title robGoodsName, a.f_image robGoodsImagePath, a.f_content robGoodInfo, " +
					"b.f_credits robPacketCredits, a.f_organization packetOrganization, a.f_packet_goods robPacketName, c.f_nickname robPacketMemberName FROM t_bonus a" +
					" LEFT JOIN t_bonus_log b ON a.f_id = b.f_bonus LEFT JOIN t_member c ON c.f_id = a.f_member LEFT JOIN t_grab_seckill_log d ON d.f_goods = b.f_id" +
					" WHERE b.f_id = :robGoodsId  AND d.f_goods_type = 2  AND d.f_member =:userId AND d.f_grab_seckills=  :grabSeckillId ";
			sqlCoupon="SELECT b.f_id robGoodsId, a.f_goods_type + 2 robGoodsType, b.f_name robGoodsName, b.f_coupon_type robCouponType, b.f_introduction robCouponUseRange," +
					" b.f_count_price robCouponPrice, DATE_FORMAT(b.f_begin_date, '%Y.%m.%d') robCouponBeginTime, DATE_FORMAT(b.f_end_date, '%Y.%m.%d') robCouponEndTime " +
					"FROM t_grab_seckill_log a LEFT JOIN t_coupon b ON a.f_goods = b.f_id WHERE a.f_goods_type = 3 AND b.f_id = :robGoodsId AND a.f_member = :userId  AND" +
					" a.f_grab_seckills=  :grabSeckillId  ";
			 if(robGoodsType ==2||robGoodsType ==3||robGoodsType ==4){
				Query queryBonus = entityManager.createNativeQuery(sqlBonus);
				queryBonus.setParameter("robGoodsId", robGoodsId);
				queryBonus.setParameter("grabSeckillId", grabSeckillId);
				queryBonus.setParameter("userId", userId);
				queryBonus.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listBonus= queryBonus.getResultList();
				if(listBonus.size()>0){
					//存放最终的执行结果
					mapResult.put(UnivParameter.DATA, listBonus.get(0));
				}else{
					//存放最终的执行结果
					mapResult.put(UnivParameter.DATA," ");
				}
			}else if(robGoodsType ==5){
				Query queryCoupon = entityManager.createNativeQuery(sqlCoupon);
				queryCoupon.setParameter("robGoodsId", robGoodsId);
				queryCoupon.setParameter("grabSeckillId", grabSeckillId);
				queryCoupon.setParameter("userId", userId);
				queryCoupon.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listCoupon= queryCoupon.getResultList();
				if(listCoupon.size()>0){
					//存放最终的执行结果
					mapResult.put(UnivParameter.DATA, listCoupon.get(0));
				}else{
					//存放最终的执行结果
					mapResult.put(UnivParameter.DATA," ");
				}
			}
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

	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getShoppingRush(String userId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
		//SQL语句
		String sql="SELECT a.f_id robId, a.f_goods goodsId, a.f_goods_type goodsType, DAte(a.f_datetime) robDate, b.f_image goodsImagePath, b.f_title goodsName, b.f_price robPrice," +
				" c.f_status robOrderState, CASE WHEN a.f_goods_type = 3 THEN d.f_count_price ELSE 0 END robCouponPrice FROM t_grab_seckill_log a LEFT JOIN t_grab_seckill b ON " +
				"a.f_goods_type = b.f_goods_type LEFT JOIN t_order c ON c.f_id = a.t_order LEFT JOIN t_coupon d ON a.f_goods = d.f_id AND a.f_goods = b.f_goods WHERE a.f_member = :userId  ";
		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("userId", userId);
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list= query.getResultList();
		//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getBonusDetail(String packetId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT a.f_bonus_type packetType, DATE(b.acquire_time) creatDate, a.f_title pcaketName, a.f_content packetInfo, b.f_credits packetCredit, " +
					"a.f_organization packetOrganization, a.f_packet_goods packetGoods, b.f_member redEnvelopesId, c.f_nickname redEnvelopesNickname FROM t_bonus a" +
					" LEFT JOIN t_bonus_log b ON a.f_id = b.f_bonus LEFT JOIN t_member c ON c.f_id = a.f_member WHERE  b.f_id= :packetId   ";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("packetId", packetId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
}

	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getGoodsDetail(String goodsId,String productSn) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> listPromotion=new ArrayList<Object>();
		List<Object> listmanzeng=new ArrayList<Object>();
		List<Object> listmaizeng=new ArrayList<Object>();
		List<Object> listdanpin=new ArrayList<Object>();
		List<Object> listmanjian=new ArrayList<Object>();
		List<Object> li =new ArrayList<Object>();
		List<BindProductInfoVO> vos = new ArrayList<BindProductInfoVO>();
		Map<String,Object> result= new HashMap<String, Object>();
		Product product=null;
		try {
			  if(goodsId == null || goodsId.isEmpty()){
				  if( productSn == null || productSn.isEmpty() ){
					  	mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
						mapResult.put(UnivParameter.ERRORMESSAGE, "商品不存在");
						return mapResult;
				  }else{
					  product=productDao.findBySn(productSn);
				  }
			  }else{
				  product=productDao.find(Long.parseLong(goodsId));
			  }
			if(null != product){
				goodsId = product.getId().toString();
				result.put("goodsId", product.getId());
				result.put("goodsName", product.getName());
				result.put("goodsPrice", product.getPrice());
				result.put("goodsMarketPrice", product.getMarketPrice());
				result.put("goodsImage", product.getProductImages());
				result.put("goodsSubhead",product.getCaption());
				result.put("goodsDefaultColor",product.getSpecificationItems());
				result.put("buyType",product.getOperator().getId());
				result.put("buyName",product.getOperator().getName());
				result.put("stock",product.getAvailableStock());
				
				if(product.getIsPreOrder() && product.getPreOrderTime().after(new Date())){
					result.put("isPreOrder", true);
					result.put("earnestMoney", product.getPreOrderPrice());
				}else{
					result.put("isPreOrder", false);
					result.put("earnestMoney", 0);
				}
				
				//根据 Model 查找到 和该商品匹配的商品
				ProductModel productModel = product.getProductModel();
				List<Product> products = productDao.listProductsByModel(productModel);
				
				//从entity中抽出相关VO数据
				List<ProductSpecificationVO> productSpecificationVOs = new ArrayList<ProductSpecificationVO>();
				ProductSpecificationVO tempProductSpecificationVO = null;
				
				//循环生成 VO 数据
				for(Product tempproduct : products ){
					tempProductSpecificationVO = new ProductSpecificationVO();
					tempProductSpecificationVO.setId(tempproduct.getId());
					tempProductSpecificationVO.setSn(tempproduct.getSn());
					tempProductSpecificationVO.setOperatorId( tempproduct.getOperator().getId() );
					tempProductSpecificationVO.setOperatorName( tempproduct.getOperator().getName() );
					tempProductSpecificationVO.setSpecifications(tempproduct.getSpecifications());
					tempProductSpecificationVO.setSpecificationValueIds(tempproduct.getSpecificationValueIds());
					tempProductSpecificationVO.setOutOfStock(tempproduct.getIsOutOfStock());
					productSpecificationVOs.add(tempProductSpecificationVO);
				}
				result.put("productSpecification" ,  productSpecificationVOs);
				
				//捆绑商品
				String sqlPromotion="SELECT a.f_product_1 product1,a.f_product_2 product2,a.f_product_3 product3,a.f_product_4 product4,a.f_product_5 product5,a.f_price_1 price1," +
						"a.f_price_2 price2,a.f_price_3 price3,a.f_price_4 price4,a.f_price_5 price5 ,b.f_title title,b.f_name name FROM t_promotion_bind a LEFT JOIN t_promotion b " +
						"ON a.f_promotions=b.f_id WHERE a.f_promotions = " +
						"(SELECT a.f_id promotionId FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN " +
						"( SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion" +
						" b ON a.f_id = b.f_product WHERE a.f_id = :goodsId ) AND a.f_promotion_type = 5 AND a.f_end_date>NOW() AND f_begin_date < NOW());";
				//执行sql语句
				Query queryPromotion = entityManager.createNativeQuery(sqlPromotion);
				queryPromotion.setParameter("goodsId", goodsId);
				//结果集格式化
				queryPromotion.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listPromotion= queryPromotion.getResultList();
				
				if(listPromotion.size()>0){
					Map<String,Object> m1 = (Map<String, Object>) listPromotion.get(0);
					String title=String.valueOf(m1.get("title"));
					String name=String.valueOf(m1.get("name"));
					String product1=String.valueOf(m1.get("product1"));
					String product2=String.valueOf(m1.get("product2"));
					String product3=String.valueOf(m1.get("product3"));
					String product4=String.valueOf(m1.get("product4"));
					String product5=String.valueOf(m1.get("product5"));
					
					BigDecimal price1=new BigDecimal(String.valueOf(m1.get("price1") != null ? m1.get("price1") : 0));
					BigDecimal price2=new BigDecimal(String.valueOf(m1.get("price2") != null ? m1.get("price2") : 0));
					BigDecimal price3=new BigDecimal(String.valueOf(m1.get("price3") != null ? m1.get("price3") : 0));
					BigDecimal price4=new BigDecimal(String.valueOf(m1.get("price4") != null ? m1.get("price3") : 0));
					BigDecimal price5=new BigDecimal(String.valueOf(m1.get("price5") != null ? m1.get("price5") : 0));
					
					String img1=null;
					String img2=null;
					String img3=null;
					String img4=null;
					String img5=null;
					
					BigDecimal preferentialPrice1=null;
					BigDecimal preferentialPrice2=null;
					BigDecimal preferentialPrice3=null;
					BigDecimal preferentialPrice4=null;
					BigDecimal preferentialPrice5=null;
					
					vos.add(new BindProductInfoVO(product1, price1, img1 ,title , name , preferentialPrice1 ,null));
					vos.add(new BindProductInfoVO(product2, price2, img2 ,title , name , preferentialPrice2 ,null));
					vos.add(new BindProductInfoVO(product3, price3, img3 ,title , name , preferentialPrice3 ,null));
					vos.add(new BindProductInfoVO(product4, price4, img4 ,title , name , preferentialPrice4 ,null));
					vos.add(new BindProductInfoVO(product5, price5, img5 ,title , name , preferentialPrice5 ,null));
					
					String sq="SELECT a.f_id id,a.f_image img,a.f_name name,a.f_price price FROM t_product a WHERE a.f_id in (:product1,:product2,:product3,:product4,:product5)";
					//执行sql语句
					Query qu = entityManager.createNativeQuery(sq);
					qu.setParameter("product1", product1);
					qu.setParameter("product2", product2);
					qu.setParameter("product3", product3);
					qu.setParameter("product4", product4);
					qu.setParameter("product5", product5);
					//结果集格式化
					qu.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					li= qu.getResultList();
					
					for( Object obj : li ){
						Map<String,Object> m2 = (Map<String, Object>) obj;
						for (int i = 0; i < vos.size(); i++) {
							if( m2.get("id").toString().equals( vos.get(i).getProductId().toString())){
								vos.get(i).setProductName( String.valueOf(m2.get("name")) );
								vos.get(i).setImg( String.valueOf(m2.get("img")) );
								vos.get(i).setPreferentialPrice(new BigDecimal(String.valueOf(m2.get("price"))).subtract(vos.get(i).getPrice()));
									break;
							}
						}
					}
					
					List<BindProductInfoVO> tempList = new ArrayList<BindProductInfoVO>();
					for (int i = 0; i < vos.size(); i++) {
						if(vos.get(i).getProductId()==null || vos.get(i).getProductId().equals("null")){
							tempList.add(vos.get(i));
						}
					}
					vos.removeAll( tempList );
					result.put("bind", vos);
				}
				
				//满赠
				String sqlmanzeng="SELECT b.f_id id, b.f_product_images img ,c.f_title title,c.f_name titleName,c.f_minimum_price minimumPrice FROM t_promotion_gift a  LEFT JOIN t_product b ON " +
						"a.f_gifts=b.f_id LEFT JOIN t_promotion c ON a.f_promotions=c.f_id WHERE a.f_promotions = ( " +
						"SELECT a.f_id promotionId FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN ( " +
						"SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId ) AND a.f_promotion_type = 3 AND a.f_end_date > NOW() and a.f_begin_date < NOW() );";
				//执行sql语句
				Query querymanzeng = entityManager.createNativeQuery(sqlmanzeng);
				querymanzeng.setParameter("goodsId", goodsId);
				//结果集格式化
				querymanzeng.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listmanzeng= querymanzeng.getResultList();
				
				if(listmanzeng.size()>0){
					result.put("manzeng",listmanzeng.get(0));
				}
				
				//买赠
				String sqlmaizeng="SELECT b.f_id id, b.f_product_images img ,c.f_title title,c.f_name titleName,c.f_minimum_quantity minimumQuantity FROM t_promotion_gift a LEFT JOIN t_promotion c ON a.f_promotions=c.f_id  LEFT JOIN t_product b ON a.f_gifts=b.f_id WHERE a.f_promotions = ( " +
						"SELECT a.f_id promotionId FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN ( " +
						"SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId ) AND a.f_promotion_type = 4 AND a.f_end_date > NOW() and a.f_begin_date < NOW() );";
				//执行sql语句
				Query querymaizeng = entityManager.createNativeQuery(sqlmaizeng);
				querymaizeng.setParameter("goodsId", goodsId);
				//结果集格式化
				querymaizeng.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listmaizeng= querymaizeng.getResultList();
				
				if(listmaizeng.size()>0){
					result.put("maizeng",listmaizeng);
				}
				
				//单品促销
				String sqldanpin="SELECT a.f_id promotionId,a.f_name titleName, a.f_title title FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN (" +
						"SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId AND a.f_promotion_type = 1 AND a.f_end_date > NOW() and a.f_begin_date < NOW() )";
				//执行sql语句
				Query querydanpin = entityManager.createNativeQuery(sqldanpin);
				querydanpin.setParameter("goodsId", goodsId);
				//结果集格式化
				querydanpin.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listdanpin= querydanpin.getResultList();
				
				if(listdanpin.size()>0){
					result.put("danpin",listdanpin.get(0));
				}
				
				//满减
				String sqlmanjian="SELECT a.f_id id,a.f_name titleName, a.f_title title FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN (" +
						"SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId AND a.f_promotion_type = 2 AND a.f_end_date > NOW() and a.f_begin_date < NOW())";
				//执行sql语句
				Query querymanjian = entityManager.createNativeQuery(sqlmanjian);
				querymanjian.setParameter("goodsId", goodsId);
				//结果集格式化
				querymanjian.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listmanjian= querymanjian.getResultList();
				
				if(listmanjian.size()>0){
					result.put("manjian",listmanjian.get(0));
				}
			}
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
//			mapResult.put(UnivParameter.DATA, resultLists);
		mapResult.put(UnivParameter.DATA, result);
		return mapResult;
	}

	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getGoodsPromotionDetail(String goodsId,
			int promotionType) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> listGoods=new ArrayList<Object>();
		List<Object> listBonus=new ArrayList<Object>();
		List<Object> listCoupon=new ArrayList<Object>();
		String sqlGoods=null;
		String sqlBonus=null;
		String sqlCoupon=null;
		try {
			//SQL语句
			//1：满送（满5000赠Iphone）
			sqlGoods="SELECT d.promotionId promotionId, d.promotionName promotionName, d.promotionGiftId promotionGiftId, a.f_image promotionGiftImagePath," +
					"a.f_name promotionGiftName, a.f_market_price promotionGiftPrice FROM t_product a, ( SELECT c.f_id promotionId, c.f_name promotionName, d.f_gifts promotionGiftId " +
					"FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product LEFT JOIN t_promotion c ON c.f_id = b.f_promotions LEFT JOIN t_promotion_gift d ON " +
					"d.f_promotions = c.f_id WHERE a.f_id = :goodsId  AND c.f_id = 3 ) d WHERE a.f_id = d.promotionGiftId ";
			//2：买赠（买商品送Ipone）
			sqlBonus="SELECT d.promotionId promotionId, d.promotionName promotionName, d.promotionGiftId promotionGiftId, a.f_image promotionGiftImagePath, " +
					"a.f_name promotionGiftName, a.f_market_price promotionGiftPrice FROM t_product a, ( SELECT c.f_id promotionId, c.f_name promotionName, d.f_gifts promotionGiftId " +
					"FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product LEFT JOIN t_promotion c ON c.f_id = b.f_promotions LEFT JOIN t_promotion_gift d ON " +
					"d.f_promotions = c.f_id WHERE a.f_id = :goodsId AND c.f_id = 4 ) d WHERE a.f_id = d.promotionGiftId  ";
			//3：组合（几个商品组合在一起卖）
			sqlCoupon="  ";
			//执行sql语句
			//促销类型【1：满送（满5000赠Iphone），2：买赠（买商品送Ipone），3：组合（几个商品组合在一起卖）
			if(promotionType==1){
				Query queryGoods = entityManager.createNativeQuery(sqlGoods);
			queryGoods.setParameter("goodsId", goodsId);
				listGoods= queryGoods.getResultList();
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, listGoods);
			}else if(promotionType ==2){
				Query queryBonus = entityManager.createNativeQuery(sqlBonus);
				queryBonus.setParameter("goodsId", goodsId);
				queryBonus.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listBonus= queryBonus.getResultList();
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, listBonus);
			}else if(promotionType ==3){
				Query queryCoupon = entityManager.createNativeQuery(sqlCoupon);
				queryCoupon.setParameter("goodsId", goodsId);
				queryCoupon.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				listCoupon= queryCoupon.getResultList();
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, listCoupon);
			}
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override

	public Map<String, Object> idgroup(Long id,String specification,int operator) {
		Map<String,Object> mapResult = new HashMap<String,Object>();

		List<Object> list= new  ArrayList<Object>();
		try {
			String sql="SELECT a.f_id id,a.f_stock stock FROM t_product a WHERE a.f_product_model =:id AND a.f_specification_values=:specification and a.f_operator =:operator";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			
			query.setParameter("id", id);
			query.setParameter("specification", specification);
			query.setParameter("operator", operator);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			if(list.size()>0){
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, list.get(0));
			}else{
				//存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.DATA,new HashMap());
			}
			
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> setDefaultAddress(String userId, String addressId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list=null;
		try {
			String sql="SELECT * FROM t_receiver a WHERE a.f_member =:userId AND a.f_is_default = 1";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			
			query.setParameter("userId", userId);
			//结果集格式化
			list= query.getResultList();
			//判断是否存在默认地址
			if(list.size()>0){
				//存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"已存在");
			}else{
				Receiver receiver=receiverService.find(Long.parseLong(addressId));
				receiver.setIsDefault(true);
				receiverService.update(receiver);
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			}
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getBind(Long productId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> listPromotion=new ArrayList<Object>();
		List<CartItemBindProductValue> vos = new ArrayList<CartItemBindProductValue>();
		try {
			//捆绑商品
			String sqlPromotion="SELECT IFNULL(a.f_product_1, 0) product1,IFNULL(a.f_product_2, 0) product2,IFNULL(a.f_product_3, 0) product3,IFNULL(a.f_product_4, 0) product4," +
					"IFNULL(a.f_product_5, 0) product5,IFNULL(a.f_price_1, 0) price1,IFNULL(a.f_price_2, 0) price2,IFNULL(a.f_price_3, 0) price3,IFNULL(a.f_price_4, 0) price4," +
					"IFNULL(a.f_price_5, 0) price5 FROM t_promotion_bind a  WHERE a.f_promotions = " +
					"(SELECT a.f_id promotionId FROM t_promotion a " +
					"LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN ( SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion" +
					" b ON a.f_id = b.f_product WHERE a.f_id in ( :goodsId ) ) AND a.f_promotion_type = 5 AND a.f_end_date>NOW());";
			//执行sql语句
			Query queryPromotion = entityManager.createNativeQuery(sqlPromotion);
			queryPromotion.setParameter("goodsId", productId);
			//结果集格式化
			queryPromotion.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listPromotion= queryPromotion.getResultList();
			
			if(listPromotion.size()>0){
				Map<String,Object> m1 = (Map<String, Object>) listPromotion.get(0);
				
				Long product1=Long.parseLong(String.valueOf(m1.get("product1")));
				Long product2=Long.parseLong(String.valueOf(m1.get("product2")));
				Long product3=Long.parseLong(String.valueOf(m1.get("product3")));
				Long product4=Long.parseLong(String.valueOf(m1.get("product4")));
				Long product5=Long.parseLong(String.valueOf(m1.get("product5")));
				
				BigDecimal price1= new BigDecimal(String.valueOf((m1.get("price1"))));
				BigDecimal price2=new BigDecimal(String.valueOf((m1.get("price2"))));
				BigDecimal price3=new BigDecimal(String.valueOf((m1.get("price3"))));
				BigDecimal price4=new BigDecimal(String.valueOf((m1.get("price4"))));
				BigDecimal price5=new BigDecimal(String.valueOf((m1.get("price5"))));
				
				
				vos.add(new CartItemBindProductValue(product1, price1));
				vos.add(new CartItemBindProductValue(product2, price2));
				vos.add(new CartItemBindProductValue(product3, price3));
				vos.add(new CartItemBindProductValue(product4, price4));
				vos.add(new CartItemBindProductValue(product5, price5));
				
				List<CartItemBindProductValue> tempList = new ArrayList<CartItemBindProductValue>();
				for (int i = 0; i < vos.size(); i++) {
					if(vos.get(i).getId()==null || vos.get(i).getId().equals("null") || vos.get(i).getId()==0 ){
						tempList.add(vos.get(i));
					}
				}
				
				vos.removeAll( tempList );
			}
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, vos);
		return mapResult;
	}

	@Override
	public List<CartItem> getCartItem(Cart cart, Product product) {
		if( cart == null || product == null ){
			return null;
		}
		String jpql = "select cartItem from CartItem cartItem where cartItem.cart = :cart and cartItem.product = :product";
		return entityManager.createQuery(jpql, CartItem.class).setParameter("cart", cart).setParameter("product", product).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getOrdersDetail(String orderSn) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> fahuomap = new HashMap<String,Object>();
		Map<String,Object> fukuanmap = new HashMap<String,Object>();
		List<Object> listPro=new ArrayList<Object>();
		List<Object> listItem=new ArrayList<Object>();
		try {
			//订单基本信息
			String sql="SELECT a.f_expire expire, a.f_sn sn,a.f_deposit deposit,date_format(a.f_create_date,'%Y-%m-%d %T') createDate,date_format(a.f_complete_date,'%Y-%m-%d %T') completeDate,c.f_name organization,d.f_name organizationArea, c.f_address organizationAddress,a.f_collect_time collectTime,"
					+ "a.f_status statu,a.f_consignee consignee,a.f_phone phone,a.f_address address ,a.f_area_name areaName ,c.f_tel organizationTel,a.f_amount_paid-a.f_godmoney_paid shifukuan, " +
					"a.f_payment_method paymentMethod,a.f_type orderType,a.f_shipping_method shippingMethod ,IFNULL(a.f_invoice_title, ' ') as invoiceTitle,f.f_price price,a.f_amount amount,a.f_godmoney_paid godmoneyPaid ," +
					"a.f_freight freight FROM t_order a LEFT JOIN t_member b ON a.f_member = b.f_id LEFT JOIN t_order_item f ON a.f_id = f.f_orders LEFT JOIN t_organization c ON c.f_id = a.f_organization LEFT JOIN t_area d ON d.f_id = c.f_area WHERE a.f_sn = :orderSn ";
			//执行sql语句
			Query query= entityManager.createNativeQuery(sql);
			query.setParameter("orderSn", orderSn);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listPro= query.getResultList();
			Order order = orderDao.findBySn(orderSn);
			
			if(order.getShippingMethod().getId() == 1){
				List<Shipping> shipping = shippingDao.findByOrder(order);
				
				if(shipping.size() >0 ){
					fahuomap.put("fahuoriqi", format.format(shipping.get(0).getCreateDate()));
				}else{
					fahuomap.put("fahuoriqi", null);
				}
			}else{
				fahuomap.put("fahuoriqi", null);
			}
			listPro.add(fahuomap);
			
			
			List<PaymentLog> paymentLogs = new ArrayList<PaymentLog>(order.getPaymentLogs());
			if( paymentLogs.size() > 0 ){
				for (PaymentLog paymentLog : paymentLogs) {
					if( paymentLog.getStatus() == PaymentLog.Status.success){
						fukuanmap.put("fukuanshijian", format.format(paymentLog.getCreateDate()));
					}else{
						fukuanmap.put("fukuanshijian", null);
					}
				}
			}else{
				fukuanmap.put("fukuanshijian", null);
			}
			
			listPro.add(fukuanmap);
			//订单项基本信息
			String sqlItem="SELECT b.f_is_review isReview,b.f_id id,b.f_quantity quantity ,b.f_thumbnail thumbnail,b.f_price price,c.f_name 'name',c.f_sn goodsSn,c.f_caption caption,e.f_number phoneNumber,e.f_price phonePrice,g.f_name contractName,h.f_name operatorName"
					+ " FROM t_order a LEFT JOIN t_order_item b ON a.f_id = b.f_orders LEFT JOIN t_product c ON b.f_product = c.f_id LEFT JOIN t_contract_phonenumber_userinfo d ON d.f_id = b.f_phonenum_info LEFT JOIN t_phone_number e ON d.f_phone_number = e.f_id "
					+ "LEFT JOIN t_contract_package f ON f.f_id = d.f_contract_item LEFT JOIN t_contract g ON g.f_id = f.f_contract LEFT JOIN t_operator h ON h.f_id = g.f_operator WHERE a.f_sn = :orderSn ";
			//执行sql语句
			Query queryItem= entityManager.createNativeQuery(sqlItem);
			queryItem.setParameter("orderSn", orderSn);
			//结果集格式化
			queryItem.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listItem= queryItem.getResultList();
			
			if(listPro.size()>0){
				map.put("pro", listPro);
			}
			if(listItem.size()>0){
				map.put("proItem", listItem);
			}
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, map);
		return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findFavorite(Long userId,Long productId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list= new  ArrayList<Object>();
		int istrue;
		String sql;
		try {
			if(productId != null){
				sql="SELECT * FROM t_member_favorite_products WHERE f_favorite_members = :userId  AND f_favorite_products = :productId";
			}else{
				sql="SELECT * FROM t_member_favorite_products WHERE f_favorite_members = :userId";
			}
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			
			query.setParameter("userId", userId);
			if(productId != null){
				query.setParameter("productId", productId);
			}
			
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			istrue=list.size();
			
		} catch (Exception e) {
			return Member.MAX_FAVORITE_COUNT;
		}
		return istrue;
	}

	@Override
	public Map<String, Object> addFavorite(Long userId, Long productId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		int i;
		try {
			//订单基本信息
			String sql="INSERT INTO t_member_favorite_products VALUES (:userId , :productId)";
			//执行sql语句
			Query query= entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			query.setParameter("productId", productId);
			i= query.executeUpdate();	
			
			if(i>0){
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			}else{
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			}
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

	@Override
	public Map<String, Object> dellFavorite(Long userId, Long[] productIds) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		int i;
		try {
			StringBuffer sb = new StringBuffer();
			for(int k = 0; k < productIds.length; k++){
				sb. append(productIds[k]);
			}

			String newStr = sb.toString();
			//订单基本信息
			String sql="DELETE FROM t_member_favorite_products WHERE f_favorite_members= :userId AND f_favorite_products in (:productIds)";
			//执行sql语句
			Query query= entityManager.createNativeQuery(sql);
			query.setParameter("userId", userId);
			query.setParameter("productIds", newStr);
			i= query.executeUpdate();	
			
			if(i>0){
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			}else{
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			}
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

	@Override
	public Map<String, Object> organizationList(List<Organization> list) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String, Object> map=null;
		try {
			List<Object> list2=new ArrayList<Object>();
			for (Organization organization : list) {
				map=new HashMap<String, Object>();
				map.put("id", organization.getId());
				map.put("name", organization.getName());
				map.put("address", organization.getAddress());
				map.put("opening", organization.getOpening());
				list2.add(map);
			}
			mapResult.put(UnivParameter.DATA, list2);
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}
	
	//TODO
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> grabSkillList(Long userId,int pageLoadType, int pageRowsCount, String createDate) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> listPro=new ArrayList<Object>();
		try {
			//订单基本信息
 			String sqlBeg="SELECT date_format(a.f_create_date,'%Y-%m-%d %T') date,a.f_goods_type type,CASE (a.f_goods_type) WHEN 1 THEN (d.f_name) "
					+ "WHEN 2 THEN (CASE (f.f_bonus_type) WHEN 1 THEN concat(e.f_credits,'神币已入账') WHEN 2 THEN concat(e.f_credits,'积分已入账') ELSE "
					+ "f.f_packet_goods END) ELSE (c.f_name) END AS name, CASE (a.f_goods_type) WHEN 1 THEN '商品' WHEN 2 THEN (CASE (f.f_bonus_type) "
					+ "WHEN 1 THEN '神币红包' WHEN 2 THEN '积分红包' ELSE '实物红包' END ) ELSE (CASE (c.f_coupon_type) WHEN 1 THEN '本平台优惠券' ELSE '第三方优惠券'"
					+ " END) END AS leixing,CASE (f.f_bonus_type) WHEN 1 THEN null WHEN 2 THEN NULL ELSE date_format(f.f_check_time,'%Y-%m-%d %T') END AS quhuoshijian,CASE (f.f_bonus_type) "
					+ "WHEN 1 THEN null WHEN 2 THEN NULL ELSE g.f_name END AS quhuomendian,CASE (a.f_goods_type) WHEN 1 THEN (d.f_id) WHEN 2 THEN (f.f_id) ELSE (c.f_id) END AS id FROM t_grab_seckill_log"
					+ " a LEFT JOIN t_coupon c ON a.f_goods = c.f_id LEFT JOIN t_product d ON d.f_id = a.f_goods LEFT JOIN t_bonus f ON a.f_goods = f.f_id "
					+ "LEFT JOIN t_bonus_log e ON e.f_bonus = f.f_id and e.f_member = a.f_member LEFT JOIN t_organization g ON f.f_organization = g.f_id WHERE a.f_type = '1' AND a.f_member = :userId";
			String sql=sqlBeg+" AND a.f_create_date <:createDate GROUP BY id,a.f_member order BY a.f_create_date desc  LIMIT 0,:pageRowsCount";
			//执行sql语句
			Query query= entityManager.createNativeQuery(sql);
			
			if(pageLoadType==0){
				query.setParameter("createDate", "9999");
			}else{
				query.setParameter("createDate", createDate);
			}
			query.setParameter("pageRowsCount", pageRowsCount);
			query.setParameter("userId", userId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listPro=query.getResultList();
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.DATA,listPro);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsSaleSupport(String goodsId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT t.f_sale_support saleSupport FROM t_product t WHERE t.f_id =:goodsId";
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> fengshenbang(int type) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list=null;
		List<Object> lists=null;
		Map<String, Object> map = new HashMap<String, Object>();
		String sqlend = null;
		try {
			String sql="SELECT SUM(a.f_credit) point,b.f_id userId,b.f_photo_path userPhotoPath,b.f_nickname userNickName FROM t_point_log a LEFT JOIN "
					+ "t_member b ON a.f_member = b.f_id ";
			switch (type) {
			//SQL语句
			case 1:
				sqlend="WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(a.f_create_date) GROUP BY a.f_member "
						+ "ORDER BY point DESC LIMIT 0,7";
				break;
			case 2:
				sqlend="WHERE to_days(a.f_create_date)=to_days(now()) GROUP BY a.f_member "
						+ "ORDER BY point DESC LIMIT 0,1";
				break;
			default:
				break;
			}
			
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql+sqlend);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			switch (type) {
			case 2:
				if(list.size() > 0){
					Map<String,Object> m = (Map<String, Object>) list.get(0);
					Object userIdObject =m.get("userId");
					String userId=String.valueOf(userIdObject);
					String sqls="SELECT a.f_id,f_image FROM t_review a  WHERE a.f_member =:userId ORDER BY a.f_create_date DESC LIMIT 0,1";
					//执行sql语句
					Query querys = entityManager.createNativeQuery(sqls);
					querys.setParameter("userId", userId);
					//结果集格式化
					querys.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					lists= querys.getResultList();
					map.put("img", lists);
					list.add(map);
				}else{
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.DATA,"没有数据");
				}
				
				break;

			default:
				break;
			}
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> reviewList(int type, int pageLoadType,int pageRowsCount, String id,String time){
        //初始化map集合
        Map<String,Object> mapResult = new HashMap<String,Object>();
        List<Object> list=null;
        String sqlBeg = null;
        try {
        	switch (type) {
			case 1:
				sqlBeg = "SELECT a.f_content content,date_format(a.f_create_date,'%Y-%m-%d %T') date,b.f_nickname phone from t_comment a LEFT JOIN t_member b ON a.f_member =  b.f_id WHERE a.f_organization =:id";
				break;
			case 2:
				sqlBeg = "SELECT a.f_content content,date_format(a.f_create_date,'%Y-%m-%d %T') date,b.f_nickname phone from t_comment a LEFT JOIN t_member b ON a.f_member =  b.f_id WHERE a.f_admin =:id";
				break;
			default:
				break;
			}
            //SQL语句
        	String sql=sqlBeg+" AND a.f_create_date <:time GROUP BY a.f_create_date desc  LIMIT 0,:pageRowsCount";

            //执行sql语句
            Query query = entityManager.createNativeQuery(sql);
            
            if(pageLoadType==0){
				query.setParameter("time", "9999");
			}else{
				query.setParameter("time", time);
			}
            query.setParameter("pageRowsCount", pageRowsCount);
			query.setParameter("id", id);
            //结果集格式化
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            list= query.getResultList();

            //存放正确的返回参数CODE--1
            mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
        } catch (Exception e) {
            //存放错误的返回参数CODE--0
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
            return mapResult;
        }

        //存放最终的执行结果
        mapResult.put(UnivParameter.DATA, list);
        return mapResult;
    }
    
    
    /**
     * getShopComment 类型 1门店 2店员
     * @param commentType 门店或者店员id
     * @param pageLoadType 分页方向【0：刷新，1：加载更多】
     * @param pageRowsCount 每页加载数据条数
     * @param shopId
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getShopComment(int commentType,
			int pageLoadType, int pageRowsCount, String shopId,String commentId) {
		 //初始化map集合
        Map<String,Object> mapResult = new HashMap<String,Object>();
        if(commentType<1||commentType>2){
        	mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "查询类型错误，无法查询");
            return mapResult;
        }
        if(shopId==null){
        	mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "缺少查询编号");
            return mapResult;
        }
        List<Object> list=null;
        String sqlBeg = null;
        String sql=null;
        try {
        	switch (commentType) {
			case 1:
				sqlBeg = "SELECT a.f_content commentContent, a.f_id commentId, a.f_score commentScore FROM t_comment a LEFT JOIN "
						+ "t_organization b ON a.f_organization = b.f_id WHERE b.f_id = :shopId  ";
				break;
			case 2:
				sqlBeg = "SELECT a.f_content commentContent, a.f_id commentId, a.f_score commentScore FROM t_comment a LEFT JOIN "
						+ "t_admin b ON a.f_admin = b.f_id WHERE b.f_id = :shopId  ";
				break;
			default:
				break;
			}
            //SQL语句
        	if(pageLoadType==0){
        		sql=sqlBeg+" AND a.f_id >:commentId GROUP BY a.f_id desc  LIMIT 0,:pageRowsCount";
        	}else{
        		sql=sqlBeg+" AND a.f_id <:commentId GROUP BY a.f_id desc  LIMIT 0,:pageRowsCount";
        	}
            //执行sql语句
            Query query = entityManager.createNativeQuery(sql);
            
            if(pageLoadType==0){
				query.setParameter("commentId", "0");
			}else{
				query.setParameter("commentId", commentId);
			}
            query.setParameter("pageRowsCount", pageRowsCount);
			query.setParameter("shopId", shopId);
            //结果集格式化
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            list= query.getResultList();

            //存放正确的返回参数CODE--1
            mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
        } catch (Exception e) {
            //存放错误的返回参数CODE--0
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
            return mapResult;
        }
        //存放最终的执行结果
        mapResult.put(UnivParameter.DATA, list);
        return mapResult;
    }

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> aftersales(Long memberId, int pageLoadType, int pageRowsCount, String createDate) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		try {
			//SQL语句
			String sql="SELECT a.f_id returnOrderId,d.f_sn orderSn,a.f_store_shipping_method shippingMethod, a.f_sn returnOrderSn,date_format(a.f_create_date,'%Y-%m-%d %T') createDate,a.f_repair_price repairPrice,a.f_type type, c.f_sn productSn, c.f_image imgs, b.f_returned_quantity quantity, c.f_name productName,"
					+ "a.f_create_date returnOrderDate, a.f_status state FROM t_return_order a LEFT JOIN t_return_order_item b ON a.f_id = b.f_return_order LEFT JOIN t_order d ON d.f_id = a.f_order "
					+ "LEFT JOIN t_product c ON b.f_sn = c.f_sn WHERE a.f_member = :memberId AND a.f_create_date <:time order BY a.f_create_date desc  LIMIT 0,:pageRowsCount";
			Query query = entityManager.createNativeQuery(sql);
			//执行sql语句
			if(pageLoadType==0){
				query.setParameter("time", "9999");
			}else{
				query.setParameter("time", createDate);
			}
            query.setParameter("pageRowsCount", pageRowsCount);
			
			query.setParameter("memberId", memberId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
			return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> sql(Long id,String sql) {
		Query query = entityManager.createNativeQuery(sql);
		if( id != null){
			query.setParameter("goodsId", id);
		}
		
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Object> list= query.getResultList();
		return list;
	}

	@Override
	public Map<String, Object> findPromotionByProduct(int i,Long id) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		String sql = null;
		try {
			switch (i) {
			case 1:
				//SQL语句 惠
				sql="SELECT COUNT(*) count FROM t_promotion a LEFT JOIN t_product_promotion b ON a.f_id = b.f_promotions WHERE"
						+ " b.f_product = :id AND a.f_promotion_type in (1,2) AND a.f_end_date > NOW() AND a.f_begin_date < NOW()";
				break;
			case 2:
				//SQL语句 赠
				sql="SELECT COUNT(*) count FROM t_promotion a LEFT JOIN t_product_promotion b ON a.f_id = b.f_promotions WHERE"
						+ " b.f_product = :id AND a.f_promotion_type in (3,4) AND a.f_end_date > NOW() AND a.f_begin_date < NOW()";
				break;
			case 3:
				//SQL语句 促
				sql="SELECT COUNT(*) count FROM t_promotion a LEFT JOIN t_product_promotion b ON a.f_id = b.f_promotions WHERE"
						+ " b.f_product = :id AND a.f_promotion_type = 5 AND a.f_end_date > NOW() AND a.f_begin_date < NOW()";
				break;
			default:
				break;
			}
			
			Query query = entityManager.createNativeQuery(sql);
			//执行sql语句
			query.setParameter("id", id);
			//结果集格式化
			Long  j= Long.parseLong(query.getSingleResult().toString());
			mapResult.put(UnivParameter.DATA, j );
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

}
