<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ProductInfo" %>
<%@ page import="com.chengxusheji.domain.ProductClass" %>
<%@ page import="com.chengxusheji.domain.YesOrNo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�ProductClass��Ϣ
    List<ProductClass> productClassList = (List<ProductClass>)request.getAttribute("productClassList");
    //��ȡ���е�YesOrNo��Ϣ
    List<YesOrNo> yesOrNoList = (List<YesOrNo>)request.getAttribute("yesOrNoList");
    ProductInfo productInfo = (ProductInfo)request.getAttribute("productInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸���Ʒ��Ϣ</TITLE>
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
    var productNo = document.getElementById("productInfo.productNo").value;
    if(productNo=="") {
        alert('��������Ʒ���!');
        return false;
    }
    var productName = document.getElementById("productInfo.productName").value;
    if(productName=="") {
        alert('��������Ʒ����!');
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
    <TD align="left" vAlign=top ><s:form action="ProductInfo/ProductInfo_ModifyProductInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��Ʒ���:</td>
    <td width=70%><input id="productInfo.productNo" name="productInfo.productNo" type="text" value="<%=productInfo.getProductNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��Ʒ���:</td>
    <td width=70%>
      <select name="productInfo.productClassObj.classId">
      <%
        for(ProductClass productClass:productClassList) {
          String selected = "";
          if(productClass.getClassId() == productInfo.getProductClassObj().getClassId())
            selected = "selected";
      %>
          <option value='<%=productClass.getClassId() %>' <%=selected %>><%=productClass.getClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ʒ����:</td>
    <td width=70%><input id="productInfo.productName" name="productInfo.productName" type="text" size="20" value='<%=productInfo.getProductName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ƷͼƬ:</td>
    <td width=70%><img src="<%=basePath %><%=productInfo.getProductPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="productInfo.productPhoto" value="<%=productInfo.getProductPhoto() %>" />
    <input id="productPhotoFile" name="productPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>��Ʒ����:</td>
    <td width=70%><input id="productInfo.productPrice" name="productInfo.productPrice" type="text" size="8" value='<%=productInfo.getProductPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��Ʒ���:</td>
    <td width=70%><input id="productInfo.productCount" name="productInfo.productCount" type="text" size="8" value='<%=productInfo.getProductCount() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ƿ��Ƽ�:</td>
    <td width=70%>
      <select name="productInfo.recommendFlag.id">
      <%
        for(YesOrNo yesOrNo:yesOrNoList) {
          String selected = "";
          if(yesOrNo.getId().equals(productInfo.getRecommendFlag().getId()))
            selected = "selected";
      %>
          <option value='<%=yesOrNo.getId() %>' <%=selected %>><%=yesOrNo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ֵ:</td>
    <td width=70%><input id="productInfo.hotNum" name="productInfo.hotNum" type="text" size="8" value='<%=productInfo.getHotNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ϼ�����:</td>
    <% DateFormat onlineDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="productInfo.onlineDate"  name="productInfo.onlineDate" onclick="setDay(this);" value='<%=onlineDateSDF.format(productInfo.getOnlineDate()) %>'/></td>
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
