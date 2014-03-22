/**
 * File Name:    AddNodeAction.java
 *
 * File Desc:    新增节点Action
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

import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;
import ztree.dao.ArticleDao;
import ztree.entities.Article;
import ztree.utils.ZTreeUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 新增节点Action
 * @author Gxx
 * @version 1.0
 */
public class AddNodeAction extends ActionSupport implements ServletResponseAware
{
    HttpServletResponse response;
    String pId;
    boolean isType;
    String name;


    public String execute() throws Exception
    {
        System.out.println("AddNodeAction~pId=[" + pId + "],isType=[" + isType + "],name=[" + name + "]");

        // 1 构造Article对象
        Article article = new Article();
        article.setPid(Integer.parseInt(pId));
        article.setIndexId(ArticleDao.getMaxIndexIdByPid(Integer.parseInt(pId)) + 1);
        article.setName(name);
        article.setArticleType(isType);
        article.setContent(StringUtils.EMPTY);
        article.setImageIds(StringUtils.EMPTY);
        article.setRequestDate(ServiceDataUtil.getNowDate());
        article.setRequestTime(ServiceDataUtil.getNowTime());
        article.setDelete(Boolean.FALSE);

        // 2 新增节点
        ArticleDao.addNode(article);
        System.out.println("新增节点成功！");

        // 3 返回Article对应json串
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(ZTreeUtils.getJsonFromArticle(article));
        return null;
    }


    public void setServletResponse(HttpServletResponse response)
    {
        this.response = response;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public boolean getIsType() {
        return isType;
    }

    public void setIsType(boolean isType) {
        this.isType = isType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
