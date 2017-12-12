package com.merged_order.model;

public class MergedOrderService {

	MergedOrderDAO_interface dao;
	public MergedOrderService(){
		dao = new MergedOrderDAO();
		
	}
	
	public MergedOrderVO getOneByMemMer(String mem_num, String meror_num){
		return dao.getOneByMemMer(mem_num, meror_num);
	}
	
	public MergedOrderVO getOneByMer(String meror_num){
		return dao.getOneByMer(meror_num);
	}
	
	public String addMOByMem(String mem_num){
		return dao.addMOByMem(mem_num);
	}
	
	public void updatePrice(String meror_num,  int tol_amount){
		dao.updatePrice(meror_num,  tol_amount);
	}
}
