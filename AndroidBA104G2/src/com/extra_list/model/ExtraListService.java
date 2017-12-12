package com.extra_list.model;

import java.util.List;

public class ExtraListService {

	ExtraListDAO_interface dao;
	
	public ExtraListService(){
		dao = new ExtraListDAO();
	}
	
	public void addExtraList(String ordet_num, String ext_num){
		dao.addExtra(ordet_num, ext_num);
	}
	
	public List<ExtraListVO> getListByOdNum(String ordet_num){
		return dao.getListByOdNum(ordet_num);
	}
	
	public void delOneByOdID(String ordet_num){
		dao.delOneByOdID(ordet_num);
	}
}
