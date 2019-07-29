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
import com.chengxusheji.dao.MemberInfoDAO;
import com.chengxusheji.domain.MemberInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class MemberInfoAction extends BaseAction {

	/*图片或文件字段photo参数接收*/
	private File photoFile;
	private String photoFileFileName;
	private String photoFileContentType;
	public File getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(File photoFile) {
		this.photoFile = photoFile;
	}
	public String getPhotoFileFileName() {
		return photoFileFileName;
	}
	public void setPhotoFileFileName(String photoFileFileName) {
		this.photoFileFileName = photoFileFileName;
	}
	public String getPhotoFileContentType() {
		return photoFileContentType;
	}
	public void setPhotoFileContentType(String photoFileContentType) {
		this.photoFileContentType = photoFileContentType;
	}
    /*界面层需要查询的属性: 会员用户名*/
    private String memberUserName;
    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }
    public String getMemberUserName() {
        return this.memberUserName;
    }

    /*界面层需要查询的属性: 出生日期*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
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

    /*待操作的MemberInfo对象*/
    private MemberInfo memberInfo;
    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }
    public MemberInfo getMemberInfo() {
        return this.memberInfo;
    }

    /*跳转到添加MemberInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加MemberInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证会员用户名是否已经存在*/
        String memberUserName = memberInfo.getMemberUserName();
        MemberInfo db_memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);
        if(null != db_memberInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该会员用户名已经存在!"));
            return "error";
        }
        try {
            /*处理会员照片上传*/
            String photoPath = "upload/noimage.jpg"; 
       	 	if(photoFile != null)
       	 		photoPath = photoUpload(photoFile,photoFileContentType);
       	 	memberInfo.setPhoto(photoPath);
            memberInfoDAO.AddMemberInfo(memberInfo);
            ctx.put("message",  java.net.URLEncoder.encode("MemberInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo添加失败!"));
            return "error";
        }
    }

    /*查询MemberInfo信息*/
    public String QueryMemberInfo() {
        if(currentPage == 0) currentPage = 1;
        if(memberUserName == null) memberUserName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName, birthday, currentPage);
        /*计算总的页数和总的记录数*/
        memberInfoDAO.CalculateTotalPageAndRecordNumber(memberUserName, birthday);
        /*获取到总的页码数目*/
        totalPage = memberInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = memberInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("memberInfoList",  memberInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("memberUserName", memberUserName);
        ctx.put("birthday", birthday);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryMemberInfoOutputToExcel() { 
        if(memberUserName == null) memberUserName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName,birthday);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "MemberInfo信息记录"; 
        String[] headers = { "会员用户名","真实姓名","性别","出生日期","联系电话","联系邮箱","联系qq","会员照片"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<memberInfoList.size();i++) {
        	MemberInfo memberInfo = memberInfoList.get(i); 
        	dataset.add(new String[]{memberInfo.getMemberUserName(),memberInfo.getRealName(),memberInfo.getSex(),new SimpleDateFormat("yyyy-MM-dd").format(memberInfo.getBirthday()),memberInfo.getTelephone(),memberInfo.getEmail(),memberInfo.getQq(),memberInfo.getPhoto()});
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
			response.setHeader("Content-disposition","attachment; filename="+"MemberInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询MemberInfo信息*/
    public String FrontQueryMemberInfo() {
        if(currentPage == 0) currentPage = 1;
        if(memberUserName == null) memberUserName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName, birthday, currentPage);
        /*计算总的页数和总的记录数*/
        memberInfoDAO.CalculateTotalPageAndRecordNumber(memberUserName, birthday);
        /*获取到总的页码数目*/
        totalPage = memberInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = memberInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("memberInfoList",  memberInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("memberUserName", memberUserName);
        ctx.put("birthday", birthday);
        return "front_query_view";
    }

    /*查询要修改的MemberInfo信息*/
    public String ModifyMemberInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键memberUserName获取MemberInfo对象*/
        MemberInfo memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);

        ctx.put("memberInfo",  memberInfo);
        return "modify_view";
    }

    /*查询要修改的MemberInfo信息*/
    public String FrontShowMemberInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键memberUserName获取MemberInfo对象*/
        MemberInfo memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);

        ctx.put("memberInfo",  memberInfo);
        return "front_show_view";
    }

    /*更新修改MemberInfo信息*/
    public String ModifyMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*处理会员照片上传*/
            if(photoFile != null) {
            	String photoPath = photoUpload(photoFile,photoFileContentType);
            	memberInfo.setPhoto(photoPath);
            }
            memberInfoDAO.UpdateMemberInfo(memberInfo);
            ctx.put("message",  java.net.URLEncoder.encode("MemberInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除MemberInfo信息*/
    public String DeleteMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            memberInfoDAO.DeleteMemberInfo(memberUserName);
            ctx.put("message",  java.net.URLEncoder.encode("MemberInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo删除失败!"));
            return "error";
        }
    }

}
