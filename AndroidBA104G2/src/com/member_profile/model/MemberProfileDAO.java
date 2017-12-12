package com.member_profile.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class MemberProfileDAO implements MemberProfileDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	private static final String GET_ONE_BY_ACCPWD ="select * from MEMBER_PROFILE where MEM_ACC=? and MEM_PWD=?";
	private static final String GET_ONE_BY_MEMNUM ="select * from MEMBER_PROFILE where MEM_NUM=?";
	private static final String UPDATE_POINT_BY_MEM = "update member_profile set rem_point = ? where mem_num=?";
	

	@Override
	public void updatePointByMem(int point, String mem_num){

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
		
			pstmt = con.prepareStatement(UPDATE_POINT_BY_MEM);

			pstmt.setInt(1, point);
			pstmt.setString(2, mem_num);

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
	public MemberProfileVO findMemberByAccPsw(String mem_acc, String mem_pwd) {
		
		MemberProfileVO memProVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
		
			pstmt = con.prepareStatement(GET_ONE_BY_ACCPWD);

			pstmt.setString(1, mem_acc);
			pstmt.setString(2, mem_pwd);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memProVO = new MemberProfileVO();
				memProVO.setMem_num(rs.getString("MEM_NUM"));
				
				memProVO.setMem_acc(rs.getString("MEM_ACC"));
				memProVO.setMem_pwd(rs.getString("MEM_PWD"));
				memProVO.setMem_name(rs.getString("MEM_NAME"));
				memProVO.setSex(rs.getString("SEX"));
				memProVO.setAge(rs.getInt("AGE"));
				memProVO.setMobile(rs.getString("MOBILE"));
				memProVO.setCek_mobile(rs.getString("CEK_MOBILE"));
				memProVO.setEmail(rs.getString("EMAIL"));
				memProVO.setAddress(rs.getString("ADDRESS"));
				memProVO.setRem_point(rs.getInt("REM_POINT"));
				memProVO.setStatus(rs.getString("STATUS"));
				memProVO.setMem_img(rs.getBytes("MEM_IMG"));
				
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
		
		
		
		return memProVO;
		
		
	}

	
	@Override
	public MemberProfileVO findMemberByID(String mem_num) {
		
		MemberProfileVO memProVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
		
			pstmt = con.prepareStatement(GET_ONE_BY_MEMNUM);

			pstmt.setString(1, mem_num);
			

			rs = pstmt.executeQuery();
			memProVO = new MemberProfileVO();
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				
				memProVO.setMem_num(rs.getString("MEM_NUM"));
				
				memProVO.setMem_acc(rs.getString("MEM_ACC"));
				memProVO.setMem_pwd(rs.getString("MEM_PWD"));
				memProVO.setMem_name(rs.getString("MEM_NAME"));
				memProVO.setSex(rs.getString("SEX"));
				memProVO.setAge(rs.getInt("AGE"));
				memProVO.setMobile(rs.getString("MOBILE"));
				memProVO.setCek_mobile(rs.getString("CEK_MOBILE"));
				memProVO.setEmail(rs.getString("EMAIL"));
				memProVO.setAddress(rs.getString("ADDRESS"));
				memProVO.setRem_point(rs.getInt("REM_POINT"));
				memProVO.setStatus(rs.getString("STATUS"));
				memProVO.setMem_img(rs.getBytes("MEM_IMG"));
				
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
		
		
		
		return memProVO;
		
		
	}
}
