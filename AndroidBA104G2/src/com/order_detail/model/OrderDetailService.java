package com.order_detail.model;

import java.util.List;

import com.extra_list.model.ExtraListService;

public class OrderDetailService {
	OrderDetailDAO_interface dao;
	
	public OrderDetailService(){
		dao = new OrderDetailDAO();
	}
	
	public void delOneByOdID(String ordet_num){
		dao.delOneByodID(ordet_num);
	}
	
	public OrderDetailVO getOneByODID(String ordet_num){
		return dao.getOneByODID(ordet_num);
	}
	
	public String addOrderDetail(String com_num, String or_num, String sweet_num, String ice_num, String com_size, String mercom_num, int od_price, String[] extraID){
		
		OrderDetailVO odVO = new OrderDetailVO();
		
		String od = null;
		if(mercom_num == null){
			
			odVO.setCom_num(com_num);
			odVO.setOr_num(or_num);
			odVO.setSweet_num(sweet_num);
			odVO.setIce_num(ice_num);
			odVO.setCom_size(com_size);
			odVO.setMercom_num(null);
			odVO.setOd_price(od_price);
			od = dao.addOrderDetail(odVO);
			
			if(extraID!=null){
				ExtraListService exLSer = new ExtraListService();
				for(int i=0; i<extraID.length; i++){
					exLSer.addExtraList(od, extraID[i]);
				}
			}
			
			
		}else{
			
		}
		
		return od;
		
	}
	
	public List<OrderDetailVO> getListByOrNum(String or_num){
		return dao.getListByOrNum(or_num);
	}
	

}
