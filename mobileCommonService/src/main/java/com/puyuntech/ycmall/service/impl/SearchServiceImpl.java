package com.puyuntech.ycmall.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.ProductDao;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.service.SearchService;

/**
 * 
 * Service 搜索
 * . 
 * Created on 2015-9-9 上午11:18:00 
 * @author 施长成
 */
@Service("searchServiceImpl")
@Transactional
public class SearchServiceImpl implements SearchService {
	
	/** 模糊查询最小相似度 **/
	private static final float FUZZY_QUERY_MINIMUM_SIMILARITY  = 0.8F;
	
	@PersistenceContext
	private EntityManager entityManager;
	@Resource(name="productDaoImpl")
	private ProductDao productDao;

	@Override
	public void index() {
		index(Product.class);
	}

	@Override
	public void index(Class<?> type) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
		if( type == Product.class ){//检索 商品信息
			for(int i=0 ;i<productDao.count(); i+=20){
				List<Product> productsList = productDao.findList(i, 20, null, null);
				for(Product product : productsList){
					fullTextEntityManager.index(product);
				}
				fullTextEntityManager.flushToIndexes();
				fullTextEntityManager.clear();
				productDao.clear();
			}
		}
		
	}

	@Override
	public void index(Product product) {
		if( product != null ){
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.index(product);
		}
		
	}

	@Override
	public void purge() {
		purge(Product.class);
	}

	@Override
	public void purge(Class<?> type) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		if(type == Product.class){
			fullTextEntityManager.purgeAll(Product.class);
		}
	}

	@Override
	public void purge(Product goods) {
		if(null != goods){
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.purge(Product.class, goods.getId());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Product> search(String keyword, BigDecimal startPrice,
			BigDecimal endPrice, Product.OrderType orderType, Pageable pageable) {

		if (StringUtils.isEmpty(keyword)) {
			return new Page<Product>();
		}

		if (pageable == null) {
			pageable = new Pageable();
		}
		try {
			String text = QueryParser.escape(keyword);
			TermQuery snQuery = new TermQuery(new Term("sn", text));
			Query keywordQuery = new QueryParser(Version.LUCENE_36, "keyword", new IKAnalyzer()).parse(text);
			QueryParser nameParser = new QueryParser(Version.LUCENE_36, "name", new IKAnalyzer());
			
			nameParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query nameQuery = nameParser.parse(text);
			FuzzyQuery nameFuzzyQuery = new FuzzyQuery(new Term("name", text), FUZZY_QUERY_MINIMUM_SIMILARITY);
			
			TermQuery introductionQuery = new TermQuery(new Term("introduction", text));
			TermQuery isMarketableQuery = new TermQuery(new Term("isMarketable", "true"));
			TermQuery isListQuery = new TermQuery(new Term("isList", "true"));
			//过滤积分商品等其他，只显示普通商品
			TermQuery typeQuery = new TermQuery(new Term("type",  "general"));
			
			BooleanQuery textQuery = new BooleanQuery();
			BooleanQuery query = new BooleanQuery();
			
			textQuery.add(snQuery, BooleanClause.Occur.SHOULD);
			textQuery.add(keywordQuery, BooleanClause.Occur.SHOULD);
			textQuery.add(nameQuery, BooleanClause.Occur.SHOULD);
			textQuery.add(nameFuzzyQuery, BooleanClause.Occur.SHOULD);
			
			textQuery.add(introductionQuery, BooleanClause.Occur.SHOULD);
			query.add(isMarketableQuery, BooleanClause.Occur.MUST);
			query.add(isListQuery, BooleanClause.Occur.MUST);
			query.add(textQuery, BooleanClause.Occur.MUST);
            query.add(typeQuery, BooleanClause.Occur.MUST);

			if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
				BigDecimal temp = startPrice;
				startPrice = endPrice;
				endPrice = temp;
			}
			if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0 && endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
				NumericRangeQuery<Double> numericRangeQuery = NumericRangeQuery.newDoubleRange("price", startPrice.doubleValue(), endPrice.doubleValue(), true, true);
				query.add(numericRangeQuery, BooleanClause.Occur.MUST);
			} else if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
				NumericRangeQuery<Double> numericRangeQuery = NumericRangeQuery.newDoubleRange("price", startPrice.doubleValue(), null, true, false);
				query.add(numericRangeQuery, BooleanClause.Occur.MUST);
			} else if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
				NumericRangeQuery<Double> numericRangeQuery = NumericRangeQuery.newDoubleRange("price", null, endPrice.doubleValue(), false, true);
				query.add(numericRangeQuery, BooleanClause.Occur.MUST);
			}
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Product.class);
			SortField[] sortFields = null;
			if (orderType != null) {
				switch (orderType) {
				case topDesc:
					sortFields = new SortField[] { new SortField("isTop", SortField.STRING, true), new SortField(null, SortField.SCORE), new SortField("createDate", SortField.LONG, true) };
					break;
				case priceAsc:
					sortFields = new SortField[] { new SortField("price", SortField.DOUBLE, false), new SortField("createDate", SortField.LONG, true) };
					break;
				case priceDesc:
					sortFields = new SortField[] { new SortField("price", SortField.DOUBLE, true), new SortField("createDate", SortField.LONG, true) };
					break;
				case salesDesc:
					sortFields = new SortField[] { new SortField("sales", SortField.LONG, true), new SortField("createDate", SortField.LONG, true) };
					break;
				case scoreDesc:
					sortFields = new SortField[] { new SortField("score", SortField.FLOAT, true), new SortField("createDate", SortField.LONG, true) };
					break;
				case dateDesc:
					sortFields = new SortField[] { new SortField("createDate", SortField.LONG, true) };
					break;
				}
			} else {
				sortFields = new SortField[] { new SortField("isTop", SortField.STRING, true), new SortField(null, SortField.SCORE), new SortField("createDate", SortField.LONG, true) };
			}
			fullTextQuery.setSort(new Sort(sortFields));
			fullTextQuery.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			fullTextQuery.setMaxResults(pageable.getPageSize());
			Page<Product> page=new Page<Product>(fullTextQuery.getResultList(), fullTextQuery.getResultSize(), pageable);
			return page;
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	
	}

}
