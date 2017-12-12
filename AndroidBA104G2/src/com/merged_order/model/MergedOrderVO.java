package com.merged_order.model;

public class MergedOrderVO implements java.io.Serializable {
	private String meror_num;
	private String mem_num;
	private Integer tol_amount;

	public String getMeror_num() {
		return meror_num;
	}

	public void setMeror_num(String meror_num) {
		this.meror_num = meror_num;
	}

	public String getMem_num() {
		return mem_num;
	}

	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}

	public Integer getTol_amount() {
		return tol_amount;
	}

	public void setTol_amount(Integer tol_amount) {
		this.tol_amount = tol_amount;
	}

}