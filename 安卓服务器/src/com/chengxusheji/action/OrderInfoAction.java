package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.dao.MemberInfoDAO;
import com.chengxusheji.domain.MemberInfo;
import com.chengxusheji.dao.OrderStateDAO;
import com.chengxusheji.domain.OrderState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderInfoAction extends BaseAction {

    /*界面层需要查询的属性: 订单编号*/
    private String orderNo;
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderNo() {
        return this.orderNo;
    }

    /*界面层需要查询的属性: 下单会员*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
    }

    /*界面层需要查询的属性: 下单时间*/
    private String orderTime;
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderTime() {
        return this.orderTime;
    }

    /*界面层需要查询的属性: 订单状态*/
    private OrderState orderStateObj;
    public void setOrderStateObj(OrderState orderStateObj) {
        this.orderStateObj = orderStateObj;
    }
    public OrderState getOrderStateObj() {
        return this.orderStateObj;
    }

    /*界面层需要查询的属性: 收货人姓名*/
    private String realName;
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRealName() {
        return this.realName;
    }

    /*界面层需要查询的属性: 收货人电话*/
    private String telphone;
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getTelphone() {
        return this.telphone;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource MemberInfoDAO memberInfoDAO;
    @Resource OrderStateDAO orderStateDAO;
    @Resource OrderInfoDAO orderInfoDAO;

    /*待操作的OrderInfo对象*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*跳转到添加OrderInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的MemberInfo信息*/
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        /*查询所有的OrderState信息*/
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        return "add_view";
    }

    /*添加OrderInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证订单编号是否已经存在*/
        String orderNo = orderInfo.getOrderNo();
        OrderInfo db_orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);
        if(null != db_orderInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该订单编号已经存在!"));
            return "error";
        }
        try {
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(orderInfo.getMemberObj().getMemberUserName());
            orderInfo.setMemberObj(memberObj);
            OrderState orderStateObj = orderStateDAO.GetOrderStateByStateId(orderInfo.getOrderStateObj().getStateId());
            orderInfo.setOrderStateObj(orderStateObj);
            orderInfoDAO.AddOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo添加失败!"));
            return "error";
        }
    }

    /*查询OrderInfo信息*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderTime == null) orderTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderTime, orderStateObj, realName, telphone, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderTime, orderStateObj, realName, telphone);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderNo", orderNo);
        ctx.put("memberObj", memberObj);
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("orderTime", orderTime);
        ctx.put("orderStateObj", orderStateObj);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("realName", realName);
        ctx.put("telphone", telphone);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryOrderInfoOutputToExcel() { 
        if(orderNo == null) orderNo = "";
        if(orderTime == null) orderTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo,memberObj,orderTime,orderStateObj,realName,telphone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderInfo信息记录"; 
        String[] headers = { "订单编号","下单会员","下单时间","订单总金额","订单状态","付款方式","收货人姓名","收货人电话","邮政编码","收货地址"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderInfoList.size();i++) {
        	OrderInfo orderInfo = orderInfoList.get(i); 
        	dataset.add(new String[]{orderInfo.getOrderNo(),orderInfo.getMemberObj().getMemberUserName(),
orderInfo.getOrderTime(),orderInfo.getTotalMoney() + "",orderInfo.getOrderStateObj().getStateName(),
orderInfo.getBuyWay(),orderInfo.getRealName(),orderInfo.getTelphone(),orderInfo.getPostcode(),orderInfo.getAddress()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"OrderInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询OrderInfo信息*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderTime == null) orderTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderTime, orderStateObj, realName, telphone, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderTime, orderStateObj, realName, telphone);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderNo", orderNo);
        ctx.put("memberObj", memberObj);
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("orderTime", orderTime);
        ctx.put("orderStateObj", orderStateObj);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("realName", realName);
        ctx.put("telphone", telphone);
        return "front_query_view";
    }

    /*查询要修改的OrderInfo信息*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderNo获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);

        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("orderInfo",  orderInfo);
        return "modify_view";
    }

    /*查询要修改的OrderInfo信息*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderNo获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);

        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("orderInfo",  orderInfo);
        return "front_show_view";
    }

    /*更新修改OrderInfo信息*/
    public String ModifyOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(orderInfo.getMemberObj().getMemberUserName());
            orderInfo.setMemberObj(memberObj);
            OrderState orderStateObj = orderStateDAO.GetOrderStateByStateId(orderInfo.getOrderStateObj().getStateId());
            orderInfo.setOrderStateObj(orderStateObj);
            orderInfoDAO.UpdateOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderInfo信息*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderNo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo删除失败!"));
            return "error";
        }
    }

}
