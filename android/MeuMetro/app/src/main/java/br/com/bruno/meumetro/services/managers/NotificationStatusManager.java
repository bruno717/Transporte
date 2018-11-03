package br.com.bruno.meumetro.services.managers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.crashlytics.android.Crashlytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import br.com.bruno.meumetro.MainActivity;
import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.enums.DayType;
import br.com.bruno.meumetro.enums.NotificationType;
import br.com.bruno.meumetro.enums.lines.LineType;
import br.com.bruno.meumetro.models.Line;
import br.com.bruno.meumetro.models.Message;
import br.com.bruno.meumetro.models.settings.DaySetting;
import br.com.bruno.meumetro.models.settings.HourSetting;
import br.com.bruno.meumetro.models.settings.Setting;

/**
 * Created by Bruno on 06/05/2017.
 */
public class NotificationStatusManager {

    public static final long A_MINUTE_SLEEP = 60000;
    public Boolean mHasLoop = true;
    public long mSleepTime = 0;

    private Context mContext;

    public NotificationStatusManager(Context context) {
        mContext = context;
    }

    public void setupTimeZone() {
        TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
        TimeZone.setDefault(tz);
        GregorianCalendar.getInstance(tz);
    }

    public void sleepTimeNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        mSleepTime = hourOfDay - 24;
        if (mSleepTime < 0)
            mSleepTime = -mSleepTime;

        mSleepTime = TimeUnit.HOURS.toMillis(mSleepTime);
    }

    public void sleepTimeNextHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        mSleepTime = calendar.get(Calendar.MINUTE) - 60;
        if (mSleepTime < 0)
            mSleepTime = -mSleepTime;
        mSleepTime = TimeUnit.MINUTES.toMillis(mSleepTime);
    }

    public Boolean isDayToNotification(Setting setting, String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        DayType dayType = DayType.getDayTypeById(calendar.get(Calendar.DAY_OF_WEEK));
        for (DaySetting daySetting : setting.getDays()) {
            if (dayType == DayType.getDayTypeByName(daySetting.getDay())) {
                SimpleDateFormat format = new SimpleDateFormat(Line.PATTERN_DATE, Locale.getDefault());
                calendar.setTime(format.parse(date));
                DayType dayNotification = DayType.getDayTypeById(calendar.get(Calendar.DAY_OF_WEEK));
                return dayType == dayNotification;
            }
        }
        return false;
    }

    public Boolean isHourToNotification(Setting setting, String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        for (HourSetting hourSetting : setting.getHours()) {
            if (Integer.parseInt(hourSetting.getHour().replace("h", "")) == hourOfDay) {
                SimpleDateFormat format = new SimpleDateFormat(Line.PATTERN_DATE, Locale.getDefault());
                calendar.setTime(format.parse(date));
                return hourOfDay == calendar.get(Calendar.HOUR_OF_DAY);
            }
        }
        return false;
    }

    public List<Line> verifyLinesToNotify(List<Line> lines, Setting setting) {
        List<Line> linesToNotify = new ArrayList<>();
        for (Line line : lines) {
            for (int i = 0; i < setting.getLines().size(); i++) {
                if (line.getLineType() == LineType.getLineTypeByName(setting.getLines().get(i).getLine()) && !line.getSituation().equalsIgnoreCase(mContext.getString(R.string.service_notification_status_normal_operation))) {
                    linesToNotify.add(line);
                    break;
                }
            }
        }
        return linesToNotify;
    }

    public void setupNotification(Message message, NotificationType notificationType) {

        try {
            NotificationCompat.InboxStyle inboxStyle = null;

            Intent notificationIntent = new Intent(mContext, MainActivity.class);
            notificationIntent.putExtra(MainActivity.MAIN_ACTIVITY_INTENT_KEY_IS_TAB_USER, notificationType == NotificationType.STATUS_BY_USER);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(mContext, notificationType.getValue(), notificationIntent, 0);

            if (message.getLines().size() > 1) {
                inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.setBigContentTitle(message.getTitle());
                for (Line line : message.getLines()) {
                    inboxStyle.addLine(String.format(Locale.getDefault(), "%s - %s", line.getName(), line.getSituation()));
                }
            }

            NotificationManager notifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
            notificationBuilder
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(new long[]{100, 300, 100, 300})
                    .setContentTitle(message.getTitle())
                    .setContentIntent(intent)
                    .setColor(ContextCompat.getColor(mContext, R.color.background_notification_blue))
                    .setAutoCancel(true)
                    .setWhen(new SimpleDateFormat(Line.PATTERN_DATE, Locale.getDefault()).parse(message.getDate()).getTime())
                    .setShowWhen(true)
                    .setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.mipmap.ic_logo_notification : R.mipmap.ic_launcher);

            if (inboxStyle != null) {
                notificationBuilder.setStyle(inboxStyle)
                        .setContentText(mContext.getResources().getString(R.string.service_notification_status_change_status));
            } else {
                notificationBuilder.setContentText(message.getSimpleDescription());
            }

            notifyManager.notify(notificationType.getValue(), notificationBuilder.build());
        } catch (ParseException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }
}
