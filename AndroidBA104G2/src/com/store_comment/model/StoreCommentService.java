package com.store_comment.model;

import java.util.List;

public class StoreCommentService {

	StoreCommentDAO_interface dao;
	
	public StoreCommentService(){
		dao = new StoreCommentDAO();
	}
	
	public List<StoreCommentVO> getListByStoNum(String sto_num){
		return dao.getListByStoNum(sto_num);
	}
	
	public List<StoreCommentVO> getListByStoStatus(String sto_num, String status){
		return dao.getListByStoStatus(sto_num, status);
	}
	
	public void addComment(String com_title, String sto_num, String mem_num, int stars, String commentt, String or_num){
		dao.addComment(com_title, sto_num, mem_num, stars, commentt, or_num);
	}
}
