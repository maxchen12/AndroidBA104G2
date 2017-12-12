package com.extra.model;

import java.util.List;

public interface ExtraDAO_interface {

	public List<ExtraVO> getListByStoNum(String sto_num);
	
	public ExtraVO getOneByExtID(String ext_num);
}
