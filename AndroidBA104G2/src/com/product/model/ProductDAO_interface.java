package com.product.model;

import java.util.List;

public interface ProductDAO_interface {
	
	public ProductVO getOneByComNum(String com_num);
	public List<ProductVO> getListByStoreIDandType(String sto_num, boolean isCombine);
}
