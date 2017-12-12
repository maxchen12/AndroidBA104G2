package com.favorite_store.model;

import java.util.List;

public class FavoriteStoreService {

	FavoriteStoreDAO_interface dao;
	
	public FavoriteStoreService(){
		dao = new FavoriteStoreDAO();
	}
	
	public void updateByMemSto(String mem_num, String sto_num, String isFavo){
		
		if(dao.getOneByMemSto(mem_num, sto_num)!=null){
			dao.updateByMemSto(mem_num, sto_num, isFavo);
		}
		else{
			dao.insertByMemSto(mem_num, sto_num);

		}
	}
	
	public List<FavoriteStoreVO> getAllByMemNum(String mem_num){
		return dao.getAllByMem(mem_num);
	}
	
	public FavoriteStoreVO getOnerByMemSto(String mem_num, String sto_num){
		return dao.getOneByMemSto(mem_num, sto_num);
	}
}
