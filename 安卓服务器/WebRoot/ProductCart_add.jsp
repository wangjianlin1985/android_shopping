<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.MemberInfo" %>
<%@ page import="com.chengxusheji.domain.ProductInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�MemberInfo��Ϣ
    List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    //��ȡ���е�ProductInfo��Ϣ
    List<ProductInfo> productInfoList = (List<ProductInfo>)request.getAttribute("productInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�����Ʒ���ﳵ</TITLE> 
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
    <s:form action="ProductCart/ProductCart_AddProductCart.action" method="post" id="productCartAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>�û���:</td>
    <td width=70%>
      <select name="productCart.memberObj.memberUserName">
      <%
        for(MemberInfo memberInfo:memberInfoList) {
      %>
          <option value='<%=memberInfo.getMemberUserName() %>'><%=memberInfo.getMemberUserName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ʒ����:</td>
    <td width=70%>
      <select name="productCart.productObj.productNo">
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
    <td width=30%>��Ʒ����:</td>
    <td width=70%><input id="productCart.price" name="productCart.price" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>��Ʒ����:</td>
    <td width=70%><input id="productCart.count" name="productCart.count" type="text" size="8" /></td>
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
