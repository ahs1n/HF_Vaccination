package edu.aku.dmu.hf_vaccination.models;

import static edu.aku.dmu.hf_vaccination.core.MainApp.PROJECT_NAME;
import static edu.aku.dmu.hf_vaccination.core.MainApp._EMPTY_;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccDueDates;

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
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.VaccinesDueTable;
import edu.aku.dmu.hf_vaccination.core.MainApp;


public class VaccDueDates extends BaseObservable implements Observable {

    private final String TAG = "VaccinesDueDates";
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    // APP VARIABLES
    private String projectName = PROJECT_NAME;
    // APP VARIABLES
    private String id = _EMPTY_;
    private String uid = _EMPTY_;
    private String uuid = _EMPTY_;
    private String aid = _EMPTY_;
    private String userName = _EMPTY_;
    private String sysDate = _EMPTY_;
    private String sno = _EMPTY_;
    private String deviceId = _EMPTY_;
    private String appver = _EMPTY_;
    private String synced = _EMPTY_;
    private String syncDate = _EMPTY_;
    private String ucCode = _EMPTY_;
    private String facilityCode = _EMPTY_;
    private String villageCode = _EMPTY_;
    private String wlArea = _EMPTY_;

    // FIELD VARIABLES
    private String vb02 = _EMPTY_;
    private String vb04a = _EMPTY_;
    private String vb04 = _EMPTY_;
    private String vb08CDueCode = _EMPTY_;
    private String vb08CDueAntigen = _EMPTY_;
    private String vb08CDueDate = _EMPTY_;


    public void populateMeta() {
        setUserName(MainApp.formVB.getUserName());
        setUcCode(MainApp.user.getUccode());
        setDeviceId(MainApp.deviceid);
        setUuid(MainApp.formVB.getUid());
        setAid(MainApp.attendance.getUid());
        setVb02(MainApp.formVB.getVb02());
        setVb04a(MainApp.formVB.getVb04a());
        setVb04(MainApp.formVB.getVb04());
        setVillageCode(MainApp.workLocation.getWlVillageCode());
        setFacilityCode(MainApp.workLocation.getWlFacilityCode());
        setWlArea(MainApp.workLocation.getWlArea());
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setAppver(MainApp.appInfo.getAppVersion());
        setProjectName(PROJECT_NAME);
    }

    public void populateMetaFollowUp() {
        setUserName(MainApp.user.getUserName());
        setUcCode(MainApp.user.getUccode());
        setDeviceId(MainApp.attendance.getDeviceId());
        setUuid(MainApp.vaccinesData.getUID());
        setAid(MainApp.attendance.getUid());
        setVb02(MainApp.vaccinesData.getVBO2());
        setVb04a(MainApp.vaccinesData.getVB04A());
        setVb04(MainApp.vaccinesData.getVB04());
        setVillageCode(MainApp.workLocation.getWlVillageCode());
        setFacilityCode(MainApp.workLocation.getWlFacilityCode());
        setWlArea(MainApp.workLocation.getWlArea());
        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    @Bindable
    public String getVb02() {
        return vb02;
    }

    public void setVb02(String vb02) {
        this.vb02 = vb02;
        notifyPropertyChanged(BR.vb02);
    }

    @Bindable
    public String getVb04a() {
        return vb04a;
    }

    public void setVb04a(String vb04a) {
        this.vb04a = vb04a;
        notifyPropertyChanged(BR.vb04a);
    }

    @Bindable
    public String getVb04() {
        return vb04;
    }

    public void setVb04(String vb04) {
        this.vb04 = vb04;
        notifyPropertyChanged(BR.vb04);
    }

    @Bindable
    public String getVb08CDueCode() {
        return vb08CDueCode;
    }

    public void setVb08CDueCode(String vb08CDueCode) {
        this.vb08CDueCode = vb08CDueCode;
        notifyPropertyChanged(BR.vb08CDueCode);
    }

    @Bindable
    public String getVb08CDueAntigen() {
        return vb08CDueAntigen;
    }

    public void setVb08CDueAntigen(String vb08CDueAntigen) {
        this.vb08CDueAntigen = vb08CDueAntigen;
        notifyPropertyChanged(BR.vb08CDueAntigen);
    }

    @Bindable
    public String getVb08CDueDate() {
        return vb08CDueDate;
    }

    public void setVb08CDueDate(String vb08CDueDate) {
        this.vb08CDueDate = vb08CDueDate;
        notifyPropertyChanged(BR.vb08CDueDate);
    }

    @Bindable
    public String getUcCode() {
        return ucCode;
    }

    public void setUcCode(String ucCode) {
        this.ucCode = ucCode;
    }

    @Bindable
    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
        notifyPropertyChanged(BR.villageCode);
    }

    @Bindable
    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
        notifyPropertyChanged(BR.facilityCode);
    }

    @Bindable
    public String getWlArea() {
        return wlArea;
    }

    public void setWlArea(String wlArea) {
        this.wlArea = wlArea;
        notifyPropertyChanged(BR.wlArea);
    }


    public VaccDueDates Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_UUID));
        this.aid = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_AID));
        this.projectName = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_PROJECT_NAME));
        this.sno = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_SNO));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_USERNAME));
        this.ucCode = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_UC_CODE));
        this.villageCode = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_VILLAGE_CODE));
        this.facilityCode = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_FACILITY_CODE));
        this.wlArea = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_AREA_NAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_SYSDATE));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_DEVICEID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_APPVERSION));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_SYNC_DATE));
        this.vb02 = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_VB02));
        this.vb04a = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_VB04A));
        this.vb04 = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_VB04));
        this.vb08CDueCode = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_VB08C_CODE));
        this.vb08CDueAntigen = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_VB08C_ANTIGEN));
        this.vb08CDueDate = cursor.getString(cursor.getColumnIndexOrThrow(VaccinesDueTable.COLUMN_VB08C_DATE));
        return this;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(VaccinesDueTable.COLUMN_ID, this.id);
        json.put(VaccinesDueTable.COLUMN_UID, this.uid);
        json.put(VaccinesDueTable.COLUMN_UUID, this.uuid);
        json.put(VaccinesDueTable.COLUMN_AID, this.aid);
        json.put(VaccinesDueTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(VaccinesDueTable.COLUMN_SNO, this.sno);
        json.put(VaccinesDueTable.COLUMN_USERNAME, this.userName);
        json.put(VaccinesDueTable.COLUMN_UC_CODE, this.ucCode);
        json.put(VaccinesDueTable.COLUMN_VILLAGE_CODE, this.villageCode);
        json.put(VaccinesDueTable.COLUMN_FACILITY_CODE, this.facilityCode);
        json.put(VaccinesDueTable.COLUMN_AREA_NAME, this.wlArea);
        json.put(VaccinesDueTable.COLUMN_SYSDATE, this.sysDate);
        json.put(VaccinesDueTable.COLUMN_DEVICEID, this.deviceId);
        json.put(VaccinesDueTable.COLUMN_SYNCED, this.synced);
        json.put(VaccinesDueTable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(VaccinesDueTable.COLUMN_APPVERSION, this.appver);
        json.put(VaccinesDueTable.COLUMN_VB02, this.vb02);
        json.put(VaccinesDueTable.COLUMN_VB04A, this.vb04a);
        json.put(VaccinesDueTable.COLUMN_VB04, this.vb04);
        json.put(VaccinesDueTable.COLUMN_VB08C_CODE, this.vb08CDueCode);
        json.put(VaccinesDueTable.COLUMN_VB08C_ANTIGEN, this.vb08CDueAntigen);
        json.put(VaccinesDueTable.COLUMN_VB08C_DATE, this.vb08CDueDate);
        return json;
    }

    public void updateDueAntigen() {
        setVb08CDueCode(vaccDueDates.getVb08CDueCode());
        setVb08CDueAntigen(vaccDueDates.getVb08CDueAntigen());
        setVb08CDueDate(vaccDueDates.getVb08CDueDate());
    }
}