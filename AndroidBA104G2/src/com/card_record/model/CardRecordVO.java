package com.card_record.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CardRecordVO implements Serializable {
	private String card_num;
	private Integer or_num;
	private Integer add_value;
	private Timestamp get_date;
	
	public CardRecordVO(){
		
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public Integer getOr_num() {
		return or_num;
	}

	public void setOr_num(Integer or_num) {
		this.or_num = or_num;
	}

	public Integer getAdd_value() {
		return add_value;
	}

	public void setAdd_value(Integer add_value) {
		this.add_value = add_value;
	}

	public Timestamp getGet_date() {
		return get_date;
	}

	public void setGet_date(Timestamp get_date) {
		this.get_date = get_date;
	}
	
	
}
