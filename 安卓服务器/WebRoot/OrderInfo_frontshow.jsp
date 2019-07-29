<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.MemberInfo" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的MemberInfo信息
    List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    //获取所有的OrderState信息
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

%>
<HTML><HEAD><TITLE>查看订单信息</TITLE>
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
    <td width=30%>订单编号:</td>
    <td width=70%><%=orderInfo.getOrderNo() %></td>
  </tr>

  <tr>
    <td width=30%>下单会员:</td>
    <td width=70%>
      <%=orderInfo.getMemberObj().getMemberUserName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>下单时间:</td>
    <td width=70%><%=orderInfo.getOrderTime() %></td>
  </tr>

  <tr>
    <td width=30%>订单总金额:</td>
    <td width=70%><%=orderInfo.getTotalMoney() %></td>
  </tr>

  <tr>
    <td width=30%>订单状态:</td>
    <td width=70%>
      <%=orderInfo.getOrderStateObj().getStateName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>付款方式:</td>
    <td width=70%><%=orderInfo.getBuyWay() %></td>
  </tr>

  <tr>
    <td width=30%>收货人姓名:</td>
    <td width=70%><%=orderInfo.getRealName() %></td>
  </tr>

  <tr>
    <td width=30%>收货人电话:</td>
    <td width=70%><%=orderInfo.getTelphone() %></td>
  </tr>

  <tr>
    <td width=30%>邮政编码:</td>
    <td width=70%><%=orderInfo.getPostcode() %></td>
  </tr>

  <tr>
    <td width=30%>收货地址:</td>
    <td width=70%><%=orderInfo.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><%=orderInfo.getMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
