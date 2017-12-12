package com.order_master.model;

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

import com.store_profile.model.StoreProfileVO;

public class OrderMasterDAO implements OrderMasterDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	private static final String GET_LIST_BY_MEMNUM = "select * from order_master where mem_num = ? order by or_time desc";
	private static final String CREATE_ORDER = "INSERT INTO ORDER_MASTER (OR_NUM, MEM_NUM, STO_NUM, OR_STANUM, RECE, PAY_MEN, OR_AMOUNT)"
			                                   +"VALUES ('OM' || TRIM(TO_CHAR(SEQ_OR_NUM.NEXTVAL, '0000000000')), ?, ?, '修改中', '自取', '現金', 0)";
	
	private static final String CREATE_MEROR_ORDER = "INSERT INTO ORDER_MASTER (OR_NUM, MEM_NUM, STO_NUM, OR_STANUM, RECE, PAY_MEN, OR_AMOUNT, MEROR_NUM)"
            +"VALUES ('OM' || TRIM(TO_CHAR(SEQ_OR_NUM.NEXTVAL, '0000000000')), ?, ?, '修改中', '自取', '現金', 0,?)";
	
	private static final String GET_ONE_BY_OR_NUM = "select * from order_master where or_num = ?";
	private static final String GET_ONE_BY_MEM_MER = "select * from order_master where mem_num = ? and meror_num=?";
	private static final String UPDATE_BY_ORNUM = "update ORDER_MASTER set or_time = SYSDATE, rece=nvl(?, rece), pay_men=nvl(?, pay_men), or_amount=nvl(?, or_amount), meror_num=nvl(?, meror_num), or_stanum=nvl(?, or_stanum),address=nvl(?, address) where or_num=?";
	private static final String UPDATE_BY_ORNUM_EXCEPT_TIME = "update ORDER_MASTER set rece=nvl(?, rece), pay_men=nvl(?, pay_men), or_amount=nvl(?, or_amount), meror_num=nvl(?, meror_num), or_stanum=nvl(?, or_stanum),address=nvl(?, address) where or_num=?";
	
	private static final String GET_LIST_BY_MERORNUM = "select * from order_master where meror_num = ?";
	
	
	
	private static final String GET_LIST_BY_STONUM = "SELECT * FROM ORDER_MASTER WHERE STO_NUM= ? "
			+ "and or_stanum!='修改中' and or_stanum!='已確認' order by or_time desc";
	
	@Override
	public List<OrderMasterVO> getListByStoNum(String sto_num){
		List<OrderMasterVO> orderList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_STONUM);
			pstmt.setString(1, sto_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				OrderMasterVO orderVO = new OrderMasterVO();
				
				orderVO.setOr_num(rs.getString("or_num"));
				orderVO.setMem_num(rs.getString("mem_num"));
				orderVO.setSto_num(rs.getString("sto_num"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				orderVO.setOr_stanum(rs.getString("or_stanum"));
				orderVO.setRece(rs.getString("rece"));
				orderVO.setPay_men(rs.getString("pay_men"));
				orderVO.setPre_rece(rs.getTimestamp("pre_rece"));
				orderVO.setOr_amount(rs.getInt("or_amount"));
				orderVO.setMeror_num(rs.getString("meror_num"));
				orderVO.setAddress(rs.getString("address"));
				orderList.add(orderVO);
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
		
		return orderList;
	}
	
	
	@Override
	public void updateOrder(OrderMasterVO omVO, boolean updateTime){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		try {
			

			con = ds.getConnection();
			if(updateTime){
				pstmt = con.prepareStatement(UPDATE_BY_ORNUM);
			}else{
				pstmt = con.prepareStatement(UPDATE_BY_ORNUM_EXCEPT_TIME);
			}
			
			pstmt.setString(1, omVO.getRece());
			pstmt.setString(2, omVO.getPay_men());
			if(omVO.getOr_amount()!=null){
				pstmt.setInt(3, omVO.getOr_amount());
			}else{
				pstmt.setString(3, null);
			}
			
			pstmt.setString(4, omVO.getMeror_num());
			pstmt.setString(5, omVO.getOr_stanum());
			pstmt.setString(6, omVO.getAddress());
			pstmt.setString(7, omVO.getOr_num());
			
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
	public List<OrderMasterVO> getListByMemNum(String mem_num) {
		List<OrderMasterVO> orderList = new ArrayList<>();
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
				OrderMasterVO orderVO = new OrderMasterVO();
				
				orderVO.setOr_num(rs.getString("or_num"));
				orderVO.setMem_num(rs.getString("mem_num"));
				orderVO.setSto_num(rs.getString("sto_num"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				orderVO.setOr_stanum(rs.getString("or_stanum"));
				orderVO.setRece(rs.getString("rece"));
				orderVO.setPay_men(rs.getString("pay_men"));
				orderVO.setPre_rece(rs.getTimestamp("pre_rece"));
				orderVO.setOr_amount(rs.getInt("or_amount"));
				orderVO.setMeror_num(rs.getString("meror_num"));
				orderVO.setAddress(rs.getString("address"));
//				orderVO.setIs_point(rs.getString("is_point"));
				
				orderList.add(orderVO);
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
		return orderList;
	}

	@Override
	public String createOrder(String mem_num, String sto_num) {
		String order_num = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String cols[] = { "OR_NUM" };
		
		//掘取對應的自增主鍵值
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(CREATE_ORDER, cols);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);

			pstmt.executeUpdate();

			ResultSet rsKeys = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rsKeys.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			
			if (rsKeys.next()) {
				order_num = rsKeys.getString(1);
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
		return order_num;
	}

	@Override
	public String createOrder(String mem_num, String sto_num, String meror_num) {
		String order_num = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String cols[] = { "OR_NUM" };
		
		//掘取對應的自增主鍵值
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(CREATE_MEROR_ORDER, cols);

			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);
			pstmt.setString(3, meror_num);
			
			pstmt.executeUpdate();

			ResultSet rsKeys = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rsKeys.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			
			if (rsKeys.next()) {
				order_num = rsKeys.getString(1);
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
		return order_num;
	}
	
	@Override
	public OrderMasterVO getOneByOrNum(String or_num) {
		OrderMasterVO orderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_OR_NUM);

			pstmt.setString(1, or_num);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				orderVO = new OrderMasterVO();
				
				orderVO.setOr_num(rs.getString("or_num"));
				orderVO.setMem_num(rs.getString("mem_num"));
				orderVO.setSto_num(rs.getString("sto_num"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				orderVO.setOr_stanum(rs.getString("or_stanum"));
				orderVO.setRece(rs.getString("rece"));
				orderVO.setPay_men(rs.getString("pay_men"));
				orderVO.setPre_rece(rs.getTimestamp("pre_rece"));
				orderVO.setOr_amount(rs.getInt("or_amount"));
				orderVO.setMeror_num(rs.getString("meror_num"));
				orderVO.setAddress(rs.getString("address"));
//				orderVO.setIs_point(rs.getString("is_point"));
			
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
		return orderVO;
	}

	
	@Override
	public OrderMasterVO getOneByMemMer(String mem_num, String meror_num) {
		OrderMasterVO orderVO = null;
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
				orderVO = new OrderMasterVO();
				
				orderVO.setOr_num(rs.getString("or_num"));
				orderVO.setMem_num(rs.getString("mem_num"));
				orderVO.setSto_num(rs.getString("sto_num"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				orderVO.setOr_stanum(rs.getString("or_stanum"));
				orderVO.setRece(rs.getString("rece"));
				orderVO.setPay_men(rs.getString("pay_men"));
				orderVO.setPre_rece(rs.getTimestamp("pre_rece"));
				orderVO.setOr_amount(rs.getInt("or_amount"));
				orderVO.setMeror_num(rs.getString("meror_num"));
				orderVO.setAddress(rs.getString("address"));
//				orderVO.setIs_point(rs.getString("is_point"));
			
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
		return orderVO;
	}
	
	
	@Override
	public List<OrderMasterVO> getListByMeror(String meror_num) {
		List<OrderMasterVO> orderList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_MERORNUM);

			pstmt.setString(1, meror_num);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				OrderMasterVO orderVO = new OrderMasterVO();
				
				orderVO.setOr_num(rs.getString("or_num"));
				orderVO.setMem_num(rs.getString("mem_num"));
				orderVO.setSto_num(rs.getString("sto_num"));
				orderVO.setOr_time(rs.getTimestamp("or_time"));
				orderVO.setOr_stanum(rs.getString("or_stanum"));
				orderVO.setRece(rs.getString("rece"));
				orderVO.setPay_men(rs.getString("pay_men"));
				orderVO.setPre_rece(rs.getTimestamp("pre_rece"));
				orderVO.setOr_amount(rs.getInt("or_amount"));
				orderVO.setMeror_num(rs.getString("meror_num"));
				orderVO.setAddress(rs.getString("address"));
//				orderVO.setIs_point(rs.getString("is_point"));
				
				orderList.add(orderVO);
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
		return orderList;
	}
	
	
}
