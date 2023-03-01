package edu.aku.dmu.hf_vaccination.models;

import static edu.aku.dmu.hf_vaccination.core.MainApp.PROJECT_NAME;
import static edu.aku.dmu.hf_vaccination.core.MainApp._EMPTY_;

import android.database.Cursor;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.dmu.hf_vaccination.BR;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.AttendanceTable;
import edu.aku.dmu.hf_vaccination.core.MainApp;


public class Attendance extends BaseObservable implements Observable {

    private final String TAG = "FormVA";
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    // APP VARIABLES
    private String projectName = PROJECT_NAME;
    // APP VARIABLES
    private String id = _EMPTY_;
    private String uid = _EMPTY_;
    private String userName = _EMPTY_;
    private String sysDate = _EMPTY_;
    private String deviceId = _EMPTY_;
    private String appver = _EMPTY_;
    private String synced = _EMPTY_;
    private String syncDate = _EMPTY_;
    private String gpsLat = _EMPTY_;
    private String gpsLng = _EMPTY_;
    private String gpsDT = _EMPTY_;
    private String gpsAcc = _EMPTY_;
    private String ucCode = _EMPTY_;


    public Attendance() {

/*        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        setAppver(MainApp.appInfo.getAppVersion());*/

    }


    public void populateMeta() {

        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setUcCode(MainApp.user.getUccode());
        setDeviceId(MainApp.deviceid);
        //   setUuid(MainApp.form.getUid());  // not applicable in Form table
        setAppver(MainApp.appInfo.getAppVersion());
        setProjectName(PROJECT_NAME);
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver;
    }


    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }


    @Bindable
    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
        notifyPropertyChanged(BR.gpsLat);
    }

    @Bindable
    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
        notifyPropertyChanged(BR.gpsLng);
    }

    @Bindable
    public String getGpsDT() {
        return gpsDT;
    }

    public void setGpsDT(String gpsDT) {
        this.gpsDT = gpsDT;
        notifyPropertyChanged(BR.gpsDT);
    }

    @Bindable
    public String getGpsAcc() {
        return gpsAcc;
    }

    public void setGpsAcc(String gpsAcc) {
        this.gpsAcc = gpsAcc;
        notifyPropertyChanged(BR.gpsAcc);
    }


    @Bindable
    public String getUcCode() {
        return ucCode;
    }

    public void setUcCode(String ucCode) {
        this.ucCode = ucCode;
    }


    public Attendance Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_UID));
        this.projectName = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_PROJECT_NAME));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_USERNAME));
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_UC_CODE));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_SYSDATE));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_DEVICEID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_APPVERSION));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_SYNC_DATE));
        this.gpsLat = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_GPSLNG));
        this.gpsDT = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndexOrThrow(AttendanceTable.COLUMN_GPSACC));

        return this;
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(AttendanceTable.COLUMN_ID, this.id);
        json.put(AttendanceTable.COLUMN_UID, this.uid);
        json.put(AttendanceTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(AttendanceTable.COLUMN_USERNAME, this.userName);
        json.put(AttendanceTable.COLUMN_UC_CODE, this.ucCode);
        json.put(AttendanceTable.COLUMN_SYSDATE, this.sysDate);
        json.put(AttendanceTable.COLUMN_DEVICEID, this.deviceId);

        json.put(AttendanceTable.COLUMN_SYNCED, this.synced);
        json.put(AttendanceTable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(AttendanceTable.COLUMN_APPVERSION, this.appver);
        json.put(AttendanceTable.COLUMN_GPSLAT, this.gpsLat);
        json.put(AttendanceTable.COLUMN_GPSLNG, this.gpsLng);
        json.put(AttendanceTable.COLUMN_GPSDATE, this.gpsDT);
        json.put(AttendanceTable.COLUMN_GPSACC, this.gpsAcc);

        return json;
    }
}