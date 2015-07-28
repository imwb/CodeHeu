package alexhao.codeheu.Async;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import alexhao.codeheu.Application.iApplication;
import alexhao.codeheu.Dialog.VerifypicDialog;
import alexhao.codeheu.Fragment.GradeFragment;
import alexhao.codeheu.R;
import alexhao.codeheu.Util.HttpStreamTools;

/**
 * Created by ALexHao on 15/7/16.
 */
public class MainTask extends AsyncTask<Integer,Integer,byte[]> {

    private VerifypicDialog verifypicDialog;
    private GradeFragment gradeFragment;
    public HttpStreamTools hst;
    private iApplication iapp;
    private static int postExecuteType = 0;

    public MainTask() {
        hst = new HttpStreamTools();
        iapp =iApplication.getInstance();
    }
    public MainTask(VerifypicDialog verifypicDialog) {
        this.verifypicDialog = verifypicDialog;
        hst = new HttpStreamTools();
        iapp =iApplication.getInstance();
    }

    public MainTask(GradeFragment gradeFragment) {
        this.gradeFragment = gradeFragment;
        hst = new HttpStreamTools();
        iapp =iApplication.getInstance();
    }


    @Override
    protected byte[] doInBackground(Integer... integers) {

        byte[] result = new byte[2048];

        switch (integers[0]){
            case 0: //0：获取验证图片
                postExecuteType = 0;
                try {
                    result = hst.httpGet(iapp.getAddr_verifyPic(),0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1://1:登录
                postExecuteType = 1;
                try {
                    result = hst.httpPost(iapp.getAddr_login(), verifypicDialog.sb, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2://1:登录后跳转的成绩
                postExecuteType = 2;
                try {
                    result = hst.httpGet(iapp.getAddr_grade(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return result;
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
        switch (postExecuteType)
        {
            case 0:
                verifypicDialog.pb_loadverify.setVisibility(View.INVISIBLE);
                verifypicDialog.img_verifypic.setVisibility(View.VISIBLE);
                if(bytes.length==0){
                    verifypicDialog.img_verifypic.setImageResource(R.drawable.morenpic);
                }else{
                    Bitmap bitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    verifypicDialog.img_verifypic.setImageBitmap(bitmap);
                }
                break;
            case 1:
                break;
            case 2:
                try {
                    String a = hst.byteToString(bytes);
                    if(a==null)
                      gradeFragment.grade.setText("请刷新");
                    else if(gradeFragment==null)
                    {
                        Log.d("frag","kong");
                    }else
                    {
                        gradeFragment.grade.setText(a);
                        Log.d("frag", a);
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        switch (postExecuteType)
        {
            case 0:
            verifypicDialog.pb_loadverify.setVisibility(View.VISIBLE);
            verifypicDialog.img_verifypic.setVisibility(View.INVISIBLE);
            break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        switch (postExecuteType)
        {
            case 0:
                verifypicDialog.pb_loadverify.setVisibility(View.VISIBLE);
                verifypicDialog.img_verifypic.setVisibility(View.INVISIBLE);
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }


}
