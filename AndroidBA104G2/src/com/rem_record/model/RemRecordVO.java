package com.rem_record.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class RemRecordVO implements Serializable {
	private String rem_num;
	private String sto_num;
	private Integer rem_account;
	private Integer rem_cash;
	private Timestamp apply_date;
	private Timestamp rem_date;
	private String rem_status;
	
	
	public RemRecordVO(){
		
	}
	
	public Timestamp getRem_date() {
		return rem_date;
	}



	public void setRem_date(Timestamp rem_date) {
		this.rem_date = rem_date;
	}

	public String getRem_num() {
		return rem_num;
	}

	public void setRem_num(String rem_num) {
		this.rem_num = rem_num;
	}

	public String getSto_num() {
		return sto_num;
	}

	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}

	public int getRem_account() {
		return rem_account;
	}

	public void setRem_account(int rem_account) {
		this.rem_account = rem_account;
	}

	public int getRem_cash() {
		return rem_cash;
	}

	public void setRem_cash(int rem_cash) {
		this.rem_cash = rem_cash;
	}

	public Timestamp getApply_date() {
		return apply_date;
	}

	public void setApply_date(Timestamp timestamp) {
		this.apply_date = timestamp;
	}

	public String getRem_status() {
		return rem_status;
	}

	public void setRem_status(String rem_status) {
		this.rem_status = rem_status;
	}

}
