package edu.aku.dmu.hf_vaccination.models;

import static edu.aku.dmu.hf_vaccination.core.MainApp.PROJECT_NAME;
import static edu.aku.dmu.hf_vaccination.core.MainApp._EMPTY_;

import android.database.Cursor;
import android.util.Log;

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
import edu.aku.dmu.hf_vaccination.contracts.TableContracts;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.WorkLocationTable;
import edu.aku.dmu.hf_vaccination.core.MainApp;


public class
WorkLocation extends BaseObservable implements Observable {

    private final String TAG = "WorkLocation";
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    // APP VARIABLES
    private String projectName = PROJECT_NAME;
    // APP VARIABLES
    private String id = _EMPTY_;
    private String uid = _EMPTY_;
    private String aid = _EMPTY_;
    private String userName = _EMPTY_;
    private String ucCode = _EMPTY_;
    private String sysDate = _EMPTY_;
    private String sno = _EMPTY_;
    private String deviceId = _EMPTY_;
    private String deviceTag = _EMPTY_;
    private String appver = _EMPTY_;
    private String endTime = _EMPTY_;
    private String iStatus = _EMPTY_;
    private String iStatus96x = _EMPTY_;
    private String synced = _EMPTY_;
    private String syncDate = _EMPTY_;
    private String entryType = _EMPTY_;
    private String gpsLat = _EMPTY_;
    private String gpsLng = _EMPTY_;
    private String gpsDT = _EMPTY_;
    private String gpsAcc = _EMPTY_;


    private String wlDate = _EMPTY_;
    private String wlLocationType = _EMPTY_;
    private String wlFacilityName = _EMPTY_;
    private String wlFacilityCode = _EMPTY_;
    private String wlVillageName = _EMPTY_;
    private String wlVillageCode = _EMPTY_;
    private String wlArea = _EMPTY_;


    public WorkLocation() {

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
        setAid(MainApp.attendance.getUid());
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

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    @Bindable
    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
        notifyPropertyChanged(BR.sno);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUcCode() {
        return ucCode;
    }

    public void setUcCode(String ucCode) {
        this.ucCode = ucCode;
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

    public String getDeviceTag() {
        return deviceTag;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getiStatus() {
        return iStatus;
    }

    public void setiStatus(String iStatus) {
        this.iStatus = iStatus;
    }

    public String getiStatus96x() {
        return iStatus96x;
    }

    public void setiStatus96x(String iStatus96x) {
        this.iStatus96x = iStatus96x;
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
    public String getWlDate() {
        return wlDate;
    }

    public void setWlDate(String wlDate) {
        this.wlDate = wlDate;
        notifyPropertyChanged(BR.wlDate);
    }

    @Bindable
    public String getWlArea() {
        return wlArea;
    }

    public void setWlArea(String wlArea) {
        this.wlArea = wlArea;
        notifyPropertyChanged(BR.wlArea);
    }

    @Bindable
    public String getWlLocationType() {
        return wlLocationType;
    }

    public void setWlLocationType(String wlLocationType) {
        this.wlLocationType = wlLocationType;
        notifyPropertyChanged(BR.wlLocationType);
    }

    @Bindable
    public String getWlFacilityName() {
        return wlFacilityName;
    }

    public void setWlFacilityName(String wlFacilityName) {
        this.wlFacilityName = wlFacilityName;
        notifyPropertyChanged(BR.wlFacilityName);
    }

    @Bindable
    public String getWlFacilityCode() {
        return wlFacilityCode;
    }

    public void setWlFacilityCode(String wlFacilityCode) {
        this.wlFacilityCode = wlFacilityCode;
        notifyPropertyChanged(BR.wlFacilityCode);
    }

    @Bindable
    public String getWlVillageName() {
        return wlVillageName;
    }

    public void setWlVillageName(String wlVillageName) {
        this.wlVillageName = wlVillageName;
        notifyPropertyChanged(BR.wlVillageName);
    }

    @Bindable
    public String getWlVillageCode() {
        return wlVillageCode;
    }

    public void setWlVillageCode(String wlVillageCode) {
        this.wlVillageCode = wlVillageCode;
        notifyPropertyChanged(BR.wlVillageCode);
    }


    public WorkLocation Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_UID));
        this.aid = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_AID));
        this.projectName = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_PROJECT_NAME));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_USERNAME));
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_UC_CODE));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_SYSDATE));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_DEVICETAGID));
        //   this.entryType = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_ENTRY_TYPE));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_SYNC_DATE));
        this.gpsLat = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_GPSLNG));
        this.gpsDT = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_GPSACC));

        sWlHydrate(cursor.getString(cursor.getColumnIndexOrThrow(WorkLocationTable.COLUMN_SWL)));
        return this;
    }

    public void sWlHydrate(String string) throws JSONException {
        Log.d(TAG, "sWlHydrate: " + string);
        if (string != null) {
            JSONObject json = null;
            json = new JSONObject(string);
            this.wlDate = json.getString("wlDate");
            this.wlLocationType = json.getString("wlLocationType");
            this.wlFacilityName = json.getString("wlFacilityName");
            this.wlFacilityCode = json.getString("wlFacilityCode");
            this.wlVillageName = json.getString("wlVillageName");
            this.wlVillageCode = json.getString("wlVillageCode");
            this.wlArea = json.getString("wlArea");


        }
    }

    public String sWltoString() throws JSONException {
        Log.d(TAG, "sWltoString: ");
        JSONObject json = new JSONObject();
        json
                .put("wlDate", wlDate)
                .put("wlLocationType", wlLocationType)
                .put("wlFacilityName", wlFacilityName)
                .put("wlFacilityCode", wlFacilityCode)
                .put("wlVillageName", wlVillageName)
                .put("wlVillageCode", wlVillageCode)
                .put("wlArea", wlArea);
        return json.toString();
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(WorkLocationTable.COLUMN_ID, this.id);
        json.put(WorkLocationTable.COLUMN_UID, this.uid);
        json.put(WorkLocationTable.COLUMN_AID, this.aid);
        json.put(WorkLocationTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(WorkLocationTable.COLUMN_USERNAME, this.userName);
        json.put(WorkLocationTable.COLUMN_UC_CODE, this.ucCode);
        json.put(WorkLocationTable.COLUMN_SYSDATE, this.sysDate);
        json.put(WorkLocationTable.COLUMN_DEVICEID, this.deviceId);
        json.put(WorkLocationTable.COLUMN_DEVICETAGID, this.deviceTag);
        //    json.put(FormsTable.COLUMN_ENTRY_TYPE, this.entryType);
        json.put(WorkLocationTable.COLUMN_ISTATUS, this.iStatus);
        json.put(WorkLocationTable.COLUMN_SYNCED, this.synced);
        json.put(WorkLocationTable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(WorkLocationTable.COLUMN_APPVERSION, this.appver);
        json.put(WorkLocationTable.COLUMN_GPSLAT, this.gpsLat);
        json.put(WorkLocationTable.COLUMN_GPSLNG, this.gpsLng);
        json.put(TableContracts.WorkLocationTable.COLUMN_GPSDATE, this.gpsDT);
        json.put(WorkLocationTable.COLUMN_GPSACC, this.gpsAcc);
        json.put(WorkLocationTable.COLUMN_SWL, new JSONObject(sWltoString()));
        return json;
    }
}