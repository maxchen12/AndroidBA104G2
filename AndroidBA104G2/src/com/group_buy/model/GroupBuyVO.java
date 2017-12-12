package com.group_buy.model;

import java.util.Date;

public class GroupBuyVO implements java.io.Serializable {
	private String inv_mem_num;
	private String invd_mem_num;
	private String meror_num;
	private String isaccept;
	private Date inv_time;
	
	
	
	public Date getInv_time() {
		return inv_time;
	}

	public void setInv_time(Date inv_time) {
		this.inv_time = inv_time;
	}

	public String getInv_mem_num() {
		return inv_mem_num;
	}

	public void setInv_mem_num(String inv_mem_num) {
		this.inv_mem_num = inv_mem_num;
	}

	public String getInvd_mem_num() {
		return invd_mem_num;
	}

	public void setInvd_mem_num(String invd_mem_num) {
		this.invd_mem_num = invd_mem_num;
	}

	public String getMeror_num() {
		return meror_num;
	}

	public void setMeror_num(String meror_num) {
		this.meror_num = meror_num;
	}

	public String getIsaccept() {
		return isaccept;
	}

	public void setIsaccept(String isaccept) {
		this.isaccept = isaccept;
	}
}