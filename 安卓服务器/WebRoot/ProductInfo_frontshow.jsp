<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ProductInfo" %>
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
    ProductInfo productInfo = (ProductInfo)request.getAttribute("productInfo");

%>
<HTML><HEAD><TITLE>查看商品信息</TITLE>
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
    <td width=30%>商品编号:</td>
    <td width=70%><%=productInfo.getProductNo() %></td>
  </tr>

  <tr>
    <td width=30%>商品类别:</td>
    <td width=70%>
      <%=productInfo.getProductClassObj().getClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>商品名称:</td>
    <td width=70%><%=productInfo.getProductName() %></td>
  </tr>

  <tr>
    <td width=30%>商品图片:</td>
    <td width=70%><img src="<%=basePath %><%=productInfo.getProductPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>商品单价:</td>
    <td width=70%><%=productInfo.getProductPrice() %></td>
  </tr>

  <tr>
    <td width=30%>商品库存:</td>
    <td width=70%><%=productInfo.getProductCount() %></td>
  </tr>

  <tr>
    <td width=30%>是否推荐:</td>
    <td width=70%>
      <%=productInfo.getRecommendFlag().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>人气值:</td>
    <td width=70%><%=productInfo.getHotNum() %></td>
  </tr>

  <tr>
    <td width=30%>上架日期:</td>
        <% java.text.DateFormat onlineDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=onlineDateSDF.format(productInfo.getOnlineDate()) %></td>
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
