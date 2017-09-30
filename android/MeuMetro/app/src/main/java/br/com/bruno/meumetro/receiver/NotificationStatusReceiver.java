//package br.com.bruno.meumetro.receiver;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import br.com.bruno.meumetro.services.NotificationStatusOfficialService;
//import br.com.bruno.meumetro.services.managers.NotificationStatusManager;
//
///**
// * Created by Bruno on 29/10/2016.
// */
//
//public class NotificationStatusReceiver extends BroadcastReceiver {
//
//    public NotificationStatusReceiver() {
//        super();
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        NotificationStatusManager.startNotificationStatusOfficialService(context);
//        NotificationStatusManager.startNotificationStatusByUserService(context);
//    }
//}
