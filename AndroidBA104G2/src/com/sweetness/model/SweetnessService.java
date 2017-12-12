package com.sweetness.model;

import java.util.List;

public class SweetnessService {
	
	SweetnessDAO_interface dao;
	
	public SweetnessService(){
		dao = new SweetnessDAO();
	}
	
	public List<SweetnessVO> getListByStoNum(String sto_num){
		return dao.getListByStoNum(sto_num);
	}
	
	public String getNameByID(String sweet_num){
		return dao.getNameByID(sweet_num);
	}
}
