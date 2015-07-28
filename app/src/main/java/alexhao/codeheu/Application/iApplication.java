package alexhao.codeheu.Application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import alexhao.codeheu.Database.RunDatabaseHelper;

/**
 * 存放全局变量
 * Created by ALexHao on 15/7/7.
 */
public class iApplication extends Application{

    private static String Addr_verifyPic = "http://jw.hrbeu.edu.cn/ACTIONVALIDATERANDOMPICTURE.APPPROCESS";
    private static String Addr_login = "http://jw.hrbeu.edu.cn/ACTIONLOGON.APPPROCESS";
    private static String Addr_grade= "http://jw.hrbeu.edu.cn/ACTIONQUERYSTUDENTSCORE.APPPROCESS";
    private static String responseCookie="cookie is null";
    private static String sessionId="sessionid is null";
    private static String verifyNum="verifynum is null";
    private static Context context;
    private SharedPreferences spf;
    private SharedPreferences.Editor editor ;
    private static iApplication iapp;
    private RunDatabaseHelper dbHelper;

    public iApplication() {
        super();
    }

    public static iApplication getInstance()
    {
        return iapp;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        iapp=this;
        context = getApplicationContext();
        spf= getSharedPreferences("userConfig", MODE_PRIVATE);
        editor= spf.edit();
        if(spf.getString("init_success","0")=="0")
            initUserConfig();
        dbHelper = new RunDatabaseHelper(this,"CodeHeu.db",null,1);
        dbHelper.getReadableDatabase();
    }

    public static Context getContext()
    {
        return context;
    }

    public  String getAddr_login()
    {
        return  Addr_login;
    }
    public  String getAddr_verifyPic()
    {
        return  Addr_verifyPic;
    }
    public  String getResponseCookie()
    {
        return  responseCookie;
    }
    public  String getSessionId()
    {
        return  sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setResponseCookie(String responseCookie) {
        this.responseCookie = responseCookie;
    }

    public String getVerifyNum() {
        return verifyNum;
    }

    public void setVerifyNum(String verifyNum) {
        this.verifyNum = verifyNum;
    }

    public  String getAddr_grade()
    {
        return  Addr_grade;
    }


    /**
     * 初始化用户配置信息
     */
    public void initUserConfig() {
        editor.putString("init_success", "1"); //判断初始化是否成功
        editor.putString("user_no","");
        editor.putString("user_name", "");
        editor.putString("user_password", "");
        editor.putString("isChecked","0");
        editor.commit();
    }

}
