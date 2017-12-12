package com.group_buy.model;

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

public class GroupBuyDAO implements GroupBuyDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	private static final String GET_IS_ACCEPT_BY_INVD_MER= "SELECT * FROM GROUP_BUY WHERE INVD_MEM_NUM=? AND MEROR_NUM=?";
	private static final String INSERT = "INSERT INTO group_buy (INV_MEM_NUM, INVD_MEM_NUM, MEROR_NUM, ISACCEPT)"+ 
								"VALUES (?, ?, ?, 'ONCONFIRM')";
	private static final String GET_ONE_BY_INV_INVD_MEROR="SELECT * FROM GROUP_BUY WHERE INV_MEM_NUM =? and INVD_MEM_NUM=? AND MEROR_NUM=?";
	private static final String UPDATE_ISACCEPT = "UPDATE GROUP_BUY SET ISACCEPT=? WHERE INV_MEM_NUM=? AND INVD_MEM_NUM=? AND MEROR_NUM=?";
	private static final String GET_LIST_BY_INVD="select * from group_buy where INVD_MEM_NUM =? order by INV_TIME desc";
	
	
	@Override
	public List<GroupBuyVO> getListByInvd(String invd_mem_num){
		List<GroupBuyVO> groupList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_INVD);

			pstmt.setString(1, invd_mem_num);
			
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()){
				GroupBuyVO groupVO = new GroupBuyVO();
				groupVO.setInv_mem_num(rs.getString("inv_mem_num"));
				groupVO.setInvd_mem_num(rs.getString("invd_mem_num"));
				groupVO.setIsaccept(rs.getString("isaccept"));
				groupVO.setMeror_num(rs.getString("meror_num"));
				groupVO.setInv_time(rs.getTimestamp("inv_time"));
				
				groupList.add(groupVO);
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
		return groupList;
	}
	
	@Override
	public void updateByInvInvdMer(String isAccept, String inv_mem_num, String invd_mem_num, String meror_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ISACCEPT);

			pstmt.setString(1, isAccept);
			pstmt.setString(2, inv_mem_num);
			pstmt.setString(3, invd_mem_num);
			pstmt.setString(4, meror_num);
			
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
	public GroupBuyVO getOneByInvInvdMer(String inv_mem_num, String invd_mem_num, String meror_num){
		GroupBuyVO groupVO =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_INV_INVD_MEROR);

			pstmt.setString(1, inv_mem_num);
			pstmt.setString(2, invd_mem_num);
			pstmt.setString(3, meror_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				groupVO = new GroupBuyVO();
				groupVO.setInv_mem_num(rs.getString("Inv_mem_num"));
				groupVO.setInvd_mem_num(rs.getString("invd_mem_num"));
				groupVO.setMeror_num(rs.getString("meror_num"));
				groupVO.setIsaccept(rs.getString("isaccept"));
				groupVO.setInv_time(rs.getTimestamp("inv_time"));

				
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
		
		return groupVO;
	}
	
	@Override
	public void addInvite(String inv_mem_num, String invd_mem_num, String meror_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, inv_mem_num);
			pstmt.setString(2, invd_mem_num);
			pstmt.setString(3, meror_num);
			
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
	public  String getIsAcceptByInvdMer(String invd_mem_num, String meror_num){
		String isAccept=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_IS_ACCEPT_BY_INVD_MER);

			pstmt.setString(1, invd_mem_num);
			pstmt.setString(2, meror_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				isAccept  = rs.getString("isaccept");
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
		return isAccept;
	}
	
	
}
