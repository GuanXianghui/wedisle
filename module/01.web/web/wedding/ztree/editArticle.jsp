<%@ page import="ztree.entities.Article" %>
<%@ page import="ztree.dao.ArticleDao" %>
<%@ page import="com.gxx.record.utils.PropertyUtil" %>
<%@ page import="com.gxx.record.interfaces.BaseInterface" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="ztree.entities.Image" %>
<%@ page import="ztree.dao.ImageDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% String baseUrl = PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL);%>
    <title></title>
</head>
<body>
    <%
        Article article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("id")));
    %>
    <div style="border: 1px solid gray;">
        <form action="<%=baseUrl%>editArticle" method="post" autocomplete="off" enctype="multipart/form-data">
            <table>
                <input type="hidden" name="id" value="<%=article.getId()%>">
                <input type="hidden" id="pre_img_id" name="preImgId" value="">
                <input type="hidden" id="next_img_id" name="nextImgId" value="">
                <input type="hidden" id="del_img_id" name="delImgId" value="">
                <tr>
                    <td>文章名：</td>
                    <td><input type="text" name="name" value="<%=article.getName().replaceAll("&", "&amp;")%>"></td>
                </tr>
                <tr>
                    <td>文章内容：</td>
                    <td>
                        <textarea rows="20" cols="90" name="content"><%=article.getContent().replaceAll("&", "&amp;")%></textarea>
                    </td>
                </tr>
                <%
                    if(StringUtils.isNotBlank(article.getImageIds()))
                    {
                        String[] imageIds = article.getImageIds().split(",");
                %>
                <tr>
                    <td colspan="2" align="center">
                        <div align="center" style="width: 800px; overflow: auto; border: 1px solid gray;">
                            <table>
                                <tr>
                                    <%
                                        for(int i=0;i<imageIds.length;i++)
                                        {
                                            Image image = ImageDao.getImageById(Integer.parseInt(imageIds[i]));
                                    %>
                                    <td onmouseover="document.getElementById('command_<%=image.getId()%>').style.display='';"
                                        onmouseout="document.getElementById('command_<%=image.getId()%>').style.display='none';">
                                        <table>
                                            <tr>
                                                <td>
                                                    <img src="<%=baseUrl + image.getPath()%>" style="width: 200px; height: 150px;">
                                                </td>
                                                <td>
                                                    <table id="command_<%=image.getId()%>" style="display: none;">
                                                        <tr><td>
                                                            <input type="submit" value="前置"
                                                                   onclick="document.getElementById('pre_img_id').value='<%=image.getId()%>';">
                                                        </td></tr>
                                                        <tr><td>
                                                            <input type="submit" value="后置"
                                                                   onclick="document.getElementById('next_img_id').value='<%=image.getId()%>';">
                                                        </td></tr>
                                                        <tr><td>
                                                            <input type="submit" value="删除"
                                                                   onclick="document.getElementById('del_img_id').value='<%=image.getId()%>';">
                                                        </td></tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <%
                                        }
                                    %>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td>图片：</td>
                    <td>
                        <input type="file" name="file">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="修改">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>