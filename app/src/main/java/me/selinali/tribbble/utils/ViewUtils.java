package me.selinali.tribbble.utils;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.selinali.tribbble.TribbbleApp;

public final class ViewUtils {

  private ViewUtils() {}

  public static int getStatusBarHeight() {
    return getInternalDimension("status_bar_height");
  }

  public static int getNavigationBarHeight() {
    return getInternalDimension("navigation_bar_height");
  }

  private static int getInternalDimension(String name) {
    Resources res = TribbbleApp.context().getResources();
    int resId = res.getIdentifier(name, "dimen", "android");
    return resId != 0 ? res.getDimensionPixelSize(resId) : 0;
  }

  public static int dpToPx(int dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        TribbbleApp.context().getResources().getDisplayMetrics());
  }

  public static void setTopMargin(View view, int topMargin) {
    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin);
  }

  public static void setBottomMargin(View view, int bottomMargin) {
    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottomMargin);
  }

  public static void setRightMargin(View view, int rightMargin) {
    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    params.setMargins(params.leftMargin, params.topMargin, rightMargin, params.bottomMargin);
  }

  public static void applyColorFilter(ImageView imageView, @ColorRes int resId) {
    imageView.setColorFilter(new PorterDuffColorFilter(
        TribbbleApp.color(resId), PorterDuff.Mode.SRC_ATOP));
  }

  public static void fadeView(View view, boolean show, long duration) {
    if (show && view.getVisibility() == View.INVISIBLE) {
      ViewCompat.animate(view).alpha(1f).setDuration(duration)
          .withStartAction(() -> view.setVisibility(View.VISIBLE));
    } else if (!show && view.getVisibility() == View.VISIBLE) {
      ViewCompat.animate(view).alpha(0f).setDuration(duration)
          .withEndAction(() -> view.setVisibility(View.INVISIBLE));
    }
  }
}