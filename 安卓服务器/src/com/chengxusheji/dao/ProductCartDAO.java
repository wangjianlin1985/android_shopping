package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.MemberInfo;
import com.chengxusheji.domain.ProductInfo;
import com.chengxusheji.domain.ProductCart;

@Service @Transactional
public class ProductCartDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddProductCart(ProductCart productCart) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(productCart);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductCart> QueryProductCartInfo(MemberInfo memberObj,ProductInfo productObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ProductCart productCart where 1=1";
    	if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and productCart.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and productCart.productObj.productNo='" + productObj.getProductNo() + "'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List productCartList = q.list();
    	return (ArrayList<ProductCart>) productCartList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductCart> QueryProductCartInfo(MemberInfo memberObj,ProductInfo productObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ProductCart productCart where 1=1";
    	if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and productCart.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and productCart.productObj.productNo='" + productObj.getProductNo() + "'";
    	Query q = s.createQuery(hql);
    	List productCartList = q.list();
    	return (ArrayList<ProductCart>) productCartList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductCart> QueryAllProductCartInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ProductCart";
        Query q = s.createQuery(hql);
        List productCartList = q.list();
        return (ArrayList<ProductCart>) productCartList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(MemberInfo memberObj,ProductInfo productObj) {
        Session s = factory.getCurrentSession();
        String hql = "From ProductCart productCart where 1=1";
        if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and productCart.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
        if(null != productObj && !productObj.getProductNo().equals("")) hql += " and productCart.productObj.productNo='" + productObj.getProductNo() + "'";
        Query q = s.createQuery(hql);
        List productCartList = q.list();
        recordNumber = productCartList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ProductCart GetProductCartByCartId(int cartId) {
        Session s = factory.getCurrentSession();
        ProductCart productCart = (ProductCart)s.get(ProductCart.class, cartId);
        return productCart;
    }

    /*更新ProductCart信息*/
    public void UpdateProductCart(ProductCart productCart) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(productCart);
    }

    /*删除ProductCart信息*/
    public void DeleteProductCart (int cartId) throws Exception {
        Session s = factory.getCurrentSession();
        Object productCart = s.load(ProductCart.class, cartId);
        s.delete(productCart);
    }

}
