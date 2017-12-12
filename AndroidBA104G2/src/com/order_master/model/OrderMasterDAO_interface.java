package com.order_master.model;

import java.util.List;

public interface OrderMasterDAO_interface {

	public List<OrderMasterVO> getListByMemNum(String mem_num);
	public OrderMasterVO getOneByOrNum(String or_num);
	public OrderMasterVO getOneByMemMer(String mem_num, String meror_num);
	public void updateOrder(OrderMasterVO omVO, boolean updateTime);
	public String createOrder(String mem_num, String sto_num);
	public String createOrder(String mem_num, String sto_num, String meror_num);
	public List<OrderMasterVO> getListByMeror(String meror_num);
	
	
	public List<OrderMasterVO> getListByStoNum(String sto_num);
//	public String changetOrderToMer();
}
