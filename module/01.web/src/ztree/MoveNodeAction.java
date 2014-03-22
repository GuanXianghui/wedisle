/**
 * File Name:    MoveNodeAction.java
 *
 * File Desc:    �ƶ��ڵ�Action
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
 * �ƶ��ڵ�Action
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

        // 1 ����ID��Article
        Article article = ArticleDao.getArticleById(Integer.parseInt(id));
        Article targetArticle = ArticleDao.getArticleById(Integer.parseInt(targetId));
        if(null == article || null == targetArticle)
        {
            System.out.println("û���ҵ�Article����");
            throw new RuntimeException("û���ҵ�Article����");
        }

        // 2 �޸��Ƿ�ɾ��
        if(MOVE_TYPE_INNER.equals(moveType))//����
        {
            article.setPid(targetArticle.getId());
            article.setIndexId(ArticleDao.getMaxIndexIdByPid(targetArticle.getId()) + 1);
        } else
        {
            article.setPid(targetArticle.getPid());
            if(MOVE_TYPE_PREV.equals(moveType))//����ǰ��
            {
                article.setIndexId(targetArticle.getIndexId());
                // targetArticleͬһ���� ����targetArticle ����index_id��+1
                ArticleDao.addIndexIdByOne(targetArticle.getPid(), targetArticle.getIndexId(), true);
            } else//���ں���
            {
                article.setIndexId(targetArticle.getIndexId() + 1);
                // targetArticleͬһ���� ������targetArticle ����index_id��+1
                ArticleDao.addIndexIdByOne(targetArticle.getPid(), targetArticle.getIndexId(), false);
            }
        }

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
