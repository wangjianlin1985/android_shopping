package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.YesOrNo;
import com.mobileclient.util.HttpUtil;

/*是否信息管理业务逻辑层*/
public class YesOrNoService {
	/* 添加是否信息 */
	public String AddYesOrNo(YesOrNo yesOrNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", yesOrNo.getId());
		params.put("name", yesOrNo.getName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YesOrNoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询是否信息 */
	public List<YesOrNo> QueryYesOrNo(YesOrNo queryConditionYesOrNo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "YesOrNoServlet?action=query";
		if(queryConditionYesOrNo != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		YesOrNoListHandler yesOrNoListHander = new YesOrNoListHandler();
		xr.setContentHandler(yesOrNoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<YesOrNo> yesOrNoList = yesOrNoListHander.getYesOrNoList();
		return yesOrNoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<YesOrNo> yesOrNoList = new ArrayList<YesOrNo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				YesOrNo yesOrNo = new YesOrNo();
				yesOrNo.setId(object.getString("id"));
				yesOrNo.setName(object.getString("name"));
				yesOrNoList.add(yesOrNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yesOrNoList;
	}

	/* 更新是否信息 */
	public String UpdateYesOrNo(YesOrNo yesOrNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", yesOrNo.getId());
		params.put("name", yesOrNo.getName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YesOrNoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除是否信息 */
	public String DeleteYesOrNo(String id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YesOrNoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "是否信息信息删除失败!";
		}
	}

	/* 根据是否编号获取是否信息对象 */
	public YesOrNo GetYesOrNo(String id)  {
		List<YesOrNo> yesOrNoList = new ArrayList<YesOrNo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YesOrNoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				YesOrNo yesOrNo = new YesOrNo();
				yesOrNo.setId(object.getString("id"));
				yesOrNo.setName(object.getString("name"));
				yesOrNoList.add(yesOrNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = yesOrNoList.size();
		if(size>0) return yesOrNoList.get(0); 
		else return null; 
	}
}
