package com.persy.learnandroid.utils;

import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BindingAdapters {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    @BindingAdapter("renderHtml")
    public static void setRenderHtml(TextView textView, String htmlText) {
        if (textView == null) return;
        if (htmlText != null) {
            textView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText("");
        }
    }

    @BindingAdapter("goneUnless")
    public static void setGoneUnless(View view, boolean visible) {
        if (view == null) return;
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("errorMessage")
    public static void setErrorMessage(TextInputLayout textInputLayout, String error) {
        if (textInputLayout == null) return;
        textInputLayout.setError(error);
    }

    @BindingAdapter("formattedDate")
    public static void setFormattedDate(TextView textView, Date date) {
        if (textView == null) return;
        if (date != null) {
            textView.setText(DATE_FORMAT.format(date));
        } else {
            textView.setText("");
        }
    }

    @BindingAdapter(value = {"spinnerEntries", "spinnerLayout", "spinnerDropdownLayout"}, requireAll = false)
    public static void setSpinnerEntries(Spinner spinner, List<String> entries, Integer itemLayout, Integer dropdownLayout) {
        if (spinner == null || entries == null) return;

        int layoutId = (itemLayout != null) ? itemLayout : android.R.layout.simple_spinner_item;
        int dropLayoutId = (dropdownLayout != null) ? dropdownLayout : android.R.layout.simple_spinner_dropdown_item;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(), layoutId, entries);
        adapter.setDropDownViewResource(dropLayoutId);
        spinner.setAdapter(adapter);
    }
}
