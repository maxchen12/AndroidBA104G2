package com.card.model;

public interface CardDAO_interface {
	public CardVO getOneByCardKinds(String card_kinds);
	public CardVO getAvaOneBySto(String sto_num);
}
