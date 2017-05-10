package com.chestnut.lidong.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.RadioGroup;

import com.chestnut.lidong.R;
import com.chestnut.lidong.callback.Fragment2ActivityCallback;
import com.chestnut.lidong.ui.fragment.AboutMeFragment;
import com.chestnut.lidong.ui.fragment.FriendFragment;
import com.chestnut.lidong.ui.fragment.InfoFragment;
import com.chestnut.lidong.ui.fragment.SportFragment;

import java.lang.reflect.Field;
import java.util.Iterator;

public class MainActivity extends BaseActivity implements Fragment2ActivityCallback {

    private FragmentManager fragmentManager;
    private SportFragment sportFragment;
    private FriendFragment friendFragment;
    private InfoFragment infoFragment;
    private AboutMeFragment aboutMeFragment;

    private RadioGroup fragmentMenuRG;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentMenuRG = (RadioGroup) findViewById(R.id.fragment_menu_rg);
        setFragment();
        setDrawerLayout();
    }



    private void setDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setDrawerLeftEdgeSize(this, drawerLayout, 0.4f);
    }


    private void setFragment() {
        fragmentManager = getFragmentManager();
        fragmentMenuRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectFragment(i);
            }
        });
        selectFragment(R.id.fragment_menu_rbt_fourth);
    }

    private void selectFragment(int checkedId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);

        switch (checkedId) {
            case R.id.fragment_menu_rbt_first:
                if (sportFragment == null) {
                    sportFragment = new SportFragment();
                    fragmentTransaction.add(R.id.fragment_show_window, sportFragment);
                } else {
                    fragmentTransaction.show(sportFragment);
                }
                break;
            case R.id.fragment_menu_rbt_second:
                if (friendFragment == null) {
                    friendFragment = new FriendFragment();
                    fragmentTransaction.add(R.id.fragment_show_window, friendFragment);
                } else {
                    fragmentTransaction.show(friendFragment);
                }
                break;
            case R.id.fragment_menu_rbt_third:
                if (infoFragment == null) {
                    infoFragment = new InfoFragment();
                    fragmentTransaction.add(R.id.fragment_show_window, infoFragment);
                } else {
                    fragmentTransaction.show(infoFragment);
                }
                break;
            case R.id.fragment_menu_rbt_fourth:
                if (aboutMeFragment == null) {
                    aboutMeFragment = new AboutMeFragment();
                    fragmentTransaction.add(R.id.fragment_show_window, aboutMeFragment);
                } else {
                    fragmentTransaction.show(aboutMeFragment);
                }
                break;

            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (sportFragment != null) {
            fragmentTransaction.hide(sportFragment);
        }
        if (infoFragment != null) {
            fragmentTransaction.hide(infoFragment);
        }
        if (aboutMeFragment != null) {
            fragmentTransaction.hide(aboutMeFragment);
        }
        if (friendFragment != null) {
            fragmentTransaction.hide(friendFragment);
        }

    }

    //反射 解决DrawerLayout只能从边缘滑出的问题
    public static void setDrawerLeftEdgeSize(Activity activity,
                                             DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            // find ViewDragHelper and set it accessible
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField(
                    "mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField
                    .get(drawerLayout);
            // find edgesize and set is accessible
            Field edgeSizeField = leftDragger.getClass().getDeclaredField(
                    "mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            // set new edgesize
            // Point displaySize = new Point();
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize,
                    (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }

    @Override
    public void openDrawerLayout() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }
}
