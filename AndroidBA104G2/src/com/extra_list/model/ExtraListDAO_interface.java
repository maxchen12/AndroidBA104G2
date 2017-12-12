package com.extra_list.model;

import java.util.List;

public interface ExtraListDAO_interface {

	public void addExtra(String ordet_num, String ext_num);
	public List<ExtraListVO> getListByOdNum(String ordet_num);
	public void delOneByOdID(String ordet_num);
}
