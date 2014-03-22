//=ztree����������ajax����===============================================
var SUCCESS_STR = "success";//�ɹ�����
var SUCCESS = "1";//�ɹ�����
var ERROR = "0";//ʧ�ܱ���
var zTree = $.fn.zTree.getZTreeObj("treeDemo");//���ؼ��ĸ�

/**
 * �����ڵ�(�����࣬��������)
 * @param treeNode ���ڵ�
 * @param isType �Ƿ��ļ���
 * @param name �ļ����� ���� �ļ���
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
 * �޸Ľڵ�����(�����࣬��������)
 * @param treeNode �ڵ�
 * @param name ���ļ����� ���� ���ļ���
 * @param beforeEditNameStr �ϵ��ļ����� ���� �ϵ��ļ���
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
 * ɾ���ڵ�(�����࣬��������)
 * @param treeNode �ڵ�
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
 * �ƶ��ڵ�(�����࣬��������)
 * @param treeNodes �ƶ��ڵ�����
 * @param targetNode Ŀ��ڵ�
 * @param moveType ���ڵ� prev inner next
 * @param beforeParentNode �ƶ�ǰ���ڵ� ǿ��Ϊ���ڵ� ��Ҫ����ͼ��任
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