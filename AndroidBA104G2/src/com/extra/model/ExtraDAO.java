package com.extra.model;

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

import com.sweetness.model.SweetnessVO;

public class ExtraDAO implements ExtraDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_LIST_BY_STONUM="select * from extra where sto_num = ? and status='上架'";
	private static final String GET_ONE_BY_EXTNUM = "select * from extra where EXT_NUM = ?";
	
	@Override
	public List<ExtraVO> getListByStoNum(String sto_num) {
		List<ExtraVO> extraList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_STONUM);

			pstmt.setString(1, sto_num);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				ExtraVO extraVO = new ExtraVO();
				
				extraVO.setExt_num(rs.getString("ext_num"));
				extraVO.setExt_name(rs.getString("ext_name"));
				extraVO.setExt_amount(rs.getInt("ext_amount"));
				extraVO.setSto_num(rs.getString("sto_num"));
				extraVO.setStatus(rs.getString("status"));
				
				extraList.add(extraVO);
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
		return extraList;
	}


	@Override
	public ExtraVO getOneByExtID(String ext_num) {
		ExtraVO exVO = new ExtraVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_EXTNUM);

			pstmt.setString(1, ext_num);
//			System.out.println("extnum" + ext_num);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				exVO.setExt_amount(rs.getInt("ext_amount"));
				exVO.setExt_name(rs.getString("ext_name"));
				exVO.setExt_num(rs.getString("ext_num"));
				exVO.setSto_num(rs.getString("sto_num"));
				exVO.setStatus(rs.getString("status"));
				
//				System.out.println("extname" + exVO.getExt_name());
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
		return exVO;
	}

	
}
