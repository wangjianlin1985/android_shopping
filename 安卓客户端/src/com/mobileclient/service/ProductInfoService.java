package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ProductInfo;
import com.mobileclient.util.HttpUtil;

/*商品信息管理业务逻辑层*/
public class ProductInfoService {
	/* 添加商品信息 */
	public String AddProductInfo(ProductInfo productInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productInfo.getProductNo());
		params.put("productClassObj", productInfo.getProductClassObj() + "");
		params.put("productName", productInfo.getProductName());
		params.put("productPhoto", productInfo.getProductPhoto());
		params.put("productPrice", productInfo.getProductPrice() + "");
		params.put("productCount", productInfo.getProductCount() + "");
		params.put("recommendFlag", productInfo.getRecommendFlag());
		params.put("hotNum", productInfo.getHotNum() + "");
		params.put("onlineDate", productInfo.getOnlineDate().toString());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询商品信息 */
	public List<ProductInfo> QueryProductInfo(ProductInfo queryConditionProductInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ProductInfoServlet?action=query";
		if(queryConditionProductInfo != null) {
			urlString += "&productNo=" + URLEncoder.encode(queryConditionProductInfo.getProductNo(), "UTF-8") + "";
			urlString += "&productClassObj=" + queryConditionProductInfo.getProductClassObj();
			urlString += "&productName=" + URLEncoder.encode(queryConditionProductInfo.getProductName(), "UTF-8") + "";
			urlString += "&recommendFlag=" + URLEncoder.encode(queryConditionProductInfo.getRecommendFlag(), "UTF-8") + "";
			if(queryConditionProductInfo.getOnlineDate() != null) {
				urlString += "&onlineDate=" + URLEncoder.encode(queryConditionProductInfo.getOnlineDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ProductInfoListHandler productInfoListHander = new ProductInfoListHandler();
		xr.setContentHandler(productInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ProductInfo> productInfoList = productInfoListHander.getProductInfoList();
		return productInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductInfo productInfo = new ProductInfo();
				productInfo.setProductNo(object.getString("productNo"));
				productInfo.setProductClassObj(object.getInt("productClassObj"));
				productInfo.setProductName(object.getString("productName"));
				productInfo.setProductPhoto(object.getString("productPhoto"));
				productInfo.setProductPrice((float) object.getDouble("productPrice"));
				productInfo.setProductCount(object.getInt("productCount"));
				productInfo.setRecommendFlag(object.getString("recommendFlag"));
				productInfo.setHotNum(object.getInt("hotNum"));
				productInfo.setOnlineDate(Timestamp.valueOf(object.getString("onlineDate")));
				productInfoList.add(productInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productInfoList;
	}

	/* 更新商品信息 */
	public String UpdateProductInfo(ProductInfo productInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productInfo.getProductNo());
		params.put("productClassObj", productInfo.getProductClassObj() + "");
		params.put("productName", productInfo.getProductName());
		params.put("productPhoto", productInfo.getProductPhoto());
		params.put("productPrice", productInfo.getProductPrice() + "");
		params.put("productCount", productInfo.getProductCount() + "");
		params.put("recommendFlag", productInfo.getRecommendFlag());
		params.put("hotNum", productInfo.getHotNum() + "");
		params.put("onlineDate", productInfo.getOnlineDate().toString());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除商品信息 */
	public String DeleteProductInfo(String productNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "商品信息信息删除失败!";
		}
	}

	/* 根据商品编号获取商品信息对象 */
	public ProductInfo GetProductInfo(String productNo)  {
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("productNo", productNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductInfo productInfo = new ProductInfo();
				productInfo.setProductNo(object.getString("productNo"));
				productInfo.setProductClassObj(object.getInt("productClassObj"));
				productInfo.setProductName(object.getString("productName"));
				productInfo.setProductPhoto(object.getString("productPhoto"));
				productInfo.setProductPrice((float) object.getDouble("productPrice"));
				productInfo.setProductCount(object.getInt("productCount"));
				productInfo.setRecommendFlag(object.getString("recommendFlag"));
				productInfo.setHotNum(object.getInt("hotNum"));
				productInfo.setOnlineDate(Timestamp.valueOf(object.getString("onlineDate")));
				productInfoList.add(productInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = productInfoList.size();
		if(size>0) return productInfoList.get(0); 
		else return null; 
	}
}
