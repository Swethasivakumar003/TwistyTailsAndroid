package com.example.twistytails.slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.twistytails.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private GetStartedActivity context;
    private List<SliderItems> list;

    public SliderAdapter(GetStartedActivity context, List<SliderItems> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SliderItems slider = list.get(position);
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_items, container, false);

        ImageView image = view.findViewById(R.id.slide);
        TextView title = view.findViewById(R.id.infoTitle);
        TextView msg = view.findViewById(R.id.infoMsg);

        image.setImageResource(slider.getImage());
        title.setText(slider.getTitle());
        msg.setText(slider.getDescription());
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}