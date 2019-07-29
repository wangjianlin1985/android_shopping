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
	/* ������Ʒ�����󣬽�����Ʒ�������ҵ�� */
	public String AddProductClass(ProductClass productClass) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������Ʒ��� */
			String sqlString = "insert into ProductClass(className) values (";
			sqlString += "'" + productClass.getClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ʒ�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ʒ��� */
	public String DeleteProductClass(int classId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ProductClass where classId=" + classId;
			db.executeUpdate(sqlString);
			result = "��Ʒ���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������Ż�ȡ����Ʒ��� */
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
	/* ������Ʒ��� */
	public String UpdateProductClass(ProductClass productClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ProductClass set ";
			sql += "className='" + productClass.getClassName() + "'";
			sql += " where classId=" + productClass.getClassId();
			db.executeUpdate(sql);
			result = "��Ʒ�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
