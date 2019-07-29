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

	/*���충����ϸ��Ϣҵ������*/
	private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

	/*Ĭ�Ϲ��캯��*/
	public OrderDetailServlet() {
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
			/*��ȡ��ѯ������ϸ��Ϣ�Ĳ�����Ϣ*/
			String orderObj = "";
			if (request.getParameter("orderObj") != null)
				orderObj = request.getParameter("orderObj");
			String productObj = "";
			if (request.getParameter("productObj") != null)
				productObj = request.getParameter("productObj");

			/*����ҵ���߼���ִ�ж�����ϸ��Ϣ��ѯ*/
			List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetail(orderObj,productObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӷ�����ϸ��Ϣ����ȡ������ϸ��Ϣ�������������浽�½��Ķ�����ϸ��Ϣ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = orderDetailDAO.AddOrderDetail(orderDetail);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������ϸ��Ϣ����ȡ������ϸ��Ϣ�ļ�¼���*/
			int detailId = Integer.parseInt(request.getParameter("detailId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = orderDetailDAO.DeleteOrderDetail(detailId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¶�����ϸ��Ϣ֮ǰ�ȸ���detailId��ѯĳ��������ϸ��Ϣ*/
			int detailId = Integer.parseInt(request.getParameter("detailId"));
			OrderDetail orderDetail = orderDetailDAO.GetOrderDetail(detailId);

			// �ͻ��˲�ѯ�Ķ�����ϸ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¶�����ϸ��Ϣ����ȡ������ϸ��Ϣ�������������浽�½��Ķ�����ϸ��Ϣ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = orderDetailDAO.UpdateOrderDetail(orderDetail);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
