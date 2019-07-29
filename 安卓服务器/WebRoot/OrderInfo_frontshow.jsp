<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.MemberInfo" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�MemberInfo��Ϣ
    List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    //��ȡ���е�OrderState��Ϣ
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

%>
<HTML><HEAD><TITLE>�鿴������Ϣ</TITLE>
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
    <td width=30%>�������:</td>
    <td width=70%><%=orderInfo.getOrderNo() %></td>
  </tr>

  <tr>
    <td width=30%>�µ���Ա:</td>
    <td width=70%>
      <%=orderInfo.getMemberObj().getMemberUserName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�µ�ʱ��:</td>
    <td width=70%><%=orderInfo.getOrderTime() %></td>
  </tr>

  <tr>
    <td width=30%>�����ܽ��:</td>
    <td width=70%><%=orderInfo.getTotalMoney() %></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%>
      <%=orderInfo.getOrderStateObj().getStateName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ʽ:</td>
    <td width=70%><%=orderInfo.getBuyWay() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ�������:</td>
    <td width=70%><%=orderInfo.getRealName() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ��˵绰:</td>
    <td width=70%><%=orderInfo.getTelphone() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=orderInfo.getPostcode() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ���ַ:</td>
    <td width=70%><%=orderInfo.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><%=orderInfo.getMemo() %></td>
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
