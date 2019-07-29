<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.MemberInfo" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�MemberInfo��Ϣ
    List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    //��ȡ���е�OrderState��Ϣ
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ķ�����Ϣ</TITLE>
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
    var orderNo = document.getElementById("orderInfo.orderNo").value;
    if(orderNo=="") {
        alert('�����붩�����!');
        return false;
    }
    var orderTime = document.getElementById("orderInfo.orderTime").value;
    if(orderTime=="") {
        alert('�������µ�ʱ��!');
        return false;
    }
    var realName = document.getElementById("orderInfo.realName").value;
    if(realName=="") {
        alert('�������ջ�������!');
        return false;
    }
    var telphone = document.getElementById("orderInfo.telphone").value;
    if(telphone=="") {
        alert('�������ջ��˵绰!');
        return false;
    }
    var address = document.getElementById("orderInfo.address").value;
    if(address=="") {
        alert('�������ջ���ַ!');
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
    <TD align="left" vAlign=top ><s:form action="OrderInfo/OrderInfo_ModifyOrderInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>�������:</td>
    <td width=70%><input id="orderInfo.orderNo" name="orderInfo.orderNo" type="text" value="<%=orderInfo.getOrderNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�µ���Ա:</td>
    <td width=70%>
      <select name="orderInfo.memberObj.memberUserName">
      <%
        for(MemberInfo memberInfo:memberInfoList) {
          String selected = "";
          if(memberInfo.getMemberUserName().equals(orderInfo.getMemberObj().getMemberUserName()))
            selected = "selected";
      %>
          <option value='<%=memberInfo.getMemberUserName() %>' <%=selected %>><%=memberInfo.getMemberUserName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�µ�ʱ��:</td>
    <td width=70%><input id="orderInfo.orderTime" name="orderInfo.orderTime" type="text" size="20" value='<%=orderInfo.getOrderTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�����ܽ��:</td>
    <td width=70%><input id="orderInfo.totalMoney" name="orderInfo.totalMoney" type="text" size="8" value='<%=orderInfo.getTotalMoney() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%>
      <select name="orderInfo.orderStateObj.stateId">
      <%
        for(OrderState orderState:orderStateList) {
          String selected = "";
          if(orderState.getStateId() == orderInfo.getOrderStateObj().getStateId())
            selected = "selected";
      %>
          <option value='<%=orderState.getStateId() %>' <%=selected %>><%=orderState.getStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ʽ:</td>
    <td width=70%><input id="orderInfo.buyWay" name="orderInfo.buyWay" type="text" size="20" value='<%=orderInfo.getBuyWay() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ջ�������:</td>
    <td width=70%><input id="orderInfo.realName" name="orderInfo.realName" type="text" size="20" value='<%=orderInfo.getRealName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ջ��˵绰:</td>
    <td width=70%><input id="orderInfo.telphone" name="orderInfo.telphone" type="text" size="20" value='<%=orderInfo.getTelphone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="orderInfo.postcode" name="orderInfo.postcode" type="text" size="20" value='<%=orderInfo.getPostcode() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ջ���ַ:</td>
    <td width=70%><input id="orderInfo.address" name="orderInfo.address" type="text" size="80" value='<%=orderInfo.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><input id="orderInfo.memo" name="orderInfo.memo" type="text" size="50" value='<%=orderInfo.getMemo() %>'/></td>
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
