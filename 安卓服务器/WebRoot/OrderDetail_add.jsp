<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.ProductInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的OrderInfo信息
    List<OrderInfo> orderInfoList = (List<OrderInfo>)request.getAttribute("orderInfoList");
    //获取所有的ProductInfo信息
    List<ProductInfo> productInfoList = (List<ProductInfo>)request.getAttribute("productInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加订单明细信息</TITLE> 
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
/*验证表单*/
function checkForm() {
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
    <s:form action="OrderDetail/OrderDetail_AddOrderDetail.action" method="post" id="orderDetailAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>定单编号:</td>
    <td width=70%>
      <select name="orderDetail.orderObj.orderNo">
      <%
        for(OrderInfo orderInfo:orderInfoList) {
      %>
          <option value='<%=orderInfo.getOrderNo() %>'><%=orderInfo.getOrderNo() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>商品名称:</td>
    <td width=70%>
      <select name="orderDetail.productObj.productNo">
      <%
        for(ProductInfo productInfo:productInfoList) {
      %>
          <option value='<%=productInfo.getProductNo() %>'><%=productInfo.getProductName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>商品单价:</td>
    <td width=70%><input id="orderDetail.price" name="orderDetail.price" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>订购数量:</td>
    <td width=70%><input id="orderDetail.count" name="orderDetail.count" type="text" size="8" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
