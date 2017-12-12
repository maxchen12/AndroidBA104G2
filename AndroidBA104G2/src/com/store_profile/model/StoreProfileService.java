package com.store_profile.model;

import java.util.List;

public class StoreProfileService {

	private StoreProfileDAO_interface dao;
	
	public StoreProfileService(){
		dao = new StoreProfileDAO();
	}
	
	public StoreProfileVO findStoreByID(String sto_num){
		return dao.findStoreByID(sto_num);
	}
	public StoreProfileVO findStoreByAccPsw(String sto_acc, String sto_pwd){
		
		return dao.findStoreByAccPsw(sto_acc, sto_pwd);
	}
	
	public List<StoreProfileVO> getAll(String type){
		
		return dao.getAll(type);
	}
}
