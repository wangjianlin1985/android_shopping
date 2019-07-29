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
	/* ������Ʒ���ﳵ���󣬽�����Ʒ���ﳵ�����ҵ�� */
	public String AddProductCart(ProductCart productCart) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������Ʒ���ﳵ */
			String sqlString = "insert into ProductCart(memberObj,productObj,price,count) values (";
			sqlString += "'" + productCart.getMemberObj() + "',";
			sqlString += "'" + productCart.getProductObj() + "',";
			sqlString += productCart.getPrice() + ",";
			sqlString += productCart.getCount();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ʒ���ﳵ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ���ﳵ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ʒ���ﳵ */
	public String DeleteProductCart(int cartId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ProductCart where cartId=" + cartId;
			db.executeUpdate(sqlString);
			result = "��Ʒ���ﳵɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ���ﳵɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����Ʒ���ﳵ */
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
	/* ������Ʒ���ﳵ */
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
			result = "��Ʒ���ﳵ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ���ﳵ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
