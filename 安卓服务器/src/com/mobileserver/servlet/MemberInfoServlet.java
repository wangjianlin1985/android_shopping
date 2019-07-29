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

	/*构造会员信息业务层对象*/
	private MemberInfoDAO memberInfoDAO = new MemberInfoDAO();

	/*默认构造函数*/
	public MemberInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询会员信息的参数信息*/
			String memberUserName = request.getParameter("memberUserName");
			memberUserName = memberUserName == null ? "" : new String(request.getParameter(
					"memberUserName").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));

			/*调用业务逻辑层执行会员信息查询*/
			List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfo(memberUserName,birthday);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加会员信息：获取会员信息参数，参数保存到新建的会员信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = memberInfoDAO.AddMemberInfo(memberInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除会员信息：获取会员信息的会员用户名*/
			String memberUserName = new String(request.getParameter("memberUserName").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = memberInfoDAO.DeleteMemberInfo(memberUserName);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新会员信息之前先根据memberUserName查询某个会员信息*/
			String memberUserName = new String(request.getParameter("memberUserName").getBytes("iso-8859-1"), "UTF-8");
			MemberInfo memberInfo = memberInfoDAO.GetMemberInfo(memberUserName);

			// 客户端查询的会员信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新会员信息：获取会员信息参数，参数保存到新建的会员信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = memberInfoDAO.UpdateMemberInfo(memberInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
