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
import com.chengxusheji.domain.YesOrNo;

@Service @Transactional
public class YesOrNoDAO {

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
    public void AddYesOrNo(YesOrNo yesOrNo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(yesOrNo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<YesOrNo> QueryYesOrNoInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From YesOrNo yesOrNo where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List yesOrNoList = q.list();
    	return (ArrayList<YesOrNo>) yesOrNoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<YesOrNo> QueryYesOrNoInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From YesOrNo yesOrNo where 1=1";
    	Query q = s.createQuery(hql);
    	List yesOrNoList = q.list();
    	return (ArrayList<YesOrNo>) yesOrNoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<YesOrNo> QueryAllYesOrNoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From YesOrNo";
        Query q = s.createQuery(hql);
        List yesOrNoList = q.list();
        return (ArrayList<YesOrNo>) yesOrNoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From YesOrNo yesOrNo where 1=1";
        Query q = s.createQuery(hql);
        List yesOrNoList = q.list();
        recordNumber = yesOrNoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public YesOrNo GetYesOrNoById(String id) {
        Session s = factory.getCurrentSession();
        YesOrNo yesOrNo = (YesOrNo)s.get(YesOrNo.class, id);
        return yesOrNo;
    }

    /*更新YesOrNo信息*/
    public void UpdateYesOrNo(YesOrNo yesOrNo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(yesOrNo);
    }

    /*删除YesOrNo信息*/
    public void DeleteYesOrNo (String id) throws Exception {
        Session s = factory.getCurrentSession();
        Object yesOrNo = s.load(YesOrNo.class, id);
        s.delete(yesOrNo);
    }

}
