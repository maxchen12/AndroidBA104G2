package com.android.order.controller;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.JSONObject;

import com.card.model.CardService;
import com.card.model.CardVO;
import com.card_list.model.CardListService;
import com.card_list.model.CardListVO;
import com.card_record.model.CardRecordService;
import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.coupon_list.model.CouponListService;
import com.coupon_list.model.CouponListVO;
import com.extra_list.model.ExtraListService;
import com.keep_record.model.KeepRecordService;
import com.keep_record.model.KeepRecordVO;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.merged_order.model.MergedOrderService;
import com.merged_order.model.MergedOrderVO;
import com.order_detail.model.OrderDetailService;
import com.order_detail.model.OrderDetailVO;
import com.order_master.model.OrderMasterService;
import com.order_master.model.OrderMasterVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

import oracle.jdbc.rowset.OracleRowSetListenerAdapter;

public class Order extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
	
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	                             throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");
		
		String acc = req.getParameter("a");
	  	String pwd = req.getParameter("p");
	  	String type = req.getParameter("t");
	  	String action = req.getParameter("action");
	  	
//	  	System.out.println(acc+pwd+type);
	  	JSONObject jsonOut = new JSONObject();
	  	
	  	MemberProfileService memProSer = new MemberProfileService();
	  	MemberProfileVO memProVO = memProSer.findMemberByAccPsw(acc, pwd);
	  	
	  	
	  	
	  	if("passOrder".equals(action)){
	  		String orderID = req.getParameter("orderID");
	  		String method = req.getParameter("method");
	  		OrderMasterService orSer = new OrderMasterService();
	  		OrderMasterVO orVO = orSer.getOneByOrderID(orderID);
	  		
	  		List<OrderMasterVO> orList = null;	//是否團購
  			if(orVO.getMeror_num() != null){
  				orList = orSer.getListByMeror(orVO.getMeror_num());
  			}else{
  				orList = new ArrayList<>();
  				orList.add(orVO);
  			}
  				
  			CardListService cardListSer = new CardListService();
  			KeepRecordService keepSer = new KeepRecordService();
  			
	  		if("pass".equals(method)){ //訂單狀態改為處理中  //寄杯狀態改為未領取
//	  			OrderDetailService odSer = new OrderDetailService();
	  			for(OrderMasterVO orderVO:orList){
	  				orSer.updateOrder(null, null, null, null, "處理中", orderVO.getOr_num(),true,null);
	  				cardListSer.updateStatusByOrnum("已使用", orderVO.getOr_num(),null);
//	  				List<OrderDetailVO> odList = odSer.getListByOrNum(orderVO.getOr_num());
//	  				for(OrderDetailVO odVO: odList){
//	  					keepSer.updateStatusByOdnum("未領取", odVO.getOrdet_num(),false);
//	  				}
	  			}
	  			jsonOut.put("info", "success");
	  			
	  		}else if("unpass".equals(method)){//退回點數付款 訂單狀態改為修改中 檢查集體訂購 集點卡 折價券去掉使用時間	
	  			
	  			CouponListService couListSer = new CouponListService();
	  			
	  			for(OrderMasterVO orderVO:orList){
	  			//訂單狀態改為修改中
	  				orSer.updateOrder(null, null, null, null, "修改中", orderVO.getOr_num(),true,null);
	  			//退回點數付款 
	  				if("點數".equals(orderVO.getPay_men())){
	  					MemberProfileVO memVO = memProSer.findMemberByID(orderVO.getMem_num());
		  				memProSer.updatePointByMem(memVO.getRem_point()+orderVO.getOr_amount() , memVO.getMem_num());
	  				}
	  			//集點卡 折價券去掉使用時間
	  				cardListSer.updateTimeByOrnum(false, orderVO.getOr_num());
	  				couListSer.updateTimeByOrnum(false, orderVO.getOr_num());
	  			}
	  			
	  			
	  			
	  			jsonOut.put("info", "success");
	  		}else if("dealComplete".equals(method)){//店家通知處理完成 將訂單狀態依運送方式改為外送中或是待領取
	  			for(OrderMasterVO orderVO:orList){
	  				if("外送".equals(orderVO.getRece())){
	  					orSer.updateOrder(null, null, null, null, "外送中", orderVO.getOr_num(),true,null);
	  				}else if("自取".equals(orderVO.getRece())){
	  					orSer.updateOrder(null, null, null, null, "待領取", orderVO.getOr_num(),true,null);
	  				}else{
	  					System.out.println("wrong in order dealComplete");
	  				}	
	  			}
	  			
	  			jsonOut.put("info", "success");
	  		}else if("confirmDelivery".equals(method)){//店家將貨物交出 將訂單狀態改為已交貨 寄杯狀態改為未領取
	  			OrderDetailService odSer = new OrderDetailService();
	  			for(OrderMasterVO orderVO:orList){
	  				orSer.updateOrder(null, null, null, null, "已交貨", orderVO.getOr_num(),true,null);
	  				List<OrderDetailVO> odList = odSer.getListByOrNum(orderVO.getOr_num());
	  				for(OrderDetailVO odVO: odList){
	  					keepSer.updateStatusByOdnum("未領取", odVO.getOrdet_num(),false);
	  				}
	  			}
	  			jsonOut.put("info", "success");
	  		}else if("getProduct".equals(method)){//買家確認收到貨物 將訂單狀態改為交易完成 集點卡加點數 增加集點紀錄
	  			CardRecordService cardRecSer = new CardRecordService();
	  			CardService cardSer = new CardService();
	  			OrderDetailService odSer = new OrderDetailService();
	  			
	  			for(OrderMasterVO orderVO:orList){
	  				orSer.updateOrder(null, null, null, null, "交易完成", orderVO.getOr_num(),true,null);
	  				int quantity = odSer.getListByOrNum(orderVO.getOr_num()).size();
	  				
	  				int leftPoint = 0;
  					int increasePoint = 0;
  					
  					
  					do{
  						CardListVO cardListVO = cardListSer.getOneByMemSto(orderVO.getMem_num(), orderVO.getSto_num());
  		  				if(cardListVO!=null){
  		  					CardVO cardVO = cardSer.getOneByCardKinds(cardListVO.getCard_kinds());
  		  					String status = null;
  		  					//算出要加多少點
  		  					if(quantity + cardListVO.getValue() >= cardVO.getPoints()){
  		  						leftPoint = cardListVO.getValue() + quantity - cardVO.getPoints();
  			  					increasePoint = quantity - leftPoint;
  			  					status = "可使用";
  		  					}else{
  		  						leftPoint = 0;
  		  						increasePoint = quantity;
  		  					}
  		  					
  		  					//點數紀錄加一筆資料
  		  					cardRecSer.addRecord(cardListVO.getCard_num(), orderVO.getOr_num(), increasePoint);	
  		  					//集點卡清單更新點數及狀態
  		  					cardListSer.updateStatusByMemnum(status, orderVO.getMem_num(), increasePoint + cardListVO.getValue());
  		  					
  		  					
  		  					quantity = leftPoint;
  		  				}else{
  		  					CardVO cardVO = cardSer.getAvaOneBySto(orderVO.getSto_num());
  		  					if(cardVO==null){
  		  						break;
  		  					}else{
  		  						cardListSer.addCard(orderVO.getMem_num(), orderVO.getSto_num(), cardVO.getCard_kinds(), cardVO.getExp_date());
  		 
  		  					}
  		  				}
  					}while(quantity > 0);
  					
	  				
	  			}
	  			jsonOut.put("info", "success");
	  		}else{
	  			System.out.println("wrong in Order passOrder");
	  			jsonOut.put("info","fail");
	  		}
	  		
	  		
	  	}else if("insert".equals(action)){//新增訂單 回傳訂單ID
	  		String storeID = req.getParameter("storeID");
	  		OrderMasterService orderSer = new OrderMasterService();
	  		String orderID = orderSer.createOrder(memProVO.getMem_num(), storeID);
	  		
	  		jsonOut.put("orderID", orderID);
	  		
	  	}else if("confirmOrder".equals(action)){
	  		String orderID = req.getParameter("orderID");
	  		//更改團購訂單的狀態
	  		OrderMasterService omSer = new OrderMasterService();
	  		OrderMasterVO omVO = omSer.getOneByOrderID(orderID);
	  		List<OrderMasterVO> omList = omSer.getListByMeror(omVO.getMeror_num());
	  		if(omList==null || omList.size()==0){
	  			omList = new ArrayList<>();
	  			omList.add(omVO);
	  		}
	  		
	  		//檢查是否有人點數付款 但點數不足
	  		for(OrderMasterVO orderVO : omList){
	  			if("點數".equals(orderVO.getPay_men())){
	  				MemberProfileVO mem = memProSer.findMemberByID(orderVO.getMem_num());
	  				if(mem.getRem_point() < orderVO.getOr_amount()){
	  					jsonOut.put("info", "point");
	  					
	  					res.setContentType("application/json");
	  					res.setCharacterEncoding("UTF-8");
	  				 	res.getWriter().write(jsonOut.toString());
	  				 	return;
	  				}
	  			}
	  		}
	  		
	  		
	  		CardListService cardSer = new CardListService();
	  		CouponListService couSer = new CouponListService();
	  		MergedOrderService merSer = new MergedOrderService();
	  		int totalPrice = 0;
	  		
	  		//訂單狀態改為待審核 集點卡 折價券加入使用時間 點數付款扣款
	  		for(OrderMasterVO orderVO : omList){
	  			totalPrice = totalPrice + orderVO.getOr_amount();
	  			omSer.updateOrder(null, null, null, null, "待審核", orderVO.getOr_num(),true,null);
	  			couSer.updateTimeByOrnum(true, orderVO.getOr_num());
	  			cardSer.updateTimeByOrnum(true, orderVO.getOr_num());
	  			
	  			if("點數".equals(orderVO.getPay_men())){
	  				MemberProfileVO memVO = memProSer.findMemberByID(orderVO.getMem_num());
	  				int point = memVO.getRem_point() - orderVO.getOr_amount();
	  				
	  				memProSer.updatePointByMem(point, orderVO.getMem_num());
	  			}
	  		}
	  		
	  		if(totalPrice<0){
	  			totalPrice = 0;
	  		}
	  		
	  		merSer.updatePrice(omList.get(0).getMeror_num(), totalPrice);
	  		
	  		
	  		//折價券 集點卡 加上時間
	  		jsonOut.put("info", "success");
	  		
	  	}else if("confirm".equals(action)){ //團購時 依需求更改訂單狀態
	  		String status = req.getParameter("status");
	  		String orderId = req.getParameter("orderID");
	  		OrderMasterService omSer = new OrderMasterService();
	  		if("Y".equals(status)){
	  			omSer.updateOrder(null, null, null, null, "已確認", orderId,true,null);
	  		}else if("N".equals(status)){
	  			omSer.updateOrder(null, null, null, null, "修改中", orderId,true,null);
	  		}else{
	  			System.out.println("wrong in Order confirm***");
	  		}
	  		
	  	}else if("getMember".equals(action)){//抓一起訂購人員的訂單資訊 (也可抓單人)
	  		String orderID = req.getParameter("orderID");
	  		String addition = req.getParameter("addition");
	  		
	  		
	  		OrderMasterService orderSer = new OrderMasterService();
	  		OrderMasterVO orVO = orderSer.getOneByOrderID(orderID);
	  		List<OrderMasterVO> orList;
	  		if(orVO.getMeror_num()!=null){
	  			orList = orderSer.getListByMeror(orVO.getMeror_num());
	  		}else{
	  			orList = new ArrayList<>();
	  			orList.add(orVO);
	  		}
	  		
	  		for(int i=0; i<orList.size(); i++){
	  			JSONObject jsobj = new JSONObject();
	  			OrderMasterVO orMVO = orList.get(i);
	  			MemberProfileVO memVO = memProSer.findMemberByID(orMVO.getMem_num());
	  			if("orderConfirm".equals(addition)){/////////////////
			  		
	  				calPriceIntoJson(orMVO.getOr_num(),memVO.getMem_num(),jsobj );
	  				jsobj.put("delivery",orMVO.getRece());
	  			
			  	}
	  			jsobj.put("address", orMVO.getAddress());
	  			
	  			jsobj.put("orderID", orMVO.getOr_num());
	  			jsobj.put("memberID", orMVO.getMem_num());
	  			jsobj.put("status", orMVO.getOr_stanum());
	  			
	  			
	  			jsobj.put("memberName",memVO.getMem_name());
	  			
	  			MergedOrderService merSer = new MergedOrderService();
	  			if(merSer.getOneByMemMer(orMVO.getMem_num(), orMVO.getMeror_num())!=null){
	  				jsobj.put("isLeader", true);
	  			}else{
	  				jsobj.put("isLeader", false);
	  			}
	  			
	  			if("點數".equals(orMVO.getPay_men())){
	  				jsobj.put("isPoint", true);
	            }else{
	            	jsobj.put("isPoint", false);
	            }
	  			
	  			jsobj.put("phone", memVO.getMobile());
	  			jsonOut.put("member" + (i+1), jsobj);
	  		}
	  		
	  	}else if("useCard".equals(action)){
	  		String orderID = req.getParameter("orderID");
	  		String use = req.getParameter("use");
	  		if("N".equals(use)){
	  			CardListService cardListSer = new CardListService();
	  			cardListSer.updateOrnumByOrnum(null, orderID);
	  		}else if("Y".equals(use)){
	  			String cardID = req.getParameter("cardID");
		  		String cardType = req.getParameter("cardType");
		  		
	  			CardListService cardListSer = new CardListService();
	  			cardListSer.updateOrnumByOrnum(null, orderID);
	  			cardListSer.updateOrnumByIDType(orderID, cardID, cardType);
	  		}else{
	  			System.out.println("wrong in order useCard");
	  		}
	  	}else if("useCoupon".equals(action)){
	  		
	  		String orderID = req.getParameter("orderID");
	  		String use = req.getParameter("use");
	  		if("N".equals(use)){//取消使用折價卷
	  			CouponListService couListSer = new CouponListService();
	  			couListSer.updateOrnumByOrnum(null, orderID);
	  		}else if("Y".equals(use)){//使用折價卷 先把舊的取消
	  			
	  			String couponID = req.getParameter("couponID");
		  		String couponType = req.getParameter("couponType");
		  		
	  			CouponListService couListSer = new CouponListService();
	  			couListSer.updateOrnumByOrnum(null, orderID);
	  			couListSer.updateOrnumByCaCn(orderID, Integer.valueOf(couponID), couponType);
	  			
	  		}else{
	  			System.out.println("wrong in order useCoupon");
	  		}
	  		
	  		
	  	}else if("update".equals(action)){//修改訂單資訊
	  		String temp;
	  		String delivery;
	  		String pay_men;
	  		String or_stanum;
	  		String address;
	  		try{
	  			temp = req.getParameter("delivery");
		  		delivery = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
	  		}catch(Exception e){
	  			delivery = null;
	  		}
	  		
	  		try{
	  			temp = req.getParameter("address");
	  			address = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
	  		}catch(Exception e){
	  			address = null;
	  		}
	  		
	  		try{
	  			temp = req.getParameter("point");
		  		pay_men = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
	  		}catch(Exception e){
	  			pay_men = null;
	  		}
	  		
	  		try{
	  			temp = req.getParameter("status");
	  			or_stanum = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
	  		}catch(Exception e){
	  			or_stanum = null;
	  		}
	  		Integer or_amount;
	  		try{
	  			or_amount = Integer.valueOf( req.getParameter("or_amount"));
	  		}catch(Exception e){
	  			or_amount = null;
	  		}
	  		
	  		String meror_num = req.getParameter("meror_num");
	  		String or_num = req.getParameter("orderID");
	  		
	  		OrderMasterService omSer = new OrderMasterService();
	  		
	  		omSer.updateOrder(delivery, pay_men, or_amount, meror_num, or_stanum,or_num,false,address);
	  		
	  		
	  		//當運送方式不為空值 且 該會員是發起人時 要將團購訂單的運送方式集體修改
	  		if(delivery!=null || address!=null){
	  			OrderMasterVO omVO = omSer.getOneByOrderID(or_num);
	  			if(omVO.getMeror_num()!=null){
	  				MergedOrderService moSer = new MergedOrderService();
	  		  		MergedOrderVO moVO = moSer.getOneByMemMer(omVO.getMem_num(), omVO.getMeror_num());
	  		  		if(moVO!=null){
	  		  			List<OrderMasterVO> omList = omSer.getListByMeror(omVO.getMeror_num());
	  		  			
	  		  			for(OrderMasterVO groupOMVO : omList){
	  		  				omSer.updateOrder(delivery, pay_men, or_amount, meror_num, or_stanum, groupOMVO.getOr_num(),false,address);
	  		  			}
	  		  		}
	  			}
	  		}
	  		
	  		
	  	}else if("getStore".equals(action)){//傳入訂單ID 回傳店家資訊
	  		String orderID = req.getParameter("orderID");
	  		OrderMasterService orderSer = new OrderMasterService();
	  		OrderMasterVO orderVO = orderSer.getOneByOrderID(orderID);
	  		
	  		
	  		StoreProfileService stoProSer = new StoreProfileService();
	  		StoreProfileVO stoProVO = stoProSer.findStoreByID(orderVO.getSto_num());
	  		
	    	  
	  		jsonOut.put("name", stoProVO.getSto_name());
	  		jsonOut.put("storeID", stoProVO.getSto_num());
	  		jsonOut.put("address", stoProVO.getAddress());
	  		jsonOut.put("phone", stoProVO.getMobile());
	          
	  		
	  	}else if("getOrder".equals(action)){//抓一筆訂單的資訊
	  		String orderID = req.getParameter("orderID");
	  		OrderMasterService orderSer = new OrderMasterService();
	  		OrderMasterVO orderVO = orderSer.getOneByOrderID(orderID);
	  		
	  		jsonOut.put("orderID", orderVO.getOr_num());
	  		jsonOut.put("memID", orderVO.getMem_num());
	  		jsonOut.put("rece", orderVO.getRece());
	  		jsonOut.put("pay", orderVO.getPay_men());
	  		if(orderVO.getAddress() == null){
	  			jsonOut.put("address","");
	  		}else{
	  			jsonOut.put("address", orderVO.getAddress());
	  		}
	  		
	  		
	  		
	  		if(orderVO.getMeror_num() == null){
	  			jsonOut.put("merorID", "not");
	  			
	  		}else{
	  			jsonOut.put("merorID", orderVO.getMeror_num());
	  			MergedOrderService moSer = new MergedOrderService();
	  			MergedOrderVO moVO = moSer.getOneByMemMer(orderVO.getMem_num(), orderVO.getMeror_num());
	  			if(moVO!=null){
	  				jsonOut.put("isLeader", "y");
	  			}
	  			else{
	  				jsonOut.put("isLeader", "n");
	  			}
	  		}
	  		
	  		
	  	//折價券資訊
	  		CouponListService couSer = new CouponListService();
	  		List<CouponListVO> couList = couSer.getAbleListByMemSto(orderVO.getMem_num(), orderVO.getSto_num());
	  		JSONObject jsonCou = new JSONObject();
	  		for(int i=0; i< couList.size(); i++){
	  			CouponListVO coVO = couList.get(i);
		  		JSONObject json = new JSONObject();

	  			CouponService couponSer = new CouponService();
	  			CouponVO couponVO = couponSer.getOneByCouNum(coVO.getCoupon_num());
	  			json.put("couponType", couponVO.getCoupon_num());
	  			json.put("cash", couponVO.getCoupon_cash());
	  			json.put("couponID", coVO.getCoupon_amount());
	  				
	  			if(orderID.equals(coVO.getOr_num()))
	  			{
	  				json.put("isUse", true);
	  			}else{
	  				json.put("isUse", false);
	  			}
	  			
	  			jsonCou.put("coupon" + (i+1), json);
	  		}
	  		jsonOut.put("coupon", jsonCou);
	  		
	  		//集點卡資訊
	  		
	  		JSONObject jsonCard = new JSONObject();
	  		CardListService cardSer = new CardListService();
	  		List<CardListVO> cardList = cardSer.getAvaListByMemSto(orderVO.getMem_num(), orderVO.getSto_num());
	  		
	  		for(int i=0; i<cardList.size(); i++){
	  			CardListVO cardVO = cardList.get(i);
	  			JSONObject json = new JSONObject();
	  			
	  			json.put("cardID", cardVO.getCard_num());
	  			json.put("cardType", cardVO.getCard_kinds());
	  			
	  			if(orderID.equals(cardVO.getOr_num())){
	  				json.put("isUse", true);
	  			}else{
	  				json.put("isUse", false);
	  			}
	  			
	  			CardService caSer = new CardService();
	  			CardVO caVO = caSer.getOneByCardKinds(cardVO.getCard_kinds());
	  			
	  			json.put("cash", caVO.getPoints_cash());
	  			
	  				
	  			
	  			
	  			jsonCard.put("card" + (i+1), json);

	  		}
	  		jsonOut.put("card", jsonCard);
	  		
	  		

	  		
	  		
	  		
	  		
	  		
	  	}else if("delOD".equals(action)){
	  		String[] odIDs = req.getParameterValues("odID");
	  		
	  		KeepRecordService keepSer = new KeepRecordService();
	  		ExtraListService exSer = new ExtraListService();
	  		OrderDetailService odSer = new OrderDetailService();
	  		
	  		
	  		
	  		for(int i=0; i<odIDs.length; i++){
//		  		System.out.println(odIDs[i]);
		  		keepSer.delOneByOdID(odIDs[i]);
		  		exSer.delOneByOdID(odIDs[i]);
		  		odSer.delOneByOdID(odIDs[i]);
	  		}
	  		
	  		
	  		
	  		
	  		

	  	}else if("member".equals(type)){//抓某會員的所有訂單資訊
	  	
	  		
	      	
	      	OrderMasterService orderSer = new OrderMasterService();
	      	List<OrderMasterVO> orderList = orderSer.getListByMemNum(memProVO.getMem_num());
	      	
	      
	      	
	      	for(int i=0; i<orderList.size(); i++){
	      		
	      		JSONObject json = new JSONObject();
	      		OrderMasterVO orderVO = orderList.get(i);
	      		
	      		json.put("name", memProVO.getMem_name());
	      		
	            
	            StoreProfileService stoProSer = new StoreProfileService();
	            StoreProfileVO stoProVO = stoProSer.findStoreByID(orderVO.getSto_num());
	            json.put("shop", stoProVO.getSto_name());
	            json.put("shopID", stoProVO.getSto_num());
	            
	            json.put("orderNumber", orderVO.getOr_num());
	            
	            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
	            String time = sdFormat.format(orderVO.getOr_time());		
	            json.put("time", time);
	            
	            
	            json.put("status", orderVO.getOr_stanum());
	            
	            if(orderVO.getMeror_num()==null){
	            	json.put("isCombine", false);
	            }else{
	            	json.put("isCombine", true);
	            }
	            
	            
	            
	            jsonOut.put("order"+(i+1), json);
	      	}
	      	
	  	}else if("shop".equals(type)){
	  		StoreProfileService stoSer = new StoreProfileService();
	  		StoreProfileVO stoVO = stoSer.findStoreByAccPsw(acc, pwd);	  		
	  		String storeID = stoVO.getSto_num();
	  		OrderMasterService orSer = new OrderMasterService();
	  		List<OrderMasterVO> orList = orSer.getListByStoNum(storeID);
	  		MergedOrderService merSer = new MergedOrderService();
	  		
	  		int counter = 0;
	  		for(int i=0; i<orList.size(); i++){
	  			OrderMasterVO orVO = orList.get(i);
	  			
	  			
	  			
	  			if(orVO.getMeror_num()!=null){
	  				MergedOrderVO merVO = merSer.getOneByMer(orVO.getMeror_num());
	  				
	  				if(!merVO.getMem_num().equals(orVO.getMem_num())){
	  					continue;
	  				}
	  			}
	  			
	  			
	  			
	  				MemberProfileVO memVO = memProSer.findMemberByID(orVO.getMem_num());
		  			
		  			JSONObject jobj = new JSONObject();
		  			jobj.put("name", memVO.getMem_name());
		  			jobj.put("memID", memVO.getMem_num());
		  			jobj.put("orderNumber", orVO.getOr_num());
		  			
		  			jobj.put("shop", stoVO.getSto_name());
		            jobj.put("shopID", stoVO.getSto_num());
		            	            
		            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		            String time = sdFormat.format(orVO.getOr_time());		
		            jobj.put("time", time);
		            
		            
		            jobj.put("status", orVO.getOr_stanum());
		            
		            jobj.put("delivery", orVO.getRece());
		            
		            if(orVO.getMeror_num()==null){
		            	jobj.put("isCombine", false);
		            }else{
		            	jobj.put("isCombine", true);
		            }
		  			
		  			
		  			jsonOut.put("order" + (counter+1), jobj);
		  			counter++;
	  			
	  			
	  			
	  		}
	  		
	  		
	  	}
	  	
	  	
	  	
	  	
	  
	  res.setContentType("application/json");
	  res.setCharacterEncoding("UTF-8");
	  res.getWriter().write(jsonOut.toString());
	    
	}
	
	
	
	
	public static void calPriceIntoJson(String orderID, String memID, JSONObject json){
		OrderDetailService odSer = new OrderDetailService();
  		CardListService cardListSer = new CardListService();
  		CardService cardService = new CardService();
  		CouponListService couponListSer = new CouponListService();
  		CouponService couponSer = new CouponService();
  		
  		
  		System.out.println("orderID " + orderID);
  		List<OrderDetailVO> odList = odSer.getListByOrNum(orderID);
  		
  		int orderPrice = 0;
  		for(OrderDetailVO od : odList){
  			orderPrice = orderPrice + od.getOd_price();
  		}
  		
  		
  		CardListVO cardListVO = cardListSer.getOneByOrnumMemnum(orderID, memID);
  		if(cardListVO==null){
  			json.put("card", 0);
  		}else{
  			
  			CardVO cardVO = cardService.getOneByCardKinds(cardListVO.getCard_kinds());
  			orderPrice= orderPrice - cardVO.getPoints_cash();
  			json.put("card", cardVO.getPoints_cash());
  		}
  		
  		
  		CouponListVO couListVO = couponListSer.getOneByOrMem(orderID, memID);
  		if(couListVO==null){
  			json.put("coupon", 0);
  		}else{
  			CouponVO couponVO = couponSer.getOneByCouNum(couListVO.getCoupon_num());
  			orderPrice = orderPrice - couponVO.getCoupon_cash();
  			json.put("coupon", couponVO.getCoupon_cash());
  		}
  		if(orderPrice < 0){
  			orderPrice = 0;
  		}
  		OrderMasterService omSer = new OrderMasterService();
  		omSer.updateOrder(null, null, orderPrice, null, null,orderID,false,null);
  		json.put("price", orderPrice);
  		
  	
	}
}


