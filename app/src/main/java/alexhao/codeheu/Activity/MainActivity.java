package alexhao.codeheu.Activity;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alexhao.codeheu.Adapter.MyPagerAdapter;
import alexhao.codeheu.Application.iApplication;
import alexhao.codeheu.Fragment.CourseFragment;
import alexhao.codeheu.Fragment.CreditFragment;
import alexhao.codeheu.Fragment.GradeFragment;
import alexhao.codeheu.Fragment.NewsFragment;
import alexhao.codeheu.R;
import alexhao.codeheu.Util.ZoomOutPageTransformer;

public class MainActivity extends FragmentActivity {

    private iApplication iapp;
    private ViewPager mPager;//页卡内容
    private TextView tv_news,tv_grade,tv_credit,tv_course;
    private List<Fragment> fragmentlist;
    private int currIndex=0;
    private long exitTime = 0;

    public FragmentManager fm;
    public FragmentTransaction ft;
    public NewsFragment  newsfrag;
    public GradeFragment gradefrag;
    public CreditFragment creditfrag;
    public CourseFragment coursefrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initViews();
        initFragments();
        initEvents();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (newsfrag == null && fragment instanceof NewsFragment) {
            newsfrag = (NewsFragment) fragment;
        }else if (gradefrag == null && fragment instanceof GradeFragment) {
            gradefrag = (GradeFragment) fragment;
        }else if ( creditfrag == null && fragment instanceof CreditFragment) {
            creditfrag = (CreditFragment) fragment;
        }else if (coursefrag == null && fragment instanceof CourseFragment) {
            coursefrag = (CourseFragment) fragment;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按就要说再见咯T^T~", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 初始化视图
     */
    public void initViews()
    {
        mPager= (ViewPager) findViewById(R.id.viewpager);
        tv_course= (TextView) findViewById(R.id.tv_course);
        tv_credit= (TextView) findViewById(R.id.tv_credit);
        tv_news= (TextView) findViewById(R.id.tv_news);
        tv_grade= (TextView) findViewById(R.id.tv_grade);

    }

    /**
     * 初始化事件
     */
    public void initEvents()
    {
        tv_news.setOnClickListener(new MyOnTabClickListener(0));
        tv_grade.setOnClickListener(new MyOnTabClickListener(1));
        tv_credit.setOnClickListener(new MyOnTabClickListener(2));
        tv_course.setOnClickListener(new MyOnTabClickListener(3));

    }

    /**
     * 初始化fragment和viewpager并设置监听
     */
    public void initFragments()
    {
        fm= getSupportFragmentManager();
        ft=fm.beginTransaction();
        fragmentlist=new ArrayList<Fragment>();

        gradefrag=GradeFragment.newInstance();
        newsfrag=NewsFragment.newInstance();
        coursefrag=CourseFragment.newInstance();
        creditfrag=CreditFragment.newInstance();

        fragmentlist.add(newsfrag);
        fragmentlist.add(gradefrag);
        fragmentlist.add(creditfrag);
        fragmentlist.add(coursefrag);

        mPager.setOffscreenPageLimit(4);
        mPager.setAdapter(new MyPagerAdapter(fm, fragmentlist));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPagerChangeListener());
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    /**
     * 重置文字颜色
     */
    public void resetTextColor()
    {
        tv_news.setTextColor(getResources().getColor(R.color.wordcolor_original));
        tv_grade.setTextColor(getResources().getColor(R.color.wordcolor_original));
        tv_credit.setTextColor(getResources().getColor(R.color.wordcolor_original));
        tv_course.setTextColor(getResources().getColor(R.color.wordcolor_original));
    }

    /**
     * 隐藏fragment
     * @param ft
     */
    private void hideFragments(FragmentTransaction ft) {
        if(newsfrag!=null)
        {
            ft.hide(newsfrag);
        }
        if(gradefrag!=null)
        {
            ft.hide(gradefrag);
        }
        if(creditfrag!=null)
        {
            ft.hide(creditfrag);
        }
        if(coursefrag!=null)
        {
            ft.hide(coursefrag);
        }
    }

    /**
     *  Tab点击监听
     */
    public class MyOnTabClickListener implements View.OnClickListener {
        private int index = 0;
        public MyOnTabClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }


    /**
     * viewpager切换监听
     */
    public class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int pos) {

            hideFragments(ft);
            resetTextColor();
            switch(pos)
            {
                case 0:
                    if (currIndex == 1) {
                        tv_news.setTextColor(getResources().getColor(R.color.white));

                    } else if (currIndex == 2) {
                        tv_news.setTextColor(getResources().getColor(R.color.white));

                    }
                    else {
                        tv_news.setTextColor(getResources().getColor(R.color.white));

                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        tv_grade.setTextColor(getResources().getColor(R.color.white));

                    } else if (currIndex == 2) {
                        tv_grade.setTextColor(getResources().getColor(R.color.white));

                    }
                    else  {
                        tv_grade.setTextColor(getResources().getColor(R.color.white));

                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        tv_credit.setTextColor(getResources().getColor(R.color.white));

                    } else if (currIndex == 1) {
                        tv_credit.setTextColor(getResources().getColor(R.color.white));

                    }
                    else {
                        tv_credit.setTextColor(getResources().getColor(R.color.white));

                    }
                    break;

                case 3:
                    if (currIndex == 0) {
                        tv_course.setTextColor(getResources().getColor(R.color.white));

                    } else if (currIndex == 1) {
                        tv_course.setTextColor(getResources().getColor(R.color.white));

                    }
                    else  {
                        tv_course.setTextColor(getResources().getColor(R.color.white));

                    }

                    break;

            }
            currIndex = pos;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
