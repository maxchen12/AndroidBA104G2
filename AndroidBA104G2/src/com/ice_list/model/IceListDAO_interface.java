package com.ice_list.model;

import java.util.List;

public interface IceListDAO_interface {

	public List<IceListVO> getListbyStoNum(String sto_num);
	public String getNameByID(String ice_num);
}
