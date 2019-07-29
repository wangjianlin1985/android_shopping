package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.YesOrNo;
import com.mobileserver.util.DB;

public class YesOrNoDAO {

	public List<YesOrNo> QueryYesOrNo() {
		List<YesOrNo> yesOrNoList = new ArrayList<YesOrNo>();
		DB db = new DB();
		String sql = "select * from YesOrNo where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				YesOrNo yesOrNo = new YesOrNo();
				yesOrNo.setId(rs.getString("id"));
				yesOrNo.setName(rs.getString("name"));
				yesOrNoList.add(yesOrNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return yesOrNoList;
	}
	/* �����Ƿ���Ϣ���󣬽����Ƿ���Ϣ�����ҵ�� */
	public String AddYesOrNo(YesOrNo yesOrNo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������Ƿ���Ϣ */
			String sqlString = "insert into YesOrNo(id,name) values (";
			sqlString += "'" + yesOrNo.getId() + "',";
			sqlString += "'" + yesOrNo.getName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�Ƿ���Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�Ƿ���Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���Ƿ���Ϣ */
	public String DeleteYesOrNo(String id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from YesOrNo where id='" + id + "'";
			db.executeUpdate(sqlString);
			result = "�Ƿ���Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�Ƿ���Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �����Ƿ��Ż�ȡ���Ƿ���Ϣ */
	public YesOrNo GetYesOrNo(String id) {
		YesOrNo yesOrNo = null;
		DB db = new DB();
		String sql = "select * from YesOrNo where id='" + id + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				yesOrNo = new YesOrNo();
				yesOrNo.setId(rs.getString("id"));
				yesOrNo.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return yesOrNo;
	}
	/* �����Ƿ���Ϣ */
	public String UpdateYesOrNo(YesOrNo yesOrNo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update YesOrNo set ";
			sql += "name='" + yesOrNo.getName() + "'";
			sql += " where id='" + yesOrNo.getId() + "'";
			db.executeUpdate(sql);
			result = "�Ƿ���Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�Ƿ���Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
