package com.order_detail.model;

import java.util.List;

public interface OrderDetailDAO_interface {

	public String addOrderDetail(OrderDetailVO odVO);
	public List<OrderDetailVO> getListByOrNum(String or_num);
	public void delOneByodID(String ordet_num);
	
	public OrderDetailVO getOneByODID(String ordet_num);
}
