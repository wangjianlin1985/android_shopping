package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderState;
import com.mobileserver.util.DB;

public class OrderStateDAO {

	public List<OrderState> QueryOrderState() {
		List<OrderState> orderStateList = new ArrayList<OrderState>();
		DB db = new DB();
		String sql = "select * from OrderState where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderState orderState = new OrderState();
				orderState.setStateId(rs.getInt("stateId"));
				orderState.setStateName(rs.getString("stateName"));
				orderStateList.add(orderState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderStateList;
	}
	/* ���붩��״̬��Ϣ���󣬽��ж���״̬��Ϣ�����ҵ�� */
	public String AddOrderState(OrderState orderState) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¶���״̬��Ϣ */
			String sqlString = "insert into OrderState(stateName) values (";
			sqlString += "'" + orderState.getStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "����״̬��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������״̬��Ϣ */
	public String DeleteOrderState(int stateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderState where stateId=" + stateId;
			db.executeUpdate(sqlString);
			result = "����״̬��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����״̬��Ż�ȡ������״̬��Ϣ */
	public OrderState GetOrderState(int stateId) {
		OrderState orderState = null;
		DB db = new DB();
		String sql = "select * from OrderState where stateId=" + stateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderState = new OrderState();
				orderState.setStateId(rs.getInt("stateId"));
				orderState.setStateName(rs.getString("stateName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderState;
	}
	/* ���¶���״̬��Ϣ */
	public String UpdateOrderState(OrderState orderState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderState set ";
			sql += "stateName='" + orderState.getStateName() + "'";
			sql += " where stateId=" + orderState.getStateId();
			db.executeUpdate(sql);
			result = "����״̬��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
