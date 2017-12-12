package com.keep_record.model;

import java.util.List;

public class KeepRecordService {
	KeepRecordDAO_interface dao;
	
	public KeepRecordService(){
		dao = new KeepRecordDAO();
	}
	
	public List<KeepRecordVO> findAvaListBySto(String sto_num){
		return dao.findAvaListBySto(sto_num);
	}
	
	public void updateStatusByOdnum(String keep_status, String ordet_num, boolean isRece){
		dao.updateStatusByOdnum(keep_status, ordet_num, isRece);
	}
	
	public List<KeepRecordVO> findListByMemNum(String mem_num){
		return dao.findListByMemNum(mem_num);
	}
	
	public void delOneByOdID(String ordet_num){
		dao.delOneByOdID(ordet_num);
	}
	
	public void addOneByOdID(String mem_num, String sto_num, String com_num,String ordet_num){
		dao.addOneByOdID(mem_num, sto_num, com_num, ordet_num);
	}
	
	public KeepRecordVO getOneByOdID(String ordet_num){
		return dao.getOneByOdID(ordet_num);
	}
}
