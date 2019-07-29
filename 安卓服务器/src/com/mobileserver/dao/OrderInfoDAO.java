package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderInfo;
import com.mobileserver.util.DB;

public class OrderInfoDAO {

	public List<OrderInfo> QueryOrderInfo(String orderNo,String memberObj,String orderTime,int orderStateObj,String realName,String telphone) {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		DB db = new DB();
		String sql = "select * from OrderInfo where 1=1";
		if (!orderNo.equals(""))
			sql += " and orderNo like '%" + orderNo + "%'";
		if (!memberObj.equals(""))
			sql += " and memberObj = '" + memberObj + "'";
		if (!orderTime.equals(""))
			sql += " and orderTime like '%" + orderTime + "%'";
		if (orderStateObj != 0)
			sql += " and orderStateObj=" + orderStateObj;
		if (!realName.equals(""))
			sql += " and realName like '%" + realName + "%'";
		if (!telphone.equals(""))
			sql += " and telphone like '%" + telphone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNo(rs.getString("orderNo"));
				orderInfo.setMemberObj(rs.getString("memberObj"));
				orderInfo.setOrderTime(rs.getString("orderTime"));
				orderInfo.setTotalMoney(rs.getFloat("totalMoney"));
				orderInfo.setOrderStateObj(rs.getInt("orderStateObj"));
				orderInfo.setBuyWay(rs.getString("buyWay"));
				orderInfo.setRealName(rs.getString("realName"));
				orderInfo.setTelphone(rs.getString("telphone"));
				orderInfo.setPostcode(rs.getString("postcode"));
				orderInfo.setAddress(rs.getString("address"));
				orderInfo.setMemo(rs.getString("memo"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfoList;
	}
	/* ���붩����Ϣ���󣬽��ж�����Ϣ�����ҵ�� */
	public String AddOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¶�����Ϣ */
			String sqlString = "insert into OrderInfo(orderNo,memberObj,orderTime,totalMoney,orderStateObj,buyWay,realName,telphone,postcode,address,memo) values (";
			sqlString += "'" + orderInfo.getOrderNo() + "',";
			sqlString += "'" + orderInfo.getMemberObj() + "',";
			sqlString += "'" + orderInfo.getOrderTime() + "',";
			sqlString += orderInfo.getTotalMoney() + ",";
			sqlString += orderInfo.getOrderStateObj() + ",";
			sqlString += "'" + orderInfo.getBuyWay() + "',";
			sqlString += "'" + orderInfo.getRealName() + "',";
			sqlString += "'" + orderInfo.getTelphone() + "',";
			sqlString += "'" + orderInfo.getPostcode() + "',";
			sqlString += "'" + orderInfo.getAddress() + "',";
			sqlString += "'" + orderInfo.getMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������Ϣ */
	public String DeleteOrderInfo(String orderNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderInfo where orderNo='" + orderNo + "'";
			db.executeUpdate(sqlString);
			result = "������Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݶ�����Ż�ȡ��������Ϣ */
	public OrderInfo GetOrderInfo(String orderNo) {
		OrderInfo orderInfo = null;
		DB db = new DB();
		String sql = "select * from OrderInfo where orderNo='" + orderNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderInfo = new OrderInfo();
				orderInfo.setOrderNo(rs.getString("orderNo"));
				orderInfo.setMemberObj(rs.getString("memberObj"));
				orderInfo.setOrderTime(rs.getString("orderTime"));
				orderInfo.setTotalMoney(rs.getFloat("totalMoney"));
				orderInfo.setOrderStateObj(rs.getInt("orderStateObj"));
				orderInfo.setBuyWay(rs.getString("buyWay"));
				orderInfo.setRealName(rs.getString("realName"));
				orderInfo.setTelphone(rs.getString("telphone"));
				orderInfo.setPostcode(rs.getString("postcode"));
				orderInfo.setAddress(rs.getString("address"));
				orderInfo.setMemo(rs.getString("memo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfo;
	}
	/* ���¶�����Ϣ */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderInfo set ";
			sql += "memberObj='" + orderInfo.getMemberObj() + "',";
			sql += "orderTime='" + orderInfo.getOrderTime() + "',";
			sql += "totalMoney=" + orderInfo.getTotalMoney() + ",";
			sql += "orderStateObj=" + orderInfo.getOrderStateObj() + ",";
			sql += "buyWay='" + orderInfo.getBuyWay() + "',";
			sql += "realName='" + orderInfo.getRealName() + "',";
			sql += "telphone='" + orderInfo.getTelphone() + "',";
			sql += "postcode='" + orderInfo.getPostcode() + "',";
			sql += "address='" + orderInfo.getAddress() + "',";
			sql += "memo='" + orderInfo.getMemo() + "'";
			sql += " where orderNo='" + orderInfo.getOrderNo() + "'";
			db.executeUpdate(sql);
			result = "������Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
