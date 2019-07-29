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

	/*构造商品评价业务层对象*/
	private EvaluateDAO evaluateDAO = new EvaluateDAO();

	/*默认构造函数*/
	public EvaluateServlet() {
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
			/*获取查询商品评价的参数信息*/
			String productObj = "";
			if (request.getParameter("productObj") != null)
				productObj = request.getParameter("productObj");
			String memberObj = "";
			if (request.getParameter("memberObj") != null)
				memberObj = request.getParameter("memberObj");

			/*调用业务逻辑层执行商品评价查询*/
			List<Evaluate> evaluateList = evaluateDAO.QueryEvaluate(productObj,memberObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加商品评价：获取商品评价参数，参数保存到新建的商品评价对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = evaluateDAO.AddEvaluate(evaluate);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除商品评价：获取商品评价的评价编号*/
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			/*调用业务逻辑层执行删除操作*/
			String result = evaluateDAO.DeleteEvaluate(evaluateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新商品评价之前先根据evaluateId查询某个商品评价*/
			int evaluateId = Integer.parseInt(request.getParameter("evaluateId"));
			Evaluate evaluate = evaluateDAO.GetEvaluate(evaluateId);

			// 客户端查询的商品评价对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新商品评价：获取商品评价参数，参数保存到新建的商品评价对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = evaluateDAO.UpdateEvaluate(evaluate);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
