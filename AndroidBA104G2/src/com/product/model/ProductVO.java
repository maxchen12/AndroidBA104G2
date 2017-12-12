package com.product.model;

public class ProductVO implements java.io.Serializable {
	
	private String com_num;
	private String sto_num;
	private String com_name;
	private Integer m_price;
	private Integer l_price;
	private String discribe;
	private byte[] img;
	private String pt_num;
	private String status;
	private String mercom_num;

	public String getCom_num() {
		return com_num;
	}
	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public Integer getM_price() {
		return m_price;
	}
	public void setM_price(Integer m_price) {
		this.m_price = m_price;
	}
	public Integer getL_price() {
		return l_price;
	}
	public void setL_price(Integer l_price) {
		this.l_price = l_price;
	}
	public String getDiscribe() {
		return discribe;
	}
	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public String getPt_num() {
		return pt_num;
	}
	public void setPt_num(String pt_num) {
		this.pt_num = pt_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMercom_num() {
		return mercom_num;
	}
	public void setMercom_num(String mercom_num) {
		this.mercom_num = mercom_num;
	}
	
}