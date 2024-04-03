package com.example.twistytails.slider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.twistytails.R;
import com.example.twistytails.SelectLoginActivity;

import java.util.ArrayList;

public class GetStartedActivity extends AppCompatActivity {

    private TextView skip;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private AlertDialog dialog;
    private TextView btnSliderNext;
    private ArrayList<SliderItems> models;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_started);

        skip = findViewById(R.id.txtSkip);
        viewPager = findViewById(R.id.viewPager);
        btnSliderNext = findViewById(R.id.btnSliderNext);
        sliderDotspanel = findViewById(R.id.sliderDots);

        models = new ArrayList<>();
        models.add(new SliderItems(R.drawable.get_started_img, "Welcome To Pet Care", "All types of services for your pet in one place,instantly searchable"));
        models.add(new SliderItems(R.drawable.get_started_img, "Proven experts", "We interview every specialist before they get to work"));
        models.add(new SliderItems(R.drawable.get_started_img, "Reliable reviews", "A review can be left only by a user who used the service"));

        SliderAdapter adapter = new SliderAdapter(GetStartedActivity.this, models);
        viewPager.setAdapter(adapter);

        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.non_active_bg));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_bg));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2) {
                    skip.setVisibility(View.INVISIBLE);
                    btnSliderNext.setText("Get Started");
                } else {
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(GetStartedActivity.this, R.drawable.non_active_bg));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(GetStartedActivity.this, R.drawable.active_bg));
            }
        });

        btnSliderNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.show();
                if (viewPager.getCurrentItem() == 2) {
                    skip.setVisibility(View.INVISIBLE);
//                    dialog.dismiss();
                    Intent intent = new Intent(GetStartedActivity.this, SelectLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
//                    dialog.dismiss();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.show();
                Intent intent = new Intent(GetStartedActivity.this, SelectLoginActivity.class);
                startActivity(intent);
                finish();
//                dialog.dismiss();
            }
        });
    }
}