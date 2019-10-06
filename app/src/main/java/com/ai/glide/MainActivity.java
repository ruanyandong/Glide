package com.ai.glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * ImageLoader的优点：
 *            1、支持本地缓存文件名规则定义
 *            2、默认实现多种内存缓存算法
 *            3、避免内存泄漏，在可以View滚动的时候暂停
 * ImageLoader的缺点：
 *            1、ImageLoader配置起来相当麻烦
 *            2、作者已经停止更新
 * =================================================
 * Glide的优点：
 *      1、支持优先级处理
 *      2、不但支持静态图片
 *      3、不但可以缓存图片，还可以缓存媒体
 *      4、复用bitmap，有Bitmap复用池，如果大小超出了缓存的bitmap，如果不能复用才创建新的Bitmap
 *      5、自动管理生命周期,with(this)，内部创建了Fragment来监听Activity的生命周期
 *      6、同一张图片会缓存多个不同尺寸，速度快
 *      7、可配置度高、自适应度高；
 *      8、支持多种数据源，本地、网络，assets gif在glide是支持
 *      9、高效缓存，支持memory和disk图片缓存，默认使用二级缓存
 *      10、高效处理Bitmap：使用Bitmap pool复用Bitmap
 *      11、图片加载过程可以监听
 *      12、生命周期集成到Glide
 * Glide的缺点：
 *      1、加载Gif图片时，如果最后一帧为透明，会出现闪烁的情况
 *      2、Glide使用的时候消耗内存比较严重
 * Glide四级缓存：
 *        1、变量缓存
 *        2、内存缓存
 *        3、本地缓存
 *        4、网络缓存
 * ====================================================
 * Fresco的优点：
 *       1、内存缓存
 *       2、Fresco与Glide同样支持优先级处理、GIF和WebP图片
 *       3、图片预览，渐进式显示效果和多图请求
 * Fresco的缺点：
 *       1、Fresco比较重量级，如果引入会明显增大项目
 *       2、Fresco的注入性比较强
 *       3、内部集成了很多第三方库，容易发生jar包冲突
 *=======================================================
 * Picasso的优点：
 *        1、Picasso采用的ARGB-8888，加载的是全图，图片质量和清晰度要比Glide高
 *        2、Glide是在Picasso的基础上进行了二次封装
 * Picasso的缺点：
 *        1、采样率过高，出现OOM异常的概率要比Glide高很多，缓存的是全尺寸图片
 *        2、不能加载GIF图片
 *        3、缓存策略和加载速度明显不足
 *==========================================================
 * 三级缓存：
 *        内存缓存：优先加载，速度最快
 *        本地缓存：次优先加载，速度快
 *        网络缓存：最后加载，速度慢
 *==========================================================
 * 为什么要进行三级缓存
 *      1、减少不必要的流量消耗
 *      2、加载很慢，影响用户体验
 *      3、OOM导致的频繁GC
 *============================================================
 * 三级缓存的原理
 *       1、首次加载的时候通过网络加载，获取图片，然后保存到内存和SD卡中。
 *       2、之后运行APP时，优先访问内存中的图片缓存。
 *       3、如果内存没有，则加载本地SD卡中的图片。(解码，把file流解码为bitmap)
 * 具体的缓存策略可以是这样的：
 * 内存作为一级缓存，本地作为二级缓存，网络加载为最后。
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private LinearLayout mScroll_line;
    String url = "http://g.hiphotos.baidu.com/image/pic/item/0823dd54564e92589f2fe1019882d158cdbf4ec1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScroll_line = findViewById(R.id.scrooll_line);
        verifyStoragePermissions(this);

        findViewById(R.id.single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mScroll_line.addView(imageView);

                Glide.with(MainActivity.this).load(url).loading(R.mipmap.ic_launcher).listener(new RequestListener(){
                    @Override
                    public boolean onSuccess(Bitmap bitmap) {
                        return false;
                    }

                    @Override
                    public boolean onFailure() {
                        return false;
                    }
                }).into(imageView);
            }
        });
    }

    private void verifyStoragePermissions(Activity activity) {
        // 检查是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }
}
