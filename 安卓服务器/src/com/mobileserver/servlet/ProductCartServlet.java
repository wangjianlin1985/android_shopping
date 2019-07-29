package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ProductCartDAO;
import com.mobileserver.domain.ProductCart;

import org.json.JSONStringer;

public class ProductCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造商品购物车业务层对象*/
	private ProductCartDAO productCartDAO = new ProductCartDAO();

	/*默认构造函数*/
	public ProductCartServlet() {
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
			/*获取查询商品购物车的参数信息*/
			String memberObj = "";
			if (request.getParameter("memberObj") != null)
				memberObj = request.getParameter("memberObj");
			String productObj = "";
			if (request.getParameter("productObj") != null)
				productObj = request.getParameter("productObj");

			/*调用业务逻辑层执行商品购物车查询*/
			List<ProductCart> productCartList = productCartDAO.QueryProductCart(memberObj,productObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ProductCarts>").append("\r\n");
			for (int i = 0; i < productCartList.size(); i++) {
				sb.append("	<ProductCart>").append("\r\n")
				.append("		<cartId>")
				.append(productCartList.get(i).getCartId())
				.append("</cartId>").append("\r\n")
				.append("		<memberObj>")
				.append(productCartList.get(i).getMemberObj())
				.append("</memberObj>").append("\r\n")
				.append("		<productObj>")
				.append(productCartList.get(i).getProductObj())
				.append("</productObj>").append("\r\n")
				.append("		<price>")
				.append(productCartList.get(i).getPrice())
				.append("</price>").append("\r\n")
				.append("		<count>")
				.append(productCartList.get(i).getCount())
				.append("</count>").append("\r\n")
				.append("	</ProductCart>").append("\r\n");
			}
			sb.append("</ProductCarts>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ProductCart productCart: productCartList) {
				  stringer.object();
			  stringer.key("cartId").value(productCart.getCartId());
			  stringer.key("memberObj").value(productCart.getMemberObj());
			  stringer.key("productObj").value(productCart.getProductObj());
			  stringer.key("price").value(productCart.getPrice());
			  stringer.key("count").value(productCart.getCount());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加商品购物车：获取商品购物车参数，参数保存到新建的商品购物车对象 */ 
			ProductCart productCart = new ProductCart();
			int cartId = Integer.parseInt(request.getParameter("cartId"));
			productCart.setCartId(cartId);
			String memberObj = new String(request.getParameter("memberObj").getBytes("iso-8859-1"), "UTF-8");
			productCart.setMemberObj(memberObj);
			String productObj = new String(request.getParameter("productObj").getBytes("iso-8859-1"), "UTF-8");
			productCart.setProductObj(productObj);
			float price = Float.parseFloat(request.getParameter("price"));
			productCart.setPrice(price);
			int count = Integer.parseInt(request.getParameter("count"));
			productCart.setCount(count);

			/* 调用业务层执行添加操作 */
			String result = productCartDAO.AddProductCart(productCart);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除商品购物车：获取商品购物车的记录编号*/
			int cartId = Integer.parseInt(request.getParameter("cartId"));
			/*调用业务逻辑层执行删除操作*/
			String result = productCartDAO.DeleteProductCart(cartId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新商品购物车之前先根据cartId查询某个商品购物车*/
			int cartId = Integer.parseInt(request.getParameter("cartId"));
			ProductCart productCart = productCartDAO.GetProductCart(cartId);

			// 客户端查询的商品购物车对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("cartId").value(productCart.getCartId());
			  stringer.key("memberObj").value(productCart.getMemberObj());
			  stringer.key("productObj").value(productCart.getProductObj());
			  stringer.key("price").value(productCart.getPrice());
			  stringer.key("count").value(productCart.getCount());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新商品购物车：获取商品购物车参数，参数保存到新建的商品购物车对象 */ 
			ProductCart productCart = new ProductCart();
			int cartId = Integer.parseInt(request.getParameter("cartId"));
			productCart.setCartId(cartId);
			String memberObj = new String(request.getParameter("memberObj").getBytes("iso-8859-1"), "UTF-8");
			productCart.setMemberObj(memberObj);
			String productObj = new String(request.getParameter("productObj").getBytes("iso-8859-1"), "UTF-8");
			productCart.setProductObj(productObj);
			float price = Float.parseFloat(request.getParameter("price"));
			productCart.setPrice(price);
			int count = Integer.parseInt(request.getParameter("count"));
			productCart.setCount(count);

			/* 调用业务层执行更新操作 */
			String result = productCartDAO.UpdateProductCart(productCart);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
