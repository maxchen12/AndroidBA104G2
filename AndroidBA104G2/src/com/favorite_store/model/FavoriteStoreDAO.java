package com.favorite_store.model;

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

import com.member_profile.model.MemberProfileVO;

public class FavoriteStoreDAO implements FavoriteStoreDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	private static final String UPDATE_BY_MEMNUM = "update favorite_store set is_favo=? where mem_num=? and sto_num=?";
	private static final String INSERT_BY_MEM_STO = "insert into favorite_store (mem_num, sto_num, is_favo) values (?, ?, ?)";
	private static final String GET_ONE_BY_MEM_STO = "select * from favorite_store where mem_num=? and sto_num=?";
	private static final String GET_ALL_BY_MEM = "select * from favorite_store where mem_num=? and is_favo='Y'";
	
	@Override
	public void updateByMemSto(String mem_num, String sto_num, String isFavo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
		
			pstmt = con.prepareStatement(UPDATE_BY_MEMNUM);

			pstmt.setString(1, isFavo);
			pstmt.setString(2, mem_num);
			pstmt.setString(3, sto_num);
			
			
			pstmt.executeUpdate();
			

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
		
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
	public void insertByMemSto(String mem_num, String sto_num){
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
		
			pstmt = con.prepareStatement(INSERT_BY_MEM_STO);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);
			pstmt.setString(3, "Y");
			
			
			pstmt.executeUpdate();
			

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
		
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
	public FavoriteStoreVO getOneByMemSto(String mem_num, String sto_num){
		FavoriteStoreVO faVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
		
			pstmt = con.prepareStatement(GET_ONE_BY_MEM_STO);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				faVO = new FavoriteStoreVO();
				faVO.setMem_num(rs.getString("mem_num"));
				faVO.setSto_num(rs.getString("sto_num"));
				faVO.setIs_favo(rs.getString("is_favo"));
				faVO.setChang_time(rs.getTimestamp("chang_time"));
				
				
				
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
		
		
		
		return faVO;
	}
	
	@Override
	public List<FavoriteStoreVO> getAllByMem(String mem_num){
		List<FavoriteStoreVO> faList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
		
			pstmt = con.prepareStatement(GET_ALL_BY_MEM);

			pstmt.setString(1, mem_num);
			

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				FavoriteStoreVO faVO = new FavoriteStoreVO();
				faVO.setMem_num(rs.getString("mem_num"));
				faVO.setSto_num(rs.getString("sto_num"));
				faVO.setIs_favo(rs.getString("is_favo"));
				faVO.setChang_time(rs.getTimestamp("chang_time"));
				
				faList.add(faVO);
				
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
		
		
		
		return faList;
	}

}
