package com.product.model;

import java.util.List;

public class ProductService {

	ProductDAO_interface dao ;
	public ProductService(){
		dao = new ProductDAO();
	}
	
	public ProductVO getOneByComNum(String com_num){
		return dao.getOneByComNum(com_num);
	}
	
	public List<ProductVO> getListByStoreIDandType(String storeID, boolean isCombine){
		
		return dao.getListByStoreIDandType(storeID, isCombine);
	}
}
