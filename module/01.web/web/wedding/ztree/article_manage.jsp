<%@ page import="ztree.dao.ArticleDao" %>
<%@ page import="ztree.utils.ZTreeUtils" %>
<%@ page import="com.gxx.record.utils.PropertyUtil" %>
<%@ page import="com.gxx.record.interfaces.BaseInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<HTML>
<HEAD>
    <% String baseUrl = PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL);%>
	<TITLE> ZTREE DEMO - beforeEditName / beforeRemove / onRemove / beforeRename / onRename</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/demo.css" type="text/css">
	<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.exedit-3.5.js"></script>
    <script type="text/javascript" src="js/ztree_ajax.js"></script>
	<SCRIPT type="text/javascript">
        var zTree;//树控件的根
        var baseUrl = '<%=baseUrl%>';
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
                beforeDrop: beforeDrop,
                beforeDblClick: beforeDblClick,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename,
                onDrag: onDrag,
                onDrop: onDrop
			}
		};

		var zNodes =<%=ZTreeUtils.getJsonFromArticles(ArticleDao.queryAllArticles())%>;
		
		var log, className = "dark";
        var beforeParentNode;
        var beforePreNode;
        var beforeNextNode;
        function beforeDrag(treeId, treeNodes) {
            if(treeNodes[0].id == 0)
            {
                alert("根节点不允许拖动");
                return false;
            }
            beforeParentNode = treeNodes[0].getParentNode();
            beforePreNode = treeNodes[0].getPreNode();
            beforeNextNode = treeNodes[0].getNextNode();
            return true;
        }
        var beforeEditNameStr;
		function beforeEditName(treeId, treeNode) {
            beforeEditNameStr = treeNode.name;
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			zTree.selectNode(treeNode);
			return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			zTree.selectNode(treeNode);
            if(!confirm("确认删除 节点 -- " + treeNode.name + " 吗？"))
            {
                return false;
            }
            removeNode(treeNode);
			return false;
		}
		function onRemove(e, treeId, treeNode) {
			//treeNode.getParentNode().isParent = true;
			//zTree.refresh();
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				setTimeout(function(){
                    treeNode.name=beforeEditNameStr;
                    zTree.editName(treeNode)
                }, 10);
				return true;
			}
			return true;
		}
		function onRename(e, treeId, treeNode, isCancel) {
            renameNode(treeNode, treeNode.name, beforeEditNameStr);
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		}
        function onDrag(e, treeId, treeNode) {
        }
        function beforeDrop(treeId, treeNodes, targetNode, moveType) {
            if(null == targetNode)
            {
                alert("所有文章都应该属于[文章]下！");
                return false;
            }
            if(!targetNode.isParent && ("inner"==moveType)) {
                alert("该对象非文章类，不能放入");
                return false;
            }
            moveNode(treeNodes, targetNode, moveType, beforeParentNode);
            return false;
        }
        function onDrop(event, treeId, treeNodes, targetNode, moveType) {
        }
        function beforeDblClick(treeId, treeNode)
        {
            if(!treeNode.isParent)
            {
                document.getElementById("article_iframe").src="showArticle.jsp?id=" + treeNode.id;
            }
            return true;
        }
		function showRemoveBtn(treeId, treeNode) {
			if(1 == treeNode.id) return false;
			return true;//!treeNode.isFirstNode;
		}
		function showRenameBtn(treeId, treeNode) {
			if(1 == treeNode.id) return false;
			return true;//!treeNode.isLastNode;
		}
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addArticleTypeBtn_"+treeNode.tId).length>0
                    || $("#addArticleBtn_"+treeNode.tId).length>0
                    || $("#editArticleBtn_"+treeNode.tId).length>0) return;
			var addStr = "";
			if(treeNode.isParent) {//判是文章类 才能新增子文章类 和 文章
				addStr += "<span class='button addArticleType' id='addArticleTypeBtn_" + treeNode.tId
					+ "' title='新增文章分类' onfocus='this.blur();'></span>"
					+ "<span class='button addArticle' id='addArticleBtn_" + treeNode.tId
					+ "' title='新增文章' onfocus='this.blur();'></span>";
			} else
            {
                addStr += "<span class='button editArticle' id='editArticleBtn_" + treeNode.tId
                        + "' title='编辑文章' onfocus='this.blur();'></span>";
            }
			sObj.after(addStr);
			var btn = $("#addArticleTypeBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
                addNode(treeNode, true, "文章类名");
				return false;
			});
            var btn2 = $("#addArticleBtn_"+treeNode.tId);
            if (btn2) btn2.bind("click", function(){
                addNode(treeNode, false, "文章名");
                return false;
            });
            var btn3 = $("#editArticleBtn_"+treeNode.tId);
            if (btn3) btn3.bind("click", function(){
                document.getElementById("article_iframe").src="editArticle.jsp?id=" + treeNode.id;
                return false;
            });
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addArticleTypeBtn_"+treeNode.tId).unbind().remove();
            $("#addArticleBtn_"+treeNode.tId).unbind().remove();
            $("#editArticleBtn_"+treeNode.tId).unbind().remove();
		};
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("treeDemo");
		});
	</SCRIPT>
	<style type="text/css">
.ztree li span.button.addArticleType {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.addArticle {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.editArticle {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}

.ztree li span.button.pIcon01_ico_open{margin-right:2px; background: url(css/zTreeStyle/img/diy/1_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.pIcon01_ico_close{margin-right:2px; background: url(css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.pIcon02_ico_open, 
.ztree li span.button.pIcon02_ico_close{margin-right:2px; background: url(css/zTreeStyle/img/diy/2.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon01_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon02_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/4.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon03_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/5.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon04_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/6.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon05_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/7.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon06_ico_docu{margin-right:2px; background: url(css/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	</style>
</HEAD>

<BODY>
<h1>文章页_后台</h1>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right" style="border: 0px solid red;">
        <iframe id="article_iframe"
                src=""
                style="border: 0px solid red; width: 100%; height: 100%;"
                ></iframe>
	</div>
</div>
</BODY>
</HTML>