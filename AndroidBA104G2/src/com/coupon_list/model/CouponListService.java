package com.coupon_list.model;

import java.util.List;

public class CouponListService {
	CouponListDAO_interface dao;
	
	public CouponListService(){
		dao = new CouponListDAO();
	}
	
	public CouponListVO getOneByOrMem(String or_num, String mem_num){
		return dao.getOneByOrMem(or_num, mem_num);
	}
	
	
	public List<CouponListVO> getAbleListByMemSto(String mem_num, String sto_num){
		return dao.getAbleListByMemSto(mem_num, sto_num);
	}
	
	public void updateTimeByOrnum(boolean use, String or_num){
		dao.updateTimeByOrnum(use, or_num);
	}
	
	public void updateOrnumByOrnum(String or_num, String or_num2){
		dao.updateOrnumByOrnum(or_num, or_num2);
	}
	
	public void updateOrnumByCaCn(String or_num, int coupon_amount, String coupon_num){
		dao.updateOrnumByCaCn(or_num, coupon_amount, coupon_num);
	}
}
