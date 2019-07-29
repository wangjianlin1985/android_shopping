package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.YesOrNo;
public class YesOrNoListHandler extends DefaultHandler {
	private List<YesOrNo> yesOrNoList = null;
	private YesOrNo yesOrNo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (yesOrNo != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	yesOrNo.setId(valueString); 
            else if ("name".equals(tempString)) 
            	yesOrNo.setName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("YesOrNo".equals(localName)&&yesOrNo!=null){
			yesOrNoList.add(yesOrNo);
			yesOrNo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		yesOrNoList = new ArrayList<YesOrNo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("YesOrNo".equals(localName)) {
            yesOrNo = new YesOrNo(); 
        }
        tempString = localName; 
	}

	public List<YesOrNo> getYesOrNoList() {
		return this.yesOrNoList;
	}
}
