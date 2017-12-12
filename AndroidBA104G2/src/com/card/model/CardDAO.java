package com.card.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.card_list.model.CardListVO;

public class CardDAO implements CardDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ONE_BY_KINDS="select * from card where card_kinds=?";
	private static final String GET_AVAONTE_BY_STO = "SELECT * FROM CARD WHERE STATUS='上架' AND STO_NUM=?";
	

	
	@Override
	public CardVO getAvaOneBySto(String sto_num){
		
		CardVO cardVO= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_AVAONTE_BY_STO);

			pstmt.setString(1, sto_num);
			
			rs = pstmt.executeQuery();

			
			while (rs.next()) {
				cardVO = new CardVO();
				
				cardVO.setCard_kinds(rs.getString("card_kinds"));
				cardVO.setCard_des(rs.getString("card_des"));
				cardVO.setExp_date(rs.getInt("exp_date"));
				cardVO.setPoints(rs.getInt("points"));
				cardVO.setPoints_cash(rs.getInt("points_cash"));
				cardVO.setStatus(rs.getString("status"));
				cardVO.setSto_num(rs.getString("sto_num"));
				
		
			
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
		return cardVO;
	}
	
	
	@Override
	public CardVO getOneByCardKinds(String card_kinds){
		
		CardVO cardVO= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_KINDS);

			pstmt.setString(1, card_kinds);
			
			rs = pstmt.executeQuery();

			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				cardVO = new CardVO();
				
				cardVO.setCard_kinds(rs.getString("card_kinds"));
				cardVO.setCard_des(rs.getString("card_des"));
				cardVO.setExp_date(rs.getInt("exp_date"));
				cardVO.setPoints(rs.getInt("points"));
				cardVO.setPoints_cash(rs.getInt("points_cash"));
				cardVO.setStatus(rs.getString("status"));
				cardVO.setSto_num(rs.getString("sto_num"));
				
		
			
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
		return cardVO;
	}
	
}
