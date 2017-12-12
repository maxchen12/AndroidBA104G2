package com.android.login.controller;

//package servlet_examples;

import java.io.*;
import java.util.Enumeration;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.JSONObject;

import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

public class Login extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
			doGet(req,res);
	}
	
	
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
                             throws ServletException, IOException {

	    JSONObject json = new JSONObject();
	    
	    //帳 密 類型
	    String acc = req.getParameter("a");	
	    String pwd = req.getParameter("p");
	    String type = req.getParameter("t");
//	    String action = req.getParameter("action");
	    
//	    if("getStoreInfo".equals(action)){
//	    	StoreProfileService stoSer = new StoreProfileService();
//	    	StoreProfileVO stoVO = stoSer.findStoreByAccPsw(acc, pwd);
//	    	
//	    }else{
	    	 //會員
		    if("member".equals(type)){
		    	MemberProfileService memProSrv = new MemberProfileService();
		        MemberProfileVO memProVO = memProSrv.findMemberByAccPsw(acc, pwd);	
		        
		        if(memProVO!=null && !memProVO.getStatus().equals("停權")){
		        	  json.put("info", "success");
		        	  json.put("memID", memProVO.getMem_num());
		        	  json.put("memName", memProVO.getMem_name());
		        }
		        else
		        {
		          json.put("info", "fail");
		        }
		    }//店家
		    else{
		    	StoreProfileService stoProSrv = new StoreProfileService();
		    	StoreProfileVO stoProVO = stoProSrv.findStoreByAccPsw(acc, pwd);
		    	
		    	if(stoProVO != null && !stoProVO.getSto_status().equals("申請中")){
		    		json.put("info", "success");
		    		JSONObject jobj = new JSONObject();
		    		jobj.put("name", stoProVO.getSto_name());
		    		jobj.put("storeID", stoProVO.getSto_num());
		    		jobj.put("address", stoProVO.getArea() + stoProVO.getAddress());
		    		jobj.put("phone", stoProVO.getMobile());
		    		
		    		json.put("store", jobj);
		        }
		        else
		        {
		          json.put("info", "fail");
		        }
		    	
		    }
//	    }
	    
	   
	
	    res.setContentType("application/json");
	    res.setCharacterEncoding("UTF-8");
	    res.getWriter().write(json.toString());
	    
	    
	}

}

