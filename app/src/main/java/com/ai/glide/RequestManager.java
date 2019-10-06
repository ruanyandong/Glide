package com.ai.glide;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author -> miracle
 * @date -> 2019/10/4
 * @email -> ruanyandongai@gmail.com 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 */
public class RequestManager {
    /**
     * 单例
     */
    private static final RequestManager instance = new RequestManager();

    /**
     * 创建队列
     */
    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();

    /**
     * 创建一个线程数组
     */
    private BitmapDispatcher[] bitmapDispatchers;


    private RequestManager(){
        start();
    }

    private void startAllDispatcher() {
        // 创建并开始所有线程
        // 获取手机支持的单个应用最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher dispatcher = new BitmapDispatcher(requestQueue);
            dispatcher.start();
            //  将每个dispatcher放到数组中统一管理
            bitmapDispatchers[i] = dispatcher;
        }
    }

    private void start() {
        stop();
        startAllDispatcher();
    }

    private void stop() {
        if (bitmapDispatchers != null && bitmapDispatchers.length > 0){
            for (BitmapDispatcher dispatcher:bitmapDispatchers) {
                if (!dispatcher.isInterrupted()){
                    dispatcher.interrupt();
                }
            }
        }
    }

    public static RequestManager getInstance() {
        return instance;
    }

    /**
     * 将图片请求加入到队列中
     */
    public void addBitmapRequest(BitmapRequest bitmapRequest){
        if (bitmapRequest == null){
            return;
        }
        if (!requestQueue.contains(bitmapRequest)){
            requestQueue.add(bitmapRequest);
        }
    }




}
