package com.puyuntech.ycmall.dao.impl;


import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.CommentDao;
import com.puyuntech.ycmall.entity.Comment;

/**
 * 
 * 门店和店员 评论. 
 * Created on 2015-12-25 上午10:53:53 
 * @author 严志森
 */
@Repository("commentDaoImpl")
public class CommentDaoImpl extends BaseDaoImpl<Comment, Long> implements CommentDao {

}