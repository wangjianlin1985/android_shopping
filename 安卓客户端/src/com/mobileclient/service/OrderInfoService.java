package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.util.HttpUtil;

/*订单信息管理业务逻辑层*/
public class OrderInfoService {
	/* 添加订单信息 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderInfo.getOrderNo());
		params.put("memberObj", orderInfo.getMemberObj());
		params.put("orderTime", orderInfo.getOrderTime());
		params.put("totalMoney", orderInfo.getTotalMoney() + "");
		params.put("orderStateObj", orderInfo.getOrderStateObj() + "");
		params.put("buyWay", orderInfo.getBuyWay());
		params.put("realName", orderInfo.getRealName());
		params.put("telphone", orderInfo.getTelphone());
		params.put("postcode", orderInfo.getPostcode());
		params.put("address", orderInfo.getAddress());
		params.put("memo", orderInfo.getMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询订单信息 */
	public List<OrderInfo> QueryOrderInfo(OrderInfo queryConditionOrderInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=query";
		if(queryConditionOrderInfo != null) {
			urlString += "&orderNo=" + URLEncoder.encode(queryConditionOrderInfo.getOrderNo(), "UTF-8") + "";
			urlString += "&memberObj=" + URLEncoder.encode(queryConditionOrderInfo.getMemberObj(), "UTF-8") + "";
			urlString += "&orderTime=" + URLEncoder.encode(queryConditionOrderInfo.getOrderTime(), "UTF-8") + "";
			urlString += "&orderStateObj=" + queryConditionOrderInfo.getOrderStateObj();
			urlString += "&realName=" + URLEncoder.encode(queryConditionOrderInfo.getRealName(), "UTF-8") + "";
			urlString += "&telphone=" + URLEncoder.encode(queryConditionOrderInfo.getTelphone(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderInfoListHandler orderInfoListHander = new OrderInfoListHandler();
		xr.setContentHandler(orderInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderInfo> orderInfoList = orderInfoListHander.getOrderInfoList();
		return orderInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNo(object.getString("orderNo"));
				orderInfo.setMemberObj(object.getString("memberObj"));
				orderInfo.setOrderTime(object.getString("orderTime"));
				orderInfo.setTotalMoney((float) object.getDouble("totalMoney"));
				orderInfo.setOrderStateObj(object.getInt("orderStateObj"));
				orderInfo.setBuyWay(object.getString("buyWay"));
				orderInfo.setRealName(object.getString("realName"));
				orderInfo.setTelphone(object.getString("telphone"));
				orderInfo.setPostcode(object.getString("postcode"));
				orderInfo.setAddress(object.getString("address"));
				orderInfo.setMemo(object.getString("memo"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderInfoList;
	}

	/* 更新订单信息 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderInfo.getOrderNo());
		params.put("memberObj", orderInfo.getMemberObj());
		params.put("orderTime", orderInfo.getOrderTime());
		params.put("totalMoney", orderInfo.getTotalMoney() + "");
		params.put("orderStateObj", orderInfo.getOrderStateObj() + "");
		params.put("buyWay", orderInfo.getBuyWay());
		params.put("realName", orderInfo.getRealName());
		params.put("telphone", orderInfo.getTelphone());
		params.put("postcode", orderInfo.getPostcode());
		params.put("address", orderInfo.getAddress());
		params.put("memo", orderInfo.getMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除订单信息 */
	public String DeleteOrderInfo(String orderNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "订单信息信息删除失败!";
		}
	}

	/* 根据订单编号获取订单信息对象 */
	public OrderInfo GetOrderInfo(String orderNo)  {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNo(object.getString("orderNo"));
				orderInfo.setMemberObj(object.getString("memberObj"));
				orderInfo.setOrderTime(object.getString("orderTime"));
				orderInfo.setTotalMoney((float) object.getDouble("totalMoney"));
				orderInfo.setOrderStateObj(object.getInt("orderStateObj"));
				orderInfo.setBuyWay(object.getString("buyWay"));
				orderInfo.setRealName(object.getString("realName"));
				orderInfo.setTelphone(object.getString("telphone"));
				orderInfo.setPostcode(object.getString("postcode"));
				orderInfo.setAddress(object.getString("address"));
				orderInfo.setMemo(object.getString("memo"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderInfoList.size();
		if(size>0) return orderInfoList.get(0); 
		else return null; 
	}
}
