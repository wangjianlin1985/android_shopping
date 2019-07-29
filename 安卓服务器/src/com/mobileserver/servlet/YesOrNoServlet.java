package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.YesOrNoDAO;
import com.mobileserver.domain.YesOrNo;

import org.json.JSONStringer;

public class YesOrNoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����Ƿ���Ϣҵ������*/
	private YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();

	/*Ĭ�Ϲ��캯��*/
	public YesOrNoServlet() {
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
			/*��ȡ��ѯ�Ƿ���Ϣ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ���Ƿ���Ϣ��ѯ*/
			List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNo();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<YesOrNos>").append("\r\n");
			for (int i = 0; i < yesOrNoList.size(); i++) {
				sb.append("	<YesOrNo>").append("\r\n")
				.append("		<id>")
				.append(yesOrNoList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<name>")
				.append(yesOrNoList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("	</YesOrNo>").append("\r\n");
			}
			sb.append("</YesOrNos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(YesOrNo yesOrNo: yesOrNoList) {
				  stringer.object();
			  stringer.key("id").value(yesOrNo.getId());
			  stringer.key("name").value(yesOrNo.getName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����Ƿ���Ϣ����ȡ�Ƿ���Ϣ�������������浽�½����Ƿ���Ϣ���� */ 
			YesOrNo yesOrNo = new YesOrNo();
			String id = new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
			yesOrNo.setId(id);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			yesOrNo.setName(name);

			/* ����ҵ���ִ����Ӳ��� */
			String result = yesOrNoDAO.AddYesOrNo(yesOrNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���Ƿ���Ϣ����ȡ�Ƿ���Ϣ���Ƿ���*/
			String id = new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = yesOrNoDAO.DeleteYesOrNo(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����Ƿ���Ϣ֮ǰ�ȸ���id��ѯĳ���Ƿ���Ϣ*/
			String id = new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
			YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNo(id);

			// �ͻ��˲�ѯ���Ƿ���Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(yesOrNo.getId());
			  stringer.key("name").value(yesOrNo.getName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����Ƿ���Ϣ����ȡ�Ƿ���Ϣ�������������浽�½����Ƿ���Ϣ���� */ 
			YesOrNo yesOrNo = new YesOrNo();
			String id = new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
			yesOrNo.setId(id);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			yesOrNo.setName(name);

			/* ����ҵ���ִ�и��²��� */
			String result = yesOrNoDAO.UpdateYesOrNo(yesOrNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
