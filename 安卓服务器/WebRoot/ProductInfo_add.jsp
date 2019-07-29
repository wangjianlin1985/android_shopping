<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.ProductClass" %>
<%@ page import="com.chengxusheji.domain.YesOrNo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的ProductClass信息
    List<ProductClass> productClassList = (List<ProductClass>)request.getAttribute("productClassList");
    //获取所有的YesOrNo信息
    List<YesOrNo> yesOrNoList = (List<YesOrNo>)request.getAttribute("yesOrNoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加商品信息</TITLE> 
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
    var productNo = document.getElementById("productInfo.productNo").value;
    if(productNo=="") {
        alert('请输入商品编号!');
        return false;
    }
    var productName = document.getElementById("productInfo.productName").value;
    if(productName=="") {
        alert('请输入商品名称!');
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
    <s:form action="ProductInfo/ProductInfo_AddProductInfo.action" method="post" id="productInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>商品编号:</td>
    <td width=70%><input id="productInfo.productNo" name="productInfo.productNo" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>商品类别:</td>
    <td width=70%>
      <select name="productInfo.productClassObj.classId">
      <%
        for(ProductClass productClass:productClassList) {
      %>
          <option value='<%=productClass.getClassId() %>'><%=productClass.getClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>商品名称:</td>
    <td width=70%><input id="productInfo.productName" name="productInfo.productName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>商品图片:</td>
    <td width=70%><input id="productPhotoFile" name="productPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>商品单价:</td>
    <td width=70%><input id="productInfo.productPrice" name="productInfo.productPrice" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>商品库存:</td>
    <td width=70%><input id="productInfo.productCount" name="productInfo.productCount" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>是否推荐:</td>
    <td width=70%>
      <select name="productInfo.recommendFlag.id">
      <%
        for(YesOrNo yesOrNo:yesOrNoList) {
      %>
          <option value='<%=yesOrNo.getId() %>'><%=yesOrNo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>人气值:</td>
    <td width=70%><input id="productInfo.hotNum" name="productInfo.hotNum" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>上架日期:</td>
    <td width=70%><input type="text" readonly id="productInfo.onlineDate"  name="productInfo.onlineDate" onclick="setDay(this);"/></td>
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
