package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ProductClassDAO;
import com.mobileserver.domain.ProductClass;

import org.json.JSONStringer;

public class ProductClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������Ʒ���ҵ������*/
	private ProductClassDAO productClassDAO = new ProductClassDAO();

	/*Ĭ�Ϲ��캯��*/
	public ProductClassServlet() {
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
			/*��ȡ��ѯ��Ʒ���Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ����Ʒ����ѯ*/
			List<ProductClass> productClassList = productClassDAO.QueryProductClass();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ProductClasss>").append("\r\n");
			for (int i = 0; i < productClassList.size(); i++) {
				sb.append("	<ProductClass>").append("\r\n")
				.append("		<classId>")
				.append(productClassList.get(i).getClassId())
				.append("</classId>").append("\r\n")
				.append("		<className>")
				.append(productClassList.get(i).getClassName())
				.append("</className>").append("\r\n")
				.append("	</ProductClass>").append("\r\n");
			}
			sb.append("</ProductClasss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ProductClass productClass: productClassList) {
				  stringer.object();
			  stringer.key("classId").value(productClass.getClassId());
			  stringer.key("className").value(productClass.getClassName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����Ʒ��𣺻�ȡ��Ʒ���������������浽�½�����Ʒ������ */ 
			ProductClass productClass = new ProductClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			productClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			productClass.setClassName(className);

			/* ����ҵ���ִ����Ӳ��� */
			String result = productClassDAO.AddProductClass(productClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ʒ��𣺻�ȡ��Ʒ���������*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = productClassDAO.DeleteProductClass(classId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������Ʒ���֮ǰ�ȸ���classId��ѯĳ����Ʒ���*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			ProductClass productClass = productClassDAO.GetProductClass(classId);

			// �ͻ��˲�ѯ����Ʒ�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("classId").value(productClass.getClassId());
			  stringer.key("className").value(productClass.getClassName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������Ʒ��𣺻�ȡ��Ʒ���������������浽�½�����Ʒ������ */ 
			ProductClass productClass = new ProductClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			productClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			productClass.setClassName(className);

			/* ����ҵ���ִ�и��²��� */
			String result = productClassDAO.UpdateProductClass(productClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
