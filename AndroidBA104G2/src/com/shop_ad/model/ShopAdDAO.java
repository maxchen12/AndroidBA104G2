package com.shop_ad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.store_profile.model.StoreProfileVO;

public class ShopAdDAO implements ShopAdDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_NUMBER_BY_ADDMODE ="select count(*) as count from shop_ad where sa_addmode = '上架'";
	
	private static final String GET_ALL_AVA = "select * from shop_ad where sa_addmode='上架' order by sa_no";
	
	@Override
	public int getNumberOfAvaAd() {
		
		int number = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NUMBER_BY_ADDMODE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				number = rs.getInt("COUNT");

			
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
		return number;
	}


	@Override
	public ShopAdVO getOneByCounter(int counter) {
		ShopAdVO shopAdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_AVA);
			rs = pstmt.executeQuery();

			
			
			for(int i=0; i<=counter; i++){
				if(rs.next()==false){
					break;
				}
			}
			
			shopAdVO = new ShopAdVO();
			
			shopAdVO.setSa_no(rs.getString("sa_no"));
			shopAdVO.setSto_num(rs.getString("sto_num"));
			shopAdVO.setSa_title(rs.getString("sa_title"));
			shopAdVO.setSa_text(rs.getString("sa_text"));
			shopAdVO.setSa_img(rs.getBytes("sa_img"));
			shopAdVO.setSa_views(rs.getInt("sa_views"));
			shopAdVO.setSa_apptime(rs.getTimestamp("sa_apptime"));
			shopAdVO.setSa_addtime(rs.getTimestamp("sa_addtime"));
			shopAdVO.setSa_preofftime(rs.getTimestamp("sa_preofftime"));
			shopAdVO.setAb_no(rs.getString("ab_no"));
			shopAdVO.setSa_addmode(rs.getString("sa_addmode"));
			shopAdVO.setSa_paytime(rs.getTimestamp("sa_paytime"));


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
		return shopAdVO;
	}

}
