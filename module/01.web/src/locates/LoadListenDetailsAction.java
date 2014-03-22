package locates;

import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesListenDao;
import locates.dao.LocatesListenDetailDao;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesListen;
import locates.entities.LocatesListenDetail;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 加载位置监听详细
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class LoadListenDetailsAction extends ActionSupport implements ServletResponseAware
{
    String imei;
    boolean isError;
    String errorMsg;
    String message;
    LocatesUser user;
    LocatesListen listen;
    List<LocatesListenDetail> detailList;
    HttpServletResponse response;


    public String execute() throws Exception
    {
        System.out.println("加载位置监听详细~imei=[" + imei + "]");

        if(StringUtils.isBlank(imei))
        {
            isError = true;
            errorMsg = "imei用户名不能为空!";
            writeResult();
            return null;
        }

        user = LocatesUserDao.getUserByImei(imei);
        if(null == user)
        {
            isError = true;
            errorMsg = "该用户[" + imei + "]未注册!";
            writeResult();
            return null;
        }

        if(!user.isListening())
        {
            isError = true;
            errorMsg = "该用户[" + imei + "]已关闭监听!";
            writeResult();
            return null;
        }
        // 根据定位监听ID查询定位监听
        listen =  LocatesListenDao.getListenById(user.getListenId());
        // 根据定位监听ID查看所有的定位监听详细 观看录像时候/监听时候 会调用
        detailList = LocatesListenDetailDao.queryDetailsByListenId(listen.getListenId());
        message = "加载位置监听详细成功!";
        writeResult();
        return null;
    }

    /**
     * 回写结果
     */
    public void writeResult() throws Exception
    {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(getJson());
    }

    /**
     * 构造Json对象
     * @return
     */
    public String getJson()
    {
        String json;
        if(isError)
        {
            json = "{isError: " + isError + ", " +
                    "errorMsg: '" + StringUtils.trimToEmpty(errorMsg) + "'}";
        } else
        {
            boolean isPenOpen = user.isPenOpen();//是否开启电子围栏 1开启 0关闭
            double penRadius = user.getPenRadius();//电子围栏半径 米位单位
            double penCenterLat = user.getPenCenterLat();//电子围栏中心维度
            double penCenterLng = user.getPenCenterLng();//电子围栏中心经度
            int listenId = user.getListenId();//定位监听ID

            String startDateTime = ServiceDataUtil.getCNDateTime(ServiceDataUtil.getDateTime
                    (listen.getStartDateTime()));//本次监听开始时间
            json = "{isError: " + isError + ", " +
                    "errorMsg: '" + StringUtils.trimToEmpty(errorMsg) + "', " +
                    "message: '" + message + "', " +
                    "imei: '" + imei + "', " +
                    "isPenOpen: " + isPenOpen + "," +
                    "penRadius: '" + (isPenOpen?penRadius:0) + "'," +
                    "penCenterLat: '" + (isPenOpen?penCenterLat:"-") + "'," +
                    "penCenterLng: '" + (isPenOpen?penCenterLng:"-") + "'," +
                    "listenId: '" + listenId + "'," +
                    "startDateTime: '" + startDateTime + "'," +
                    "lats: " + getDetailListLatsJson() + "," +
                    "lngs: " + getDetailListLngsJson() + "," +
                    "dateTimes: " + getDetailListDateTimesJson() +"}";
        }
        System.out.println(json);
        return json;
    }

    /**
     * 构造定位详细维度Json对象
     * @return
     */
    public String getDetailListLatsJson()
    {
        String json = "[";
        for(int i=0;i<detailList.size();i++)
        {
            if(i>0)
            {
                json += ",";
            }
            json += "'"
                    + detailList.get(i).getLat()
                    + "'";
        }
        json += "]";
        return json;
    }

    /**
     * 构造定位详细经度Json对象
     * @return
     */
    public String getDetailListLngsJson()
    {
        String json = "[";
        for(int i=0;i<detailList.size();i++)
        {
            if(i>0)
            {
                json += ",";
            }
            json += "'"
                    + detailList.get(i).getLng()
                    + "'";
        }
        json += "]";
        return json;
    }

    /**
     * 构造定位详细维度Json对象
     * @return
     */
    public String getDetailListDateTimesJson()
    {
        String json = "[";
        for(int i=0;i<detailList.size();i++)
        {
            if(i>0)
            {
                json += ",";
            }
            json += "'"
                    + ServiceDataUtil.getCNDateTime(ServiceDataUtil.getDateTime(detailList.get(i).getDateTime()))
                    + "'";
        }
        json += "]";
        return json;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public LocatesUser getUser() {
        return user;
    }

    public void setUser(LocatesUser user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocatesListen getListen() {
        return listen;
    }

    public void setListen(LocatesListen listen) {
        this.listen = listen;
    }

    public List<LocatesListenDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<LocatesListenDetail> detailList) {
        this.detailList = detailList;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
}
