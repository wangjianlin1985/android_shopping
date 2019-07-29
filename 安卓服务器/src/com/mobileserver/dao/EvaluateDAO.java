package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Evaluate;
import com.mobileserver.util.DB;

public class EvaluateDAO {

	public List<Evaluate> QueryEvaluate(String productObj,String memberObj) {
		List<Evaluate> evaluateList = new ArrayList<Evaluate>();
		DB db = new DB();
		String sql = "select * from Evaluate where 1=1";
		if (!productObj.equals(""))
			sql += " and productObj = '" + productObj + "'";
		if (!memberObj.equals(""))
			sql += " and memberObj = '" + memberObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Evaluate evaluate = new Evaluate();
				evaluate.setEvaluateId(rs.getInt("evaluateId"));
				evaluate.setProductObj(rs.getString("productObj"));
				evaluate.setMemberObj(rs.getString("memberObj"));
				evaluate.setContent(rs.getString("content"));
				evaluate.setEvaluateTime(rs.getString("evaluateTime"));
				evaluateList.add(evaluate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return evaluateList;
	}
	/* 传入商品评价对象，进行商品评价的添加业务 */
	public String AddEvaluate(Evaluate evaluate) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新商品评价 */
			String sqlString = "insert into Evaluate(productObj,memberObj,content,evaluateTime) values (";
			sqlString += "'" + evaluate.getProductObj() + "',";
			sqlString += "'" + evaluate.getMemberObj() + "',";
			sqlString += "'" + evaluate.getContent() + "',";
			sqlString += "'" + evaluate.getEvaluateTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "商品评价添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品评价添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除商品评价 */
	public String DeleteEvaluate(int evaluateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Evaluate where evaluateId=" + evaluateId;
			db.executeUpdate(sqlString);
			result = "商品评价删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品评价删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据评价编号获取到商品评价 */
	public Evaluate GetEvaluate(int evaluateId) {
		Evaluate evaluate = null;
		DB db = new DB();
		String sql = "select * from Evaluate where evaluateId=" + evaluateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				evaluate = new Evaluate();
				evaluate.setEvaluateId(rs.getInt("evaluateId"));
				evaluate.setProductObj(rs.getString("productObj"));
				evaluate.setMemberObj(rs.getString("memberObj"));
				evaluate.setContent(rs.getString("content"));
				evaluate.setEvaluateTime(rs.getString("evaluateTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return evaluate;
	}
	/* 更新商品评价 */
	public String UpdateEvaluate(Evaluate evaluate) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Evaluate set ";
			sql += "productObj='" + evaluate.getProductObj() + "',";
			sql += "memberObj='" + evaluate.getMemberObj() + "',";
			sql += "content='" + evaluate.getContent() + "',";
			sql += "evaluateTime='" + evaluate.getEvaluateTime() + "'";
			sql += " where evaluateId=" + evaluate.getEvaluateId();
			db.executeUpdate(sql);
			result = "商品评价更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "商品评价更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
