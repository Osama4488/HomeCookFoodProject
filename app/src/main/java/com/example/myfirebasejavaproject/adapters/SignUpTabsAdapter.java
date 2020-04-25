package com.example.myfirebasejavaproject.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class SignUpTabsAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private List<String> titleList;
    public SignUpTabsAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList,List<String> titleList, int behavior) {
        super(fm, behavior);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
