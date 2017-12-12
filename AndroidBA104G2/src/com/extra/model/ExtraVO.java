package com.extra.model;

public class ExtraVO implements java.io.Serializable {

	private String ext_num;
	private String ext_name;
	private String sto_num;
	private Integer ext_amount;
	private String status;

	public String getExt_num() {
		return ext_num;
	}

	public void setExt_num(String ext_num) {
		this.ext_num = ext_num;
	}

	public String getExt_name() {
		return ext_name;
	}

	public void setExt_name(String ext_name) {
		this.ext_name = ext_name;
	}

	public String getSto_num() {
		return sto_num;
	}

	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}

	public Integer getExt_amount() {
		return ext_amount;
	}

	public void setExt_amount(Integer ext_amount) {
		this.ext_amount = ext_amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}