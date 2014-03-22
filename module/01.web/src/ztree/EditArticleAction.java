/**
 * File Name:    EditArticleAction.java
 *
 * File Desc:    修改文章Action
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
 * 修改文章Action
 * @author Gxx
 * @version 1.0
 */
public class EditArticleAction extends ActionSupport
{
    private String id;//文章ID
    private String name;//文章名字
    private String content;//文章内容
    private File file;//上传图片文件
    private String delImgId;//删除图片ID
    private String preImgId;//前置图片ID
    private String nextImgId;//后置图片ID


    public String execute() throws Exception
    {
        System.out.println("EditArticleAction~id=[" + id + "],name=[" + name + "],content=[" + content + "]" +
                ",file=[" + file + "],delImgId=[" + delImgId + "]");

        // 1 根据ID查Article
        Article article = ArticleDao.getArticleById(Integer.parseInt(id));
        if(null == article)
        {
            System.out.println("没有找到Article对象");
            throw new RuntimeException("没有找到Article对象");
        }

        // 2 修改属性
        article.setName(ZTreeUtils.formatInputSpecialString(name));
        article.setContent(ZTreeUtils.formatInputSpecialString(content));

        // 3 判图片操作 修改图片ID属性
        if(StringUtils.isNotBlank(delImgId))//删除图片
        {
            article.setImageIds(ZTreeUtils.delImage(article, delImgId));
        }
        if(StringUtils.isNotBlank(preImgId))//前置图片
        {
            article.setImageIds(ZTreeUtils.preImage(article, preImgId));
        }
        if(StringUtils.isNotBlank(nextImgId))//后置图片
        {
            article.setImageIds(ZTreeUtils.nextImage(article, nextImgId));
        }

        // 4 判文件非空
        if(null != file)
        {
            // 5 拷贝图片到本地
            String fileName = new Date().getTime() + ".jpg";//此名称用当前时间与上传图片名组成
            String imageRealPath = ServletActionContext.getServletContext().getRealPath("wedding/ztree/img/article")
                    + "/" + fileName;//服务器上文件路径
            File imageFile = new File(imageRealPath);//文件对象
            ZTreeUtils.copy(file, imageFile);//拷贝文件

            // 6 新增图片实体
            String filePath = "wedding/ztree/img/article" + "/" + fileName;//WEB下的图片路径
            Image image = new Image();
            image.setPath(filePath);
            ImageDao.addImage(image);

            // 7 修改图片ID属性
            if(StringUtils.isBlank(article.getImageIds()))
            {
                article.setImageIds(StringUtils.EMPTY + image.getId());
            } else
            {
                article.setImageIds(article.getImageIds() + "," + image.getId());
            }
        }

        // 8 修改实体
        ArticleDao.updateArticle(article);

        // 9 图片操作 则继续修改页面
        if(StringUtils.isNotBlank(delImgId) || StringUtils.isNotBlank(preImgId)
                || StringUtils.isNotBlank(nextImgId))//图片操作
        {
            return "edit";
        }

        // 7 跳转到查看文章页面
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
