package org.puyuntech;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {"/applicationContext.xml","/applicationContext-mvc.xml"})

public class TestAd extends AbstractJUnit4SpringContextTests {/*
	@Autowired
	private AdService adService;
	
	@Test
	public void add(){
		Ad ad = new Ad();
		ad.setFCreateDate(new Timestamp(2012, 12, 12, 12, 12, 12, 12));
		ad.setFModifyDate(new Timestamp(2012, 12, 12, 12, 12, 12, 12));
		ad.setVersion(1L);
		ad.setOrder(1);
		ad.setFBeginDate(new Timestamp(2012, 12, 12, 12, 12, 12, 12));
		ad.setFContent("施长成");
		ad.setFEndDate(new Timestamp(2012, 12, 12, 12, 12, 12, 12));
		ad.setFPath("www.baidu.com");
		ad.setFTitle("图片二");
		ad.setFType(Ad.Type.image);
		adService.save(ad);
		
	}
	
	

*/}
