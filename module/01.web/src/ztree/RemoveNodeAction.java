/**
 * File Name:    RemoveNodeAction.java
 *
 * File Desc:    ɾ���ڵ�Action
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ɾ���ڵ�Action
 * @author Gxx
 * @version 1.0
 */
public class RemoveNodeAction extends ActionSupport implements ServletResponseAware
{
    HttpServletResponse response;
    String id;


    public String execute() throws Exception
    {
        System.out.println("RemoveNodeAction~id=[" + id + "]");

        // 1 ����ID��Article
        Article article = ArticleDao.getArticleById(Integer.parseInt(id));
        if(null == article)
        {
            System.out.println("û���ҵ�Article����,id=" + id);
            throw new RuntimeException("û���ҵ�Article����,id=" + id);
        }

        // 2 �޸��Ƿ�ɾ��
        article.setDelete(Boolean.TRUE);

        // 3 �޸�ʵ��
        ArticleDao.updateArticle(article);

        // 4 �� ������ �ñ�������������µ��������º���������
        if(article.isArticleType())
        {
            // 5 �ݹ��ѯ Article�µ�����Article
            List<Article> articles = ArticleDao.queryAllSubArticlesByPid(article.getId());

            // 6 ѭ��ɾ��
            for(int i=0;i<articles.size();i++)
            {
                Article tempArticle = articles.get(i);
                tempArticle.setDelete(Boolean.TRUE);
                ArticleDao.updateArticle(tempArticle);
            }
        }

        // 7 ����Article��Ӧjson��
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
}
