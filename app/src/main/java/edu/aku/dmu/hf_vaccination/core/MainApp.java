package edu.aku.dmu.hf_vaccination.core;

import static edu.aku.dmu.hf_vaccination.database.DatabaseHelper.DATABASE_NAME;
import static edu.aku.dmu.hf_vaccination.database.DatabaseHelper.DATABASE_PASSWORD;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.core.app.ActivityCompat;

import com.edittextpicker.aliazaz.EditTextPicker;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.soloader.SoLoader;
import com.validatorcrawler.aliazaz.Clear;

import net.sqlcipher.database.SQLiteDatabase;

import org.json.JSONArray;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import edu.aku.dmu.hf_vaccination.BuildConfig;
import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.models.Attendance;
import edu.aku.dmu.hf_vaccination.models.FormCR;
import edu.aku.dmu.hf_vaccination.models.FormVA;
import edu.aku.dmu.hf_vaccination.models.FormVB;
import edu.aku.dmu.hf_vaccination.models.FormWR;
import edu.aku.dmu.hf_vaccination.models.Users;
import edu.aku.dmu.hf_vaccination.models.VaccDueDates;
import edu.aku.dmu.hf_vaccination.models.Vaccines;
import edu.aku.dmu.hf_vaccination.models.VaccinesData;
import edu.aku.dmu.hf_vaccination.models.VaccinesSchedule;
import edu.aku.dmu.hf_vaccination.models.WomenFollowUP;
import edu.aku.dmu.hf_vaccination.models.WorkLocation;
import edu.aku.dmu.hf_vaccination.ui.LockActivity;


public class MainApp extends Application {

    public static final String PROJECT_NAME = "HF Vaccination";
    public static final String DIST_ID = null;
    public static final String SYNC_LOGIN = "sync_login";
    public static final String _IP = "https://vcoe1.aku.edu";// .LIVE server
    public static final String _HOST_URL = MainApp._IP + "/hfp/api/";// .TEST server;
    public static final String _PHOTO_UPLOAD_URL = _HOST_URL + "uploads.php";
    public static final String _SERVER_URL = "syncgcm.php";
    public static final String _SERVER_GET_URL = "getDatagcm.php";
    public static final String _UPDATE_URL = MainApp._IP + "/hfp/app/";
    public static final String _USER_URL = "resetpasswordgcm.php";

    public static final String _EMPTY_ = "";
    public static final String _APP_FOLDER = "../app/hfp_vaccination";
    private static final String TAG = "MainApp";
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    public static int TRATS = 8;
    public static String IBAHC = "";
    //COUNTRIES
    public static int PAKISTAN = 1;
    public static int TAJIKISTAN = 3;
    public static int memberCount = 0;
    public static File sdDir;
    public static String[] downloadData;
    public static FormCR cr;
    public static FormWR wr;
    public static FormVA formVA;
    public static FormVB formVB;
    public static WorkLocation workLocation;
    public static Attendance attendance;
    public static Vaccines vaccines;
    public static VaccDueDates vaccDueDates;
    public static List<Vaccines> vaccinesList;
    public static List<VaccinesData> vaccinesDataList;
    public static List<WomenFollowUP> womenFollowUPList;
    public static WomenFollowUP womenFollowUP;
    public static VaccinesData vaccinesData;
    public static VaccinesSchedule vaccinesSchedule;
    public static VaccDueDates dueDates;
    public static boolean superuser;
    public static String selectedVillageCode;
    public static String selectedUCCode;
    public static int vaccineCount = 0;
    public static boolean flag = false;

    //public static List<VaccinesData> vaccinesDataList;
    public static boolean flagVA = false;
    public static List<FormVB> formVBList;
    public static String DeviceURL = "devices.php";
    public static AppInfo appInfo;
    public static Users user;
    public static Boolean admin = false;
    public static List<JSONArray> uploadData;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences sharedPref;
    public static String deviceid;
    public static int versionCode = BuildConfig.VERSION_CODE;
    public static String versionName = BuildConfig.VERSION_NAME;
    public static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 2;
    public static boolean permissionCheck = false;
    public static CountDownTimer timer;
    protected static LocationManager locationManager;
    static ToneGenerator toneGen1;
    public static String SITE_ID = "";


    public static void hideSystemUI(View decorView) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public static String getDeviceId(Context context) {
        String deviceId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
           /* final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }*/
        }
        return "deviceId";
    }

    public static void cbCheck(CheckBox cb1, CheckBox cb2, CheckBox cb3, EditTextPicker edt) {
        cb1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Clear.clearAllFields(edt, false);
                cb2.setChecked(false);
                cb2.setEnabled(false);
                cb3.setChecked(false);
                cb3.setEnabled(false);
            } else {
                Clear.clearAllFields(edt, true);
                cb2.setEnabled(true);
                cb3.setEnabled(true);
            }
        });
    }


    public static Boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    public static void lockScreen(Context c) {

        if (timer != null) {
            timer.cancel();
        }

        //   Context mContext = c;
        Activity activity = (Activity) c;


        timer = new CountDownTimer(15 * 60 * 1000, 1000) {
            //timer = new CountDownTimer(30 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                //Some code
                //bi.timeLeft.setText((millisUntilFinished / 1000) + " secs left");
                if ((millisUntilFinished / 1000) < 14) {
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                }

            }

            public void onFinish() {
                //Logout
                //
                //   finish();
                // lockScreen();
                Intent intent = new Intent();
                intent.setClass(c, LockActivity.class);
                c.startActivity(intent);
                timer.cancel();
                //  startActivity(new Intent(((Activity) c).getLocalClassName(), LockActivity.class));
            }
        };
        timer.start();

    }

    // Set max value by current month
    public static void setMaxDayByCurrent(EditTextPicker et) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        et.setMaxvalue(cal.get(Calendar.DAY_OF_MONTH));
    }

    // Set max value by current month
    public static void setMaxMonthByCurrent(EditTextPicker et) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        et.setMaxvalue(cal.get(Calendar.MONTH) + 1);
    }

    // Set max value by current year
    public static void setMaxYearByCurrent(EditTextPicker et) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        et.setMaxvalue(cal.get(Calendar.YEAR));
    }

    @Override
    public void onCreate() {
        super.onCreate();

         /*
        RootBeer rootBeer = new RootBeer(this);
        if (rootBeer.isRooted()) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }*/

        //Initiate DateTime
        //Initializ App info
        appInfo = new AppInfo(this);
        sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        editor = sharedPref.edit();
        MainApp.editor
                .putString("mh01", "")
                .apply();
        deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new GPSLocationListener(this) // Implement this class from code

        );


        initSecure();

        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        SoLoader.init(this, false);

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            final FlipperClient client = AndroidFlipperClient.getInstance(this);
            client.addPlugin(new InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()));
            client.addPlugin(new DatabasesFlipperPlugin(this));

            client.start();
        }
    }

    private void initSecure() {
        // Initialize SQLCipher library
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath(DATABASE_NAME);
       /* databaseFile.mkdirs();
        databaseFile.delete();*/
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, DATABASE_PASSWORD, null);
        // Prepare encryption KEY
        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            TRATS = bundle.getInt("YEK_TRATS");
            //IBAHC = bundle.getString("YEK_REVRES").substring(TRATS, TRATS + 16);
            IBAHC = bundle.getString("YEK_REVRES");
            Log.d(TAG, "onCreate: YEK_REVRES = " + IBAHC);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
