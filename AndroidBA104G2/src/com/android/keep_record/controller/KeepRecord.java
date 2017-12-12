package com.android.keep_record.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.extra.model.ExtraService;
import com.extra_list.model.ExtraListService;
import com.extra_list.model.ExtraListVO;
import com.ice_list.model.IceListService;
import com.keep_record.model.KeepRecordService;
import com.keep_record.model.KeepRecordVO;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.order_detail.model.OrderDetailService;
import com.order_detail.model.OrderDetailVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;
import com.sweetness.model.SweetnessService;

public class KeepRecord extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

      
      	String acc = req.getParameter("a");
      	String pwd = req.getParameter("p");
      	String type = req.getParameter("t");
      
      	String action = req.getParameter("action");
//      	System.out.println(acc+pwd+type);
      	JSONObject jsonOut = new JSONObject();
      	KeepRecordService keepRecSer = new KeepRecordService();
      	MemberProfileService memProSer = new MemberProfileService();
      	
      	if("memGetList".equals(action)){
      		
          	MemberProfileVO memProVO = memProSer.findMemberByAccPsw(acc, pwd);
          	
       
          	
          	List<KeepRecordVO> keepList = keepRecSer.findListByMemNum(memProVO.getMem_num());
          	
          	for(int i=0; i<keepList.size(); i++){
          		JSONObject json = new JSONObject();
                KeepRecordVO keepRecVO = keepList.get(i);
                
                StoreProfileService stoProSer = new StoreProfileService();
                StoreProfileVO stoProVO = stoProSer.findStoreByID(keepRecVO.getSto_num());
                json.put("shop", stoProVO.getSto_name());
                
                
                ProductService proSer = new ProductService();
                ProductVO proVO = proSer.getOneByComNum(keepRecVO.getCom_num());
                json.put("productID", keepRecVO.getCom_num());
                json.put("product",  proVO.getCom_name());
                
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
                String time = null;
                if("已領取".equals(keepRecVO.getKeep_status())){
                	time = sdFormat.format(keepRecVO.getRec_time());
                }else{
                	time = sdFormat.format(keepRecVO.getKeep_time());
                }
                
                		
                json.put("time", time);
                
                json.put("status", keepRecVO.getKeep_status());
                json.put("storageID", keepRecVO.getKeep_num());
                json.put("odID", keepRecVO.getOrdet_num());
                
                
                jsonOut.put("storage"+(i+1), json);
          	}
          	
      	}else if("passKeepQR".equals(action)){
      		String storeID = req.getParameter("storeID");
      		String targetID = req.getParameter("targetID");
      		KeepRecordVO keepVO = keepRecSer.getOneByOdID(targetID);
      		if(keepVO != null){
      			if(keepVO.getSto_num().equals(storeID)){
      				keepRecSer.updateStatusByOdnum("已領取", targetID, true);
      				
      				jsonOut.put("info",keepVO.getMem_num());
      			}else{
      				jsonOut.put("info","掃到別家的寄杯囉!");
      			}
      		}else{
      			jsonOut.put("info","查無此寄杯");
      		}
      	}else if("passKeep".equals(action)){
      		String way = req.getParameter("way");
      		String odID = req.getParameter("odID");
      		if("pass".equals(way)){
      			keepRecSer.updateStatusByOdnum("審核成功", odID,false);
      		}else if("unpass".equals(way)){
      			keepRecSer.updateStatusByOdnum("未領取", odID,false);
      		}else if("submit".equals(way)){
      			keepRecSer.updateStatusByOdnum("已領取", odID, true);

      		}
      		
      		jsonOut.put("info", "success");
      		
      	}else if("storeGetList".equals(action)){//店家抓取狀態是申請中及審核通過的寄杯資訊
      		StoreProfileService stoSer = new StoreProfileService();
      		StoreProfileVO stoVO = stoSer.findStoreByAccPsw(acc, pwd);
      		
      		List<KeepRecordVO> keepList = keepRecSer.findAvaListBySto(stoVO.getSto_num());
      		for(int i=0; i<keepList.size(); i++){
          		JSONObject json = new JSONObject();
                KeepRecordVO keepRecVO = keepList.get(i);
                MemberProfileVO memVO = memProSer.findMemberByID(keepRecVO.getMem_num());

                json.put("name", memVO.getMem_name());
                json.put("memID", memVO.getMem_num());
               
                
                ProductService proSer = new ProductService();
                ProductVO proVO = proSer.getOneByComNum(keepRecVO.getCom_num());
                json.put("productID", keepRecVO.getCom_num());
                json.put("product",  proVO.getCom_name());
                
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
                String time = null;
                if("已領取".equals(keepRecVO.getKeep_status())){
                	time = sdFormat.format(keepRecVO.getRec_time());
                }else{
                	time = sdFormat.format(keepRecVO.getKeep_time());
                }
                
                		
                json.put("time", time);
                
                json.put("status", keepRecVO.getKeep_status());
                json.put("storageID", keepRecVO.getKeep_num());
                json.put("odID", keepRecVO.getOrdet_num());
                
                
                jsonOut.put("storage"+(i+1), json);
          	}
      		
      	}else if("applyKeep".equals(action)){
      		
      		String odID = req.getParameter("odID");
      		keepRecSer.updateStatusByOdnum("申請中", odID,false);
      		
      		jsonOut.put("info", "success");
      		
      	}else if("getDetail".equals(action)){
      		String odID = req.getParameter("odID");
      		
      		OrderDetailService odSer = new OrderDetailService();
//      		ExtraListService exSer = new ExtraListService();
      		
      		
//      		JSONObject jsobj = new JSONObject();
  			OrderDetailVO odVO = odSer.getOneByODID(odID);
  			
  			jsonOut.put("odID", odVO.getOrdet_num());
  			jsonOut.put("orderID", odVO.getOr_num());
  			jsonOut.put("productID", odVO.getCom_num());
  			
  			ProductService proSer = new ProductService();
  			ProductVO proVO = proSer.getOneByComNum(odVO.getCom_num());
  			jsonOut.put("productName", proVO.getCom_name());
  			
  			jsonOut.put("size", odVO.getCom_size());
  			
  			SweetnessService sweetSer = new SweetnessService();
  			String sweet = sweetSer.getNameByID(odVO.getSweet_num());
  			jsonOut.put("sweet", sweet);
  			
  			IceListService iceSer = new IceListService();
  			String ice = iceSer.getNameByID(odVO.getIce_num());
  			jsonOut.put("ice", ice);
  			
  			jsonOut.put("price", odVO.getOd_price());
  			
  			KeepRecordService keepSer = new KeepRecordService();
  			KeepRecordVO keepVO = keepSer.getOneByOdID(odVO.getOrdet_num());
  			if(keepVO==null){
  				jsonOut.put("isKeep", false);
  			}else{
  				jsonOut.put("isKeep", true);
  			}
  			
  			ExtraListService exListSer = new ExtraListService();
  			List<ExtraListVO> exList = exListSer.getListByOdNum(odVO.getOrdet_num());
  			JSONObject jsex = new JSONObject();
  			ExtraService exSer = new ExtraService();
  			for(int j=0; j<exList.size(); j++){
  				ExtraListVO exListVO = exList.get(j);
  				String exName = exSer.getOneByExtID(exListVO.getExt_num()).getExt_name();
  				jsex.put("ex"+(j+1), exName);
  			}
  			jsonOut.put("extra", jsex);
  			
  			
//  			jsonOut.put("od"+(i+1),jsobj);
  			
  			
      	}
      	
      
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.getWriter().write(jsonOut.toString());
      
      
  }
  
  
  public void setJSONData(JSONObject jsonOut){
	  
	  JSONObject json = new JSONObject();
      
      json.put("shop", "清新福全");
      json.put("product", "奶綠");
      json.put("time", "寄杯時間:2017/10/14");
      json.put("status", "未領取");
      json.put("storageID", "st166122");
      
      jsonOut.put("storage1", json);
      
      
      json = new JSONObject();
      json.put("shop", "清新福全");
      json.put("product", "烏龍綠");
      json.put("time", "寄杯時間:2017/10/12");
      json.put("status", "未領取");
      json.put("storageID", "st166111");
      
      jsonOut.put("storage2", json);
      
      
      json = new JSONObject();
      json.put("shop", "清新福全");
      json.put("product", "紅茶拿鐵");
      json.put("time", "領取時間:2017/10/14");
      json.put("status", "已領取");
      json.put("storageID", "st112211");
      
      jsonOut.put("storage3", json);
      
      
      
      json = new JSONObject();
      json.put("shop", "清新福全");
      json.put("product", "紅茶拿鐵");
      json.put("time", "寄杯時間:2017/10/16");
      json.put("status", "未領取");
      json.put("storageID", "st111111");
      
      
      jsonOut.put("storage4", json);
  }
  
  
}
