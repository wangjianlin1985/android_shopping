package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.MemberInfo;
import com.mobileclient.util.HttpUtil;

/*会员信息管理业务逻辑层*/
public class MemberInfoService {
	/* 添加会员信息 */
	public String AddMemberInfo(MemberInfo memberInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberUserName", memberInfo.getMemberUserName());
		params.put("password", memberInfo.getPassword());
		params.put("realName", memberInfo.getRealName());
		params.put("sex", memberInfo.getSex());
		params.put("birthday", memberInfo.getBirthday().toString());
		params.put("telephone", memberInfo.getTelephone());
		params.put("email", memberInfo.getEmail());
		params.put("qq", memberInfo.getQq());
		params.put("address", memberInfo.getAddress());
		params.put("photo", memberInfo.getPhoto());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MemberInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询会员信息 */
	public List<MemberInfo> QueryMemberInfo(MemberInfo queryConditionMemberInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "MemberInfoServlet?action=query";
		if(queryConditionMemberInfo != null) {
			urlString += "&memberUserName=" + URLEncoder.encode(queryConditionMemberInfo.getMemberUserName(), "UTF-8") + "";
			if(queryConditionMemberInfo.getBirthday() != null) {
				urlString += "&birthday=" + URLEncoder.encode(queryConditionMemberInfo.getBirthday().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		MemberInfoListHandler memberInfoListHander = new MemberInfoListHandler();
		xr.setContentHandler(memberInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<MemberInfo> memberInfoList = memberInfoListHander.getMemberInfoList();
		return memberInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<MemberInfo> memberInfoList = new ArrayList<MemberInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				MemberInfo memberInfo = new MemberInfo();
				memberInfo.setMemberUserName(object.getString("memberUserName"));
				memberInfo.setPassword(object.getString("password"));
				memberInfo.setRealName(object.getString("realName"));
				memberInfo.setSex(object.getString("sex"));
				memberInfo.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				memberInfo.setTelephone(object.getString("telephone"));
				memberInfo.setEmail(object.getString("email"));
				memberInfo.setQq(object.getString("qq"));
				memberInfo.setAddress(object.getString("address"));
				memberInfo.setPhoto(object.getString("photo"));
				memberInfoList.add(memberInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberInfoList;
	}

	/* 更新会员信息 */
	public String UpdateMemberInfo(MemberInfo memberInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberUserName", memberInfo.getMemberUserName());
		params.put("password", memberInfo.getPassword());
		params.put("realName", memberInfo.getRealName());
		params.put("sex", memberInfo.getSex());
		params.put("birthday", memberInfo.getBirthday().toString());
		params.put("telephone", memberInfo.getTelephone());
		params.put("email", memberInfo.getEmail());
		params.put("qq", memberInfo.getQq());
		params.put("address", memberInfo.getAddress());
		params.put("photo", memberInfo.getPhoto());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MemberInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除会员信息 */
	public String DeleteMemberInfo(String memberUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberUserName", memberUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MemberInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "会员信息信息删除失败!";
		}
	}

	/* 根据会员用户名获取会员信息对象 */
	public MemberInfo GetMemberInfo(String memberUserName)  {
		List<MemberInfo> memberInfoList = new ArrayList<MemberInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberUserName", memberUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MemberInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				MemberInfo memberInfo = new MemberInfo();
				memberInfo.setMemberUserName(object.getString("memberUserName"));
				memberInfo.setPassword(object.getString("password"));
				memberInfo.setRealName(object.getString("realName"));
				memberInfo.setSex(object.getString("sex"));
				memberInfo.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				memberInfo.setTelephone(object.getString("telephone"));
				memberInfo.setEmail(object.getString("email"));
				memberInfo.setQq(object.getString("qq"));
				memberInfo.setAddress(object.getString("address"));
				memberInfo.setPhoto(object.getString("photo"));
				memberInfoList.add(memberInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = memberInfoList.size();
		if(size>0) return memberInfoList.get(0); 
		else return null; 
	}
}
