package com.limxing.chatdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;


import com.limxing.chatdemo.application.ChatApplication;
import com.limxing.library.utils.LogUtils;
import com.limxing.library.utils.StringUtils;

import cn.finalteam.okhttpfinal.dm.DownloadInfo;
import cn.finalteam.okhttpfinal.dm.DownloadListener;
import cn.finalteam.okhttpfinal.dm.DownloadManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开始登录
        String userid = "limxing1";
        String password = "woaifeng521";
        IYWLoginService loginService = ChatApplication.getImCore().getLoginService();
        YWLoginParam Param = YWLoginParam.createLoginParam(userid, password);
        loginService.login(Param, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                //登录成功
                LogUtils.i("onSuccess"+arg0.toString());
            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
                LogUtils.i("onProgress"+arg0);
            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                LogUtils.i("onError=="+errCode+"==description:"+description);
            }
        });


    }
    public void down(String url){
        if (!DownloadManager.getInstance(this).hasTask(url)) {
            DownloadManager.getInstance(this).addTask(url, null);
        }
        DownloadManager.getInstance(this).addTaskListener(url, new DownloadListener() {
            @Override
            public void onProgress(DownloadInfo downloadInfo) {
                super.onProgress(downloadInfo);
                holder.mTvOperate.setText("暂停");
                holder.mTvDownloadState.setText("下载中");
                holder.mNumberProgressBar.setProgress(downloadInfo.getProgress());
                String downladScale = StringUtils.formatFileSize(downloadInfo.getDownloadLength()) + "/"
                        + StringUtils.formatFileSize(downloadInfo.getTotalLength());
                holder.mTvDownloadScale.setText(downladScale);
                holder.mTvDownloadSpeed.setText(StringUtils.formatFileSize(downloadInfo.getNetworkSpeed()));
            }

            @Override
            public void onError(DownloadInfo downloadInfo) {
                super.onError(downloadInfo);
                holder.mTvOperate.setText("继续");
                holder.mTvDownloadState.setText("已暂停");
            }

            @Override
            public void onFinish(DownloadInfo downloadInfo) {
                super.onFinish(downloadInfo);
                holder.mTvDownloadState.setText("下载完成");
                holder.mTvOperate.setText("安装");
            }
        });
//        DownloadManager.getInstance(this).setGlobalDownloadListener(new DownloadListener());
    }
}
