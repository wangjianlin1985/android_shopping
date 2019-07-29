package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.OrderDetail;
public class OrderDetailListHandler extends DefaultHandler {
	private List<OrderDetail> orderDetailList = null;
	private OrderDetail orderDetail;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (orderDetail != null) { 
            String valueString = new String(ch, start, length); 
            if ("detailId".equals(tempString)) 
            	orderDetail.setDetailId(new Integer(valueString).intValue());
            else if ("orderObj".equals(tempString)) 
            	orderDetail.setOrderObj(valueString); 
            else if ("productObj".equals(tempString)) 
            	orderDetail.setProductObj(valueString); 
            else if ("price".equals(tempString)) 
            	orderDetail.setPrice(new Float(valueString).floatValue());
            else if ("count".equals(tempString)) 
            	orderDetail.setCount(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("OrderDetail".equals(localName)&&orderDetail!=null){
			orderDetailList.add(orderDetail);
			orderDetail = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		orderDetailList = new ArrayList<OrderDetail>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("OrderDetail".equals(localName)) {
            orderDetail = new OrderDetail(); 
        }
        tempString = localName; 
	}

	public List<OrderDetail> getOrderDetailList() {
		return this.orderDetailList;
	}
}
