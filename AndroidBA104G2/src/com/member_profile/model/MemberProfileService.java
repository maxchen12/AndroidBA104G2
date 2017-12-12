package com.member_profile.model;

public class MemberProfileService {

	private MemberProfileDAO_interface dao;
	
	public MemberProfileService(){
		dao = new MemberProfileDAO();
	}
	
	public MemberProfileVO findMemberByAccPsw(String mem_acc, String mem_pwd){
		return dao.findMemberByAccPsw(mem_acc, mem_pwd);
	}
	
	public MemberProfileVO findMemberByID(String mem_num){
		return dao.findMemberByID(mem_num);
	}
	
	public void updatePointByMem(int point, String mem_num){
		dao.updatePointByMem(point, mem_num);
	}
	
}
