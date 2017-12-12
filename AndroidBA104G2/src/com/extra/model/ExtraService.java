package com.extra.model;

import java.util.List;

public class ExtraService {
	ExtraDAO_interface dao;
	
	public ExtraService(){
		dao = new ExtraDAO();
	}
	
	public List<ExtraVO> getListByStoNum(String sto_num){
		return dao.getListByStoNum(sto_num);
	}
	
	public ExtraVO getOneByExtID(String ext_num){
		return dao.getOneByExtID(ext_num);
	}
}
