<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӻ�Ա��Ϣ</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var memberUserName = document.getElementById("memberInfo.memberUserName").value;
    if(memberUserName=="") {
        alert('�������Ա�û���!');
        return false;
    }
    var password = document.getElementById("memberInfo.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var realName = document.getElementById("memberInfo.realName").value;
    if(realName=="") {
        alert('��������ʵ����!');
        return false;
    }
    var sex = document.getElementById("memberInfo.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="MemberInfo/MemberInfo_AddMemberInfo.action" method="post" id="memberInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��Ա�û���:</td>
    <td width=70%><input id="memberInfo.memberUserName" name="memberInfo.memberUserName" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="memberInfo.password" name="memberInfo.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ʵ����:</td>
    <td width=70%><input id="memberInfo.realName" name="memberInfo.realName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="memberInfo.sex" name="memberInfo.sex" type="text" size="2" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="memberInfo.birthday"  name="memberInfo.birthday" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="memberInfo.telephone" name="memberInfo.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ����:</td>
    <td width=70%><input id="memberInfo.email" name="memberInfo.email" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵqq:</td>
    <td width=70%><input id="memberInfo.qq" name="memberInfo.qq" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><input id="memberInfo.address" name="memberInfo.address" type="text" size="60" /></td>
  </tr>

  <tr>
    <td width=30%>��Ա��Ƭ:</td>
    <td width=70%><input id="photoFile" name="photoFile" type="file" size="50" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
