package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.MemberInfo;
import com.mobileserver.util.DB;

public class MemberInfoDAO {

	public List<MemberInfo> QueryMemberInfo(String memberUserName,Timestamp birthday) {
		List<MemberInfo> memberInfoList = new ArrayList<MemberInfo>();
		DB db = new DB();
		String sql = "select * from MemberInfo where 1=1";
		if (!memberUserName.equals(""))
			sql += " and memberUserName like '%" + memberUserName + "%'";
		if(birthday!=null)
			sql += " and birthday='" + birthday + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				MemberInfo memberInfo = new MemberInfo();
				memberInfo.setMemberUserName(rs.getString("memberUserName"));
				memberInfo.setPassword(rs.getString("password"));
				memberInfo.setRealName(rs.getString("realName"));
				memberInfo.setSex(rs.getString("sex"));
				memberInfo.setBirthday(rs.getTimestamp("birthday"));
				memberInfo.setTelephone(rs.getString("telephone"));
				memberInfo.setEmail(rs.getString("email"));
				memberInfo.setQq(rs.getString("qq"));
				memberInfo.setAddress(rs.getString("address"));
				memberInfo.setPhoto(rs.getString("photo"));
				memberInfoList.add(memberInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return memberInfoList;
	}
	/* �����Ա��Ϣ���󣬽��л�Ա��Ϣ�����ҵ�� */
	public String AddMemberInfo(MemberInfo memberInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����»�Ա��Ϣ */
			String sqlString = "insert into MemberInfo(memberUserName,password,realName,sex,birthday,telephone,email,qq,address,photo) values (";
			sqlString += "'" + memberInfo.getMemberUserName() + "',";
			sqlString += "'" + memberInfo.getPassword() + "',";
			sqlString += "'" + memberInfo.getRealName() + "',";
			sqlString += "'" + memberInfo.getSex() + "',";
			sqlString += "'" + memberInfo.getBirthday() + "',";
			sqlString += "'" + memberInfo.getTelephone() + "',";
			sqlString += "'" + memberInfo.getEmail() + "',";
			sqlString += "'" + memberInfo.getQq() + "',";
			sqlString += "'" + memberInfo.getAddress() + "',";
			sqlString += "'" + memberInfo.getPhoto() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ա��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ա��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ա��Ϣ */
	public String DeleteMemberInfo(String memberUserName) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from MemberInfo where memberUserName='" + memberUserName + "'";
			db.executeUpdate(sqlString);
			result = "��Ա��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ա��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݻ�Ա�û�����ȡ����Ա��Ϣ */
	public MemberInfo GetMemberInfo(String memberUserName) {
		MemberInfo memberInfo = null;
		DB db = new DB();
		String sql = "select * from MemberInfo where memberUserName='" + memberUserName + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				memberInfo = new MemberInfo();
				memberInfo.setMemberUserName(rs.getString("memberUserName"));
				memberInfo.setPassword(rs.getString("password"));
				memberInfo.setRealName(rs.getString("realName"));
				memberInfo.setSex(rs.getString("sex"));
				memberInfo.setBirthday(rs.getTimestamp("birthday"));
				memberInfo.setTelephone(rs.getString("telephone"));
				memberInfo.setEmail(rs.getString("email"));
				memberInfo.setQq(rs.getString("qq"));
				memberInfo.setAddress(rs.getString("address"));
				memberInfo.setPhoto(rs.getString("photo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return memberInfo;
	}
	/* ���»�Ա��Ϣ */
	public String UpdateMemberInfo(MemberInfo memberInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update MemberInfo set ";
			sql += "password='" + memberInfo.getPassword() + "',";
			sql += "realName='" + memberInfo.getRealName() + "',";
			sql += "sex='" + memberInfo.getSex() + "',";
			sql += "birthday='" + memberInfo.getBirthday() + "',";
			sql += "telephone='" + memberInfo.getTelephone() + "',";
			sql += "email='" + memberInfo.getEmail() + "',";
			sql += "qq='" + memberInfo.getQq() + "',";
			sql += "address='" + memberInfo.getAddress() + "',";
			sql += "photo='" + memberInfo.getPhoto() + "'";
			sql += " where memberUserName='" + memberInfo.getMemberUserName() + "'";
			db.executeUpdate(sql);
			result = "��Ա��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ա��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
