package com.example.awesoman.bigproject3;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import static com.hyphenate.easeui.EaseConstant.CHATTYPE_GROUP;


/**
 * Created by Awesome on 2016/12/2.
 */

public class EMUserManager {
    private static EMUserManager emUserManager ;
    private EMUserManager() {
    }
    public static EMUserManager getInstance(){
        if(emUserManager==null)
             emUserManager = new EMUserManager();
        return emUserManager;
    }

    public void asyncLogin(String EMId,String passWord){
        EMClient.getInstance().login(EMId, passWord, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("成功","yeah");
            }

            @Override
            public void onError(int i, String s) {
                Log.i("失败",i+"fuck you"+s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.i("进行中",i+"fuck you"+s);
            }
        });
    }

    public void asyncSendMessage(String content, String toChatUsername){
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
////如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(EMMessage.ChatType.GroupChat);
//发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public void asyncSendImage(String imagePath,boolean isUnCompress,String toChatUsername){
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
//如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public void asyncReceiveMessage(){

        EMMessageListener msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }
}
