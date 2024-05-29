package com.example.bai1;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {
    private FragmentActivity fragmentActivity;
    private static final int NUM_PAGES = 2; // Số lượng trang
    private int[] colors ;
    public MyAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        colors = new int[]{ContextCompat.getColor(fragmentActivity, R.color.color_page1),
                ContextCompat.getColor(fragmentActivity, R.color.color_page2)};
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Tạo và trả về fragment cho từng trang
        return MyFragment.newInstance(position,colors[position]);
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
