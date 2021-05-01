package com.example.hhhw9.ui.movies;

import android.graphics.Color;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;

import androidx.core.content.ContextCompat;

import static android.os.Build.VERSION_CODES.R;

public class NoLineClickSpan extends URLSpan {
    public NoLineClickSpan(String url) {
        super(url);
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(Color.parseColor("#3399FF"));
    }
}
