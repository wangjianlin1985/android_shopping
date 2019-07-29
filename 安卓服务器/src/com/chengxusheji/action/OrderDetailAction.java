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
import com.chengxusheji.dao.OrderDetailDAO;
import com.chengxusheji.domain.OrderDetail;
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.dao.ProductInfoDAO;
import com.chengxusheji.domain.ProductInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderDetailAction extends BaseAction {

    /*�������Ҫ��ѯ������: �������*/
    private OrderInfo orderObj;
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }
    public OrderInfo getOrderObj() {
        return this.orderObj;
    }

    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
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

    private int detailId;
    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
    public int getDetailId() {
        return detailId;
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
    @Resource OrderInfoDAO orderInfoDAO;
    @Resource ProductInfoDAO productInfoDAO;
    @Resource OrderDetailDAO orderDetailDAO;

    /*��������OrderDetail����*/
    private OrderDetail orderDetail;
    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
    public OrderDetail getOrderDetail() {
        return this.orderDetail;
    }

    /*��ת�����OrderDetail��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�OrderInfo��Ϣ*/
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        /*��ѯ���е�ProductInfo��Ϣ*/
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "add_view";
    }

    /*���OrderDetail��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try {
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderDetail.getOrderObj().getOrderNo());
            orderDetail.setOrderObj(orderObj);
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(orderDetail.getProductObj().getProductNo());
            orderDetail.setProductObj(productObj);
            orderDetailDAO.AddOrderDetail(orderDetail);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderDetail��Ϣ*/
    public String QueryOrderDetail() {
        if(currentPage == 0) currentPage = 1;
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderDetailDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderDetailDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderDetailDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderDetailList",  orderDetailList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderObj", orderObj);
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ctx.put("productObj", productObj);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryOrderDetailOutputToExcel() { 
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj,productObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderDetail��Ϣ��¼"; 
        String[] headers = { "��¼���","�������","��Ʒ����","��Ʒ����","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderDetailList.size();i++) {
        	OrderDetail orderDetail = orderDetailList.get(i); 
        	dataset.add(new String[]{orderDetail.getDetailId() + "",orderDetail.getOrderObj().getOrderNo(),
orderDetail.getProductObj().getProductName(),
orderDetail.getPrice() + "",orderDetail.getCount() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"OrderDetail.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯOrderDetail��Ϣ*/
    public String FrontQueryOrderDetail() {
        if(currentPage == 0) currentPage = 1;
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderDetailDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderDetailDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderDetailDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderDetailList",  orderDetailList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderObj", orderObj);
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ctx.put("productObj", productObj);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�OrderDetail��Ϣ*/
    public String ModifyOrderDetailQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������detailId��ȡOrderDetail����*/
        OrderDetail orderDetail = orderDetailDAO.GetOrderDetailByDetailId(detailId);

        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("orderDetail",  orderDetail);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderDetail��Ϣ*/
    public String FrontShowOrderDetailQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������detailId��ȡOrderDetail����*/
        OrderDetail orderDetail = orderDetailDAO.GetOrderDetailByDetailId(detailId);

        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("orderDetail",  orderDetail);
        return "front_show_view";
    }

    /*�����޸�OrderDetail��Ϣ*/
    public String ModifyOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try {
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderDetail.getOrderObj().getOrderNo());
            orderDetail.setOrderObj(orderObj);
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(orderDetail.getProductObj().getProductNo());
            orderDetail.setProductObj(productObj);
            orderDetailDAO.UpdateOrderDetail(orderDetail);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderDetail��Ϣ*/
    public String DeleteOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderDetailDAO.DeleteOrderDetail(detailId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetailɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetailɾ��ʧ��!"));
            return "error";
        }
    }

}
