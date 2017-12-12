package com.card_list.model;

import java.util.Date;
import java.util.List;

public interface CardListDAO_interface {

	public List<CardListVO> getAvaListByMemSto(String mem_num, String sto_num);
	public void updateOrnumByOrnum(String or_num, String or_num2);
	public void updateOrnumByIDType(String orderID, String cardID, String cardType);
	public CardListVO getOneByOrnumMemnum(String or_num, String mem_num);
	public void updateTimeByOrnum(boolean use,String or_num);
	public void updateStatusByOrnum(String status, String or_num, Integer value);
	
	public void updateStatusByMemnum(String status, String mem_num, Integer value);
	
	
	public CardListVO getOneByMemSto(String mem_num, String sto_num);
	
	
	public void addCard(String mem_num, String sto_num, String card_kinds, Integer exp_date);
	
	
}
