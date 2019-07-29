package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ProductInfo;
import com.mobileserver.util.DB;

public class ProductInfoDAO {

	public List<ProductInfo> QueryProductInfo(String productNo,int productClassObj,String productName,String recommendFlag,Timestamp onlineDate) {
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		DB db = new DB();
		String sql = "select * from ProductInfo where 1=1";
		if (!productNo.equals(""))
			sql += " and productNo like '%" + productNo + "%'";
		if (productClassObj != 0)
			sql += " and productClassObj=" + productClassObj;
		if (!productName.equals(""))
			sql += " and productName like '%" + productName + "%'";
		if (!recommendFlag.equals(""))
			sql += " and recommendFlag = '" + recommendFlag + "'";
		if(onlineDate!=null)
			sql += " and onlineDate='" + onlineDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setProductNo(rs.getString("productNo"));
				productInfo.setProductClassObj(rs.getInt("productClassObj"));
				productInfo.setProductName(rs.getString("productName"));
				productInfo.setProductPhoto(rs.getString("productPhoto"));
				productInfo.setProductPrice(rs.getFloat("productPrice"));
				productInfo.setProductCount(rs.getInt("productCount"));
				productInfo.setRecommendFlag(rs.getString("recommendFlag"));
				productInfo.setHotNum(rs.getInt("hotNum"));
				productInfo.setOnlineDate(rs.getTimestamp("onlineDate"));
				productInfoList.add(productInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return productInfoList;
	}
	/* ������Ʒ��Ϣ���󣬽�����Ʒ��Ϣ�����ҵ�� */
	public String AddProductInfo(ProductInfo productInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������Ʒ��Ϣ */
			String sqlString = "insert into ProductInfo(productNo,productClassObj,productName,productPhoto,productPrice,productCount,recommendFlag,hotNum,onlineDate) values (";
			sqlString += "'" + productInfo.getProductNo() + "',";
			sqlString += productInfo.getProductClassObj() + ",";
			sqlString += "'" + productInfo.getProductName() + "',";
			sqlString += "'" + productInfo.getProductPhoto() + "',";
			sqlString += productInfo.getProductPrice() + ",";
			sqlString += productInfo.getProductCount() + ",";
			sqlString += "'" + productInfo.getRecommendFlag() + "',";
			sqlString += productInfo.getHotNum() + ",";
			sqlString += "'" + productInfo.getOnlineDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ʒ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ʒ��Ϣ */
	public String DeleteProductInfo(String productNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ProductInfo where productNo='" + productNo + "'";
			db.executeUpdate(sqlString);
			result = "��Ʒ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ������Ʒ��Ż�ȡ����Ʒ��Ϣ */
	public ProductInfo GetProductInfo(String productNo) {
		ProductInfo productInfo = null;
		DB db = new DB();
		String sql = "select * from ProductInfo where productNo='" + productNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				productInfo = new ProductInfo();
				productInfo.setProductNo(rs.getString("productNo"));
				productInfo.setProductClassObj(rs.getInt("productClassObj"));
				productInfo.setProductName(rs.getString("productName"));
				productInfo.setProductPhoto(rs.getString("productPhoto"));
				productInfo.setProductPrice(rs.getFloat("productPrice"));
				productInfo.setProductCount(rs.getInt("productCount"));
				productInfo.setRecommendFlag(rs.getString("recommendFlag"));
				productInfo.setHotNum(rs.getInt("hotNum"));
				productInfo.setOnlineDate(rs.getTimestamp("onlineDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return productInfo;
	}
	/* ������Ʒ��Ϣ */
	public String UpdateProductInfo(ProductInfo productInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ProductInfo set ";
			sql += "productClassObj=" + productInfo.getProductClassObj() + ",";
			sql += "productName='" + productInfo.getProductName() + "',";
			sql += "productPhoto='" + productInfo.getProductPhoto() + "',";
			sql += "productPrice=" + productInfo.getProductPrice() + ",";
			sql += "productCount=" + productInfo.getProductCount() + ",";
			sql += "recommendFlag='" + productInfo.getRecommendFlag() + "',";
			sql += "hotNum=" + productInfo.getHotNum() + ",";
			sql += "onlineDate='" + productInfo.getOnlineDate() + "'";
			sql += " where productNo='" + productInfo.getProductNo() + "'";
			db.executeUpdate(sql);
			result = "��Ʒ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
