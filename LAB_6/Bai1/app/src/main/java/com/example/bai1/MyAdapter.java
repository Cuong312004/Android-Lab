package com.example.bai1;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
public class MyAdapter extends RecyclerView.Adapter<PagerVH>  {
    private final int[] colors = new int[]{
            android.R.color.black,
            android.R.color.holo_red_light,
            android.R.color.holo_blue_dark,
            android.R.color.holo_purple
    };

    private Context mContext;

    public MyAdapter(Context context) {
        mContext = context;
    }
    @Override
    public PagerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PagerVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_layout, parent, false));
    }

    @Override
    public int getItemCount() {
        return 2; // number of pages
    }

    @Override
    public void onBindViewHolder(PagerVH holder, int position) {
        int currentItemPosition = position % colors.length;
        View itemView = holder.itemView;
        TextView nameTextView = itemView.findViewById(R.id.name);
        TextView positionTextView = itemView.findViewById(R.id.position);
        TextView emailTextView = itemView.findViewById(R.id.email);

        itemView.findViewById(R.id.background).setBackgroundResource(R.color.light_green);

        switch (currentItemPosition) {
            case 0:
                nameTextView.setText(mContext.getString(R.string.elizabeth));
                positionTextView.setText(mContext.getString(R.string.pm));
                emailTextView.setText(mContext.getString(R.string.eli_email));
                itemView.findViewById(R.id.background).setBackgroundResource(R.color.light_pink);
                break;
            case 1:
                nameTextView.setText(mContext.getString(R.string.catherine));
                positionTextView.setText(mContext.getString(R.string.sale));
                emailTextView.setText(mContext.getString(R.string.cat_email));
                itemView.findViewById(R.id.background).setBackgroundResource(R.color.light_blue);
                break;
            default:
                break;
        }
    }
}
class PagerVH extends RecyclerView.ViewHolder {
    public PagerVH(View itemView) {
        super(itemView);
    }
}