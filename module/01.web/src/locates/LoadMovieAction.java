package locates;

import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesListenDao;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesListen;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 加载录像
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class LoadMovieAction extends ActionSupport implements ServletResponseAware
{
    String imei;
    boolean isError;
    String errorMsg;
    String message;
    List<LocatesListen> listens;
    HttpServletResponse response;


    public String execute() throws Exception
    {
        System.out.println("加载录像~imei=[" + imei + "]");

        if(StringUtils.isBlank(imei))
        {
            isError = true;
            errorMsg = "imei用户名不能为空!";
            writeResult();
            return null;
        }

        LocatesUser user = LocatesUserDao.getUserByImei(imei);
        if(null == user)
        {
            isError = true;
            errorMsg = "该用户[" + imei + "]未注册!";
            writeResult();
            return null;
        }

        listens =  LocatesListenDao.queryNotListeningListensByImei(imei);
        message = "加载录像成功!";
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
        String json = "{isError: " + isError + ", " +
                "errorMsg: '" + StringUtils.trimToEmpty(errorMsg) + "', " +
                "message: '" + message + "', " +
                "imei: '" + imei + "', " +
                "movies: " + getMoviesJson() + "," +
                "movieIds: " + getMovieIdsJson() + "}";
        System.out.println(json);
        return json;
    }

    /**
     * 构造录像Json对象
     * @return
     */
    public String getMoviesJson()
    {
        String json = "[";
        for(int i=0;i<listens.size();i++)
        {
            if(i>0)
            {
                json += ",";
            }
            json += "'"
                    + ServiceDataUtil.getCNDateTime(ServiceDataUtil.getDateTime(listens.get(i).getStartDateTime()))
                    + "~"
                    + ServiceDataUtil.getCNDateTime(ServiceDataUtil.getDateTime(listens.get(i).getEndDateTime()))
                    + "'";
        }
        json += "]";
        return json;
    }

    /**
     * 构造录像IDJson对象
     * @return
     */
    public String getMovieIdsJson()
    {
        String json = "[";
        for(int i=0;i<listens.size();i++)
        {
            if(i>0)
            {
                json += ",";
            }
            json += "'"
                    + listens.get(i).getListenId()
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public List<LocatesListen> getListens() {
        return listens;
    }

    public void setListens(List<LocatesListen> listens) {
        this.listens = listens;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
