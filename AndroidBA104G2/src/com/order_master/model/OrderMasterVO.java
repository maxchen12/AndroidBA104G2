package com.order_master.model;

import java.io.Serializable;
import java.util.Date;

public class OrderMasterVO implements Serializable{

	private String or_num;
	private String mem_num;
	private String sto_num;
	private Date or_time;
	private String or_stanum;
	private String rece;
	private String pay_men;
	private Date pre_rece;	
	private Integer or_amount;
	private String meror_num;
	private String address;
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOr_num() {
		return or_num;
	}
	public void setOr_num(String or_num) {
		this.or_num = or_num;
	}
	public String getMem_num() {
		return mem_num;
	}
	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public Date getOr_time() {
		return or_time;
	}
	public void setOr_time(Date or_time) {
		this.or_time = or_time;
	}
	public String getOr_stanum() {
		return or_stanum;
	}
	public void setOr_stanum(String or_stanum) {
		this.or_stanum = or_stanum;
	}
	public String getRece() {
		return rece;
	}
	public void setRece(String rece) {
		this.rece = rece;
	}
	public String getPay_men() {
		return pay_men;
	}
	public void setPay_men(String pay_men) {
		this.pay_men = pay_men;
	}
	public Date getPre_rece() {
		return pre_rece;
	}
	public void setPre_rece(Date pre_rece) {
		this.pre_rece = pre_rece;
	}
	public Integer getOr_amount() {
		return or_amount;
	}
	public void setOr_amount(Integer or_amount) {
		this.or_amount = or_amount;
	}
	public String getMeror_num() {
		return meror_num;
	}
	public void setMeror_num(String meror_num) {
		this.meror_num = meror_num;
	}

	
	
}
