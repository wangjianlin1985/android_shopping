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
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.domain.ProductInfo;
import com.chengxusheji.domain.OrderDetail;

@Service @Transactional
public class OrderDetailDAO {

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
    public void AddOrderDetail(OrderDetail orderDetail) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(orderDetail);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderDetail> QueryOrderDetailInfo(OrderInfo orderObj,ProductInfo productObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderDetail orderDetail where 1=1";
    	if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderDetail.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and orderDetail.productObj.productNo='" + productObj.getProductNo() + "'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List orderDetailList = q.list();
    	return (ArrayList<OrderDetail>) orderDetailList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderDetail> QueryOrderDetailInfo(OrderInfo orderObj,ProductInfo productObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderDetail orderDetail where 1=1";
    	if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderDetail.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and orderDetail.productObj.productNo='" + productObj.getProductNo() + "'";
    	Query q = s.createQuery(hql);
    	List orderDetailList = q.list();
    	return (ArrayList<OrderDetail>) orderDetailList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderDetail> QueryAllOrderDetailInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From OrderDetail";
        Query q = s.createQuery(hql);
        List orderDetailList = q.list();
        return (ArrayList<OrderDetail>) orderDetailList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(OrderInfo orderObj,ProductInfo productObj) {
        Session s = factory.getCurrentSession();
        String hql = "From OrderDetail orderDetail where 1=1";
        if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderDetail.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
        if(null != productObj && !productObj.getProductNo().equals("")) hql += " and orderDetail.productObj.productNo='" + productObj.getProductNo() + "'";
        Query q = s.createQuery(hql);
        List orderDetailList = q.list();
        recordNumber = orderDetailList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public OrderDetail GetOrderDetailByDetailId(int detailId) {
        Session s = factory.getCurrentSession();
        OrderDetail orderDetail = (OrderDetail)s.get(OrderDetail.class, detailId);
        return orderDetail;
    }

    /*����OrderDetail��Ϣ*/
    public void UpdateOrderDetail(OrderDetail orderDetail) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(orderDetail);
    }

    /*ɾ��OrderDetail��Ϣ*/
    public void DeleteOrderDetail (int detailId) throws Exception {
        Session s = factory.getCurrentSession();
        Object orderDetail = s.load(OrderDetail.class, detailId);
        s.delete(orderDetail);
    }

}
