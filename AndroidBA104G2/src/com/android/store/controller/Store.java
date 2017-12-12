package com.android.store.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;


public class Store extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

	  
	  String action = req.getParameter("action");
	  
	 
	  //在context中 放經緯度
	  ServletContext context = getServletContext();
	  Map<String, MyLocation> storePos = (Map<String, MyLocation>)context.getAttribute("storePos");
	  
	  if(storePos==null){
		  storePos = new HashMap<>();
		  context.setAttribute("storePos", storePos);
	  }
	  

      

      JSONObject jsonOut = new JSONObject();
      
      
      
      if("getAll".equals(action)){
		  
		  JSONObject json = new JSONObject();
	      StoreProfileService stoProSer = new StoreProfileService();
	      List<StoreProfileVO> stoList = stoProSer.getAll("normal");
		  
	      for(int i=0; i<stoList.size(); i++){
	    	  StoreProfileVO stoProVO = stoList.get(i);
	    	  json = new JSONObject();
	    	  
				json.put("name", stoProVO.getSto_name());
				json.put("storeID", stoProVO.getSto_num());
		          json.put("address", stoProVO.getArea()+stoProVO.getAddress());
		          json.put("phone", stoProVO.getMobile());
		          
		          jsonOut.put("near"+(i+1), json);
			
//		          getLocByAddr(stoProVO.getAddress());
	          
	      }
	  }else if("getAllMap".equals(action)){
  
		  String mem_acc = req.getParameter("a");
		  String mem_pwd = req.getParameter("p");
		  if(mem_acc==null || mem_pwd==null){
			  jsonOut.put("memName", "guest");
			  jsonOut.put("memID", String.valueOf((int)Math.random()*100000));
		  }else{
			  MemberProfileService memSer = new MemberProfileService();
			  MemberProfileVO memVO = memSer.findMemberByAccPsw(mem_acc, mem_pwd);
			  jsonOut.put("memName", memVO.getMem_name());
			  jsonOut.put("memID", memVO.getMem_num()); 
		  }
		  
	      StoreProfileService stoProSer = new StoreProfileService();
	      List<StoreProfileVO> stoList = stoProSer.getAll("normal");
	      JSONObject jsonStore = new JSONObject();
	      
	      
	      for(int i=0; i<stoList.size(); i++){
	    	  StoreProfileVO stoProVO = stoList.get(i);
	    	  JSONObject json = new JSONObject();
	    	  
				json.put("name", stoProVO.getSto_name());
				json.put("storeID", stoProVO.getSto_num());
		        json.put("address", stoProVO.getArea() + stoProVO.getAddress());
		        json.put("phone", stoProVO.getMobile());
		          
		          
			
		          
		          if(!storePos.containsKey(stoProVO.getSto_num())){
		        	  MyLocation stoLoction = getLocByAddr(stoProVO.getArea()+stoProVO.getAddress());
		        	  storePos.put(stoProVO.getSto_num(), stoLoction);
		          }
		          
		          json.put("lat", storePos.get(stoProVO.getSto_num()).lat);
		          json.put("lng", storePos.get(stoProVO.getSto_num()).lng);
		         
		          
		          jsonStore.put("near"+(i+1), json);
		         
 
	      }
	      jsonOut.put("store", jsonStore);
	  }else if("near".equals(req.getParameter("action"))){
		  
		  double lat = Double.valueOf(req.getParameter("lat"));
		  double lng = Double.valueOf(req.getParameter("lng"));
		  MyLocation myLoc = new MyLocation(lng, lat);
		  
		  JSONObject json = new JSONObject();
	      StoreProfileService stoProSer = new StoreProfileService();
	      List<StoreProfileVO> stoList = stoProSer.getAll("normal");
	      
	      
	      List<MyStore> myStoList = new ArrayList<>();
	      for(int i=0; i<stoList.size(); i++){
	    	  StoreProfileVO stoProVO = stoList.get(i);
	    	  
	    	  if(!storePos.containsKey(stoProVO.getSto_num())){
	        	  MyLocation stoLoction = getLocByAddr(stoProVO.getArea()+stoProVO.getAddress());
	        	  storePos.put(stoProVO.getSto_num(), stoLoction);
	          }
	    	  
	    	  MyLocation stoLoc = storePos.get(stoProVO.getSto_num());
	    	  
	    	  MyStore mySto = new MyStore(stoProVO, stoLoc);
	    	  
	    	  mySto.dis = getDis(myLoc, stoLoc);
	    	  
	    	  myStoList.add(mySto);
	      }
	    	  
	      Collections.sort(myStoList);
	      
	      for(int i=0; i<myStoList.size(); i++){
//	    	  System.out.println("dis : " + myStoList.get(i).getDis());
	    	  if(i>2 && myStoList.get(i).getDis()>2000){
	    		  break;
	    	  }
	    	  json = new JSONObject();
	    	  StoreProfileVO stoProVO = myStoList.get(i).getStoVO();
	    	  
	    	  
	    	  json.put("name", stoProVO.getSto_name());
	    	  json.put("storeID", stoProVO.getSto_num());
		      json.put("address", stoProVO.getArea() + stoProVO.getAddress());
		      json.put("phone", stoProVO.getMobile());
	    	  json.put("dis", myStoList.get(i).getDis());
	    	  jsonOut.put("near"+(i+1), json);
	    	  
	    	  
	    	  
	    	  
	      }
	              
//		          if(!storePos.containsKey(stoProVO.getSto_num())){
//		        	  MyLocation stoLoction = getLocByAddr(stoProVO.getAddress());
//		        	  storePos.put(stoProVO.getSto_num(), stoLoction);
//		          }
//		          
//		          json.put("lat", storePos.get(stoProVO.getSto_num()).lat);
//		          json.put("lng", storePos.get(stoProVO.getSto_num()).lng);
		
//	      }
		  
		  
	  }
      
      
      
      if("near".equals(req.getParameter("storeSearchType"))){
    	  setJSONDataNear(jsonOut);
      }
      else if("rank".equals(req.getParameter("storeSearchType"))){
    	  setJSONDataRank(jsonOut);
      }
    	  
      
      
      
      
      
      
//      String sql = "SELECT uname, password FROM users where uname=? and password=?";
//      Connection con = DBConnectionHandler.getConnection();
//       
//      try {
//          PreparedStatement ps = con.prepareStatement(sql);
//          ps.setString(1, params[0]);
//          ps.setString(2, params[1]);
//          ResultSet rs = ps.executeQuery();
//          if (rs.next()) {
//              json.put("info", "success");
//          } else {
//              json.put("info", "fail");
//          }
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
      
      
//      if("getAll".equals(action)){
//    	  System.out.println("getall" + jsonOut);
//	  }
      
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.getWriter().write(jsonOut.toString());
      
//      System.out.println("innnnnnn");
  }
  
  class MyStore implements Comparable{
	  StoreProfileVO stoVO;
	  MyLocation loc;
	  double dis;
	  
	  
	  
	public MyStore(StoreProfileVO stoVO, MyLocation loc) {
		super();
		this.stoVO = stoVO;
		this.loc = loc;
	}

	


	public StoreProfileVO getStoVO() {
		return stoVO;
	}




	public void setStoVO(StoreProfileVO stoVO) {
		this.stoVO = stoVO;
	}




	public MyLocation getLoc() {
		return loc;
	}




	public void setLoc(MyLocation loc) {
		this.loc = loc;
	}




	public double getDis() {
		return dis;
	}




	public void setDis(double dis) {
		this.dis = dis;
	}




	@Override
	public int compareTo(Object arg) {
		
		MyStore b = (MyStore) arg;
		
		if(this.dis < b.dis){
			return -1;
		}else if(this.dis > b.dis){
			return 1;
		}
		
		// TODO Auto-generated method stub
		return 0;
	}
	  
  }
  
  
  
  public void setJSONDataNear(JSONObject jsonOut){
	  
	  JSONObject json = new JSONObject();
      StoreProfileService stoProSer = new StoreProfileService();
      List<StoreProfileVO> stoList = stoProSer.getAll("normal");
	  
      for(int i=0; i<stoList.size(); i++){
    	  StoreProfileVO stoProVO = stoList.get(i);
    	  json = new JSONObject();
    	
			json.put("name", stoProVO.getSto_name());
			json.put("storeID", stoProVO.getSto_num());
	          json.put("address", stoProVO.getArea() + stoProVO.getAddress());
	          json.put("phone", stoProVO.getMobile());
	          
	          jsonOut.put("near"+(i+1), json);
		
          
      }
      
  }
  
  
  public void setJSONDataRank(JSONObject jsonOut){
		  
		JSONObject json = new JSONObject();
	    StoreProfileService stoProSer = new StoreProfileService();
	    List<StoreProfileVO> stoList = stoProSer.getAll("desc");
		  
	    for(int i=0; i<stoList.size(); i++){
	  	  StoreProfileVO stoProVO = stoList.get(i);
	  	  json = new JSONObject();
	  	  
			json.put("name", stoProVO.getSto_name());
			json.put("storeID", stoProVO.getSto_num());
	        json.put("address", stoProVO.getArea() + stoProVO.getAddress());
	        json.put("phone", stoProVO.getMobile());
	        
	        jsonOut.put("rank"+(i+1), json);
		
	        
	    }
	      
  }

  


	public double getDis(MyLocation loc1, MyLocation loc2) {
		return GetDistance(loc1.lat, loc1.lng, loc2.lat, loc2.lng);
	}
	
	public double GetDistance(double lat1, double lng1, double lat2, double lng2) {
		double EARTH_RADIUS = 6378137;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
		+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	private double rad(double d) {
		return d * Math.PI / 180.0;
	}	
	
	class MyLocation{
		double lng;
		double lat;
		public MyLocation(double lng, double lat) {
			this.lng = lng;
			this.lat = lat;
		}
	}


	
	MyLocation getLocByAddr(String addr)  {
		
		String type = "xml";
		String address = "";
		try {
			address = java.net.URLEncoder.encode(addr,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		String key = "AIzaSyCwQseEGzijMIUnPtkvcCUZe2h049AyHuM";
		
		String url = String.format("https://maps.googleapis.com/maps/api/geocode/%s?address=%s&key=%s&sensor=false&language=zh-TW", type, address, key);
		
//		URLConnection HTTPsConn = null;
		
//		HTTPsConn = (URLConnection) myURL.openConnection();
		
		
		
		
		Double lat=null;
		Double lng=null;
		
		try {
			URL myURL = new URL(url);
			// 以URL物件建立URLConnection物件
			URLConnection urlConn = myURL.openConnection();
			// 以URLConnection物件取得輸入資料流
			InputStream ins = urlConn.getInputStream();

			// 建立URL資料流
			BufferedReader br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String data;
			
			
//			List<Double> latList = new ArrayList<>();
//			List<Double> lngList = new ArrayList<>();
//			double lat;
//			double lng;
			
			while ((data = br.readLine()) != null) {
				//System.out.println(data);
				if (data.contains("<lat>")) {
					lat = Double.valueOf(data.substring(data.indexOf("<lat>") + 5, data.indexOf("</lat>")));
//					latList.add(lat);
					
				}
				if(data.contains("<lng>")){
					lng = Double.valueOf(data.substring(data.indexOf("<lng>") + 5, data.indexOf("</lng>")));
//					System.out.println();
				}
				
				if(lat!=null && lng!=null){
					break;
				}
			}

			br.close();
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		MyLocation loc = new MyLocation(lng,lat);
		
		return loc;
		
		
		
		
		
		
		
//		
//		
//		
//		
//		
//		StringBuilder data = new StringBuilder("");
//		if(HTTPsConn!=null)
//		{
//			InputStreamReader insr = new InputStreamReader(	HTTPsConn.getInputStream(), "UTF-8");
//			BufferedReader br = new BufferedReader(insr);
//			
//			String temp=null;
//			while((temp =  br.readLine()) != null) {
//				data.append(temp);
//			}
//			
//			System.out.println("string from google: " + data);
//			
//			
//
//		}else {
//			System.out.println("HTTPsConn  nullllllllll");
//		}
//		
//		System.out.println(url);
//		
//		
//		
////		JSONObject result = new JSONObject(data.toString());
////		JSONArray arr = result.getJSONArray("results");
////		JSONObject temp = arr.getJSONObject(0);
////		JSONObject geo = temp.getJSONObject("geometry");
////		JSONObject location = geo.getJSONObject("location");
////		double lng = location.getDouble("lng");
////		double lat = location.getDouble("lat");
////		MyLocation loc = new MyLocation(lng,lat);
//		
////		System.out.println(location);
////		System.out.println("lng: "+lng);
////		System.out.println("lat: "+lat);
	
//		return loc;
		
		
		
		
	}


}
