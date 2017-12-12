package com.extra_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ExtraListDAO implements ExtraListDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_NEW ="INSERT INTO EXTRA_LIST (ORDET_NUM, EXT_NUM)" + 
												"VALUES (?, ?)";
	private static final String GET_LIST_BY_ODNUM = "select * from extra_list where ordet_num = ?";
			
	private static final String DEL_ONE_BY_ODID = "delete from extra_list where ORDET_NUM = ?";
	
	
	@Override
	public void delOneByOdID(String ordet_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DEL_ONE_BY_ODID);

			pstmt.setString(1, ordet_num);

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
	public void addExtra(String ordet_num, String ext_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_NEW);

			pstmt.setString(1, ordet_num);
			pstmt.setString(2, ext_num);

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
	public List<ExtraListVO> getListByOdNum(String ordet_num) {
		List<ExtraListVO> exList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_ODNUM);

			pstmt.setString(1, ordet_num);

			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ExtraListVO exVO = new ExtraListVO();
				exVO.setExt_num(rs.getString("ext_num"));
				exVO.setOrdet_num(rs.getString("ordet_num"));
				
//				System.out.println("ext_num " + exVO.getExt_num());
//				System.out.println("ordet_num " + exVO.getOrdet_num());
				exList.add(exVO);
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
		
		return exList;
	}

}
