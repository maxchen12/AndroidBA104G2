package com.coupon_list.model;

import java.util.List;

public interface CouponListDAO_interface {

	public List<CouponListVO> getAbleListByMemSto(String mem_num, String sto_num);
	 
	public void updateOrnumByOrnum(String or_num, String or_num2);
	
	public void updateOrnumByCaCn(String or_num, int coupon_amount, String coupon_num);
	
	public CouponListVO getOneByOrMem(String or_num, String mem_num);
	
	public void updateTimeByOrnum(boolean use,String or_num);
}
