package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Notice;
import com.mobileserver.util.DB;

public class NoticeDAO {

	public List<Notice> QueryNotice(String title,Timestamp publishDate) {
		List<Notice> noticeList = new ArrayList<Notice>();
		DB db = new DB();
		String sql = "select * from Notice where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if(publishDate!=null)
			sql += " and publishDate='" + publishDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Notice notice = new Notice();
				notice.setNoticeId(rs.getInt("noticeId"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setPublishDate(rs.getTimestamp("publishDate"));
				noticeList.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return noticeList;
	}
	/* ����ϵͳ������󣬽���ϵͳ��������ҵ�� */
	public String AddNotice(Notice notice) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ϵͳ���� */
			String sqlString = "insert into Notice(title,content,publishDate) values (";
			sqlString += "'" + notice.getTitle() + "',";
			sqlString += "'" + notice.getContent() + "',";
			sqlString += "'" + notice.getPublishDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ϵͳ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ϵͳ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ϵͳ���� */
	public String DeleteNotice(int noticeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Notice where noticeId=" + noticeId;
			db.executeUpdate(sqlString);
			result = "ϵͳ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ϵͳ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��ϵͳ���� */
	public Notice GetNotice(int noticeId) {
		Notice notice = null;
		DB db = new DB();
		String sql = "select * from Notice where noticeId=" + noticeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				notice = new Notice();
				notice.setNoticeId(rs.getInt("noticeId"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setPublishDate(rs.getTimestamp("publishDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return notice;
	}
	/* ����ϵͳ���� */
	public String UpdateNotice(Notice notice) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Notice set ";
			sql += "title='" + notice.getTitle() + "',";
			sql += "content='" + notice.getContent() + "',";
			sql += "publishDate='" + notice.getPublishDate() + "'";
			sql += " where noticeId=" + notice.getNoticeId();
			db.executeUpdate(sql);
			result = "ϵͳ������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ϵͳ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
