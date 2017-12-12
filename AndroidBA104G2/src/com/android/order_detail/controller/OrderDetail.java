package com.android.order_detail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.extra.model.ExtraService;
import com.extra_list.model.ExtraListService;
import com.extra_list.model.ExtraListVO;
import com.ice_list.model.IceListService;
import com.ice_list.model.IceListVO;
import com.keep_record.model.KeepRecordService;
import com.keep_record.model.KeepRecordVO;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.order_detail.model.OrderDetailService;
import com.order_detail.model.OrderDetailVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.sweetness.model.SweetnessService;


public class OrderDetail extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	                             throws ServletException, IOException {
	
		String acc = req.getParameter("a");
	  	String pwd = req.getParameter("p");
	  	String type = req.getParameter("t");
	  	String action = req.getParameter("action");
//	  	String storeID = req.getParameter("storeID");
	  	JSONObject jsonOut = new JSONObject();
	  	
//	  	System.out.println("od " + acc+pwd+type);
	  	
	  	
	  	if("insert".equals(action)){
	  		String productID = req.getParameter("productID");
	  		String orderID = req.getParameter("orderID");
	  		String sweetID = req.getParameter("sweetID");
	  		String iceID = req.getParameter("iceID");
	  		String size = req.getParameter("size");
	  		String mercomID = req.getParameter("mercomID");
	  		int pricePer = Integer.valueOf(req.getParameter("pricePer"));
	  		String isKeep = req.getParameter("isKeep");
	  		String storeID = req.getParameter("storeID");
	  		MemberProfileService memSer = new MemberProfileService();
	  		MemberProfileVO memVO = memSer.findMemberByAccPsw(acc, pwd);
	  		String memID = memVO.getMem_num();
	  		
	  		String[] extraList = req.getParameterValues("extraID");
	  		
	  		OrderDetailService odSer = new OrderDetailService();
	  		KeepRecordService keepSer = new KeepRecordService();
	  		int quantity = Integer.valueOf(req.getParameter("quantity"));
	  		
	  		for(int i=0; i<quantity; i++){
	  			String odID = odSer.addOrderDetail(productID, orderID, sweetID, iceID, size, mercomID, pricePer, extraList);
	  			if("Y".equals(isKeep)){
	  				keepSer.addOneByOdID(memID, storeID, productID, odID);
	  			}
	  		}
	  	
	  		jsonOut.put("info", "success");
	  		
	  	}else if("getDetail".equals(action)){
	  		String orderID = req.getParameter("orderID");
	  		
//	  		System.out.println("gd " + orderID );
	  		
	  		
	  		OrderDetailService odSer = new OrderDetailService();
	  		List<OrderDetailVO> odList = odSer.getListByOrNum(orderID);
	  		
	  		for(int i=0; i<odList.size(); i++){
	  			JSONObject jsobj = new JSONObject();
	  			OrderDetailVO odVO = odList.get(i);
	  			
	  			jsobj.put("odID", odVO.getOrdet_num());
	  			jsobj.put("orderID", odVO.getOr_num());
	  			jsobj.put("productID", odVO.getCom_num());
	  			
	  			ProductService proSer = new ProductService();
	  			ProductVO proVO = proSer.getOneByComNum(odVO.getCom_num());
	  			jsobj.put("productName", proVO.getCom_name());
	  			
	  			jsobj.put("size", odVO.getCom_size());
	  			
	  			SweetnessService sweetSer = new SweetnessService();
	  			String sweet = sweetSer.getNameByID(odVO.getSweet_num());
	  			jsobj.put("sweet", sweet);
	  			
	  			IceListService iceSer = new IceListService();
	  			String ice = iceSer.getNameByID(odVO.getIce_num());
	  			jsobj.put("ice", ice);
	  			
	  			jsobj.put("price", odVO.getOd_price());
	  			
	  			KeepRecordService keepSer = new KeepRecordService();
	  			KeepRecordVO keepVO = keepSer.getOneByOdID(odVO.getOrdet_num());
	  			if(keepVO==null){
	  				jsobj.put("isKeep", false);
	  			}else{
	  				jsobj.put("isKeep", true);
	  			}
	  			
	  			ExtraListService exListSer = new ExtraListService();
	  			List<ExtraListVO> exList = exListSer.getListByOdNum(odVO.getOrdet_num());
	  			JSONObject jsex = new JSONObject();
	  			ExtraService exSer = new ExtraService();
	  			for(int j=0; j<exList.size(); j++){
	  				ExtraListVO exListVO = exList.get(j);
	  				String exName = exSer.getOneByExtID(exListVO.getExt_num()).getExt_name();
	  				jsex.put("ex"+(j+1), exName);
	  			}
	  			jsobj.put("extra", jsex);
	  			
	  			
	  			jsonOut.put("od"+(i+1),jsobj);
	  			
	  		}
	  	}
	  	
	  	
	  	
//	  	System.out.println(jsonOut.toJSONString());
	  	
	  		
	  	
	  	
	  res.setContentType("application/json");
	  res.setCharacterEncoding("UTF-8");
	  res.getWriter().write(jsonOut.toString());
	    
	}

}
