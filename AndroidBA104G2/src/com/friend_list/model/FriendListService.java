package com.friend_list.model;

import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FriendListService {
	FriendListDAO_interface dao;
	
	public FriendListService(){
		dao = new FriendListDAO();
	}
	
	public Set<String> getSetByMemNum(String mem_num){
		return dao.getSetByMemNum(mem_num);
	}
	
	public void addFriend(String inv_mem_num, String invd_mem_num){
		
		
			FriendListVO friVO = new FriendListVO();
			friVO.setInv_mem_num(inv_mem_num);
			friVO.setInvd_mem_num(invd_mem_num);
			friVO.setIsfriend("Y");
			
			if(dao.getOneByInvInvd(inv_mem_num, invd_mem_num) != null){
				dao.updateIsFriend(friVO);
			}else{
				dao.insert(friVO);
			}
			
			friVO = new FriendListVO();
			friVO.setInv_mem_num(invd_mem_num);
			friVO.setInvd_mem_num(inv_mem_num);
			friVO.setIsfriend("Y");
			
			if(dao.getOneByInvInvd(invd_mem_num, inv_mem_num) != null){
				dao.updateIsFriend(friVO);
			}else{
				dao.insert(friVO);
			}
		
		
	}
}
