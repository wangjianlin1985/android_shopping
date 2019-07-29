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
    List<OrderInfo> orderInfoList = (List<OrderInfo>)request.getAttribute("orderInfoList");
    //获取所有的MemberInfo信息
    List<MemberInfo> memberInfoList = (List<MemberInfo>)request.getAttribute("memberInfoList");
    MemberInfo memberObj = (MemberInfo)request.getAttribute("memberInfo");

    //获取所有的OrderState信息
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    OrderState orderStateObj = (OrderState)request.getAttribute("orderState");

    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int  recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String orderNo = (String)request.getAttribute("orderNo"); //订单编号查询关键字
    String orderTime = (String)request.getAttribute("orderTime"); //下单时间查询关键字
    String realName = (String)request.getAttribute("realName"); //收货人姓名查询关键字
    String telphone = (String)request.getAttribute("telphone"); //收货人电话查询关键字
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单信息查询</title>
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
.STYLE1 {font-size: 12px}
.STYLE3 {font-size: 12px; font-weight: bold; }
.STYLE4 {
    color: #03515d;
    font-size: 12px;
}
-->
</style>

 <script src="<%=basePath %>calendar.js"></script>
<script>
var  highlightcolor='#c1ebff';
//此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
var  clickcolor='#51b2f6';
function  changeto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
for(i=0;i<cs.length;i++){
    cs[i].style.backgroundColor=clickcolor;
}
else
for(i=0;i<cs.length;i++){
    cs[i].style.backgroundColor="";
}
}

function  changeback(){
if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
return
if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
//source.style.backgroundColor=originalcolor
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.forms[0].currentPage.value = currentPage;
    document.forms[0].action = "<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfo.action";
    document.forms[0].submit();

}

function changepage(totalPage)
{
    var pageValue=document.bookQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.orderInfoQueryForm.currentPage.value = pageValue;
    document.forms["orderInfoQueryForm"].action = "<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfo.action";
    document.orderInfoQueryForm.submit();
}

function QueryOrderInfo() {
	document.forms["orderInfoQueryForm"].action = "<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfo.action";
	document.forms["orderInfoQueryForm"].submit();
}

function OutputToExcel() {
	document.forms["orderInfoQueryForm"].action = "<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfoOutputToExcel.action";
	document.forms["orderInfoQueryForm"].submit(); 
}
</script>
</head>

<body>
<form action="<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfo.action" name="orderInfoQueryForm" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="<%=basePath %>images/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="<%=basePath %>images/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="46%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="<%=basePath %>images/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：[订单信息管理]-[订单信息查询]</td>
              </tr>
            </table></td>
            <td width="54%"><table border="0" align="right" cellpadding="0" cellspacing="0">

            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=basePath %>images/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>


  <tr>
  <td>
订单编号:<input type=text name="orderNo" value="<%=orderNo %>" />&nbsp;
下单会员：<select name="memberObj.memberUserName">
 				<option value="">不限制</option>
 				<%
 					for(MemberInfo memberInfoTemp:memberInfoList) {
 						String selected = "";
 						if(memberObj!=null && memberInfoTemp.getMemberUserName().equals(memberObj.getMemberUserName())) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=memberInfoTemp.getMemberUserName() %>"><%=memberInfoTemp.getMemberUserName() %></option>
 			   <%
 					}
 				%>
 			</select>
下单时间:<input type=text name="orderTime" value="<%=orderTime %>" />&nbsp;
订单状态：<select name="orderStateObj.stateId">
 				<option value="0">不限制</option>
 				<%
 					for(OrderState orderStateTemp:orderStateList) {
 						String selected = "";
 						if(orderStateObj!=null && orderStateTemp.getStateId() == orderStateObj.getStateId()) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=orderStateTemp.getStateId() %>"><%=orderStateTemp.getStateName() %></option>
 			   <%
 					}
 				%>
 			</select>
收货人姓名:<input type=text name="realName" value="<%=realName %>" />&nbsp;
收货人电话:<input type=text name="telphone" value="<%=telphone %>" />&nbsp;
    <input type=hidden name=currentPage value="1" />
    <input type=submit value="查询" onclick="QueryOrderInfo();" />
  </td>
</tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="<%=basePath %>images/tab_12.gif">&nbsp;</td>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
          <tr>
          <!-- 
            <td width="3%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center">
              <input type="checkbox" name="checkall" onclick="checkAll();" />
            </div></td> -->
            <td width="3%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">序号</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">订单编号</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">下单会员</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">下单时间</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">订单总金额</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">订单状态</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">付款方式</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">收货人姓名</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">收货人电话</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">邮政编码</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">收货地址</span></div></td>
            <td width="10%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">基本操作</div></td>
          </tr>
           <%
           		/*计算起始序号*/
            	int startIndex = (currentPage -1) * 3;
            	/*遍历记录*/
            	for(int i=0;i<orderInfoList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		OrderInfo orderInfo = orderInfoList.get(i); //获取到OrderInfo对象
             %>
          <tr>
            <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1">
              <div align="center"><%=currentIndex %></div>
            </div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getOrderNo() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getMemberObj().getMemberUserName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getOrderTime() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getTotalMoney() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getOrderStateObj().getStateName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getBuyWay() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getRealName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getTelphone() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getPostcode() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getAddress() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center">
            <span class="STYLE4">
            <span style="cursor:hand;" onclick="location.href='<%=basePath %>OrderInfo/OrderInfo_ModifyOrderInfoQuery.action?orderNo=<%=orderInfo.getOrderNo() %>'"><a href='#'><img src="<%=basePath %>images/edt.gif" width="16" height="16"/>编辑&nbsp; &nbsp;</a></span>
            <img src="<%=basePath %>images/del.gif" width="16" height="16"/><a href="<%=basePath  %>OrderInfo/OrderInfo_DeleteOrderInfo.action?orderNo=<%=orderInfo.getOrderNo() %>" onclick="return confirm('确定删除本OrderInfo吗?');">删除</a></span>
            </div></td>
          </tr>
          <%	} %>
        </table></td>
        <td width="8" background="images/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>

  <tr>
    <td height="35" background="<%=basePath %>images/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="<%=basePath %>images/tab_18.gif" width="12" height="35" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="STYLE4">&nbsp;&nbsp;共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页&nbsp;&nbsp;<span style="color:red;text-decoration:underline;cursor:hand" onclick="OutputToExcel();">导出当前记录到excel</span></td>
            <td><table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="40"><img src="<%=basePath %>images/first.gif" width="37" height="15" style="cursor:hand;" onclick="GoToPage(1,<%=totalPage %>);" /></td>
                  <td width="45"><img src="<%=basePath %>images/back.gif" width="43" height="15" style="cursor:hand;" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);"/></td>
                  <td width="45"><img src="<%=basePath %>images/next.gif" width="43" height="15" style="cursor:hand;" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);" /></td>
                  <td width="40"><img src="<%=basePath %>images/last.gif" width="37" height="15" style="cursor:hand;" onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);"/></td>
                  <td width="100"><div align="center"><span class="STYLE1">转到第
                    <input name="pageValue" type="text" size="4" style="height:12px; width:20px; border:1px solid #999999;" />
                    页 </span></div></td>
                  <td width="40"><img src="<%=basePath %>images/go.gif" onclick="changepage(<%=totalPage %>);" width="37" height="15" /></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=basePath %>images/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
  </form>
</body>
</html>
