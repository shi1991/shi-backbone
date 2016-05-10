package com.puyuntech.ycmall.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.AdminDao;
import com.puyuntech.ycmall.dao.OrderDao;
import com.puyuntech.ycmall.dao.OrganizationDao;
import com.puyuntech.ycmall.dao.PosIndexDao;
import com.puyuntech.ycmall.dao.ReturnOrderDao;
import com.puyuntech.ycmall.entity.Admin;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ReturnOrder;
import com.puyuntech.ycmall.entity.ReturnOrderLog;
import com.puyuntech.ycmall.entity.Shipping;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.util.CommonParameter;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.vo.BindProductInfoVO;
import com.puyuntech.ycmall.vo.Result;

/**
 * 
 * DaoImpl - POS首页相关信息. Created on 2015-11-3 下午7:28:37
 * 
 * @author 南金豆
 */
@SuppressWarnings("rawtypes")
@Repository("posIndexDaoImpl")
public class PosIndexDaoImpl extends BaseDaoImpl implements
		PosIndexDao {

	@Resource(name = "organizationDaoImpl")
	private OrganizationDao organizationDao;
	
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;
	
	@Resource(name = "shippingDaoImpl")
	private ShippingDaoImpl shippingDao;
	
	@Resource(name = "returnOrderDaoImpl")
	private ReturnOrderDao returnOrderDao;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Resource(name = "adminDaoImpl")
	private AdminDao adminDaoImpl; 

	@SuppressWarnings("unchecked")
	public Map<String, Object> goodsSearch(String selectVaule, int selectType,
			int pageLoadType, int pageRowsCount, String goodsId,String shopId) {
		// 初始化map集合
		Map<String, Object> mapResult = new HashMap<String, Object>();
		// 每页加载数据条数(暂定默认10条)
		List<Result> list = null;
		String sql = null;
		String sqlBeg = null;
		try {
			// selectType 检索类型【1：商品名称（模糊查询），2：品牌检索】
			// 商品类型检索
			if (selectType == 1) {
				sqlBeg = "SELECT a.f_id goodsId, a.f_name goodsName, a.f_image goodsImagePath, a.f_price goodsPrice, count(b.f_id) goodsNum FROM t_product a LEFT JOIN t_stock_log " +
						"b ON b.f_product = a.f_id WHERE a.f_name LIKE :selectVaule AND b.f_organization =:shopId  AND  a.f_is_pre_order = 0 and b.f_state=1  AND a.f_is_list=1 AND a.f_is_marketable=1	AND a.type=1 ";
				// 向上加载
				if (pageLoadType == 1) {
					sql = sqlBeg
							+ " AND a.f_id <:goodsId GROUP BY a.f_id	ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount ";
				} else {
					sql = sqlBeg
							+ " AND a.f_id >:goodsId GROUP BY a.f_id ORDER BY a.f_create_date ASC  LIMIT 0,:pageRowsCount";
				}
			}
			// 品牌检索
			if (selectType == 2) {
				sqlBeg = "SELECT a.f_id goodsId, a.f_name goodsName, a.f_image goodsImagePath, a.f_market_price goodsPrice, count(c.f_id) goodsNum FROM t_product a LEFT JOIN " +
						"t_brand b ON a.f_brand = b.f_id LEFT JOIN t_stock_log c ON c.f_product = a.f_id WHERE b.f_name LIKE :selectVaule AND c.f_organization =:shopId  AND   " +
						"c.f_state=1 AND a.f_is_list=1 AND a.f_is_marketable=1  AND a.type=1 and a.f_is_pre_order = 0 ";
				// 向上加载
				if (pageLoadType == 1) {
					sql = sqlBeg
							+ " AND a.f_id <:goodsId GROUP BY a.f_id ORDER BY a.f_create_date DESC  LIMIT 0,:pageRowsCount  ";
				} else {
					sql = sqlBeg
							+ " AND a.f_id >:goodsId GROUP BY a.f_id ORDER BY a.f_create_date ASC  LIMIT 0,:pageRowsCount  ";
				}
			}
			// 执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			query.setParameter("shopId", shopId);
			query.setParameter("pageRowsCount", pageRowsCount);
			query.setParameter("selectVaule", "%" + selectVaule + "%");
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.getResultList();
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		// 存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}


	@SuppressWarnings("unchecked")
	public Map<String, Object> InventorySearch(String selectVaule,
			int pageLoadType, int pageRowsCount, String shopId,String content,int pageType) {
		// 初始化map集合
		Map<String, Object> mapResult = new HashMap<String, Object>();
		// 每页加载数据条数(暂定默认10条)
		List<Result> list = new ArrayList<Result>();
		String sql = null;
		String sqlBeg = null;
		try {
			// 商品名称检索
			sqlBeg = " SELECT b.f_id shopId,b.f_tel shopPhone,c.type goodType, b.f_name shopName, c.f_name goodsName, count(a.f_product_sn) shopGodsNum,c.f_price  goodsPrice,c.f_image  goodsImagePath "
					+ " FROM t_stock_log a LEFT JOIN t_organization b ON a.f_organization = b.f_id LEFT JOIN t_product c ON c.f_id = a.f_product WHERE ";
			if(StringUtils.isNotEmpty(selectVaule)){
				sqlBeg += "c.f_name LIKE :selectVaule  AND ";
			}
			sql = sqlBeg + "a.f_state=1 AND c.f_is_list=1 AND c.f_is_marketable=1 and b.f_id = :shopId";
			switch (pageType) {
				//名称
			case 1:
				sql +=" and c.f_name >:content GROUP BY a.f_product ORDER BY c.f_name ASC LIMIT 0,:pageRowsCount";
				break;
				//类型
			case 2:
				sql +=" and c.f_name >:content GROUP BY a.f_product ORDER BY c.type ASC LIMIT 0,:pageRowsCount";
				break;
			default:
				break;
			}
			// 执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("pageRowsCount", pageRowsCount);
			query.setParameter("shopId", shopId);
			query.setParameter("content", content);
			if(StringUtils.isNotEmpty(selectVaule)){
				query.setParameter("selectVaule", "%" + selectVaule + "%");
			}
			
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.getResultList();
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		// 存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAdminInfo(String adminId) {
 		Map<String, Object> mapResult = new HashMap<String, Object>();
		List<Object> resultList=new ArrayList<Object>();
		List<Object> resultTransfer=new ArrayList<Object>();
		List<Result> list = null;
		String sqlList =null;
		String sql = null;
		String sql2 = null;
		List<Result> resultList2 = null;
		String shopId = adminDaoImpl.find(Long.parseLong(adminId)).getOrganization();
		String shopName = organizationDao.find(Long.parseLong(shopId)).getName();
		
		try {
			// SQL语句  POS首页今日处理订单
			sql = "SELECT count(b.f_id) adminOrdersFinished FROM t_admin_order_log a LEFT JOIN t_order b ON a.f_order = b.f_id WHERE a.f_admin = :adminId AND b.f_status = 5 AND DATE(b.f_complete_date) = DATE(NOW())";
			// 执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("adminId", adminId);
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.getResultList();
			
			// SQL语句  POS首页今日营业额
			sql2 = "SELECT cast(ifnull(SUM(c.amount), 0) as decimal(10,3)) adminTurnover FROM t_admin_order_log a LEFT JOIN t_order b ON a.f_order = b.f_id LEFT JOIN t_payment_log c ON b.f_id = c.orders "
					+ "WHERE a.f_admin = :adminId AND b.f_shipping_method = 2 AND c.`status` = 1 AND DATE(c.f_modify_date) = DATE(NOW())";
			// 执行sql语句
			Query query2 = entityManager.createNativeQuery(sql2);
			query2.setParameter("adminId", adminId);
			// 结果集格式化
			query2.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			resultList2 = query2.getResultList();
			
			// SQL语句未处理订单
			sqlList = "SELECT count(a.f_id) shopOrdersSurplus FROM t_organization b LEFT JOIN t_admin c ON c.f_organization = b.f_id LEFT JOIN t_order a ON "
					+ "a.f_organization = b.f_id WHERE a.f_status in (2,3) AND c.f_id = :adminId  ;";
			// 执行sql语句
			Query queryList= entityManager.createNativeQuery(sqlList);
			queryList.setParameter("adminId", adminId);
			// 结果集格式化
			queryList.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			resultList= queryList.getResultList();
			// SQL语句 未处理调拨订单
			String sqlTransfer = "SELECT count(c.f_id) shopTransfer FROM t_admin a LEFT JOIN t_organization b ON a.f_organization = b.f_id LEFT JOIN t_stock_trans_req c ON c.f_from_organization = " +
					"b.f_id  WHERE a.f_id = :adminId  AND c.f_status = 1";
			// 执行sql语句
			Query queryTransfer= entityManager.createNativeQuery(sqlTransfer);
			queryTransfer.setParameter("adminId", adminId);
			// 结果集格式化
			queryTransfer.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			resultTransfer= queryTransfer.getResultList();
			if(resultList.size()>0&&resultList.size()>0&&resultTransfer.size()>0){
				Map<String,Object> m = (Map<String, Object>) list.get(0);
				Map<String,Object> m2 = (Map<String, Object>) resultList2.get(0);
				Object adminOrdersFinishedObject=m.get("adminOrdersFinished");
				Long adminOrdersFinished = Long.parseLong(String.valueOf(adminOrdersFinishedObject));
				Object adminTurnoverObject=m2.get("adminTurnover");
				String adminTurnover = String.valueOf(adminTurnoverObject);
				Map<String,Object> mm= (Map<String, Object>) resultList.get(0);
				Object shopOrdersSurplusObject=mm.get("shopOrdersSurplus");
				String shopOrdersSurplus = String.valueOf(shopOrdersSurplusObject);
				Map<String,Object> mmm= (Map<String, Object>) resultTransfer.get(0);
				Object shopTransferObject=mmm.get("shopTransfer");
				String shopTransfer = String.valueOf(shopTransferObject);
				Map<String,Object> result= new HashMap<String, Object>();
				result.put("adminOrdersFinished",adminOrdersFinished);
				result.put("adminTurnover",adminTurnover);
				result.put("shopId",shopId);
				result.put("shopName",shopName);
				result.put("shopOrdersSurplus",shopOrdersSurplus);
				result.put("shopTransfer",shopTransfer);
				mapResult.put(UnivParameter.DATA, result);
			}else{
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, resultList);
			}
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		// 存放最终的执行结果
		return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBonusAccept(int pageLoadType,
			int pageRowsCount, String packetId,String shopId) {//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sqlBeg=null;
		String sql=null;
		try {
			//SQL语句
			sqlBeg="SELECT Date(a.f_apply_time) applyDate, a.f_title pcaketTitle, a.f_packet_goods packetGoods, a.f_state packetState, b.f_phone applyerPhone,"
					+ " a.f_id packetId, a.f_credit packetGoodsNum, b.f_nickname applyerNickname,a.f_gross packetNum,a.f_content packetInfo,DATE(a.f_check_time) checkTime "
					+ " FROM t_bonus a LEFT JOIN t_member b ON a.f_member = b.f_id WHERE a.f_bonus_type = 3 AND a.f_organization = :shopId  AND a.f_state < 3  ";
			if(packetId.equals("0")){
				sql=sqlBeg+"AND a.f_id >0  ORDER BY a.f_apply_time DESC LIMIT 0,:pageRowsCount";
			}else{
				if(pageLoadType==1){
					sql=sqlBeg+"AND a.f_id >:packetId ORDER BY a.f_apply_time ASC LIMIT 0,:pageRowsCount";
				}else{
					sql=sqlBeg+"AND a.f_id <:packetId ORDER BY a.f_apply_time  DESC LIMIT 0,:pageRowsCount";
				}
			}
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			if(!packetId.equals("0")){
				query.setParameter("packetId", packetId);
			}
			query.setParameter("shopId", shopId);
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
	public Map<String, Object> getBonusDetails(String packetId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		try {
			//SQL语句
			sql="SELECT Date(a.f_apply_time) applyDate, a.f_title pcaketTitle, a.f_packet_goods packetGoods, a.f_state packetState, b.f_phone applyerPhone, "
					+ "b.f_nickname applyerNickname, a.f_credit packetGoodsNum, a.f_content packetInfo, a.f_gross packetNum, DATE(a.f_check_time) checkTime"
					+ " FROM t_bonus a LEFT JOIN t_member b ON b.f_id = a.f_member WHERE a.f_id = :packetId ";
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

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsParameter(String goodsId) {
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
		if(list.hashCode()>=0){
			mapResult.put(UnivParameter.DATA, list.get(0));
		}else{
			mapResult.put(UnivParameter.DATA, list);
		}
		return mapResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsInfo(String goodsId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> resultList=new ArrayList<Object>();
		List<Object> promotionList=new ArrayList<Object>();
		List<Object> listPromotion=new ArrayList<Object>();
		BindProductInfoVO [] vos = new BindProductInfoVO[5];
		List<Object> li =new ArrayList<Object>();
		List<Result> listGoods=null;
		List<Result> listBonus=null;
		String sqlGoods=null;
		String sqlBonus=null;
		List<Object> list=null;
		try {
			//满送（满5000赠Iphone）
			sqlGoods="SELECT d.promotionGiftId promotionGiftId, a.f_image promotionGiftImagePath," +
					"a.f_name promotionGiftName, a.f_market_price promotionGiftPrice FROM t_product a, ( SELECT c.f_id promotionId, c.f_name promotionName, d.f_gifts promotionGiftId " +
					"FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product LEFT JOIN t_promotion c ON c.f_id = b.f_promotions LEFT JOIN t_promotion_gift d ON " +
					"d.f_promotions = c.f_id WHERE a.f_id = :goodsId  AND c.f_id = 3 ) d WHERE a.f_id = d.promotionGiftId ";
			Query queryGoods = entityManager.createNativeQuery(sqlGoods);
			queryGoods.setParameter("goodsId", goodsId);
			queryGoods.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listGoods= queryGoods.getResultList();
			//买赠（买商品送Ipone）
			sqlBonus="SELECT d.promotionGiftId promotionGiftId, a.f_image promotionGiftImagePath, a.f_name promotionGiftName, a.f_market_price promotionGiftPrice FROM " +
					"t_product a, ( SELECT c.f_id promotionId, c.f_name promotionName, d.f_gifts promotionGiftId FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = " +
					"b.f_product LEFT JOIN t_promotion c ON c.f_id = b.f_promotions LEFT JOIN t_promotion_gift d ON d.f_promotions = c.f_id WHERE a.f_id = :goodsId  AND c.f_id = 4 ) d " +
					"WHERE a.f_id = d.promotionGiftId";
			Query queryBonus = entityManager.createNativeQuery(sqlBonus);
			queryBonus.setParameter("goodsId", goodsId);
			queryBonus.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listBonus= queryBonus.getResultList();
			//捆绑商品
			String sqlPromotion="SELECT a.f_product_1 product1,a.f_product_2 product2,a.f_product_3 product3,a.f_product_4 product4,a.f_product_5 product5,a.f_price_1 price1," +
					"a.f_price_2 price2,a.f_price_3 price3,a.f_price_4 price4,a.f_price_5 price5 FROM t_promotion_bind a  WHERE a.f_promotions = " +
					"(SELECT a.f_id promotionId FROM t_promotion a " +
					"LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN ( SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion" +
					" b ON a.f_id = b.f_product WHERE a.f_id = :goodsId ) AND a.f_promotion_type = 5 AND a.f_end_date>NOW());";
			//执行sql语句
			Query queryPromotion = entityManager.createNativeQuery(sqlPromotion);
			queryPromotion.setParameter("goodsId", goodsId);
			//结果集格式化
			queryPromotion.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listPromotion= queryPromotion.getResultList();
			
			if(listPromotion.size()>0){
				Map<String,Object> m1 = (Map<String, Object>) listPromotion.get(0);
				
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
				
				vos[0] = new BindProductInfoVO(product1, price1, img1);
				vos[1] = new BindProductInfoVO(product2, price2, img2);
				vos[2] = new BindProductInfoVO(product3, price3, img3);
				vos[3] = new BindProductInfoVO(product4, price4, img4);
				vos[4] = new BindProductInfoVO(product5, price5, img5);
				
				String sq="SELECT a.f_id id,a.f_image img FROM t_product a WHERE a.f_id in (:product1,:product2,:product3,:product4,:product5)";
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
					for (int i = 0; i < vos.length; i++) {
						if( m2.get("id").toString().equals( vos[i].getProductId().toString())){
								vos[i].setImg( String.valueOf(m2.get("img")) );
								break;
						}
					}
				}
			}
			//获得优惠方式
			String sql="SELECT a.f_id promotionId, a.f_promotion_type promotionType, a.f_title promotionTitle FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type" +
					" = c.f_promotions WHERE a.f_id IN ( SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId )  ";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//商品图片，Id信息
			String sqlBeg="SELECT a.f_id goodsId, a.f_name goodsName, a.f_caption goodsSubhead, a.f_price goodsPrice, a.f_market_price goodsMarketPrice, " +
					"a.f_product_images goodsImagePath, a.f_specification_values goodsDefaultColor, a.f_operator buyType FROM t_product a WHERE a.f_id = :goodsId ;";
			//执行sql语句
			Query queryProduct = entityManager.createNativeQuery(sqlBeg);
			queryProduct.setParameter("goodsId", goodsId);
			//结果集格式化
			queryProduct.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			resultList= queryProduct.getResultList();
			//寻找到商品信息
			if(resultList.size()>0){
				//解析商品信息
				Map<String,Object> m = (Map<String, Object>) resultList.get(0);
				List<Object> resultDefaultColor= new ArrayList<Object>();
				Object goodsIdObject=m.get("goodsId");
				String Id = String.valueOf(goodsIdObject);
				Object goodsNameObject=m.get("goodsName");
				String goodsName = String.valueOf(goodsNameObject);
				Object goodsSubheadObject=m.get("goodsSubhead");
				String goodsSubhead = String.valueOf(goodsSubheadObject);
				Object goodsPriceObject=m.get("goodsPrice");
				String goodsPrice = String.valueOf(goodsPriceObject);
				Object goodsMarketPriceObject=m.get("goodsMarketPrice");
				String goodsMarketPrice = String.valueOf(goodsMarketPriceObject);
				Object goodsImagePathObject=m.get("goodsImagePath");
				String goodsImage = String.valueOf(goodsImagePathObject);
				Object goodsDefaultColor=m.get("goodsDefaultColor");
				JSONArray defaultColor = JSONArray.fromObject(goodsDefaultColor);
				//存放颜色和内存大小
				for(int i=0;i<defaultColor.size();i++){
					JSONObject  color= defaultColor.getJSONObject(i);
					String defaul=color.getString("value");
					resultDefaultColor.add(defaul);
				 }
				Map<String,Object> result= new HashMap<String, Object>();
				result.put("goodsId",Id);
				result.put("goodsName",goodsName);
				result.put("goodsSubhead",goodsSubhead);
				result.put("goodsPrice",goodsPrice);
				result.put("goodsMarketPrice",goodsMarketPrice);
				result.put("defaultColor",resultDefaultColor);
				//如果优惠方式为空，存放空字节
				if(list.hashCode()==0){
					result.put("promotion","  ");
				}else{
					//解析优惠方式的信息					
					for(int i=0;i<list.size();i++){
						Map<String,Object> mm = (Map<String, Object>) list.get(i);
						Object promotionIdObject =mm.get("promotionId");
						String promotionId=String.valueOf(promotionIdObject);
						Object promotionTypeObject =mm.get("promotionType");
						String promotionType=String.valueOf(promotionTypeObject);
						Object promotionTitleObject =mm.get("promotionTitle");
						String promotionTitle=String.valueOf(promotionTitleObject);;
						Map<String,Object> promotionResult= new HashMap<String, Object>();
						promotionResult.put("promotionId",promotionId);
						promotionResult.put("promotionType",promotionType);
						promotionResult.put("promotionTitle",promotionTitle);
						if(promotionType.equals("3")&&listGoods.size()>0){
							promotionResult.put("promotionGift",listGoods);
						}else if(promotionType.equals("4")&&listBonus.size()>0){
							promotionResult.put("promotionGift",listBonus);
						}else if(promotionType.equals("5")&&listBonus.size()>0){
							promotionResult.put("promotionGift",vos);
						}else{
							promotionResult.put("promotionGift"," ");
						}
						promotionList.add(promotionResult);
					}
				result.put("promotion",promotionList);
				}
				result.put("goodsImage",goodsImage);
				resultList.clear();
				resultList.add(result);
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, resultList);
			}else{
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, resultList);
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
	public Map<String, Object> getNewGoods(String goodsId, String goodsColor,
			String goodsMemory) {
		//初始化map集合
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object>SpecificationColor = new HashMap<String,Object>();
		Map<String,Object>SpecificationMemory = new HashMap<String,Object>();
		List<Object> resultDefaultColor= new ArrayList<Object>();
		List<Object> SpecificationValue=new ArrayList<Object>();
		List<Object> resultList=new ArrayList<Object>();
		List<Object> goodsList=new ArrayList<Object>();
		List<Object> list=new ArrayList<Object>();
		String sql=null;
		String sqlGoods=null;
		try {
			//SQL语句
			//取出商品的同种规格
			sql="SELECT b.f_specification_items modelValues FROM t_product a LEFT JOIN t_product_model b ON a.f_product_model = b.f_id WHERE a.f_id = :goodsId  ";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			list= query.getResultList();
			//将list转成JSONArray
			JSONArray modelValues = JSONArray.fromObject(list.get(0));
			//将modelValues按颜色和内存大小分开
			for(int i=0;i<modelValues.size();i++){
				JSONObject model = modelValues.getJSONObject(i);
				//颜色
				if(i==0){
						//商品的颜色分成3部分 name,entries和selectedOptions
						String selectValue=model.getString("selectedOptions");
						mapResult.put("specificationColor", selectValue);
						List<Object> entriesList= (List<Object>) model.get("entries");
						//将entries转成JSONArray
						JSONArray entries = JSONArray.fromObject(entriesList);
						for(int k=0;k<entries.size();k++){
							//取出其中的value值
							JSONObject entrie = entries.getJSONObject(k);
							String value = entrie.getString("value");
							//如果取出的value值和goodsColor相同则放到SpecificationColor中
								if(value.equals(goodsColor)){
									SpecificationColor.put("id",entrie.getInt("id"));
									SpecificationColor.put("value",value);
									SpecificationValue.add(SpecificationColor);
								}
						}
					}
				if(i==1){
						String selectValue=model.getString("selectedOptions");
						mapResult.put("specificationMemory", selectValue);
						List<Object> entriesList= (List<Object>) model.get("entries");
						//将entries转成JSONArray
						JSONArray entries = JSONArray.fromObject(entriesList);
						for(int k=0;k<entries.size();k++){
							//取出其中的value值
							JSONObject entrie = entries.getJSONObject(k);
							String value = entrie.getString("value");
							//如果取出的value值和goodsMemory相同则放到SpecificationColor中
								if(value.equals(goodsMemory)){
									SpecificationMemory.put("id",entrie.getInt("id"));
									SpecificationMemory.put("value",value);
									SpecificationValue.add(SpecificationMemory);
								}
						}
				}
			 }
			//取出购买方式【合约机 ，电信，联通，移动】
			String  	sqlOpera="SELECT b.f_name operatorName   FROM t_operator b;";
			//执行sql语句
			Query queryOpera = entityManager.createNativeQuery(sqlOpera);
			resultList= queryOpera.getResultList();
			//取出商品的名称，ID，库存，图片等等
			sqlGoods="SELECT a.f_id goodsId, a.f_market_price goodsMarketPrice, a.f_stock goodsStock, a.f_image  goodsImagePath, a.f_name goodsName, a.f_price goodsPrice," +
					" a.f_specification_values goodsDefaultColor, b.f_name buyType FROM t_product a LEFT JOIN t_operator b ON a.f_operator = b.f_id WHERE a.f_product_model IN ( SELECT " +
					"a.f_product_model FROM t_product a LEFT JOIN t_product_model c ON a.f_product_model = c.f_id WHERE a.f_id =  :goodsId ) AND" +
					" a.f_specification_values = :jsonArray  ";
			//执行sql语句
			Query queryGoods = entityManager.createNativeQuery(sqlGoods);
			queryGoods.setParameter("goodsId", goodsId);
			//将SpecificationValue转为JSONArray格式			
			JSONArray jsonArray = JSONArray.fromObject(SpecificationValue);
			String stringArray =jsonArray.toString();
			queryGoods.setParameter("jsonArray", stringArray);
			//结果集格式化
			queryGoods.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			goodsList= queryGoods.getResultList();
			if(goodsList.size()>0){
				Map<String,Object> m = (Map<String, Object>) goodsList.get(0);
				Object goodsIdObject=m.get("goodsId");
				String Id = String.valueOf(goodsIdObject);
				Object goodsNameObject=m.get("goodsName");
				String goodsName = String.valueOf(goodsNameObject);
				Object goodsPriceObject=m.get("goodsPrice");
				String goodsPrice = String.valueOf(goodsPriceObject);
				Object goodsMarketPriceObject=m.get("goodsMarketPrice");
				String goodsMarketPrice = String.valueOf(goodsMarketPriceObject);
				Object goodsStockObject=m.get("goodsStock");
				String goodsStock = String.valueOf(goodsStockObject);
				Object goodsImagePathObject=m.get("goodsImagePath");
				String goodsImagePath = String.valueOf(goodsImagePathObject);
				Object buyTypeObject=m.get("buyType");
				String buyType = String.valueOf(buyTypeObject);
				Object goodsDefaultColor=m.get("goodsDefaultColor");
				JSONArray defaultColor = JSONArray.fromObject(goodsDefaultColor);
				for(int i=0;i<defaultColor.size();i++){
					JSONObject  color= defaultColor.getJSONObject(i);
					String defaul=color.getString("value");
					resultDefaultColor.add(defaul);
				 }
				mapResult.put("goodsId",Id);
				mapResult.put("goodsName",goodsName);
				mapResult.put("buyType",buyType);
				mapResult.put("goodsPrice",goodsPrice);
				mapResult.put("goodsMarketPrice",goodsMarketPrice);
				mapResult.put("defaultColor",resultDefaultColor);
				mapResult.put("goodsImagePath",goodsImagePath);
				mapResult.put("goodsStock",goodsStock);
				mapResult.put("purchaseMethod",resultList);
				//存放最终的执行结果
				result.put(UnivParameter.DATA, mapResult);
			}else{
				//存放最终的执行结果
				result.put(UnivParameter.DATA, goodsList);
			}
			//存放正确的返回参数CODE--1
			result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}

	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getOperaInfo(String goodsId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list=new ArrayList<Object>();
		try {
			String  	sql="SELECT DISTINCT d.f_last_time contractTime FROM t_product a LEFT JOIN t_operator b ON a.f_operator = b.f_id LEFT JOIN t_contract c ON c.f_operator " +
					"= b.f_id LEFT JOIN t_contract_package d ON c.f_id = d.f_contract WHERE a.f_id = :goodsId  ORDER BY d.f_last_time  ASC    ;";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			//结果集格式化
			list= query.getResultList();
			List<Object> resultList=new ArrayList<Object>();
			//SQL语句
			 String sqlList="SELECT DISTINCT d.f_name contractArea, b.f_name operaName, c.f_name contractName FROM t_product a LEFT JOIN t_operator b ON a.f_operator = b.f_id " +
			 		"LEFT JOIN t_contract c ON c.f_operator = b.f_id LEFT JOIN t_area d ON c.f_area = d.f_orders WHERE a.f_id =  :goodsId  ;";
			//执行sql语句
			Query queryList = entityManager.createNativeQuery(sqlList);
			queryList.setParameter("goodsId", goodsId);
			//结果集格式化
			queryList.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			resultList= queryList.getResultList();
			if(resultList.size()>0){
				Map<String,Object> m = (Map<String, Object>) resultList.get(0);
				Object contractAreaObject=m.get("contractArea");
				String contractArea = String.valueOf(contractAreaObject);	
				Object operaNameObject=m.get("operaName");
				String operaName = String.valueOf(operaNameObject);
				Object contractNameObject=m.get("contractName");
				String contractName = String.valueOf(contractNameObject);
				Map<String,Object> result= new HashMap<String, Object>();
				result.put("contractArea",contractArea);
				result.put("operaName",operaName);
				result.put("contractName",contractName);
				result.put("contractTime",list);
				mapResult.put(UnivParameter.DATA, result);
			}else{
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, resultList);
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
	public Map<String, Object> changePhoneNumber(String phoneNumId,String selectValue,String goodsId) {
		// 初始化map集合
				Map<String, Object> mapResult = new HashMap<String, Object>();
				// 每页加载数据条数(暂定默认10条)
				List<Result> list = null;
				String sql = null;
				try {
					
					if(selectValue==null){
						// 手机号列表
						sql = "SELECT a.f_id phoneNumId, a.f_price phoneNumPrice, a.f_tel_fare phoneNumTelFare, a.f_number phoneNum FROM t_phone_number a LEFT JOIN t_operator b" +
								" ON b.f_id = a.f_operator LEFT JOIN t_product c ON c.f_operator = b.f_id WHERE a.f_is_sold = 0 AND c.f_id =:goodsId  AND a.f_id > :phoneNumId ORDER BY a.f_id ASC " +
								"LIMIT 0, 20";
						// 执行sql语句
						Query query = entityManager.createNativeQuery(sql);
						query.setParameter("phoneNumId", phoneNumId);
						query.setParameter("goodsId", goodsId);
						// 结果集格式化
						query.unwrap(SQLQuery.class).setResultTransformer(
								Transformers.ALIAS_TO_ENTITY_MAP);
						list = query.getResultList();
						if(list.size()<1){
							// 手机号列表
							sql = "SELECT a.f_id phoneNumId, a.f_price phoneNumPrice, a.f_tel_fare phoneNumTelFare, a.f_number phoneNum FROM t_phone_number a LEFT JOIN t_operator b" +
									" ON b.f_id = a.f_operator LEFT JOIN t_product c ON c.f_operator = b.f_id WHERE a.f_is_sold = 0 AND c.f_id =:goodsId  AND a.f_id >0 ORDER BY a.f_id ASC " +
									"LIMIT 0, 20";
							// 执行sql语句
							Query queryNew = entityManager.createNativeQuery(sql);
							queryNew.setParameter("goodsId", goodsId);
							// 结果集格式化
							queryNew.unwrap(SQLQuery.class).setResultTransformer(
									Transformers.ALIAS_TO_ENTITY_MAP);
							list = queryNew.getResultList();
						}
					}else{
						// 手机号列表
						sql = "SELECT a.f_id phoneNumId, Round(a.f_price,2) phoneNumPrice, Round(a.f_tel_fare,2) phoneNumTelFare, a.f_number phoneNum FROM t_phone_number a LEFT JOIN t_operator b" +
								" ON b.f_id = a.f_operator LEFT JOIN t_product c ON c.f_operator = b.f_id WHERE a.f_is_sold = 0 AND c.f_id =:goodsId  AND  a.f_id > :phoneNumId  " +
								"AND a.f_number LIKE :selectValue  ORDER BY a.f_id ASC LIMIT 0, 20 ";
						// 执行sql语句
						Query query = entityManager.createNativeQuery(sql);
						query.setParameter("phoneNumId", phoneNumId);
						query.setParameter("goodsId", goodsId);
						query.setParameter("selectValue", "%" + selectValue + "%");
						// 结果集格式化
						query.unwrap(SQLQuery.class).setResultTransformer(
								Transformers.ALIAS_TO_ENTITY_MAP);
						list = query.getResultList();
						if(list.size()<1){
							// 手机号列表
							sql = "SELECT a.f_id phoneNumId, Round(a.f_price,2) phoneNumPrice, Round(a.f_tel_fare,2) phoneNumTelFare, a.f_number phoneNum FROM t_phone_number a LEFT JOIN t_operator b" +
									" ON b.f_id = a.f_operator LEFT JOIN t_product c ON c.f_operator = b.f_id WHERE a.f_is_sold = 0 AND c.f_id =:goodsId  AND  a.f_id > 0  " +
									"AND a.f_number LIKE :selectValue  ORDER BY a.f_id ASC LIMIT 0, 20 ";
							Query queryNew = entityManager.createNativeQuery(sql);
							queryNew.setParameter("goodsId", goodsId);
							queryNew.setParameter("selectValue", "%" + selectValue + "%");
							// 结果集格式化
							queryNew.unwrap(SQLQuery.class).setResultTransformer(
									Transformers.ALIAS_TO_ENTITY_MAP);
							list = queryNew.getResultList();
						}
					}
					// 存放正确的返回参数CODE--1
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
				} catch (Exception e) {
					// 存放错误的返回参数CODE--0
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
					return mapResult;
				}
				// 存放最终的执行结果
				mapResult.put(UnivParameter.DATA, list);
				return mapResult;
	}

	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> changeContractPackage(String goodsId,
			String contractTime) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list=new ArrayList<Object>();
		try {
			String  	sql="SELECT d.f_id contractId, d.f_month_cost monthCost, d.f_desc contractDesc, d.f_fare_rules fareRules, d.f_tel_fare contractTelfare FROM t_product a LEFT JOIN " +
					"t_operator b ON a.f_operator = b.f_id LEFT JOIN t_contract c ON c.f_operator = b.f_id LEFT JOIN t_contract_package d ON c.f_id = d.f_contract WHERE a.f_id = :goodsId AND " +
					"d.f_last_time = :contractTime AND d.f_state = 1 ORDER BY d.f_month_cost ASC;";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("goodsId", goodsId);
			query.setParameter("contractTime", contractTime);
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
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
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}
	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getShopAdmin(String shopId,String selectValue) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		if(shopId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "门店Id为空");
			return mapResult;
		}
		try {
			if(selectValue==null){
				sql="SELECT c.f_id adminId, c.f_name adminName FROM t_admin c LEFT JOIN t_organization d ON c.f_organization = d.f_id WHERE d.f_id = :shopId " +
						"AND c.f_is_enabled=1	 ;";
			}else{
			//SQL语句
				sql="SELECT c.f_id adminId, c.f_name adminName FROM t_admin c LEFT JOIN t_organization d ON c.f_organization = d.f_id WHERE d.f_id = :shopId " +
						"AND c.f_name LIKE  :selectValue AND c.f_is_enabled=1	 ;";
			}
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("shopId", shopId);
			if(selectValue!=null){
				query.setParameter("selectValue", "%" + selectValue + "%");
			}
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getShopAdmin(String adminId, String beginTime,
			String endtime) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list=null;
		String sql=null;
		//如果开始的时间大于结束的时间则把开始时间和结束时间互换
		if(beginTime.compareTo(endtime)>=0){
			String m=beginTime;
			beginTime =  endtime;
			endtime = m;
		}
		//SQL语句
		sql="SELECT c.f_id adminId,  CASE WHEN SUM(b.f_amount) IS NULL THEN 0 ELSE  Round(SUM(b.f_amount),2)  END salePrice, c.f_name adminName, "
				+ "c.f_jobnumber adminJobNumber, COUNT(b.f_id) saleNum FROM t_admin_order_log a LEFT JOIN t_order b ON a.f_order = b.f_id LEFT JOIN "
				+ "t_admin c ON a.f_admin = c.f_id WHERE c.f_id = :adminId AND b.f_status = 5 AND DATE(b.f_complete_date) >=" +
				" :beginTime AND DATE(b.f_complete_date) <=  :endtime   ";
		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("adminId", adminId);
		query.setParameter("beginTime", beginTime);
		query.setParameter("endtime", endtime);
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list= query.getResultList();
		if(list.size()>0){
			Map<String,Object> m = (Map<String, Object>) list.get(0);
			Object adminIdObject=m.get("adminId");
			String adminsId = String.valueOf(adminIdObject);	
			Object salePriceObject=m.get("salePrice");
			String salePrice = String.valueOf(salePriceObject);
			Object adminNameObject=m.get("adminName");
			String adminName = String.valueOf(adminNameObject);
			Object adminJobNumberObject=m.get("adminJobNumber");
			String adminJobNumber = String.valueOf(adminJobNumberObject);
			Object saleNumObject=m.get("saleNum");
			String saleNum = String.valueOf(saleNumObject);
			mapResult.put("adminsId",adminsId);
			mapResult.put("salePrice",salePrice);
			mapResult.put("adminName",adminName);
			mapResult.put("adminJobNumber",adminJobNumber);
			mapResult.put("saleNum",saleNum);
			return  mapResult;
		}
		return null;
	}
	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> shopRank(String beginTime, String endtime) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> listName =new ArrayList<Object>();
		List<Object> listResult =new ArrayList<Object>();
		List<Result> list=null;
		String sql=null;
		String sqlName=null;
		try {
			if(beginTime.compareTo(endtime)>0){
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "开始时间不能大于结束时间");
				return mapResult;
			}
			//SQL语句
			sql="SELECT d.f_id shopId, CASE WHEN SUM(b.f_amount) IS NULL THEN 0 ELSE  Round(SUM(b.f_amount),2)  END salePrice, d.f_name adminName, COUNT(b.f_id) saleNum FROM " +
					"t_organization d, t_admin c, t_admin_order_log a, t_order b WHERE d.f_id = c.f_organization AND a.f_admin = c.f_id AND b.f_id = a.f_order AND b.f_status = 5 AND" +
					" DATE(b.f_complete_date) >= :beginTime  AND DATE(b.f_complete_date)<= :endtime GROUP BY d.f_id ORDER BY SUM(b.f_amount) DESC, COUNT(b.f_id) DESC LIMIT 5 ";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("beginTime", beginTime);
			query.setParameter("endtime", endtime);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> m = (Map<String, Object>) list.get(i);
					Object salePriceObject=m.get("salePrice");
					String salePrice = String.valueOf(salePriceObject);	
					Object shopIdObject=m.get("shopId");
					String shopId = String.valueOf(shopIdObject);
					Object adminNameObject=m.get("adminName");
					String adminName = String.valueOf(adminNameObject);
					Object saleNumObject=m.get("saleNum");
					String saleNum = String.valueOf(saleNumObject);
					Map<String,Object> orderMap= new HashMap<String, Object>();
					orderMap.put("salePrice",salePrice);
					orderMap.put("shopId",shopId);
					orderMap.put("adminName",adminName);
					orderMap.put("saleNum",saleNum);
					sqlName="SELECT b.f_name FROM t_organization a LEFT JOIN t_admin b ON a.f_id = b.f_organization WHERE a.f_id = :shopId  AND b.f_office = 0		";
					//执行sql语句
					Query queryName = entityManager.createNativeQuery(sqlName);
					queryName.setParameter("shopId", shopId);
					listName= queryName.getResultList();
					orderMap.put("shopManagerName",listName.get(0).toString());
					listResult.add(orderMap);
				}
				mapResult.put(UnivParameter.DATA, listResult);
			}else{
				mapResult.put(UnivParameter.DATA, list);
			}
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		return mapResult;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> myShopRank(String shopId, String beginTime,
			String endtime) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		try {
			if(beginTime.compareTo(endtime)>0){
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "开始时间不能大于结束时间");
				return mapResult;
			}
			//SQL语句
			sql="SELECT c.f_id adminId, CASE WHEN SUM(b.f_amount) IS NULL THEN 0 ELSE Round(SUM(b.f_amount),2) END  salePrice, c.f_name adminName, c.f_jobnumber adminJobNumber," +
					" COUNT(b.f_id) saleNum FROM t_admin_order_log a LEFT JOIN t_order b ON a.f_order = b.f_id LEFT JOIN t_admin c ON a.f_admin = c.f_id LEFT JOIN t_organization " +
					"d ON c.f_organization = d.f_id WHERE d.f_id = :shopId AND b.f_status = 5 AND DATE(b.f_complete_date) >=:beginTime  AND DATE(b.f_complete_date) <=:endtime " +
					" GROUP BY c.f_id ORDER BY SUM(b.f_amount) DESC, COUNT(b.f_id) DESC  LIMIT 5  ;";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("shopId", shopId);
			query.setParameter("beginTime", beginTime);
			query.setParameter("endtime", endtime);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	
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
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getShopOrders(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId) {
			// 初始化map集合
			Map<String, Object> mapResult = new HashMap<String, Object>();
			List<Object> listProduct =new ArrayList<Object>();
			List<Object> listPromotion=new ArrayList<Object>();
			List<Object> listResult = new ArrayList<Object>();
			// 每页加载数据条数(暂定默认10条)
			List<Result> list = null;
			String sql = null;
			String sqlBeg = "SELECT a.f_id orderId, a.f_sn ordersSn, d.f_phone orderPhone,a.f_amount amount, Round(a.f_amount - a.f_amount_paid,2)  orderPrice,a.f_status odersStatus,DATE_FORMAT( a.f_create_date, '%Y.%m.%d %T' ) preOrderDate FROM t_order a " +
						"LEFT JOIN t_member d ON a.f_member = d.f_id WHERE a.f_is_online = 0 AND a.f_organization = :shopId AND a.f_is_delete=0 ";
			String preOrderDate="";
			try {
				// ordersType 订单状态【1：未完成，2：已完成】
				switch (ordersType) {
					// 1：未完成(线下未付款)
					case 1:
						sqlBeg =sqlBeg+"AND a.f_status = 0 ";
						break;
					// 2：已完成(线下等待发货或者完成)
					case 2:
						sqlBeg =sqlBeg+"AND (a.f_status = 3 or a.f_status = 5) ";
						break;
					default:
						break;
				}
				// 向上加载
				if(ordersId.equals("0")){
					sql=sqlBeg+"AND a.f_id >0  ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
				}else{
					if(pageLoadType==1){
						sql=sqlBeg+"AND a.f_id >:ordersId ORDER BY a.f_create_date ASC LIMIT 0,:pageRowsCount";
					}else{
						sql=sqlBeg+"AND a.f_id <:ordersId ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
					}
				}
				// 执行sql语句
				Query query = entityManager.createNativeQuery(sql);
				query.setParameter("shopId", shopId);
				if(!ordersId.equals("0")){
					query.setParameter("ordersId", ordersId);
				}
				query.setParameter("pageRowsCount", pageRowsCount);
				// 结果集格式化
				query.unwrap(SQLQuery.class).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP);
				list = query.getResultList();
				if(list.size()>0){
					for(int i=0;i<list.size();i++){
						Map<String,Object> m = (Map<String, Object>) list.get(i);
						Object orderIdObject=m.get("orderId");
						String orderId = String.valueOf(orderIdObject);	
						Object ordersSnObject=m.get("ordersSn");
						String ordersSn = String.valueOf(ordersSnObject);
						Object amountObject=m.get("amount");
						String amount = String.valueOf(amountObject);
						Object orderPhoneObject=m.get("orderPhone");
						String orderPhone = String.valueOf(orderPhoneObject);
						Object orderPriceObject=m.get("orderPrice");
						String orderPrice = String.valueOf(orderPriceObject);
						Object odersStatusObject=m.get("odersStatus");
						String odersStatus = String.valueOf(odersStatusObject);
						Object preOrderDateObject=m.get("preOrderDate");
						preOrderDate = String.valueOf(preOrderDateObject);
						Map<String,Object> orderMap= new HashMap<String, Object>();
						orderMap.put("ordersId",orderId);
						orderMap.put("amount",amount);
						orderMap.put("ordersSn",ordersSn);
						orderMap.put("orderPhone",orderPhone);
						orderMap.put("ordersPrice",orderPrice);
						orderMap.put("odersStatus",odersStatus);
						orderMap.put("preOrderDate",preOrderDate);
						//sql商品图片，Id信息,向orderMap里插入商品信息
						String sqlProduct="SELECT c.f_id goodsId, b.f_price goodsPrice, c.f_image goodsImagePath, c.f_name goodsName, b.f_quantity goodsNum, b.f_type goodsType " +
								"FROM t_order a LEFT JOIN t_order_item b ON a.f_id = b.f_orders LEFT JOIN t_product c ON b.f_product = c.f_id WHERE a.f_id = :orderId  ";
						//执行sql语句
						Query queryProduct = entityManager.createNativeQuery(sqlProduct);
						queryProduct.setParameter("orderId", orderId);
						//结果集格式化
						queryProduct.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						listProduct= queryProduct.getResultList();
						List<Object> productList =new ArrayList<Object>();
						//寻找到商品信息
						if(listProduct.hashCode()==0){
							orderMap.put("goods","  ");
						}else{
							for(int k=0;k<listProduct.size();k++){
								//解析商品信息
								Map<String,Object> mProduct = (Map<String, Object>) listProduct.get(k);
								Object goodsIdObject=mProduct.get("goodsId");
								String goodsId = String.valueOf(goodsIdObject);
								Object goodsNameObject=mProduct.get("goodsName");
								String goodsName = String.valueOf(goodsNameObject);
								Object goodsPriceObject=mProduct.get("goodsPrice");
								String goodsPrice = String.valueOf(goodsPriceObject);
								Object goodsImagePathObject=mProduct.get("goodsImagePath");
								String goodsImage = String.valueOf(goodsImagePathObject);
								Object goodsNumObject=mProduct.get("goodsNum");
								String goodsNum = String.valueOf(goodsNumObject);
								Object goodsTypeObject=mProduct.get("goodsType");
								String goodsType = String.valueOf(goodsTypeObject);
								Map<String,Object> productMap= new HashMap<String, Object>();
								productMap.put("goodsId",goodsId);
								productMap.put("goodsName",goodsName);
								productMap.put("goodsPrice",goodsPrice);
								productMap.put("goodsNum",goodsNum);
								productMap.put("goodsType",goodsType);
								productMap.put("goodsImage",goodsImage);
								if(goodsType.equals("1")){
									//获得优惠方式
									String sqlPromotion="SELECT a.f_id promotionId, a.f_promotion_type promotionType, a.f_title promotionTitle FROM t_promotion a "
											+ "LEFT JOIN t_product_promotion b ON a.f_id = b.f_promotions LEFT JOIN t_product c ON c.f_id = b.f_product WHERE c.f_id =:goodsId  AND "
									+ "DATE_FORMAT( a.f_begin_date,  '%Y.%m.%d %T' ) < :preOrderDate AND DATE_FORMAT( a.f_end_date,  '%Y.%m.%d %T' ) > :preOrderDate   ";
									//执行sql语句
									Query queryPromotion = entityManager.createNativeQuery(sqlPromotion);
									queryPromotion.setParameter("goodsId", goodsId);
									queryPromotion.setParameter("preOrderDate", preOrderDate);
									//结果集格式化
									queryPromotion.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
									listPromotion= queryPromotion.getResultList();
									//如果优惠方式为空，存放空字节
									if(listPromotion.size()>0){
										productMap.put("promotion",listPromotion);
									}	
								}							
									productList.add(productMap);
							}	
						orderMap.put("goods",productList);
					}
				listResult.add(orderMap);
			}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, listResult);
		}else{
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
		}
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);		
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;	
	}

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
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getTakeOrders(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId) {
			// 初始化map集合
			Map<String, Object> mapResult = new HashMap<String, Object>();
			List<Object> listProduct =new ArrayList<Object>();
			List<Object> listPromotion=new ArrayList<Object>();
			List<Object> listResult = new ArrayList<Object>();
			// 每页加载数据条数(暂定默认10条)
			List<Result> list = null;
			String sql = null;
			String sqlBeg = null;
			String preOrderDate=" ";
			try {
				// ordersType 订单状态【1：未完成，2：已完成】
				// 1：未完成(线上未付款或待自提配送方式自提)
				if (ordersType == 1) {
					sqlBeg ="SELECT a.f_id orderId, a.f_sn ordersSn,a.f_amount amount, d.f_phone orderPhone,Round(a.f_amount - a.f_amount_paid,2) orderPrice, a.f_status odersStatus,DATE_FORMAT( a.f_create_date, '%Y.%m.%d %T') preOrderDate"
							+ " ,CASE WHEN a.f_collect_time IS NULL THEN '' ELSE a.f_collect_time END  collectTime  FROM t_order a " +
							"LEFT JOIN t_member d ON a.f_member = d.f_id WHERE a.f_is_online = 1 AND a.f_organization = :shopId  AND a.f_status = 0 and a.f_type != 2 AND a.f_shipping_method=2 AND a.f_is_delete=0 ";
					// 向上加载
					if(ordersId.equals("0")){
						sql=sqlBeg+"AND a.f_id >0  ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
					}else{
						if(pageLoadType==1){
							sql=sqlBeg+"AND a.f_id >:ordersId ORDER BY a.f_create_date ASC LIMIT 0,:pageRowsCount";
						}else{
							sql=sqlBeg+"AND a.f_id <:ordersId ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
						}
					}
				}
				// 2：已完成(线上完成配送方式自提)
				if (ordersType == 2) {
					sqlBeg ="SELECT a.f_id orderId, a.f_sn ordersSn,a.f_amount amount, d.f_phone orderPhone, Round(a.f_amount,2) orderPrice,  a.f_status odersStatus,DATE_FORMAT( a.f_create_date,  '%Y.%m.%d %T' ) "
							+ "preOrderDate  ,CASE WHEN a.f_collect_time IS NULL THEN '' ELSE a.f_collect_time END  collectTime  FROM t_order a " +
							"LEFT JOIN t_member d ON a.f_member = d.f_id WHERE a.f_is_online = 1 AND a.f_organization = :shopId  AND   (a.f_status = 3 OR a.f_status = 5)  AND a.f_shipping_method=2  ";
					// 向上加载
					if(ordersId.equals("0")){
						sql=sqlBeg+"AND a.f_id >0  ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
					}else{
						if(pageLoadType==1){
							sql=sqlBeg+"AND a.f_id >:ordersId ORDER BY a.f_create_date ASC LIMIT 0,:pageRowsCount";
						}else{
							sql=sqlBeg+"AND a.f_id <:ordersId ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
						}
					}
				}
				// 执行sql语句
				Query query = entityManager.createNativeQuery(sql);
				query.setParameter("shopId", shopId);
				if(!ordersId.equals("0")){
					query.setParameter("ordersId", ordersId);
				}
				query.setParameter("pageRowsCount", pageRowsCount);
				// 结果集格式化
 				query.unwrap(SQLQuery.class).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP);
				list = query.getResultList();
				if(list.size()>0){
							for(int i=0;i<list.size();i++){
								Map<String,Object> m = (Map<String, Object>) list.get(i);
								Object orderIdObject=m.get("orderId");
								String orderId = String.valueOf(orderIdObject);	
								Object ordersSnObject=m.get("ordersSn");
								String ordersSn = String.valueOf(ordersSnObject);
								Object orderPhoneObject=m.get("orderPhone");
								String orderPhone = String.valueOf(orderPhoneObject);
								Object orderPriceObject=m.get("orderPrice");
								String orderPrice = String.valueOf(orderPriceObject);
								Object amountObject=m.get("amount");
								String amountPrice = String.valueOf(amountObject);
								Object odersStatusObject=m.get("odersStatus");
								String odersStatus = String.valueOf(odersStatusObject);
								Object preOrderDateObject=m.get("preOrderDate");
								preOrderDate = String.valueOf(preOrderDateObject);
								Object collectTimeObject=m.get("collectTime");
								String collectTime = String.valueOf(collectTimeObject);	
								Map<String,Object> orderMap= new HashMap<String, Object>();
								orderMap.put("ordersId",orderId);
								orderMap.put("ordersSn",ordersSn);
								orderMap.put("orderPhone",orderPhone);
								orderMap.put("ordersPrice",orderPrice);
								orderMap.put("collectTime",collectTime);
								orderMap.put("odersStatus",odersStatus);
								orderMap.put("preOrderDate",preOrderDate);
								orderMap.put("amount",amountPrice);
								//sql商品图片，Id信息,向orderMap里插入商品信息
								String sqlProduct="SELECT c.f_id goodsId, b.f_price goodsPrice, c.f_image goodsImagePath, c.f_name goodsName, b.f_quantity goodsNum, b.f_type goodsType " +
										"FROM t_order a LEFT JOIN t_order_item b ON a.f_id = b.f_orders LEFT JOIN t_product c ON b.f_product = c.f_id WHERE a.f_id = :orderId  ";
								//执行sql语句
								Query queryProduct = entityManager.createNativeQuery(sqlProduct);
								queryProduct.setParameter("orderId", orderId);
								//结果集格式化
								queryProduct.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
								listProduct= queryProduct.getResultList();
								List<Object> productList =new ArrayList<Object>();
								//寻找到商品信息
								if(listProduct.hashCode()==0){
									orderMap.put("goods","  ");
								}else{
												for(int k=0;k<listProduct.size();k++){
															//解析商品信息
															Map<String,Object> mProduct = (Map<String, Object>) listProduct.get(k);
															Object goodsIdObject=mProduct.get("goodsId");
															String goodsId = String.valueOf(goodsIdObject);
															Object goodsNameObject=mProduct.get("goodsName");
															String goodsName = String.valueOf(goodsNameObject);
															Object goodsPriceObject=mProduct.get("goodsPrice");
															String goodsPrice = String.valueOf(goodsPriceObject);
															Object goodsImagePathObject=mProduct.get("goodsImagePath");
															String goodsImage = String.valueOf(goodsImagePathObject);
															Object goodsNumObject=mProduct.get("goodsNum");
															String goodsNum = String.valueOf(goodsNumObject);
															Object goodsTypeObject=mProduct.get("goodsType");
															String goodsType = String.valueOf(goodsTypeObject);
															Map<String,Object> productMap= new HashMap<String, Object>();
															productMap.put("goodsId",goodsId);
															productMap.put("goodsName",goodsName);
															productMap.put("goodsPrice",goodsPrice);
															productMap.put("goodsNum",goodsNum);
															productMap.put("goodsType",goodsType);
															productMap.put("goodsImage",goodsImage);
															if(goodsType.equals("1")){
																//获得优惠方式
																String sqlPromotion="SELECT a.f_id promotionId, a.f_promotion_type promotionType, a.f_title promotionTitle FROM t_promotion a "
																		+ "LEFT JOIN t_product_promotion b ON a.f_id = b.f_promotions LEFT JOIN t_product c ON c.f_id = b.f_product WHERE c.f_id =:goodsId  AND "
																+ "DATE_FORMAT( a.f_begin_date,  '%Y.%m.%d %T' ) < :preOrderDate AND DATE_FORMAT( a.f_end_date,  '%Y.%m.%d %T' ) > :preOrderDate   ";
																//执行sql语句
																Query queryPromotion = entityManager.createNativeQuery(sqlPromotion);
																queryPromotion.setParameter("goodsId", goodsId);
																queryPromotion.setParameter("preOrderDate", preOrderDate);
																//结果集格式化
																queryPromotion.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
																listPromotion= queryPromotion.getResultList();
																//如果优惠方式为空，存放空字节
																if(listPromotion.size()>0){
																	productMap.put("promotion",listPromotion);
																}	
															}	
												productList.add(productMap);
									}	
								orderMap.put("goods",productList);
							}
						listResult.add(orderMap);
						}
					//存放最终的执行结果
					mapResult.put(UnivParameter.DATA, listResult);
				}else{
					//存放最终的执行结果
					mapResult.put(UnivParameter.DATA, list);
				}
				// 存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
			} catch (Exception e) {
				// 存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
				return mapResult;
			}
			return mapResult;	
	}

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
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getDistributeOrders(String shopId,
			int ordersType, int pageLoadType, int pageRowsCount, String ordersId) {
		// 初始化map集合
		Map<String, Object> mapResult = new HashMap<String, Object>();
		List<Object> listProduct =new ArrayList<Object>();
		List<Object> listPromotion=new ArrayList<Object>();
		List<Object> listResult = new ArrayList<Object>();
		// 每页加载数据条数(暂定默认10条)
		List<Result> list = null;
		String sql = null;
		String sqlBeg = null;
		String preOrderDate="";
		try {
			// ordersType 订单状态【1：未完成，2：已完成】
			// 1：未完成(线上待发货配送方式发货)
			if (ordersType == 1) {
				sqlBeg = "SELECT a.f_id orderId, a.f_sn ordersSn, d.f_phone orderPhone, Round(a.f_amount,2) orderPrice, a.f_status odersStatus, a.f_address ordersAddress, a.f_area_name ordersFullName, " +
						"a.f_consignee ordersConsignee, a.f_zip_code ordersZipCode, DATE_FORMAT( a.f_create_date, '%Y.%m.%d %T' ) preOrderDate FROM t_order a LEFT JOIN t_member d ON a.f_member = " +
						"d.f_id LEFT JOIN t_area c ON a.f_area = c.f_orders WHERE a.f_is_online = 1 AND a.f_organization = :shopId  AND a.f_status = 2 AND a.f_shipping_method = 1 ";
				// 向上加载
				if(ordersId.equals("0")){
					sql=sqlBeg+"AND a.f_id >0  ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
				}else{
					if(pageLoadType==1){
						sql=sqlBeg+"AND a.f_id >:ordersId ORDER BY a.f_create_date ASC LIMIT 0,:pageRowsCount";
					}else{
						sql=sqlBeg+"AND a.f_id <:ordersId ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
					}
				}
			}
			// 2：已完成(线上待收货配送方式发货)
			if (ordersType == 2) {
				sqlBeg = "SELECT a.f_id orderId, a.f_sn ordersSn, d.f_phone orderPhone, Round(a.f_amount,2) orderPrice, a.f_status odersStatus, a.f_address ordersAddress, a.f_area_name ordersFullName, " +
						"a.f_consignee ordersConsignee, a.f_zip_code ordersZipCode, DATE_FORMAT( a.f_create_date, '%Y.%m.%d %T' ) preOrderDate FROM t_order a LEFT JOIN t_member d ON a.f_member = " +
						"d.f_id LEFT JOIN t_area c ON a.f_area = c.f_orders WHERE a.f_is_online = 1 AND a.f_organization = :shopId  AND a.f_status = 1 AND a.f_shipping_method = 1 ";
				// 向上加载
				if(ordersId.equals("0")){
					sql=sqlBeg+"AND a.f_id >0  ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
				}else{
					if(pageLoadType==1){
						sql=sqlBeg+"AND a.f_id >:ordersId ORDER BY a.f_create_date ASC LIMIT 0,:pageRowsCount";
					}else{
						sql=sqlBeg+"AND a.f_id <:ordersId ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
					}
				}
			}
			// 执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("shopId", shopId);
			if(!ordersId.equals("0")){
				query.setParameter("ordersId", ordersId);
			}
			query.setParameter("pageRowsCount", pageRowsCount);
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.getResultList();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> m = (Map<String, Object>) list.get(i);
					Object orderIdObject=m.get("orderId");
					String orderId = String.valueOf(orderIdObject);	
					Object ordersSnObject=m.get("ordersSn");
					String ordersSn = String.valueOf(ordersSnObject);
					Object orderPhoneObject=m.get("orderPhone");
					String orderPhone = String.valueOf(orderPhoneObject);
					Object ordersAddressObject=m.get("ordersAddress");
					String ordersAddress = String.valueOf(ordersAddressObject);	
					Object ordersFullNameObject=m.get("ordersFullName");
					String ordersFullName = String.valueOf(ordersFullNameObject);
					Object ordersConsigneeObject=m.get("ordersConsignee");
					String ordersConsignee = String.valueOf(ordersConsigneeObject);
					Object ordersZipCodeObject=m.get("ordersZipCode");
					String ordersZipCode = String.valueOf(ordersZipCodeObject);
					Object orderPriceObject=m.get("orderPrice");
					String orderPrice = String.valueOf(orderPriceObject);
					Object odersStatusObject=m.get("odersStatus");
					String odersStatus = String.valueOf(odersStatusObject);
					Object preOrderDateObject=m.get("preOrderDate");
					preOrderDate = String.valueOf(preOrderDateObject);
					Map<String,Object> orderMap= new HashMap<String, Object>();
					orderMap.put("ordersId",orderId);
					orderMap.put("ordersSn",ordersSn);
					orderMap.put("orderPhone",orderPhone);
					orderMap.put("ordersPrice",orderPrice);
					orderMap.put("ordersAddress",ordersAddress);
					orderMap.put("ordersFullName",ordersFullName);
					orderMap.put("ordersConsignee",ordersConsignee);
					orderMap.put("ordersZipCode",ordersZipCode);
					orderMap.put("odersStatus",odersStatus);
					orderMap.put("preOrderDate",preOrderDate);
					List<Object> wuliuList =new ArrayList<Object>();
					Map<String,Object> productMap1= new HashMap<String, Object>();
					Map<String,Object> snMap= new HashMap<String, Object>();
					List<Object> snList = new ArrayList<Object>();
					//已完成订单
					if( ordersType == 2 ){
						Order order = orderDao.find(Long.parseLong(orderId));
						//存放物流信息
						Shipping shipping = shippingDao.find(order.getShippings().iterator().next().getId());
						productMap1.put("freight", shipping.getFreight());
						productMap1.put("deliveryCorp", shipping.getDeliveryCorp());
						productMap1.put("trackingNo", shipping.getTrackingNo());
						wuliuList.add(productMap1);
						orderMap.put("wuliu",wuliuList);
						
						Iterator<StockLog> iterator = shipping.getStockLogs().iterator();
						while (iterator.hasNext()) {
							snMap = new HashMap<String, Object>();
							StockLog stockLog = (StockLog) iterator.next();
							snMap.put(stockLog.getProduct().getId().toString(), stockLog.getProductSn());
							snList.add(snMap);
						}
						orderMap.put("snList",snList);
					}
					
					
					
					
					//sql商品图片，Id信息,向orderMap里插入商品信息
					String sqlProduct="SELECT c.f_id goodsId, b.f_price goodsPrice, c.f_image goodsImagePath, c.f_name goodsName, b.f_quantity goodsNum, b.f_type goodsType " +
							"FROM t_order a LEFT JOIN t_order_item b ON a.f_id = b.f_orders LEFT JOIN t_product c ON b.f_product = c.f_id WHERE a.f_id = :orderId  ";
					//执行sql语句
					Query queryProduct = entityManager.createNativeQuery(sqlProduct);
					queryProduct.setParameter("orderId", orderId);
					//结果集格式化
					queryProduct.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					listProduct= queryProduct.getResultList();
					List<Object> productList =new ArrayList<Object>();
					//寻找到商品信息
					if(listProduct.hashCode()==0){
						orderMap.put("goods","  ");
					}else{
						for(int k=0;k<listProduct.size();k++){
						//解析商品信息
						Map<String,Object> mProduct = (Map<String, Object>) listProduct.get(k);
						Object goodsIdObject=mProduct.get("goodsId");
						String goodsId = String.valueOf(goodsIdObject);
						Object goodsNameObject=mProduct.get("goodsName");
						String goodsName = String.valueOf(goodsNameObject);
						Object goodsPriceObject=mProduct.get("goodsPrice");
						String goodsPrice = String.valueOf(goodsPriceObject);
						Object goodsImagePathObject=mProduct.get("goodsImagePath");
						String goodsImage = String.valueOf(goodsImagePathObject);
						Object goodsNumObject=mProduct.get("goodsNum");
						String goodsNum = String.valueOf(goodsNumObject);
						Object goodsTypeObject=mProduct.get("goodsType");
						String goodsType = String.valueOf(goodsTypeObject);
						Map<String,Object> productMap= new HashMap<String, Object>();
						productMap.put("goodsId",goodsId);
						productMap.put("goodsName",goodsName);
						productMap.put("goodsPrice",goodsPrice);
						productMap.put("goodsNum",goodsNum);
						productMap.put("goodsType",goodsType);
						productMap.put("goodsImage",goodsImage);
						if(goodsType.equals("1")){
							//获得优惠方式
							String sqlPromotion="SELECT a.f_id promotionId, a.f_promotion_type promotionType, a.f_title promotionTitle FROM t_promotion a "
									+ "LEFT JOIN t_product_promotion b ON a.f_id = b.f_promotions LEFT JOIN t_product c ON c.f_id = b.f_product WHERE c.f_id =:goodsId  AND "
							+ "DATE_FORMAT( a.f_begin_date,  '%Y.%m.%d %T' ) < :preOrderDate AND DATE_FORMAT( a.f_end_date,  '%Y.%m.%d %T' ) > :preOrderDate   ";
							//执行sql语句
							Query queryPromotion = entityManager.createNativeQuery(sqlPromotion);
							queryPromotion.setParameter("goodsId", goodsId);
							queryPromotion.setParameter("preOrderDate", preOrderDate);
							//结果集格式化
							queryPromotion.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
							listPromotion= queryPromotion.getResultList();
							//如果优惠方式为空，存放空字节
							if(listPromotion.size()>0){
								productMap.put("promotion",listPromotion);
							}	
						}
							productList.add(productMap);
						}	
						orderMap.put("goods",productList);
				}
			listResult.add(orderMap);
		}
	//存放最终的执行结果
	mapResult.put(UnivParameter.DATA, listResult);
						
			}else{
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, "不存在该订单");
			}
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;	
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getOrdersDetail(String ordersId) {
		// 初始化map集合
		Map<String, Object> mapResult = new HashMap<String, Object>();
		List<Object> listProduct =new ArrayList<Object>();
		List<Object> listResult = new ArrayList<Object>();
		// 每页加载数据条数(暂定默认10条)
		List<Result> list = null;
		String sql = null;
		try {
			//sql商品图片，Id信息,向orderMap里插入商品信息
			String sqlProduct="SELECT c.f_id goodsId, c.f_price goodsPrice, c.f_image goodsImagePath, c.f_name goodsName, b.f_quantity goodsNum FROM t_order a LEFT JOIN" +
					" t_order_item b ON a.f_id = b.f_orders LEFT JOIN t_product c ON b.f_product = c.f_id WHERE a.f_id = :ordersId ";
			//执行sql语句
			Query queryProduct = entityManager.createNativeQuery(sqlProduct);
			queryProduct.setParameter("ordersId", ordersId);
			//结果集格式化
			queryProduct.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listProduct= queryProduct.getResultList();
			sql = "SELECT a.f_id orderId, a.f_sn ordersSn, d.f_phone orderPhone, (a.f_amount-a.f_amount_paid) orderPrice, a.f_consignee memberName,  a.f_address memberAddress, a.f_area_name memberFullName, " +
					"a.f_is_online ordersType, a.f_freight carryCost, a.f_payment_method_name paymentMethod, a.f_refund_amount refundAmount, DATE_FORMAT( a.f_create_date,  '%Y.%m.%d %T') " +
					"preOrderDate FROM t_order a LEFT JOIN t_member d ON a.f_member = d.f_id LEFT JOIN t_area e ON d.f_area = e.f_orders WHERE a.f_id = :ordersId  ";
			// 执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("ordersId", ordersId);
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.getResultList();
			if(list.size()>0){
						for(int i=0;i<list.size();i++){
							Map<String,Object> m = (Map<String, Object>) list.get(i);
							Object orderIdObject=m.get("ordersId");
							String orderId = String.valueOf(orderIdObject);	
							Object ordersSnObject=m.get("ordersSn");
							String ordersSn = String.valueOf(ordersSnObject);
							Object orderPhoneObject=m.get("orderPhone");
							String orderPhone = String.valueOf(orderPhoneObject);
							Object orderPriceObject=m.get("orderPrice");
							String orderPrice = String.valueOf(orderPriceObject);
							Object preOrderDateObject=m.get("preOrderDate");
							String preOrderDate = String.valueOf(preOrderDateObject);
							Object memberNameObject=m.get("memberName");
							String memberName = String.valueOf(memberNameObject);	
							Object memberAddressObject=m.get("memberAddress");
							String memberAddress = String.valueOf(memberAddressObject);
							Object memberFullNameObject=m.get("memberFullName");
							String memberFullName = String.valueOf(memberFullNameObject);
							Object ordersTypeObject=m.get("ordersType");
							String ordersType = String.valueOf(ordersTypeObject);
							Object carryCostObject=m.get("carryCost");
							String carryCost = String.valueOf(carryCostObject);
							Object paymentMethodObject=m.get("paymentMethod");
							String paymentMethod = String.valueOf(paymentMethodObject);
							Object refundAmountObject=m.get("refundAmount");
							String refundAmount = String.valueOf(refundAmountObject);
							Map<String,Object> orderMap= new HashMap<String, Object>();
							orderMap.put("ordersId",orderId);
							orderMap.put("ordersSn",ordersSn);
							orderMap.put("orderPhone",orderPhone);
							orderMap.put("ordersPrice",orderPrice);
							orderMap.put("preOrderDate",preOrderDate);
							orderMap.put("memberName",memberName);
							orderMap.put("memberAddress",memberAddress);
							orderMap.put("memberFullName",memberFullName);
							orderMap.put("ordersType",ordersType);
							orderMap.put("carryCost",carryCost);
							orderMap.put("paymentMethod",paymentMethod);
							orderMap.put("refundAmount",refundAmount);
							//寻找到商品信息
							if(listProduct.hashCode()==0){
								orderMap.put("goods","  ");
							}else{
								orderMap.put("goods",listProduct);
							}	
							listResult.add(orderMap);
				}
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, listResult);
			}else{
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, "不存在该订单");
			}
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;	
	}
	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getInventoryChange(String shopId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		if(shopId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "数据不可为空");
			return mapResult;
		}
		try {
			List<Object> listDistribution=new ArrayList<Object>();
			List<Object> listShipping=new ArrayList<Object>();
			List<Object> listShippingGoods=new ArrayList<Object>();
			List<Object> listShopChang=new ArrayList<Object>();
			List<Object> listShopChangGoods=new ArrayList<Object>();
			List<Object> listAllot=new ArrayList<Object>();
			List<Object> listAllotGoods=new ArrayList<Object>();
			List<Object> listAllotCheck=new ArrayList<Object>();
			List<Object> listResult=new ArrayList<Object>();
			//查询配送单SQL语句	
			String  sqlDistribution="SELECT a.f_bill_state listType, a.f_id listId, b.f_id goodsId, b.f_name goodsName, DATE_FORMAT( a.f_create_date, "
					+ "'%Y.%m.%d %T' ) createTime, b.f_sn goodsSn, CASE WHEN c.f_name IS NULL THEN '暂无'  ELSE c.f_name END  adminName, "
					+ "a.f_number goodsNum FROM t_distribution a LEFT JOIN t_distribution_check  d ON d.f_distribution = a.f_id LEFT JOIN t_product"
					+ " b ON a.f_product = b.f_id LEFT JOIN t_admin c ON d.f_operator = c.f_id WHERE a.f_to_address =:shopId ORDER BY a.f_create_date DESC";
			//执行sql语句
			Query queryDistribution = entityManager.createNativeQuery(sqlDistribution);
			queryDistribution.setParameter("shopId", shopId);
			//结果集格式化
			queryDistribution.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			//结果集格式化
			listDistribution= queryDistribution.getResultList();
			
			//查询发货单SQL语句			
			String sqlShipping="SELECT a.f_id listId, DATE_FORMAT( a.f_create_date, '%Y.%m.%d %T' ) createTime, a.f_operator adminName, b.f_id ordersId FROM t_shipping a LEFT "
					+ "JOIN t_order b ON a.f_orders = b.f_id WHERE b.f_organization =:shopId ORDER BY a.f_create_date DESC ";
			//执行sql语句
			Query queryShipping = entityManager.createNativeQuery(sqlShipping);
			queryShipping.setParameter("shopId", shopId);
			//结果集格式化
			queryShipping.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listShipping= queryShipping.getResultList();
			
			//查询门店退货SQL语句					
//			String sqlShopChang="SELECT a.f_id listId, DATE_FORMAT( a.f_create_date,  '%Y.%m.%d %T' ) createTime, c.f_name adminName FROM t_shop_chang a, t_admin c WHERE" +
//					" a.f_from_address = :shopId  AND a.operator = c.f_id ORDER BY a.f_create_date DESC";
			String sqlShopChang="SELECT a.f_id listId, DATE_FORMAT( a.f_create_date,  '%Y.%m.%d %T' ) createTime, c.f_name adminName FROM t_stock_trans_log a, t_admin c WHERE" +
					" a.f_from_organization = :shopId AND a.f_operator = c.f_id AND a.f_type=2 AND a.f_status  = 2 ORDER BY a.f_create_date DESC";
			//执行sql语句
			Query queryShopChang = entityManager.createNativeQuery(sqlShopChang);
			queryShopChang.setParameter("shopId", shopId);
			//结果集格式化
			queryShopChang.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listShopChang= queryShopChang.getResultList();
			
			//查询门店调拨单SQL语句（门店发货方)	
//			String sqlAllot="SELECT a.f_id listId, DATE_FORMAT(a.f_create_date, '%Y.%m.%d %T') createTime, d.f_name adminName FROM t_allot a LEFT JOIN t_admin d ON a.f_operator = " +
//					"d.f_id WHERE a.f_from_organization = :shopId AND a.f_is_dispose = 1 ORDER BY a.f_create_date DESC";
			String sqlAllot="SELECT a.f_id listId, DATE_FORMAT(a.f_create_date, '%Y.%m.%d %T') createTime, d.f_name adminName FROM t_stock_trans_log a LEFT JOIN t_admin d ON a.f_operator = " +
					"d.f_id WHERE a.f_from_organization = :shopId AND a.f_type=1 AND a.f_status  = 2 ORDER BY a.f_create_date DESC";
			//执行sql语句
			Query queryAllot = entityManager.createNativeQuery(sqlAllot);
			queryAllot.setParameter("shopId", shopId);
			//结果集格式化
			queryAllot.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listAllot= queryAllot.getResultList();
			
			//查询调拨门店单SQL语句（门店收货方)
//			String sqlAllotCheck="SELECT a.f_id listId, DATE_FORMAT(a.f_create_date, '%Y.%m.%d %T') createTime, CASE WHEN c.f_bill_state IS NULL THEN 0 ELSE c.f_bill_state END listType,"
//					+ " e.f_number goodsNum, d.f_id goodsId, d.f_sn goodsSn, CASE WHEN b.f_name IS NULL THEN '暂无' ELSE b.f_name END adminName,d.f_name goodsName FROM t_allot a LEFT JOIN "
//					+ "t_allot_check c ON a.f_id = c.f_allot LEFT JOIN t_admin b ON c.f_operator = b.f_id LEFT JOIN t_product d ON d.f_id = a.f_product LEFT JOIN t_allot_requisition "
//					+ "e ON a.t_allot_requisition_id = e.f_id WHERE a.f_to_organization = :shopId AND a.f_is_dispose = 1 ORDER BY a.f_create_date DESC";
			
			String sqlAllotCheck="SELECT a.f_id listId, DATE_FORMAT(a.f_create_date, '%Y.%m.%d %T') createTime, d.f_name adminName FROM t_stock_trans_log a LEFT JOIN t_admin d ON a.f_operator = " +
					"d.f_id WHERE a.f_to_organization = :shopId AND a.f_type=1 AND a.f_status  = 2 ORDER BY a.f_create_date DESC";
			//执行sql语句
			Query queryAllotCheck = entityManager.createNativeQuery(sqlAllotCheck);
			queryAllotCheck.setParameter("shopId", shopId);
			//结果集格式化
			queryAllotCheck.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			listAllotCheck= queryAllotCheck.getResultList();
			
			//如果查询的返回值不为空		
			/**listType/1：门店退货单（门店到总库，门店库存减少）    2：门店调拨单（门店到门店 门店发货方）3：调拨门店单（门店到门店 门店收货方）
		 	4：配送单（总店配送到门店） 5：发货单（门店库存减少）
			listState  1：未收货 2：已收货 3:未发货4：已发货**/
			if(listShipping.size()>0||listDistribution.size()>0||listShopChang.size()>0||listAllot.size()>0||listAllotCheck.size()>0){
				//退货单				
				if(listShopChang.size()>0){
					for(int i=0;i<listShopChang.size();i++){
						Map<String,Object> m = (Map<String, Object>) listShopChang.get(i);
						Object listIdObject=m.get("listId");
						String listId = String.valueOf(listIdObject);	
						Object createTimeObject=m.get("createTime");
						String createTime = String.valueOf(createTimeObject);
						Object adminNameObject=m.get("adminName");
						String adminName = String.valueOf(adminNameObject);
						//查询发货单的商品子集SQL语句			
//						String sqlShopChangGoods="SELECT e.f_id goodsId, e.f_name goodsName, e.f_sn goodsSn, COUNT(e.f_id) goodsNum FROM t_shop_chang_productsn b LEFT JOIN " +
//								"t_stock_log d ON d.f_id = b.f_product_sn LEFT JOIN t_product e ON d.f_product = e.f_id WHERE b.f_shop_chang = :listId  GROUP BY e.f_id; ";
						String sqlShopChangGoods="SELECT e.f_id goodsId, e.f_name goodsName, e.f_sn goodsSn, COUNT(e.f_id) goodsNum FROM t_stock_trans_productsn b LEFT JOIN " +
								"t_stock_log d ON d.f_id = b.f_product_sn LEFT JOIN t_product e ON d.f_product = e.f_id WHERE b.t_stock_trans_item = :listId  GROUP BY e.f_id; ";
						
						//执行sql语句
						Query queryShopChangGoods = entityManager.createNativeQuery(sqlShopChangGoods);
						queryShopChangGoods.setParameter("listId", listId);
						//结果集格式化
						queryShopChangGoods.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						listShopChangGoods= queryShopChangGoods.getResultList();
						Map<String,Object> resultMap= new HashMap<String, Object>();
						resultMap.put("listId",listId);
						resultMap.put("createTime",createTime);
						resultMap.put("adminName",adminName);
						resultMap.put("listType","1");
						resultMap.put("listState","3");
						//寻找到商品信息
						if(listShopChangGoods.hashCode()==0 || listShopChangGoods.size() <= 0){
							resultMap.put("goods","  ");
						}else{
							resultMap.put("goods",listShopChangGoods);
						}	
						listResult.add(resultMap);
					}
				}
				//门店调拨单				
				if(listAllot.size()>0){
					for(int i=0;i<listAllot.size();i++){
						Map<String,Object> m = (Map<String, Object>) listAllot.get(i);
						Object listIdObject=m.get("listId");
						String listId = String.valueOf(listIdObject);	
						Object createTimeObject=m.get("createTime");
						String createTime = String.valueOf(createTimeObject);
						Object adminNameObject=m.get("adminName");
						String adminName = String.valueOf(adminNameObject);
						//查询发货单的商品子集SQL语句			
//						String sqlAllotGoods="SELECT e.f_id goodsId, e.f_name goodsName, e.f_sn goodsSn, COUNT(c.f_id) goodsNum FROM t_allot_productsn b LEFT JOIN t_stock_log c ON c.f_id " +
//								"= b.f_product_sn LEFT JOIN t_product e ON e.f_id = c.f_product WHERE b.f_allot =:listId  GROUP BY (e.f_id);";
						String sqlAllotGoods="SELECT e.f_id goodsId, e.f_name goodsName, e.f_sn goodsSn, COUNT(c.f_id) goodsNum FROM t_stock_trans_productsn b LEFT JOIN t_stock_log c ON c.f_id " +
								"= b.f_product_sn LEFT JOIN t_product e ON e.f_id = c.f_product WHERE b.t_stock_trans_item =:listId  GROUP BY e.f_id;";
						//执行sql语句
						Query queryAllotGoods = entityManager.createNativeQuery(sqlAllotGoods);
						queryAllotGoods.setParameter("listId", listId);
						//结果集格式化
						queryAllotGoods.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						listAllotGoods= queryAllotGoods.getResultList();
						Map<String,Object> resultMap= new HashMap<String, Object>();
						resultMap.put("listId",listId);
						resultMap.put("createTime",createTime);
						resultMap.put("adminName",adminName);
						resultMap.put("listType","2");
						resultMap.put("listState","4");
						//寻找到商品信息
						if(listAllotGoods.hashCode()==0 || listAllotGoods.size() <= 0){
							resultMap.put("goods","  ");
						}else{
							resultMap.put("goods",listAllotGoods);
						}	
						listResult.add(resultMap);
					}
				}
				//调拨门店单				
				if(listAllotCheck.size()>0){
					for(int i=0;i<listAllotCheck.size();i++){
						Map<String,Object> m = (Map<String, Object>) listAllotCheck.get(i);
						Object listIdObject=m.get("listId");
						String listId = String.valueOf(listIdObject);	
						Object createTimeObject=m.get("createTime");
						String createTime = String.valueOf(createTimeObject);
						Object adminNameObject=m.get("adminName");
						String adminName = String.valueOf(adminNameObject);
						
						
						String sqlAllotGoods="SELECT e.f_id goodsId, e.f_name goodsName, e.f_sn goodsSn, COUNT(c.f_id) goodsNum FROM t_stock_trans_productsn b LEFT JOIN t_stock_log c ON c.f_id " +
								"= b.f_product_sn LEFT JOIN t_product e ON e.f_id = c.f_product WHERE b.t_stock_trans_item =:listId  GROUP BY e.f_id;";
						//执行sql语句
						Query queryAllotGoods = entityManager.createNativeQuery(sqlAllotGoods);
						queryAllotGoods.setParameter("listId", listId);
						//结果集格式化
						queryAllotGoods.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						listAllotCheck= queryAllotGoods.getResultList();
//						Object listTypeObject=m.get("listType");
//						String listType = String.valueOf(listTypeObject);
//						Object goodsIdObject=m.get("goodsId");
//						String goodsId = String.valueOf(goodsIdObject);	
//						Object goodsNameObject=m.get("goodsName");
//						String goodsName = String.valueOf(goodsNameObject);
//						Object goodsNumObject=m.get("goodsNum");
//						String goodsNum = String.valueOf(goodsNumObject);
//						Object goodsSnObject=m.get("goodsSn");
//						String goodsSn = String.valueOf(goodsSnObject);
//						if(goodsSn =="null"){
//							continue;
//						}
						Map<String,Object> resultMap= new HashMap<String, Object>();
						resultMap.put("listId",listId);
						resultMap.put("createTime",createTime);
						resultMap.put("adminName",adminName);
//						resultMap.put("goodsId",goodsId);
//						resultMap.put("goodsName",goodsName);
//						resultMap.put("goodsNum",goodsNum);
//						resultMap.put("goodsSn",goodsSn);
						resultMap.put("listType","3");
						resultMap.put("listState","4");
//						if(listType.equals("1")){
//							resultMap.put("listState","4");
//						}else{
//							resultMap.put("listState","1");
//						}
						//寻找到商品信息
						if(listAllotCheck.hashCode()==0 || listAllotCheck.size() <= 0){
							resultMap.put("goods","  ");
						}else{
							resultMap.put("goods",listAllotCheck);
						}	
						listResult.add(resultMap);
					}
				}
				//	配送单			
				if(listDistribution.size()>0){
					for(int i=0;i<listDistribution.size();i++){
						Map<String,Object> m = (Map<String, Object>) listDistribution.get(i);
						Object listIdObject=m.get("listId");
						String listId = String.valueOf(listIdObject);	
						Object createTimeObject=m.get("createTime");
						String createTime = String.valueOf(createTimeObject);
						Object adminNameObject=m.get("adminName");
						String adminName = String.valueOf(adminNameObject);
						Object listTypeObject=m.get("listType");
						String listType = String.valueOf(listTypeObject);
						Object goodsIdObject=m.get("goodsId");
						String goodsId = String.valueOf(goodsIdObject);	
						Object goodsNameObject=m.get("goodsName");
						String goodsName = String.valueOf(goodsNameObject);
						Object goodsNumObject=m.get("goodsNum");
						String goodsNum = String.valueOf(goodsNumObject);
						Object goodsSnObject=m.get("goodsSn");
						String goodsSn = String.valueOf(goodsSnObject);
						if(goodsSn =="null"){
							continue;
						}
						Map<String,Object> resultMap= new HashMap<String, Object>();
						resultMap.put("listId",listId);
						resultMap.put("createTime",createTime);
						resultMap.put("adminName",adminName);
						resultMap.put("goodsId",goodsId);
						resultMap.put("goodsName",goodsName);
						resultMap.put("goodsNum",goodsNum);
						resultMap.put("goodsSn",goodsSn);
						resultMap.put("listType","4");
						if(listType.equals("0")){
							resultMap.put("listState","1");
						}else{
							resultMap.put("listState","4");
						}
						listResult.add(resultMap);
					}
				}
				//发货单				
				if(listShipping.size()>0){
					for(int i=0;i<listShipping.size();i++){
						Map<String,Object> m = (Map<String, Object>) listShipping.get(i);
						Object listIdObject=m.get("listId");
						String listId = String.valueOf(listIdObject);	
						Object createTimeObject=m.get("createTime");
						String createTime = String.valueOf(createTimeObject);
						Object adminNameObject=m.get("adminName");
						String adminName = String.valueOf(adminNameObject);
						Object ordersIdObject=m.get("ordersId");
						String ordersId = String.valueOf(ordersIdObject);
						//查询发货单的商品子集SQL语句			
						String sqlShippingGoods="SELECT c.f_id goodsId, c.f_price goodsPrice, c.f_name goodsName, b.f_quantity goodsNum, c.f_sn goodsSn FROM t_order a LEFT JOIN t_order_item " +
								"b ON a.f_id = b.f_orders LEFT JOIN t_product c ON b.f_product = c.f_id WHERE a.f_id = :ordersId  ";
						//执行sql语句
						Query queryShippingGoods = entityManager.createNativeQuery(sqlShippingGoods);
						queryShippingGoods.setParameter("ordersId", ordersId);
						//结果集格式化
						queryShippingGoods.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						listShippingGoods= queryShippingGoods.getResultList();
						Map<String,Object> resultMap= new HashMap<String, Object>();
						resultMap.put("listId",listId);
						resultMap.put("createTime",createTime);
						resultMap.put("adminName",adminName);
						resultMap.put("listType","5");
						resultMap.put("listState","2");
						//寻找到商品信息
						if(listShippingGoods.hashCode()==0 || listShippingGoods.size() <= 0 ){
							resultMap.put("goods","  ");
						}else{
							resultMap.put("goods",listShippingGoods);
						}	
						listResult.add(resultMap);
					}
				}
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, listResult);
			}else{
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, " ");
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
		
		return mapResult;
	}


	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getInventoryChangeDetail(String listId,
			int listType) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list = null;
		String  	sql=null;
		if(listId==null&&listType<1&&listType>5){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "数据为空或错误");
			return mapResult;
		}
		try {
			/** listType 单类别1：门店退货单（门店到总库，门店库存减少）    2：门店调拨单（门店到门店 门店发货方）3：调拨门店单（门店到门店 门店收货方）
		 	4：配送单（总店配送到门店） 5：发货单（门店库存减少）**/
			switch(listType)
			{
			    case 1:
			    	sql="SELECT c.f_product_sn FROM t_shop_chang_productsn b LEFT JOIN t_stock_log c ON b.f_product_sn = c.f_id LEFT JOIN t_shop_chang a ON a.f_id = b.f_shop_chang WHERE a.f_id = :listId  ";
			     break;
			    case 2:
			    	sql="SELECT b.f_product_sn FROM t_allot_productsn a LEFT JOIN t_stock_log b ON a.f_product_sn = b.f_id LEFT JOIN t_allot c ON a.f_allot = c.f_id WHERE c.f_id = :listId  ";
			    break;
			    case 3:
			    	sql="SELECT b.f_product_sn FROM t_allot_productsn a LEFT JOIN t_stock_log b ON a.f_product_sn = b.f_id LEFT JOIN t_allot c ON a.f_allot = c.f_id WHERE c.f_id = :listId  ";
			    break;
			    case 4:
			    	sql="SELECT b.f_product_sn FROM t_distribution_productsn a LEFT JOIN t_stock_log b ON a.f_product_sn = b.f_id LEFT JOIN t_distribution c ON a.f_distribution = c.f_id WHERE c.f_id = :listId  ";
			     break;
			    case 5:
			    	sql="SELECT a.f_sn FROM t_shipping_item a WHERE a.f_shipping= :listId  ";
			     break;
			}
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("listId", listId);
			list= query.getResultList();
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
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	@Override
	public Map<String, Object> getAfterSale(String shopId , Pageable pageable) {
		// 初始化map集合
		Map<String, Object> mapResult = new HashMap<String, Object>();
		if(shopId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "数据为空或错误");
			return mapResult;
		}
		List<Object> listProduct =new ArrayList<Object>();
		List<Object> listResult = new ArrayList<Object>();
		Map<String,Object> orderMap= new HashMap<String, Object>();
		Map<String,Object> map= new HashMap<String, Object>();
		Page<ReturnOrder> page = new Page<ReturnOrder>();
		try {
			Organization organization = organizationDao.find(Long.parseLong(shopId));
			page = returnOrderDao.findByOrg(organization,pageable);
			if(page.getTotal() > 0){
				for (ReturnOrder returnOrder : page.getContent()) {
					
					orderMap = new HashMap<String, Object>();
					map = new HashMap<String, Object>();
					Long ordersId = returnOrder.getId();
					String adminName = "";
					//TODO 完成时间还是创建时间
					String createTime = format.format(returnOrder.getCreateDate());	
					Set<ReturnOrderLog> logs = returnOrder.getReturnOrderLogs();
					if(logs.size() > 0){
						List<ReturnOrderLog> list = new ArrayList<ReturnOrderLog>(logs);
						adminName = list.get(list.size() - 1).getOperator() != null ? list.get(list.size() - 1).getOperator().getName() : "客户操作";
					}
					String ordersSn = returnOrder.getSn();
					List<StockLog> items = new ArrayList<StockLog>(returnOrder.getStockLogs());
					if( items.size() > 0){
						orderMap.put("ordersId",ordersId);
						orderMap.put("dingdanId",returnOrder.getOrder().getSn());
						orderMap.put("ordersSn",ordersSn);
						orderMap.put("createTime",createTime);
						orderMap.put("adminName",adminName);
						orderMap.put("orderStatus",returnOrder.getStatus().ordinal());
						orderMap.put("orderType",returnOrder.getType().ordinal());
						for (StockLog returnOrderItem : items) {
							listProduct = new ArrayList<Object>();
							Product product = returnOrderItem.getProduct();
							map = new HashMap<String, Object>();
							map.put("goodsId",product.getId());
							map.put("goodsSn",product.getSn());
							map.put("sn",returnOrderItem.getProductSn());
							map.put("goodsName",product.getName());
							listProduct.add(map);
							orderMap.put("goods", listProduct);
						}
						listResult.add(orderMap);
					}
					
				}
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, listResult);
			}else{
				//存放最终的执行结果
				mapResult.put(UnivParameter.DATA, "不存在该订单");
			}
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;	
	}


	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getAdminList(String shopId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		if(shopId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "数据为空或错误");
			return mapResult;
		}
		try {
			//SQL语句
			sql="SELECT a.f_id adminId, a.f_name adminName, a.f_jobnumber adminJobNumber, a.f_card_code adminCardCode, a.f_office adminOffice, a.f_phone adminPhone," +
					" DATE_FORMAT(a.f_create_date, '%Y.%m.%d %T') entryTime, a.f_image adminImage FROM t_admin a WHERE a.f_organization = :shopId AND a.f_is_enabled=1	"
					+ " ORDER BY a.f_office ASC ";
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
			mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> dataManagement(String adminId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result= null;
		List<Result> list=null;
		List<Result> listWeixin=null;
		List<Result> listCash=null;
		List<Result> listUnion=null;
		List<Result> listalipay=null;
		String sql=null;
		String sqlPayment=null;
		String weixinPayment = "0" ;
		String cashPayment = "0" ;
		String unionPayment = "0" ;
		String alipaypayment = "0" ;
		//获得微信支付ID		
	   	String	weixinpaymentId=CommonParameter.WEIXIN_PAYMENT;
	   	//获得现金支付ID
	   	String cashpaymentId=CommonParameter.CASH_PAYMENT;
	   	//获得银联支付ID	   	
	   	String unionpaymentId=CommonParameter.UNION_PAYMENT;
	   	//获得支付宝支付ID	   	
	   	String alipaypaymentId=CommonParameter.ALIPAY_PAYMENT;
	   	Admin admin = adminDaoImpl.find(Long.parseLong(adminId));
		if(admin==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "数据为空或错误");
			return mapResult;
		}
		String shopId = admin.getOrganization();
		String shopName = organizationDao.find(Long.parseLong(shopId)).getName();
		try {
			//SQL语句
			sql = "SELECT count(b.f_id) adminOrdersFinished, CASE WHEN sum(b.f_amount) IS NULL THEN 0 ELSE  Round(SUM(b.f_amount),2)  END adminTurnover, c.f_name adminName, d.f_name shopName, c.f_jobnumber " +
					"adminJobNumber, date(NOW()) date FROM t_admin_order_log a LEFT JOIN t_order b ON a.f_order = b.f_id LEFT JOIN t_admin c ON a.f_admin = c.f_id LEFT JOIN t_organization " +
					"d ON c.f_organization = d.f_id WHERE c.f_id = :adminId  AND b.f_status = 5 AND DATE(b.f_complete_date) = DATE(NOW());";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("adminId", adminId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			//SQL语句
			sqlPayment = "SELECT CASE WHEN sum(b.f_amount) IS NULL THEN 0 ELSE Round(SUM(b.f_amount), 2) END payment FROM t_admin_order_log a "
					+ "LEFT JOIN t_order b ON a.f_order = b.f_id LEFT JOIN t_admin c ON a.f_admin = c.f_id LEFT JOIN t_payment_log d ON b.f_id = d.orders "
					+ "WHERE c.f_id = :adminId  AND b.f_status = 5 AND DATE(b.f_complete_date) = DATE(NOW()) AND d.payment_plugin_id = :paymentId  ";
			//执行sql语句
			Query queryPaymeny = entityManager.createNativeQuery(sqlPayment);
			queryPaymeny.setParameter("adminId", adminId);
			for(int i=0;i<4;i++){
				if(i==0){
					queryPaymeny.setParameter("paymentId", weixinpaymentId);
					queryPaymeny.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					listWeixin=queryPaymeny.getResultList();
					if( listWeixin.size() > 0 ){
						Map<String,Object> mm = (Map<String, Object>) listWeixin.get(0);
						Object weixinPaymentObject=mm.get("payment");
						weixinPayment = String.valueOf(weixinPaymentObject);
					}
				}else if(i==1){
					queryPaymeny.setParameter("paymentId", cashpaymentId);
					queryPaymeny.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					listCash=queryPaymeny.getResultList();
					if( listCash.size() > 0){
						Map<String,Object> mmm= (Map<String, Object>) listCash.get(0);
						Object cashPaymentObject=mmm.get("payment");
						cashPayment = String.valueOf(cashPaymentObject);
					}
				}else if(i==2) {
					queryPaymeny.setParameter("paymentId", alipaypaymentId);
					queryPaymeny.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					listalipay=queryPaymeny.getResultList();
					if( listalipay.size() > 0){
						Map<String,Object> mmm= (Map<String, Object>) listalipay.get(0);
						Object alipayPaymentObject=mmm.get("payment");
						alipaypayment = String.valueOf(alipayPaymentObject);
					}
				}else{
					queryPaymeny.setParameter("paymentId", unionpaymentId);
					queryPaymeny.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					listUnion=queryPaymeny.getResultList();
					if( listCash.size() > 0){
						Map<String,Object> mmmm= (Map<String, Object>) listUnion.get(0);
						Object unionPaymentObject=mmmm.get("payment");
						unionPayment = String.valueOf(unionPaymentObject);
					}
				}
			}
			if( list.size()>0 && listWeixin.size() >0 && listCash.size()>0 && listUnion.size()>0 ){
				Map<String,Object> m = (Map<String, Object>) list.get(0);
				Object adminOrdersFinishedObject=m.get("adminOrdersFinished");
				String adminOrdersFinished = String.valueOf(adminOrdersFinishedObject);
				Object adminTurnoverObject=m.get("adminTurnover");
				String adminTurnover = String.valueOf(adminTurnoverObject);
				Object dateObject=m.get("date");
				String date = String.valueOf(dateObject);
				result=new  HashMap<String,Object>();
				result.put("adminOrdersFinished",adminOrdersFinished);
				result.put("adminTurnover",adminTurnover);
				result.put("adminName",admin.getName());
				result.put("shopName",shopName);
				result.put("adminJobNumber",admin.getJobNumber());
				result.put("date",date);
				result.put("weixinPayment",weixinPayment);
				result.put("cashPayment",cashPayment);
				result.put("unionPayment",unionPayment);
				result.put("alipaypayment",alipaypayment);
				mapResult.put(UnivParameter.DATA, result);
			}else{
				mapResult.put(UnivParameter.DATA,result);
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
		return mapResult;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> advisoryCenterList(String shopId,int type) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		if(shopId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "数据为空或错误");
			return mapResult;
		}
		try {
			switch (type) {
				//新闻
			case 1:
				//SQL语句
				sql = "SELECT a.f_title shopActivityTitle, DATE_FORMAT( a.f_create_date, '%Y.%m.%d %T' ) shopActivityDate, a.f_id shopActivityId, a.f_content shopActivityContent, " +
						"a.f_image shopActivityLogo,CASE WHEN a.f_author IS NULL THEN ''  ELSE a.f_author END  shopActivityAuthor FROM t_information a LEFT JOIN t_organization_information"
						+ " b ON b.f_information = a.f_id LEFT JOIN t_organization c ON c.f_id = b.f_organization WHERE c.f_id = :shopId AND a.f_is_display = 1 ORDER BY "
						+ "a.f_create_date DESC ";
				break;
				//公告
			case 2:
				//SQL语句
				sql = "SELECT a.f_title shopActivityTitle, DATE_FORMAT( a.f_create_date, '%Y.%m.%d %T' ) shopActivityDate, a.f_id shopActivityId, a.f_content shopActivityContent, " +
						"d.f_name shopActivityAuthor FROM t_inform a LEFT JOIN t_organization_inform b ON b.f_inform = a.f_id LEFT JOIN t_admin d ON d.f_id = a.f_creater"
						+ " LEFT JOIN t_organization c ON c.f_id = b.f_organization WHERE c.f_id = :shopId AND a.f_is_display = 1 ORDER BY a.f_create_date DESC ";
				break;
			default:
				break;
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
			mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getAdvisoryDetail(String shopActivityId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		if(shopActivityId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "数据为空或错误");
			return mapResult;
		}
		try {
			//SQL语句
			sql = "SELECT a.f_title shopActivityTitle, a.f_content shopActivityContent, a.f_id shopActivityId,CASE WHEN a.f_author IS NULL THEN ''  ELSE a.f_author END  shopActivityAuthor FROM t_information a WHERE a.f_id =  :shopActivityId  ";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("shopActivityId", shopActivityId);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
			
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
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

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
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> changeTakeOrders(String shopId, int ordersType,int pageLoadType, int pageRowsCount, String ordersId, String num, int type) {
        // 初始化map集合
        Map<String, Object> mapResult = new HashMap<String, Object>();
        List<Object> listProduct = new ArrayList<Object>();
        List<Object> listPromotion = new ArrayList<Object>();
        List<Object> listResult = new ArrayList<Object>();
        // 每页加载数据条数(暂定默认10条)
        List<Result> list = null;
        String sql = null;
        String preOrderDate = "";
        if (ordersType < 1 || ordersType > 2 || type < 1 || type > 2) {
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "类型选择错误");
            return mapResult;
        }
        try {
            // ordersType 订单状态【1：未完成，2：已完成】
            if (ordersType == 1) {
                // 1：未完成(线上未付款或待自提配送方式自提)
                sql = " SELECT a.f_id orderId, a.f_sn ordersSn, d.f_phone orderPhone, Round(a.f_amount - a.f_amount_paid,2) orderPrice, a.f_status odersStatus, DATE_FORMAT( a.f_create_date, " +
                        "'%Y.%m.%d %T' ) preOrderDate  ,CASE WHEN a.f_collect_time IS NULL THEN '' ELSE a.f_collect_time END  collectTime  FROM t_order a LEFT JOIN t_member d ON "
                        + "a.f_member = d.f_id WHERE a.f_is_online = 1 AND a.f_organization = :shopId  AND a.f_status = 0 AND a.f_shipping_method = 2 AND a.f_is_delete = 0 ";
                
            } else if (ordersType == 2) {
                // 2：已完成(线上完成配送方式自提)
                sql = " SELECT a.f_id orderId, a.f_sn ordersSn, d.f_phone orderPhone, Round(a.f_amount ,2) orderPrice,  a.f_status odersStatus,DATE_FORMAT( a.f_create_date,  '%Y.%m.%d %T' )" +
                        " preOrderDate,CASE WHEN a.f_collect_time IS NULL THEN '' ELSE a.f_collect_time END  collectTime FROM t_order a LEFT JOIN t_member d ON a.f_member = d.f_id WHERE "
                        + "a.f_is_online = 1 AND a.f_organization = :shopId  AND   (a.f_status = 3 OR a.f_status = 5)  AND a.f_shipping_method=2  AND a.f_is_delete = 0  ";
            }
            
            if( type == 1 ){
                sql += " AND d.f_phone like :num ";
            }else{
                sql += " AND a.f_sn like :num ";
            }
            
            // 向上加载
            if(ordersId.equals("0")){
                sql=sql+" AND a.f_id >0  ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
            }else{
                if(pageLoadType==1){
                    sql=sql+" AND a.f_id >:ordersId ORDER BY a.f_create_date ASC LIMIT 0,:pageRowsCount";
                }else{
                    sql=sql+" AND a.f_id <:ordersId ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
                }
            }

            // 执行sql语句
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("shopId", shopId);
            query.setParameter("num", "%" + num + "%");
            if(!ordersId.equals("0")){
                query.setParameter("ordersId", ordersId);
            }
            query.setParameter("pageRowsCount", pageRowsCount);
            // 结果集格式化
            query.unwrap(SQLQuery.class).setResultTransformer(
                    Transformers.ALIAS_TO_ENTITY_MAP);
            list = query.getResultList();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> m = (Map<String, Object>) list.get(i);
                    Object orderIdObject = m.get("orderId");
                    String orderId = String.valueOf(orderIdObject);
                    Object ordersSnObject = m.get("ordersSn");
                    String ordersSn = String.valueOf(ordersSnObject);
                    Object orderPhoneObject = m.get("orderPhone");
                    String orderPhone = String.valueOf(orderPhoneObject);
                    Object orderPriceObject = m.get("orderPrice");
                    String orderPrice = String.valueOf(orderPriceObject);
                    Object odersStatusObject = m.get("odersStatus");
                    String odersStatus = String.valueOf(odersStatusObject);
                    Object preOrderDateObject = m.get("preOrderDate");
                    preOrderDate = String.valueOf(preOrderDateObject);
                    Object collectTimeObject = m.get("collectTime");
                    String collectTime = String.valueOf(collectTimeObject);
                    Map<String, Object> orderMap = new HashMap<String, Object>();
                    orderMap.put("ordersId", orderId);
                    orderMap.put("ordersSn", ordersSn);
                    orderMap.put("orderPhone", orderPhone);
                    orderMap.put("ordersPrice", orderPrice);
                    orderMap.put("odersStatus", odersStatus);
                    orderMap.put("preOrderDate", preOrderDate);
                    orderMap.put("collectTime", collectTime);
                    //sql商品图片，Id信息,向orderMap里插入商品信息
                    String sqlProduct = "SELECT c.f_id goodsId, b.f_price goodsPrice, c.f_image goodsImagePath, c.f_name goodsName, b.f_quantity goodsNum, b.f_type goodsType " +
                            "FROM t_order a LEFT JOIN t_order_item b ON a.f_id = b.f_orders LEFT JOIN t_product c ON b.f_product = c.f_id WHERE a.f_id = :orderId  ";
                    //执行sql语句
                    Query queryProduct = entityManager.createNativeQuery(sqlProduct);
                    queryProduct.setParameter("orderId", orderId);
                    //结果集格式化
                    queryProduct.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                    listProduct = queryProduct.getResultList();
                    List<Object> productList = new ArrayList<Object>();
                    //寻找到商品信息
                    if (listProduct.hashCode() == 0) {
                        orderMap.put("goods", "  ");
                    } else {
                        for (int k = 0; k < listProduct.size(); k++) {
                            //解析商品信息
                            Map<String, Object> mProduct = (Map<String, Object>) listProduct.get(k);
                            Object goodsIdObject = mProduct.get("goodsId");
                            String goodsId = String.valueOf(goodsIdObject);
                            Object goodsNameObject = mProduct.get("goodsName");
                            String goodsName = String.valueOf(goodsNameObject);
                            Object goodsPriceObject = mProduct.get("goodsPrice");
                            String goodsPrice = String.valueOf(goodsPriceObject);
                            Object goodsImagePathObject = mProduct.get("goodsImagePath");
                            String goodsImage = String.valueOf(goodsImagePathObject);
                            Object goodsNumObject = mProduct.get("goodsNum");
                            String goodsNum = String.valueOf(goodsNumObject);
                            Object goodsTypeObject = mProduct.get("goodsType");
                            String goodsType = String.valueOf(goodsTypeObject);
                            Map<String, Object> productMap = new HashMap<String, Object>();
                            productMap.put("goodsId", goodsId);
                            productMap.put("goodsName", goodsName);
                            productMap.put("goodsPrice", goodsPrice);
                            productMap.put("goodsNum", goodsNum);
                            productMap.put("goodsType", goodsType);
                            productMap.put("goodsImage", goodsImage);
                            if (goodsType.equals("1")) {
                                //获得优惠方式
                                String sqlPromotion = "SELECT a.f_id promotionId, a.f_promotion_type promotionType, a.f_title promotionTitle FROM t_promotion a "
                                        + "LEFT JOIN t_product_promotion b ON a.f_id = b.f_promotions LEFT JOIN t_product c ON c.f_id = b.f_product WHERE c.f_id =:goodsId  AND "
                                        + "DATE_FORMAT( a.f_begin_date,  '%Y.%m.%d %T' ) < :preOrderDate AND DATE_FORMAT( a.f_end_date,  '%Y.%m.%d %T' ) > :preOrderDate   ";
                                //执行sql语句
                                Query queryPromotion = entityManager.createNativeQuery(sqlPromotion);
                                queryPromotion.setParameter("goodsId", goodsId);
                                queryPromotion.setParameter("preOrderDate", preOrderDate);
                                //结果集格式化
                                queryPromotion.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                                listPromotion = queryPromotion.getResultList();
                                //如果优惠方式为空，存放空字节
                                if (listPromotion.size() > 0) {
                                    productMap.put("promotion", listPromotion);
                                }
                            }
                            productList.add(productMap);
                        }
                        orderMap.put("goods", productList);
                    }
                    listResult.add(orderMap);
                }
                //存放最终的执行结果
                mapResult.put(UnivParameter.DATA, listResult);
            } else {
                //存放最终的执行结果
                mapResult.put(UnivParameter.DATA, list);
            }
            // 存放正确的返回参数CODE--1
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
        } catch (Exception e) {
            // 存放错误的返回参数CODE--0
            mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
            return mapResult;
        }
        return mapResult;
    }

	
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getDeliveryCorpList() {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Result> list=null;
		String sql=null;
		
		try {
			//SQL语句
			sql = "SELECT a.f_id deliveryCorpId, a.f_name deliveryCorpName FROM t_delivery_corp a";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list= query.getResultList();
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
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}

	/**
	 *  门店订单列表获取条件查询.
	 * author: yanzhisen
	 *   date: 2016-03-07 下午16:00:08
	 * @param shopId   门店编号
	 * @param ordersType 订单状态【1：未完成，2：已完成】
	 * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
	 * @param pageRowsCount 每页加载数据条数
	 * @param ordersId 起始订单编号
	 * @param num 查询内容
	 * @param type 类型：1手机号 2订单号
	 * @return
	 */
	@SuppressWarnings( "unchecked")
	@Override
	public Map<String, Object> getShopOrdersByCond(String shopId,
			int ordersType, int pageLoadType, int pageRowsCount,
			String ordersId, String num, int type) {
		// 初始化map集合
		Map<String, Object> mapResult = new HashMap<String, Object>();
		List<Object> listProduct =new ArrayList<Object>();
		List<Object> listPromotion=new ArrayList<Object>();
		List<Object> listResult = new ArrayList<Object>();
		// 每页加载数据条数(暂定默认10条)
		List<Result> list = null;
		String sql = null;
		String sqlBeg = "SELECT a.f_id orderId, a.f_sn ordersSn, d.f_phone orderPhone, Round(a.f_amount - a.f_amount_paid,2)  orderPrice,a.f_status odersStatus,DATE_FORMAT( a.f_create_date, '%Y.%m.%d %T' ) preOrderDate FROM t_order a " +
					"LEFT JOIN t_member d ON a.f_member = d.f_id WHERE a.f_is_online = 0 AND a.f_organization = :shopId AND a.f_is_delete=0  ";
		String preOrderDate="";
		try {
			// ordersType 订单状态【1：未完成，2：已完成】
			switch (ordersType) {
				// 1：未完成(线下未付款)
				case 1:
					sqlBeg =sqlBeg+"AND a.f_status = 0 ";
					break;
				// 2：已完成(线下等待发货或者完成)
				case 2:
					sqlBeg =sqlBeg+"AND (a.f_status = 3 or a.f_status = 5) ";
					break;
				default:
					break;
			}
			// type 查询类型【1：手机号，2：订单号】
			switch (type) {
				// 1：手机号
				case 1:
					sqlBeg =sqlBeg+"AND d.f_phone like :num ";
					break;
				// 2：订单号
				case 2:
					sqlBeg =sqlBeg+"AND a.f_sn like :num ";
					break;
				default:
					break;
			}
			// 向上加载
			if(ordersId.equals("0")){
				sql=sqlBeg+"AND a.f_id >0  ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
			}else{
				if(pageLoadType==1){
					sql=sqlBeg+"AND a.f_id >:ordersId ORDER BY a.f_create_date ASC LIMIT 0,:pageRowsCount";
				}else{
					sql=sqlBeg+"AND a.f_id <:ordersId ORDER BY a.f_create_date DESC LIMIT 0,:pageRowsCount";
				}
			}
		
			// 执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("shopId", shopId);
			query.setParameter("num", "%" + num + "%");
			if(!ordersId.equals("0")){
				query.setParameter("ordersId", ordersId);
			}
			query.setParameter("pageRowsCount", pageRowsCount);
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.getResultList();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> m = (Map<String, Object>) list.get(i);
					Object orderIdObject=m.get("orderId");
					String orderId = String.valueOf(orderIdObject);	
					Object ordersSnObject=m.get("ordersSn");
					String ordersSn = String.valueOf(ordersSnObject);
					Object orderPhoneObject=m.get("orderPhone");
					String orderPhone = String.valueOf(orderPhoneObject);
					Object orderPriceObject=m.get("orderPrice");
					String orderPrice = String.valueOf(orderPriceObject);
					Object odersStatusObject=m.get("odersStatus");
					String odersStatus = String.valueOf(odersStatusObject);
					Object preOrderDateObject=m.get("preOrderDate");
					preOrderDate = String.valueOf(preOrderDateObject);
					Map<String,Object> orderMap= new HashMap<String, Object>();
					orderMap.put("ordersId",orderId);
					orderMap.put("ordersSn",ordersSn);
					orderMap.put("orderPhone",orderPhone);
					orderMap.put("ordersPrice",orderPrice);
					orderMap.put("odersStatus",odersStatus);
					orderMap.put("preOrderDate",preOrderDate);
					//sql商品图片，Id信息,向orderMap里插入商品信息
					String sqlProduct="SELECT c.f_id goodsId, b.f_price goodsPrice, c.f_image goodsImagePath, c.f_name goodsName, b.f_quantity goodsNum, b.f_type goodsType " +
							"FROM t_order a LEFT JOIN t_order_item b ON a.f_id = b.f_orders LEFT JOIN t_product c ON b.f_product = c.f_id WHERE a.f_id = :orderId  ";
					//执行sql语句
					Query queryProduct = entityManager.createNativeQuery(sqlProduct);
					queryProduct.setParameter("orderId", orderId);
					//结果集格式化
					queryProduct.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					listProduct= queryProduct.getResultList();
					List<Object> productList =new ArrayList<Object>();		
					//寻找到商品信息
					if(listProduct.hashCode()==0){
						orderMap.put("goods","  ");
					}else{
						for(int k=0;k<listProduct.size();k++){
							//解析商品信息
							Map<String,Object> mProduct = (Map<String, Object>) listProduct.get(k);
							Object goodsIdObject=mProduct.get("goodsId");
							String goodsId = String.valueOf(goodsIdObject);
							Object goodsNameObject=mProduct.get("goodsName");
							String goodsName = String.valueOf(goodsNameObject);
							Object goodsPriceObject=mProduct.get("goodsPrice");
							String goodsPrice = String.valueOf(goodsPriceObject);
							Object goodsImagePathObject=mProduct.get("goodsImagePath");
							String goodsImage = String.valueOf(goodsImagePathObject);
							Object goodsNumObject=mProduct.get("goodsNum");
							String goodsNum = String.valueOf(goodsNumObject);
							Object goodsTypeObject=mProduct.get("goodsType");
							String goodsType = String.valueOf(goodsTypeObject);
							Map<String,Object> productMap= new HashMap<String, Object>();
							productMap.put("goodsId",goodsId);
							productMap.put("goodsName",goodsName);
							productMap.put("goodsPrice",goodsPrice);
							productMap.put("goodsNum",goodsNum);
							productMap.put("goodsType",goodsType);
							productMap.put("goodsImage",goodsImage);		
							if(goodsType.equals("1")){
								//获得优惠方式
								String sqlPromotion="SELECT a.f_id promotionId, a.f_promotion_type promotionType, a.f_title promotionTitle FROM t_promotion a "
										+ "LEFT JOIN t_product_promotion b ON a.f_id = b.f_promotions LEFT JOIN t_product c ON c.f_id = b.f_product WHERE c.f_id =:goodsId  AND "
								+ "DATE_FORMAT( a.f_begin_date,  '%Y.%m.%d %T' ) < :preOrderDate AND DATE_FORMAT( a.f_end_date,  '%Y.%m.%d %T' ) > :preOrderDate   ";
								//执行sql语句
								Query queryPromotion = entityManager.createNativeQuery(sqlPromotion);
								queryPromotion.setParameter("goodsId", goodsId);
								queryPromotion.setParameter("preOrderDate", preOrderDate);
								//结果集格式化
								queryPromotion.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
								listPromotion= queryPromotion.getResultList();
									//如果优惠方式为空，存放空字节
									if(listPromotion.size()>0){
										productMap.put("promotion",listPromotion);
									}	
							}				
							productList.add(productMap);			
						}	
					orderMap.put("goods",productList);
				}
				listResult.add(orderMap);
			}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, listResult);
		}else{
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
		}
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;	
}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> orgList() {
		// 初始化map集合
		Map<String, Object> mapResult = new HashMap<String, Object>();
		List<Object> listOrg =new ArrayList<Object>();
		List<Object> listResult = new ArrayList<Object>();
		List<Result> list = null;
		try {
			String sql = "SELECT a.f_area area,b.f_name areaName FROM t_organization a LEFT JOIN t_area b ON a.f_area = b.f_id GROUP BY a.f_area ORDER BY a.f_area";
		
			// 执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.getResultList();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> m = (Map<String, Object>) list.get(i);
					String area=String.valueOf(m.get("area"));
					String areaName=String.valueOf(m.get("areaName"));
					Map<String,Object> areaMap= new HashMap<String, Object>();
					areaMap.put("area",area);
					areaMap.put("areaName",areaName);
					//根据区域id查询相关门店信息
					String sqlOrg="SELECT a.f_id orgId,a.f_name orgName FROM t_organization a WHERE a.f_area =:area";
					//执行sql语句
					Query queryOrg = entityManager.createNativeQuery(sqlOrg);
					queryOrg.setParameter("area", area);
					//结果集格式化
					queryOrg.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					listOrg= queryOrg.getResultList();
					//门店信息
					if(listOrg.size() > 0){
						areaMap.put("org",listOrg);
					}else{
						areaMap.put("org",null);
					}
				listResult.add(areaMap);
			}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, listResult);
		}else{
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, list);
		}
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> InventorySearchQuanqu(String selectVaule,
			int pageLoadType,String shopId, int pageRowsCount, int pageType) {
		// 初始化map集合
		Map<String, Object> mapResult = new HashMap<String, Object>();
		// 每页加载数据条数(暂定默认10条)
		List<Result> list = new ArrayList<Result>();
		String sql = null;
		String sqlBeg = null;
		try {
			// 商品名称检索
			sqlBeg = " SELECT b.f_id shopId,b.f_tel shopPhone,c.type goodType, b.f_name shopName, c.f_name goodsName, count(a.f_product_sn) shopGodsNum,c.f_price  goodsPrice,c.f_image  goodsImagePath "
					+ " FROM t_stock_log a LEFT JOIN t_organization b ON a.f_organization = b.f_id LEFT JOIN t_product c ON c.f_id = a.f_product WHERE ";
			if(StringUtils.isNotEmpty(selectVaule)){
				sqlBeg +="c.f_name LIKE :selectVaule  AND ";
			}
			sql = sqlBeg + "a.f_state=1 AND c.f_is_list=1 AND c.f_is_marketable=1 and c.f_name= :selectVaule and";
			switch (pageType) {
				//名称
			case 1:
					sql +=" c.f_name >:selectVaule GROUP BY a.f_product ORDER BY c.f_name ASC LIMIT 0,:pageRowsCount";
				break;
				//类型
			case 2:
					sql +=" c.f_name >:shopId GROUP BY a.f_product ORDER BY c.type ASC LIMIT 0,:pageRowsCount";
				break;
				//门店
			case 3:
					sql +=" b.f_id >=:shopId ORDER BY  b.f_id ASC LIMIT 0,:pageRowsCount";
				break;
			default:
				break;
			}
			// 执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("pageRowsCount", pageRowsCount);
			query.setParameter("shopId", shopId);
			query.setParameter("selectVaule", selectVaule);
			
			// 结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.getResultList();
			// 存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		// 存放最终的执行结果
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}


	
}
