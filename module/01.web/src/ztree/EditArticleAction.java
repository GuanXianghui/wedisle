/**
 * File Name:    EditArticleAction.java
 *
 * File Desc:    �޸�����Action
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
 * History:      2013-09-08 created by Gxx
 */
package ztree;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import ztree.dao.ArticleDao;
import ztree.dao.ImageDao;
import ztree.entities.Article;
import ztree.entities.Image;
import ztree.utils.ZTreeUtils;

import java.io.File;
import java.util.Date;

/**
 * �޸�����Action
 * @author Gxx
 * @version 1.0
 */
public class EditArticleAction extends ActionSupport
{
    private String id;//����ID
    private String name;//��������
    private String content;//��������
    private File file;//�ϴ�ͼƬ�ļ�
    private String delImgId;//ɾ��ͼƬID
    private String preImgId;//ǰ��ͼƬID
    private String nextImgId;//����ͼƬID


    public String execute() throws Exception
    {
        System.out.println("EditArticleAction~id=[" + id + "],name=[" + name + "],content=[" + content + "]" +
                ",file=[" + file + "],delImgId=[" + delImgId + "]");

        // 1 ����ID��Article
        Article article = ArticleDao.getArticleById(Integer.parseInt(id));
        if(null == article)
        {
            System.out.println("û���ҵ�Article����");
            throw new RuntimeException("û���ҵ�Article����");
        }

        // 2 �޸�����
        article.setName(ZTreeUtils.formatInputSpecialString(name));
        article.setContent(ZTreeUtils.formatInputSpecialString(content));

        // 3 ��ͼƬ���� �޸�ͼƬID����
        if(StringUtils.isNotBlank(delImgId))//ɾ��ͼƬ
        {
            article.setImageIds(ZTreeUtils.delImage(article, delImgId));
        }
        if(StringUtils.isNotBlank(preImgId))//ǰ��ͼƬ
        {
            article.setImageIds(ZTreeUtils.preImage(article, preImgId));
        }
        if(StringUtils.isNotBlank(nextImgId))//����ͼƬ
        {
            article.setImageIds(ZTreeUtils.nextImage(article, nextImgId));
        }

        // 4 ���ļ��ǿ�
        if(null != file)
        {
            // 5 ����ͼƬ������
            String fileName = new Date().getTime() + ".jpg";//�������õ�ǰʱ�����ϴ�ͼƬ�����
            String imageRealPath = ServletActionContext.getServletContext().getRealPath("wedding/ztree/img/article")
                    + "/" + fileName;//���������ļ�·��
            File imageFile = new File(imageRealPath);//�ļ�����
            ZTreeUtils.copy(file, imageFile);//�����ļ�

            // 6 ����ͼƬʵ��
            String filePath = "wedding/ztree/img/article" + "/" + fileName;//WEB�µ�ͼƬ·��
            Image image = new Image();
            image.setPath(filePath);
            ImageDao.addImage(image);

            // 7 �޸�ͼƬID����
            if(StringUtils.isBlank(article.getImageIds()))
            {
                article.setImageIds(StringUtils.EMPTY + image.getId());
            } else
            {
                article.setImageIds(article.getImageIds() + "," + image.getId());
            }
        }

        // 8 �޸�ʵ��
        ArticleDao.updateArticle(article);

        // 9 ͼƬ���� ������޸�ҳ��
        if(StringUtils.isNotBlank(delImgId) || StringUtils.isNotBlank(preImgId)
                || StringUtils.isNotBlank(nextImgId))//ͼƬ����
        {
            return "edit";
        }

        // 7 ��ת���鿴����ҳ��
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getDelImgId() {
        return delImgId;
    }

    public void setDelImgId(String delImgId) {
        this.delImgId = delImgId;
    }

    public String getPreImgId() {
        return preImgId;
    }

    public void setPreImgId(String preImgId) {
        this.preImgId = preImgId;
    }

    public String getNextImgId() {
        return nextImgId;
    }

    public void setNextImgId(String nextImgId) {
        this.nextImgId = nextImgId;
    }
}
