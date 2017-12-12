package com.shop_ad.model;

import java.util.Date;

public class ShopAdVO implements java.io.Serializable {
	private String sa_no;
	private String sto_num;
	private String sa_title;
	private String sa_text;
	private byte[] sa_img;
	private Integer sa_views;
	private Date sa_apptime;
	private Date sa_addtime;
	private Date sa_preofftime;
	private String ab_no;
	private String sa_addmode;
	private Date sa_paytime;
	
	public String getSa_no() {
		return sa_no;
	}
	public void setSa_no(String sa_no) {
		this.sa_no = sa_no;
	}
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public String getSa_title() {
		return sa_title;
	}
	public void setSa_title(String sa_title) {
		this.sa_title = sa_title;
	}
	public String getSa_text() {
		return sa_text;
	}
	public void setSa_text(String sa_text) {
		this.sa_text = sa_text;
	}
	public byte[] getSa_img() {
		return sa_img;
	}
	public void setSa_img(byte[] sa_img) {
		this.sa_img = sa_img;
	}
	public Integer getSa_views() {
		return sa_views;
	}
	public void setSa_views(Integer sa_views) {
		this.sa_views = sa_views;
	}
	public Date getSa_apptime() {
		return sa_apptime;
	}
	public void setSa_apptime(Date sa_apptime) {
		this.sa_apptime = sa_apptime;
	}
	public Date getSa_addtime() {
		return sa_addtime;
	}
	public void setSa_addtime(Date sa_addtime) {
		this.sa_addtime = sa_addtime;
	}
	public Date getSa_preofftime() {
		return sa_preofftime;
	}
	public void setSa_preofftime(Date sa_preofftime) {
		this.sa_preofftime = sa_preofftime;
	}
	public String getAb_no() {
		return ab_no;
	}
	public void setAb_no(String ab_no) {
		this.ab_no = ab_no;
	}
	public String getSa_addmode() {
		return sa_addmode;
	}
	public void setSa_addmode(String sa_addmode) {
		this.sa_addmode = sa_addmode;
	}
	public Date getSa_paytime() {
		return sa_paytime;
	}
	public void setSa_paytime(Date sa_paytime) {
		this.sa_paytime = sa_paytime;
	}
}