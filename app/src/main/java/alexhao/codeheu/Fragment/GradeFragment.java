package alexhao.codeheu.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import alexhao.codeheu.Application.iApplication;
import alexhao.codeheu.Async.MainTask;
import alexhao.codeheu.R;

public class GradeFragment extends Fragment implements View.OnClickListener{

    private TextView tv_name;
    private TextView tv_funcmenu;
    public TextView grade;
    private iApplication iapp;


    public static GradeFragment newInstance() {
        GradeFragment fragment = new GradeFragment();
        return fragment;
    }

    public GradeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("----getget", "nnnnnnnn");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_grade, container, false);
        tv_name= (TextView)view.findViewById(R.id.tv_name);
        tv_funcmenu= (TextView)view.findViewById(R.id.tv_funcmenu);
        grade = (TextView)view.findViewById(R.id.grade);
        tv_funcmenu.setText("选择学期");
        tv_funcmenu.setOnClickListener(this);
        iapp = iApplication.getInstance();
        MainTask mainTask = new MainTask(GradeFragment.this);
        mainTask.execute(2);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_funcmenu:
                Toast tos=Toast.makeText(iApplication.getContext(), "选择学期", Toast.LENGTH_SHORT);
                tos.show();

        }
    }
}
