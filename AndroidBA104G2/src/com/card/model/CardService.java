package com.card.model;

public class CardService {
	CardDAO_interface dao;
	
	public CardService(){
		dao = new CardDAO();
	}
	
	public CardVO getOneByCardKinds(String card_kinds){
		return dao.getOneByCardKinds(card_kinds);
	}
	
	public CardVO getAvaOneBySto(String sto_num){
		return dao.getAvaOneBySto(sto_num);
	}
}
