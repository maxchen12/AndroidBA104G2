package com.order_detail.model;

public class OrderDetailVO implements java.io.Serializable {
	private String ordet_num;
	private String com_num;
	private String or_num;
	private String sweet_num;
	private String ice_num;
	private String com_size;
	private String mercom_num;
	private Integer od_price;
	
	
	
	public Integer getOd_price() {
		return od_price;
	}

	public void setOd_price(Integer od_price) {
		this.od_price = od_price;
	}

	public String getOrdet_num() {
		return ordet_num;
	}

	public void setOrdet_num(String ordet_num) {
		this.ordet_num = ordet_num;
	}

	public String getCom_num() {
		return com_num;
	}

	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}

	public String getOr_num() {
		return or_num;
	}

	public void setOr_num(String or_num) {
		this.or_num = or_num;
	}

	public String getSweet_num() {
		return sweet_num;
	}

	public void setSweet_num(String sweet_num) {
		this.sweet_num = sweet_num;
	}

	public String getIce_num() {
		return ice_num;
	}

	public void setIce_num(String ice_num) {
		this.ice_num = ice_num;
	}

	public String getCom_size() {
		return com_size;
	}

	public void setCom_size(String com_size) {
		this.com_size = com_size;
	}

	public String getMercom_num() {
		return mercom_num;
	}

	public void setMercom_num(String mercom_num) {
		this.mercom_num = mercom_num;
	}

}