package com.product.model;

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

import com.keep_record.model.KeepRecordVO;

public class ProductDAO implements ProductDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ONE_BY_COMNUM = "select * from product where com_num=?";
	private static final String GET_LIST_BY_STONUM_NOT_MERCOM = "select * from product where sto_num=? and status='已上架' and mercom_num is null";
	private static final String GET_LIST_BY_STONUM_MERCOM = "select * from product where sto_num=? and status='已上架' and not mercom_num is null";	
	@Override
	public ProductVO getOneByComNum(String com_num) {
		ProductVO proVO = new ProductVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_COMNUM);

			pstmt.setString(1, com_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				proVO.setCom_num(rs.getString("com_num"));
				proVO.setSto_num(rs.getString("sto_num"));
				proVO.setCom_name(rs.getString("com_name"));
				proVO.setM_price(rs.getInt("m_price"));
				proVO.setL_price(rs.getInt("l_price"));
				proVO.setDiscribe(rs.getString("discribe"));
				proVO.setImg(rs.getBytes("img"));
				proVO.setPt_num(rs.getString("pt_num"));
				proVO.setStatus(rs.getString("status"));
				proVO.setMercom_num(rs.getString("mercom_num"));
				
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
		return proVO;
	}
	
	public List<ProductVO> getListByStoreIDandType(String sto_num, boolean isCombine){
		List<ProductVO> proList = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			
			if(isCombine){
				pstmt = con.prepareStatement(GET_LIST_BY_STONUM_MERCOM) ;
			}else{
				pstmt = con.prepareStatement(GET_LIST_BY_STONUM_NOT_MERCOM) ;
			}
			

			pstmt.setString(1, sto_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductVO proVO = new ProductVO();
				// empVo 也稱為 Domain objects
				proVO.setCom_num(rs.getString("com_num"));
				proVO.setSto_num(rs.getString("sto_num"));
				proVO.setCom_name(rs.getString("com_name"));
				proVO.setM_price(rs.getInt("m_price"));
				proVO.setL_price(rs.getInt("l_price"));
				proVO.setDiscribe(rs.getString("discribe"));
				proVO.setImg(rs.getBytes("img"));
				proVO.setPt_num(rs.getString("pt_num"));
				proVO.setStatus(rs.getString("status"));
				proVO.setMercom_num(rs.getString("mercom_num"));
				
				proList.add(proVO);
				
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
		return proList;
	}

}
