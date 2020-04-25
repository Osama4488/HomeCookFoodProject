package com.example.myfirebasejavaproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myfirebasejavaproject.R;

public class sliderAdapter extends PagerAdapter {

    Context content;
    LayoutInflater layoutInflater;
    public sliderAdapter(Context content) {
        this.content = content;
    }

    int images[] = {
            R.drawable.login_logo,
            R.drawable.icon8_train,
            R.drawable.icons8_airplane,
            R.drawable.icons8_bus
    };

    int headings[] = {

            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title,
            R.string.fourth_slide_title

    };
    int descriptios[] = {

            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc,
            R.string.fourth_slide_desc

    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {


        return view == (ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) content.getSystemService(content.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.slider_heading);
        TextView description = view.findViewById(R.id.slider_desc);
        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        description.setText(descriptios[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
