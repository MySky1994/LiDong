package com.chestnut.lidong.ui.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chestnut.lidong.R;
import com.chestnut.lidong.callback.Fragment2ActivityCallback;

/**
 * Created by AshZheng on 2016/9/5.
 */
public class FriendFragment extends BaseFragment {
    private FragmentManager fragmentManager;
    private MyFriendFragment myFriendFragment;
    private MyGroupFragment myGroupFragment;
    private MyMessageFragment myMessageFragment;

    private TabLayout tabLayout;

    private Fragment2ActivityCallback myCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View friendFragment = inflater.inflate(R.layout.fragment_friend, container, false);

        initView(friendFragment);
        setFragment();

        return friendFragment;
    }

    private void setFragment() {
        fragmentManager = getFragmentManager();
        selectFragment(0);
    }

    private void selectFragment(int id) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);

        switch (id) {
            case 0:
                if (myFriendFragment == null) {
                    myFriendFragment = new MyFriendFragment();
                    fragmentTransaction.add(R.id.fragment_friend_content_layout, myFriendFragment);
                } else {
                    fragmentTransaction.show(myFriendFragment);
                }
                break;
            case 1:
                if (myGroupFragment == null) {
                    myGroupFragment = new MyGroupFragment();
                    fragmentTransaction.add(R.id.fragment_friend_content_layout, myGroupFragment);
                } else {
                    fragmentTransaction.show(myGroupFragment);
                }
                break;
            case 2:
                if (myMessageFragment == null) {
                    myMessageFragment = new MyMessageFragment();
                    fragmentTransaction.add(R.id.fragment_friend_content_layout, myMessageFragment);
                } else {
                    fragmentTransaction.show(myMessageFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();

    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (myFriendFragment != null) {
            fragmentTransaction.hide(myFriendFragment);
        }
        if (myGroupFragment != null) {
            fragmentTransaction.hide(myGroupFragment);
        }
        if (myMessageFragment != null) {
            fragmentTransaction.hide(myMessageFragment);
        }
    }

    private void initView(View myView) {

        myView.findViewById(R.id.add_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(getActivity(), view);
            }
        });

        tabLayout = (TabLayout) myView.findViewById(R.id.fragment_friend_table_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText("我的好友").setTag(1));
        tabLayout.addTab(tabLayout.newTab().setText("我的跑团").setTag(2));
        tabLayout.addTab(tabLayout.newTab().setText("我的消息").setTag(3));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.mainColorBlue));
        tabLayout.setTabTextColors(getResources().getColor(R.color.wordColorGray), getResources().getColor(R.color.mainColorBlue));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        myView.findViewById(R.id.top_bar_open_drawer_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCallBack.openDrawerLayout();
            }
        });

    }

    private void showPopupWindow(Context context, View view) {

        View contentView = LayoutInflater.from(context).inflate(R.layout.fragment_friend_add_pop_window_layout, null);
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.showAsDropDown(view);
    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof Fragment2ActivityCallback)) {
            throw new IllegalStateException("MyFragment所在的Activity必须实现MyCallBack接口！");
        }
        myCallBack = (Fragment2ActivityCallback) activity;

        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        if (!(context instanceof Fragment2ActivityCallback)) {
            throw new IllegalStateException("MyFragment所在的Activity必须实现MyCallBack接口！");
        }
        myCallBack = (Fragment2ActivityCallback) context;

        super.onAttach(context);
    }
}


