package com.persy.learnandroid.utils;

import android.widget.RatingBar;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class RatingBarBindingAdapters {

    @BindingAdapter("ratingValue")
    public static void setRatingValue(RatingBar ratingBar, Float newRating) {
        float rating = (newRating == null) ? 0f : newRating;
        if (ratingBar.getRating() != rating) {
            ratingBar.setRating(rating);
        }
    }

    @InverseBindingAdapter(attribute = "ratingValue")
    public static Float getRatingValue(RatingBar ratingBar) {
        return ratingBar.getRating();
    }

    @BindingAdapter("ratingValueAttrChanged")
    public static void setRatingValueListener(RatingBar ratingBar, final InverseBindingListener listener) {
        if (listener == null) {
            ratingBar.setOnRatingBarChangeListener(null);
            return;
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar bar, float rating, boolean fromUser) {
                if (fromUser) {
                    listener.onChange();
                }
            }
        });
    }
}
