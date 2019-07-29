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

	/*ͼƬ���ļ��ֶ�photo��������*/
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
    /*�������Ҫ��ѯ������: ��Ա�û���*/
    private String memberUserName;
    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }
    public String getMemberUserName() {
        return this.memberUserName;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
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

    /*��������MemberInfo����*/
    private MemberInfo memberInfo;
    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }
    public MemberInfo getMemberInfo() {
        return this.memberInfo;
    }

    /*��ת�����MemberInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���MemberInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��Ա�û����Ƿ��Ѿ�����*/
        String memberUserName = memberInfo.getMemberUserName();
        MemberInfo db_memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);
        if(null != db_memberInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�û�Ա�û����Ѿ�����!"));
            return "error";
        }
        try {
            /*�����Ա��Ƭ�ϴ�*/
            String photoPath = "upload/noimage.jpg"; 
       	 	if(photoFile != null)
       	 		photoPath = photoUpload(photoFile,photoFileContentType);
       	 	memberInfo.setPhoto(photoPath);
            memberInfoDAO.AddMemberInfo(memberInfo);
            ctx.put("message",  java.net.URLEncoder.encode("MemberInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯMemberInfo��Ϣ*/
    public String QueryMemberInfo() {
        if(currentPage == 0) currentPage = 1;
        if(memberUserName == null) memberUserName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName, birthday, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        memberInfoDAO.CalculateTotalPageAndRecordNumber(memberUserName, birthday);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = memberInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryMemberInfoOutputToExcel() { 
        if(memberUserName == null) memberUserName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName,birthday);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "MemberInfo��Ϣ��¼"; 
        String[] headers = { "��Ա�û���","��ʵ����","�Ա�","��������","��ϵ�绰","��ϵ����","��ϵqq","��Ա��Ƭ"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"MemberInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯMemberInfo��Ϣ*/
    public String FrontQueryMemberInfo() {
        if(currentPage == 0) currentPage = 1;
        if(memberUserName == null) memberUserName = "";
        if(birthday == null) birthday = "";
        List<MemberInfo> memberInfoList = memberInfoDAO.QueryMemberInfoInfo(memberUserName, birthday, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        memberInfoDAO.CalculateTotalPageAndRecordNumber(memberUserName, birthday);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = memberInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�MemberInfo��Ϣ*/
    public String ModifyMemberInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������memberUserName��ȡMemberInfo����*/
        MemberInfo memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);

        ctx.put("memberInfo",  memberInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�MemberInfo��Ϣ*/
    public String FrontShowMemberInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������memberUserName��ȡMemberInfo����*/
        MemberInfo memberInfo = memberInfoDAO.GetMemberInfoByMemberUserName(memberUserName);

        ctx.put("memberInfo",  memberInfo);
        return "front_show_view";
    }

    /*�����޸�MemberInfo��Ϣ*/
    public String ModifyMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*�����Ա��Ƭ�ϴ�*/
            if(photoFile != null) {
            	String photoPath = photoUpload(photoFile,photoFileContentType);
            	memberInfo.setPhoto(photoPath);
            }
            memberInfoDAO.UpdateMemberInfo(memberInfo);
            ctx.put("message",  java.net.URLEncoder.encode("MemberInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��MemberInfo��Ϣ*/
    public String DeleteMemberInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            memberInfoDAO.DeleteMemberInfo(memberUserName);
            ctx.put("message",  java.net.URLEncoder.encode("MemberInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MemberInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
