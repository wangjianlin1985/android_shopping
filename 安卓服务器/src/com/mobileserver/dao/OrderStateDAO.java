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
	/* 传入订单状态信息对象，进行订单状态信息的添加业务 */
	public String AddOrderState(OrderState orderState) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新订单状态信息 */
			String sqlString = "insert into OrderState(stateName) values (";
			sqlString += "'" + orderState.getStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "订单状态信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单状态信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除订单状态信息 */
	public String DeleteOrderState(int stateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderState where stateId=" + stateId;
			db.executeUpdate(sqlString);
			result = "订单状态信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单状态信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据状态编号获取到订单状态信息 */
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
	/* 更新订单状态信息 */
	public String UpdateOrderState(OrderState orderState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderState set ";
			sql += "stateName='" + orderState.getStateName() + "'";
			sql += " where stateId=" + orderState.getStateId();
			db.executeUpdate(sql);
			result = "订单状态信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单状态信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
