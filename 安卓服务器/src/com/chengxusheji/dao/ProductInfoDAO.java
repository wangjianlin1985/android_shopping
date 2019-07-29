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
import com.chengxusheji.domain.ProductClass;
import com.chengxusheji.domain.YesOrNo;
import com.chengxusheji.domain.ProductInfo;

@Service @Transactional
public class ProductInfoDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddProductInfo(ProductInfo productInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(productInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductInfo> QueryProductInfoInfo(String productNo,ProductClass productClassObj,String productName,YesOrNo recommendFlag,String onlineDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ProductInfo productInfo where 1=1";
    	if(!productNo.equals("")) hql = hql + " and productInfo.productNo like '%" + productNo + "%'";
    	if(null != productClassObj && productClassObj.getClassId()!=0) hql += " and productInfo.productClassObj.classId=" + productClassObj.getClassId();
    	if(!productName.equals("")) hql = hql + " and productInfo.productName like '%" + productName + "%'";
    	if(null != recommendFlag && !recommendFlag.getId().equals("")) hql += " and productInfo.recommendFlag.id='" + recommendFlag.getId() + "'";
    	if(!onlineDate.equals("")) hql = hql + " and productInfo.onlineDate like '%" + onlineDate + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List productInfoList = q.list();
    	return (ArrayList<ProductInfo>) productInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductInfo> QueryProductInfoInfo(String productNo,ProductClass productClassObj,String productName,YesOrNo recommendFlag,String onlineDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ProductInfo productInfo where 1=1";
    	if(!productNo.equals("")) hql = hql + " and productInfo.productNo like '%" + productNo + "%'";
    	if(null != productClassObj && productClassObj.getClassId()!=0) hql += " and productInfo.productClassObj.classId=" + productClassObj.getClassId();
    	if(!productName.equals("")) hql = hql + " and productInfo.productName like '%" + productName + "%'";
    	if(null != recommendFlag && !recommendFlag.getId().equals("")) hql += " and productInfo.recommendFlag.id='" + recommendFlag.getId() + "'";
    	if(!onlineDate.equals("")) hql = hql + " and productInfo.onlineDate like '%" + onlineDate + "%'";
    	Query q = s.createQuery(hql);
    	List productInfoList = q.list();
    	return (ArrayList<ProductInfo>) productInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductInfo> QueryAllProductInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ProductInfo";
        Query q = s.createQuery(hql);
        List productInfoList = q.list();
        return (ArrayList<ProductInfo>) productInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String productNo,ProductClass productClassObj,String productName,YesOrNo recommendFlag,String onlineDate) {
        Session s = factory.getCurrentSession();
        String hql = "From ProductInfo productInfo where 1=1";
        if(!productNo.equals("")) hql = hql + " and productInfo.productNo like '%" + productNo + "%'";
        if(null != productClassObj && productClassObj.getClassId()!=0) hql += " and productInfo.productClassObj.classId=" + productClassObj.getClassId();
        if(!productName.equals("")) hql = hql + " and productInfo.productName like '%" + productName + "%'";
        if(null != recommendFlag && !recommendFlag.getId().equals("")) hql += " and productInfo.recommendFlag.id='" + recommendFlag.getId() + "'";
        if(!onlineDate.equals("")) hql = hql + " and productInfo.onlineDate like '%" + onlineDate + "%'";
        Query q = s.createQuery(hql);
        List productInfoList = q.list();
        recordNumber = productInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ProductInfo GetProductInfoByProductNo(String productNo) {
        Session s = factory.getCurrentSession();
        ProductInfo productInfo = (ProductInfo)s.get(ProductInfo.class, productNo);
        return productInfo;
    }

    /*����ProductInfo��Ϣ*/
    public void UpdateProductInfo(ProductInfo productInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(productInfo);
    }

    /*ɾ��ProductInfo��Ϣ*/
    public void DeleteProductInfo (String productNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object productInfo = s.load(ProductInfo.class, productNo);
        s.delete(productInfo);
    }

}
