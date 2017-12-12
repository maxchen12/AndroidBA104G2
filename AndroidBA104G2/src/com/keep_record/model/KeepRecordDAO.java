package com.keep_record.model;

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

import com.store_profile.model.StoreProfileVO;

public class KeepRecordDAO implements KeepRecordDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_LIST_BY_MEMNUM = "select * from keep_record where mem_num=? and keep_status!='待確認' order by nvl(rec_time,keep_time) desc" ;
	private static final String DEL_ONE_BY_ODID ="delete from keep_record where ORDET_NUM = ?";
	private static final String ADD_ONE_BY_ODID = "INSERT INTO KEEP_RECORD (KEEP_NUM,MEM_NUM,STO_NUM,COM_NUM,ORDET_NUM,KEEP_STATUS)"+
"VALUES('KN' || TRIM(TO_CHAR(SEQ_KEEP_NUM.NEXTVAL, '0000000000')) ,?,?,?,?,'待確認')";
	
	private static final String GET_ONE_BY_ODID = "select * from keep_record where ordet_num = ?";
	
	private static final String UPDATE_STATUS_BY_ODID="UPDATE KEEP_RECORD SET KEEP_STATUS=? WHERE ORDET_NUM=?";
	private static final String UPDATE_STATUS_RECETIME_BY_ODID="UPDATE KEEP_RECORD SET KEEP_STATUS=?, rec_time=sysdate WHERE ORDET_NUM=?";
	
	
	private static final String GET_LIST_BY_STONUM = "select * from keep_record where sto_num=? and (keep_status='申請中' or keep_status='審核成功' or keep_status='已領取') order by nvl(rec_time,keep_time) desc";
	
	
	@Override
	public List<KeepRecordVO> findAvaListBySto(String sto_num){
		List<KeepRecordVO> keepList = new ArrayList<>();
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
				KeepRecordVO keepVO = new KeepRecordVO();
				
				keepVO.setKeep_num(rs.getString("keep_num"));
				keepVO.setMem_num(rs.getString("mem_num"));
				keepVO.setSto_num(rs.getString("sto_num"));
				keepVO.setCom_num(rs.getString("com_num"));
				keepVO.setOrdet_num(rs.getString("ordet_num"));
				keepVO.setKeep_time(rs.getTimestamp("keep_time"));
				keepVO.setRec_time(rs.getTimestamp("rec_time"));
				keepVO.setKeep_status(rs.getString("keep_status"));
				keepVO.setFail_reason(rs.getString("fail_reason"));
				
				

				keepList.add(keepVO);
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
		return keepList;
	}
	
	@Override
	public void updateStatusByOdnum(String keep_status, String ordet_num, boolean isRece){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			if(isRece){
				pstmt = con.prepareStatement(UPDATE_STATUS_RECETIME_BY_ODID);
			}else{
				pstmt = con.prepareStatement(UPDATE_STATUS_BY_ODID);
			}
			

			pstmt.setString(1, keep_status);
			pstmt.setString(2, ordet_num);
			
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
	public KeepRecordVO getOneByOdID(String ordet_num){
		KeepRecordVO keepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_ODID);

			pstmt.setString(1, ordet_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				keepVO = new KeepRecordVO();
				keepVO.setCom_num(rs.getString("com_num"));
				keepVO.setFail_reason(rs.getString("fail_reason"));
				keepVO.setKeep_num(rs.getString("keep_num"));
				keepVO.setKeep_status(rs.getString("keep_status"));
				keepVO.setKeep_time(rs.getTimestamp("keep_time"));
				keepVO.setMem_num(rs.getString("mem_num"));
				keepVO.setOrdet_num(rs.getString("ordet_num"));
				keepVO.setRec_time(rs.getTimestamp("rec_time"));
				keepVO.setSto_num(rs.getString("sto_num"));
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
		
		return keepVO;
		
	}
	
	@Override
	public void addOneByOdID(String mem_num, String sto_num, String com_num,String ordet_num){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_ONE_BY_ODID);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);
			pstmt.setString(3, com_num);
			pstmt.setString(4, ordet_num);
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
	public List<KeepRecordVO> findListByMemNum(String mem_num) {
		List<KeepRecordVO> keepList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_MEMNUM);

			pstmt.setString(1, mem_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				KeepRecordVO keepVO = new KeepRecordVO();
				
				keepVO.setKeep_num(rs.getString("keep_num"));
				keepVO.setMem_num(rs.getString("mem_num"));
				keepVO.setSto_num(rs.getString("sto_num"));
				keepVO.setCom_num(rs.getString("com_num"));
				keepVO.setOrdet_num(rs.getString("ordet_num"));
				keepVO.setKeep_time(rs.getTimestamp("keep_time"));
				keepVO.setRec_time(rs.getTimestamp("rec_time"));
				keepVO.setKeep_status(rs.getString("keep_status"));
				keepVO.setFail_reason(rs.getString("fail_reason"));
				
				

				keepList.add(keepVO);
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
		return keepList;
	}
	
	
}
