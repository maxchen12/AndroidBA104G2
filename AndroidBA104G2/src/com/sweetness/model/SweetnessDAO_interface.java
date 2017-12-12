package com.sweetness.model;

import java.util.List;

public interface SweetnessDAO_interface {
	
	public List<SweetnessVO> getListByStoNum(String sto_num);
	public String getNameByID(String sweet_num);
}
