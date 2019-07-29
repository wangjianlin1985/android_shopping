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

    /*�������Ҫ��ѯ������: �������*/
    private String orderNo;
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderNo() {
        return this.orderNo;
    }

    /*�������Ҫ��ѯ������: �µ���Ա*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
    }

    /*�������Ҫ��ѯ������: �µ�ʱ��*/
    private String orderTime;
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderTime() {
        return this.orderTime;
    }

    /*�������Ҫ��ѯ������: ����״̬*/
    private OrderState orderStateObj;
    public void setOrderStateObj(OrderState orderStateObj) {
        this.orderStateObj = orderStateObj;
    }
    public OrderState getOrderStateObj() {
        return this.orderStateObj;
    }

    /*�������Ҫ��ѯ������: �ջ�������*/
    private String realName;
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRealName() {
        return this.realName;
    }

    /*�������Ҫ��ѯ������: �ջ��˵绰*/
    private String telphone;
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getTelphone() {
        return this.telphone;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource MemberInfoDAO memberInfoDAO;
    @Resource OrderStateDAO orderStateDAO;
    @Resource OrderInfoDAO orderInfoDAO;

    /*��������OrderInfo����*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*��ת�����OrderInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�MemberInfo��Ϣ*/
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        /*��ѯ���е�OrderState��Ϣ*/
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        return "add_view";
    }

    /*���OrderInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��������Ƿ��Ѿ�����*/
        String orderNo = orderInfo.getOrderNo();
        OrderInfo db_orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);
        if(null != db_orderInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�ö�������Ѿ�����!"));
            return "error";
        }
        try {
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(orderInfo.getMemberObj().getMemberUserName());
            orderInfo.setMemberObj(memberObj);
            OrderState orderStateObj = orderStateDAO.GetOrderStateByStateId(orderInfo.getOrderStateObj().getStateId());
            orderInfo.setOrderStateObj(orderStateObj);
            orderInfoDAO.AddOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderInfo��Ϣ*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderTime == null) orderTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderTime, orderStateObj, realName, telphone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderTime, orderStateObj, realName, telphone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryOrderInfoOutputToExcel() { 
        if(orderNo == null) orderNo = "";
        if(orderTime == null) orderTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo,memberObj,orderTime,orderStateObj,realName,telphone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderInfo��Ϣ��¼"; 
        String[] headers = { "�������","�µ���Ա","�µ�ʱ��","�����ܽ��","����״̬","���ʽ","�ջ�������","�ջ��˵绰","��������","�ջ���ַ"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"OrderInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯOrderInfo��Ϣ*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderTime == null) orderTime = "";
        if(realName == null) realName = "";
        if(telphone == null) telphone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, memberObj, orderTime, orderStateObj, realName, telphone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, memberObj, orderTime, orderStateObj, realName, telphone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderNo��ȡOrderInfo����*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);

        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("orderInfo",  orderInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderNo��ȡOrderInfo����*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);

        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        List<OrderState> orderStateList = orderStateDAO.QueryAllOrderStateInfo();
        ctx.put("orderStateList", orderStateList);
        ctx.put("orderInfo",  orderInfo);
        return "front_show_view";
    }

    /*�����޸�OrderInfo��Ϣ*/
    public String ModifyOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(orderInfo.getMemberObj().getMemberUserName());
            orderInfo.setMemberObj(memberObj);
            OrderState orderStateObj = orderStateDAO.GetOrderStateByStateId(orderInfo.getOrderStateObj().getStateId());
            orderInfo.setOrderStateObj(orderStateObj);
            orderInfoDAO.UpdateOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderInfo��Ϣ*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderNo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
