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

	/*构造商品信息业务层对象*/
	private ProductInfoDAO productInfoDAO = new ProductInfoDAO();

	/*默认构造函数*/
	public ProductInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询商品信息的参数信息*/
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

			/*调用业务逻辑层执行商品信息查询*/
			List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfo(productNo,productClassObj,productName,recommendFlag,onlineDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加商品信息：获取商品信息参数，参数保存到新建的商品信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = productInfoDAO.AddProductInfo(productInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除商品信息：获取商品信息的商品编号*/
			String productNo = new String(request.getParameter("productNo").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = productInfoDAO.DeleteProductInfo(productNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新商品信息之前先根据productNo查询某个商品信息*/
			String productNo = new String(request.getParameter("productNo").getBytes("iso-8859-1"), "UTF-8");
			ProductInfo productInfo = productInfoDAO.GetProductInfo(productNo);

			// 客户端查询的商品信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新商品信息：获取商品信息参数，参数保存到新建的商品信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = productInfoDAO.UpdateProductInfo(productInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
