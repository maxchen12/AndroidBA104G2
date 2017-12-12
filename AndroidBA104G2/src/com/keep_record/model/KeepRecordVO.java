package com.keep_record.model;

import java.util.Date;

public class KeepRecordVO implements java.io.Serializable {
	
	private String keep_num;
	private String mem_num;
	private String sto_num;
	private String com_num;
	private String ordet_num;
	private Date keep_time;
	private Date rec_time;
	private String keep_status;
	private String fail_reason;
	
	
	public String getKeep_num() {
		return keep_num;
	}
	public void setKeep_num(String keep_num) {
		this.keep_num = keep_num;
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
	public String getCom_num() {
		return com_num;
	}
	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}
	public String getOrdet_num() {
		return ordet_num;
	}
	public void setOrdet_num(String ordet_num) {
		this.ordet_num = ordet_num;
	}
	public Date getKeep_time() {
		return keep_time;
	}
	public void setKeep_time(Date keep_time) {
		this.keep_time = keep_time;
	}
	public Date getRec_time() {
		return rec_time;
	}
	public void setRec_time(Date rec_time) {
		this.rec_time = rec_time;
	}
	public String getKeep_status() {
		return keep_status;
	}
	public void setKeep_status(String keep_status) {
		this.keep_status = keep_status;
	}
	public String getFail_reason() {
		return fail_reason;
	}
	public void setFail_reason(String fail_reason) {
		this.fail_reason = fail_reason;
	}
	
	

}