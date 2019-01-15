package br.com.bruno.meumetro.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import br.com.bruno.meumetro.enums.NotificationType;
import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.Line;
import br.com.bruno.meumetro.models.Message;
import br.com.bruno.meumetro.models.settings.Setting;
import br.com.bruno.meumetro.services.managers.NotificationStatusManager;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Bruno on 19/05/2017.
 */

public class MeuMetroMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        try {

            final Message message = new Message();
            message.setTitle(remoteMessage.getData().get("title"));
            message.setType(Integer.parseInt(remoteMessage.getData().get("type")));
            message.setDate(remoteMessage.getData().get("date"));
            if (remoteMessage.getData().get("simpleDescription") != null)
                message.setSimpleDescription(remoteMessage.getData().get("simpleDescription"));

            if (remoteMessage.getData().get("bigDescription") != null) {
                message.setBigDescription(Arrays.asList(remoteMessage.getData().get("bigDescription").split(",")));
            }
            try {
                TypeReference<List<Line>> reference = new TypeReference<List<Line>>() {
                };
                message.setLines((List<Line>) new ObjectMapper().readValue(remoteMessage.getData().get("lines"), reference));
            } catch (IOException e) {
                e.printStackTrace();
            }

//            final Setting setting = RealmDbHelper.findFirst(Setting.class);
            Setting setting = SharedPreferenceManager.getSetting(getApplicationContext());
            if (setting != null) {
                try {
                    verifySettingsToMountNotification(setting, message);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Setting> settings = realm.where(Setting.class).findAll();
                        try {
                            verifySettingsToMountNotification(settings.first(), message);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        realm.close();
                    }
                });
            }

//            NotificationStatusManager manager = new NotificationStatusManager(getApplicationContext());
//            NotificationType type = NotificationType.getNotification(message.getType());
//            if ((setting != null && setting.getIsNotificationOfficial() != null && setting.getIsNotificationOfficial() && type == NotificationType.STATUS_OFFICIAL)
//                    || setting != null && setting.getIsNotificationByUser() != null && setting.getIsNotificationByUser() && type == NotificationType.STATUS_BY_USER) {
//                if (manager.isDayToNotification(setting, message.getDate()) && manager.isHourToNotification(setting, message.getDate())) {
//                    message.setLines(manager.verifyLinesToNotify(message.getLines(), setting));
//                    if (message.getLines().size() > 0) {
//                        manager.setupNotification(message, type);
//                    }
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }

    private void verifySettingsToMountNotification(Setting setting, Message message) throws ParseException {
        NotificationStatusManager manager = new NotificationStatusManager(getApplicationContext());
        NotificationType type = NotificationType.getNotification(message.getType());
        if ((setting != null && setting.getIsNotificationOfficial() != null && setting.getIsNotificationOfficial() && type == NotificationType.STATUS_OFFICIAL)
                || setting != null && setting.getIsNotificationByUser() != null && setting.getIsNotificationByUser() && type == NotificationType.STATUS_BY_USER) {
            if (manager.isDayToNotification(setting, message.getDate()) && manager.isHourToNotification(setting, message.getDate())) {
                message.setLines(manager.verifyLinesToNotify(message.getLines(), setting));
                if (message.getLines().size() > 0) {
                    manager.setupNotification(message, type);
                }
            }
        }
    }

}
