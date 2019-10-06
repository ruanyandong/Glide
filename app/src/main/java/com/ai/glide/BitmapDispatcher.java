package com.ai.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author -> miracle
 * @date -> 2019/10/2
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class BitmapDispatcher extends Thread {
    /**
     * 单链表实现，单向队列，头部取数据，尾部添加数据，并发情况下的线程安全问题
     */
    private LinkedBlockingQueue<BitmapRequest> requestQueue;

    private Handler handler = new Handler(Looper.getMainLooper());

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestQueue){
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        super.run();
        while(!isInterrupted()){
            try {
                BitmapRequest br = requestQueue.take();
                // 设置占位图片
                showLoadingImage(br);
                // 加载图片
                Bitmap bitmap = findBitmap(br);
                // 把图片显示到imageView上面
                showImageView(br,bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageView(final BitmapRequest br, final Bitmap bitmap) {
        if (bitmap != null && br.getImageView() != null &&
                br.getUrlMd5().equals(br.getImageView().getTag())){
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    if (br.getRequestListener() != null){
                        RequestListener listener = br.getRequestListener();
                        listener.onSuccess(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest br) {
        Bitmap bitmap = null;
        bitmap = downloadImage(br.getUrl());
        return bitmap;
    }

    private Bitmap downloadImage(String uri) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void showLoadingImage(BitmapRequest br) {
        if (br.getResId() > 0 && br.getImageView() != null){
            final int resId = br.getResId();
            final ImageView iv = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iv.setImageResource(resId);
                }
            });
        }
    }
}
