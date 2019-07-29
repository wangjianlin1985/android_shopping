package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.OrderDetail;
import com.mobileclient.util.HttpUtil;

/*订单明细信息管理业务逻辑层*/
public class OrderDetailService {
	/* 添加订单明细信息 */
	public String AddOrderDetail(OrderDetail orderDetail) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detailId", orderDetail.getDetailId() + "");
		params.put("orderObj", orderDetail.getOrderObj());
		params.put("productObj", orderDetail.getProductObj());
		params.put("price", orderDetail.getPrice() + "");
		params.put("count", orderDetail.getCount() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderDetailServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询订单明细信息 */
	public List<OrderDetail> QueryOrderDetail(OrderDetail queryConditionOrderDetail) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderDetailServlet?action=query";
		if(queryConditionOrderDetail != null) {
			urlString += "&orderObj=" + URLEncoder.encode(queryConditionOrderDetail.getOrderObj(), "UTF-8") + "";
			urlString += "&productObj=" + URLEncoder.encode(queryConditionOrderDetail.getProductObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderDetailListHandler orderDetailListHander = new OrderDetailListHandler();
		xr.setContentHandler(orderDetailListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderDetail> orderDetailList = orderDetailListHander.getOrderDetailList();
		return orderDetailList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setDetailId(object.getInt("detailId"));
				orderDetail.setOrderObj(object.getString("orderObj"));
				orderDetail.setProductObj(object.getString("productObj"));
				orderDetail.setPrice((float) object.getDouble("price"));
				orderDetail.setCount(object.getInt("count"));
				orderDetailList.add(orderDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderDetailList;
	}

	/* 更新订单明细信息 */
	public String UpdateOrderDetail(OrderDetail orderDetail) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detailId", orderDetail.getDetailId() + "");
		params.put("orderObj", orderDetail.getOrderObj());
		params.put("productObj", orderDetail.getProductObj());
		params.put("price", orderDetail.getPrice() + "");
		params.put("count", orderDetail.getCount() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderDetailServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除订单明细信息 */
	public String DeleteOrderDetail(int detailId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detailId", detailId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderDetailServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "订单明细信息信息删除失败!";
		}
	}

	/* 根据记录编号获取订单明细信息对象 */
	public OrderDetail GetOrderDetail(int detailId)  {
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detailId", detailId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderDetailServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setDetailId(object.getInt("detailId"));
				orderDetail.setOrderObj(object.getString("orderObj"));
				orderDetail.setProductObj(object.getString("productObj"));
				orderDetail.setPrice((float) object.getDouble("price"));
				orderDetail.setCount(object.getInt("count"));
				orderDetailList.add(orderDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderDetailList.size();
		if(size>0) return orderDetailList.get(0); 
		else return null; 
	}
}
