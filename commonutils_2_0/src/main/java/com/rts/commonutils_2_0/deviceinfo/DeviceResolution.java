package com.rts.commonutils_2_0.deviceinfo;

import android.app.Activity;
import android.graphics.Typeface;

import android.util.DisplayMetrics;

public class DeviceResolution {

    private int mHeight, mWidth;
    private double mScreenInches;
    private Activity mActivity;
   // public Typeface typefaceRegular,typefacemedium, typefaceSemiBold, typefaceBold, typeLight, typeThin,frutigerltw75black,frutigerltw55roman;
     public Typeface KOMIKAX,MavenProBlack,MavenProBold,MavenProMedium,MavenProRegular;
    public DeviceResolution(Activity activity) {
        this.mActivity = activity;
        getDeviceResolution();
       // initializeTypeface(activity);
    }

    private void getDeviceResolution() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mHeight = displaymetrics.heightPixels;
        mWidth = displaymetrics.widthPixels;

        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(mWidth / dm.xdpi, 2);
        double y = Math.pow(mHeight / dm.ydpi, 2);

        mScreenInches = Math.sqrt(x + y);

        if (mWidth <= 480 && mScreenInches > 5.0) {
            mScreenInches = 4.5;
        }
    }

   /* public void initializeTypeface(Activity activity) {
        typefacemedium=Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Medium.ttf");
        typefaceRegular = Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Regular.ttf");
        typefaceSemiBold = Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Medium.ttf");
        typefaceBold = Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Bold.ttf");
        typeLight = Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Light.ttf");
        typeThin = Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Thin.ttf");
        frutigerltw75black=Typeface.createFromAsset(activity.getAssets(),"font/frutigerltw20-75black.ttf");
        frutigerltw55roman=Typeface.createFromAsset(activity.getAssets(),"font/frutigerltw20-55roman.ttf");
    }*/

   /* public Typeface getgothmbold(Activity activity){
        frutigerltw75black=Typeface.createFromAsset(activity.getAssets(),"font/Gotham-Bold.otf");
        return frutigerltw75black;

    }
    public Typeface getgothmlight(Activity activity){
        frutigerltw55roman=Typeface.createFromAsset(activity.getAssets(),"font/Gotham-Light.otf");
        return frutigerltw55roman;

    }

    public Typeface getgothamthin(Activity activity){
        frutigerltw55roman=Typeface.createFromAsset(activity.getAssets(),"font/Gotham-Thin.otf");
        return frutigerltw55roman;

    }
    public Typeface getgothmXlight(Activity activity){
        frutigerltw55roman=Typeface.createFromAsset(activity.getAssets(),"font/Gotham-XLight.otf");
        return typefacemedium;

    }
    public Typeface getbebas(Activity activity){
        frutigerltw55roman=Typeface.createFromAsset(activity.getAssets(),"font/BEBAS.ttf");
        return frutigerltw55roman;

    }*/

    public Typeface getKOMIKAX(Activity activity){
        KOMIKAX=Typeface.createFromAsset(activity.getAssets(),"font/KOMIKAX.ttf");
        return KOMIKAX;

    }
    public Typeface getmavenproBlack(Activity activity){
        MavenProBlack=Typeface.createFromAsset(activity.getAssets(),"font/MavenPro-Black.ttf");
        return MavenProBlack;

    }
    public Typeface getMavenProBold(Activity activity){
        MavenProBold=Typeface.createFromAsset(activity.getAssets(),"font/MavenPro-Bold.ttf");
        return MavenProBold;

    }
    public Typeface getMavenProMedium(Activity activity){
        MavenProMedium=Typeface.createFromAsset(activity.getAssets(),"font/MavenPro-Medium.ttf");
        return MavenProMedium;

    }
    public Typeface getMavenProRegular(Activity activity){
        MavenProRegular=Typeface.createFromAsset(activity.getAssets(),"font/MavenPro-Regular.ttf");
        return MavenProRegular;

    }

    public int getHeight(double heightVal) {
        return (int) (mHeight * heightVal);
    }

    public int getWidth(double widthVal) {
        return (int) (mWidth * widthVal);
    }

    public float getTextSize(double textSize) {
        return (float) (mScreenInches * textSize);
    }

}
