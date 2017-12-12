package com.sweetness.model;

public class SweetnessVO implements java.io.Serializable {
	private String sweet_num;
	private String sto_num;
	private String sweet_type;
	private String status;

	public String getSweet_num() {
		return sweet_num;
	}

	public void setSweet_num(String sweet_num) {
		this.sweet_num = sweet_num;
	}

	public String getSto_num() {
		return sto_num;
	}

	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}

	public String getSweet_type() {
		return sweet_type;
	}

	public void setSweet_type(String sweet_type) {
		this.sweet_type = sweet_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}