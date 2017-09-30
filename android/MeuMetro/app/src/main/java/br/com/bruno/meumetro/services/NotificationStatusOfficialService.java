//package br.com.bruno.meumetro.services;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//
//import java.util.List;
//
//import br.com.bruno.meumetro.database.RealmDbHelper;
//import br.com.bruno.meumetro.enums.NotificationType;
//import br.com.bruno.meumetro.models.Line;
//import br.com.bruno.meumetro.models.settings.Setting;
//import br.com.bruno.meumetro.rest.StatusLineService;
//import br.com.bruno.meumetro.services.managers.NotificationStatusManager;
//import br.com.bruno.meumetro.utils.ConnectionUtils;
//
///**
// * Created by Bruno on 29/10/2016.
// */
//public class NotificationStatusOfficialService extends Service {
//
//    private NotificationStatusManager mManager;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mManager = new NotificationStatusManager(getApplicationContext());
//        mManager.setupTimeZone();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                while (mManager.mHasLoop) {
//                    Setting setting = RealmDbHelper.findFirst(Setting.class);
//                    if (setting != null && setting.getIsNotificationOfficial() != null && setting.getIsNotificationOfficial()) {
//                        if (mManager.isDayToNotification(setting)) {
//                            if (mManager.isHourToNotification(setting)) {
//                                if (ConnectionUtils.isConnected(getApplicationContext())) {
//                                    List<Line> lines = new StatusLineService().getLinesStatusLinesOfficialSync();
//                                    if (lines != null) {
//                                        lines = mManager.verifyLinesToNotify(lines, setting);
//                                        if (lines.size() > 0) {
//                                            mManager.setupNotification(lines, NotificationType.STATUS_OFFICIAL);
//                                        } else {
//                                            mManager.sleepTimeNextHour();
//                                        }
//                                    } else {
//                                        mManager.mSleepTime = NotificationStatusManager.A_MINUTE_SLEEP;
//                                    }
//                                } else {
//                                    mManager.mSleepTime = NotificationStatusManager.A_MINUTE_SLEEP;
//                                }
//                            } else {
//                                mManager.sleepTimeNextHour();
//                            }
//                        } else {
//                            mManager.sleepTimeNextDay();
//                        }
//                        try {
//                            Thread.sleep(mManager.mSleepTime);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            Thread.currentThread().interrupt();
//                        }
//                    } else {
//                        stopSelf();
//                    }
//                }
//            }
//        });
//        thread.setPriority(Thread.MAX_PRIORITY);
//        thread.start();
//
//        return START_STICKY;
//    }
//}
