package com.value_record.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ValueRecordVO implements Serializable {
	
	private String value_num;
	private String mem_num;
	private Timestamp value_date;
	private Integer value_cash;
	
	public ValueRecordVO(){
		
	}
	
	public String getValue_num() {
		return value_num;
	}
	public void setValue_num(String value_num) {
		this.value_num = value_num;
	}
	public String getMem_num() {
		return mem_num;
	}
	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}
	public Timestamp getValue_date() {
		return value_date;
	}
	public void setValue_date(Timestamp value_date) {
		this.value_date = value_date;
	}
	public Integer getValue_cash() {
		return value_cash;
	}
	public void setValue_cash(Integer value_cash) {
		this.value_cash = value_cash;
	}
	
	
	
	

}
