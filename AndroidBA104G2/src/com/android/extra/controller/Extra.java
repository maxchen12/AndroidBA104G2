package com.android.extra.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.extra.model.ExtraService;
import com.extra.model.ExtraVO;
import com.ice_list.model.IceListService;
import com.ice_list.model.IceListVO;


public class Extra extends HttpServlet {
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
	  	ExtraService exSer = new ExtraService();
	  	List<ExtraVO> exList = exSer.getListByStoNum(storeID);
	  	
	  	for(int i=0; i<exList.size(); i++){
	  		JSONObject json = new JSONObject();
	  		ExtraVO extra = exList.get(i);
	  		
	  		json.put("extraID", extra.getExt_num());
	  		json.put("extraName", extra.getExt_name());
	  		json.put("extraPrice", extra.getExt_amount());
	  		
	  		jsonOut.put("extra"+(i+1), json);
	  	}
	  	
	  	
	  res.setContentType("application/json");
	  res.setCharacterEncoding("UTF-8");
	  res.getWriter().write(jsonOut.toString());
	    
	}


}
