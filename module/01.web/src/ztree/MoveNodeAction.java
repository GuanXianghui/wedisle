/**
 * File Name:    MoveNodeAction.java
 *
 * File Desc:    移动节点Action
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
import ztree.interfaces.ZTreeInterface;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 移动节点Action
 * @author Gxx
 * @version 1.0
 */
public class MoveNodeAction extends ActionSupport implements ServletResponseAware, ZTreeInterface
{
    HttpServletResponse response;
    String id;
    String targetId;
    String moveType;// prev inner next


    public String execute() throws Exception
    {
        System.out.println("MoveNodeAction~id=[" + id + "],targetId=[" + targetId + "],moveType=[" + moveType + "]");

        // 1 根据ID查Article
        Article article = ArticleDao.getArticleById(Integer.parseInt(id));
        Article targetArticle = ArticleDao.getArticleById(Integer.parseInt(targetId));
        if(null == article || null == targetArticle)
        {
            System.out.println("没有找到Article对象");
            throw new RuntimeException("没有找到Article对象");
        }

        // 2 修改是否删除
        if(MOVE_TYPE_INNER.equals(moveType))//放入
        {
            article.setPid(targetArticle.getId());
            article.setIndexId(ArticleDao.getMaxIndexIdByPid(targetArticle.getId()) + 1);
        } else
        {
            article.setPid(targetArticle.getPid());
            if(MOVE_TYPE_PREV.equals(moveType))//放在前面
            {
                article.setIndexId(targetArticle.getIndexId());
                // targetArticle同一级别 包括targetArticle 所有index_id都+1
                ArticleDao.addIndexIdByOne(targetArticle.getPid(), targetArticle.getIndexId(), true);
            } else//放在后面
            {
                article.setIndexId(targetArticle.getIndexId() + 1);
                // targetArticle同一级别 不包括targetArticle 所有index_id都+1
                ArticleDao.addIndexIdByOne(targetArticle.getPid(), targetArticle.getIndexId(), false);
            }
        }

        // 3 修改实体
        ArticleDao.updateArticle(article);

        // 4 返回Article对应json串
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

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
