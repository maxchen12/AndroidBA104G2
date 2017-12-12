package com.card_list.model;

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

import com.coupon_list.model.CouponListVO;

public class CardListDAO implements CardListDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String UPDATE_ORNUM_BY_ORNUM = "update card_list set or_num=? where or_num=?";
	private static final String GET_AVA_LIST_BY_MEM_STO="select * from card_list where mem_num = ? and sto_num=? and status='可使用'";
//	private static final String GET_AVA_LIST_BY_MEM_STO = "select * from card_list  join card on card_list.CARD_KINDS = card.CARD_KINDS "+
//			 "where mem_num = ? and card_list.sto_num = ? and card_list.status='可使用'";
	
	private static final String UPDATE_ORNUM_BY_ID_TYPE = "update card_list set or_num=? where card_num =? and card_kinds=?";
	
	private static final String GET_ONE_BY_OR_MEM = "SELECT * FROM CARD_LIST WHERE OR_NUM=? AND MEM_NUM=?";
	
	private static final String UPDATE_TIME_BY_ORNUM = "update CARD_LIST set used_date=sysdate where or_num=?";
	
	private static final String UPDATE_NULLTIME_BY_ORNUM = "update CARD_LIST set used_date=null where or_num=?";

	private static final String UPDATE_STATUS_BY_ORNUM = "update CARD_LIST set status=nvl(?, status) , value=nvl(?,value)where or_num=?";

	private static final String GET_ONE_BY_MEM_STO ="select * from card_list where mem_num=? and sto_num = ? and status='尚未集滿' and exp_date>sysdate";

	private static final String ADD_CARD = "INSERT INTO CARD_LIST(CARD_NUM,MEM_NUM,STO_NUM,CARD_KINDS,VALUE,STATUS,EXP_DATE)"+
"VALUES( 'CN' || TRIM(TO_CHAR(SEQ_CARD_NUM.NEXTVAL, '000000')), ?, ?, ?, 0,'尚未集滿',to_date(ADD_MONTHS(sysdate, ?)))";
	
	private static final String UPDATE_STATUS_BY_MEMNUM = "update CARD_LIST set status=nvl(?, status) , value=nvl(?,value)where mem_num=? and status='尚未集滿'";
	
	@Override
	public void addCard(String mem_num, String sto_num, String card_kinds, Integer exp_date){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			
			pstmt = con.prepareStatement(ADD_CARD);
			
			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);
			pstmt.setString(3, card_kinds);
			pstmt.setInt(4, exp_date);


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
	public void updateStatusByMemnum(String status, String mem_num, Integer value){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_STATUS_BY_MEMNUM);
			
			

			pstmt.setString(1, status);
			pstmt.setString(3, mem_num);
			if(value == null){
				pstmt.setString(2, null);
			}else{
				pstmt.setInt(2, value);
			}

			
			

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
public void updateStatusByOrnum(String status, String or_num, Integer value){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	try {

		con = ds.getConnection();
		
		pstmt = con.prepareStatement(UPDATE_STATUS_BY_ORNUM);
		
		

		pstmt.setString(1, status);
		pstmt.setString(3, or_num);
		if(value == null){
			pstmt.setString(2, null);
		}else{
			pstmt.setInt(2, value);
		}

		
		

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
	public void updateTimeByOrnum(boolean use, String or_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			if(use){
				pstmt = con.prepareStatement(UPDATE_TIME_BY_ORNUM);
			}else{
				pstmt = con.prepareStatement(UPDATE_NULLTIME_BY_ORNUM);
			}
			

			pstmt.setString(1, or_num);
	
			
			

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
	public CardListVO getOneByOrnumMemnum(String or_num, String mem_num){
		CardListVO cardVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_OR_MEM);

			pstmt.setString(1, or_num);
			pstmt.setString(2, mem_num);
			
			

			rs = pstmt.executeQuery();

			while(rs.next()){
				cardVO = new CardListVO();
				cardVO.setCard_kinds(rs.getString("card_kinds"));
				cardVO.setCard_num(rs.getString("card_num"));
				cardVO.setExp_date(rs.getTimestamp("exp_date"));
				cardVO.setMem_num(rs.getString("mem_num"));
				cardVO.setOr_num(rs.getString("or_num"));
				cardVO.setStatus(rs.getString("status"));
				cardVO.setSto_num(rs.getString("sto_num"));
				cardVO.setUsed_date(rs.getTimestamp("used_date"));
				cardVO.setValue(rs.getInt("value"));
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
	public CardListVO getOneByMemSto(String mem_num, String sto_num){
		CardListVO cardVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MEM_STO);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);
			
			

			rs = pstmt.executeQuery();

			while(rs.next()){
				cardVO = new CardListVO();
				cardVO.setCard_kinds(rs.getString("card_kinds"));
				cardVO.setCard_num(rs.getString("card_num"));
				cardVO.setExp_date(rs.getTimestamp("exp_date"));
				cardVO.setMem_num(rs.getString("mem_num"));
				cardVO.setOr_num(rs.getString("or_num"));
				cardVO.setStatus(rs.getString("status"));
				cardVO.setSto_num(rs.getString("sto_num"));
				cardVO.setUsed_date(rs.getTimestamp("used_date"));
				cardVO.setValue(rs.getInt("value"));
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
	public void updateOrnumByIDType(String orderID, String cardID, String cardType){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ORNUM_BY_ID_TYPE);

			pstmt.setString(1, orderID);
			pstmt.setString(2, cardID);
			pstmt.setString(3, cardType);
			

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
	public void updateOrnumByOrnum(String or_num, String or_num2){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ORNUM_BY_ORNUM);

			pstmt.setString(1, or_num);
			pstmt.setString(2, or_num2);
			
			

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
	public List<CardListVO> getAvaListByMemSto(String mem_num, String sto_num){
		List<CardListVO> cardList= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_AVA_LIST_BY_MEM_STO);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);
			

			rs = pstmt.executeQuery();

			cardList = new ArrayList<>();
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				CardListVO cardVO = new CardListVO();
				cardVO.setCard_kinds(rs.getString("card_kinds"));
				cardVO.setCard_num(rs.getString("card_num"));
				cardVO.setExp_date(rs.getTimestamp("exp_date"));
				cardVO.setMem_num(rs.getString("mem_num"));
				cardVO.setOr_num(rs.getString("or_num"));
				cardVO.setStatus(rs.getString("status"));
				cardVO.setSto_num(rs.getString("sto_num"));
				cardVO.setUsed_date(rs.getTimestamp("used_date"));
				cardVO.setValue(rs.getInt("value"));
				if(cardVO.getUsed_date()==null){
					if(new Date().getTime() < cardVO.getExp_date().getTime()){
						cardList.add(cardVO);
					}
				}
				
//				if(couVO.getUsed_date()!= null){
//					OrderMasterService omSer = new OrderMasterService();
//					OrderMasterVO omVO = omSer.getOneByOrderID(couVO.getOr_num());
//					if("修改中".equals(omVO.getOr_stanum())){
//						couponList.add(couVO);
//					}
//				}else{
//					couponList.add(couVO);
//				}
//				
		
			
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
		
		return cardList;
	}
}
