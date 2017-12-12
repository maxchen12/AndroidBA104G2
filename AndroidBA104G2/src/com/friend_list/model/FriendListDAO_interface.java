package com.friend_list.model;

import java.util.List;
import java.util.Set;

public interface FriendListDAO_interface {

	public Set<String> getSetByMemNum(String mem_num);
//	public void addFriend(String inv_mem_num, String invd_mem_num);
	public FriendListVO getOneByInvInvd(String inv_mem_num, String invd_mem_num);
	public void updateIsFriend(FriendListVO friVO);
	public void insert(FriendListVO friVO);
	
}
