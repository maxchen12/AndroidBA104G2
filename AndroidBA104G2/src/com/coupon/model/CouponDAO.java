package com.coupon.model;

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

import com.coupon_list.model.CouponListVO;
import com.order_master.model.OrderMasterService;
import com.order_master.model.OrderMasterVO;

public class CouponDAO implements CouponDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
private static final String GET_ONE_BY_COUPONNUM ="select * from coupon where coupon_num=?";
	
	@Override
	public CouponVO getOneByCouNum(String coupon_num){
		CouponVO couVO= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_COUPONNUM);

			pstmt.setString(1, coupon_num);
			
			rs = pstmt.executeQuery();

			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				couVO = new CouponVO();
				couVO.setApply_date(rs.getTimestamp("apply_date"));
				couVO.setCoupon_cash(rs.getInt("coupon_cash"));
				couVO.setCoupon_desc(rs.getString("coupon_desc"));
				couVO.setCoupon_num(rs.getString("coupon_num"));
				couVO.setCoupon_status(rs.getString("coupon_status"));
				couVO.setDown_date(rs.getTimestamp("down_date"));
				couVO.setExp_date(rs.getTimestamp("exp_date"));
				couVO.setImage(rs.getBytes("image"));
				couVO.setLeft(rs.getInt("left"));
				couVO.setNotice_desc(rs.getString("notice_desc"));
				couVO.setNotice_down_date(rs.getTimestamp("notice_down_date"));
				couVO.setNotice_up_date(rs.getTimestamp("notice_up_date"));
				couVO.setSto_num(rs.getString("sto_num"));
				couVO.setTotal(rs.getInt("total"));
				couVO.setUp_date(rs.getTimestamp("up_date"));
				
				
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
	
}
