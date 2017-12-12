package com.member_profile.model;

public interface MemberProfileDAO_interface {
	
	public MemberProfileVO findMemberByAccPsw(String mem_acc, String mem_pwd);
	
	public MemberProfileVO findMemberByID(String mem_num);
	
	public void updatePointByMem(int point, String mem_num);
}
