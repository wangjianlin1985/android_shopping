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
	/* 传入会员信息对象，进行会员信息的添加业务 */
	public String AddMemberInfo(MemberInfo memberInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新会员信息 */
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
			result = "会员信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "会员信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除会员信息 */
	public String DeleteMemberInfo(String memberUserName) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from MemberInfo where memberUserName='" + memberUserName + "'";
			db.executeUpdate(sqlString);
			result = "会员信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "会员信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据会员用户名获取到会员信息 */
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
	/* 更新会员信息 */
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
			result = "会员信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "会员信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
