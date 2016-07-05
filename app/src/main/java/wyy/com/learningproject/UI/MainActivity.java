package wyy.com.learningproject.UI;

import android.animation.ObjectAnimator;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wyy.com.learningproject.NetUtils.Url;
import wyy.com.learningproject.R;
import wyy.com.learningproject.Utils.DateUtils;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private FloatingActionButton mFloatingActionButton;
    private PopupWindow mPopupWindow;
    private NewsFragment newsFragment;
    private HotFragment hotFragment;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    private List<Fragment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment();

        mFragmentPagerAdapter = new wyy.com.learningproject.Adapter.FragmentPagerAdapter(getSupportFragmentManager(),list);


        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setAdapter(mFragmentPagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                mFloatingActionButton.setAlpha(1-(position+positionOffset));
                mFloatingActionButton.setRotation((1-(position+positionOffset))*360*-1);
                mFloatingActionButton.setScaleX(1-(position+positionOffset));
                mFloatingActionButton.setScaleY(1-(position+positionOffset));
                if (mFloatingActionButton.getAlpha()==0)
                    mFloatingActionButton.setVisibility(View.GONE);
                else if(mFloatingActionButton.getVisibility()==View.GONE)
                    mFloatingActionButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        if(mPopupWindow== null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.calendar, null);
            MaterialCalendarView calendar = (MaterialCalendarView) view.findViewById(R.id.calendar);
            calendar.state().edit().setMaximumDate(new Date(System.currentTimeMillis())).commit();
            calendar.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                    newsFragment.setUrl(Url.getNews(DateUtils.getDate(date.getDate(), "yyyyMMdd")));
                    mPopupWindow.dismiss();
                }
            });
            mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            mPopupWindow.update();
            mPopupWindow.setTouchable(true);
            mPopupWindow.setFocusable(true);
        }
        if(!mPopupWindow.isShowing()){
            mPopupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER,0,0);
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void createFragment(){
        list = new ArrayList<Fragment>();


        newsFragment = NewsFragment.newInstance(Url.getNews(DateUtils.getCurrentDate("yyyyMMdd")));
        list.add(newsFragment);

        hotFragment = HotFragment.newInstance(Url.getHot());
        list.add(hotFragment);

//        ThemesListFragment themesListFragment = ThemesListFragment.newInstance(Url.getThemes());
//        list.add(themesListFragment);

    }
}
