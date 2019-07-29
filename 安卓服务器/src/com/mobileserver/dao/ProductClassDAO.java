package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ProductClass;
import com.mobileserver.util.DB;

public class ProductClassDAO {

	public List<ProductClass> QueryProductClass() {
		List<ProductClass> productClassList = new ArrayList<ProductClass>();
		DB db = new DB();
		String sql = "select * from ProductClass where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ProductClass productClass = new ProductClass();
				productClass.setClassId(rs.getInt("classId"));
				productClass.setClassName(rs.getString("className"));
				productClassList.add(productClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return productClassList;
	}
	/* 传入商品类别对象，进行商品类别的添加业务 */
	public String AddProductClass(ProductClass productClass) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新商品类别 */
			String sqlString = "insert into ProductClass(className) values (";
			sqlString += "'" + productClass.getClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "商品类别添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品类别添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除商品类别 */
	public String DeleteProductClass(int classId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ProductClass where classId=" + classId;
			db.executeUpdate(sqlString);
			result = "商品类别删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品类别删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据类别编号获取到商品类别 */
	public ProductClass GetProductClass(int classId) {
		ProductClass productClass = null;
		DB db = new DB();
		String sql = "select * from ProductClass where classId=" + classId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				productClass = new ProductClass();
				productClass.setClassId(rs.getInt("classId"));
				productClass.setClassName(rs.getString("className"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return productClass;
	}
	/* 更新商品类别 */
	public String UpdateProductClass(ProductClass productClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ProductClass set ";
			sql += "className='" + productClass.getClassName() + "'";
			sql += " where classId=" + productClass.getClassId();
			db.executeUpdate(sql);
			result = "商品类别更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品类别更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
