package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Evaluate;
public class EvaluateListHandler extends DefaultHandler {
	private List<Evaluate> evaluateList = null;
	private Evaluate evaluate;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (evaluate != null) { 
            String valueString = new String(ch, start, length); 
            if ("evaluateId".equals(tempString)) 
            	evaluate.setEvaluateId(new Integer(valueString).intValue());
            else if ("productObj".equals(tempString)) 
            	evaluate.setProductObj(valueString); 
            else if ("memberObj".equals(tempString)) 
            	evaluate.setMemberObj(valueString); 
            else if ("content".equals(tempString)) 
            	evaluate.setContent(valueString); 
            else if ("evaluateTime".equals(tempString)) 
            	evaluate.setEvaluateTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Evaluate".equals(localName)&&evaluate!=null){
			evaluateList.add(evaluate);
			evaluate = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		evaluateList = new ArrayList<Evaluate>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Evaluate".equals(localName)) {
            evaluate = new Evaluate(); 
        }
        tempString = localName; 
	}

	public List<Evaluate> getEvaluateList() {
		return this.evaluateList;
	}
}
