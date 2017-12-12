package com.friend_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.order_master.model.OrderMasterVO;

public class FriendListDAO implements FriendListDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	private static final String GET_LIST_BY_MEMNUM ="SELECT * FROM FRIEND_LIST WHERE (INV_MEM_NUM=? OR INVD_MEM_NUM=?) AND ISFRIEND='Y'";
	private static final String GET_ONE_BY_INV_INVD = "SELECT * FROM FRIEND_LIST WHERE INV_MEM_NUM=? AND INVD_MEM_NUM=? ";
	private static final String UPDATE_ISFRIEND = "UPDATE FRIEND_LIST SET ISFRIEND = ? WHERE INV_MEM_NUM=? AND INVD_MEM_NUM=?";
	private static final String INSERT = "INSERT INTO FRIEND_LIST (INV_MEM_NUM, INVD_MEM_NUM, ISFRIEND)"+ 
											"VALUES (?, ?, ?)";
	
	@Override 
	public void insert(FriendListVO friVO){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, friVO.getInv_mem_num());
			pstmt.setString(2, friVO.getInvd_mem_num());
			pstmt.setString(3, friVO.getIsfriend());
			
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
	
	
	@Override 
	public void updateIsFriend(FriendListVO friVO){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ISFRIEND);

			pstmt.setString(1, friVO.getIsfriend());
			pstmt.setString(2, friVO.getInv_mem_num());
			pstmt.setString(3, friVO.getInvd_mem_num());
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
	
	@Override
	public FriendListVO getOneByInvInvd(String inv_mem_num, String invd_mem_num){
		FriendListVO friendVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_INV_INVD);

			pstmt.setString(1, inv_mem_num);
			pstmt.setString(2, invd_mem_num);

			rs = pstmt.executeQuery();

			
			while (rs.next()) {
				friendVO = new FriendListVO();
				friendVO.setInv_mem_num(rs.getString("inv_mem_num"));
				friendVO.setInvd_mem_num(rs.getString("invd_mem_num"));
				friendVO.setIsfriend(rs.getString("isfriend"));
				
			}
			
			// Handle any driver errors
		} catch (SQLException se) {
			friendVO = null;
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
		return friendVO;
	}
	
	@Override
	public  Set<String> getSetByMemNum(String mem_num){
		Set<String> friendIDSet = new HashSet<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_MEMNUM);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, mem_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				friendIDSet.add(rs.getString("INV_MEM_NUM"));
				friendIDSet.add(rs.getString("INVD_MEM_NUM"));
			}
			friendIDSet.remove(mem_num);

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
		return friendIDSet;
	}
}
