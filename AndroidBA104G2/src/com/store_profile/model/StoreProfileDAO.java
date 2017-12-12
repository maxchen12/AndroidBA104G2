package com.store_profile.model;

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

public class StoreProfileDAO implements StoreProfileDAO_interface{

	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	private static final String GET_ONE_BY_ACCPWD ="select * from STORE_PROFILE where STO_ACC=? and STO_PWD=?";
	private static final String GET_ONE_BY_STONUM ="select * from STORE_PROFILE where STO_NUM=?";
	private static final String GET_ALL = "select * from STORE_PROFILE WHERE STO_STATUS='已上架'";
	private static final String GET_ALL_DESC = "select * from STORE_PROFILE WHERE STO_STATUS='已上架' order by sto_num desc";
	@Override
	public StoreProfileVO findStoreByAccPsw(String sto_acc, String sto_pwd){
		StoreProfileVO stoProVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_ACCPWD);

			pstmt.setString(1, sto_acc);
			pstmt.setString(2, sto_pwd);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				stoProVO = new StoreProfileVO();
				
				stoProVO.setSto_num(rs.getString("STO_NUM"));
				stoProVO.setSto_acc(rs.getString("STO_ACC"));
				stoProVO.setSto_pwd(rs.getString("STO_PWD"));
				stoProVO.setSto_name(rs.getString("STO_NAME"));
				stoProVO.setGuy(rs.getString("GUY"));
				stoProVO.setUni_num(rs.getInt("UNI_NUM"));
				stoProVO.setMobile(rs.getString("MOBILE"));
				stoProVO.setArea(rs.getString("AREA"));
				stoProVO.setAddress(rs.getString("ADDRESS"));
				stoProVO.setSet_time(rs.getTimestamp("SET_TIME"));
//				logo introduce
				stoProVO.setSto_status(rs.getString("STO_STATUS"));
				stoProVO.setRem_point(rs.getInt("REM_POINT"));

			
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
		return stoProVO;
		
	}
	
	@Override
	public StoreProfileVO findStoreByID(String sto_num){
		
		StoreProfileVO stoProVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_STONUM);

			pstmt.setString(1, sto_num);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				stoProVO = new StoreProfileVO();
				
				stoProVO.setSto_num(rs.getString("STO_NUM"));
				stoProVO.setSto_acc(rs.getString("STO_ACC"));
				stoProVO.setSto_pwd(rs.getString("STO_PWD"));
				stoProVO.setSto_name(rs.getString("STO_NAME"));
				stoProVO.setGuy(rs.getString("GUY"));
				stoProVO.setUni_num(rs.getInt("UNI_NUM"));
				stoProVO.setMobile(rs.getString("MOBILE"));
				stoProVO.setArea(rs.getString("AREA"));
				stoProVO.setAddress(rs.getString("ADDRESS"));
				stoProVO.setSet_time(rs.getTimestamp("SET_TIME"));
				stoProVO.setSto_logo(rs.getBytes("STO_LOGO"));
//				introduce
				stoProVO.setSto_status(rs.getString("STO_STATUS"));
				stoProVO.setRem_point(rs.getInt("REM_POINT"));

			
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
		return stoProVO;
		
	}
	
	
	
	@Override
	public List<StoreProfileVO> getAll(String type){
		List<StoreProfileVO> stoList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			if("normal".equals(type)){
				pstmt = con.prepareStatement(GET_ALL);
			}
			else{
				pstmt = con.prepareStatement(GET_ALL_DESC);
			}
			

		
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				StoreProfileVO stoProVO = new StoreProfileVO();
			
				stoProVO.setSto_num(rs.getString("STO_NUM"));
				stoProVO.setSto_acc(rs.getString("STO_ACC"));
				stoProVO.setSto_pwd(rs.getString("STO_PWD"));
				stoProVO.setSto_name(rs.getString("STO_NAME"));
				stoProVO.setGuy(rs.getString("GUY"));
				stoProVO.setUni_num(rs.getInt("UNI_NUM"));
				stoProVO.setMobile(rs.getString("MOBILE"));
				stoProVO.setArea(rs.getString("AREA"));
				stoProVO.setAddress(rs.getString("ADDRESS"));
				stoProVO.setSet_time(rs.getTimestamp("SET_TIME"));
				stoProVO.setSto_logo(rs.getBytes("STO_LOGO"));
//				introduce
				stoProVO.setSto_status(rs.getString("STO_STATUS"));
				stoProVO.setRem_point(rs.getInt("REM_POINT"));
					
				stoList.add(stoProVO);
			
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
		return stoList;
		
	}
	
	
	
}
