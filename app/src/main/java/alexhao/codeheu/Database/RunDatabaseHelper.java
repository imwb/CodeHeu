package alexhao.codeheu.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Sqlite助手类
 * 负责打开关闭数据库等基本事务
 * Created by ALexHao on 15/7/17.
 */

public class RunDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String CREATE_GRADE = "create table grade(" +
            "c_type text" +                     //课程性质
            "c_no text" +                       //课程编号
            "c_name text" +                     //课程名称
            "e_type text" +                     //考试类型
            "c_time text" +                     //学时
            "c_credit text" +                   //学分
            "g_type text" +                     //成绩类型
            "g_endterm text" +                  //期末成绩
            "g_overall text" +                  //总评成绩
            "termbelonged text primary key" +   //所属学期
            ")";

    /**
     *
     * @param context  上下文
     * @param name     数据库名称
     * @param factory  cursor工厂 一般为null
     * @param version  数据库版本
     */
    public RunDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GRADE);
        Log.d("---SQL创建成功","SQL创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {

    }
}
