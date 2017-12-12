package com.coupon_list.model;

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

import com.merged_order.model.MergedOrderVO;
import com.order_master.model.OrderMasterService;
import com.order_master.model.OrderMasterVO;

public class CouponListDAO implements CouponListDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_LIST_BY_MEM_STO ="SELECT * FROM COUPON_LIST JOIN COUPON ON COUPON_LIST.COUPON_NUM = COUPON.COUPON_NUM WHERE MEM_NUM=? AND STO_NUM=?";
	
	
	private static final String UPDATE_ORNUM_BY_ORNUM = "update coupon_list set or_num=? where or_num=?";
	private static final String UPDATE_ORNUM_BY_CA_CN = "update coupon_list set or_num=? where coupon_amount =? and coupon_num=?";
	
	private static final String GET_ONE_BY_OR_MEM = "select * from coupon_list where or_num=? and mem_num=?";
	
	private static final String UPDATE_TIME_BY_ORNUM = "update coupon_list set used_date=sysdate where or_num=?";
	private static final String UPDATE_NULLTIME_BY_ORNUM = "update coupon_list set used_date=null where or_num=?";
	
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
	public CouponListVO getOneByOrMem(String or_num, String mem_num){
		CouponListVO couVO= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_OR_MEM);

			pstmt.setString(1, or_num);
			pstmt.setString(2, mem_num);
			
			

			rs = pstmt.executeQuery();

	
			while (rs.next()) {
				couVO = new CouponListVO();
				
				couVO.setCoupon_amount(rs.getInt("coupon_amount"));
				couVO.setCoupon_num(rs.getString("coupon_num"));
				couVO.setGet_date(rs.getTimestamp("get_date"));
				couVO.setMem_num(rs.getString("mem_num"));
				couVO.setOr_num(rs.getString("or_num"));
				couVO.setUsed_date(rs.getTimestamp("used_date"));
			
			
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
		return couVO;
		
	}
	
	@Override
	public void updateOrnumByCaCn(String or_num, int coupon_amount, String coupon_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ORNUM_BY_CA_CN);

			pstmt.setString(1, or_num);
			pstmt.setInt(2, coupon_amount);
			pstmt.setString(3, coupon_num);
			

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
	public List<CouponListVO> getAbleListByMemSto(String mem_num, String sto_num){
		List<CouponListVO> couponList= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_MEM_STO);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);
			
			

			rs = pstmt.executeQuery();

			couponList = new ArrayList<>();
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				CouponListVO couVO = new CouponListVO();
				
				couVO.setCoupon_amount(rs.getInt("coupon_amount"));
				couVO.setCoupon_num(rs.getString("coupon_num"));
				couVO.setGet_date(rs.getTimestamp("get_date"));
				couVO.setMem_num(rs.getString("mem_num"));
				couVO.setOr_num(rs.getString("or_num"));
				couVO.setUsed_date(rs.getTimestamp("used_date"));
				
				
				if(couVO.getUsed_date()==null){
					if(new Date().getTime() < rs.getTimestamp("exp_date").getTime()){
						couponList.add(couVO);
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
		return couponList;
	}
}
