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
import com.chengxusheji.dao.YesOrNoDAO;
import com.chengxusheji.domain.YesOrNo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class YesOrNoAction extends BaseAction {

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

    private String id;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
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
    @Resource YesOrNoDAO yesOrNoDAO;

    /*��������YesOrNo����*/
    private YesOrNo yesOrNo;
    public void setYesOrNo(YesOrNo yesOrNo) {
        this.yesOrNo = yesOrNo;
    }
    public YesOrNo getYesOrNo() {
        return this.yesOrNo;
    }

    /*��ת�����YesOrNo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���YesOrNo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�Ƿ����Ƿ��Ѿ�����*/
        String id = yesOrNo.getId();
        YesOrNo db_yesOrNo = yesOrNoDAO.GetYesOrNoById(id);
        if(null != db_yesOrNo) {
            ctx.put("error",  java.net.URLEncoder.encode("���Ƿ����Ѿ�����!"));
            return "error";
        }
        try {
            yesOrNoDAO.AddYesOrNo(yesOrNo);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯYesOrNo��Ϣ*/
    public String QueryYesOrNo() {
        if(currentPage == 0) currentPage = 1;
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        yesOrNoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = yesOrNoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = yesOrNoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yesOrNoList",  yesOrNoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryYesOrNoOutputToExcel() { 
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "YesOrNo��Ϣ��¼"; 
        String[] headers = { "�Ƿ���","�Ƿ���Ϣ"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<yesOrNoList.size();i++) {
        	YesOrNo yesOrNo = yesOrNoList.get(i); 
        	dataset.add(new String[]{yesOrNo.getId(),yesOrNo.getName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"YesOrNo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯYesOrNo��Ϣ*/
    public String FrontQueryYesOrNo() {
        if(currentPage == 0) currentPage = 1;
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        yesOrNoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = yesOrNoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = yesOrNoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yesOrNoList",  yesOrNoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�YesOrNo��Ϣ*/
    public String ModifyYesOrNoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡYesOrNo����*/
        YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNoById(id);

        ctx.put("yesOrNo",  yesOrNo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�YesOrNo��Ϣ*/
    public String FrontShowYesOrNoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡYesOrNo����*/
        YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNoById(id);

        ctx.put("yesOrNo",  yesOrNo);
        return "front_show_view";
    }

    /*�����޸�YesOrNo��Ϣ*/
    public String ModifyYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            yesOrNoDAO.UpdateYesOrNo(yesOrNo);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��YesOrNo��Ϣ*/
    public String DeleteYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            yesOrNoDAO.DeleteYesOrNo(id);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNoɾ��ʧ��!"));
            return "error";
        }
    }

}
