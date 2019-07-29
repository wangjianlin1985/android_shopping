package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ProductCart;
import com.mobileserver.util.DB;

public class ProductCartDAO {

	public List<ProductCart> QueryProductCart(String memberObj,String productObj) {
		List<ProductCart> productCartList = new ArrayList<ProductCart>();
		DB db = new DB();
		String sql = "select * from ProductCart where 1=1";
		if (!memberObj.equals(""))
			sql += " and memberObj = '" + memberObj + "'";
		if (!productObj.equals(""))
			sql += " and productObj = '" + productObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ProductCart productCart = new ProductCart();
				productCart.setCartId(rs.getInt("cartId"));
				productCart.setMemberObj(rs.getString("memberObj"));
				productCart.setProductObj(rs.getString("productObj"));
				productCart.setPrice(rs.getFloat("price"));
				productCart.setCount(rs.getInt("count"));
				productCartList.add(productCart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return productCartList;
	}
	/* 传入商品购物车对象，进行商品购物车的添加业务 */
	public String AddProductCart(ProductCart productCart) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新商品购物车 */
			String sqlString = "insert into ProductCart(memberObj,productObj,price,count) values (";
			sqlString += "'" + productCart.getMemberObj() + "',";
			sqlString += "'" + productCart.getProductObj() + "',";
			sqlString += productCart.getPrice() + ",";
			sqlString += productCart.getCount();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "商品购物车添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品购物车添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除商品购物车 */
	public String DeleteProductCart(int cartId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ProductCart where cartId=" + cartId;
			db.executeUpdate(sqlString);
			result = "商品购物车删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品购物车删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到商品购物车 */
	public ProductCart GetProductCart(int cartId) {
		ProductCart productCart = null;
		DB db = new DB();
		String sql = "select * from ProductCart where cartId=" + cartId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				productCart = new ProductCart();
				productCart.setCartId(rs.getInt("cartId"));
				productCart.setMemberObj(rs.getString("memberObj"));
				productCart.setProductObj(rs.getString("productObj"));
				productCart.setPrice(rs.getFloat("price"));
				productCart.setCount(rs.getInt("count"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return productCart;
	}
	/* 更新商品购物车 */
	public String UpdateProductCart(ProductCart productCart) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ProductCart set ";
			sql += "memberObj='" + productCart.getMemberObj() + "',";
			sql += "productObj='" + productCart.getProductObj() + "',";
			sql += "price=" + productCart.getPrice() + ",";
			sql += "count=" + productCart.getCount();
			sql += " where cartId=" + productCart.getCartId();
			db.executeUpdate(sql);
			result = "商品购物车更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品购物车更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
