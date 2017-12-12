package com.android.sweetness.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.ice_list.model.IceListService;
import com.ice_list.model.IceListVO;
import com.sweetness.model.SweetnessService;
import com.sweetness.model.SweetnessVO;


public class Sweetness extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	                             throws ServletException, IOException {
	
		
		
		
	
		
		String acc = req.getParameter("a");
	  	String pwd = req.getParameter("p");
	  	String type = req.getParameter("t");
	  	String action = req.getParameter("action");
	  	
//	  	System.out.println("sweet " + acc+pwd+type);
	  	JSONObject jsonOut = new JSONObject();
	  	
	  	String storeID = req.getParameter("storeID");
	  	SweetnessService sweetSer = new SweetnessService();
	  	List<SweetnessVO> sweetList = sweetSer.getListByStoNum(storeID);
	  	
	  	for(int i=0; i<sweetList.size(); i++){
	  		JSONObject json = new JSONObject();
	  		SweetnessVO sweet = sweetList.get(i);
	  		
	  		json.put("sweetID", sweet.getSweet_num());
	  		json.put("sweetName", sweet.getSweet_type());
	  		
	  		jsonOut.put("sweet"+(i+1), json);
	  	}
	  	
	  res.setContentType("application/json");
	  res.setCharacterEncoding("UTF-8");
	  res.getWriter().write(jsonOut.toString());
	    
	}
}
