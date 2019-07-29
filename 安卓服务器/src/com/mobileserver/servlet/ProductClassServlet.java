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

	/*构造商品类别业务层对象*/
	private ProductClassDAO productClassDAO = new ProductClassDAO();

	/*默认构造函数*/
	public ProductClassServlet() {
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
			/*获取查询商品类别的参数信息*/

			/*调用业务逻辑层执行商品类别查询*/
			List<ProductClass> productClassList = productClassDAO.QueryProductClass();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加商品类别：获取商品类别参数，参数保存到新建的商品类别对象 */ 
			ProductClass productClass = new ProductClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			productClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			productClass.setClassName(className);

			/* 调用业务层执行添加操作 */
			String result = productClassDAO.AddProductClass(productClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除商品类别：获取商品类别的类别编号*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			/*调用业务逻辑层执行删除操作*/
			String result = productClassDAO.DeleteProductClass(classId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新商品类别之前先根据classId查询某个商品类别*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			ProductClass productClass = productClassDAO.GetProductClass(classId);

			// 客户端查询的商品类别对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新商品类别：获取商品类别参数，参数保存到新建的商品类别对象 */ 
			ProductClass productClass = new ProductClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			productClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			productClass.setClassName(className);

			/* 调用业务层执行更新操作 */
			String result = productClassDAO.UpdateProductClass(productClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
