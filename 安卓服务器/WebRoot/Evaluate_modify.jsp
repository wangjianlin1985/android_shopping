<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Evaluate" %>
<%@ page import="com.chengxusheji.domain.ProductInfo" %>
<%@ page import="com.chengxusheji.domain.MemberInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的ProductInfo信息
    List<ProductInfo> productInfoList = (List<ProductInfo>)request.getAttribute("productInfoList");
    //获取所有的MemberInfo信息
    List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    Evaluate evaluate = (Evaluate)request.getAttribute("evaluate");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改商品评价</TITLE>
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
    var content = document.getElementById("evaluate.content").value;
    if(content=="") {
        alert('请输入评价内容!');
        return false;
    }
    var evaluateTime = document.getElementById("evaluate.evaluateTime").value;
    if(evaluateTime=="") {
        alert('请输入评价时间!');
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
    <TD align="left" vAlign=top ><s:form action="Evaluate/Evaluate_ModifyEvaluate.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>评价编号:</td>
    <td width=70%><input id="evaluate.evaluateId" name="evaluate.evaluateId" type="text" value="<%=evaluate.getEvaluateId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>商品名称:</td>
    <td width=70%>
      <select name="evaluate.productObj.productNo">
      <%
        for(ProductInfo productInfo:productInfoList) {
          String selected = "";
          if(productInfo.getProductNo().equals(evaluate.getProductObj().getProductNo()))
            selected = "selected";
      %>
          <option value='<%=productInfo.getProductNo() %>' <%=selected %>><%=productInfo.getProductName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>用户名:</td>
    <td width=70%>
      <select name="evaluate.memberObj.memberUserName">
      <%
        for(MemberInfo memberInfo:memberInfoList) {
          String selected = "";
          if(memberInfo.getMemberUserName().equals(evaluate.getMemberObj().getMemberUserName()))
            selected = "selected";
      %>
          <option value='<%=memberInfo.getMemberUserName() %>' <%=selected %>><%=memberInfo.getMemberUserName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>评价内容:</td>
    <td width=70%><input id="evaluate.content" name="evaluate.content" type="text" size="50" value='<%=evaluate.getContent() %>'/></td>
  </tr>

  <tr>
    <td width=30%>评价时间:</td>
    <td width=70%><input id="evaluate.evaluateTime" name="evaluate.evaluateTime" type="text" size="20" value='<%=evaluate.getEvaluateTime() %>'/></td>
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
