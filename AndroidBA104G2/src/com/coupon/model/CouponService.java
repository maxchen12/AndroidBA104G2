package com.coupon.model;

public class CouponService {
	CouponDAO_interface dao;
	
	public CouponService(){
		dao = new CouponDAO();
	}
	
	public CouponVO getOneByCouNum(String coupon_num){
		return dao.getOneByCouNum(coupon_num);
	}
}
