package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.EvaluateDAO;
import com.mobileserver.domain.Evaluate;

import org.json.JSONStringer;

public class EvaluateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������Ʒ����ҵ������*/
	private EvaluateDAO evaluateDAO = new EvaluateDAO();

	/*Ĭ�Ϲ��캯��*/
	public EvaluateServlet() {
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
			/*��ȡ��ѯ��Ʒ���۵Ĳ�����Ϣ*/
			String productObj = "";
			if (request.getParameter("productObj") != null)
				productObj = request.getParameter("productObj");
			String memberObj = "";
			if (request.getParameter("memberObj") != null)
				memberObj = request.getParameter("memberObj");

			/*����ҵ���߼���ִ����Ʒ���۲�ѯ*/
			List<Evaluate> evaluateList = evaluateDAO.QueryEvaluate(productObj,memberObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Evaluates>").append("\r\n");
			for (int i = 0; i < evaluateList.size(); i++) {
				sb.append("	<Evaluate>").append("\r\n")
				.append("		<evaluateId>")
				.append(evaluateList.get(i).getEvaluateId())
				.append("</evaluateId>").append("\r\n")
				.append("		<productObj>")
				.append(evaluateList.get(i).getProductObj())
				.append("</productObj>").append("\r\n")
				.append("		<memberObj>")
				.append(evaluateList.get(i).getMemberObj())
				.append("</memberObj>").append("\r\n")
				.append("		<content>")
				.append(evaluateList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<evaluateTime>")
				.append(evaluateList.get(i).getEvaluateTime())
				.append("</evaluateTime>").append("\r\n")
				.append("	</Evaluate>").append("\r\n");
			}
			sb.append("</Evaluates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Evaluate evaluate: evaluateList) {
				  stringer.object();
			  stringer.key("evaluateId").value(evaluate.getEvaluateId());
			  stringer.key("productObj").value(evaluate.getProductObj());
			  stringer.key("memberObj").value(evaluate.getMemberObj());
			  stringer.key("content").value(evaluate.getContent());
			  stringer.key("evaluateTime").value(evaluate.getEvaluateTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����Ʒ���ۣ���ȡ��Ʒ���۲������������浽�½�����Ʒ���۶��� */ 
			Evaluate evaluate = new Evaluate();
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			evaluate.setEvaluateId(evaluateId);
			String productObj = new String(request.getParameter("productObj").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setProductObj(productObj);
			String memberObj = new String(request.getParameter("memberObj").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setMemberObj(memberObj);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setContent(content);
			String evaluateTime = new String(request.getParameter("evaluateTime").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setEvaluateTime(evaluateTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = evaluateDAO.AddEvaluate(evaluate);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ʒ���ۣ���ȡ��Ʒ���۵����۱��*/
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = evaluateDAO.DeleteEvaluate(evaluateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������Ʒ����֮ǰ�ȸ���evaluateId��ѯĳ����Ʒ����*/
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			Evaluate evaluate = evaluateDAO.GetEvaluate(evaluateId);

			// �ͻ��˲�ѯ����Ʒ���۶��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("evaluateId").value(evaluate.getEvaluateId());
			  stringer.key("productObj").value(evaluate.getProductObj());
			  stringer.key("memberObj").value(evaluate.getMemberObj());
			  stringer.key("content").value(evaluate.getContent());
			  stringer.key("evaluateTime").value(evaluate.getEvaluateTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������Ʒ���ۣ���ȡ��Ʒ���۲������������浽�½�����Ʒ���۶��� */ 
			Evaluate evaluate = new Evaluate();
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			evaluate.setEvaluateId(evaluateId);
			String productObj = new String(request.getParameter("productObj").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setProductObj(productObj);
			String memberObj = new String(request.getParameter("memberObj").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setMemberObj(memberObj);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setContent(content);
			String evaluateTime = new String(request.getParameter("evaluateTime").getBytes("iso-8859-1"), "UTF-8");
			evaluate.setEvaluateTime(evaluateTime);

			/* ����ҵ���ִ�и��²��� */
			String result = evaluateDAO.UpdateEvaluate(evaluate);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
