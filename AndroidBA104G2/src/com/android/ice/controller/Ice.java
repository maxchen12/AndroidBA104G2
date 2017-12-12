package com.android.ice.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.ice_list.model.IceListService;
import com.ice_list.model.IceListVO;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.merged_order.model.MergedOrderService;
import com.merged_order.model.MergedOrderVO;
import com.order_master.model.OrderMasterService;
import com.order_master.model.OrderMasterVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;


public class Ice extends HttpServlet {
       
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	                             throws ServletException, IOException {
	
	
		
		
		
		
		String acc = req.getParameter("a");
	  	String pwd = req.getParameter("p");
	  	String type = req.getParameter("t");
	  	String action = req.getParameter("action");
	  	
//	  	System.out.println(acc+pwd+type);
	  	JSONObject jsonOut = new JSONObject();
	  	
	  	String storeID = req.getParameter("storeID");
	  	IceListService iceSer = new IceListService();
	  	List<IceListVO> iceList = iceSer.getListByStoNum(storeID);
	  	
	  	for(int i=0; i<iceList.size(); i++){
	  		JSONObject json = new JSONObject();
	  		IceListVO ice = iceList.get(i);
	  		
	  		json.put("iceID", ice.getIce_num());
	  		json.put("iceName", ice.getIce_type());
	  		
	  		jsonOut.put("ice"+(i+1), json);
	  	}
	  	
	  res.setContentType("application/json");
	  res.setCharacterEncoding("UTF-8");
	  res.getWriter().write(jsonOut.toString());
	    
	}

}
