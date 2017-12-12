package com.ice_list.model;

public class IceListVO implements java.io.Serializable {
	private String ice_num;
	private String sto_num;
	private String ice_type;
	private String status;

	public String getIce_num() {
		return ice_num;
	}

	public void setIce_num(String ice_num) {
		this.ice_num = ice_num;
	}

	public String getSto_num() {
		return sto_num;
	}

	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}

	public String getIce_type() {
		return ice_type;
	}

	public void setIce_type(String ice_type) {
		this.ice_type = ice_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
