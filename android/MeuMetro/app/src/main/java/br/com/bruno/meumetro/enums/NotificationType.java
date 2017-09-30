package br.com.bruno.meumetro.enums;

import lombok.Getter;

/**
 * Created by Bruno on 11/05/2017.
 */
@Getter
public enum NotificationType {

    STATUS_OFFICIAL(3210),
    STATUS_BY_USER(3211);

    private int value;

    NotificationType(int value) {
        this.value = value;
    }

    public static NotificationType getNotification(int type) {
        switch (type) {
            case 1:
                return STATUS_OFFICIAL;

            case 2:
                return STATUS_BY_USER;
        }

        return STATUS_OFFICIAL;
    }
}
