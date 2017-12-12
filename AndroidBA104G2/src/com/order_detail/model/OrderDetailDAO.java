package com.order_detail.model;

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

public class OrderDetailDAO implements OrderDetailDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_NEW ="INSERT INTO ORDER_DETAIL (ORDET_NUM, COM_NUM, OR_NUM, SWEET_NUM, ICE_NUM, COM_SIZE, MERCOM_NUM, OD_PRICE)"+ 
"VALUES ('OD' || TRIM(TO_CHAR(SEQ_ORDET_NUM.NEXTVAL, '0000000000')), ?, ?, ?, ?, ?, ?, ?)";
			
	private static final String GET_LIST_BY_ORNUM = "select * from order_detail where or_num = ?";		
	private static final String DEL_ONE_BY_ODID ="delete from order_detail where ORDET_NUM = ?";
	
	private static final String GET_ONE_BY_ODID = "select * from order_detail where ordet_num=?";
	
	@Override
	public OrderDetailVO getOneByODID(String ordet_num){
		OrderDetailVO odVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_ODID);

			pstmt.setString(1, ordet_num);

			rs = pstmt.executeQuery();
			
			while(rs.next()){
				odVO = new OrderDetailVO();
				
				odVO.setCom_num(rs.getString("com_num"));
				odVO.setCom_size(rs.getString("com_size"));
				odVO.setIce_num(rs.getString("ice_num"));
				odVO.setMercom_num(rs.getString("mercom_num"));
				odVO.setOd_price(rs.getInt("od_price"));
				odVO.setOr_num(rs.getString("or_num"));
				odVO.setOrdet_num(rs.getString("ordet_num"));
				odVO.setSweet_num(rs.getString("sweet_num"));
				
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
		return odVO;
	}
	
	
	@Override
	public void delOneByodID(String ordet_num){
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
	public String addOrderDetail(OrderDetailVO odVO) {
		String ordet_num="";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String cols[] = { "ORDET_NUM" };
		
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_NEW, cols);

			pstmt.setString(1, odVO.getCom_num());
			pstmt.setString(2, odVO.getOr_num());
			pstmt.setString(3, odVO.getSweet_num());
			pstmt.setString(4, odVO.getIce_num());
			pstmt.setString(5, odVO.getCom_size());
			pstmt.setString(6, odVO.getMercom_num());
			pstmt.setInt(7, odVO.getOd_price());
			
			pstmt.executeUpdate();

			ResultSet rsKeys = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rsKeys.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			
			if (rsKeys.next()) {
				ordet_num = rsKeys.getString(1);
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
		return ordet_num;
	}

	@Override
	public List<OrderDetailVO> getListByOrNum(String or_num) {
		List<OrderDetailVO> odList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_ORNUM);

			pstmt.setString(1, or_num);
			rs = pstmt.executeQuery();

			while(rs.next()){
				OrderDetailVO odVO = new OrderDetailVO();
				
				odVO.setCom_num(rs.getString("com_num"));
				odVO.setCom_size(rs.getString("com_size"));
				odVO.setIce_num(rs.getString("ice_num"));
				odVO.setMercom_num(rs.getString("mercom_num"));
				odVO.setOd_price(rs.getInt("od_price"));
				odVO.setOr_num(rs.getString("or_num"));
				odVO.setOrdet_num(rs.getString("ordet_num"));
				odVO.setSweet_num(rs.getString("sweet_num"));
				
				
				odList.add(odVO);
				
			}
			
			// Handle any driver errors
		} catch (SQLException se) {
			System.out.println("wrong in odDAO getlistbyornum");
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
		return odList;
	}
	
	

}
