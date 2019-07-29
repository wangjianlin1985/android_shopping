package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ProductInfoDAO;
import com.mobileserver.domain.ProductInfo;

import org.json.JSONStringer;

public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������Ʒ��Ϣҵ������*/
	private ProductInfoDAO productInfoDAO = new ProductInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public ProductInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ��Ʒ��Ϣ�Ĳ�����Ϣ*/
			String productNo = request.getParameter("productNo");
			productNo = productNo == null ? "" : new String(request.getParameter(
					"productNo").getBytes("iso-8859-1"), "UTF-8");
			int productClassObj = 0;
			if (request.getParameter("productClassObj") != null)
				productClassObj = Integer.parseInt(request.getParameter("productClassObj"));
			String productName = request.getParameter("productName");
			productName = productName == null ? "" : new String(request.getParameter(
					"productName").getBytes("iso-8859-1"), "UTF-8");
			String recommendFlag = "";
			if (request.getParameter("recommendFlag") != null)
				recommendFlag = request.getParameter("recommendFlag");
			Timestamp onlineDate = null;
			if (request.getParameter("onlineDate") != null)
				onlineDate = Timestamp.valueOf(request.getParameter("onlineDate"));

			/*����ҵ���߼���ִ����Ʒ��Ϣ��ѯ*/
			List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfo(productNo,productClassObj,productName,recommendFlag,onlineDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ProductInfos>").append("\r\n");
			for (int i = 0; i < productInfoList.size(); i++) {
				sb.append("	<ProductInfo>").append("\r\n")
				.append("		<productNo>")
				.append(productInfoList.get(i).getProductNo())
				.append("</productNo>").append("\r\n")
				.append("		<productClassObj>")
				.append(productInfoList.get(i).getProductClassObj())
				.append("</productClassObj>").append("\r\n")
				.append("		<productName>")
				.append(productInfoList.get(i).getProductName())
				.append("</productName>").append("\r\n")
				.append("		<productPhoto>")
				.append(productInfoList.get(i).getProductPhoto())
				.append("</productPhoto>").append("\r\n")
				.append("		<productPrice>")
				.append(productInfoList.get(i).getProductPrice())
				.append("</productPrice>").append("\r\n")
				.append("		<productCount>")
				.append(productInfoList.get(i).getProductCount())
				.append("</productCount>").append("\r\n")
				.append("		<recommendFlag>")
				.append(productInfoList.get(i).getRecommendFlag())
				.append("</recommendFlag>").append("\r\n")
				.append("		<hotNum>")
				.append(productInfoList.get(i).getHotNum())
				.append("</hotNum>").append("\r\n")
				.append("		<onlineDate>")
				.append(productInfoList.get(i).getOnlineDate())
				.append("</onlineDate>").append("\r\n")
				.append("	</ProductInfo>").append("\r\n");
			}
			sb.append("</ProductInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ProductInfo productInfo: productInfoList) {
				  stringer.object();
			  stringer.key("productNo").value(productInfo.getProductNo());
			  stringer.key("productClassObj").value(productInfo.getProductClassObj());
			  stringer.key("productName").value(productInfo.getProductName());
			  stringer.key("productPhoto").value(productInfo.getProductPhoto());
			  stringer.key("productPrice").value(productInfo.getProductPrice());
			  stringer.key("productCount").value(productInfo.getProductCount());
			  stringer.key("recommendFlag").value(productInfo.getRecommendFlag());
			  stringer.key("hotNum").value(productInfo.getHotNum());
			  stringer.key("onlineDate").value(productInfo.getOnlineDate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����Ʒ��Ϣ����ȡ��Ʒ��Ϣ�������������浽�½�����Ʒ��Ϣ���� */ 
			ProductInfo productInfo = new ProductInfo();
			String productNo = new String(request.getParameter("productNo").getBytes("iso-8859-1"), "UTF-8");
			productInfo.setProductNo(productNo);
			int productClassObj = Integer.parseInt(request.getParameter("productClassObj"));
			productInfo.setProductClassObj(productClassObj);
			String productName = new String(request.getParameter("productName").getBytes("iso-8859-1"), "UTF-8");
			productInfo.setProductName(productName);
			String productPhoto = new String(request.getParameter("productPhoto").getBytes("iso-8859-1"), "UTF-8");
			productInfo.setProductPhoto(productPhoto);
			float productPrice = Float.parseFloat(request.getParameter("productPrice"));
			productInfo.setProductPrice(productPrice);
			int productCount = Integer.parseInt(request.getParameter("productCount"));
			productInfo.setProductCount(productCount);
			String recommendFlag = new String(request.getParameter("recommendFlag").getBytes("iso-8859-1"), "UTF-8");
			productInfo.setRecommendFlag(recommendFlag);
			int hotNum = Integer.parseInt(request.getParameter("hotNum"));
			productInfo.setHotNum(hotNum);
			Timestamp onlineDate = Timestamp.valueOf(request.getParameter("onlineDate"));
			productInfo.setOnlineDate(onlineDate);

			/* ����ҵ���ִ����Ӳ��� */
			String result = productInfoDAO.AddProductInfo(productInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ʒ��Ϣ����ȡ��Ʒ��Ϣ����Ʒ���*/
			String productNo = new String(request.getParameter("productNo").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = productInfoDAO.DeleteProductInfo(productNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������Ʒ��Ϣ֮ǰ�ȸ���productNo��ѯĳ����Ʒ��Ϣ*/
			String productNo = new String(request.getParameter("productNo").getBytes("iso-8859-1"), "UTF-8");
			ProductInfo productInfo = productInfoDAO.GetProductInfo(productNo);

			// �ͻ��˲�ѯ����Ʒ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("productNo").value(productInfo.getProductNo());
			  stringer.key("productClassObj").value(productInfo.getProductClassObj());
			  stringer.key("productName").value(productInfo.getProductName());
			  stringer.key("productPhoto").value(productInfo.getProductPhoto());
			  stringer.key("productPrice").value(productInfo.getProductPrice());
			  stringer.key("productCount").value(productInfo.getProductCount());
			  stringer.key("recommendFlag").value(productInfo.getRecommendFlag());
			  stringer.key("hotNum").value(productInfo.getHotNum());
			  stringer.key("onlineDate").value(productInfo.getOnlineDate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������Ʒ��Ϣ����ȡ��Ʒ��Ϣ�������������浽�½�����Ʒ��Ϣ���� */ 
			ProductInfo productInfo = new ProductInfo();
			String productNo = new String(request.getParameter("productNo").getBytes("iso-8859-1"), "UTF-8");
			productInfo.setProductNo(productNo);
			int productClassObj = Integer.parseInt(request.getParameter("productClassObj"));
			productInfo.setProductClassObj(productClassObj);
			String productName = new String(request.getParameter("productName").getBytes("iso-8859-1"), "UTF-8");
			productInfo.setProductName(productName);
			String productPhoto = new String(request.getParameter("productPhoto").getBytes("iso-8859-1"), "UTF-8");
			productInfo.setProductPhoto(productPhoto);
			float productPrice = Float.parseFloat(request.getParameter("productPrice"));
			productInfo.setProductPrice(productPrice);
			int productCount = Integer.parseInt(request.getParameter("productCount"));
			productInfo.setProductCount(productCount);
			String recommendFlag = new String(request.getParameter("recommendFlag").getBytes("iso-8859-1"), "UTF-8");
			productInfo.setRecommendFlag(recommendFlag);
			int hotNum = Integer.parseInt(request.getParameter("hotNum"));
			productInfo.setHotNum(hotNum);
			Timestamp onlineDate = Timestamp.valueOf(request.getParameter("onlineDate"));
			productInfo.setOnlineDate(onlineDate);

			/* ����ҵ���ִ�и��²��� */
			String result = productInfoDAO.UpdateProductInfo(productInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
