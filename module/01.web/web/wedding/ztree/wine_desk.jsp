<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<HTML>
<HEAD>
<TITLE> ZTREE DEMO - drag with other DOM</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/demo.css" type="text/css">
<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="js/jquery.ztree.exedit-3.5.js"></script>
<SCRIPT type="text/javascript">

var sizeEveryTable=3;
var nodeCount = 0;

function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addPeopleBtn_"+treeNode.tId).length>0) return;
    var addStr = "";
    if(treeNode.isParent) {//判是文章类 才能新增子文章类 和 文章
        addStr += "<span class='button addPeople' id='addPeopleBtn_" + treeNode.tId
                + "' title='新增宾客' onfocus='this.blur();'></span>";
    }
    sObj.after(addStr);
    var btn = $("#addPeopleBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");//树控件的根
        zTree.addNodes(treeNode, {id:nodeCount++, pId:treeNode.id, name:"宾客名"});
        return false;
    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addPeopleBtn_"+treeNode.tId).unbind().remove();
};

    <!--
    var MoveTest = {
        errorMsg: "放错了...请选择正确的类别！",
        curTarget: null,
        curTmpTarget: null,
        noSel: function() {
            try {
                window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
            } catch(e){}
        },
        dragTree2Dom: function(treeId, treeNodes) {
            return !treeNodes[0].isParent;
        },
        prevTree: function(treeId, treeNodes, targetNode) {
            return !targetNode.isParent && targetNode.parentTId == treeNodes[0].parentTId;
        },
        nextTree: function(treeId, treeNodes, targetNode) {
            return !targetNode.isParent && targetNode.parentTId == treeNodes[0].parentTId;
        },
        innerTree: function(treeId, treeNodes, targetNode) {
            return targetNode!=null && targetNode.isParent && targetNode.tId == treeNodes[0].parentTId;
        },
        dropTree2Dom: function(e, treeId, treeNodes, targetNode, moveType) {
            var tableId;
            var nowSize;
            var domId = "dom_" + treeNodes[0].getParentNode().id;
            if(e.target.id.indexOf("dom_")==0)//放在桌子内
            {
                tableId = e.target.id;
                nowSize = getPeopleCountFromNodes($("#" + e.target.id)[0].childNodes);//$("#" + e.target.id)[0].childNodes.length;
            }
            if(e.target.parentNode.id.indexOf("dom_")==0)//放在桌子内的宾客上
            {
                tableId = e.target.parentNode.id;
                nowSize = getPeopleCountFromNodes($("#" + e.target.parentNode.id)[0].childNodes);//$("#" + e.target.parentNode.id)[0].childNodes.length;
            }
            if(nowSize-1+getPeopleCountFromName(treeNodes[0].name) > sizeEveryTable)
            {
                alert("桌子已坐满");
                return false;
            }
            //if (moveType == null && (domId == e.target.id || $(e.target).parents("#" + domId).length > 0)) {
            if (moveType == null && (e.target.id.indexOf("dom_")==0 || e.target.parentNode.id.indexOf("dom_")==0)) {
                //alert($("#" + domId)[0].childNodes.length);

                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.removeNode(treeNodes[0]);

//                var newDom = $("span[domId=" + treeNodes[0].id + "]");
//                if (newDom.length > 0) {
//                    newDom.removeClass("domBtn_Disabled");
//                    newDom.addClass("domBtn");
//                } else {
                $("#" + tableId).append("<span class='domBtn' domId='" + treeNodes[0].id + "'>" + treeNodes[0].name + "</span>");
                if(treeNodes[0].name.indexOf('*')>0)
                {
                    var number = (treeNodes[0].name.substring(treeNodes[0].name.indexOf('*')+1));
                    if(checkNum(number))
                    {
                        addTotalPeople(number);
                    } else
                    {
                        addTotalPeople(1);
                    }
                } else
                {
                    addTotalPeople(1);
                }

//                }
                //MoveTest.updateType();
            } else if ( $(e.target).parents(".domBtnDiv").length > 0) {
                alert(MoveTest.errorMsg);
            }
        },
        dom2Tree: function(e, treeId, treeNode) {
            var target = MoveTest.curTarget, tmpTarget = MoveTest.curTmpTarget;
            if (!target) return;
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"), parentNode;
            if (treeNode != null && treeNode.isParent) {// && "dom_" + treeNode.id == target.parent().attr("id")
                parentNode = treeNode;
            } else if (treeNode != null && !treeNode.isParent) {// && "dom_" + treeNode.getParentNode().id == target.parent().attr("id")
                parentNode = treeNode.getParentNode();
            }

            if (tmpTarget) tmpTarget.remove();
            if (!!parentNode) {
                var nodes = zTree.addNodes(parentNode, {id:target.attr("domId"), name: target.text()});
                zTree.selectNode(nodes[0]);
                target.remove();
                if(target.text().indexOf('*')>0)
                {
                    var number = (target.text().substring(target.text().indexOf('*')+1));
                    if(checkNum(number))
                    {
                        delTotalPeople(number);
                    } else
                    {
                        delTotalPeople(1);
                    }
                } else
                {
                    delTotalPeople(1);
                }
            } else {
                target.removeClass("domBtn_Disabled");
                target.addClass("domBtn");
                alert("请放入宾客中！");
            }
            MoveTest.updateType();
            MoveTest.curTarget = null;
            MoveTest.curTmpTarget = null;
        },
        updateType: function() {
            /** 更新(子节点个数)
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getNodes();
            for (var i=0, l=nodes.length; i<l; i++) {
                var num = nodes[i].children ? nodes[i].children.length : 0;
                nodes[i].name = nodes[i].name.replace(/ \(.*\)/gi, "") + " (" + num + ")";
                zTree.updateNode(nodes[i]);
            }
             */
        },
        bindDom: function() {
            $(".domBtnDiv").bind("mousedown", MoveTest.bindMouseDown);
        },
        bindMouseDown: function(e) {
            var target = e.target;
            if (target!=null && target.className=="domBtn") {
                var doc = $(document), target = $(target),
                        docScrollTop = doc.scrollTop(),
                        docScrollLeft = doc.scrollLeft();
                target.addClass("domBtn_Disabled");
                target.removeClass("domBtn");
                curDom = $("<span class='dom_tmp domBtn'>" + target.text() + "</span>");
                curDom.appendTo("body");

                curDom.css({
                    "top": (e.clientY + docScrollTop + 3) + "px",
                    "left": (e.clientX + docScrollLeft + 3) + "px"
                });
                MoveTest.curTarget = target;
                MoveTest.curTmpTarget = curDom;

                doc.bind("mousemove", MoveTest.bindMouseMove);
                doc.bind("mouseup", MoveTest.bindMouseUp);
                doc.bind("selectstart", MoveTest.docSelect);
            }
            if(e.preventDefault) {
                e.preventDefault();
            }
        },
        bindMouseMove: function(e) {
            MoveTest.noSel();
            var doc = $(document),
                    docScrollTop = doc.scrollTop(),
                    docScrollLeft = doc.scrollLeft(),
                    tmpTarget = MoveTest.curTmpTarget;
            if (tmpTarget) {
                tmpTarget.css({
                    "top": (e.clientY + docScrollTop + 3) + "px",
                    "left": (e.clientX + docScrollLeft + 3) + "px"
                });
            }
            return false;
        },
        bindMouseUp: function(e) {
            var doc = $(document);
            doc.unbind("mousemove", MoveTest.bindMouseMove);
            doc.unbind("mouseup", MoveTest.bindMouseUp);
            doc.unbind("selectstart", MoveTest.docSelect);

            var target = MoveTest.curTarget, tmpTarget = MoveTest.curTmpTarget;
            if (tmpTarget) tmpTarget.remove();

            var tableId;
            var nowSize;
            if(e.target.id.indexOf("dom_")==0)//放在桌子内
            {
                tableId = e.target.id;
                nowSize = getPeopleCountFromNodes($("#" + e.target.id)[0].childNodes);//$("#" + e.target.id)[0].childNodes.length;
            }
            if(e.target.parentNode.id.indexOf("dom_")==0)//放在桌子内的宾客上
            {
                tableId = e.target.parentNode.id;
                nowSize = getPeopleCountFromNodes($("#" + e.target.parentNode.id)[0].childNodes);//$("#" + e.target.parentNode.id)[0].childNodes.length;
            }
            if(nowSize-1+(getPeopleCountFromName(target.text())) > sizeEveryTable)
            {
                target.removeClass("domBtn_Disabled");
                target.addClass("domBtn");
                alert("桌子已坐满");
                return false;
            }
            if ((e.target.id.indexOf("dom_")==0 || e.target.parentNode.id.indexOf("dom_")==0)) {
                $("#" + tableId).append("<span class='domBtn' domId='" + nodeCount++ + "'>" + target.text() + "</span>");
                if (target) {
                    target.remove();
                }
            } else
            {
                target.removeClass("domBtn_Disabled");
                target.addClass("domBtn");
            }

            if ($(e.target).parents("#treeDemo").length == 0) {
                //if (target) {
                //    target.remove();
                //}
                MoveTest.curTarget = null;
                MoveTest.curTmpTarget = null;
            }
        },
        bindSelect: function() {
            return false;
        }
    };

    var setting = {
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            selectedMulti: false
        },
        edit: {
            enable: true,
            showRemoveBtn: showRemoveBtn,
            showRenameBtn: showRenameBtn,
            drag: {
                prev: MoveTest.prevTree,
                next: MoveTest.nextTree,
                inner: MoveTest.innerTree
            }
        },
        data: {
            keep: {
                parent: true,
                leaf: true
            },
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: MoveTest.dragTree2Dom,
            onDrop: MoveTest.dropTree2Dom,
            onMouseUp: MoveTest.dom2Tree
        }
    };

    var zNodes =[
        { id:1, pId:0, name:"宾客(拖家带口请重命名加*N)", isParent: true, open:true},
        { id:10, pId:1, name:"路人甲"},
        { id:19, pId:1, name:"路人乙"}
    ];

    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        MoveTest.updateType();
        MoveTest.bindDom();
    });

function showRemoveBtn(treeId, treeNode) {
    if(1 == treeNode.id) return false;
    return true;//!treeNode.isFirstNode;
}

function showRenameBtn(treeId, treeNode) {
    if(1 == treeNode.id) return false;
    return true;//!treeNode.isLastNode;
}

function addTable()
{
    var temp = document.getElementById("table_count_id").innerText;
    document.getElementById("table_count_id").innerText = ++temp;
    document.getElementById("tables").innerHTML = document.getElementById("tables").innerHTML
                    + ("<div id='dom_" + temp + "' class='categoryDiv'><div align='center'>" + temp + "</div></div>");
}

function delTable()
{
    var temp = document.getElementById("table_count_id").innerText;
    if($("#dom_" + (temp))[0].childNodes.length > 1)
    {
        alert("最后一桌有客人，不能删掉这桌！");
        return false;
    }
    document.getElementById("table_count_id").innerText = --temp;
    $("#dom_" + (temp+1)).remove();
}

function addPeopleEveryTable()
{
    var temp = document.getElementById("people_every_table_count_id").innerText;
    document.getElementById("people_every_table_count_id").innerText = ++temp;
    sizeEveryTable ++;
}

function delPeopleEveryTable()
{
    var temp = document.getElementById("people_every_table_count_id").innerText;
    if(temp == 0)
    {
        alert("每桌人数不能为负数！");
        return false;
    }

    var tableCount = document.getElementById("table_count_id").innerText;
    for(var i=0;i<tableCount;i++)
    {
        if(getPeopleCountFromNodes($("#dom_" + (i+1))[0].childNodes)-1 == temp)
        {
            alert("第" + (i+1) + "桌已经坐满，不能减少每桌人数！");
            return false;
        }
    }

    document.getElementById("people_every_table_count_id").innerText = --temp;
    sizeEveryTable --;
}

function addTotalPeople(num)
{
    var temp = document.getElementById("total_perple_id").innerText;
    document.getElementById("total_perple_id").innerText = --temp + ++num;
}

function delTotalPeople(num)
{
    var temp = document.getElementById("total_perple_id").innerText;
    document.getElementById("total_perple_id").innerText = temp++ - num--;
}

function getPeopleCountFromName(name)
{
    if(name.indexOf('*')>0)
    {
        var number = (name.substring(name.indexOf('*')+1));
        if(checkNum(number))
        {
            return --number+1;
        } else
        {
            return 1;
        }
    } else
    {
        return 1;
    }
}

function getPeopleCountFromNodes(nodes)
{
    var count = 0;
    for(var i=0;i<nodes.length;i++)
    {
        count += getPeopleCountFromName(nodes[i].innerHTML);
    }
    return count;
}

function checkNum(number)
{
    var reg = /^\d+$/;
    return number.match(reg);
}
    //-->
</SCRIPT>
<style type="text/css">
    .dom_line {margin:2px;border-bottom:1px gray dotted;height:1px}
    .domBtnDiv {display:block;padding:2px;}
    .categoryDiv {display:inline-block; width:200px; height: 150px;background-color:powderblue;border:1px gray dotted; float: left; margin-left: 1%; margin-top: 1%;}
    .domBtn {display:inline-block;cursor:pointer;padding:2px;margin:2px 10px;border:1px gray solid;background-color:#FFE6B0}
    .domBtn_Disabled {display:inline-block;cursor:default;padding:2px;margin:2px 10px;border:1px gray solid;background-color:#DFDFDF;color:#999999}
    .dom_tmp {position:absolute;font-size:12px;}

    .ztree li span.button.addPeople {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
</HEAD>

<BODY>
<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
    <div class="right">
        <ul class="info">
            <li class="title">
                <div align="center">
                    <table border="1" style="border: 1px solid gray;">
                        <tr>
                            <td colspan="4" align="center">席位安排</td>
                        </tr>
                        <tr>
                            <td>当前桌数</td>
                            <td id="table_count_id">4</td>
                            <td><input type="button" value="+" onclick="addTable()"></td>
                            <td><input type="button" value="-" onclick="delTable()"></td>
                        </tr>
                        <tr>
                            <td>每桌人数</td>
                            <td id="people_every_table_count_id">3</td>
                            <td><input type="button" value="+" onclick="addPeopleEveryTable()"></td>
                            <td><input type="button" value="-" onclick="delPeopleEveryTable()"></td>
                        </tr>
                        <tr>
                            <td>已坐人数</td>
                            <td align="center" colspan="3" id="total_perple_id">0</td>
                        </tr>
                    </table>
                </div>
                <div id="tables" class="domBtnDiv">
                    <div id="dom_1" class="categoryDiv"><div align="center">1</div></div>
                    <div id="dom_2" class="categoryDiv"><div align="center">2</div></div>
                    <div id="dom_3" class="categoryDiv"><div align="center">3</div></div>
                    <div id="dom_4" class="categoryDiv"><div align="center">4</div></div>
                </div>
            </li>
        </ul>
    </div>
</div>
</BODY>
</HTML>