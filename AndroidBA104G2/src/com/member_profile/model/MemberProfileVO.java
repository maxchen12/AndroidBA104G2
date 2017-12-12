package com.member_profile.model;

public class MemberProfileVO implements java.io.Serializable{
	private String mem_num;
	private String mem_acc;
	private String mem_pwd;
	private String mem_name;
	private String sex;
	private Integer age;
	private String mobile;
	private String cek_mobile;
	private String email;
	private String address;
	private Integer rem_point;
	private byte[] mem_img;
	private String status;
	
	
	public String getMem_num() {
		return mem_num;
	}
	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_acc() {
		return mem_acc;
	}
	public void setMem_acc(String mem_acc) {
		this.mem_acc = mem_acc;
	}
	public String getMem_pwd() {
		return mem_pwd;
	}
	public void setMem_pwd(String mem_pwd) {
		this.mem_pwd = mem_pwd;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCek_mobile() {
		return cek_mobile;
	}
	public void setCek_mobile(String cek_mobile) {
		this.cek_mobile = cek_mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getRem_point() {
		return rem_point;
	}
	public void setRem_point(Integer rem_point) {
		this.rem_point = rem_point;
	}
	public byte[] getMem_img() {
		return mem_img;
	}
	public void setMem_img(byte[] mem_img) {
		this.mem_img = mem_img;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}