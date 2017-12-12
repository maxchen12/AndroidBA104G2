package com.group_buy.model;

import java.util.List;

public class GroupBuyService {
	GroupBuyDAO_interface dao;
	
	public GroupBuyService(){
		dao = new GroupBuyDAO();
	}
	
	public String getIsAcceptByInvdMer(String invd_mem_num, String meror_num){
		return dao.getIsAcceptByInvdMer(invd_mem_num, meror_num);
	}
	
	public void addInvite(String inv_mem_num, String invd_mem_num, String meror_num){
		
		if(dao.getOneByInvInvdMer(inv_mem_num, invd_mem_num, meror_num) == null){
			dao.addInvite(inv_mem_num,invd_mem_num,meror_num);
		}else{
			dao.updateByInvInvdMer("ONCONFIRM", inv_mem_num, invd_mem_num, meror_num);
		}
		
	}
	
	public void acceptInvite(String inv_mem_num, String invd_mem_num, String meror_num){
		dao.updateByInvInvdMer("Y", inv_mem_num, invd_mem_num, meror_num);
	}
	
	public List<GroupBuyVO> getListByInvd(String invd_mem_num){
		return dao.getListByInvd(invd_mem_num);
	}
}
