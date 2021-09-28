package com.example.mickaelly.ada_ava.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.example.mickaelly.ada_ava.R;

public class SnackBarManager {

    public static final int NONE_DRAWABLE = 0;

    protected Snackbar mSnackBar;
    protected Activity mActivity;

    public SnackBarManager(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void showErrorSnackBar(final View view, final String message, final int time, final Snackbar.Callback callback) {
        showSnackBar(view, message, time, R.drawable.ic_alert, getColor(mActivity, R.color.error_background),
                getColor(mActivity, R.color.colorWhite), callback);
    }

    public void showSuccessSnackBar(final View view, final String message, final int time, final Snackbar.Callback callback) {
        showSnackBar(view, message, time, R.drawable.ic_check_circle_white_24dp, getColor(mActivity, R.color.colorGreen),
                getColor(mActivity, R.color.colorWhite),callback);
    }

    protected void showSnackBar(final View view, final String message, final int time,
                                final int icon, final Integer backgroundColor,
                                final Integer textColor, final Snackbar.Callback callback) {
        if (view != null) {
            Spanned text = getTextFromHtml(message);
            mSnackBar = Snackbar.make(view, text, time);

            View sbView = mSnackBar.getView();
            if (backgroundColor != null) {
                sbView.setBackgroundColor(backgroundColor);
            }
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setCompoundDrawablesWithIntrinsicBounds(icon, NONE_DRAWABLE, NONE_DRAWABLE, NONE_DRAWABLE);
            textView.setCompoundDrawablePadding(mActivity.getResources().getDimensionPixelOffset(R.dimen.fab_margin));
            if (textColor != null) {
                textView.setTextColor(textColor);
            }

            mSnackBar.setCallback(callback);
            mSnackBar.show();
        }
    }

    public boolean isShowingSnackBar() {
        return mSnackBar != null && mSnackBar.isShown();
    }

    public static int getColor(final Context context, final int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorId, null);
        }

        return context.getResources().getColor(colorId);
    }

    public Spanned getTextFromHtml(String text) {
        Spanned spCode;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spCode = Html.fromHtml(text, Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV);
        } else {
            spCode = Html.fromHtml(text);
        }

        return spCode;
    }
}
