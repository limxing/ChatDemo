package com.limxing.chatdemo.application;

import android.app.Application;

import com.alibaba.mobileim.IYWPushListener;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.IYWConversationUnreadChangeListener;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.gingko.model.tribe.YWTribe;
import com.alibaba.mobileim.login.IYWConnectionListener;
import com.limxing.library.utils.LogUtils;
import com.umeng.openim.OpenIMAgent;

/**
 * Created by limxing on 16/2/11.
 */
public class ChatApplication extends Application {
    private static YWIMCore imCore =YWAPI.createIMCore();;

    @Override
    public void onCreate() {

        OpenIMAgent im = OpenIMAgent.getInstance(this);
        im.init();

        //添加连接状态监听，即登录状态监听
        imCore.addConnectionListener(new IYWConnectionListener() {
            @Override
            public void onDisconnect(int i, String s) {
                //掉线
                LogUtils.i("掉线:" + i+"=="+s);
            }

            @Override
            public void onReConnecting() {
                //正在重登
                LogUtils.i("正在重登");
            }

            @Override
            public void onReConnected() {
                //重登成功
                LogUtils.i("重登成功");
            }
        });

//在登录前注册消息push的监听通知，以便登录成功后即能收取到消息的通知
//请尽早添加该监听，从而保证应用不会因为在登录成功后再添加通知而错过一些消息通知
        imCore.getConversationService().addPushListener(new IYWPushListener() {
            @Override
            //收到单聊消息时会回调该方法，开发者可以在该方法内更新该会话的未读数
            public void onPushMessage(IYWContact arg0, YWMessage arg1) {
                //单聊消息
                YWConversation conversation = imCore.getConversationService().getConversationByUserId(arg0.getUserId());
                int unreadCount = conversation.getUnreadCount();
                //TODO 更新UI上该会话未读数
            }

            @Override
            //收到群聊消息时会回调该方法，开发者可以在该方法内更新该会话的未读数
            public void onPushMessage(YWTribe arg0, YWMessage arg1) {
                //群消息
                YWConversation conversation = imCore.getConversationService().getTribeConversation(arg0.getTribeId());
                int unreadCount = conversation.getUnreadCount();
                //TODO 更新UI上该会话未读数
            }
        });


        //注册消息未读总数监听
        imCore.getConversationService().addTotalUnreadChangeListener(new IYWConversationUnreadChangeListener() {
            @Override
            //当前登录账号的未读消息总数发送变化时会回调该方法，用户可以再该方法中更新UI的未读数
            public void onUnreadChange() {
                int totalUnreadCount = imCore.getConversationService().getAllUnreadCount();
                //TODO 更新UI的未读数
            }
        });
        super.onCreate();
    }

    public static YWIMCore getImCore() {
        return imCore;
    }
}
