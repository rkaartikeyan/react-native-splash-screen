package org.devio.rn.splashscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
    private static Dialog mSplashDialog;
    private static WeakReference<Activity> mActivity;

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final int themeResId) {
        if (activity == null) return;
        mActivity = new WeakReference<Activity>(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {
                    mSplashDialog = new Dialog(activity, themeResId);
                    mSplashDialog.setContentView(R.layout.launch_screen);
                    mSplashDialog.setCancelable(false);

                    if (!mSplashDialog.isShowing()) {
                        mSplashDialog.show();
                    }

                }
            }
        });
    }

    public static void showSecond(final Activity activity, final int themeResId) {
        if (activity == null) return;


        mActivity = new WeakReference<Activity>(activity);



        try {
            activity.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            activity.getActionBar().hide();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {
                    try {
                        mSplashDialog = new Dialog(activity, themeResId);
                        mSplashDialog.setContentView(R.layout.launch_screen);
                        mSplashDialog.setCancelable(false);

                        final ViewGroup viewGroup = mSplashDialog.findViewById (android.R.id.content);//ViewGroup) ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);

                        ViewGroup childInside = (ViewGroup) ((ViewGroup)viewGroup).getChildAt(0);

                        ProgressBar nextChildD = (ProgressBar) childInside.getChildAt(0);
                        ImageView imageView = (ImageView) childInside.getChildAt(1);

                        nextChildD.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.INVISIBLE);

                        if (!mSplashDialog.isShowing()) {
                            mSplashDialog.show();
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final boolean fullScreen) {
        //int resourceId = fullScreen ? R.style.SplashScreen_Fullscreen : R.style.SplashScreen_SplashTheme;
        int resourceId=R.style.SplashScreen_SplashTheme;
        show(activity, resourceId);
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity) {
        show(activity, false);
    }

    public static void show(final Activity activity,boolean data,String from) {
        //int resourceId = data ? R.style.SplashScreen_Fullscreen : R.style.SplashScreen_SplashTheme;
        int resourceId=R.style.SplashScreen_Fullscreen;
        showSecond(activity, resourceId);
    }

    /**
     * 关闭启动屏
     */
    public static void hide(Activity activity) {
        if (activity == null) {
            if (mActivity == null) {
                return;
            }
            activity = mActivity.get();
        }

        if (activity == null) return;

        final Activity _activity = activity;

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog != null && mSplashDialog.isShowing()) {
                    boolean isDestroyed = false;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        isDestroyed = _activity.isDestroyed();
                    }

                    if (!_activity.isFinishing() && !isDestroyed) {
                        mSplashDialog.dismiss();
                    }
                    mSplashDialog = null;
                }
            }
        });
    }
}
