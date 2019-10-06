package com.ai.glide;

import android.graphics.Bitmap;

/**
 * @author -> miracle
 * @date -> 2019/10/2
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public interface RequestListener {
    boolean onSuccess(Bitmap bitmap);
    boolean onFailure();
}
