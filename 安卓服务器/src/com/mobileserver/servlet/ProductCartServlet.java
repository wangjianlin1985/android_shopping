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

	/*������Ʒ���ﳵҵ������*/
	private ProductCartDAO productCartDAO = new ProductCartDAO();

	/*Ĭ�Ϲ��캯��*/
	public ProductCartServlet() {
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
			/*��ȡ��ѯ��Ʒ���ﳵ�Ĳ�����Ϣ*/
			String memberObj = "";
			if (request.getParameter("memberObj") != null)
				memberObj = request.getParameter("memberObj");
			String productObj = "";
			if (request.getParameter("productObj") != null)
				productObj = request.getParameter("productObj");

			/*����ҵ���߼���ִ����Ʒ���ﳵ��ѯ*/
			List<ProductCart> productCartList = productCartDAO.QueryProductCart(memberObj,productObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����Ʒ���ﳵ����ȡ��Ʒ���ﳵ�������������浽�½�����Ʒ���ﳵ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = productCartDAO.AddProductCart(productCart);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ʒ���ﳵ����ȡ��Ʒ���ﳵ�ļ�¼���*/
			int cartId = Integer.parseInt(request.getParameter("cartId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = productCartDAO.DeleteProductCart(cartId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������Ʒ���ﳵ֮ǰ�ȸ���cartId��ѯĳ����Ʒ���ﳵ*/
			int cartId = Integer.parseInt(request.getParameter("cartId"));
			ProductCart productCart = productCartDAO.GetProductCart(cartId);

			// �ͻ��˲�ѯ����Ʒ���ﳵ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������Ʒ���ﳵ����ȡ��Ʒ���ﳵ�������������浽�½�����Ʒ���ﳵ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = productCartDAO.UpdateProductCart(productCart);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
