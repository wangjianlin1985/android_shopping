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

    /*界面层需要查询的属性: 定单编号*/
    private OrderInfo orderObj;
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }
    public OrderInfo getOrderObj() {
        return this.orderObj;
    }

    /*界面层需要查询的属性: 商品名称*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
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

    private int detailId;
    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
    public int getDetailId() {
        return detailId;
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
    @Resource OrderInfoDAO orderInfoDAO;
    @Resource ProductInfoDAO productInfoDAO;
    @Resource OrderDetailDAO orderDetailDAO;

    /*待操作的OrderDetail对象*/
    private OrderDetail orderDetail;
    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
    public OrderDetail getOrderDetail() {
        return this.orderDetail;
    }

    /*跳转到添加OrderDetail视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的OrderInfo信息*/
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        /*查询所有的ProductInfo信息*/
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "add_view";
    }

    /*添加OrderDetail信息*/
    @SuppressWarnings("deprecation")
    public String AddOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try {
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderDetail.getOrderObj().getOrderNo());
            orderDetail.setOrderObj(orderObj);
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(orderDetail.getProductObj().getProductNo());
            orderDetail.setProductObj(productObj);
            orderDetailDAO.AddOrderDetail(orderDetail);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail添加失败!"));
            return "error";
        }
    }

    /*查询OrderDetail信息*/
    public String QueryOrderDetail() {
        if(currentPage == 0) currentPage = 1;
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        orderDetailDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*获取到总的页码数目*/
        totalPage = orderDetailDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryOrderDetailOutputToExcel() { 
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj,productObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderDetail信息记录"; 
        String[] headers = { "记录编号","定单编号","商品名称","商品单价","订购数量"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"OrderDetail.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询OrderDetail信息*/
    public String FrontQueryOrderDetail() {
        if(currentPage == 0) currentPage = 1;
        List<OrderDetail> orderDetailList = orderDetailDAO.QueryOrderDetailInfo(orderObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        orderDetailDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*获取到总的页码数目*/
        totalPage = orderDetailDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的OrderDetail信息*/
    public String ModifyOrderDetailQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键detailId获取OrderDetail对象*/
        OrderDetail orderDetail = orderDetailDAO.GetOrderDetailByDetailId(detailId);

        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("orderDetail",  orderDetail);
        return "modify_view";
    }

    /*查询要修改的OrderDetail信息*/
    public String FrontShowOrderDetailQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键detailId获取OrderDetail对象*/
        OrderDetail orderDetail = orderDetailDAO.GetOrderDetailByDetailId(detailId);

        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("orderDetail",  orderDetail);
        return "front_show_view";
    }

    /*更新修改OrderDetail信息*/
    public String ModifyOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try {
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderDetail.getOrderObj().getOrderNo());
            orderDetail.setOrderObj(orderObj);
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(orderDetail.getProductObj().getProductNo());
            orderDetail.setProductObj(productObj);
            orderDetailDAO.UpdateOrderDetail(orderDetail);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderDetail信息*/
    public String DeleteOrderDetail() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderDetailDAO.DeleteOrderDetail(detailId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderDetail删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderDetail删除失败!"));
            return "error";
        }
    }

}
