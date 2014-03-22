<%@ page import="ztree.entities.Article" %>
<%@ page import="ztree.dao.ArticleDao" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="ztree.entities.Image" %>
<%@ page import="ztree.dao.ImageDao" %>
<%@ page import="com.gxx.record.utils.PropertyUtil" %>
<%@ page import="com.gxx.record.interfaces.BaseInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <% String baseUrl = PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL);%>
</head>
<body>
    <%
        Article article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("id")));
    %>
    <div width="99%" height="30" align="center" style="padding-top:5px">
        <h2><%=article.getName()%>(id=<%=article.getId()%>)</h2>
    </div>
    <%
        if(StringUtils.isNotBlank(article.getImageIds()))
        {
            String[] imageIds = article.getImageIds().split(",");
    %>
    <div align="center">
        <div align="center" style="width: 800px; overflow: auto; border: 1px solid gray;">
            <table>
                <tr>
                    <%
                        for(int i=0;i<imageIds.length;i++)
                        {
                            Image image = ImageDao.getImageById(Integer.parseInt(imageIds[i]));
                    %>
                    <td>
                        <img src="<%=baseUrl + image.getPath()%>" style="width: 200px; height: 150px;">
                    </td>
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </div>
    <%
        }
    %>
    <div>
        <div>
            <%=article.getContent()%>
        </div>
    </div>
</body>
</html>