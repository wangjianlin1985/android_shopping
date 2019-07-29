package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.OrderDetailDAO;
import com.mobileserver.domain.OrderDetail;

import org.json.JSONStringer;

public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造订单明细信息业务层对象*/
	private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

	/*默认构造函数*/
	public OrderDetailServlet() {
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
			/*获取查询订单明细信息的参数信息*/
			String orderObj = "";
			if (request.getParameter("orderObj") != null)
				orderObj = request.getParameter("orderObj");
			String productObj = "";
			if (request.getParameter("productObj") != null)
				productObj = request.getParameter("productObj");

			/*调用业务逻辑层执行订单明细信息查询*/
			List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetail(orderObj,productObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<OrderDetails>").append("\r\n");
			for (int i = 0; i < orderDetailList.size(); i++) {
				sb.append("	<OrderDetail>").append("\r\n")
				.append("		<detailId>")
				.append(orderDetailList.get(i).getDetailId())
				.append("</detailId>").append("\r\n")
				.append("		<orderObj>")
				.append(orderDetailList.get(i).getOrderObj())
				.append("</orderObj>").append("\r\n")
				.append("		<productObj>")
				.append(orderDetailList.get(i).getProductObj())
				.append("</productObj>").append("\r\n")
				.append("		<price>")
				.append(orderDetailList.get(i).getPrice())
				.append("</price>").append("\r\n")
				.append("		<count>")
				.append(orderDetailList.get(i).getCount())
				.append("</count>").append("\r\n")
				.append("	</OrderDetail>").append("\r\n");
			}
			sb.append("</OrderDetails>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(OrderDetail orderDetail: orderDetailList) {
				  stringer.object();
			  stringer.key("detailId").value(orderDetail.getDetailId());
			  stringer.key("orderObj").value(orderDetail.getOrderObj());
			  stringer.key("productObj").value(orderDetail.getProductObj());
			  stringer.key("price").value(orderDetail.getPrice());
			  stringer.key("count").value(orderDetail.getCount());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加订单明细信息：获取订单明细信息参数，参数保存到新建的订单明细信息对象 */ 
			OrderDetail orderDetail = new OrderDetail();
			int detailId = Integer.parseInt(request.getParameter("detailId"));
			orderDetail.setDetailId(detailId);
			String orderObj = new String(request.getParameter("orderObj").getBytes("iso-8859-1"), "UTF-8");
			orderDetail.setOrderObj(orderObj);
			String productObj = new String(request.getParameter("productObj").getBytes("iso-8859-1"), "UTF-8");
			orderDetail.setProductObj(productObj);
			float price = Float.parseFloat(request.getParameter("price"));
			orderDetail.setPrice(price);
			int count = Integer.parseInt(request.getParameter("count"));
			orderDetail.setCount(count);

			/* 调用业务层执行添加操作 */
			String result = orderDetailDAO.AddOrderDetail(orderDetail);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除订单明细信息：获取订单明细信息的记录编号*/
			int detailId = Integer.parseInt(request.getParameter("detailId"));
			/*调用业务逻辑层执行删除操作*/
			String result = orderDetailDAO.DeleteOrderDetail(detailId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新订单明细信息之前先根据detailId查询某个订单明细信息*/
			int detailId = Integer.parseInt(request.getParameter("detailId"));
			OrderDetail orderDetail = orderDetailDAO.GetOrderDetail(detailId);

			// 客户端查询的订单明细信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("detailId").value(orderDetail.getDetailId());
			  stringer.key("orderObj").value(orderDetail.getOrderObj());
			  stringer.key("productObj").value(orderDetail.getProductObj());
			  stringer.key("price").value(orderDetail.getPrice());
			  stringer.key("count").value(orderDetail.getCount());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新订单明细信息：获取订单明细信息参数，参数保存到新建的订单明细信息对象 */ 
			OrderDetail orderDetail = new OrderDetail();
			int detailId = Integer.parseInt(request.getParameter("detailId"));
			orderDetail.setDetailId(detailId);
			String orderObj = new String(request.getParameter("orderObj").getBytes("iso-8859-1"), "UTF-8");
			orderDetail.setOrderObj(orderObj);
			String productObj = new String(request.getParameter("productObj").getBytes("iso-8859-1"), "UTF-8");
			orderDetail.setProductObj(productObj);
			float price = Float.parseFloat(request.getParameter("price"));
			orderDetail.setPrice(price);
			int count = Integer.parseInt(request.getParameter("count"));
			orderDetail.setCount(count);

			/* 调用业务层执行更新操作 */
			String result = orderDetailDAO.UpdateOrderDetail(orderDetail);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
