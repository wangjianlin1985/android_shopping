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

    private String id;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
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
    @Resource YesOrNoDAO yesOrNoDAO;

    /*待操作的YesOrNo对象*/
    private YesOrNo yesOrNo;
    public void setYesOrNo(YesOrNo yesOrNo) {
        this.yesOrNo = yesOrNo;
    }
    public YesOrNo getYesOrNo() {
        return this.yesOrNo;
    }

    /*跳转到添加YesOrNo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加YesOrNo信息*/
    @SuppressWarnings("deprecation")
    public String AddYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证是否编号是否已经存在*/
        String id = yesOrNo.getId();
        YesOrNo db_yesOrNo = yesOrNoDAO.GetYesOrNoById(id);
        if(null != db_yesOrNo) {
            ctx.put("error",  java.net.URLEncoder.encode("该是否编号已经存在!"));
            return "error";
        }
        try {
            yesOrNoDAO.AddYesOrNo(yesOrNo);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo添加失败!"));
            return "error";
        }
    }

    /*查询YesOrNo信息*/
    public String QueryYesOrNo() {
        if(currentPage == 0) currentPage = 1;
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        yesOrNoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = yesOrNoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = yesOrNoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yesOrNoList",  yesOrNoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryYesOrNoOutputToExcel() { 
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "YesOrNo信息记录"; 
        String[] headers = { "是否编号","是否信息"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"YesOrNo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询YesOrNo信息*/
    public String FrontQueryYesOrNo() {
        if(currentPage == 0) currentPage = 1;
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryYesOrNoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        yesOrNoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = yesOrNoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = yesOrNoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yesOrNoList",  yesOrNoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的YesOrNo信息*/
    public String ModifyYesOrNoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取YesOrNo对象*/
        YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNoById(id);

        ctx.put("yesOrNo",  yesOrNo);
        return "modify_view";
    }

    /*查询要修改的YesOrNo信息*/
    public String FrontShowYesOrNoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取YesOrNo对象*/
        YesOrNo yesOrNo = yesOrNoDAO.GetYesOrNoById(id);

        ctx.put("yesOrNo",  yesOrNo);
        return "front_show_view";
    }

    /*更新修改YesOrNo信息*/
    public String ModifyYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            yesOrNoDAO.UpdateYesOrNo(yesOrNo);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo信息更新失败!"));
            return "error";
       }
   }

    /*删除YesOrNo信息*/
    public String DeleteYesOrNo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            yesOrNoDAO.DeleteYesOrNo(id);
            ctx.put("message",  java.net.URLEncoder.encode("YesOrNo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("YesOrNo删除失败!"));
            return "error";
        }
    }

}
