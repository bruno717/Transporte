package br.com.bruno.meumetro.managers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

//import com.crashlytics.android.Crashlytics;

import java.io.IOException;
import java.util.List;

/**
 * Created by Bruno on 14/01/2018.
 */

public class GeoLocationManager {

    public static final float APPROXIMATE_ZOOM = 16f;

    public static Address getInfoAddress(Context context, String locationName) {
        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> address = geocoder.getFromLocationName(locationName, 1);
            if (!address.isEmpty())
                return address.get(0);
        } catch (IOException e) {
//            Crashlytics.logException(e);
            e.printStackTrace();
        }
        return null;
    }

}
