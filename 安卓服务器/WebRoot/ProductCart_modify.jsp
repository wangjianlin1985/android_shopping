<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ProductCart" %>
<%@ page import="com.chengxusheji.domain.MemberInfo" %>
<%@ page import="com.chengxusheji.domain.ProductInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的MemberInfo信息
    List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    //获取所有的ProductInfo信息
    List<ProductInfo> productInfoList = (List<ProductInfo>)request.getAttribute("productInfoList");
    ProductCart productCart = (ProductCart)request.getAttribute("productCart");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改商品购物车</TITLE>
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
    <TD align="left" vAlign=top ><s:form action="ProductCart/ProductCart_ModifyProductCart.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><input id="productCart.cartId" name="productCart.cartId" type="text" value="<%=productCart.getCartId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>用户名:</td>
    <td width=70%>
      <select name="productCart.memberObj.memberUserName">
      <%
        for(MemberInfo memberInfo:memberInfoList) {
          String selected = "";
          if(memberInfo.getMemberUserName().equals(productCart.getMemberObj().getMemberUserName()))
            selected = "selected";
      %>
          <option value='<%=memberInfo.getMemberUserName() %>' <%=selected %>><%=memberInfo.getMemberUserName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>商品名称:</td>
    <td width=70%>
      <select name="productCart.productObj.productNo">
      <%
        for(ProductInfo productInfo:productInfoList) {
          String selected = "";
          if(productInfo.getProductNo().equals(productCart.getProductObj().getProductNo()))
            selected = "selected";
      %>
          <option value='<%=productInfo.getProductNo() %>' <%=selected %>><%=productInfo.getProductName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>商品单价:</td>
    <td width=70%><input id="productCart.price" name="productCart.price" type="text" size="8" value='<%=productCart.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>商品数量:</td>
    <td width=70%><input id="productCart.count" name="productCart.count" type="text" size="8" value='<%=productCart.getCount() %>'/></td>
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
