package com.ai.glide;

/**
 * @author -> miracle
 * @date   -> 2019/10/2
 * @email  -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone  -> 18983790146
 * @blog   -> https://ruanyandong.github.io
 */

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

/**
 * 图片请求对象封装类
 */
public class BitmapRequest {
    /**
     * 请求路径
     */
    private String url;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 需要加载图片的控件，内存不足时系统会回收软引用
     */
    private SoftReference<ImageView> imageView;
    /**
     * 占位图片
     */
    private int resId;
    /**
     * 回调对象
     */
    private RequestListener requestListener;

    /**
     * 图片的标识
     */
    private String urlMd5;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    /**
     * 链式调用
     *
     * @param url
     * @return
     */
    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMd5 = MD5Utils.toMD5(url);
        return this;
    }

    /**
     * 设置占位图片
     * @param resId
     * @return
     */
    public BitmapRequest loading(int resId){
        this.resId = resId;
        return this;
    }

    public BitmapRequest listener(RequestListener listener){
        this.requestListener = listener;
        return this;
    }
    public void into(ImageView imageView){
        imageView.setTag(this.urlMd5);
        this.imageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return context;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public int getResId() {
        return resId;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }
}
