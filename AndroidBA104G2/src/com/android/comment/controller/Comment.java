package com.android.comment.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.store_comment.model.StoreCommentService;
import com.store_comment.model.StoreCommentVO;

public class Comment extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
	
	
	

	public void doGet(HttpServletRequest req, HttpServletResponse res)
                         throws ServletException, IOException {

    
	    JSONObject jsonOut = new JSONObject();
	    
//	    String proType = req.getParameter("proType");
	    String storeID = req.getParameter("storeID");
	    String action = req.getParameter("action");
	    StoreCommentService commentSer = new StoreCommentService();
	    
	    req.setCharacterEncoding("UTF-8");
	    
	    
	    if("add".equals(action)){
	    	String orderID = req.getParameter("orderID");
	    	String star = req.getParameter("star");
	    	
	    	String temp = new String("");
	    	
	    	String title;
	    	String content;
	    	temp = req.getParameter("title");
	    	title = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
	    	
	    	temp = req.getParameter("content");
	    	content = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
	    	
//	    	String title = req.getParameter("title");
//	    	String content = req.getParameter("content");
//	    	String storeID = req.getParameter("storeID");
	    	String acc = req.getParameter("a");
	    	String pwd = req.getParameter("p");
	    	MemberProfileService memSer = new MemberProfileService();
	    	MemberProfileVO memVO = memSer.findMemberByAccPsw(acc, pwd);
	    	
	    	
	    	commentSer.addComment(title, storeID, memVO.getMem_num(), Integer.valueOf(star), content, orderID);
	    	jsonOut.put("info", "success");
	    }else{
//	    	List<StoreCommentVO> commentList = commentSer.getListByStoNum(storeID);
	    	List<StoreCommentVO> commentList = commentSer.getListByStoStatus(storeID,"一般");

	 	    for(int i=0; i<commentList.size(); i++){
	 	    	JSONObject json = new JSONObject();
	 	    	StoreCommentVO commentVO = commentList.get(i);
	 	    	json.put("commentID", commentVO.getCom_num());
	 	    	json.put("commentTitle", commentVO.getCom_title());
	 	    	json.put("memberID", commentVO.getMem_num());
	 	    	
	 	    	MemberProfileService memSer = new MemberProfileService();
	 	    	MemberProfileVO memVO = memSer.findMemberByID(commentVO.getMem_num());
	 	    	
	 	    	json.put("memberName", memVO.getMem_name());
	 	    	
	 	    	json.put("stars", commentVO.getStars());
	 	    	json.put("content", commentVO.getCommentt());
	 	    	
	 	    	
	 	    	SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
	             String time = sdFormat.format(commentVO.getCom_time());		
	             json.put("time", time);
	 	 	    	
	 	    	jsonOut.put("comment"+(i+1), json);
	 	    }
	    }
	    
	   
	   
	    
		
	    
	    
	  
	
	    res.setContentType("application/json");
	    res.setCharacterEncoding("UTF-8");
	    res.getWriter().write(jsonOut.toString());
	    
	    
	}
}
