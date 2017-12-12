package com.card.model;

import java.io.Serializable;



public class CardVO implements Serializable {
	private String card_kinds;
	private String sto_num;
	private Integer points;
	private Integer points_cash;
	private String card_des;
	private Integer exp_date;
	private String status;
	
	public CardVO(){
		
	}
	
	public String getCard_kinds() {
		return card_kinds;
	}
	public void setCard_kinds(String card_kinds) {
		this.card_kinds = card_kinds;
	}
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getPoints_cash() {
		return points_cash;
	}
	public void setPoints_cash(Integer points_cash) {
		this.points_cash = points_cash;
	}
	public String getCard_des() {
		return card_des;
	}
	public void setCard_des(String card_des) {
		this.card_des = card_des;
	}
	public Integer getExp_date() {
		return exp_date;
	}
	public void setExp_date(Integer exp_date) {
		this.exp_date = exp_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
