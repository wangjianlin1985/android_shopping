package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.MemberInfo;
public class MemberInfoListHandler extends DefaultHandler {
	private List<MemberInfo> memberInfoList = null;
	private MemberInfo memberInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (memberInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("memberUserName".equals(tempString)) 
            	memberInfo.setMemberUserName(valueString); 
            else if ("password".equals(tempString)) 
            	memberInfo.setPassword(valueString); 
            else if ("realName".equals(tempString)) 
            	memberInfo.setRealName(valueString); 
            else if ("sex".equals(tempString)) 
            	memberInfo.setSex(valueString); 
            else if ("birthday".equals(tempString)) 
            	memberInfo.setBirthday(Timestamp.valueOf(valueString));
            else if ("telephone".equals(tempString)) 
            	memberInfo.setTelephone(valueString); 
            else if ("email".equals(tempString)) 
            	memberInfo.setEmail(valueString); 
            else if ("qq".equals(tempString)) 
            	memberInfo.setQq(valueString); 
            else if ("address".equals(tempString)) 
            	memberInfo.setAddress(valueString); 
            else if ("photo".equals(tempString)) 
            	memberInfo.setPhoto(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("MemberInfo".equals(localName)&&memberInfo!=null){
			memberInfoList.add(memberInfo);
			memberInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		memberInfoList = new ArrayList<MemberInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("MemberInfo".equals(localName)) {
            memberInfo = new MemberInfo(); 
        }
        tempString = localName; 
	}

	public List<MemberInfo> getMemberInfoList() {
		return this.memberInfoList;
	}
}
