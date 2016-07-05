package wyy.com.learningproject.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by weiyuyang on 16-6-1.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter{
    private static final String[] pageTitle = new String[]{"日报","热门","主题"};


    private List<Fragment> list;
    public FragmentPagerAdapter(FragmentManager fm , List<Fragment> list) {
        super(fm);
        this.list = list;

    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle[position];
    }
}
