<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.MemberInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    MemberInfo memberInfo = (MemberInfo)request.getAttribute("memberInfo");

%>
<HTML><HEAD><TITLE>�鿴��Ա��Ϣ</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>��Ա�û���:</td>
    <td width=70%><%=memberInfo.getMemberUserName() %></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><%=memberInfo.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>��ʵ����:</td>
    <td width=70%><%=memberInfo.getRealName() %></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><%=memberInfo.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
        <% java.text.DateFormat birthdaySDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthdaySDF.format(memberInfo.getBirthday()) %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=memberInfo.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ����:</td>
    <td width=70%><%=memberInfo.getEmail() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵqq:</td>
    <td width=70%><%=memberInfo.getQq() %></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><%=memberInfo.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>��Ա��Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=memberInfo.getPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
