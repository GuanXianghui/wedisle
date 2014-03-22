package com.gxx.record.utils;

import com.gxx.record.dao.wedisle.WedisleMainStepDao;
import com.gxx.record.dao.wedisle.WedisleRelaFriendDao;
import com.gxx.record.dao.wedisle.WedisleSeatInfoDao;
import com.gxx.record.entities.wedisle.*;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * ������
 * User: Gxx
 * Time: 2013-11-02 13:14
 */
public class WedisleUtils
{
    /**
     * ��ʽ����json����[A,B,C,D,E]
     * @param list
     * @param column
     * @return
     */
    public static String formatRelaFriendJsonArrayStr(List list, String column)
    {
        String result = "[";
        if(null != list)
        {
            for(int i=0;i<list.size();i++)
            {
                WedisleRelaFriend friend = (WedisleRelaFriend)list.get(i);
                if(i > 0)
                {
                    result += ",";
                }
                if("name".equalsIgnoreCase(column))
                {
                    result += "'";
                    result += friend.getName();
                    result += "'";
                } else if("num".equalsIgnoreCase(column))
                {
                    result += friend.getNum();
                } else if("seat".equalsIgnoreCase(column))
                {
                    result += friend.getSeat();
                }
            }
        }
        result += "]";
        return result;
    }

    /**
     * ��WedisleGoodDay������Json����
     * @param goodDay
     * @return
     */
    public static String getJsonFromGoodDay(WedisleGoodDay goodDay)
    {
        String json = "{date: '" + goodDay.getDate() + "', " +
                "gongli: '" + goodDay.getGongli() + "', " +
                "nongli: '" + goodDay.getNongli() + "', " +
                "chong: '" + goodDay.getChong() + "', " +
                "yi: '" + goodDay.getYi() + "', " +
                "ji: '" + goodDay.getJi() + "', " +
                "wuxing: '" + goodDay.getWuxing() + "', " +
                "cisui: '" + goodDay.getCisui() + "', " +
                "pengzubaiji: '" + goodDay.getPengzubaiji() + "'}";
        return json;
    }

    /**
     * ��List<WedisleGoodDay>������Json����
     * @param wedisleGoodDays
     * @return
     */
    public static String getJsonFromWedisleGoodDays(List<WedisleGoodDay> wedisleGoodDays)
    {
        String json = "[";
        for(int i=0;i<wedisleGoodDays.size();i++)
        {
            if(i > 0)
            {
                json += ",";
            }
            json += getJsonFromGoodDay(wedisleGoodDays.get(i));
        }
        json += "]";
        return json;
    }

    /**
     * ��WedisleWorkDay������Json����
     * @param workDay
     * @return
     */
    public static String getJsonFromWorkDay(WedisleWorkDay workDay)
    {
        String json = "{date: '" + workDay.getDate() + "', " +
                "isWorkDay: " + workDay.isWorkDay() + "}";
        return json;
    }

    /**
     * ��List<WedisleWorkDay>������Json����
     * @param wedisleWorkDays
     * @return
     */
    public static String getJsonFromWedisleWorkDays(List<WedisleWorkDay> wedisleWorkDays)
    {
        String json = "[";
        for(int i=0;i<wedisleWorkDays.size();i++)
        {
            if(i > 0)
            {
                json += ",";
            }
            json += getJsonFromWorkDay(wedisleWorkDays.get(i));
        }
        json += "]";
        return json;
    }

    /**
     * ��WedisleMarryRegist������Json����
     * @param regist
     * @return
     */
    public static String getJsonFromMarryRegist(WedisleMarryRegist regist)
    {
        String json = "{id: '" + regist.getId() + "', " +
                "name: '" + regist.getName() + "', " +
                "time: '" + regist.getTime() + "', " +
                "phone: '" + regist.getPhone() + "', " +
                "place: '" + regist.getPlace().replaceAll("'", "\"") + "', " +
                "imageSrc: '" + regist.getImageSrc().replaceAll("'", "\"") + "', " +
                "bigImageSrc: '" + regist.getBigImageSrc() + "'}";
        return json;
    }

    /**
     * ��List<WedisleMarryRegist>������Json����
     * @param wedisleMarryRegists
     * @return
     */
    public static String getJsonFromWedisleMarryRegists(List<WedisleMarryRegist> wedisleMarryRegists)
    {
        String json = "[";
        for(int i=0;i<wedisleMarryRegists.size();i++)
        {
            if(i > 0)
            {
                json += ",";
            }
            json += getJsonFromMarryRegist(wedisleMarryRegists.get(i));
        }
        json += "]";
        return json;
    }

    /**
     * ��WedisleRelaFriend������Json����
     * @param friend
     * @return
     */
    public static String getJsonFromRelaFriend(WedisleRelaFriend friend)
    {
        String json = "{name: '" + friend.getName().replaceAll("'", "\"") + "', " +
                "num: " + friend.getNum() + ", " +
                "relationship: '" + friend.getRelationship().replaceAll("'", "\"") + "', " +
                "email: '" + friend.getEmail().replaceAll("'", "\"") + "', " +
                "phone: '" + friend.getPhone().replaceAll("'", "\"") + "', " +
                "place: '" + friend.getPlace().replaceAll("'", "\"") + "', " +
                "resv: '" + friend.getResv().replaceAll("'", "\"") + "', " +
                "helpGroup: '" + friend.getHelpGroup().replaceAll("'", "\"") + "', " +
                "worker: '" + friend.getWorker().replaceAll("'", "\"") + "', " +
                "seat: " + friend.getSeat() + "}";
        return json;
    }

    /**
     * ��List<WedisleRelaFriend>������Json����
     * @param wedisleRelaFriends
     * @return
     */
    public static String getJsonFromWedisleRelaFriends(List<WedisleRelaFriend> wedisleRelaFriends)
    {
        String json = "[";
        for(int i=0;i<wedisleRelaFriends.size();i++)
        {
            if(i > 0)
            {
                json += ",";
            }
            json += getJsonFromRelaFriend(wedisleRelaFriends.get(i));
        }
        json += "]";
        return json;
    }

    /**
     * ͳ�����Ѹ���
     * @param wedisleRelaFriends
     * @return
     */
    public static int countFriends(List<WedisleRelaFriend> wedisleRelaFriends){
        int count = 0;
        for(int i=0;i<wedisleRelaFriends.size();i++)
        {
            count += wedisleRelaFriends.get(i).getNum();
        }
        return count;
    }

    /**
     * ͳ�ư�����ϯλ�����Ѹ���
     * @param wedisleRelaFriends
     * @return
     */
    public static int countFriendsWithSeat(List<WedisleRelaFriend> wedisleRelaFriends){
        int count = 0;
        for(int i=0;i<wedisleRelaFriends.size();i++)
        {
            if(wedisleRelaFriends.get(i).getSeat() > 0){
                count += wedisleRelaFriends.get(i).getNum();
            }
        }
        return count;
    }

    /**
     * ͳ��ʣ�µ�ϯλ����
     * @param userId
     * @return
     * @throws Exception
     */
    public static int countLeftSeat(int userId) throws Exception {
        WedisleSeatInfo seatInfo = WedisleSeatInfoDao.getSeatInfoByUserId(userId);
        int totalSeats = seatInfo.getTableCount() * seatInfo.getNumEveryTable();
        int friendsWithSeat = countFriendsWithSeat(WedisleRelaFriendDao.queryRelaFriendsByUserId(userId));
        return totalSeats - friendsWithSeat;
    }

    /**
     * ͳ�ư���Ÿ���
     * @param userId
     * @return
     * @throws Exception
     */
    public static int countHelpGroup(int userId) throws Exception {
        List<WedisleRelaFriend> friends = WedisleRelaFriendDao.queryRelaFriendsByUserId(userId);
        int count = 0;
        for (WedisleRelaFriend friend : friends){
            if(StringUtils.isNotBlank(friend.getHelpGroup())){
                count += 1;
            }
        }
        return count;
    }

    /**
     * ͳ�ƹ�����Ա����
     * @param userId
     * @return
     * @throws Exception
     */
    public static int countWorker(int userId) throws Exception {
        List<WedisleRelaFriend> friends = WedisleRelaFriendDao.queryRelaFriendsByUserId(userId);
        int count = 0;
        for (WedisleRelaFriend friend : friends){
            if(StringUtils.isNotBlank(friend.getWorker())){
                count += 1;
            }
        }
        return count;
    }

    /**
     * ��������WedisleMainStep(��indexId˳������)������Json����
     * @return
     */
    public static String getJsonFromAllWedisleMainSteps() throws Exception
    {
        List<WedisleMainStep> steps = WedisleMainStepDao.queryAllWedisleMainSteps();
        String json = "{";
        for(int i=1;i<=5;i++)
        {
            if(json.length() > 1){
                json += ",";
            }
            json += "level_1_" + i + ":[";
            int count = 0;
            for(int j=0;j<steps.size();j++)
            {
                if(steps.get(j).getLevel() == 2 && steps.get(j).getPid() == i){
                    if(count > 0){
                        json += ",";
                    }
                    json += "{id:" + steps.get(j).getId() + ", name:'" + steps.get(j).getName() + "'}";
                    count ++;
                }
            }
            json += "]";
        }
        for(int i=0;i<steps.size();i++)
        {
            if(steps.get(i).getLevel() == 3){
                continue;
            }
            if(json.length() > 1){
                json += ",";
            }
            json += "level_2_" + steps.get(i).getId() + ":[";
            int count = 0;
            for(int j=0;j<steps.size();j++)
            {
                if(steps.get(j).getLevel() == 3 && steps.get(j).getPid() == steps.get(i).getId()){
                    if(count > 0){
                        json += ",";
                    }
                    json += "{id:" + steps.get(j).getId() + ", article_id:" + steps.get(j).getArticleId()
                            + ", name:'" + steps.get(j).getName() + "'}";
                    count ++;
                }
            }
            json += "]";
        }
        json += "}";
        return json;
    }

    /**
     * ��WedisleRemind������Json����
     * @param remind
     * @return
     */
    public static String getJsonFromRemind(WedisleRemind remind)
    {
        String json = "{id: " + remind.getId() + ", " +
                "userId: " + remind.getUserId() + ", " +
                "event: '" + remind.getEvent() + "', " +
                "date: '" + remind.getDate() + "', " +
                "remindType: '" + remind.getRemindType() + "', " +
                "remindDate: '" + remind.getRemindDate() + "', " +
                "remindTime: '" + remind.getRemindTime() + "'}";
        return json;
    }

    /**
     * ��List<WedisleRemind>������Json����
     * @param wedisleReminds
     * @return
     */
    public static String getJsonFromWedisleReminds(List<WedisleRemind> wedisleReminds)
    {
        String json = "[";
        for(int i=0;i<wedisleReminds.size();i++)
        {
            if(i > 0)
            {
                json += ",";
            }
            json += getJsonFromRemind(wedisleReminds.get(i));
        }
        json += "]";
        return json;
    }
}
