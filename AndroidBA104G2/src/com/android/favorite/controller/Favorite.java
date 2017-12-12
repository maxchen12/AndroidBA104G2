package com.android.favorite.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.favorite_store.model.FavoriteStoreService;
import com.favorite_store.model.FavoriteStoreVO;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.order_master.model.OrderMasterService;
import com.order_master.model.OrderMasterVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

public class Favorite extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
	
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	                             throws ServletException, IOException {
	
	
		
		String acc = req.getParameter("a");
	  	String pwd = req.getParameter("p");
	  	String type = req.getParameter("t");
	  	String action = req.getParameter("action");
	
	  	JSONObject jsonOut = new JSONObject();
	  	
	  	MemberProfileService memSer = new MemberProfileService();
	  	MemberProfileVO memVO = memSer.findMemberByAccPsw(acc, pwd);
	  	String mem_num = memVO.getMem_num();
	  	
	  	FavoriteStoreService favSer = new FavoriteStoreService();
	  	
	  	if("update".equals(action)){
	  		String isfa = req.getParameter("isfa");
	  		String sto_num = req.getParameter("storeID");
	  		favSer.updateByMemSto(mem_num, sto_num, isfa);
	  		
	  		jsonOut.put("info", "success");
	  	}
	  	else if("check".equals(action)){
	  		String sto_num = req.getParameter("storeID");
	  		FavoriteStoreVO faVO = favSer.getOnerByMemSto(mem_num, sto_num);
	  		if(faVO!=null){
	  			jsonOut.put("isfa", faVO.getIs_favo());
	  		}else{
	  			jsonOut.put("isfa", "N");
	  		}
	  		
	  		
	  	}else if("all".equals(action)){
	  		
	  		List<FavoriteStoreVO> faList = favSer.getAllByMemNum(mem_num);
	  		for(int i=0; i<faList.size(); i++){
	  			JSONObject json = new JSONObject();
	  			FavoriteStoreVO faVO = faList.get(i);
	  			json.put("storeID", faVO.getSto_num());
	  			
	  			StoreProfileService stoSer = new StoreProfileService();
	  			StoreProfileVO stoVO = stoSer.findStoreByID(faVO.getSto_num());
	  			json.put("name",stoVO.getSto_name());
	  			json.put("address", stoVO.getAddress());
	  			json.put("phone", stoVO.getMobile());
	  			
	  			json.put("isfa", faVO.getIs_favo());
	  			
	  			
	  			jsonOut.put("favorite"+(i+1), json);
	  			
	  		
	  		}
	  	}else{
	  		jsonOut.put("info", "error");
	  		System.out.println("Favorite: wrong action");
	  	}
	  	
	  
	  res.setContentType("application/json");
	  res.setCharacterEncoding("UTF-8");
	  res.getWriter().write(jsonOut.toString());
	    
	}
	
	
	
}
