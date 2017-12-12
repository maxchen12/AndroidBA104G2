package com.favorite_store.model;

import java.util.List;

public interface FavoriteStoreDAO_interface {

	public void updateByMemSto(String mem_num, String sto_num, String isFavo);
	
	public FavoriteStoreVO getOneByMemSto(String mem_num, String sto_num);
	
	public void insertByMemSto(String mem_num, String sto_num);
	
	public List<FavoriteStoreVO> getAllByMem(String mem_num);
}
