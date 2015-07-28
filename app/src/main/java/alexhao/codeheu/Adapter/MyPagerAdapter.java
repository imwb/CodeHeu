package alexhao.codeheu.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ALexHao on 15/7/7.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public List<Fragment> mlistfragment;
    public FragmentManager fm;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> listfragment) {
        super(fm);
        this.fm=fm;
        this.mlistfragment=listfragment;
    }

    @Override
    public int getCount() {
        return mlistfragment.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mlistfragment.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
