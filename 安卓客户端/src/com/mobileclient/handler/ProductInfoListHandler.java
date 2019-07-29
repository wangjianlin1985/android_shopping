package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ProductInfo;
public class ProductInfoListHandler extends DefaultHandler {
	private List<ProductInfo> productInfoList = null;
	private ProductInfo productInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (productInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("productNo".equals(tempString)) 
            	productInfo.setProductNo(valueString); 
            else if ("productClassObj".equals(tempString)) 
            	productInfo.setProductClassObj(new Integer(valueString).intValue());
            else if ("productName".equals(tempString)) 
            	productInfo.setProductName(valueString); 
            else if ("productPhoto".equals(tempString)) 
            	productInfo.setProductPhoto(valueString); 
            else if ("productPrice".equals(tempString)) 
            	productInfo.setProductPrice(new Float(valueString).floatValue());
            else if ("productCount".equals(tempString)) 
            	productInfo.setProductCount(new Integer(valueString).intValue());
            else if ("recommendFlag".equals(tempString)) 
            	productInfo.setRecommendFlag(valueString); 
            else if ("hotNum".equals(tempString)) 
            	productInfo.setHotNum(new Integer(valueString).intValue());
            else if ("onlineDate".equals(tempString)) 
            	productInfo.setOnlineDate(Timestamp.valueOf(valueString));
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ProductInfo".equals(localName)&&productInfo!=null){
			productInfoList.add(productInfo);
			productInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		productInfoList = new ArrayList<ProductInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ProductInfo".equals(localName)) {
            productInfo = new ProductInfo(); 
        }
        tempString = localName; 
	}

	public List<ProductInfo> getProductInfoList() {
		return this.productInfoList;
	}
}
