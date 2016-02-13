package com.limxing.chatdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;


import com.limxing.chatdemo.application.ChatApplication;
import com.limxing.library.utils.LogUtils;

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
}
