package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.OrderInfoDAO;
import com.mobileserver.domain.OrderInfo;

import org.json.JSONStringer;

public class OrderInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���충����Ϣҵ������*/
	private OrderInfoDAO orderInfoDAO = new OrderInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public OrderInfoServlet() {
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
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
			String orderNo = request.getParameter("orderNo");
			orderNo = orderNo == null ? "" : new String(request.getParameter(
					"orderNo").getBytes("iso-8859-1"), "UTF-8");
			String memberObj = "";
			if (request.getParameter("memberObj") != null)
				memberObj = request.getParameter("memberObj");
			String orderTime = request.getParameter("orderTime");
			orderTime = orderTime == null ? "" : new String(request.getParameter(
					"orderTime").getBytes("iso-8859-1"), "UTF-8");
			int orderStateObj = 0;
			if (request.getParameter("orderStateObj") != null)
				orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));
			String realName = request.getParameter("realName");
			realName = realName == null ? "" : new String(request.getParameter(
					"realName").getBytes("iso-8859-1"), "UTF-8");
			String telphone = request.getParameter("telphone");
			telphone = telphone == null ? "" : new String(request.getParameter(
					"telphone").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�ж�����Ϣ��ѯ*/
			List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfo(orderNo,memberObj,orderTime,orderStateObj,realName,telphone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<OrderInfos>").append("\r\n");
			for (int i = 0; i < orderInfoList.size(); i++) {
				sb.append("	<OrderInfo>").append("\r\n")
				.append("		<orderNo>")
				.append(orderInfoList.get(i).getOrderNo())
				.append("</orderNo>").append("\r\n")
				.append("		<memberObj>")
				.append(orderInfoList.get(i).getMemberObj())
				.append("</memberObj>").append("\r\n")
				.append("		<orderTime>")
				.append(orderInfoList.get(i).getOrderTime())
				.append("</orderTime>").append("\r\n")
				.append("		<totalMoney>")
				.append(orderInfoList.get(i).getTotalMoney())
				.append("</totalMoney>").append("\r\n")
				.append("		<orderStateObj>")
				.append(orderInfoList.get(i).getOrderStateObj())
				.append("</orderStateObj>").append("\r\n")
				.append("		<buyWay>")
				.append(orderInfoList.get(i).getBuyWay())
				.append("</buyWay>").append("\r\n")
				.append("		<realName>")
				.append(orderInfoList.get(i).getRealName())
				.append("</realName>").append("\r\n")
				.append("		<telphone>")
				.append(orderInfoList.get(i).getTelphone())
				.append("</telphone>").append("\r\n")
				.append("		<postcode>")
				.append(orderInfoList.get(i).getPostcode())
				.append("</postcode>").append("\r\n")
				.append("		<address>")
				.append(orderInfoList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<memo>")
				.append(orderInfoList.get(i).getMemo())
				.append("</memo>").append("\r\n")
				.append("	</OrderInfo>").append("\r\n");
			}
			sb.append("</OrderInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(OrderInfo orderInfo: orderInfoList) {
				  stringer.object();
			  stringer.key("orderNo").value(orderInfo.getOrderNo());
			  stringer.key("memberObj").value(orderInfo.getMemberObj());
			  stringer.key("orderTime").value(orderInfo.getOrderTime());
			  stringer.key("totalMoney").value(orderInfo.getTotalMoney());
			  stringer.key("orderStateObj").value(orderInfo.getOrderStateObj());
			  stringer.key("buyWay").value(orderInfo.getBuyWay());
			  stringer.key("realName").value(orderInfo.getRealName());
			  stringer.key("telphone").value(orderInfo.getTelphone());
			  stringer.key("postcode").value(orderInfo.getPostcode());
			  stringer.key("address").value(orderInfo.getAddress());
			  stringer.key("memo").value(orderInfo.getMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӷ�����Ϣ����ȡ������Ϣ�������������浽�½��Ķ�����Ϣ���� */ 
			OrderInfo orderInfo = new OrderInfo();
			String orderNo = new String(request.getParameter("orderNo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderNo(orderNo);
			String memberObj = new String(request.getParameter("memberObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setMemberObj(memberObj);
			String orderTime = new String(request.getParameter("orderTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderTime(orderTime);
			float totalMoney = Float.parseFloat(request.getParameter("totalMoney"));
			orderInfo.setTotalMoney(totalMoney);
			int orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));
			orderInfo.setOrderStateObj(orderStateObj);
			String buyWay = new String(request.getParameter("buyWay").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setBuyWay(buyWay);
			String realName = new String(request.getParameter("realName").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setRealName(realName);
			String telphone = new String(request.getParameter("telphone").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setTelphone(telphone);
			String postcode = new String(request.getParameter("postcode").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setPostcode(postcode);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setAddress(address);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setMemo(memo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = orderInfoDAO.AddOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�Ķ������*/
			String orderNo = new String(request.getParameter("orderNo").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = orderInfoDAO.DeleteOrderInfo(orderNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¶�����Ϣ֮ǰ�ȸ���orderNo��ѯĳ��������Ϣ*/
			String orderNo = new String(request.getParameter("orderNo").getBytes("iso-8859-1"), "UTF-8");
			OrderInfo orderInfo = orderInfoDAO.GetOrderInfo(orderNo);

			// �ͻ��˲�ѯ�Ķ�����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderNo").value(orderInfo.getOrderNo());
			  stringer.key("memberObj").value(orderInfo.getMemberObj());
			  stringer.key("orderTime").value(orderInfo.getOrderTime());
			  stringer.key("totalMoney").value(orderInfo.getTotalMoney());
			  stringer.key("orderStateObj").value(orderInfo.getOrderStateObj());
			  stringer.key("buyWay").value(orderInfo.getBuyWay());
			  stringer.key("realName").value(orderInfo.getRealName());
			  stringer.key("telphone").value(orderInfo.getTelphone());
			  stringer.key("postcode").value(orderInfo.getPostcode());
			  stringer.key("address").value(orderInfo.getAddress());
			  stringer.key("memo").value(orderInfo.getMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¶�����Ϣ����ȡ������Ϣ�������������浽�½��Ķ�����Ϣ���� */ 
			OrderInfo orderInfo = new OrderInfo();
			String orderNo = new String(request.getParameter("orderNo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderNo(orderNo);
			String memberObj = new String(request.getParameter("memberObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setMemberObj(memberObj);
			String orderTime = new String(request.getParameter("orderTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderTime(orderTime);
			float totalMoney = Float.parseFloat(request.getParameter("totalMoney"));
			orderInfo.setTotalMoney(totalMoney);
			int orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));
			orderInfo.setOrderStateObj(orderStateObj);
			String buyWay = new String(request.getParameter("buyWay").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setBuyWay(buyWay);
			String realName = new String(request.getParameter("realName").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setRealName(realName);
			String telphone = new String(request.getParameter("telphone").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setTelphone(telphone);
			String postcode = new String(request.getParameter("postcode").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setPostcode(postcode);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setAddress(address);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setMemo(memo);

			/* ����ҵ���ִ�и��²��� */
			String result = orderInfoDAO.UpdateOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
