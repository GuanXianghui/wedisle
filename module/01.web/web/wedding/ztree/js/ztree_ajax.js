//=ztree与服务器后端ajax交互===============================================
var SUCCESS_STR = "success";//成功编码
var SUCCESS = "1";//成功编码
var ERROR = "0";//失败编码
var zTree = $.fn.zTree.getZTreeObj("treeDemo");//树控件的根

/**
 * 创建节点(文章类，或者文章)
 * @param treeNode 父节点
 * @param isType 是否文件类
 * @param name 文件类名 或者 文件名
 */
function addNode(treeNode, isType, name)
{
    var pId = treeNode.id;
    $.ajax({
        type: "post",
        async: false,
        url: baseUrl + "addNode",
        data: "pId=" + pId + "&isType=" + isType + "&name=" + name,
        success: function(data, textStatus){
            if((SUCCESS_STR == textStatus) && (null != data) && (data.indexOf("{")==0))
            {
                var dataObj = eval("(" + data + ")");
                zTree.addNodes(treeNode, dataObj);
            } else
            {
                alert("Connection failed,please try again later!");
            }
        },
        error: function(data, textStatus){
            alert("Connection failed,please try again later!");
        }
    });
}

/**
 * 修改节点名称(文章类，或者文章)
 * @param treeNode 节点
 * @param name 新文件类名 或者 新文件名
 * @param beforeEditNameStr 老的文件类名 或者 老的文件名
 */
function renameNode(treeNode, name, beforeEditNameStr)
{
    if("" == name)
    {
        return;
    }
    var id = treeNode.id;
    $.ajax({
        type: "post",
        async: false,
        url: "renameNode",
        data: "id=" + id + "&newName=" + name,
        success: function(data, textStatus){
            if((SUCCESS_STR == textStatus) && (null != data))
            {
                if("true" != data)
                {
                    treeNode.name = beforeEditNameStr;
                    alert("Update name error,please try again later!");
                }
            } else
            {
                alert("Connection failed,please try again later!");
            }
        },
        error: function(data, textStatus){
            alert("Connection failed,please try again later!");
        }
    });
}

/**
 * 删除节点(文章类，或者文章)
 * @param treeNode 节点
 */
function removeNode(treeNode)
{
    var id = treeNode.id;
    $.ajax({
        type: "post",
        async: false,
        url: "removeNode",
        data: "id=" + id,
        success: function(data, textStatus){
            if((SUCCESS_STR == textStatus) && (null != data))
            {
                if("true" == data)
                {
                    var pNode = treeNode.getParentNode();
                    zTree.removeNode(treeNode);
                    pNode.isParent = true;
                    zTree.refresh();
                } else
                {
                    alert("Remove article or article type error,please try again later!");
                }
            } else
            {
                alert("Connection failed,please try again later!");
            }
        },
        error: function(data, textStatus){
            alert("Connection failed,please try again later!");
        }
    });
}

/**
 * 移动节点(文章类，或者文章)
 * @param treeNodes 移动节点数字
 * @param targetNode 目标节点
 * @param moveType 父节点 prev inner next
 * @param beforeParentNode 移动前父节点 强制为父节点 主要防治图标变换
 */
function moveNode(treeNodes, targetNode, moveType, beforeParentNode)
{
    var id = treeNodes[0].id;
    var targetId = targetNode.id;
    $.ajax({
        type: "post",
        async: false,
        url: "moveNode",
        data: "id=" + id + "&targetId=" + targetId + "&moveType=" + moveType,
        success: function(data, textStatus){
            if((SUCCESS_STR == textStatus) && (null != data))
            {
                if("true" == data)
                {
                    zTree.moveNode(targetNode, treeNodes[0], moveType);
                    beforeParentNode.isParent = true;
                    zTree.refresh();
                } else
                {
                    alert("Move article or article type error,please try again later!");
                }
            } else
            {
                alert("Connection failed,please try again later!");
            }
        },
        error: function(data, textStatus){
            alert("Connection failed,please try again later!");
        }
    });
}