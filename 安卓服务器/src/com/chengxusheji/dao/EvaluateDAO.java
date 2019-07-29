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
import com.chengxusheji.domain.ProductInfo;
import com.chengxusheji.domain.MemberInfo;
import com.chengxusheji.domain.Evaluate;

@Service @Transactional
public class EvaluateDAO {

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
    public void AddEvaluate(Evaluate evaluate) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(evaluate);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Evaluate> QueryEvaluateInfo(ProductInfo productObj,MemberInfo memberObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Evaluate evaluate where 1=1";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and evaluate.productObj.productNo='" + productObj.getProductNo() + "'";
    	if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and evaluate.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List evaluateList = q.list();
    	return (ArrayList<Evaluate>) evaluateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Evaluate> QueryEvaluateInfo(ProductInfo productObj,MemberInfo memberObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Evaluate evaluate where 1=1";
    	if(null != productObj && !productObj.getProductNo().equals("")) hql += " and evaluate.productObj.productNo='" + productObj.getProductNo() + "'";
    	if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and evaluate.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
    	Query q = s.createQuery(hql);
    	List evaluateList = q.list();
    	return (ArrayList<Evaluate>) evaluateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Evaluate> QueryAllEvaluateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Evaluate";
        Query q = s.createQuery(hql);
        List evaluateList = q.list();
        return (ArrayList<Evaluate>) evaluateList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(ProductInfo productObj,MemberInfo memberObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Evaluate evaluate where 1=1";
        if(null != productObj && !productObj.getProductNo().equals("")) hql += " and evaluate.productObj.productNo='" + productObj.getProductNo() + "'";
        if(null != memberObj && !memberObj.getMemberUserName().equals("")) hql += " and evaluate.memberObj.memberUserName='" + memberObj.getMemberUserName() + "'";
        Query q = s.createQuery(hql);
        List evaluateList = q.list();
        recordNumber = evaluateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Evaluate GetEvaluateByEvaluateId(int evaluateId) {
        Session s = factory.getCurrentSession();
        Evaluate evaluate = (Evaluate)s.get(Evaluate.class, evaluateId);
        return evaluate;
    }

    /*����Evaluate��Ϣ*/
    public void UpdateEvaluate(Evaluate evaluate) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(evaluate);
    }

    /*ɾ��Evaluate��Ϣ*/
    public void DeleteEvaluate (int evaluateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object evaluate = s.load(Evaluate.class, evaluateId);
        s.delete(evaluate);
    }

}
