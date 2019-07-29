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
	/* 传入商品信息对象，进行商品信息的添加业务 */
	public String AddProductInfo(ProductInfo productInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新商品信息 */
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
			result = "商品信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除商品信息 */
	public String DeleteProductInfo(String productNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ProductInfo where productNo='" + productNo + "'";
			db.executeUpdate(sqlString);
			result = "商品信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据商品编号获取到商品信息 */
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
	/* 更新商品信息 */
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
			result = "商品信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
