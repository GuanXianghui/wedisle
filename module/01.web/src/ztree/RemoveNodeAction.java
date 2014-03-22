/**
 * File Name:    RemoveNodeAction.java
 *
 * File Desc:    删除节点Action
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
 * 删除节点Action
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

        // 1 根据ID查Article
        Article article = ArticleDao.getArticleById(Integer.parseInt(id));
        if(null == article)
        {
            System.out.println("没有找到Article对象,id=" + id);
            throw new RuntimeException("没有找到Article对象,id=" + id);
        }

        // 2 修改是否删除
        article.setDelete(Boolean.TRUE);

        // 3 修改实体
        ArticleDao.updateArticle(article);

        // 4 判 文章类 得遍历这个文章类下的所有文章和文章子类
        if(article.isArticleType())
        {
            // 5 递归查询 Article下的所有Article
            List<Article> articles = ArticleDao.queryAllSubArticlesByPid(article.getId());

            // 6 循环删掉
            for(int i=0;i<articles.size();i++)
            {
                Article tempArticle = articles.get(i);
                tempArticle.setDelete(Boolean.TRUE);
                ArticleDao.updateArticle(tempArticle);
            }
        }

        // 7 返回Article对应json串
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
