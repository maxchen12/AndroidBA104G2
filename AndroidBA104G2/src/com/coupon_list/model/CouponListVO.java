package com.coupon_list.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CouponListVO implements Serializable{
	private String coupon_num;
	private Integer coupon_amount;
	private String mem_num;
	private String or_num;
	private Timestamp get_date;
	private Timestamp used_date;
	
	public CouponListVO(){
		
	}
	
	public String getCoupon_num() {
		return coupon_num;
	}
	public void setCoupon_num(String coupon_num) {
		this.coupon_num = coupon_num;
	}
	public Integer getCoupon_amount() {
		return coupon_amount;
	}
	public void setCoupon_amount(Integer coupon_amount) {
		this.coupon_amount = coupon_amount;
	}
	public String getMem_num() {
		return mem_num;
	}
	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}
	public String getOr_num() {
		return or_num;
	}
	public void setOr_num(String or_num) {
		this.or_num = or_num;
	}
	public Timestamp getGet_date() {
		return get_date;
	}
	public void setGet_date(Timestamp get_date) {
		this.get_date = get_date;
	}
	public Timestamp getUsed_date() {
		return used_date;
	}
	public void setUsed_date(Timestamp used_date) {
		this.used_date = used_date;
	}
	
	
}
