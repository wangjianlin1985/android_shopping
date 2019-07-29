package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ProductCart;
public class ProductCartListHandler extends DefaultHandler {
	private List<ProductCart> productCartList = null;
	private ProductCart productCart;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (productCart != null) { 
            String valueString = new String(ch, start, length); 
            if ("cartId".equals(tempString)) 
            	productCart.setCartId(new Integer(valueString).intValue());
            else if ("memberObj".equals(tempString)) 
            	productCart.setMemberObj(valueString); 
            else if ("productObj".equals(tempString)) 
            	productCart.setProductObj(valueString); 
            else if ("price".equals(tempString)) 
            	productCart.setPrice(new Float(valueString).floatValue());
            else if ("count".equals(tempString)) 
            	productCart.setCount(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ProductCart".equals(localName)&&productCart!=null){
			productCartList.add(productCart);
			productCart = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		productCartList = new ArrayList<ProductCart>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ProductCart".equals(localName)) {
            productCart = new ProductCart(); 
        }
        tempString = localName; 
	}

	public List<ProductCart> getProductCartList() {
		return this.productCartList;
	}
}
