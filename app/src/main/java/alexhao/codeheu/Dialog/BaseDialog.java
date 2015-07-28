package alexhao.codeheu.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import alexhao.codeheu.R;

/**
 * Created by ALexHao on 15/7/9.
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(this!=null)
            this.dismiss();
    }

    public abstract void initViews();


    public abstract void initEvents();


}
