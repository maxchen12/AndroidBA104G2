package com.keep_record.model;

import java.util.List;

public interface KeepRecordDAO_interface {

	public List<KeepRecordVO> findListByMemNum(String mem_num);
	public List<KeepRecordVO> findAvaListBySto(String sto_num);
	
	public void delOneByOdID(String ordet_num);
	public void addOneByOdID(String mem_num, String sto_num, String com_num,String ordet_num);
	public KeepRecordVO getOneByOdID(String ordet_num);
	public void updateStatusByOdnum(String keep_status, String ordet_num, boolean isRece);
	
	
}
