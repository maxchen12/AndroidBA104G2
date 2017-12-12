package com.shop_ad.model;

public class ShopAdService {

	ShopAdDAO_interface dao;
	
	public ShopAdService(){
		dao = new ShopAdDAO();
	}
	
	public int getAvaAdNumber(){
		return dao.getNumberOfAvaAd();
	}
	
	public ShopAdVO getOneByCounter(int counter){
		return dao.getOneByCounter(counter);
	}
}
