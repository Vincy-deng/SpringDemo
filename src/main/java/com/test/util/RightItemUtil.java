package com.test.util;

import com.alibaba.fastjson.JSONArray;
import com.test.vo.TxRightItemListEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author administrator
 * @date 2021/3/22 16:24
 */
public class
RightItemUtil {
    //////////////////////页面权限解析//////////////////////////////////////////////////
    /**
     * 权限解析  比如传递到后台的权限是: rights: 1,3,5,7,8,10,11,12,13,16,17,18,19,20,21,22,23,24,25,34,35  普通的权限码
     * 可以转换成通用的权限码: relRights: 170,61,255,3,12
     * 转换后保存到数据库 account_property字段
     * **/
    /*
     * 权限解析
     *
     * @param rightArray
     * @param rightItem
     * @return
     */
    public static short[] GrantRight(short[] rightArray, int rightItem)
    {
        int iHigh = rightArray.length - 1;
        int posByte = rightItem / 8;
        int posBit = rightItem % 8;
        if (posByte > iHigh)
        {
            int Result = posByte + 1;
            short[] temprightarray = new short[Result];
            System.arraycopy(rightArray, 0, temprightarray, 0, rightArray.length);
            rightArray = new short[Result];
            System.arraycopy(temprightarray, 0, rightArray, 0, temprightarray.length);
            for (int i = iHigh + 1; i <= posByte; i++)
            {
                rightArray[i] = 0;
            }
        }
        rightArray[posByte] = (short) (rightArray[posByte] | (1 << (posBit)));
        return rightArray;
    }
    public static String ByteArrayRightToStringRight(short[] byteRight)
    {
        String Result = "";
        for (int i = 0; i < byteRight.length; i++)
        {
            Result = Result + byteRight[i] + ",";
        }
        return Result.substring(0, Result.length() - 1);
    }
    //demo
    /*public static void main(String[] args) {

        String relRights = null;
        String rights = "1,3,5,7,8,10,11,12,13,16,17,18,19,20,21,22,23,24,25,34,35";
        if (rights != null && !"".equals(rights.trim()))
        {
            String[] rightsArr = rights.split(",");
            short[] rightShort = new short[] {};
            for (String str : rightsArr)
            {
                rightShort =  RightItemUtil.GrantRight(rightShort, Integer.parseInt(str));
            }
            relRights = RightItemUtil.ByteArrayRightToStringRight(rightShort);
        }
        System.out.println(relRights);//解析成数据库中的权限

    }*/
    ///////////////////////数据库权限解析////////////////////////////////////////////////////
    /**
     *
     * **/
    /*
     * 是否存在某个权限
     *
     * @param rights
     *            班长的权限
     * @param rightitem
     *            特定权限码
     * @return
     */
    public static boolean isRightGranted(String rights, int rightitem)
    {
        if (rightitem < 0 || rights == null || "".equals(rights.trim()))
            return false;
        StringTokenizer strtokey = new StringTokenizer(rights, ",");
        byte[] rightArray = new byte[strtokey.countTokens()];
        int i = 0;
        while (strtokey.hasMoreTokens())
        {
            String s = strtokey.nextToken();
            int t = Integer.parseInt(s.trim());
            rightArray[i] = (byte) t;
            i++;
        }
        int posByte = rightitem / 8;
        int posBit = rightitem % 8;
        if (posByte > rightArray.length - 1)
        {
            return false;
        }
        return (rightArray[posByte] & (1 << posBit)) != 0;
    }

    public static void main(String[] args) {
        //数据库中查询到的所有权限集合
        List<TxRightItemListEntity> rightItemList = new ArrayList<>();
        String rightItemJson = "[{\"code\":1,\"id\":1,\"type\":0,\"desc\":\"查询\"},{\"code\":3,\"id\":3,\"type\":0,\"desc\":\"声卡放音\"},{\"code\":5,\"id\":5,\"type\":0,\"desc\":\"实时监控\"},{\"code\":7,\"id\":7,\"type\":0,\"desc\":\"统计\"},{\"code\":8,\"id\":8,\"type\":0,\"desc\":\"报表\"},{\"code\":10,\"id\":10,\"type\":0,\"desc\":\"系统配置\"},{\"code\":11,\"id\":11,\"type\":0,\"desc\":\"备份\"},{\"code\":12,\"id\":12,\"type\":0,\"desc\":\"声卡监听\"},{\"code\":13,\"id\":13,\"type\":0,\"desc\":\"修改密码\"},{\"code\":16,\"id\":16,\"type\":0,\"desc\":\"播放录屏\"},{\"code\":17,\"id\":17,\"type\":0,\"desc\":\"实时录屏\"},{\"code\":18,\"id\":18,\"type\":0,\"desc\":\"批量导录音\"},{\"code\":19,\"id\":19,\"type\":0,\"desc\":\"账号管理\"},{\"code\":20,\"id\":20,\"type\":0,\"desc\":\"分机配置\"},{\"code\":21,\"id\":21,\"type\":0,\"desc\":\"报警监控\"},{\"code\":22,\"id\":22,\"type\":0,\"desc\":\"修改业务信息\"},{\"code\":23,\"id\":23,\"type\":0,\"desc\":\"录屏配置\"},{\"code\":24,\"id\":24,\"type\":0,\"desc\":\"查询班长操作记录\"},{\"code\":25,\"id\":25,\"type\":0,\"desc\":\"提取语音文件\"},{\"code\":34,\"id\":34,\"type\":0,\"desc\":\"等级标注\"}," +
                "{\"code\":35,\"id\":35,\"type\":0,\"desc\":\"查看历史注释\"}]";
        rightItemList = JSONArray.parseArray(rightItemJson,TxRightItemListEntity.class);
        List<Integer> rightInts = new ArrayList<Integer>();
        //数据库中查到的权限
        String rights = "170,32";

        for (TxRightItemListEntity rightItem : rightItemList)
        {
            if (RightItemUtil.isRightGranted(rights, rightItem.getRightCode()))
            {
                rightInts.add(rightItem.getRightCode());
            }
        }
        System.out.println(rightInts);
    }


}
