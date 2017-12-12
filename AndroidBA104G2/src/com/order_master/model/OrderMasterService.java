package com.order_master.model;

import java.util.List;

import com.merged_order.model.MergedOrderService;
import com.merged_order.model.MergedOrderVO;

public class OrderMasterService {

	OrderMasterDAO_interface dao;
	
	public OrderMasterService(){
		dao = new OrderMasterDAO();
	}
	
	public List<OrderMasterVO> getListByMemNum(String mem_num){
		return dao.getListByMemNum(mem_num);
	}
	
	public OrderMasterVO getOneByOrderID(String orderID){
		
		return dao.getOneByOrNum(orderID);
	}
	
	public void updateOrder(String rece, String pay_men, Integer or_amount, String meror_num, String or_stanum, String or_num, boolean updateTime, String address){
		
		OrderMasterVO omVO = new OrderMasterVO();
		omVO.setRece(rece);
		omVO.setPay_men(pay_men);
		omVO.setOr_amount(or_amount);
		omVO.setMeror_num(meror_num);
		omVO.setOr_stanum(or_stanum);
		omVO.setOr_num(or_num);
		omVO.setAddress(address);
		
		dao.updateOrder(omVO, updateTime);
	}
	public String createOrder(String mem_num, String sto_num){
//		if(isCombine){
//			return "";
//		}else{
			return dao.createOrder(mem_num, sto_num);
//		}
		
	}
	
	public String createOrder(String mem_num, String sto_num, String meror_num){
		
		//新增帶有合定編號的訂單
		String orderID = dao.createOrder(mem_num, sto_num, meror_num);
		
		//找出發起人的訂單資訊
		MergedOrderService moSer = new MergedOrderService();
		MergedOrderVO moVO = moSer.getOneByMer( meror_num);
		OrderMasterVO leaderVO = dao.getOneByMemMer(moVO.getMem_num(), meror_num);
		
		//將發起人的取貨方式存入剛新增的訂單
		OrderMasterVO invdVO = dao.getOneByOrNum(orderID);
		invdVO.setRece(leaderVO.getRece());
		invdVO.setAddress(leaderVO.getAddress());
		dao.updateOrder(invdVO,false);
		
		
		return orderID;
	}
	
	public List<OrderMasterVO> getListByMeror(String meror_num){
		return dao.getListByMeror(meror_num);
	}
	

	
	
	public List<OrderMasterVO> getListByStoNum(String sto_num){
		return dao.getListByStoNum(sto_num);
	}
	
}
