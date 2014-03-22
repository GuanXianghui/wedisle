/**
 * File Name:    RenameNodeAction.java
 *
 * File Desc:    �޸Ľڵ�����Action
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
import org.apache.struts2.interceptor.ServletResponseAware;
import ztree.dao.ArticleDao;
import ztree.entities.Article;
import ztree.utils.ZTreeUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * �޸Ľڵ�����Action
 * @author Gxx
 * @version 1.0
 */
public class RenameNodeAction extends ActionSupport implements ServletResponseAware
{
    HttpServletResponse response;
    String id;
    String newName;


    public String execute() throws Exception
    {
        System.out.println("RenameNodeAction~id=[" + id + "],newName=[" + newName + "]");

        // 1 ����ID��Article
        Article article = ArticleDao.getArticleById(Integer.parseInt(id));
        if(null == article)
        {
            System.out.println("û���ҵ�Article����,id=" + id);
            throw new RuntimeException("û���ҵ�Article����,id=" + id);
        }

        // 2 �޸���������
        article.setName(ZTreeUtils.formatInputSpecialString(newName));

        // 3 �޸�ʵ��
        ArticleDao.updateArticle(article);

        // 4 ����Article��Ӧjson��
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(Boolean.TRUE.toString());
        return null;
    }


    public void setServletResponse(HttpServletResponse response)
    {
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
