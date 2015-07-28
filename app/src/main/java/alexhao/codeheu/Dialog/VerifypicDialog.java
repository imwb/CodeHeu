package alexhao.codeheu.Dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import alexhao.codeheu.Activity.LoginActivity;
import alexhao.codeheu.Application.iApplication;
import alexhao.codeheu.R;

/**
 * Created by ALexHao on 15/7/9.
 */
public class VerifypicDialog extends BaseDialog implements View.OnClickListener {

    public ImageView img_verifypic;
    public TextView tv_changeverify;
    public EditText edt_verifynum;
    public Button btn_verifyok;
    public ProgressBar pb_loadverify;
    private iApplication iapp;
    private LoginActivity loginActivity;
    RefreshVerifyandLoginListener refreshVerifyandLoginListener;
    public StringBuilder sb;
    private SharedPreferences spf;
    private SharedPreferences.Editor editor ;
    private String name,psd;

    public VerifypicDialog(Context context,String name,String psd) {
        super(context);
        loginActivity=(LoginActivity)context;
        this.name = name;
        this.psd = psd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCancelable(false);
        setContentView(R.layout.dialog_verifypic);
        initViews();
        initEvents();

        sb = new StringBuilder();
        spf= iApplication.getContext().getSharedPreferences("userConfig", iApplication.getContext().MODE_PRIVATE);
        editor= spf.edit();
        iapp=iApplication.getInstance();

    }

    @Override
    public void initViews() {
        img_verifypic= (ImageView) findViewById(R.id.img_verify);
        tv_changeverify= (TextView) findViewById(R.id.tv_changeVerify);
        edt_verifynum = (EditText) findViewById(R.id.edt_verifynum);
        btn_verifyok= (Button) findViewById(R.id.btn_verifyok);
        pb_loadverify= (ProgressBar) findViewById(R.id.pb_loadverify);

    }

    @Override
    public void initEvents() {
        tv_changeverify.setOnClickListener(this);
        btn_verifyok.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(this!=null)
            this.dismiss();
    }

    /**
     * 拼接post参数
     */
    public void append()
    {
        sb.append(String.format("%s=%s&", "WebUserNO", name));
        sb.append(String.format("%s=%s&", "Password", psd));
        sb.append(String.format("%s=%s", "Agnomen", iapp.getVerifyNum()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_changeVerify:
                refreshVerifyandLoginListener.refreshVerifyPic();
                break;
            case R.id.btn_verifyok:
                if("".equals(edt_verifynum.getText().toString()))
                {
                    Toast toast=Toast.makeText(loginActivity,"请输入验证码~",Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                iapp.setVerifyNum(edt_verifynum.getText().toString());
                append();
                refreshVerifyandLoginListener.login();
                refreshVerifyandLoginListener.loginfinish();
                dismiss();
                break;
        }
    }


    public interface RefreshVerifyandLoginListener{
         void refreshVerifyPic();
         void login();
         void loginfinish();
    }

    public void setOnRefreshVerifyListener(RefreshVerifyandLoginListener refreshVerifyListener)
    {
        this.refreshVerifyandLoginListener=refreshVerifyListener;
    }

}
