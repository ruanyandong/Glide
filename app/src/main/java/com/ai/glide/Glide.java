package com.ai.glide;

import android.content.Context;

/**
 * @author -> miracle
 * @date -> 2019/10/6
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class Glide {
    public static BitmapRequest with(Context context){
        return new BitmapRequest(context);
    }
}
