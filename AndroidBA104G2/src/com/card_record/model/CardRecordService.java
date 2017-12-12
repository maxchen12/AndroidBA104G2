package com.card_record.model;

public class CardRecordService {

	CardRecordDAO_interface dao;
	
	public CardRecordService(){
		dao = new CardRecordDAO();
	}
	
	public void addRecord(String card_num, String or_num, int add_value){
		dao.addRecord(card_num, or_num, add_value);
	}
}
