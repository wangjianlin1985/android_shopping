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
import com.chengxusheji.dao.ProductInfoDAO;
import com.chengxusheji.domain.ProductInfo;
import com.chengxusheji.dao.ProductClassDAO;
import com.chengxusheji.domain.ProductClass;
import com.chengxusheji.dao.YesOrNoDAO;
import com.chengxusheji.domain.YesOrNo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ProductInfoAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�productPhoto��������*/
	private File productPhotoFile;
	private String productPhotoFileFileName;
	private String productPhotoFileContentType;
	public File getProductPhotoFile() {
		return productPhotoFile;
	}
	public void setProductPhotoFile(File productPhotoFile) {
		this.productPhotoFile = productPhotoFile;
	}
	public String getProductPhotoFileFileName() {
		return productPhotoFileFileName;
	}
	public void setProductPhotoFileFileName(String productPhotoFileFileName) {
		this.productPhotoFileFileName = productPhotoFileFileName;
	}
	public String getProductPhotoFileContentType() {
		return productPhotoFileContentType;
	}
	public void setProductPhotoFileContentType(String productPhotoFileContentType) {
		this.productPhotoFileContentType = productPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ��Ʒ���*/
    private String productNo;
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public String getProductNo() {
        return this.productNo;
    }

    /*�������Ҫ��ѯ������: ��Ʒ���*/
    private ProductClass productClassObj;
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }
    public ProductClass getProductClassObj() {
        return this.productClassObj;
    }

    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private String productName;
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return this.productName;
    }

    /*�������Ҫ��ѯ������: �Ƿ��Ƽ�*/
    private YesOrNo recommendFlag;
    public void setRecommendFlag(YesOrNo recommendFlag) {
        this.recommendFlag = recommendFlag;
    }
    public YesOrNo getRecommendFlag() {
        return this.recommendFlag;
    }

    /*�������Ҫ��ѯ������: �ϼ�����*/
    private String onlineDate;
    public void setOnlineDate(String onlineDate) {
        this.onlineDate = onlineDate;
    }
    public String getOnlineDate() {
        return this.onlineDate;
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
    @Resource ProductClassDAO productClassDAO;
    @Resource YesOrNoDAO yesOrNoDAO;
    @Resource ProductInfoDAO productInfoDAO;

    /*��������ProductInfo����*/
    private ProductInfo productInfo;
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
    public ProductInfo getProductInfo() {
        return this.productInfo;
    }

    /*��ת�����ProductInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�ProductClass��Ϣ*/
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        /*��ѯ���е�YesOrNo��Ϣ*/
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        return "add_view";
    }

    /*���ProductInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddProductInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��Ʒ����Ƿ��Ѿ�����*/
        String productNo = productInfo.getProductNo();
        ProductInfo db_productInfo = productInfoDAO.GetProductInfoByProductNo(productNo);
        if(null != db_productInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("����Ʒ����Ѿ�����!"));
            return "error";
        }
        try {
            ProductClass productClassObj = productClassDAO.GetProductClassByClassId(productInfo.getProductClassObj().getClassId());
            productInfo.setProductClassObj(productClassObj);
            YesOrNo recommendFlag = yesOrNoDAO.GetYesOrNoById(productInfo.getRecommendFlag().getId());
            productInfo.setRecommendFlag(recommendFlag);
            /*������ƷͼƬ�ϴ�*/
            String productPhotoPath = "upload/noimage.jpg"; 
       	 	if(productPhotoFile != null)
       	 		productPhotoPath = photoUpload(productPhotoFile,productPhotoFileContentType);
       	 	productInfo.setProductPhoto(productPhotoPath);
            productInfoDAO.AddProductInfo(productInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯProductInfo��Ϣ*/
    public String QueryProductInfo() {
        if(currentPage == 0) currentPage = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(onlineDate == null) onlineDate = "";
        List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfoInfo(productNo, productClassObj, productName, recommendFlag, onlineDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productInfoDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName, recommendFlag, onlineDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productInfoList",  productInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productNo", productNo);
        ctx.put("productClassObj", productClassObj);
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        ctx.put("recommendFlag", recommendFlag);
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("onlineDate", onlineDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryProductInfoOutputToExcel() { 
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(onlineDate == null) onlineDate = "";
        List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfoInfo(productNo,productClassObj,productName,recommendFlag,onlineDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ProductInfo��Ϣ��¼"; 
        String[] headers = { "��Ʒ���","��Ʒ���","��Ʒ����","��ƷͼƬ","��Ʒ����","��Ʒ���","�Ƿ��Ƽ�","����ֵ","�ϼ�����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<productInfoList.size();i++) {
        	ProductInfo productInfo = productInfoList.get(i); 
        	dataset.add(new String[]{productInfo.getProductNo(),productInfo.getProductClassObj().getClassName(),
productInfo.getProductName(),productInfo.getProductPhoto(),productInfo.getProductPrice() + "",productInfo.getProductCount() + "",productInfo.getRecommendFlag().getName(),
productInfo.getHotNum() + "",new SimpleDateFormat("yyyy-MM-dd").format(productInfo.getOnlineDate())});
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
			response.setHeader("Content-disposition","attachment; filename="+"ProductInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯProductInfo��Ϣ*/
    public String FrontQueryProductInfo() {
        if(currentPage == 0) currentPage = 1;
        if(productNo == null) productNo = "";
        if(productName == null) productName = "";
        if(onlineDate == null) onlineDate = "";
        List<ProductInfo> productInfoList = productInfoDAO.QueryProductInfoInfo(productNo, productClassObj, productName, recommendFlag, onlineDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        productInfoDAO.CalculateTotalPageAndRecordNumber(productNo, productClassObj, productName, recommendFlag, onlineDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = productInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = productInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("productInfoList",  productInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productNo", productNo);
        ctx.put("productClassObj", productClassObj);
        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        ctx.put("productName", productName);
        ctx.put("recommendFlag", recommendFlag);
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("onlineDate", onlineDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ProductInfo��Ϣ*/
    public String ModifyProductInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������productNo��ȡProductInfo����*/
        ProductInfo productInfo = productInfoDAO.GetProductInfoByProductNo(productNo);

        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("productInfo",  productInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ProductInfo��Ϣ*/
    public String FrontShowProductInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������productNo��ȡProductInfo����*/
        ProductInfo productInfo = productInfoDAO.GetProductInfoByProductNo(productNo);

        List<ProductClass> productClassList = productClassDAO.QueryAllProductClassInfo();
        ctx.put("productClassList", productClassList);
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("productInfo",  productInfo);
        return "front_show_view";
    }

    /*�����޸�ProductInfo��Ϣ*/
    public String ModifyProductInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ProductClass productClassObj = productClassDAO.GetProductClassByClassId(productInfo.getProductClassObj().getClassId());
            productInfo.setProductClassObj(productClassObj);
            YesOrNo recommendFlag = yesOrNoDAO.GetYesOrNoById(productInfo.getRecommendFlag().getId());
            productInfo.setRecommendFlag(recommendFlag);
            /*������ƷͼƬ�ϴ�*/
            if(productPhotoFile != null) {
            	String productPhotoPath = photoUpload(productPhotoFile,productPhotoFileContentType);
            	productInfo.setProductPhoto(productPhotoPath);
            }
            productInfoDAO.UpdateProductInfo(productInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ProductInfo��Ϣ*/
    public String DeleteProductInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            productInfoDAO.DeleteProductInfo(productNo);
            ctx.put("message",  java.net.URLEncoder.encode("ProductInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ProductInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
