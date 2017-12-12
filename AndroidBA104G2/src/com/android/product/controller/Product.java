package com.android.product.controller;

import java.io.IOException;
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
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

public class Product extends HttpServlet{

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
			doGet(req,res);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
                             throws ServletException, IOException {

	    
	    JSONObject jsonOut = new JSONObject();
	    
	    String proType = req.getParameter("proType");
	    String storeID = req.getParameter("storeID");
	    
	    
    	ProductService proSer = new ProductService();
    	List<ProductVO> productList = new ArrayList<>();
    	
	    if("normal".equals(proType)){
	    	productList = proSer.getListByStoreIDandType(storeID, false);
	    	
	    }else if("combine".equals(proType)){
	    	productList = proSer.getListByStoreIDandType(storeID, true);
	    }else{
	    	System.out.println("Product Type 傳入參數錯誤");
	    }
	    
	    for(int i=0; i<productList.size(); i++){
	    	JSONObject json = new JSONObject();
	    	ProductVO proVO = productList.get(i);
	    	json.put("product", proVO.getCom_name());
	    	json.put("productID", proVO.getCom_num());
	    	json.put("mPrice", proVO.getM_price());
	    	json.put("lPrice", proVO.getL_price());
	    	
	    	jsonOut.put("product"+(i+1), json);
	    }
	    
	    
	  
	
	    res.setContentType("application/json");
	    res.setCharacterEncoding("UTF-8");
	    res.getWriter().write(jsonOut.toString());
	    
	    
	}

}

