package com.store_profile.model;

import java.util.List;

public interface StoreProfileDAO_interface {

	public StoreProfileVO findStoreByAccPsw(String sto_acc, String sto_pwd);
	public StoreProfileVO findStoreByID(String sto_num);
	public List<StoreProfileVO> getAll(String type);
}
