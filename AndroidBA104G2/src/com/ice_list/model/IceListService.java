package com.ice_list.model;

import java.util.List;

public class IceListService {

	IceListDAO_interface dao;
	
	public IceListService(){
		dao = new IceListDAO();
	}
	
	public List<IceListVO> getListByStoNum(String sto_num){
		return dao.getListbyStoNum(sto_num);
	}
	
	public String getNameByID(String ice_num){
		return dao.getNameByID(ice_num);
	}
}
