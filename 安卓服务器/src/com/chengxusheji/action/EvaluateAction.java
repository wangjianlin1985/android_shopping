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
import com.chengxusheji.dao.EvaluateDAO;
import com.chengxusheji.domain.Evaluate;
import com.chengxusheji.dao.ProductInfoDAO;
import com.chengxusheji.domain.ProductInfo;
import com.chengxusheji.dao.MemberInfoDAO;
import com.chengxusheji.domain.MemberInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class EvaluateAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private ProductInfo productObj;
    public void setProductObj(ProductInfo productObj) {
        this.productObj = productObj;
    }
    public ProductInfo getProductObj() {
        return this.productObj;
    }

    /*�������Ҫ��ѯ������: �û���*/
    private MemberInfo memberObj;
    public void setMemberObj(MemberInfo memberObj) {
        this.memberObj = memberObj;
    }
    public MemberInfo getMemberObj() {
        return this.memberObj;
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

    private int evaluateId;
    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }
    public int getEvaluateId() {
        return evaluateId;
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
    @Resource ProductInfoDAO productInfoDAO;
    @Resource MemberInfoDAO memberInfoDAO;
    @Resource EvaluateDAO evaluateDAO;

    /*��������Evaluate����*/
    private Evaluate evaluate;
    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }
    public Evaluate getEvaluate() {
        return this.evaluate;
    }

    /*��ת�����Evaluate��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�ProductInfo��Ϣ*/
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        /*��ѯ���е�MemberInfo��Ϣ*/
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        return "add_view";
    }

    /*���Evaluate��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(evaluate.getProductObj().getProductNo());
            evaluate.setProductObj(productObj);
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(evaluate.getMemberObj().getMemberUserName());
            evaluate.setMemberObj(memberObj);
            evaluateDAO.AddEvaluate(evaluate);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluate��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate���ʧ��!"));
            return "error";
        }
    }

    /*��ѯEvaluate��Ϣ*/
    public String QueryEvaluate() {
        if(currentPage == 0) currentPage = 1;
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(productObj, memberObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        evaluateDAO.CalculateTotalPageAndRecordNumber(productObj, memberObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = evaluateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = evaluateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("evaluateList",  evaluateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("memberObj", memberObj);
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryEvaluateOutputToExcel() { 
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(productObj,memberObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Evaluate��Ϣ��¼"; 
        String[] headers = { "���۱��","��Ʒ����","�û���","��������","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<evaluateList.size();i++) {
        	Evaluate evaluate = evaluateList.get(i); 
        	dataset.add(new String[]{evaluate.getEvaluateId() + "",evaluate.getProductObj().getProductName(),
evaluate.getMemberObj().getMemberUserName(),
evaluate.getContent(),evaluate.getEvaluateTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Evaluate.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯEvaluate��Ϣ*/
    public String FrontQueryEvaluate() {
        if(currentPage == 0) currentPage = 1;
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(productObj, memberObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        evaluateDAO.CalculateTotalPageAndRecordNumber(productObj, memberObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = evaluateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = evaluateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("evaluateList",  evaluateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        ctx.put("memberObj", memberObj);
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Evaluate��Ϣ*/
    public String ModifyEvaluateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������evaluateId��ȡEvaluate����*/
        Evaluate evaluate = evaluateDAO.GetEvaluateByEvaluateId(evaluateId);

        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("evaluate",  evaluate);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Evaluate��Ϣ*/
    public String FrontShowEvaluateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������evaluateId��ȡEvaluate����*/
        Evaluate evaluate = evaluateDAO.GetEvaluateByEvaluateId(evaluateId);

        List<ProductInfo> productInfoList = productInfoDAO.QueryAllProductInfoInfo();
        ctx.put("productInfoList", productInfoList);
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryAllMemberInfoInfo();
        ctx.put("memberInfoList", memberInfoList);
        ctx.put("evaluate",  evaluate);
        return "front_show_view";
    }

    /*�����޸�Evaluate��Ϣ*/
    public String ModifyEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ProductInfo productObj = productInfoDAO.GetProductInfoByProductNo(evaluate.getProductObj().getProductNo());
            evaluate.setProductObj(productObj);
            MemberInfo memberObj = memberInfoDAO.GetMemberInfoByMemberUserName(evaluate.getMemberObj().getMemberUserName());
            evaluate.setMemberObj(memberObj);
            evaluateDAO.UpdateEvaluate(evaluate);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluate��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Evaluate��Ϣ*/
    public String DeleteEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            evaluateDAO.DeleteEvaluate(evaluateId);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluateɾ��ʧ��!"));
            return "error";
        }
    }

}
