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

    /*�������Ҫ��ѯ������: �û���*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
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

    private int cartId;
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    public int getCartId() {
        return cartId;
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
    @Resource ProductInfoDAO productInfoDAO;
    @Resource ProductCartDAO productCartDAO;

    /*��������ProductCart����*/
    private ProductCart productCart;
    public void setProductCart(ProductCart productCart) {
        this.productCart = productCart;
    }
    public ProductCart getProductCart() {
        return this.productCart;
    }

    /*��ת�����ProductCart��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�MemberInfo��Ϣ*/
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        /*��ѯ���е�ProductInfo��Ϣ*/
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        return "add_view";
    }

    /*���ProductCart��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(productCart.getMemberObj().getMemberUserName());
            productCart.setMemberObj(memberObj);
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(productCart.getProductObj().getProductNo());
            productCart.setProductObj(productObj);
            productCartDAO.AddProductCart(productCart);
            ctx.put("message",  java.net.URLEncoder.encode("ProductCart��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart���ʧ��!"));
            return "error";
        }
    }

    /*��ѯProductCart��Ϣ*/
    public String QueryProductCart() {
        if(currentPage == 0) currentPage = 1;
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productCartDAO.CalculateTotalPageAndRecordNumber(memberObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productCartDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryProductCartOutputToExcel() { 
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj,productObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ProductCart��Ϣ��¼"; 
        String[] headers = { "��¼���","�û���","��Ʒ����","��Ʒ����","��Ʒ����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ProductCart.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯProductCart��Ϣ*/
    public String FrontQueryProductCart() {
        if(currentPage == 0) currentPage = 1;
        List<ProductCart> productCartList = productCartDAO.QueryProductCartInfo(memberObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productCartDAO.CalculateTotalPageAndRecordNumber(memberObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productCartDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�ProductCart��Ϣ*/
    public String ModifyProductCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cartId��ȡProductCart����*/
        ProductCart productCart = productCartDAO.GetProductCartByCartId(cartId);

        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("productCart",  productCart);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ProductCart��Ϣ*/
    public String FrontShowProductCartQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������cartId��ȡProductCart����*/
        ProductCart productCart = productCartDAO.GetProductCartByCartId(cartId);

        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("productCart",  productCart);
        return "front_show_view";
    }

    /*�����޸�ProductCart��Ϣ*/
    public String ModifyProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(productCart.getMemberObj().getMemberUserName());
            productCart.setMemberObj(memberObj);
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(productCart.getProductObj().getProductNo());
            productCart.setProductObj(productObj);
            productCartDAO.UpdateProductCart(productCart);
            ctx.put("message",  java.net.URLEncoder.encode("ProductCart��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCart��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ProductCart��Ϣ*/
    public String DeleteProductCart() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productCartDAO.DeleteProductCart(cartId);
            ctx.put("message",  java.net.URLEncoder.encode("ProductCartɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductCartɾ��ʧ��!"));
            return "error";
        }
    }

}
