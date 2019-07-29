package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ProductCart;
import com.mobileclient.util.HttpUtil;

/*商品购物车管理业务逻辑层*/
public class ProductCartService {
	/* 添加商品购物车 */
	public String AddProductCart(ProductCart productCart) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", productCart.getCartId() + "");
		params.put("memberObj", productCart.getMemberObj());
		params.put("productObj", productCart.getProductObj());
		params.put("price", productCart.getPrice() + "");
		params.put("count", productCart.getCount() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询商品购物车 */
	public List<ProductCart> QueryProductCart(ProductCart queryConditionProductCart) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ProductCartServlet?action=query";
		if(queryConditionProductCart != null) {
			urlString += "&memberObj=" + URLEncoder.encode(queryConditionProductCart.getMemberObj(), "UTF-8") + "";
			urlString += "&productObj=" + URLEncoder.encode(queryConditionProductCart.getProductObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ProductCartListHandler productCartListHander = new ProductCartListHandler();
		xr.setContentHandler(productCartListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ProductCart> productCartList = productCartListHander.getProductCartList();
		return productCartList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ProductCart> productCartList = new ArrayList<ProductCart>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductCart productCart = new ProductCart();
				productCart.setCartId(object.getInt("cartId"));
				productCart.setMemberObj(object.getString("memberObj"));
				productCart.setProductObj(object.getString("productObj"));
				productCart.setPrice((float) object.getDouble("price"));
				productCart.setCount(object.getInt("count"));
				productCartList.add(productCart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productCartList;
	}

	/* 更新商品购物车 */
	public String UpdateProductCart(ProductCart productCart) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", productCart.getCartId() + "");
		params.put("memberObj", productCart.getMemberObj());
		params.put("productObj", productCart.getProductObj());
		params.put("price", productCart.getPrice() + "");
		params.put("count", productCart.getCount() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除商品购物车 */
	public String DeleteProductCart(int cartId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", cartId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "商品购物车信息删除失败!";
		}
	}

	/* 根据记录编号获取商品购物车对象 */
	public ProductCart GetProductCart(int cartId)  {
		List<ProductCart> productCartList = new ArrayList<ProductCart>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", cartId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductCart productCart = new ProductCart();
				productCart.setCartId(object.getInt("cartId"));
				productCart.setMemberObj(object.getString("memberObj"));
				productCart.setProductObj(object.getString("productObj"));
				productCart.setPrice((float) object.getDouble("price"));
				productCart.setCount(object.getInt("count"));
				productCartList.add(productCart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = productCartList.size();
		if(size>0) return productCartList.get(0); 
		else return null; 
	}
}
