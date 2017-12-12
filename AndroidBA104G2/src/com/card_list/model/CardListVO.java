package com.card_list.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CardListVO implements Serializable {
	private String card_num;
	private String mem_num;
	private String sto_num;
	private String card_kinds;
	private Integer value;
	private String status;
	private String or_num;
	private Timestamp exp_date;
	private Timestamp used_date;
	
	
	public CardListVO(){
		
	}


	public String getCard_num() {
		return card_num;
	}


	public void setCard_num(String card_num) {
		this.card_num = card_num;
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


	public String getCard_kinds() {
		return card_kinds;
	}


	public void setCard_kinds(String card_kinds) {
		this.card_kinds = card_kinds;
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getOr_num() {
		return or_num;
	}


	public void setOr_num(String or_num) {
		this.or_num = or_num;
	}


	public Timestamp getExp_date() {
		return exp_date;
	}


	public void setExp_date(Timestamp exp_date) {
		this.exp_date = exp_date;
	}


	public Timestamp getUsed_date() {
		return used_date;
	}


	public void setUsed_date(Timestamp used_date) {
		this.used_date = used_date;
	}
	
	
}
