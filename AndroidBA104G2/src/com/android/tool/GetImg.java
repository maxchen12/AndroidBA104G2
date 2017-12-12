package com.android.tool;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.friend_list.model.FriendListService;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.shop_ad.model.ShopAdService;
import com.shop_ad.model.ShopAdVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;


public class GetImg extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetImg() {
        super();
        
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		

			
			String type = req.getParameter("getType");
				
			if("ADCheck".equals(type)){
				JSONObject json = new JSONObject();
				ShopAdService shopAdser = new ShopAdService();
				int number = shopAdser.getAvaAdNumber();
				json.put("number", number);
				res.setContentType("application/json");
			    res.setCharacterEncoding("UTF-8");
			    res.getWriter().write(json.toString());
			}
			else{
				
				res.setContentType("image/*");
				ServletOutputStream out = res.getOutputStream();
				InputStream in = null;
				
				if("member".equals(type)){
					
					
					String memAcc = req.getParameter("a");
					String memPwd = req.getParameter("p");
					MemberProfileService memProSer = new MemberProfileService();
					MemberProfileVO memProVO = memProSer.findMemberByAccPsw(memAcc, memPwd);
					
					try{
						in = new ByteArrayInputStream(memProVO.getMem_img());
					}catch(Exception e){
						in = new FileInputStream(getServletContext().getRealPath("/img/tomcat.gif"));
					}
					
				}
				else if("memberID".equals(type)){
					String memID = req.getParameter("memberID");
					String size = req.getParameter("size");
					int sizeNumber = 250;
					if(size!=null){
						sizeNumber = Integer.valueOf(size);
					}
					
					
					MemberProfileService memProSer = new MemberProfileService();
					MemberProfileVO memProVO = memProSer.findMemberByID(memID);
					try{
						in = new ByteArrayInputStream(ImageUtil.shrink(memProVO.getMem_img(), sizeNumber));
					}catch(Exception e){
						in = new FileInputStream(getServletContext().getRealPath("/img/tomcat.gif"));
					}
				}
				else if("AD".equals(type)){
					
					int count = Integer.valueOf(req.getParameter("counter"));
					ShopAdService shopAdser = new ShopAdService();
					ShopAdVO shopAdVO = shopAdser.getOneByCounter(count);
					
					
					try{
						in = new ByteArrayInputStream(shopAdVO.getSa_img());
					}catch(Exception e){
						in = new FileInputStream(getServletContext().getRealPath("/img/tomcat.gif"));
					}
				}
				else if("store".equals(type)){
					
					String sto_num = req.getParameter("id");
					StoreProfileService stoProSer = new StoreProfileService();
					StoreProfileVO stoProVO = stoProSer.findStoreByID(sto_num);
					try{
						in = new ByteArrayInputStream(stoProVO.getSto_logo());
					}catch(Exception e){
						in = new FileInputStream(getServletContext().getRealPath("/img/tomcat.gif"));
					}
				}else if("product".equals(type)){
					
					String proID = req.getParameter("productID");
					ProductService proSer = new ProductService();
					ProductVO proVO = proSer.getOneByComNum(proID);
					try{
						in = new ByteArrayInputStream(proVO.getImg());
					}catch(Exception e){
						in = new FileInputStream(getServletContext().getRealPath("/img/tomcat.gif"));
					}
				}
				
				
				if(in != null){
					
					try{
						
						byte[] buffer = new byte[in.available()];
						int len = 0;
						
						try{
							while((len = in.read(buffer))!=-1){
								out.write(buffer, 0, len);
							}
								out.close();
								return;
							
							}catch(IOException e){
								e.printStackTrace();
							}
						}catch (Exception e) {
						
							byte[] propic = new byte[in.available()];
							in.read(propic);
							out.write(propic);
							in.close();

					}
				}
				
				
			} 
			
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
