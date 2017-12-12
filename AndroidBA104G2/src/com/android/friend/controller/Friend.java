package com.android.friend.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.friend_list.model.FriendListService;
import com.group_buy.model.GroupBuyService;
import com.group_buy.model.GroupBuyVO;
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


public class Friend extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	                             throws ServletException, IOException {
	
		
		String acc = req.getParameter("a");
	  	String pwd = req.getParameter("p");
	  	String type = req.getParameter("t");	
	  	String action = req.getParameter("action");						//
	  	
	  	MemberProfileService memSer = new MemberProfileService();
	  	MemberProfileVO memVO = memSer.findMemberByAccPsw(acc, pwd);
	  	JSONObject jsonOut = new JSONObject();
	  		  	
	  	if("buyInviteList".equals(action)){//將訂單變成合併訂單 並回傳好友名單
	  		String memrorID = req.getParameter("memrorID");				//
	  		if(memrorID==null){
	  			MergedOrderService merSer = new MergedOrderService();
	  			memrorID = merSer.addMOByMem(memVO.getMem_num());
	  			String orderID = req.getParameter("orderID");			//
	  			
	  			OrderMasterService orSer = new OrderMasterService();
	  			orSer.updateOrder(null, null, null, memrorID, null, orderID,false,null);
	  		}
	  		
	  		
	  		
	  		FriendListService friSer = new FriendListService();
	  		Set<String> friIDList = friSer.getSetByMemNum(memVO.getMem_num());
	  		
//	  		List<MemberProfileVO> friList = new ArrayList<>();
	  		GroupBuyService groupSer = new GroupBuyService();
	  		int i=0;
	  		for(String s : friIDList){
//	  			friList.add(memSer.findMemberByID(s));
	  			MemberProfileVO friMemVO  = memSer.findMemberByID(s);
	  			String isAccept = groupSer.getIsAcceptByInvdMer(s, memrorID);
	  			
	  			JSONObject json = new JSONObject();
	  			json.put("memberID", friMemVO.getMem_num());
	  			json.put("memberName", friMemVO.getMem_name());
	  			if(isAccept == null || isAccept.equals("")){
	  				json.put("isAccept", "N");
	  			}else{
	  				json.put("isAccept", isAccept);
	  			}
	  			json.put("combineID",memrorID);
	  			
	  			jsonOut.put("invite" + (i+1), json);
	  			i++;
	  		}
	  		
	  		
	  	}else if("inviteBuy".equals(action)){//邀請好友
	  		GroupBuyService groupSer = new GroupBuyService();
	  		String invID = memVO.getMem_num();
	  		String invdID = req.getParameter("invdID");
	  		String combineID = req.getParameter("combineID");
	  		
	  		groupSer.addInvite(invID, invdID, combineID);
	  	}else if("inviteList".equals(action)){//回傳邀請自己的名單
	  		String invdID = memVO.getMem_num();
	  		GroupBuyService groupSer = new GroupBuyService();
	  		List<GroupBuyVO> groupList = groupSer.getListByInvd(invdID);
	  		
	  	
	  		int counter = 0;
	  		for(int i=0; i<groupList.size(); i++){
	  			GroupBuyVO groupVO = groupList.get(i);
	  			JSONObject jsobj = new JSONObject();
	  			
	  			MemberProfileVO groupMemVO = memSer.findMemberByID(groupVO.getInv_mem_num());
	  			jsobj.put("memberName", groupMemVO.getMem_name());
	  			jsobj.put("memberID", groupMemVO.getMem_num());
	  			jsobj.put("combineID", groupVO.getMeror_num());
	  			jsobj.put("status", groupVO.getIsaccept());
//	  			
//	  			
//	  			MergedOrderService moSer = new MergedOrderService();
//	  			MergedOrderVO moVO = moSer.getOneByMemMer(groupVO.getInv_mem_num(), groupVO.getMeror_num());
	  			
	  			OrderMasterService omSer = new OrderMasterService();
	  			List<OrderMasterVO> omList = omSer.getListByMeror(groupVO.getMeror_num());
	  			if(omList!=null){
	  				OrderMasterVO om = omList.get(0);
	  				if(!om.getOr_stanum().equals("修改中")){
	  					continue;
	  				}
	  				
	  				
	  				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
//	 	            String time = sdFormat.format(om.getOr_time());	
	  				String time = sdFormat.format(groupVO.getInv_time());
	 	            jsobj.put("time", time);
	  				
//	  				jsobj.put("time", om.getOr_time());
	  				jsobj.put("storeID", om.getSto_num());
	  				
	  				StoreProfileService stoSer = new StoreProfileService();
	  				StoreProfileVO stoVO = stoSer.findStoreByID(om.getSto_num());
	  				
	  				jsobj.put("storeName", stoVO.getSto_name());
	  			}
	  			
	  			jsonOut.put("invite" + (counter+1), jsobj);
	  			counter++;
	  			
//	  			System.out.println("aa" + groupVO.getInv_mem_num());
	  			
	  		}
	  		
	  	  
	  	}else if("acceptCombine".equals(action)){//接受團購邀請
  			String combineID = req.getParameter("combineID");
  			String storeID = req.getParameter("storeID");
  			String invID = req.getParameter("invID");
  			
  			//建立帶有combineID的訂單
  			OrderMasterService omSer = new OrderMasterService();
  			
  			
  			List<OrderMasterVO> omList = omSer.getListByMeror(combineID);
  			String status = null;
  			for(OrderMasterVO om : omList){
  				if(om.getMem_num().equals(invID)){
  					status = om.getOr_stanum();
  					break;
  				}
  			}
  			
  			if("修改中".equals(status)){
  				String orderID = omSer.createOrder(memVO.getMem_num(), storeID,combineID);
  	  			
  	  			//更改受邀請狀態
  	  			GroupBuyService groupSer = new GroupBuyService();
  	  			groupSer.acceptInvite(invID, memVO.getMem_num(), combineID);
  	  			
  	  			//拿取店家資訊
  	  			StoreProfileService stoSer = new StoreProfileService();
  	  			StoreProfileVO stoVO = stoSer.findStoreByID(storeID);
  	  			
  	  			jsonOut.put("name", stoVO.getSto_name());
  	  			jsonOut.put("storeID", stoVO.getSto_num());
  	  			jsonOut.put("address", stoVO.getAddress());
  	  			jsonOut.put("phone", stoVO.getMobile());
  	  			jsonOut.put("orderID", orderID);
  	  			jsonOut.put("info", "success");
  			}else{
  				jsonOut.put("info", "false");
  			}
  			
  			
  		 
  		}else if("isFriend".equals(action)){
  			String invdID = req.getParameter("invdID");
  			FriendListService friSer = new FriendListService();
	  		Set<String> friIDList = friSer.getSetByMemNum(memVO.getMem_num());
	  		if(friIDList.contains(invdID)){
	  			jsonOut.put("isFriend", "Y");
	  		}else{
	  			jsonOut.put("isFriend", "N");
	  		}
  			
  		}else if("accept".equals(action)){
  			String invdID = req.getParameter("invdID");
  			FriendListService friSer = new FriendListService();
  			friSer.addFriend(memVO.getMem_num(), invdID);
  			
  		}
	  	
	  	
	  res.setContentType("application/json");
	  res.setCharacterEncoding("UTF-8");
	  res.getWriter().write(jsonOut.toString());
	    
	}
}
