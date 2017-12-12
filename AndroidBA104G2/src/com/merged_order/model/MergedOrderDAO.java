package com.merged_order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.order_master.model.OrderMasterVO;

public class MergedOrderDAO implements MergedOrderDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	private static final String GET_ONE_BY_MEM_MER = "SELECT * FROM MERGED_ORDER WHERE MEM_NUM = ? AND MEROR_NUM = ?";
	private static final String ADD_BY_MEM_NUM = "insert into merged_order (meror_num, mem_num, tol_amount) values('MO' || TRIM(TO_CHAR(SEQ_MEROR_NUM.NEXTVAL, '00000000')),?, 0)";
	private static final String GET_ONE_BY_MER = "SELECT * FROM MERGED_ORDER WHERE MEROR_NUM = ?";

	private static final String UPDATE_PRICE = "UPDATE MERGED_ORDER SET TOL_AMOUNT=? WHERE MEROR_NUM=? ";

	@Override
	public void updatePrice(String meror_num,  int tol_amount){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PRICE);
			pstmt.setInt(1, tol_amount);
			pstmt.setString(2, meror_num);
			
			
			
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
	public MergedOrderVO getOneByMer(String meror_num){
		MergedOrderVO mergeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MER);

		
			pstmt.setString(1, meror_num);
			
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				mergeVO = new MergedOrderVO();
				
				mergeVO.setMem_num(rs.getString("mem_num"));
				mergeVO.setMeror_num(rs.getString("meror_num"));
				mergeVO.setTol_amount(rs.getInt("tol_amount"));
				
		
			
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
		return mergeVO;
	}
	
	@Override
	public String addMOByMem(String mem_num) {
		String meror_num = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String cols[] = { "MEROR_NUM" };
		
		
		//掘取對應的自增主鍵值
		
		
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_BY_MEM_NUM, cols);

			pstmt.setString(1, mem_num);

			pstmt.executeUpdate();

			ResultSet rsKeys = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rsKeys.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			
			if (rsKeys.next()) {
				meror_num = rsKeys.getString(1);
//				do {
//					for (int i = 1; i <= columnCount; i++) {
//						
////						System.out.println("自增主鍵值(i=" + i + ") = " + order_num +"(剛新增成功的員工編號)");
//					}
//				} while (rsKeys.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
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
		return meror_num;
	}
	
	
	
	
	@Override
	public MergedOrderVO getOneByMemMer(String mem_num, String meror_num){
		MergedOrderVO mergeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MEM_MER);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, meror_num);
			
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				mergeVO = new MergedOrderVO();
				
				mergeVO.setMem_num(rs.getString("mem_num"));
				mergeVO.setMeror_num(rs.getString("meror_num"));
				mergeVO.setTol_amount(rs.getInt("tol_amount"));
				
		
			
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
		return mergeVO;
	}
	
	
	
}
