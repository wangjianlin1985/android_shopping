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
	/* ������Ʒ���۶��󣬽�����Ʒ���۵����ҵ�� */
	public String AddEvaluate(Evaluate evaluate) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������Ʒ���� */
			String sqlString = "insert into Evaluate(productObj,memberObj,content,evaluateTime) values (";
			sqlString += "'" + evaluate.getProductObj() + "',";
			sqlString += "'" + evaluate.getMemberObj() + "',";
			sqlString += "'" + evaluate.getContent() + "',";
			sqlString += "'" + evaluate.getEvaluateTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ʒ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ʒ���� */
	public String DeleteEvaluate(int evaluateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Evaluate where evaluateId=" + evaluateId;
			db.executeUpdate(sqlString);
			result = "��Ʒ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �������۱�Ż�ȡ����Ʒ���� */
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
	/* ������Ʒ���� */
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
			result = "��Ʒ���۸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ʒ���۸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
