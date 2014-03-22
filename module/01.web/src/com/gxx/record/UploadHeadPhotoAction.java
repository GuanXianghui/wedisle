/**
 * File Name:    UploadHeadPhotoAction.java
 *
 * File Desc:    �ϴ�ͷ��Action
 *
 * Product AB:   PAYGATE_1_0_0
 *
 * Product Name: PAYGATE
 *
 * Module Name:  01.core
 *
 * Module AB:    01.core
 *
 * Author:       Gxx
 *
 * History:      2013-06-30 created by Gxx
 */
package com.gxx.record;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.User;
import com.gxx.record.interfaces.BaseInterface;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * �ϴ�ͷ��Action
 * @author Gxx
 * @version 1.0
 */
public class UploadHeadPhotoAction extends ActionSupport implements ServletRequestAware
{
    private static final int BUFFER_SIZE = 16 * 1024;

    private File myFile;         //��ҳ��file�ֶε�name
    private String contentType;  //struts2�б����ֶ�,��Ҫ��set����
    private String fileName;     // struts2�б����ֶ�,��Ҫ��set����
    private String imageFileName;   //Ҫ�ϴ���ͼƬ�ڷ������е�����
    private String imagePath;       //����������е�·��
    private String pagePath;        //ҳ����Ҫ���õ�url
    public String errorMsg;         //������Ϣ
    public String message;          //�ɹ���Ϣ
    HttpServletRequest request;

    private static void copy(File src, File dst)
    {
        try
        {
            InputStream in = null;
            OutputStream out = null;
            try
            {
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0)
                {
                    out.write(buffer);
                }
            } finally
            {
                if (null != in)
                {
                    in.close();
                }
                if (null != out)
                {
                    out.close();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public String execute() throws Exception
    {
        User user = (User) request.getSession().getAttribute(BaseInterface.USER);
        if(null == user)
        {
            errorMsg = "���ȵ�¼!";
            return ERROR;
        }
        if(null == myFile)
        {
            errorMsg = "������δ�յ�ͼƬ!";
            return ERROR;
        }
        imageFileName = new Date().getTime() + ".jpg";   //�������õ�ǰʱ�����ϴ�ͼƬ�����
        imagePath = ServletActionContext.getServletContext().getRealPath("ueditor/jsp/head") + "\\" + imageFileName;
        pagePath = getWebPath() + "ueditor/jsp/head" + "/" + imageFileName; //ҳ������λ��
        File imageFile = new File(imagePath);
        copy(myFile, imageFile);

        DBUpdate.updateHeadPhoto(user.getId(), pagePath);
        // ���»���
        user.setHeadPhoto(pagePath);
        message = "�ϴ�ͷ����ɣ�";
        return SUCCESS;
    }

    private String getWebPath()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        return basePath;
    }

    public File getMyFile()
    {
        return myFile;
    }

    public void setMyFile(File myFile)
    {
        this.myFile = myFile;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getImageFileName()
    {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName)
    {
        this.imageFileName = imageFileName;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public String getPagePath()
    {
        return pagePath;
    }

    public void setPagePath(String pagePath)
    {
        this.pagePath = pagePath;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}