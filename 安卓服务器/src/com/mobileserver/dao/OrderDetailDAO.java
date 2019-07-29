package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderDetail;
import com.mobileserver.util.DB;

public class OrderDetailDAO {

	public List<OrderDetail> QueryOrderDetail(String orderObj,String productObj) {
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		DB db = new DB();
		String sql = "select * from OrderDetail where 1=1";
		if (!orderObj.equals(""))
			sql += " and orderObj = '" + orderObj + "'";
		if (!productObj.equals(""))
			sql += " and productObj = '" + productObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setDetailId(rs.getInt("detailId"));
				orderDetail.setOrderObj(rs.getString("orderObj"));
				orderDetail.setProductObj(rs.getString("productObj"));
				orderDetail.setPrice(rs.getFloat("price"));
				orderDetail.setCount(rs.getInt("count"));
				orderDetailList.add(orderDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderDetailList;
	}
	/* ���붩����ϸ��Ϣ���󣬽��ж�����ϸ��Ϣ�����ҵ�� */
	public String AddOrderDetail(OrderDetail orderDetail) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¶�����ϸ��Ϣ */
			String sqlString = "insert into OrderDetail(orderObj,productObj,price,count) values (";
			sqlString += "'" + orderDetail.getOrderObj() + "',";
			sqlString += "'" + orderDetail.getProductObj() + "',";
			sqlString += orderDetail.getPrice() + ",";
			sqlString += orderDetail.getCount();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������ϸ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������ϸ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������ϸ��Ϣ */
	public String DeleteOrderDetail(int detailId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderDetail where detailId=" + detailId;
			db.executeUpdate(sqlString);
			result = "������ϸ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������ϸ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��������ϸ��Ϣ */
	public OrderDetail GetOrderDetail(int detailId) {
		OrderDetail orderDetail = null;
		DB db = new DB();
		String sql = "select * from OrderDetail where detailId=" + detailId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderDetail = new OrderDetail();
				orderDetail.setDetailId(rs.getInt("detailId"));
				orderDetail.setOrderObj(rs.getString("orderObj"));
				orderDetail.setProductObj(rs.getString("productObj"));
				orderDetail.setPrice(rs.getFloat("price"));
				orderDetail.setCount(rs.getInt("count"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderDetail;
	}
	/* ���¶�����ϸ��Ϣ */
	public String UpdateOrderDetail(OrderDetail orderDetail) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderDetail set ";
			sql += "orderObj='" + orderDetail.getOrderObj() + "',";
			sql += "productObj='" + orderDetail.getProductObj() + "',";
			sql += "price=" + orderDetail.getPrice() + ",";
			sql += "count=" + orderDetail.getCount();
			sql += " where detailId=" + orderDetail.getDetailId();
			db.executeUpdate(sql);
			result = "������ϸ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������ϸ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
