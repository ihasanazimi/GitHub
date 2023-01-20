/*
Copyright 2015 Alex Florescu
Copyright 2014 Yahoo Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.masir.utility.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.example.masir.ApplicationLoader;

/**
 * Util class for converting between dp, px and other magical pixel units
 */
public class PixelUtil {

    private PixelUtil() {
    }

    public static int dpToPx(float dp) {
        return dpToPx(ApplicationLoader.Companion.getContext(), dp);
    }
    public static int dpToPx(Context context, int dp) {
        return dpToPx(context, (float)dp);
    }
    public static int dpToPx(Context context, float dp) {
        int px = Math.round(dp * getPixelScaleFactor(context));
        return px;
    }
    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, ApplicationLoader.Companion.getContext().getResources().getDisplayMetrics());
    }

    public static float pxToDp(float px) {
        return px / getPixelScaleFactor(ApplicationLoader.Companion.getContext());
    }

    private static float getPixelScaleFactor(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int getWidth(){

        return getWidth(ApplicationLoader.Companion.getContext());
    }
    public static int getWidth(Context context){

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        return (int)dpWidth ;
    }
    public static int getWidthPx(){
        return getWidthPx(ApplicationLoader.Companion.getContext());
    }
    public static int getWidthPx(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getHeight(){
        return getHeight(ApplicationLoader.Companion.getContext());
    }
    public static int getHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        return (int)dpHeight ;
    }
    public static int getHeightPx(){
        return getHeightPx(ApplicationLoader.Companion.getContext());
    }
    public static int getHeightPx(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
