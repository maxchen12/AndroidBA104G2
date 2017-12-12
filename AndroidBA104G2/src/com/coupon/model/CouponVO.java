package com.coupon.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CouponVO implements Serializable {
	private String coupon_num;
	private String sto_num;
	private Integer coupon_cash;
	private String coupon_desc;
	private Integer left;
	private Integer total;
	private Timestamp apply_date;
	private Timestamp up_date;
	private Timestamp down_date;
	private Timestamp exp_date;
	private byte[] image;
	private String notice_desc;
	private Timestamp notice_up_date;
	private Timestamp notice_down_date;
	private String coupon_status;
	
	public CouponVO (){
		
	}
	public String getCoupon_num() {
		return coupon_num;
	}
	public void setCoupon_num(String coupon_num) {
		this.coupon_num = coupon_num;
	}
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public Integer getCoupon_cash() {
		return coupon_cash;
	}
	public void setCoupon_cash(Integer coupon_cash) {
		this.coupon_cash = coupon_cash;
	}
	public String getCoupon_desc() {
		return coupon_desc;
	}
	public void setCoupon_desc(String coupon_desc) {
		this.coupon_desc = coupon_desc;
	}
	public Integer getLeft() {
		return left;
	}
	public void setLeft(Integer left) {
		this.left = left;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Timestamp getApply_date() {
		return apply_date;
	}
	public void setApply_date(Timestamp apply_date) {
		this.apply_date = apply_date;
	}
	public Timestamp getUp_date() {
		return up_date;
	}
	public void setUp_date(Timestamp up_date) {
		this.up_date = up_date;
	}
	public Timestamp getDown_date() {
		return down_date;
	}
	public void setDown_date(Timestamp down_date) {
		this.down_date = down_date;
	}
	public Timestamp getExp_date() {
		return exp_date;
	}
	public void setExp_date(Timestamp exp_date) {
		this.exp_date = exp_date;
	}
	public String getNotice_desc() {
		return notice_desc;
	}
	public void setNotice_desc(String notice_desc) {
		this.notice_desc = notice_desc;
	}
	public Timestamp getNotice_up_date() {
		return notice_up_date;
	}
	public void setNotice_up_date(Timestamp notice_up_date) {
		this.notice_up_date = notice_up_date;
	}
	public Timestamp getNotice_down_date() {
		return notice_down_date;
	}
	public void setNotice_down_date(Timestamp notice_down_date) {
		this.notice_down_date = notice_down_date;
	}
	public String getCoupon_status() {
		return coupon_status;
	}
	public void setCoupon_status(String coupon_status) {
		this.coupon_status = coupon_status;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
	
	
	
	
}
