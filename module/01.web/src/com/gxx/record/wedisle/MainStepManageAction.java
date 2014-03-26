/**
 * File Name:    MainStepManageAction.java
 *
 * File Desc:    首页步骤管理处理类
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
 * History:      2014-2-21 created by Gxx
 */
package com.gxx.record.wedisle;

import com.gxx.record.dao.wedisle.WedisleMainStepDao;
import com.gxx.record.entities.wedisle.WedisleMainStep;
import com.gxx.record.utils.WedisleUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 首页步骤管理处理类
 * @author Gxx
 * @version 1.0
 */
public class MainStepManageAction extends ActionSupport
{
    public String type;
    public String id;
    public String name;
    public String articleId;
    public String message;


    public String execute() throws Exception
    {
        System.out.println("type=[" + type + "]" + ",id=[" + id + "]" + ",name=[" + name + "]"
                + ",articleId=[" + articleId + "]");
        if(StringUtils.isBlank(type))
        {
            message = "操作类型type不能为空";
            System.out.println(message);
            return ERROR;
        }
        //如果文章号为空，填0，取不到文章内容
        if(StringUtils.isBlank(articleId)){
            articleId = "0";
        }

        try{
            //新增二级菜单
            if("add_level_2".equals(type))
            {
                int maxIndexId = WedisleMainStepDao.getMaxIndexFromLevelAndPid(2, Integer.parseInt(id));
                WedisleMainStep step = new WedisleMainStep(Integer.parseInt(id), 2, maxIndexId+1, name, 0);
                WedisleMainStepDao.insertWedisleMainStep(step);
                message = "新增二级菜单成功！";
                System.out.println(message);
                return SUCCESS;
            }

            //修改级别2
            if("update_level_2".equals(type))
            {
                WedisleMainStep step = WedisleMainStepDao.getWedisleMainStepById(Integer.parseInt(id));
                step.setName(name);
                WedisleMainStepDao.updateWedisleMainStep(step);
                message = "修改二级菜单成功！";
                System.out.println(message);
                return SUCCESS;
            }

            //上移级别2
            if("up_level_2".equals(type) || "up_level_3".equals(type))
            {
                WedisleMainStep step = WedisleMainStepDao.getWedisleMainStepById(Integer.parseInt(id));
                List<WedisleMainStep> allSteps = WedisleMainStepDao.queryAllWedisleMainSteps();
                int upStepIndexId = 0;
                int upStepId = 0;
                for(WedisleMainStep temp : allSteps){
                    if(temp.getLevel() != step.getLevel() || temp.getPid() != step.getPid()){
                        continue;
                    }
                    //说明在下方或者就是该对象
                    if(temp.getIndexId() >= step.getIndexId()){
                        continue;
                    }
                    //准备第一次赋值
                    if(0 == upStepIndexId){
                        upStepIndexId = temp.getIndexId();
                        upStepId = temp.getId();
                        continue;
                    }
                    if(temp.getIndexId() > upStepIndexId){
                        upStepIndexId = temp.getIndexId();
                        upStepId = temp.getId();
                    }
                }
                //没找到上面的元素
                if(upStepIndexId == 0){
                    message = "上移菜单成功！";
                    System.out.println(message);
                    return SUCCESS;
                }
                //先修改上一个二级菜单
                WedisleMainStep upStep = WedisleMainStepDao.getWedisleMainStepById(upStepId);
                upStep.setIndexId(step.getIndexId());
                WedisleMainStepDao.updateWedisleMainStep(upStep);
                //再修改下一个二级菜单
                step.setIndexId(upStepIndexId);
                WedisleMainStepDao.updateWedisleMainStep(step);
                message = "上移菜单成功！";
                System.out.println(message);
                return SUCCESS;
            }

            //下移级别2
            if("down_level_2".equals(type) || "down_level_3".equals(type))
            {
                WedisleMainStep step = WedisleMainStepDao.getWedisleMainStepById(Integer.parseInt(id));
                List<WedisleMainStep> allSteps = WedisleMainStepDao.queryAllWedisleMainSteps();
                int downStepIndexId = 0;
                int downStepId = 0;
                for(WedisleMainStep temp : allSteps){
                    if(temp.getLevel() != step.getLevel() || temp.getPid() != step.getPid()){
                        continue;
                    }
                    //说明在下方或者就是该对象
                    if(temp.getIndexId() <= step.getIndexId()){
                        continue;
                    }
                    //准备第一次赋值
                    if(0 == downStepIndexId){
                        downStepIndexId = temp.getIndexId();
                        downStepId = temp.getId();
                        continue;
                    }
                    if(temp.getIndexId() < downStepIndexId){
                        downStepIndexId = temp.getIndexId();
                        downStepId = temp.getId();
                    }
                }
                //没找到上面的元素
                if(downStepIndexId == 0){
                    message = "下移菜单成功！";
                    System.out.println(message);
                    return SUCCESS;
                }
                //先修改下一个二级菜单
                WedisleMainStep downStep = WedisleMainStepDao.getWedisleMainStepById(downStepId);
                downStep.setIndexId(step.getIndexId());
                WedisleMainStepDao.updateWedisleMainStep(downStep);
                //再修改上一个二级菜单
                step.setIndexId(downStepIndexId);
                WedisleMainStepDao.updateWedisleMainStep(step);
                message = "下移菜单成功！";
                System.out.println(message);
                return SUCCESS;
            }

            //删除级别2
            if("delete_level_2".equals(type))
            {
                List<WedisleMainStep> steps = WedisleMainStepDao.queryLevel3ByLevel2Id(Integer.parseInt(id));
                for(WedisleMainStep temp : steps){
                    WedisleMainStepDao.deleteWedisleMainStep(temp);
                }
                WedisleMainStep step = WedisleMainStepDao.getWedisleMainStepById(Integer.parseInt(id));
                WedisleMainStepDao.deleteWedisleMainStep(step);
                //刷新所有用户步骤
                WedisleUtils.refreshWedisleUserSteps();
                message = "删除二级菜单成功！";
                System.out.println(message);
                return SUCCESS;
            }

            //新增三级菜单
            if("add_level_3".equals(type))
            {
                int maxIndexId = WedisleMainStepDao.getMaxIndexFromLevelAndPid(3, Integer.parseInt(id));
                WedisleMainStep step = new WedisleMainStep(Integer.parseInt(id), 3, maxIndexId+1, name, Integer.parseInt(articleId));
                WedisleMainStepDao.insertWedisleMainStep(step);
                //刷新所有用户步骤
                WedisleUtils.refreshWedisleUserSteps();
                message = "新增三级菜单成功！";
                System.out.println(message);
                return SUCCESS;
            }

            //修改级别3
            if("update_level_3".equals(type))
            {
                WedisleMainStep step = WedisleMainStepDao.getWedisleMainStepById(Integer.parseInt(id));
                step.setName(name);
                step.setArticleId(Integer.parseInt(articleId));
                WedisleMainStepDao.updateWedisleMainStep(step);
                message = "修改三级菜单成功！";
                System.out.println(message);
                return SUCCESS;
            }

            //删除级别3
            if("delete_level_3".equals(type))
            {
                WedisleMainStep step = WedisleMainStepDao.getWedisleMainStepById(Integer.parseInt(id));
                WedisleMainStepDao.deleteWedisleMainStep(step);
                //刷新所有用户步骤
                WedisleUtils.refreshWedisleUserSteps();
                message = "删除三级菜单成功！";
                System.out.println(message);
                return SUCCESS;
            }
        } catch (Exception e){
            e.printStackTrace();
            message = "异常发生！";
            System.out.println(message);
            return ERROR;
        }

        System.out.println(message);
        return SUCCESS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
