package alexhao.codeheu.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import alexhao.codeheu.Application.iApplication;
import alexhao.codeheu.Async.MainTask;
import alexhao.codeheu.Dialog.VerifypicDialog;
import alexhao.codeheu.R;

public class LoginActivity extends Activity implements VerifypicDialog.RefreshVerifyandLoginListener {

    private Button btn_confirm;
    private EditText edt_userno,edt_userpsd;
    private CheckBox cb_remember;
    private SharedPreferences spf;
    private SharedPreferences.Editor editor ;
    private VerifypicDialog verifypicDialog;
    public BroadcastReceiver connectionReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initViews();
        checkNetState();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(edt_userno.getText().toString())) {
                    Toast toast1 = Toast.makeText(iApplication.getContext(), "请填写学号", Toast.LENGTH_SHORT);
                    toast1.show();
                } else if ("".equals(edt_userpsd.getText().toString())) {
                    Toast toast1 = Toast.makeText(iApplication.getContext(), "请填写密码~", Toast.LENGTH_SHORT);
                    toast1.show();
                } else
                    verifyBeforeLogin(); //登录前验证
            }
        });

        /**
         * checkbox的记住密码功能
         */
        cb_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ((compoundButton.getId() == R.id.cb_remember) && (b == true)) {
                    editor.putString("user_no", edt_userno.getText().toString());
                    editor.putString("user_password", edt_userpsd.getText().toString());
                    editor.putString("isChecked", "1");
                    editor.commit();
                } else {
                    editor.putString("user_no", "");
                    editor.putString("user_password", "");
                    editor.putString("isChecked", "0");
                    editor.commit();
                    edt_userno.setText("");
                    edt_userpsd.setText("");
                }
            }
        });
    }

    public void initViews()
    {
        btn_confirm= (Button) findViewById(R.id.btn_confirm);
        edt_userno= (EditText) findViewById(R.id.edt_userno);
        edt_userpsd= (EditText) findViewById(R.id.edt_userpsd);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);
        spf= getSharedPreferences("userConfig", MODE_PRIVATE);
        editor= spf.edit();
        if(!(spf.getString("user_no","").equals("")))
            if(!(spf.getString("user_password","").equals("")))
                if((spf.getString("isChecked","").equals("1")))
                {
                    edt_userno.setText(spf.getString("user_no",""));
                    edt_userpsd.setText(spf.getString("user_password",""));
                    cb_remember.setChecked(true);
                }
    }


    private void verifyBeforeLogin() {

        verifypicDialog = new VerifypicDialog(this,edt_userno.getText().toString(),edt_userpsd.getText().toString());
        verifypicDialog.setOnRefreshVerifyListener(this);
        verifypicDialog.show();
        MainTask mainTask = new MainTask(verifypicDialog);
        mainTask.execute(0); //0：获取验证图片

    }


    @Override
    public void refreshVerifyPic()
    {
        MainTask mainTask = new MainTask(verifypicDialog);
        mainTask.execute(0);
    }

    @Override
    public void login() {
        MainTask mainTask = new MainTask(verifypicDialog);
        mainTask.execute(1); //1: 登录
    }

    @Override
    public void loginfinish() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * 广播检测网络情况
     */
    public void checkNetState() {

        connectionReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "当前无网络连接~", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (mobNetInfo.isConnected()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "正在使用移动数据~", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

    }
}
