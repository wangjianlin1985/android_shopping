package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.MemberInfoDAO;
import com.mobileserver.domain.MemberInfo;

import org.json.JSONStringer;

public class MemberInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����Ա��Ϣҵ������*/
	private MemberInfoDAO memberInfoDAO = new MemberInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public MemberInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ��Ա��Ϣ�Ĳ�����Ϣ*/
			String memberUserName = request.getParameter("memberUserName");
			memberUserName = memberUserName == null ? "" : new String(request.getParameter(
					"memberUserName").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));

			/*����ҵ���߼���ִ�л�Ա��Ϣ��ѯ*/
			List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfo(memberUserName,birthday);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<MemberInfos>").append("\r\n");
			for (int i = 0; i < memberInfoList.size(); i++) {
				sb.append("	<MemberInfo>").append("\r\n")
				.append("		<memberUserName>")
				.append(memberInfoList.get(i).getMemberUserName())
				.append("</memberUserName>").append("\r\n")
				.append("		<password>")
				.append(memberInfoList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<realName>")
				.append(memberInfoList.get(i).getRealName())
				.append("</realName>").append("\r\n")
				.append("		<sex>")
				.append(memberInfoList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<birthday>")
				.append(memberInfoList.get(i).getBirthday())
				.append("</birthday>").append("\r\n")
				.append("		<telephone>")
				.append(memberInfoList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<email>")
				.append(memberInfoList.get(i).getEmail())
				.append("</email>").append("\r\n")
				.append("		<qq>")
				.append(memberInfoList.get(i).getQq())
				.append("</qq>").append("\r\n")
				.append("		<address>")
				.append(memberInfoList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<photo>")
				.append(memberInfoList.get(i).getPhoto())
				.append("</photo>").append("\r\n")
				.append("	</MemberInfo>").append("\r\n");
			}
			sb.append("</MemberInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(MemberInfo memberInfo: memberInfoList) {
				  stringer.object();
			  stringer.key("memberUserName").value(memberInfo.getMemberUserName());
			  stringer.key("password").value(memberInfo.getPassword());
			  stringer.key("realName").value(memberInfo.getRealName());
			  stringer.key("sex").value(memberInfo.getSex());
			  stringer.key("birthday").value(memberInfo.getBirthday());
			  stringer.key("telephone").value(memberInfo.getTelephone());
			  stringer.key("email").value(memberInfo.getEmail());
			  stringer.key("qq").value(memberInfo.getQq());
			  stringer.key("address").value(memberInfo.getAddress());
			  stringer.key("photo").value(memberInfo.getPhoto());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӻ�Ա��Ϣ����ȡ��Ա��Ϣ�������������浽�½��Ļ�Ա��Ϣ���� */ 
			MemberInfo memberInfo = new MemberInfo();
			String memberUserName = new String(request.getParameter("memberUserName").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setMemberUserName(memberUserName);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setPassword(password);
			String realName = new String(request.getParameter("realName").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setRealName(realName);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			memberInfo.setBirthday(birthday);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setEmail(email);
			String qq = new String(request.getParameter("qq").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setQq(qq);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setAddress(address);
			String photo = new String(request.getParameter("photo").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setPhoto(photo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = memberInfoDAO.AddMemberInfo(memberInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ա��Ϣ����ȡ��Ա��Ϣ�Ļ�Ա�û���*/
			String memberUserName = new String(request.getParameter("memberUserName").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = memberInfoDAO.DeleteMemberInfo(memberUserName);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���»�Ա��Ϣ֮ǰ�ȸ���memberUserName��ѯĳ����Ա��Ϣ*/
			String memberUserName = new String(request.getParameter("memberUserName").getBytes("iso-8859-1"), "UTF-8");
			MemberInfo memberInfo = memberInfoDAO.GetMemberInfo(memberUserName);

			// �ͻ��˲�ѯ�Ļ�Ա��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("memberUserName").value(memberInfo.getMemberUserName());
			  stringer.key("password").value(memberInfo.getPassword());
			  stringer.key("realName").value(memberInfo.getRealName());
			  stringer.key("sex").value(memberInfo.getSex());
			  stringer.key("birthday").value(memberInfo.getBirthday());
			  stringer.key("telephone").value(memberInfo.getTelephone());
			  stringer.key("email").value(memberInfo.getEmail());
			  stringer.key("qq").value(memberInfo.getQq());
			  stringer.key("address").value(memberInfo.getAddress());
			  stringer.key("photo").value(memberInfo.getPhoto());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���»�Ա��Ϣ����ȡ��Ա��Ϣ�������������浽�½��Ļ�Ա��Ϣ���� */ 
			MemberInfo memberInfo = new MemberInfo();
			String memberUserName = new String(request.getParameter("memberUserName").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setMemberUserName(memberUserName);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setPassword(password);
			String realName = new String(request.getParameter("realName").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setRealName(realName);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setSex(sex);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			memberInfo.setBirthday(birthday);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setEmail(email);
			String qq = new String(request.getParameter("qq").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setQq(qq);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setAddress(address);
			String photo = new String(request.getParameter("photo").getBytes("iso-8859-1"), "UTF-8");
			memberInfo.setPhoto(photo);

			/* ����ҵ���ִ�и��²��� */
			String result = memberInfoDAO.UpdateMemberInfo(memberInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
