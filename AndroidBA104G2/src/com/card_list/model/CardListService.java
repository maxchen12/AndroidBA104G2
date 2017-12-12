package com.card_list.model;

import java.util.List;

public class CardListService {
	CardListDAO_interface dao;
	
	public CardListService(){
		dao = new CardListDAO();
	}
	
	public CardListVO getOneByMemSto(String mem_num, String sto_num){
		return dao.getOneByMemSto(mem_num, sto_num);
	}
	
	public CardListVO getOneByOrnumMemnum(String or_num, String mem_num){
		return dao.getOneByOrnumMemnum(or_num, mem_num);
	}
	
	public List<CardListVO> getAvaListByMemSto(String mem_num, String sto_num){
		return dao.getAvaListByMemSto(mem_num, sto_num);
	}
	
	public void updateOrnumByOrnum(String or_num, String or_num2){
		dao.updateOrnumByOrnum(or_num, or_num2);
	}
	
	public void updateOrnumByIDType(String orderID, String cardID, String cardType){
		dao.updateOrnumByIDType(orderID, cardID, cardType);
		    
	}
	
	public void updateTimeByOrnum(boolean use,String or_num){
		dao.updateTimeByOrnum(use,or_num);
	}
	
	public void updateStatusByOrnum(String status, String or_num, Integer value){
		dao.updateStatusByOrnum(status, or_num, value);
	}
	
	public void addCard(String mem_num, String sto_num, String card_kinds, Integer exp_date){
		dao.addCard(mem_num, sto_num, card_kinds, exp_date);
	}
	
	public void updateStatusByMemnum(String status, String mem_num, Integer value){
		dao.updateStatusByMemnum(status, mem_num, value);
	}
}
