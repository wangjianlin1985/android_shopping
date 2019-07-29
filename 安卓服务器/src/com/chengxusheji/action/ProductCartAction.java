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
import com.chengxusheji.dao.ProductCartDAO;
import com.chengxusheji.domain.ProductCart;
import com.chengxusheji.dao.MemberInfoDAO;
import com.chengxusheji.domain.MemberInfo;
import com.chengxusheji.dao.ProductInfoDAO;
import com.chengxusheji.domain.ProductInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ProductCartAction extends BaseAction {

    /*界面层需要查询的属性: 用户名*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
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

    private int cartId;
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    public int getCartId() {
        return cartId;
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
    @Resource ProductInfoDAO productInfoDAO;
    @Resource ProductCartDAO productCartDAO;

    /*待操作的ProductCart对象*/
    private ProductCart productCart;
    public void setProductCart(ProductCart productCart) {
        this.productCart = productCart;
    }
    public ProductCart getProductCart() {
        return this.productCart;
    }

    /*跳转到添加ProductCart视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的MemberInfo信息*/
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        /*查询所有的ProductInfo信息*/
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "add_view";
    }

    /*添加ProductCart信息*/
    @SuppressWarnings("deprecation")
    public String AddProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(productCart.getMemberObj().getMemberUserName());
            productCart.setMemberObj(memberObj);
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(productCart.getProductObj().getProductNo());
            productCart.setProductObj(productObj);
            productCartDAO.AddProductCart(productCart);
            ctx.put("message",  java.net.URLEncoder.encode("ProductCart添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart添加失败!"));
            return "error";
        }
    }

    /*查询ProductCart信息*/
    public String QueryProductCart() {
        if(currentPage == 0) currentPage = 1;
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        productCartDAO.CalculateTotalPageAndRecordNumber(memberObj, productObj);
        /*获取到总的页码数目*/
        totalPage = productCartDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productCartDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productCartList",  productCartList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("memberObj", memberObj);
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("productObj", productObj);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryProductCartOutputToExcel() { 
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj,productObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ProductCart信息记录"; 
        String[] headers = { "记录编号","用户名","商品名称","商品单价","商品数量"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productCartList.size();i++) {
        	ProductCart productCart = productCartList.get(i); 
        	dataset.add(new String[]{productCart.getCartId() + "",productCart.getMemberObj().getMemberUserName(),
productCart.getProductObj().getProductName(),
productCart.getPrice() + "",productCart.getCount() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"ProductCart.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询ProductCart信息*/
    public String FrontQueryProductCart() {
        if(currentPage == 0) currentPage = 1;
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj, productObj, currentPage);
        /*计算总的页数和总的记录数*/
        productCartDAO.CalculateTotalPageAndRecordNumber(memberObj, productObj);
        /*获取到总的页码数目*/
        totalPage = productCartDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = productCartDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productCartList",  productCartList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("memberObj", memberObj);
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("productObj", productObj);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "front_query_view";
    }

    /*查询要修改的ProductCart信息*/
    public String ModifyProductCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键cartId获取ProductCart对象*/
        ProductCart productCart = productCartDAO.GetProductCartByCartId(cartId);

        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("productCart",  productCart);
        return "modify_view";
    }

    /*查询要修改的ProductCart信息*/
    public String FrontShowProductCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键cartId获取ProductCart对象*/
        ProductCart productCart = productCartDAO.GetProductCartByCartId(cartId);

        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("productCart",  productCart);
        return "front_show_view";
    }

    /*更新修改ProductCart信息*/
    public String ModifyProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(productCart.getMemberObj().getMemberUserName());
            productCart.setMemberObj(memberObj);
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(productCart.getProductObj().getProductNo());
            productCart.setProductObj(productObj);
            productCartDAO.UpdateProductCart(productCart);
            ctx.put("message",  java.net.URLEncoder.encode("ProductCart信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart信息更新失败!"));
            return "error";
       }
   }

    /*删除ProductCart信息*/
    public String DeleteProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productCartDAO.DeleteProductCart(cartId);
            ctx.put("message",  java.net.URLEncoder.encode("ProductCart删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart删除失败!"));
            return "error";
        }
    }

}
