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

@Service @Transactional
public class MemberInfoDAO {

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
    public void AddMemberInfo(MemberInfo memberInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(memberInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MemberInfo> QueryMemberInfoInfo(String memberUserName,String birthday,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From MemberInfo memberInfo where 1=1";
    	if(!memberUserName.equals("")) hql = hql + " and memberInfo.memberUserName like '%" + memberUserName + "%'";
    	if(!birthday.equals("")) hql = hql + " and memberInfo.birthday like '%" + birthday + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List memberInfoList = q.list();
    	return (ArrayList<MemberInfo>) memberInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MemberInfo> QueryMemberInfoInfo(String memberUserName,String birthday) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From MemberInfo memberInfo where 1=1";
    	if(!memberUserName.equals("")) hql = hql + " and memberInfo.memberUserName like '%" + memberUserName + "%'";
    	if(!birthday.equals("")) hql = hql + " and memberInfo.birthday like '%" + birthday + "%'";
    	Query q = s.createQuery(hql);
    	List memberInfoList = q.list();
    	return (ArrayList<MemberInfo>) memberInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MemberInfo> QueryAllMemberInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From MemberInfo";
        Query q = s.createQuery(hql);
        List memberInfoList = q.list();
        return (ArrayList<MemberInfo>) memberInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String memberUserName,String birthday) {
        Session s = factory.getCurrentSession();
        String hql = "From MemberInfo memberInfo where 1=1";
        if(!memberUserName.equals("")) hql = hql + " and memberInfo.memberUserName like '%" + memberUserName + "%'";
        if(!birthday.equals("")) hql = hql + " and memberInfo.birthday like '%" + birthday + "%'";
        Query q = s.createQuery(hql);
        List memberInfoList = q.list();
        recordNumber = memberInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public MemberInfo GetMemberInfoByMemberUserName(String memberUserName) {
        Session s = factory.getCurrentSession();
        MemberInfo memberInfo = (MemberInfo)s.get(MemberInfo.class, memberUserName);
        return memberInfo;
    }

    /*����MemberInfo��Ϣ*/
    public void UpdateMemberInfo(MemberInfo memberInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(memberInfo);
    }

    /*ɾ��MemberInfo��Ϣ*/
    public void DeleteMemberInfo (String memberUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object memberInfo = s.load(MemberInfo.class, memberUserName);
        s.delete(memberInfo);
    }

}
