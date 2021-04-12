package com.ifixhubke.kibu_olx.ui.fragments.details;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentImage3Binding;

 
public class Image3Fragment extends Fragment {

    String imageUrl;
    FragmentImage3Binding binding;
    int col;
    ScaleGestureDetector SGD;

    public Image3Fragment(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImage3Binding.inflate(inflater, container, false);
        View view = binding.getRoot();


        Toolbar toolbar = getActivity().findViewById(R.id.pictureBrowsingtoolbar);
        ViewPager2 viewPager2 = getActivity().findViewById(R.id.pictureBrowsingViewPager);

        // Picasso.get().load(imageUrl).placeholder(R.drawable.ic_image_placeholder).into(binding.imageViewImage3);

        Glide.with(view)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.progressBar5.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.progressBar5.setVisibility(View.INVISIBLE);
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        Palette.Builder builder = Palette.from(bitmap);
                        builder.generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@Nullable Palette palette) {
                                assert palette != null;
                                col = palette.getDominantColor(0);
                                binding.imageLayout3.setBackgroundColor(col);


                                //toolbar.setBackgroundColor(col);
                                //getActivity().getWindow().setStatusBarColor(col);

                            }
                        });

                        return false;
                    }
                })
                .into(binding.imageViewImage3);


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SGD.onTouchEvent(motionEvent);
                return true;
            }
        });

        return view;
    }
}