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

	/*构造是否信息业务层对象*/
	private YesOrNoDAO yesOrNoDAO = new YesOrNoDAO();

	/*默认构造函数*/
	public YesOrNoServlet() {
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
			/*获取查询是否信息的参数信息*/

			/*调用业务逻辑层执行是否信息查询*/
			List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNo();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加是否信息：获取是否信息参数，参数保存到新建的是否信息对象 */ 
			YesOrNo yesOrNo = new YesOrNo();
			String id = new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
			yesOrNo.setId(id);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			yesOrNo.setName(name);

			/* 调用业务层执行添加操作 */
			String result = yesOrNoDAO.AddYesOrNo(yesOrNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除是否信息：获取是否信息的是否编号*/
			String id = new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = yesOrNoDAO.DeleteYesOrNo(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新是否信息之前先根据id查询某个是否信息*/
			String id = new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
			YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNo(id);

			// 客户端查询的是否信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新是否信息：获取是否信息参数，参数保存到新建的是否信息对象 */ 
			YesOrNo yesOrNo = new YesOrNo();
			String id = new String(request.getParameter("id").getBytes("iso-8859-1"), "UTF-8");
			yesOrNo.setId(id);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			yesOrNo.setName(name);

			/* 调用业务层执行更新操作 */
			String result = yesOrNoDAO.UpdateYesOrNo(yesOrNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
