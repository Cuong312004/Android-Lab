package com.example.bai1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {
    private static final String ARG_PAGE = "page";
    private static final String ARG_COLOR = "color";
    private int pageNumber;
    private int color;

    public static MyFragment newInstance(int page, int color) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt(ARG_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARG_PAGE);
        color = getArguments().getInt(ARG_COLOR);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        rootView.setBackgroundColor(color);
        // Bạn có thể thực hiện các thao tác khác trên giao diện ở đây nếu cần
        return rootView;
    }
}
