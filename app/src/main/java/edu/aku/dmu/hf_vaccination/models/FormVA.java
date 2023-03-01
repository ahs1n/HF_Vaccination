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
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.FormsVATable;
import edu.aku.dmu.hf_vaccination.core.MainApp;


public class FormVA extends BaseObservable implements Observable {

    private final String TAG = "FormVA";
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    // APP VARIABLES
    private String projectName = PROJECT_NAME;
    // APP VARIABLES
    private String id = _EMPTY_;
    private String uid = _EMPTY_;
    private String userName = _EMPTY_;
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
    private String backfilename = _EMPTY_;
    private String frontfilename = _EMPTY_;
    private String childfilename = _EMPTY_;
    private String gpsLat = _EMPTY_;
    private String gpsLng = _EMPTY_;
    private String gpsDT = _EMPTY_;
    private String gpsAcc = _EMPTY_;

    // FIELD VARIABLES
    private String va01 = _EMPTY_;
    private String va02 = _EMPTY_;
    private String va02a = _EMPTY_;
    private String va03 = _EMPTY_;
    private String va04 = _EMPTY_;
    private String va05 = _EMPTY_;
    private String va05a = _EMPTY_;
    private String va05ax = _EMPTY_;
    private String va05acheck = _EMPTY_;
    private String va05b = _EMPTY_;
    private String va05bx = _EMPTY_;
    private String va05bcheck = _EMPTY_;
    private String va05c = _EMPTY_;
    private String va05cx = _EMPTY_;
    private String va05ccheck = _EMPTY_;
    private String va05d = _EMPTY_;
    private String va05dx = _EMPTY_;
    private String va05dcheck = _EMPTY_;
    private String va05e = _EMPTY_;
    private String va05ex = _EMPTY_;
    private String va05echeck = _EMPTY_;
    private String va05f = _EMPTY_;
    private String va05fx = _EMPTY_;
    private String va05fcheck = _EMPTY_;
    private String va05g = _EMPTY_;
    private String va05gx = _EMPTY_;
    private String va05gcheck = _EMPTY_;
    private String va05h = _EMPTY_;
    private String va05hx = _EMPTY_;
    private String va05hcheck = _EMPTY_;
    private String va05i = _EMPTY_;
    private String va05ix = _EMPTY_;


    public FormVA() {

/*        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        setAppver(MainApp.appInfo.getAppVersion());
        setAppver(MainApp.appInfo.getAppVersion());*/

    }


    public void populateMeta() {

        setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setUserName(MainApp.user.getUserName());
        setDeviceId(MainApp.deviceid);
        //   setUuid(MainApp.form.getUid());  // not applicable in Form table
        setAppver(MainApp.appInfo.getAppVersion());
        setProjectName(PROJECT_NAME);
        // setEntryType(String.valueOf(MainApp.entryType));
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
    public String getBackfilename() {
        return backfilename;
    }

    public void setBackfilename(String backfilename) {
        if (this.backfilename.equals(backfilename)) return;
        this.backfilename = backfilename;

        notifyPropertyChanged(BR.backfilename);
    }

    @Bindable
    public String getFrontfilename() {
        return frontfilename;
    }

    public void setFrontfilename(String frontfilename) {
        if (this.frontfilename.equals(frontfilename)) return;
        this.frontfilename = frontfilename;

        notifyPropertyChanged(BR.frontfilename);
    }

    @Bindable
    public String getChildfilename() {
        return childfilename;
    }

    public void setChildfilename(String childfilename) {
        if (this.childfilename.equals(childfilename)) return;
        this.childfilename = childfilename;

        notifyPropertyChanged(BR.childfilename);
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
    public String getVa01() {
        return va01;
    }

    public void setVa01(String va01) {
        this.va01 = va01;
        notifyPropertyChanged(BR.va01);
    }

    @Bindable
    public String getVa02() {
        return va02;
    }

    public void setVa02(String va02) {
        this.va02 = va02;
        notifyPropertyChanged(BR.va02);
    }

    @Bindable
    public String getVa02a() {
        return va02a;
    }

    public void setVa02a(String va02a) {
        this.va02a = va02a;
        notifyPropertyChanged(BR.va02a);
    }

    @Bindable
    public String getVa03() {
        return va03;
    }

    public void setVa03(String va03) {
        this.va03 = va03;
        notifyPropertyChanged(BR.va03);
    }

    @Bindable
    public String getVa04() {
        return va04;
    }

    public void setVa04(String va04) {
        this.va04 = va04;
        notifyPropertyChanged(BR.va04);
    }

    @Bindable
    public String getVa05() {
        return va05;
    }

    public void setVa05(String va05) {
        this.va05 = va05;
        notifyPropertyChanged(BR.va05);
    }

    @Bindable
    public String getVa05a() {
        return va05a;
    }

    public void setVa05a(String va05a) {
        if (this.va05a.equals(va05a)) return; // for all checkboxes
        this.va05a = va05a;
        setVa05acheck(va05a.equals("1") ? this.va05acheck : "");
        setVa05ax(va05a.equals("1") ? this.va05ax : "");
        notifyPropertyChanged(BR.va05a);
    }

    @Bindable
    public String getVa05ax() {
        return va05ax;
    }

    public void setVa05ax(String va05ax) {
        this.va05ax = va05ax;
        notifyPropertyChanged(BR.va05ax);
    }

    @Bindable
    public String getVa05acheck() {
        return va05acheck;
    }

    public void setVa05acheck(String va05acheck) {
        if (this.va05acheck.equals(va05acheck)) return; // for all checkboxes
        this.va05acheck = va05acheck;
        /*if (va05acheck.equals("77") && va04.length() > 0) {
            setVa05ax(this.va04);
        } else {
            setVa05ax("");
        }*/
        notifyPropertyChanged(BR.va05acheck);
    }

    @Bindable
    public String getVa05b() {
        return va05b;
    }

    public void setVa05b(String va05b) {
        if (this.va05b.equals(va05b)) return; // for all checkboxes
        this.va05b = va05b;
        setVa05bcheck(va05b.equals("1") ? this.va05bcheck : "");
        setVa05bx(va05b.equals("1") ? this.va05bx : "");
        notifyPropertyChanged(BR.va05b);
    }

    @Bindable
    public String getVa05bx() {
        return va05bx;
    }

    public void setVa05bx(String va05bx) {
        this.va05bx = va05bx;
        notifyPropertyChanged(BR.va05bx);
    }

    @Bindable
    public String getVa05bcheck() {
        return va05bcheck;
    }

    public void setVa05bcheck(String va05bcheck) {
        if (this.va05bcheck.equals(va05bcheck)) return; // for all checkboxes
        this.va05bcheck = va05bcheck;
        notifyPropertyChanged(BR.va05bcheck);
    }

    @Bindable
    public String getVa05c() {
        return va05c;
    }

    public void setVa05c(String va05c) {
        if (this.va05c.equals(va05c)) return; // for all checkboxes
        this.va05c = va05c;
        setVa05ccheck(va05c.equals("1") ? this.va05ccheck : "");
        setVa05cx(va05c.equals("1") ? this.va05cx : "");
        notifyPropertyChanged(BR.va05c);
    }

    @Bindable
    public String getVa05cx() {
        return va05cx;
    }

    public void setVa05cx(String va05cx) {
        this.va05cx = va05cx;
        notifyPropertyChanged(BR.va05cx);
    }

    @Bindable
    public String getVa05ccheck() {
        return va05ccheck;
    }

    public void setVa05ccheck(String va05ccheck) {
        if (this.va05ccheck.equals(va05ccheck)) return; // for all checkboxes
        this.va05ccheck = va05ccheck;
        notifyPropertyChanged(BR.va05ccheck);
    }

    @Bindable
    public String getVa05d() {
        return va05d;
    }

    public void setVa05d(String va05d) {
        if (this.va05d.equals(va05d)) return; // for all checkboxes
        this.va05d = va05d;
        setVa05dcheck(va05d.equals("1") ? this.va05dcheck : "");
        setVa05dx(va05d.equals("1") ? this.va05dx : "");
        notifyPropertyChanged(BR.va05d);
    }

    @Bindable
    public String getVa05dx() {
        return va05dx;
    }

    public void setVa05dx(String va05dx) {
        this.va05dx = va05dx;
        notifyPropertyChanged(BR.va05dx);
    }

    @Bindable
    public String getVa05dcheck() {
        return va05dcheck;
    }

    public void setVa05dcheck(String va05dcheck) {
        if (this.va05dcheck.equals(va05dcheck)) return; // for all checkboxes
        this.va05dcheck = va05dcheck;
        notifyPropertyChanged(BR.va05dcheck);
    }

    @Bindable
    public String getVa05e() {
        return va05e;
    }

    public void setVa05e(String va05e) {
        if (this.va05e.equals(va05e)) return; // for all checkboxes
        this.va05e = va05e;
        setVa05echeck(va05e.equals("1") ? this.va05echeck : "");
        setVa05ex(va05e.equals("1") ? this.va05ex : "");
        notifyPropertyChanged(BR.va05e);
    }

    @Bindable
    public String getVa05ex() {
        return va05ex;
    }

    public void setVa05ex(String va05ex) {
        this.va05ex = va05ex;
        notifyPropertyChanged(BR.va05ex);
    }

    @Bindable
    public String getVa05echeck() {
        return va05echeck;
    }

    public void setVa05echeck(String va05echeck) {
        if (this.va05echeck.equals(va05echeck)) return; // for all checkboxes
        this.va05echeck = va05echeck;
        notifyPropertyChanged(BR.va05echeck);
    }

    @Bindable
    public String getVa05f() {
        return va05f;
    }

    public void setVa05f(String va05f) {
        if (this.va05f.equals(va05f)) return; // for all checkboxes
        this.va05f = va05f;
        setVa05fcheck(va05f.equals("1") ? this.va05fcheck : "");
        setVa05fx(va05f.equals("1") ? this.va05fx : "");
        notifyPropertyChanged(BR.va05f);
    }

    @Bindable
    public String getVa05fx() {
        return va05fx;
    }

    public void setVa05fx(String va05fx) {
        this.va05fx = va05fx;
        notifyPropertyChanged(BR.va05fx);
    }

    @Bindable
    public String getVa05fcheck() {
        return va05fcheck;
    }

    public void setVa05fcheck(String va05fcheck) {
        if (this.va05fcheck.equals(va05fcheck)) return; // for all checkboxes
        this.va05fcheck = va05fcheck;
        notifyPropertyChanged(BR.va05fcheck);
    }

    @Bindable
    public String getVa05g() {
        return va05g;
    }

    public void setVa05g(String va05g) {
        if (this.va05g.equals(va05g)) return; // for all checkboxes
        this.va05g = va05g;
        setVa05gcheck(va05g.equals("1") ? this.va05gcheck : "");
        setVa05gx(va05g.equals("1") ? this.va05gx : "");
        notifyPropertyChanged(BR.va05g);
    }

    @Bindable
    public String getVa05gx() {
        return va05gx;
    }

    public void setVa05gx(String va05gx) {
        this.va05gx = va05gx;
        notifyPropertyChanged(BR.va05gx);
    }

    @Bindable
    public String getVa05gcheck() {
        return va05gcheck;
    }

    public void setVa05gcheck(String va05gcheck) {
        if (this.va05gcheck.equals(va05gcheck)) return; // for all checkboxes
        this.va05gcheck = va05gcheck;
        notifyPropertyChanged(BR.va05gcheck);
    }

    @Bindable
    public String getVa05h() {
        return va05h;
    }

    public void setVa05h(String va05h) {
        if (this.va05h.equals(va05h)) return; // for all checkboxes
        this.va05h = va05h;
        setVa05hcheck(va05h.equals("1") ? this.va05hcheck : "");
        setVa05hx(va05h.equals("1") ? this.va05hx : "");
        notifyPropertyChanged(BR.va05h);
    }

    @Bindable
    public String getVa05hx() {
        return va05hx;
    }

    public void setVa05hx(String va05hx) {
        this.va05hx = va05hx;
        notifyPropertyChanged(BR.va05hx);
    }

    @Bindable
    public String getVa05hcheck() {
        return va05hcheck;
    }

    public void setVa05hcheck(String va05hcheck) {
        if (this.va05hcheck.equals(va05hcheck)) return; // for all checkboxes
        this.va05hcheck = va05hcheck;
        notifyPropertyChanged(BR.va05hcheck);
    }

    @Bindable
    public String getVa05i() {
        return va05i;
    }

    public void setVa05i(String va05i) {
        if (this.va05i.equals(va05i)) return; // for all checkboxes
        this.va05i = va05i;
        setVa05ix(va05i.equals("1") ? this.va05ix : "");
        notifyPropertyChanged(BR.va05i);
    }

    @Bindable
    public String getVa05ix() {
        return va05ix;
    }

    public void setVa05ix(String va05ix) {
        this.va05ix = va05ix;
        notifyPropertyChanged(BR.va05ix);
    }


    public FormVA Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_UID));
        this.projectName = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_PROJECT_NAME));
        this.sno = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_SNO));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_SYSDATE));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_DEVICEID));
        this.deviceTag = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_DEVICETAGID));
        //   this.entryType = cursor.getString(cursor.getColumnIndexOrThrow(FormsTable.COLUMN_ENTRY_TYPE));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_ISTATUS));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_SYNC_DATE));
        this.gpsLat = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_GPSLNG));
        this.gpsDT = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_GPSACC));

        vAHydrate(cursor.getString(cursor.getColumnIndexOrThrow(FormsVATable.COLUMN_VA)));
        return this;
    }

    public void vAHydrate(String string) throws JSONException {
        Log.d(TAG, "vAHydrate: " + string);
        if (string != null) {
            JSONObject json = null;
            json = new JSONObject(string);
            this.va01 = json.getString("va01");
            this.va02 = json.getString("va02");
            this.va02a = json.getString("va02a");
            this.va03 = json.getString("va03");
            this.va04 = json.getString("va04");
            this.va05a = json.getString("va05a");
            this.va05ax = json.getString("va05ax");
            this.va05acheck = json.getString("va05acheck");
            this.va05b = json.getString("va05b");
            this.va05bx = json.getString("va05bx");
            this.va05bcheck = json.getString("va05bcheck");
            this.va05c = json.getString("va05c");
            this.va05cx = json.getString("va05cx");
            this.va05ccheck = json.getString("va05ccheck");
            this.va05d = json.getString("va05d");
            this.va05dx = json.getString("va05dx");
            this.va05dcheck = json.getString("va05dcheck");
            this.va05e = json.getString("va05e");
            this.va05ex = json.getString("va05ex");
            this.va05echeck = json.getString("va05echeck");
            this.va05f = json.getString("va05f");
            this.va05fx = json.getString("va05fx");
            this.va05fcheck = json.getString("va05fcheck");
            this.va05g = json.getString("va05g");
            this.va05gx = json.getString("va05gx");
            this.va05gcheck = json.getString("va05gcheck");
            this.va05h = json.getString("va05h");
            this.va05hx = json.getString("va05hx");
            this.va05hcheck = json.getString("va05hcheck");
            this.va05i = json.getString("va05i");
            this.va05ix = json.getString("va05ix");

        }
    }

    public String vAtoString() throws JSONException {
        Log.d(TAG, "vAtoString: ");
        JSONObject json = new JSONObject();
        json.put("va01", va01)
                .put("va02", va02)
                .put("va02a", va02a)
                .put("va03", va03)
                .put("va04", va04)
                .put("va05a", va05a)
                .put("va05ax", va05ax)
                .put("va05acheck", va05acheck)
                .put("va05b", va05b)
                .put("va05bx", va05bx)
                .put("va05bcheck", va05bcheck)
                .put("va05c", va05c)
                .put("va05cx", va05cx)
                .put("va05ccheck", va05ccheck)
                .put("va05d", va05d)
                .put("va05dx", va05dx)
                .put("va05dcheck", va05dcheck)
                .put("va05e", va05e)
                .put("va05ex", va05ex)
                .put("va05echeck", va05echeck)
                .put("va05f", va05f)
                .put("va05fx", va05fx)
                .put("va05fcheck", va05fcheck)
                .put("va05g", va05g)
                .put("va05gx", va05gx)
                .put("va05gcheck", va05gcheck)
                .put("va05h", va05h)
                .put("va05hx", va05hx)
                .put("va05hcheck", va05hcheck)
                .put("va05i", va05i)
                .put("va05ix", va05ix);
        return json.toString();
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(FormsVATable.COLUMN_ID, this.id);
        json.put(FormsVATable.COLUMN_UID, this.uid);
        json.put(FormsVATable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(FormsVATable.COLUMN_SNO, this.sno);
        json.put(FormsVATable.COLUMN_USERNAME, this.userName);
        json.put(FormsVATable.COLUMN_SYSDATE, this.sysDate);
        json.put(FormsVATable.COLUMN_DEVICEID, this.deviceId);
        json.put(FormsVATable.COLUMN_DEVICETAGID, this.deviceTag);
        //    json.put(FormsTable.COLUMN_ENTRY_TYPE, this.entryType);
        json.put(FormsVATable.COLUMN_ISTATUS, this.iStatus);
        json.put(FormsVATable.COLUMN_SYNCED, this.synced);
        json.put(FormsVATable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(FormsVATable.COLUMN_APPVERSION, this.appver);
        json.put(FormsVATable.COLUMN_GPSLAT, this.gpsLat);
        json.put(FormsVATable.COLUMN_GPSLNG, this.gpsLng);
        json.put(FormsVATable.COLUMN_GPSDATE, this.gpsDT);
        json.put(FormsVATable.COLUMN_GPSACC, this.gpsAcc);
        json.put(FormsVATable.COLUMN_VA, new JSONObject(vAtoString()));
        return json;
    }
}