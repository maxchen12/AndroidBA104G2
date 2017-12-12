package com.store_comment.model;

import java.util.List;

public interface StoreCommentDAO_interface {
	public List<StoreCommentVO> getListByStoNum(String sto_num);
	public void addComment(String com_title, String sto_num, String mem_num, int stars, String commentt, String or_num);
	public List<StoreCommentVO> getListByStoStatus(String sto_num, String status);
}
