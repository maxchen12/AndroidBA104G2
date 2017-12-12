package com.group_buy.model;

import java.util.List;

public interface GroupBuyDAO_interface {

	public String getIsAcceptByInvdMer(String invd_mem_num, String meror_num);
	public List<GroupBuyVO> getListByInvd(String invd_mem_num);
	public void addInvite(String inv_mem_num, String invd_mem_num, String meror_num);
	public GroupBuyVO getOneByInvInvdMer(String inv_mem_num, String invd_mem_num, String meror_num);
	public void updateByInvInvdMer(String isAccept, String inv_mem_num, String invd_mem_num, String meror_num);
}
