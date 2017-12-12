package com.merged_order.model;

public interface MergedOrderDAO_interface {

	public MergedOrderVO getOneByMemMer(String mem_num, String meror_num);
	public String addMOByMem(String mem_num);
	public MergedOrderVO getOneByMer(String meror_num);
	
	public void updatePrice(String meror_num, int tol_amount);
}
