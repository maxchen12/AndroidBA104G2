package com.store_comment.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.product.model.ProductVO;

public class StoreCommentDAO implements StoreCommentDAO_interface{

	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_LIST_BY_STONUM = "select * from store_comment where sto_num=?";
	private static final String GET_LIST_BY_STONUM_STATUS = "select * from store_comment where sto_num=? AND STATUS=?";
	private static final String ADD_COMMENT = "INSERT INTO STORE_COMMENT (COM_NUM, COM_TITLE, STO_NUM, MEM_NUM, STARS, COMMENTT, or_num, status)"+
										"VALUES ('CM' || TRIM(TO_CHAR(SEQ_COM_NUM.NEXTVAL, '0000000000000')), ?, ?, ?, ?, ?, ?,'一般')";
	
	
	
	public List<StoreCommentVO> getListByStoStatus(String sto_num, String status){
		List<StoreCommentVO> commentList = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_STONUM_STATUS) ;
		
			pstmt.setString(1, sto_num);
			pstmt.setString(2, status);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				StoreCommentVO commentVO = new StoreCommentVO();
				commentVO.setCom_num(rs.getString("com_num"));
				commentVO.setCom_title(rs.getString("com_title"));
				commentVO.setSto_num(rs.getString("sto_num"));
				commentVO.setMem_num(rs.getString("mem_num"));
				commentVO.setStars(rs.getInt("stars"));
				commentVO.setCommentt(rs.getString("commentt"));
				commentVO.setCom_time(rs.getTimestamp("com_time"));
				commentList.add(commentVO);
				
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return commentList;
	}
	
	public void addComment(String com_title, String sto_num, String mem_num, int stars, String commentt, String or_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_COMMENT) ;
		
			pstmt.setString(1, com_title);
			pstmt.setString(2, sto_num);
			pstmt.setString(3, mem_num);
			pstmt.setInt(4, stars);
			pstmt.setString(5, commentt);
			pstmt.setString(6, or_num);
			
			pstmt.executeUpdate();

			

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	public List<StoreCommentVO> getListByStoNum(String sto_num){
		List<StoreCommentVO> commentList = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_STONUM) ;
		
			pstmt.setString(1, sto_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				StoreCommentVO commentVO = new StoreCommentVO();
				commentVO.setCom_num(rs.getString("com_num"));
				commentVO.setCom_title(rs.getString("com_title"));
				commentVO.setSto_num(rs.getString("sto_num"));
				commentVO.setMem_num(rs.getString("mem_num"));
				commentVO.setStars(rs.getInt("stars"));
				commentVO.setCommentt(rs.getString("commentt"));
				commentVO.setCom_time(rs.getTimestamp("com_time"));
				commentList.add(commentVO);
				
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return commentList;
	}
}
