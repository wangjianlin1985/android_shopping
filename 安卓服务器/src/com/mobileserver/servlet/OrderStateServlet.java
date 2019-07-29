package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.OrderStateDAO;
import com.mobileserver.domain.OrderState;

import org.json.JSONStringer;

public class OrderStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���충��״̬��Ϣҵ������*/
	private OrderStateDAO orderStateDAO = new OrderStateDAO();

	/*Ĭ�Ϲ��캯��*/
	public OrderStateServlet() {
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
			/*��ȡ��ѯ����״̬��Ϣ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�ж���״̬��Ϣ��ѯ*/
			List<OrderState> orderStateList = orderStateDAO.QueryOrderState();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<OrderStates>").append("\r\n");
			for (int i = 0; i < orderStateList.size(); i++) {
				sb.append("	<OrderState>").append("\r\n")
				.append("		<stateId>")
				.append(orderStateList.get(i).getStateId())
				.append("</stateId>").append("\r\n")
				.append("		<stateName>")
				.append(orderStateList.get(i).getStateName())
				.append("</stateName>").append("\r\n")
				.append("	</OrderState>").append("\r\n");
			}
			sb.append("</OrderStates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(OrderState orderState: orderStateList) {
				  stringer.object();
			  stringer.key("stateId").value(orderState.getStateId());
			  stringer.key("stateName").value(orderState.getStateName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӷ���״̬��Ϣ����ȡ����״̬��Ϣ�������������浽�½��Ķ���״̬��Ϣ���� */ 
			OrderState orderState = new OrderState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			orderState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			orderState.setStateName(stateName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = orderStateDAO.AddOrderState(orderState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������״̬��Ϣ����ȡ����״̬��Ϣ��״̬���*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = orderStateDAO.DeleteOrderState(stateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¶���״̬��Ϣ֮ǰ�ȸ���stateId��ѯĳ������״̬��Ϣ*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			OrderState orderState = orderStateDAO.GetOrderState(stateId);

			// �ͻ��˲�ѯ�Ķ���״̬��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("stateId").value(orderState.getStateId());
			  stringer.key("stateName").value(orderState.getStateName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¶���״̬��Ϣ����ȡ����״̬��Ϣ�������������浽�½��Ķ���״̬��Ϣ���� */ 
			OrderState orderState = new OrderState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			orderState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			orderState.setStateName(stateName);

			/* ����ҵ���ִ�и��²��� */
			String result = orderStateDAO.UpdateOrderState(orderState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
