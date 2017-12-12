package com.store_profile.model;
import java.util.Date;

public class StoreProfileVO implements java.io.Serializable{
	private String sto_num;
	private String sto_acc;
	private String sto_pwd;
	private String sto_name;
	private String guy;
	private Integer uni_num;
	private String mobile;
	private String area;
	private String address;
	private Date set_time;
	private byte[] sto_logo;
	private String sto_introduce;
	private String sto_status;
	private Integer rem_point;
	
	
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public String getSto_acc() {
		return sto_acc;
	}
	public void setSto_acc(String sto_acc) {
		this.sto_acc = sto_acc;
	}
	public String getSto_pwd() {
		return sto_pwd;
	}
	public void setSto_pwd(String sto_pwd) {
		this.sto_pwd = sto_pwd;
	}
	public String getSto_name() {
		return sto_name;
	}
	public void setSto_name(String sto_name) {
		this.sto_name = sto_name;
	}
	public String getGuy() {
		return guy;
	}
	public void setGuy(String guy) {
		this.guy = guy;
	}
	public Integer getUni_num() {
		return uni_num;
	}
	public void setUni_num(Integer uni_num) {
		this.uni_num = uni_num;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getSet_time() {
		return set_time;
	}
	public void setSet_time(Date set_time) {
		this.set_time = set_time;
	}
	public byte[] getSto_logo() {
		return sto_logo;
	}
	public void setSto_logo(byte[] sto_logo) {
		this.sto_logo = sto_logo;
	}
	public String getSto_introduce() {
		return sto_introduce;
	}
	public void setSto_introduce(String sto_introduce) {
		this.sto_introduce = sto_introduce;
	}
	public String getSto_status() {
		return sto_status;
	}
	public void setSto_status(String sto_status) {
		this.sto_status = sto_status;
	}
	public Integer getRem_point() {
		return rem_point;
	}
	public void setRem_point(Integer rem_point) {
		this.rem_point = rem_point;
	}
	
	
}