<%@ page import="com.gxx.record.utils.WedisleUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../header.jsp" %>
<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE></TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="../../../wedding/ztree/js/jquery-1.4.4.min.js"></script>
	<SCRIPT type="text/javascript">
        //根据所有WedisleMainStep(按indexId顺序排列)对象构造Json对象
        var step_array;
        var text_array = new Array("婚礼筹划","婚纱摄影","酒店婚庆","婚品布置","大婚一周");

        $(document).ready(function(){
            step_array = eval(<%=WedisleUtils.getJsonFromAllWedisleMainSteps()%>);
        });

        /**
         * 选择级别1
         */
        function choose_level_1(id){
            var content = "<table>";
            for(var i=0;i<step_array["level_1_" + id].length;i++){
                content += "<tr><td>"
                        + "<span onclick=\"choose_level_2('" + step_array["level_1_" + id][i]["id"] + "', '" + step_array["level_1_" + id][i]["name"] + "');\">" + step_array["level_1_" + id][i]["name"] + "</span>"
                        + "<button onclick=\"update_level_2('" + step_array["level_1_" + id][i]["id"] + "', '" + step_array["level_1_" + id][i]["name"] + "');\">修改</button>"
                        + "<button onclick=\"up_level_2('" + step_array["level_1_" + id][i]["id"] + "');\">上移</button>"
                        + "<button onclick=\"down_level_2('" + step_array["level_1_" + id][i]["id"] + "');\">下移</button>"
                        + "<button onclick=\"delete_level_2('" + step_array["level_1_" + id][i]["id"] + "');\">删除</button>"
                        + "</td></tr>"
            }
            content += "<tr><td>"
                    + "<button onclick=\"add_level_2('" + id + "');\">新增</button>"
                    + "</td></tr>";
            content += "</table>";
            document.getElementById("level_2_id").innerHTML = content;
            document.getElementById("level_2_desc").innerHTML = "[" + text_array[id-1] + "]二级";
        }

        /**
         * 选择级别2
         */
        function choose_level_2(id, name){
            var content = "<table>";
            for(var i=0;i<step_array["level_2_" + id].length;i++){
                content += "<tr><td>"
                        + "<span onclick=\"update_level_3('" + id + "','" + step_array["level_2_" + id][i]["id"] + "');\">" + step_array["level_2_" + id][i]["name"] + "</span>"
                        + "<button onclick=\"up_level_3('" + step_array["level_2_" + id][i]["id"] + "');\">上移</button>"
                        + "<button onclick=\"down_level_3('" + step_array["level_2_" + id][i]["id"] + "');\">下移</button>"
                        + "<button onclick=\"delete_level_3('" + step_array["level_2_" + id][i]["id"] + "');\">删除</button>"
                        + "</td></tr>"
            }
            content += "<tr><td>"
                    + "<button onclick=\"add_level_3('" + id + "', '" + name + "');\">新增</button>"
                    + "</td></tr>";
            content += "</table>";
            document.getElementById("level_3_id").innerHTML = content;
            document.getElementById("level_3_desc").innerHTML = "[" + name + "]三级";
        }

        /**
         * 新增级别2
         */
        function add_level_2(id){
            var text = text_array[id-1];
            document.getElementById("op_desc").innerHTML = "新增[" + text + "]二级菜单";
            document.getElementById("op_type").value = "add_level_2";
            document.getElementById("op_id").value = id;
            document.getElementById("op_name").value = "";
            document.getElementById("op_article_id").value = "";
            document.getElementById("op_article_tr_id").style.display = "none";
        }

        /**
         * 修改级别2
         */
        function update_level_2(id, name){
            document.getElementById("op_desc").innerHTML = "修改[" + name + "]二级菜单";
            document.getElementById("op_type").value = "update_level_2";
            document.getElementById("op_id").value = id;
            document.getElementById("op_name").value = name;
            document.getElementById("op_article_id").value = "";
            document.getElementById("op_article_tr_id").style.display = "none";
        }

        /**
         * 上移级别2
         */
        function up_level_2(id){
            document.getElementById("op_type").value = "up_level_2";
            document.getElementById("op_id").value = id;
            document.getElementById("op_form").submit();
        }

        /**
         * 下移级别2
         */
        function down_level_2(id){
            document.getElementById("op_type").value = "down_level_2";
            document.getElementById("op_id").value = id;
            document.getElementById("op_form").submit();
        }

        /**
         * 删除级别2
         */
        function delete_level_2(id){
            document.getElementById("op_type").value = "delete_level_2";
            document.getElementById("op_id").value = id;
            document.getElementById("op_form").submit();
        }

        /**
         * 新增级别3
         */
        function add_level_3(id, name){
            document.getElementById("op_desc").innerHTML = "新增[" + name + "]三级菜单";
            document.getElementById("op_type").value = "add_level_3";
            document.getElementById("op_id").value = id;
            document.getElementById("op_name").value = "";
            document.getElementById("op_article_id").value = "";
            document.getElementById("op_article_tr_id").style.display = "";
        }

        /**
         * 修改级别3
         */
        function update_level_3(level_2_id, id){
            for(var i=0;i<step_array["level_2_" + level_2_id].length;i++){
                if(step_array["level_2_" + level_2_id][i]["id"] == id){
                    document.getElementById("op_desc").innerHTML = "查看[" + step_array["level_2_" + level_2_id][i]["name"] + "]三级菜单";
                    document.getElementById("op_type").value = "update_level_3";
                    document.getElementById("op_id").value = id;
                    document.getElementById("op_name").value = step_array["level_2_" + level_2_id][i]["name"];
                    document.getElementById("op_article_id").value = step_array["level_2_" + level_2_id][i]["article_id"];
                    document.getElementById("op_article_tr_id").style.display = "";
                }
            }
        }

        /**
         * 上移级别3
         */
        function up_level_3(id){
            document.getElementById("op_type").value = "up_level_3";
            document.getElementById("op_id").value = id;
            document.getElementById("op_form").submit();
        }

        /**
         * 下移级别3
         */
        function down_level_3(id){
            document.getElementById("op_type").value = "down_level_3";
            document.getElementById("op_id").value = id;
            document.getElementById("op_form").submit();
        }

        /**
         * 删除级别3
         */
        function delete_level_3(id){
            document.getElementById("op_type").value = "delete_level_3";
            document.getElementById("op_id").value = id;
            document.getElementById("op_form").submit();
        }

        /**
         * 提交表单
         */
        function commit_form(){
            document.getElementById("op_form").submit();
        }
	</SCRIPT>

    <style>
        table {
            border: 1px solid gray;
            text-align: center;
        }
        table tr td{
            border: 1px solid gray;
        }
    </style>
</HEAD>

<BODY style="text-align: center;">
    <h1>首页管理_后台</h1>
    <div align="center">
        <img style="cursor : pointer;" onclick="choose_level_1(1)" src="<%=baseUrl%>tool/step/img/1.png">
        <img style="cursor : pointer;" onclick="choose_level_1(2)" src="<%=baseUrl%>tool/step/img/2.png">
        <img style="cursor : pointer;" onclick="choose_level_1(3)" src="<%=baseUrl%>tool/step/img/3.png">
        <img style="cursor : pointer;" onclick="choose_level_1(4)" src="<%=baseUrl%>tool/step/img/4.png">
        <img style="cursor : pointer;" onclick="choose_level_1(5)" src="<%=baseUrl%>tool/step/img/5.png">
    </div>
    <div align="center">
        <table class="tb">
            <tr>
                <td id="level_2_desc">二级</td>
                <td id="level_3_desc">三级</td>
                <td>详情</td>
            </tr>
            <tr>
                <td id="level_2_id">

                </td>
                <td id="level_3_id">

                </td>
                <td id="detail_id">
                    <form action="<%=baseUrl%>mainStepManage" method="post" id="op_form">
                        <input type="hidden" id="op_type" name="type">
                        <input type="hidden" id="op_id" name="id">
                        <table>
                            <tr><td>操作</td><td id="op_desc"></td></tr>
                            <tr><td>名称</td><td><input type="text" id="op_name" name="name"></td></tr>
                            <tr id="op_article_tr_id"><td>文章ID</td><td><input type="text" id="op_article_id" name="articleId"></td></tr>
                            <tr><td colspan="2" align="center"><input type="button" onclick="commit_form()" value="保存"></td></tr>
                        </table>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</BODY>
</HTML>