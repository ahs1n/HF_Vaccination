package edu.aku.dmu.hf_vaccination.database;

import static edu.aku.dmu.hf_vaccination.core.MainApp.IBAHC;
import static edu.aku.dmu.hf_vaccination.core.MainApp.PROJECT_NAME;
import static edu.aku.dmu.hf_vaccination.core.MainApp.attendance;
import static edu.aku.dmu.hf_vaccination.core.MainApp.dueDates;
import static edu.aku.dmu.hf_vaccination.core.MainApp.formVB;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccDueDates;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccines;
import static edu.aku.dmu.hf_vaccination.core.UserAuth.checkPassword;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_ALTER_ADD_DOB;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_ALTER_ADD_DPT;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_ALTER_ADD_USERNAME;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_ALTER_ADD_VB07;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_ALTER_ADD_vB07;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_ATTENDANCE;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_DUE_VACCINE;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_ENTRYLOGS;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_FORMSVA;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_FORMSVB;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_HF;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_UC;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_USERS;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_VACCINE;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_VACCINESCHEDULE;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_VACCINESDATA;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_VERSIONAPP;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_VILLAGES;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_WOMENFOLLOWUP;
import static edu.aku.dmu.hf_vaccination.database.CreateTable.SQL_CREATE_WORK_LOCATION;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.dmu.hf_vaccination.contracts.TableContracts;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.AttendanceTable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.EntryLogTable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.FormCRTable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.FormWRTable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.FormsVATable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.FormsVBTable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableHealthFacilities;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableUCs;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableVaccineSchedule;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableVaccinesData;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableVillages;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableWomenFollowUP;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.UsersTable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.VaccinesDueTable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.VaccinesTable;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.WorkLocationTable;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.models.Attendance;
import edu.aku.dmu.hf_vaccination.models.EntryLog;
import edu.aku.dmu.hf_vaccination.models.FormCR;
import edu.aku.dmu.hf_vaccination.models.FormVA;
import edu.aku.dmu.hf_vaccination.models.FormVB;
import edu.aku.dmu.hf_vaccination.models.FormWR;
import edu.aku.dmu.hf_vaccination.models.HealthFacilities;
import edu.aku.dmu.hf_vaccination.models.UCs;
import edu.aku.dmu.hf_vaccination.models.Users;
import edu.aku.dmu.hf_vaccination.models.VaccDueDates;
import edu.aku.dmu.hf_vaccination.models.Vaccines;
import edu.aku.dmu.hf_vaccination.models.VaccinesData;
import edu.aku.dmu.hf_vaccination.models.VaccinesSchedule;
import edu.aku.dmu.hf_vaccination.models.Villages;
import edu.aku.dmu.hf_vaccination.models.WomenFollowUP;
import edu.aku.dmu.hf_vaccination.models.WorkLocation;



/*import edu.aku.hassannaqvi.naunehal.models.Immunization;*/

/**
 * @author hassan.naqvi on 11/30/2016.
 * @update ali hussa on 01/07/21
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = PROJECT_NAME + ".db";
    public static final String DATABASE_COPY = PROJECT_NAME + "_copy.db";
    public static final String DATABASE_PASSWORD = IBAHC;
    private static final int DATABASE_VERSION = 6;
    private static final String SQL_DELETE_VACCINESDATA = "DROP TABLE IF EXISTS " + TableContracts.TableVaccinesData.TABLE_NAME;
    private static final String SQL_DELETE_WOMEN_FOLLOWUP = "DROP TABLE IF EXISTS " + TableContracts.TableWomenFollowUP.TABLE_NAME;
    private static final String SQL_DELETE_VACCINESSCHEDULE = "DROP TABLE IF EXISTS " + TableVaccineSchedule.TABLE_NAME;
    public static boolean isSyncUCs = false;
    public static boolean isSyncVillages = false;
    public static boolean isSyncHF = false;
    public static boolean isSyncVaccDB = false;
    public static boolean isSyncWomenDB = false;
    private final String TAG = "DatabaseHelper";
    private final Context mContext;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_USERS);
            db.execSQL(SQL_CREATE_FORMSVA);
            db.execSQL(SQL_CREATE_FORMSVB);
            db.execSQL(SQL_CREATE_VACCINE);
            db.execSQL(SQL_CREATE_DUE_VACCINE);
            db.execSQL(SQL_CREATE_WORK_LOCATION);
            db.execSQL(SQL_CREATE_ATTENDANCE);
            db.execSQL(SQL_CREATE_HF);
            db.execSQL(SQL_CREATE_UC);
            db.execSQL(SQL_CREATE_VACCINESDATA);
            db.execSQL(SQL_CREATE_VILLAGES);
            db.execSQL(SQL_CREATE_VERSIONAPP);
            db.execSQL(SQL_CREATE_ENTRYLOGS);
            db.execSQL(SQL_CREATE_VACCINESCHEDULE);
            db.execSQL(SQL_CREATE_WOMENFOLLOWUP);
        } catch (Exception ex) {
            Log.e(TAG, "onCreate: " + "Database Crash");
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(SQL_DELETE_VACCINESDATA);
                db.execSQL(SQL_CREATE_VACCINESDATA);
            case 2:
                db.execSQL(SQL_ALTER_ADD_DOB);
            case 3:
                db.execSQL(SQL_DELETE_VACCINESSCHEDULE);
                db.execSQL(SQL_CREATE_VACCINESCHEDULE);
                db.execSQL(SQL_CREATE_WOMENFOLLOWUP);
                db.execSQL(SQL_CREATE_DUE_VACCINE);
            case 4:
                db.execSQL(SQL_ALTER_ADD_DPT);
                db.execSQL(SQL_ALTER_ADD_USERNAME);
            case 5:
                db.execSQL(SQL_ALTER_ADD_VB07);
                db.execSQL(SQL_ALTER_ADD_vB07);

        }

    }

    //ADDITION in DB
    public Long addFormVA(FormVA formVA) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(FormsVATable.COLUMN_PROJECT_NAME, formVA.getProjectName());
        values.put(FormsVATable.COLUMN_UID, formVA.getUid());
        values.put(FormsVATable.COLUMN_SNO, formVA.getSno());
        values.put(FormsVATable.COLUMN_USERNAME, formVA.getUserName());
        values.put(FormsVATable.COLUMN_SYSDATE, formVA.getSysDate());
        values.put(FormsVATable.COLUMN_VA, formVA.vAtoString());
        values.put(FormsVATable.COLUMN_GPSLAT, formVA.getGpsLat());
        values.put(FormsVATable.COLUMN_GPSLNG, formVA.getGpsLng());
        values.put(FormsVATable.COLUMN_GPSDATE, formVA.getGpsDT());
        values.put(FormsVATable.COLUMN_GPSACC, formVA.getGpsAcc());
        values.put(FormsVATable.COLUMN_ISTATUS, formVA.getiStatus());
        values.put(FormsVATable.COLUMN_DEVICETAGID, formVA.getDeviceTag());
        values.put(FormsVATable.COLUMN_DEVICEID, formVA.getDeviceId());
        values.put(FormsVATable.COLUMN_APPVERSION, formVA.getAppver());
        values.put(FormsVATable.COLUMN_SYNCED, formVA.getSynced());
        values.put(FormsVATable.COLUMN_SYNC_DATE, formVA.getSyncDate());

        long newRowId;
        newRowId = db.insert(
                FormsVATable.TABLE_NAME,
                FormsVATable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addFormVB(FormVB formVB) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(FormsVBTable.COLUMN_PROJECT_NAME, formVB.getProjectName());
        values.put(FormsVBTable.COLUMN_UID, formVB.getUid());
        values.put(FormsVBTable.COLUMN_WID, formVB.getWid());
        values.put(FormsVBTable.COLUMN_AID, formVB.getAid());
        values.put(FormsVBTable.COLUMN_UUID, formVB.getUuid());
        values.put(FormsVBTable.COLUMN_SNO, formVB.getSno());
        values.put(FormsVBTable.COLUMN_USERNAME, formVB.getUserName());
        values.put(FormsVBTable.COLUMN_UC_CODE, formVB.getUcCode());
        values.put(FormsVBTable.COLUMN_VILLAGE_CODE, formVB.getVillageCode());
        values.put(FormsVBTable.COLUMN_FACILITY_CODE, formVB.getFacilityCode());
        values.put(FormsVBTable.COLUMN_AREA_NAME, formVB.getWlArea());
        values.put(FormsVBTable.COLUMN_GPSLAT, formVB.getGpsLat());
        values.put(FormsVBTable.COLUMN_GPSLNG, formVB.getGpsLng());
        values.put(FormsVBTable.COLUMN_GPSDATE, formVB.getGpsDT());
        values.put(FormsVBTable.COLUMN_GPSACC, formVB.getGpsAcc());
        values.put(FormsVBTable.COLUMN_SYSDATE, formVB.getSysDate());
        values.put(FormsVBTable.COLUMN_VB, formVB.vBtoString());
        values.put(FormsVBTable.COLUMN_VAC, formVB.vACtoString());
        values.put(FormsVBTable.COLUMN_ISTATUS, formVB.getiStatus());
        values.put(FormsVBTable.COLUMN_DEVICETAGID, formVB.getDeviceTag());
        values.put(FormsVBTable.COLUMN_CARD_NO, formVB.getCardNo());
        values.put(FormsVBTable.COLUMN_VB04A_NAME, formVB.getVb04aName());
        values.put(FormsVBTable.COLUMN_DEVICEID, formVB.getDeviceId());
        values.put(FormsVBTable.COLUMN_APPVERSION, formVB.getAppver());
        values.put(FormsVBTable.COLUMN_SYNCED, formVB.getSynced());
        values.put(FormsVBTable.COLUMN_SYNC_DATE, formVB.getSyncDate());

        long newRowId;
        newRowId = db.insert(
                FormsVBTable.TABLE_NAME,
                FormsVBTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addVaccine(Vaccines vaccines) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(VaccinesTable.COLUMN_PROJECT_NAME, vaccines.getProjectName());
        values.put(VaccinesTable.COLUMN_UID, vaccines.getUid());
        values.put(VaccinesTable.COLUMN_UUID, vaccines.getUuid());
        values.put(VaccinesTable.COLUMN_AID, vaccines.getAid());
        values.put(VaccinesTable.COLUMN_SNO, vaccines.getSno());
        values.put(VaccinesTable.COLUMN_USERNAME, vaccines.getUserName());
        values.put(VaccinesTable.COLUMN_UC_CODE, vaccines.getUcCode());
        values.put(VaccinesTable.COLUMN_VILLAGE_CODE, vaccines.getVillageCode());
        values.put(VaccinesTable.COLUMN_FACILITY_CODE, vaccines.getFacilityCode());
        values.put(VaccinesTable.COLUMN_AREA_NAME, vaccines.getWlArea());
        values.put(VaccinesTable.COLUMN_SYSDATE, vaccines.getSysDate());

        values.put(VaccinesTable.COLUMN_GPSLNG, vaccines.getGpsLng());
        values.put(VaccinesTable.COLUMN_GPSLAT, vaccines.getGpsLat());
        values.put(VaccinesTable.COLUMN_GPSACC, vaccines.getGpsAcc());
        values.put(VaccinesTable.COLUMN_GPSDATE, vaccines.getGpsDT());

        values.put(VaccinesTable.COLUMN_VB02, vaccines.getVb02());
        values.put(VaccinesTable.COLUMN_VB04A, vaccines.getVb04a());
        values.put(VaccinesTable.COLUMN_VB04, vaccines.getVb04());
        values.put(VaccinesTable.COLUMN_VB07, vaccines.getVb07());
        values.put(VaccinesTable.COLUMN_VB08C_CODE, vaccines.getVb08CCode());
        values.put(VaccinesTable.COLUMN_VB08C_ANTIGEN, vaccines.getVb08CAntigen());
        values.put(VaccinesTable.COLUMN_VB08C_DATE, vaccines.getVb08CDate());
        values.put(VaccinesTable.COLUMN_FRONT_FILE_NAME, vaccines.getFrontfilename());
        values.put(VaccinesTable.COLUMN_BACK_FILE_NAME, vaccines.getBackfilename());
        values.put(VaccinesTable.COLUMN_CHILD_FILE_NAME, vaccines.getChildfilename());
        values.put(VaccinesTable.COLUMN_VB08W_CODE, vaccines.getVb08WCode());
        values.put(VaccinesTable.COLUMN_VB08W_ANTIGEN, vaccines.getVb08WAntigen());
        values.put(VaccinesTable.COLUMN_VB08W_DATE, vaccines.getVb08WDate());

        values.put(VaccinesTable.COLUMN_ISTATUS, vaccines.getiStatus());
        values.put(VaccinesTable.COLUMN_DEVICETAGID, vaccines.getDeviceTag());
        values.put(VaccinesTable.COLUMN_DEVICEID, vaccines.getDeviceId());
        values.put(VaccinesTable.COLUMN_APPVERSION, vaccines.getAppver());
        values.put(VaccinesTable.COLUMN_SYNCED, vaccines.getSynced());
        values.put(VaccinesTable.COLUMN_SYNC_DATE, vaccines.getSyncDate());

        long newRowId;
        newRowId = db.insert(
                VaccinesTable.TABLE_NAME,
                VaccinesTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addDueVaccine(VaccDueDates vaccines) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(TableContracts.VaccinesDueTable.COLUMN_PROJECT_NAME, vaccines.getProjectName());
        values.put(TableContracts.VaccinesDueTable.COLUMN_UID, vaccines.getUid());
        values.put(TableContracts.VaccinesDueTable.COLUMN_UUID, vaccines.getUuid());
        values.put(TableContracts.VaccinesDueTable.COLUMN_AID, vaccines.getAid());
        values.put(TableContracts.VaccinesDueTable.COLUMN_SNO, vaccines.getSno());
        values.put(TableContracts.VaccinesDueTable.COLUMN_USERNAME, vaccines.getUserName());
        values.put(TableContracts.VaccinesDueTable.COLUMN_UC_CODE, vaccines.getUcCode());
        values.put(TableContracts.VaccinesDueTable.COLUMN_VILLAGE_CODE, vaccines.getVillageCode());
        values.put(TableContracts.VaccinesDueTable.COLUMN_FACILITY_CODE, vaccines.getFacilityCode());
        values.put(TableContracts.VaccinesDueTable.COLUMN_AREA_NAME, vaccines.getWlArea());
        values.put(TableContracts.VaccinesDueTable.COLUMN_SYSDATE, vaccines.getSysDate());
        values.put(TableContracts.VaccinesDueTable.COLUMN_VB02, vaccines.getVb02());
        values.put(TableContracts.VaccinesDueTable.COLUMN_VB04A, vaccines.getVb04a());
        values.put(TableContracts.VaccinesDueTable.COLUMN_VB04, vaccines.getVb04());
        values.put(TableContracts.VaccinesDueTable.COLUMN_VB08C_CODE, vaccines.getVb08CDueCode());
        values.put(TableContracts.VaccinesDueTable.COLUMN_VB08C_ANTIGEN, vaccines.getVb08CDueAntigen());
        values.put(TableContracts.VaccinesDueTable.COLUMN_VB08C_DATE, vaccines.getVb08CDueDate());
        values.put(TableContracts.VaccinesDueTable.COLUMN_DEVICEID, vaccines.getDeviceId());
        values.put(TableContracts.VaccinesDueTable.COLUMN_APPVERSION, vaccines.getAppver());
        values.put(TableContracts.VaccinesDueTable.COLUMN_SYNCED, vaccines.getSynced());
        values.put(TableContracts.VaccinesDueTable.COLUMN_SYNC_DATE, vaccines.getSyncDate());

        long newRowId;
        newRowId = db.insert(
                VaccinesDueTable.TABLE_NAME,
                VaccinesDueTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    //ADDITION in DB
    public Long addWorkLocation(WorkLocation workLocation) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(WorkLocationTable.COLUMN_PROJECT_NAME, workLocation.getProjectName());
        values.put(WorkLocationTable.COLUMN_UID, workLocation.getUid());
        values.put(WorkLocationTable.COLUMN_AID, workLocation.getAid());
        values.put(WorkLocationTable.COLUMN_USERNAME, workLocation.getUserName());
        values.put(WorkLocationTable.COLUMN_UC_CODE, workLocation.getUcCode());
        values.put(WorkLocationTable.COLUMN_SYSDATE, workLocation.getSysDate());
        values.put(WorkLocationTable.COLUMN_GPSLAT, workLocation.getGpsLat());
        values.put(WorkLocationTable.COLUMN_GPSLNG, workLocation.getGpsLng());
        values.put(WorkLocationTable.COLUMN_GPSDATE, workLocation.getGpsDT());
        values.put(WorkLocationTable.COLUMN_GPSACC, workLocation.getGpsAcc());
        values.put(WorkLocationTable.COLUMN_ISTATUS, workLocation.getiStatus());
        values.put(WorkLocationTable.COLUMN_DEVICETAGID, workLocation.getDeviceTag());
        values.put(WorkLocationTable.COLUMN_DEVICEID, workLocation.getDeviceId());
        values.put(WorkLocationTable.COLUMN_APPVERSION, workLocation.getAppver());
        values.put(WorkLocationTable.COLUMN_SYNCED, workLocation.getSynced());
        values.put(WorkLocationTable.COLUMN_SYNC_DATE, workLocation.getSyncDate());
        values.put(WorkLocationTable.COLUMN_SWL, workLocation.sWltoString());

        long newRowId;
        newRowId = db.insert(
                WorkLocationTable.TABLE_NAME,
                TableContracts.WorkLocationTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    //ADDITION in DB
    public Long addAttendance(Attendance attendance) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(AttendanceTable.COLUMN_PROJECT_NAME, attendance.getProjectName());
        values.put(AttendanceTable.COLUMN_UID, attendance.getUid());
        values.put(AttendanceTable.COLUMN_USERNAME, attendance.getUserName());
        values.put(AttendanceTable.COLUMN_UC_CODE, attendance.getUcCode());
        values.put(AttendanceTable.COLUMN_SYSDATE, attendance.getSysDate());
        values.put(AttendanceTable.COLUMN_GPSLAT, attendance.getGpsLat());
        values.put(AttendanceTable.COLUMN_GPSLNG, attendance.getGpsLng());
        values.put(AttendanceTable.COLUMN_GPSDATE, attendance.getGpsDT());
        values.put(AttendanceTable.COLUMN_GPSACC, attendance.getGpsAcc());
        values.put(AttendanceTable.COLUMN_DEVICEID, attendance.getDeviceId());
        values.put(AttendanceTable.COLUMN_APPVERSION, attendance.getAppver());
        values.put(AttendanceTable.COLUMN_SYNCED, attendance.getSynced());
        values.put(TableContracts.AttendanceTable.COLUMN_SYNC_DATE, attendance.getSyncDate());

        long newRowId;
        newRowId = db.insert(
                AttendanceTable.TABLE_NAME,
                TableContracts.AttendanceTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addEntryLog(EntryLog entryLog) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(EntryLogTable.COLUMN_PROJECT_NAME, entryLog.getProjectName());
        values.put(EntryLogTable.COLUMN_UUID, entryLog.getUuid());
        values.put(EntryLogTable.COLUMN_HHID, entryLog.getHhid());
        values.put(EntryLogTable.COLUMN_USERNAME, entryLog.getUserName());
        values.put(EntryLogTable.COLUMN_SYSDATE, entryLog.getSysDate());
        values.put(EntryLogTable.COLUMN_ISTATUS, entryLog.getiStatus());
        values.put(EntryLogTable.COLUMN_ISTATUS96x, entryLog.getiStatus96x());
        values.put(EntryLogTable.COLUMN_ENTRY_TYPE, entryLog.getEntryType());
        values.put(EntryLogTable.COLUMN_ENTRY_DATE, entryLog.getEntryDate());
        values.put(EntryLogTable.COLUMN_DEVICEID, entryLog.getDeviceId());
        values.put(EntryLogTable.COLUMN_SYNCED, entryLog.getSynced());
        values.put(EntryLogTable.COLUMN_SYNC_DATE, entryLog.getSyncDate());
        values.put(EntryLogTable.COLUMN_APPVERSION, entryLog.getAppver());

        long newRowId;
        newRowId = db.insertOrThrow(
                EntryLogTable.TABLE_NAME,
                EntryLogTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addCR(FormCR cr) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormCRTable.COLUMN_PROJECT_NAME, cr.getProjectName());
        values.put(FormCRTable.COLUMN_UID, cr.getUid());
        values.put(FormCRTable.COLUMN_USERNAME, cr.getUserName());
        values.put(FormCRTable.COLUMN_SYSDATE, cr.getSysDate());
        values.put(FormCRTable.COLUMN_ISTATUS, cr.getiStatus());
        values.put(FormCRTable.COLUMN_DEVICETAGID, cr.getDeviceTag());
        values.put(FormCRTable.COLUMN_DEVICEID, cr.getDeviceId());
        values.put(FormCRTable.COLUMN_APPVERSION, cr.getAppver());
        values.put(FormCRTable.COLUMN_START_TIME, cr.getStartTime());
        values.put(FormCRTable.COLUMN_END_TIME, cr.getEndTime());

        // Put all JSON as xxtoString()
        values.put(FormCRTable.COLUMN_CR, cr.cRtoString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormCRTable.TABLE_NAME,
                FormCRTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addWR(FormWR wr) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(FormWRTable.COLUMN_PROJECT_NAME, wr.getProjectName());
        values.put(FormWRTable.COLUMN_UID, wr.getUid());
        values.put(FormWRTable.COLUMN_USERNAME, wr.getUserName());
        values.put(FormWRTable.COLUMN_SYSDATE, wr.getSysDate());
        values.put(FormWRTable.COLUMN_ISTATUS, wr.getiStatus());
        values.put(FormWRTable.COLUMN_DEVICETAGID, wr.getDeviceTag());
        values.put(FormWRTable.COLUMN_DEVICEID, wr.getDeviceId());
        values.put(FormWRTable.COLUMN_APPVERSION, wr.getAppver());
        values.put(FormWRTable.COLUMN_START_TIME, wr.getStartTime());
        values.put(FormWRTable.COLUMN_END_TIME, wr.getEndTime());

        // Put all JSON as xxtoString()
        values.put(FormWRTable.COLUMN_WR, wr.wRtoString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormWRTable.TABLE_NAME,
                FormWRTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    //UPDATE in DB
    public int updatesFormVAColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsVATable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.formVA.getId())};

        return db.update(FormsVATable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesFormVBColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsVBTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(formVB.getId())};

        return db.update(FormsVBTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesVaccineColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = VaccinesTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(vaccines.getId())};

        return db.update(VaccinesTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesDueVaccineColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = VaccinesDueTable._ID + " =? ";
        String[] selectionArgs;
        if (!vaccDueDates.getId().equals("")) {
            selectionArgs = new String[]{String.valueOf(vaccDueDates.getId())};
        } else {
            selectionArgs = new String[]{String.valueOf(dueDates.getId())};
        }

        return db.update(VaccinesDueTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesAttenColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = AttendanceTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(attendance.getId())};

        return db.update(AttendanceTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    /*
     * Functions that dealing with table data
     * */

    public int updatesWorkLocationColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = TableContracts.WorkLocationTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.workLocation.getId())};

        return db.update(WorkLocationTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesEntryLogColumn(String column, String value, String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = EntryLogTable._ID + " =? ";
        String[] selectionArgs = {id};

        return db.update(EntryLogTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateCrColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormCRTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.cr.getId())};

        return db.update(FormCRTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateWrColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormWRTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.wr.getId())};

        return db.update(FormWRTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    /*public int updateTemp(String assessNo, String temp) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_TSF305, temp);
        values.put(FormsTable.COLUMN_SYSDATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()) + "-Updated");
        values.put(FormsTable.COLUMN_ISTATUS, "1");
        values.put(FormsTable.COLUMN_SYNCED, (byte[]) null);

        String selection = FormsTable.COLUMN_ASSESMENT_NO + " =? AND " + FormsTable.COLUMN_ISTATUS + " =? ";
        // String selection = FormsTable.COLUMN_ASSESMENT_NO + " =? ";
        String[] selectionArgs = {assessNo, "9"};
        // String[] selectionArgs = {assessNo};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }*/

    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FormCRTable.COLUMN_ISTATUS, MainApp.cr.getiStatus());

        // Which row to update, based on the ID
        String selection = FormCRTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.cr.getId())};

        return db.update(FormCRTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public boolean doLogin(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalArgumentException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = UsersTable.COLUMN_USERNAME + "=? ";
        String[] whereArgs = {username};
        String groupBy = null;
        String having = null;
        String orderBy = UsersTable.COLUMN_ID + " ASC";

        Users loggedInUser = new Users();
        c = db.query(
                UsersTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            loggedInUser = new Users().hydrate(c);

        }

        c.close();

        if (checkPassword(password, loggedInUser.getPassword())) {
            MainApp.user = loggedInUser;
            return c.getCount() > 0;
        } else {
            return false;
        }
    }
/*

    public int syncClusters(JSONArray clusterList) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(ClustersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < clusterList.length(); i++) {

                JSONObject json = clusterList.getJSONObject(i);

                Clusters cluster = new Clusters();
                cluster.sync(json);
                ContentValues values = new ContentValues();

                values.put(ClustersTable.COLUMN_DISTRICT_NAME, cluster.getDistrictName());
                values.put(ClustersTable.COLUMN_DISTRICT_CODE, cluster.getDistrictCode());
                values.put(ClustersTable.COLUMN_CITY_NAME, cluster.getCityName());
                values.put(ClustersTable.COLUMN_CITY_CODE, cluster.getCityCode());
                values.put(ClustersTable.COLUMN_CLUSTER_NO, cluster.getClusterNo());
                long rowID = db.insert(ClustersTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncClusters(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncRandom(JSONArray list) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(RandomTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < list.length(); i++) {

                JSONObject json = list.getJSONObject(i);

                RandomHH ran = new RandomHH();
                ran.sync(json);
                ContentValues values = new ContentValues();
                values.put(RandomTable.COLUMN_ID, ran.getID());
                values.put(RandomTable.COLUMN_SNO, ran.getSno());
                values.put(RandomTable.COLUMN_CLUSTER_NO, ran.getClusterNo());
                values.put(RandomTable.COLUMN_HH_NO, ran.getHhno());
                values.put(RandomTable.COLUMN_HEAD_HH, ran.getHeadhh());
                long rowID = db.insert(RandomTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncRandom(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

*/

    public ArrayList<FormCR> getFormsByDate(String sysdate) {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormCRTable._ID,
                FormCRTable.COLUMN_UID,
                FormCRTable.COLUMN_SYSDATE,
                FormCRTable.COLUMN_USERNAME,
                FormCRTable.COLUMN_ISTATUS,
                FormCRTable.COLUMN_SYNCED,

        };
        String whereClause = FormCRTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
        String groupBy = null;
        String having = null;
        String orderBy = FormCRTable.COLUMN_ID + " ASC";
        ArrayList<FormCR> allForms = new ArrayList<>();
        c = db.query(
                FormCRTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            FormCR forms = new FormCR();
            forms.setId(c.getString(c.getColumnIndexOrThrow(FormCRTable.COLUMN_ID)));
            forms.setUid(c.getString(c.getColumnIndexOrThrow(FormCRTable.COLUMN_UID)));
            forms.setSysDate(c.getString(c.getColumnIndexOrThrow(FormCRTable.COLUMN_SYSDATE)));
            forms.setUserName(c.getString(c.getColumnIndexOrThrow(FormCRTable.COLUMN_USERNAME)));
            allForms.add(forms);
        }
        return allForms;
    }

    // istatus examples: (1) or (1,9) or (1,3,5)
    public FormCR getFormByAssessNo(String uid, String istatus) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FormCRTable.COLUMN_UID + "=? AND " +
                FormCRTable.COLUMN_ISTATUS + " in " + istatus;

        String[] whereArgs = {uid};

        String groupBy = null;
        String having = null;

        String orderBy = FormCRTable.COLUMN_ID + " ASC";

        FormCR allFC = null;
        c = db.query(
                FormCRTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            allFC = new FormCR().Hydrate(c);
        }
        return allFC;
    }

    public ArrayList<Cursor> getDatabaseManagerData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase(DATABASE_PASSWORD);
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(Query, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (Exception sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    public int syncversionApp(JSONArray VersionList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        long count = 0;

        JSONObject jsonObjectVersion = ((JSONArray) VersionList.getJSONObject(0).get("elements")).getJSONObject(0);

        String appPath = jsonObjectVersion.getString("outputFile");
        String versionCode = jsonObjectVersion.getString("versionCode");

        MainApp.editor.putString("outputFile", jsonObjectVersion.getString("outputFile"));
        MainApp.editor.putString("versionCode", jsonObjectVersion.getString("versionCode"));
        MainApp.editor.putString("versionName", jsonObjectVersion.getString("versionName") + ".");
        MainApp.editor.apply();
        count++;
          /*  VersionApp Vc = new VersionApp();
            Vc.sync(jsonObjectVersion);

            ContentValues values = new ContentValues();

            values.put(VersionTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }*/

        return (int) count;
    }

    public int syncusers(JSONArray userList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        for (int i = 0; i < userList.length(); i++) {

            JSONObject jsonObjectUser = userList.getJSONObject(i);

            Users user = new Users();
            user.sync(jsonObjectUser);
            ContentValues values = new ContentValues();

            values.put(UsersTable.COLUMN_USERNAME, user.getUserName());
            values.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
            values.put(UsersTable.COLUMN_FULLNAME, user.getFullname());
            values.put(UsersTable.COLUMN_ENABLED, user.getEnabled());
            values.put(UsersTable.COLUMN_ISNEW_USER, user.getNewUser());
            values.put(UsersTable.COLUMN_PWD_EXPIRY, user.getPwdExpiry());
            values.put(UsersTable.COLUMN_DESIGNATION, user.getDesignation());
            values.put(UsersTable.COLUMN_DIST_ID, user.getDist_id());
            values.put(UsersTable.COLUMN_UC_CODE, user.getUccode());
            long rowID = db.insert(UsersTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        return insertCount;
    }

    //get UnSyncedTables
    public JSONArray getUnsyncedFormVA() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        /*whereClause = FormsTable.COLUMN_SYNCED + " = '' AND " +
                FormsTable.COLUMN_ISTATUS + "!= ''";*/
        whereClause = FormsVATable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FormsVATable.COLUMN_ID + " ASC";

        JSONArray allForms = new JSONArray();
        c = db.query(
                FormsVATable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Form fc = new Form();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedFormVA: " + c.getCount());
            FormVA formVA = new FormVA();
            allForms.put(formVA.Hydrate(c).toJSONObject());


        }

        c.close();

        Log.d(TAG, "getUnsyncedFormVA: " + allForms.toString().length());
        Log.d(TAG, "getUnsyncedFormVA: " + allForms);
        return allForms;
    }

    public JSONArray getUnsyncedFormVB() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        /*whereClause = FormsTable.COLUMN_SYNCED + " = '' AND " +
                FormsTable.COLUMN_ISTATUS + "!= ''";*/
        whereClause = FormsVBTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FormsVBTable.COLUMN_ID + " ASC";

        JSONArray allForms = new JSONArray();
        c = db.query(
                FormsVBTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Form fc = new Form();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedFormVB: " + c.getCount());
            FormVB formVB = new FormVB();
            allForms.put(formVB.Hydrate(c).toJSONObject());

        }

        c.close();

        Log.d(TAG, "getUnsyncedFormVB: " + allForms.toString().length());
        Log.d(TAG, "getUnsyncedFormVB: " + allForms);
        return allForms;
    }

    public JSONArray getUnsyncedVaccine() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        /*whereClause = FormsTable.COLUMN_SYNCED + " = '' AND " +
                FormsTable.COLUMN_ISTATUS + "!= ''";*/
        whereClause = VaccinesTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = VaccinesTable.COLUMN_ID + " ASC";

        JSONArray allVaccines = new JSONArray();
        c = db.query(
                VaccinesTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Form fc = new Form();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedVaccines: " + c.getCount());
            Vaccines vaccines = new Vaccines();
            allVaccines.put(vaccines.Hydrate(c).toJSONObject());

        }

        c.close();

        Log.d(TAG, "getUnsyncedVaccines: " + allVaccines.toString().length());
        Log.d(TAG, "getUnsyncedVaccines: " + allVaccines);
        return allVaccines;
    }

    public JSONArray getUnsyncedWorkLocation() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause = TableContracts.WorkLocationTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = TableContracts.WorkLocationTable.COLUMN_ID + " ASC";

        JSONArray allWorkLocation = new JSONArray();
        c = db.query(
                TableContracts.WorkLocationTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedWorkLocation: " + c.getCount());
            WorkLocation workLocation = new WorkLocation();
            allWorkLocation.put(workLocation.Hydrate(c).toJSONObject());
        }

        c.close();

        Log.d(TAG, "getUnsyncedWorkLocation: " + allWorkLocation.toString().length());
        Log.d(TAG, "getUnsyncedWorkLocation: " + allWorkLocation);
        return allWorkLocation;
    }

    public JSONArray getUnsyncedAttendance() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause = TableContracts.AttendanceTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = TableContracts.AttendanceTable.COLUMN_ID + " ASC";

        JSONArray allAttendance = new JSONArray();
        c = db.query(
                TableContracts.AttendanceTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Form fc = new Form();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedAttendance: " + c.getCount());
            Attendance attendance = new Attendance();
            allAttendance.put(attendance.Hydrate(c).toJSONObject());
        }

        c.close();

        Log.d(TAG, "getUnsyncedAttendance: " + allAttendance.toString().length());
        Log.d(TAG, "getUnsyncedAttendance: " + allAttendance);
        return allAttendance;
    }

    public JSONArray getUnsyncedVaccines() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        /*whereClause = WorkLocationTable.COLUMN_SYNCED + " = '' AND " +
                WorkLocationTable.COLUMN_ISTATUS + "!= ''";*/
        whereClause = TableContracts.VaccinesTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = TableContracts.VaccinesTable.COLUMN_ID + " ASC";

        JSONArray vaccines = new JSONArray();
        c = db.query(
                TableContracts.VaccinesTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedVaccines: " + c.getCount());
            Vaccines vacc = new Vaccines();
            vaccines.put(vacc.Hydrate(c).toJSONObject());


        }

        c.close();

        Log.d(TAG, "getUnsyncedVaccines: " + vaccines.toString().length());
        Log.d(TAG, "getUnsyncedVaccines: " + vaccines);
        return vaccines;
    }

    public JSONArray getUnsyncedDueVaccines() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause = TableContracts.VaccinesDueTable.COLUMN_SYNCED + " = '' ";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = TableContracts.VaccinesDueTable.COLUMN_ID + " ASC";

        JSONArray vaccinesDue = new JSONArray();
        c = db.query(
                TableContracts.VaccinesDueTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedVaccines: " + c.getCount());
            VaccDueDates vaccDueDates = new VaccDueDates();
            vaccinesDue.put(vaccDueDates.Hydrate(c).toJSONObject());
        }
        c.close();
        Log.d(TAG, "getUnsyncedDueVaccines: " + vaccinesDue.toString().length());
        Log.d(TAG, "getUnsyncedDueVaccines: " + vaccinesDue);
        return vaccinesDue;
    }

    public JSONArray getUnsyncedEntryLog() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = EntryLogTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = EntryLogTable.COLUMN_ID + " ASC";

        JSONArray all = new JSONArray();
        c = db.query(
                EntryLogTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedEntryLog: " + c.getCount());
            EntryLog entryLog = new EntryLog();
            all.put(entryLog.Hydrate(c).toJSONObject());
        }
        Log.d(TAG, "getUnsyncedEntryLog: " + all.toString().length());
        Log.d(TAG, "getUnsyncedEntryLog: " + all);
        return all;
    }

    public JSONArray getUnsyncedFormCR() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FormCRTable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FormCRTable.COLUMN_ID + " ASC";

        JSONArray allCR = new JSONArray();
        c = db.query(
                FormCRTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Form fc = new Form();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedFormCR: " + c.getCount());
            FormCR cr = new FormCR();
            allCR.put(cr.Hydrate(c).toJSONObject());

        }
        if (c != null) {
            c.close();
        }
        Log.d(TAG, "getUnsyncedFormCR: " + allCR.toString().length());
        Log.d(TAG, "getUnsyncedFormCR: " + allCR);
        return allCR;
    }

    public JSONArray getUnsyncedFormWR() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FormWRTable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FormWRTable.COLUMN_ID + " ASC";

        JSONArray allWR = new JSONArray();
        c = db.query(
                FormWRTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*Form fc = new Form();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedFormWR: " + c.getCount());
            FormWR wr = new FormWR();
            allWR.put(wr.Hydrate(c).toJSONObject());
        }
        if (c != null) {
            c.close();
        }
        Log.d(TAG, "getUnsyncedFormWR: " + allWR.toString().length());
        Log.d(TAG, "getUnsyncedFormWR: " + allWR);
        return allWR;
    }

    // Sync UCs
    public int syncuclist(JSONArray ucList) throws JSONException {
        if (isSyncUCs) return ucList.length();
        isSyncUCs = true;
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TableUCs.TABLE_NAME, null, null);
        int insertCount = 0;

        for (int i = 0; i < ucList.length(); i++) {
            JSONObject jsonObjectUc = ucList.getJSONObject(i);
            UCs uc = new UCs();
            uc.sync(jsonObjectUc);
            ContentValues values = new ContentValues();

            values.put(TableUCs.COLUMN_UC_CODE, uc.getUcCode());
            values.put(TableUCs.COLUMN_UC_NAME, uc.getUcName());

            long rowID = db.insert(TableUCs.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        isSyncUCs = false;
        return insertCount;
    }

    // Sync Villages
    public int syncVillages(JSONArray villagesList) throws JSONException {
        Log.e("HERE", "syncVillages");
        if (isSyncVillages) return villagesList.length();
        isSyncVillages = true;
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TableVillages.TABLE_NAME, null, null);
        int insertCount = 0;

        for (int i = 0; i < villagesList.length(); i++) {
            JSONObject jsonObjectVillage = villagesList.getJSONObject(i);
            Villages villages = new Villages();
            villages.sync(jsonObjectVillage);
            ContentValues values = new ContentValues();

            values.put(TableVillages.COLUMN_UC_CODE, villages.getUcCode());
            values.put(TableVillages.COLUMN_VILLAGE_CODE, villages.getVillageCode());
            values.put(TableVillages.COLUMN_VILLAGE_NAME, villages.getVillageName());

            long rowID = db.insertOrThrow(TableVillages.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        isSyncVillages = false;
        return insertCount;
    }

    // Sync HF
    public int synchf_list(JSONArray healthfacilities) throws JSONException {
        Log.e("HERE", "synchf_list");
        if (isSyncHF) return healthfacilities.length();
        isSyncHF = true;
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TableHealthFacilities.TABLE_NAME, null, null);
        int insertCount = 0;

        for (int i = 0; i < healthfacilities.length(); i++) {
            JSONObject json = healthfacilities.getJSONObject(i);
            HealthFacilities facilities = new HealthFacilities();
            facilities.sync(json);
            ContentValues values = new ContentValues();

            values.put(TableHealthFacilities.COLUMN_HF_CODE, facilities.getHfCode());
            values.put(TableHealthFacilities.COLUMN_HF_NAME, facilities.getHfName());
            values.put(TableHealthFacilities.COLUMN_UC_CODE, facilities.getUc_code());

            long rowID = db.insert(TableHealthFacilities.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }

        isSyncHF = false;
        return insertCount;
    }

    // Sync VACCINESDATA
    public int syncvaccine_schedule(JSONArray vaccineSchedule) throws JSONException {
        Log.e("HERE", "syncvaccine_schedule");
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TableVaccineSchedule.TABLE_NAME, null, null);
        int insertCount = 0;

        for (int i = 0; i < vaccineSchedule.length(); i++) {
            JSONObject json = vaccineSchedule.getJSONObject(i);
            VaccinesSchedule vaccineschedule = new VaccinesSchedule();
            vaccineschedule.sync(json);
            ContentValues values = new ContentValues();

            values.put(TableVaccineSchedule.COLUMN_VNAME, vaccineschedule.getVname());
            values.put(TableVaccineSchedule.COLUMN_VGROUP, vaccineschedule.getVgroup());
            values.put(TableVaccineSchedule.COLUMN_BYDOB, vaccineschedule.getBydob());
            values.put(TableVaccineSchedule.COLUMN_BYPRVDOZE, vaccineschedule.getByprvdoze());
            values.put(TableVaccineSchedule.COLUMN_COMMENTS, vaccineschedule.getComments());

            long rowID = db.insert(TableVaccineSchedule.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }

        return insertCount;
    }

    // Delete Table
    public void deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(tableName, null, null);
    }

    // Sync VACCINESDATA
    public int syncvaccinesFollowUp(JSONArray vaccinesdata) throws JSONException {
        Log.e("HERE", "syncvaccinesFollowUp");
        if (isSyncVaccDB) return vaccinesdata.length();
        isSyncVaccDB = true;
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TableVaccinesData.TABLE_NAME, null, null);
        /*int deleted = db.delete(TableVaccinesData.TABLE_NAME, null, null);
        Log.e("DELETED_vaccFollowUp", Integer.toString(deleted));*/

        int insertCount = 0;

        for (int i = 0; i < vaccinesdata.length(); i++) {
            JSONObject json = vaccinesdata.getJSONObject(i);
            VaccinesData vaccinesData = new VaccinesData();
            vaccinesData.sync(json);
            ContentValues values = new ContentValues();

            values.put(TableVaccinesData.COLUMN_UC_CODE, vaccinesData.getUcCode());
            values.put(TableVaccinesData.COLUMN_AID, vaccinesData.getAID());
            values.put(TableVaccinesData.COLUMN_UID, vaccinesData.getUID());
            //values.put(TableVaccinesData.COLUMN_UUID, vaccinesData.getUID());
//            values.put(TableVaccinesData.COLUMN_VILLAGE_CODE, vaccinesData.getVillageCode());
            //values.put(TableVaccinesData.COLUMN_FACILITY_CODE, vaccinesData.getFacilityCode());
            //values.put(TableVaccinesData.COLUMN_VILLAGE_NAME, vaccinesData.getVillageName());
            values.put(TableVaccinesData.COLUMN_USERNAME, vaccinesData.getUsername());
            values.put(TableVaccinesData.COLUMN_VB02, vaccinesData.getVBO2());
            values.put(TableVaccinesData.COLUMN_VB03, vaccinesData.getVBO3());
            values.put(TableVaccinesData.COLUMN_VB04, vaccinesData.getVB04());
            values.put(TableVaccinesData.COLUMN_VB07, vaccinesData.getVB07());
            values.put(TableVaccinesData.COLUMN_VB04A, vaccinesData.getVB04A());
            values.put(TableVaccinesData.COLUMN_VB05A, vaccinesData.getVBO5A());
            values.put(TableVaccinesData.COLUMN_VB05D, vaccinesData.getVBO5D());
            values.put(TableVaccinesData.COLUMN_VB05M, vaccinesData.getVBO5M());
            values.put(TableVaccinesData.COLUMN_VB05Y, vaccinesData.getVBO5Y());
            values.put(TableVaccinesData.COLUMN_DOB, vaccinesData.getDob());
            values.put(TableVaccinesData.COLUMN_BCG, vaccinesData.getBcg());
            values.put(TableVaccinesData.COLUMN_OPV0, vaccinesData.getOpv0());
            values.put(TableVaccinesData.COLUMN_OPV1, vaccinesData.getOpv1());
            values.put(TableVaccinesData.COLUMN_OPV2, vaccinesData.getOpv2());
            values.put(TableVaccinesData.COLUMN_OPV3, vaccinesData.getOpv3());
            values.put(TableVaccinesData.COLUMN_HEP_B, vaccinesData.getHepB());
            values.put(TableVaccinesData.COLUMN_PENTA1, vaccinesData.getPenta1());
            values.put(TableVaccinesData.COLUMN_PENTA2, vaccinesData.getPenta2());
            values.put(TableVaccinesData.COLUMN_PENTA3, vaccinesData.getPenta3());
            values.put(TableVaccinesData.COLUMN_PCV1, vaccinesData.getPcv1());
            values.put(TableVaccinesData.COLUMN_PCV2, vaccinesData.getPcv2());
            values.put(TableVaccinesData.COLUMN_PCV3, vaccinesData.getPcv3());
            values.put(TableVaccinesData.COLUMN_IPV1, vaccinesData.getIpv1());
            values.put(TableVaccinesData.COLUMN_IPV2, vaccinesData.getIpv2());
            values.put(TableVaccinesData.COLUMN_ROTA1, vaccinesData.getRota1());
            values.put(TableVaccinesData.COLUMN_ROTA2, vaccinesData.getRota2());
            values.put(TableVaccinesData.COLUMN_MEASLES1, vaccinesData.getMeasles1());
            values.put(TableVaccinesData.COLUMN_MEASLES2, vaccinesData.getMeasles2());
            values.put(TableVaccinesData.COLUMN_TYPHOID, vaccinesData.getTyphoid());
            values.put(TableVaccinesData.COLUMN_DPT, vaccinesData.getDpt());

            long rowID = db.insert(TableVaccinesData.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        isSyncVaccDB = false;
        return insertCount;
    }

    public int syncwomenfollowup(JSONArray data) throws JSONException {
        Log.e("HERE", "syncwomenfollowup");
        if (isSyncWomenDB) return data.length();
        isSyncWomenDB = true;
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        int deleted = db.delete(TableWomenFollowUP.TABLE_NAME, null, null);
        Log.e("DELETED_womenfollowup", Integer.toString(deleted));

        int insertCount = 0;

        for (int i = 0; i < data.length(); i++) {

            JSONObject json = data.getJSONObject(i);

            WomenFollowUP womenData = new WomenFollowUP();
            womenData.sync(json);
            ContentValues values = new ContentValues();

            values.put(TableWomenFollowUP.COLUMN_UC_CODE, womenData.getUcCode());
            values.put(TableWomenFollowUP.COLUMN_AID, womenData.getAID());
            values.put(TableWomenFollowUP.COLUMN_UID, womenData.getUID());
            values.put(TableWomenFollowUP.COLUMN_VILLAGE_CODE, womenData.getVillageCode());
            values.put(TableWomenFollowUP.COLUMN_FACILITY_CODE, womenData.getFacilityCode());
            values.put(TableWomenFollowUP.COLUMN_VB02, womenData.getVBO2());
            values.put(TableWomenFollowUP.COLUMN_VB03, womenData.getVBO3());
            values.put(TableWomenFollowUP.COLUMN_VB04, womenData.getVB04());
            values.put(TableWomenFollowUP.COLUMN_VB04A, womenData.getVB04A());
            values.put(TableWomenFollowUP.COLUMN_VB05A, womenData.getVBO5A());
            values.put(TableWomenFollowUP.COLUMN_AGE, womenData.getAge());
            values.put(TableWomenFollowUP.COLUMN_TT1, womenData.getTt1());
            values.put(TableWomenFollowUP.COLUMN_TT2, womenData.getTt2());
            values.put(TableWomenFollowUP.COLUMN_TT3, womenData.getTt3());
            values.put(TableWomenFollowUP.COLUMN_TT4, womenData.getTt4());
            values.put(TableWomenFollowUP.COLUMN_TT5, womenData.getTt5());

            long rowID = db.insert(TableWomenFollowUP.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        isSyncWomenDB = false;
        return insertCount;
    }

    //update SyncedTables
    public void updateSyncedFormVA(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsVATable.COLUMN_SYNCED, true);
        values.put(FormsVATable.COLUMN_SYNC_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsVATable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsVATable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedFormVB(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsVBTable.COLUMN_SYNCED, true);
        values.put(FormsVBTable.COLUMN_SYNC_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsVBTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsVBTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedAttendance(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(TableContracts.WorkLocationTable.COLUMN_SYNCED, true);
        values.put(TableContracts.WorkLocationTable.COLUMN_SYNC_DATE, new Date().toString());

// Which row to update, based on the title
        String where = TableContracts.WorkLocationTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                WorkLocationTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedWorkLocation(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(TableContracts.WorkLocationTable.COLUMN_SYNCED, true);
        values.put(TableContracts.WorkLocationTable.COLUMN_SYNC_DATE, new Date().toString());

// Which row to update, based on the title
        String where = TableContracts.WorkLocationTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                WorkLocationTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedVaccines(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(TableContracts.VaccinesTable.COLUMN_SYNCED, true);
        values.put(TableContracts.VaccinesTable.COLUMN_SYNC_DATE, new Date().toString());

// Which row to update, based on the title
        String where = TableContracts.VaccinesTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                VaccinesTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedVaccDueDates(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(TableContracts.VaccinesDueTable.COLUMN_SYNCED, true);
        values.put(TableContracts.VaccinesDueTable.COLUMN_SYNC_DATE, new Date().toString());

// Which row to update, based on the title
        String where = TableContracts.VaccinesDueTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                VaccinesDueTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedEntryLog(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(EntryLogTable.COLUMN_SYNCED, true);
        values.put(EntryLogTable.COLUMN_SYNC_DATE, new Date().toString());
        String where = EntryLogTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                EntryLogTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedFormCR(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormCRTable.COLUMN_SYNCED, true);
        values.put(FormCRTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormCRTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormCRTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public void updateSyncedFormWR(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormWRTable.COLUMN_SYNCED, true);
        values.put(FormWRTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormWRTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormWRTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


    public int updatePassword(String hashedPassword) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(UsersTable.COLUMN_PASSWORD, hashedPassword);
        values.put(UsersTable.COLUMN_ISNEW_USER, "");

        String selection = UsersTable.COLUMN_USERNAME + " =? ";
        String[] selectionArgs = {MainApp.user.getUserName()};

        return db.update(UsersTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }




/*    public void updateSyncedSamp(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(SamplesTable.COLUMN_SYNCED, true);
        values.put(SamplesTable.COLUMN_SYNCED_DATE, new Date().toString());

        String where = SamplesTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                SamplesTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }*/


    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase(DATABASE_PASSWORD);
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public List<FormVB> getAllChilds() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = FormsVBTable.COLUMN_ID + " ASC";
        List<FormVB> allForm = new ArrayList<>();

        c = db.query(
                FormsVBTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            try {
                FormVB formVB = new FormVB().Hydrate(c);
                if (formVB.getVb03().equals("2"))
                    allForm.add(formVB);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return allForm;
    }


    public List<VaccinesData> getAllFollowupChilds() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = TableVaccinesData.COLUMN_ID + " ASC";
        List<VaccinesData> allForm = new ArrayList<>();

        c = db.query(
                TableVaccinesData.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            VaccinesData vd = new VaccinesData().hydrate(c);
            if (vd.getVBO3().equals("2"))
                allForm.add(vd);
        }
        c.close();
        return allForm;
    }


    public List<WomenFollowUP> getAllFollowupWomen() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = TableWomenFollowUP.COLUMN_ID + " ASC";
        List<WomenFollowUP> allForm = new ArrayList<>();

        c = db.query(
                TableWomenFollowUP.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            WomenFollowUP vd = new WomenFollowUP().hydrate(c);
            if (vd.getVBO3().equals("1"))
                allForm.add(vd);
        }
        c.close();
        return allForm;
    }


    public List<FormVB> getAllWomens() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = FormsVBTable.COLUMN_ID + " ASC";
        List<FormVB> allForm = new ArrayList<>();

        c = db.query(
                FormsVBTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            try {
                FormVB formVB = new FormVB().Hydrate(c);
                if (formVB.getVb03().equals("1"))
                    allForm.add(formVB);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return allForm;
    }


    public List<FormVB> getAllChildsByCardNo(String cardNo) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = FormsVBTable.COLUMN_CARD_NO + " = ? ";
        String[] whereArgs = new String[]{cardNo};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsVBTable.COLUMN_ID + " ASC";

        List<FormVB> allForm = new ArrayList<>();

        c = db.query(
                FormsVBTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            try {
                FormVB formVB = new FormVB().Hydrate(c);
                if (formVB.getVb03().equals("2"))
                    allForm.add(formVB);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return allForm;
    }

    public List<VaccinesData> getFollowupChildsByCardNo(String cardNo) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = TableVaccinesData.COLUMN_VB02 + " = ? ";
        String[] whereArgs = new String[]{cardNo};
        String groupBy = null;
        String having = null;

        String orderBy =
                TableVaccinesData.COLUMN_ID + " ASC";

        List<VaccinesData> allForm = new ArrayList<>();

        c = db.query(
                TableVaccinesData.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            VaccinesData vd = new VaccinesData().hydrate(c);
            if (vd.getVBO3().equals("2"))
                allForm.add(vd);
        }
        c.close();
        return allForm;
    }


    public List<VaccinesData> getFollowupWomenByCardNo(String cardNo) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = TableVaccinesData.COLUMN_VB02 + " = ? ";
        String[] whereArgs = new String[]{cardNo};
        String groupBy = null;
        String having = null;

        String orderBy =
                TableVaccinesData.COLUMN_ID + " ASC";

        List<VaccinesData> allForm = new ArrayList<>();

        c = db.query(
                TableVaccinesData.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            VaccinesData vd = new VaccinesData().hydrate(c);
            if (vd.getVBO3().equals("1"))
                allForm.add(vd);
        }
        c.close();
        return allForm;
    }


    public List<FormVB> getAllWomensByCardNo(String cardNo) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = FormsVBTable.COLUMN_CARD_NO + " = ? ";
        String[] whereArgs = new String[]{cardNo};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsVBTable.COLUMN_ID + " ASC";

        List<FormVB> allForm = new ArrayList<>();

        c = db.query(
                FormsVBTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            try {
                FormVB formVB = new FormVB().Hydrate(c);
                if (formVB.getVb03().equals("1"))
                    allForm.add(formVB);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return allForm;
    }


    public List<FormVB> getAllChildsByName(String memberName) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = FormsVBTable.COLUMN_VB04A_NAME + " = ? ";
        String[] whereArgs = new String[]{memberName};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsVBTable.COLUMN_ID + " ASC";

        List<FormVB> allForm = new ArrayList<>();

        c = db.query(
                FormsVBTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            try {
                FormVB formVB = new FormVB().Hydrate(c);
                if (formVB.getVb03().equals("2"))
                    allForm.add(formVB);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return allForm;
    }


    public List<FormVB> getAllWomensByName(String memberName) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = FormsVBTable.COLUMN_VB04A_NAME + " = ? ";
        String[] whereArgs = new String[]{memberName};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsVBTable.COLUMN_ID + " ASC";

        List<FormVB> allForm = new ArrayList<>();

        c = db.query(
                FormsVBTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            try {
                FormVB formVB = new FormVB().Hydrate(c);
                if (formVB.getVb03().equals("1"))
                    allForm.add(formVB);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return allForm;
    }


    public List<VaccinesData> getFollowupChildsByName(String memberName) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = TableVaccinesData.COLUMN_VB04A + " = ? ";
        String[] whereArgs = new String[]{memberName};
        String groupBy = null;
        String having = null;

        String orderBy =
                TableVaccinesData.COLUMN_ID + " ASC";

        List<VaccinesData> allForm = new ArrayList<>();

        c = db.query(
                TableVaccinesData.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            VaccinesData vd = new VaccinesData().hydrate(c);
            if (vd.getVBO3().equals("2"))
                allForm.add(vd);
        }
        c.close();
        return allForm;
    }

    public List<VaccinesSchedule> getVaccineDaysByVgroup(String vGroup) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = TableVaccineSchedule.COLUMN_VGROUP + " = ? ";
        String[] whereArgs = new String[]{vGroup};
        String groupBy = null;
        String having = null;

        String orderBy =
                TableVaccineSchedule.COLUMN_ID + " ASC";

        List<VaccinesSchedule> allForm = new ArrayList<>();

        c = db.query(
                TableVaccineSchedule.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            VaccinesSchedule vd = new VaccinesSchedule().hydrate(c);
            allForm.add(vd);
        }
        c.close();
        return allForm;
    }


    public List<VaccinesData> getFollowupWomensByName(String memberName) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;

        String whereClause = TableVaccinesData.COLUMN_VB04A + " = ? ";
        String[] whereArgs = new String[]{memberName};
        String groupBy = null;
        String having = null;

        String orderBy =
                TableVaccinesData.COLUMN_ID + " ASC";

        List<VaccinesData> allForm = new ArrayList<>();

        c = db.query(
                TableVaccinesData.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            VaccinesData vd = new VaccinesData().hydrate(c);
            if (vd.getVBO3().equals("1"))
                allForm.add(vd);
        }
        c.close();
        return allForm;
    }

    public FormVB getSelectedMembers(String uid) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;
        String whereClause = FormsVBTable.COLUMN_UID + " = ? ";
        String[] whereArgs = new String[]{uid};
        String groupBy = null;
        String having = null;
        String orderBy = FormsVBTable.COLUMN_ID + " ASC";
        FormVB vb = new FormVB();
        c = db.query(
                FormsVBTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) vb = new FormVB().Hydrate(c);
        c.close();
        return vb;
    }


    public VaccinesData getFollowupSelectedMembers(String uid, String cardNo) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;
        String whereClause = TableVaccinesData.COLUMN_UID + " = ?  AND " +
                TableVaccinesData.COLUMN_VB02 + " = ? ";
        String[] whereArgs = new String[]{uid, cardNo};
        String groupBy = null;
        String having = null;
        String orderBy = TableVaccinesData.COLUMN_ID + " ASC";
        VaccinesData vb = new VaccinesData();
        c = db.query(
                TableVaccinesData.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) vb = new VaccinesData().hydrate(c);
        c.close();
        return vb;
    }


    public List<Vaccines> getVaccinatedMembersBYUID() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = VaccinesTable.COLUMN_UUID + "=? ";
        String[] whereArgs = {formVB.getUid()};
        String groupBy = null;
        String having = null;
        String orderBy = VaccinesTable.COLUMN_ID + " ASC";

        List<Vaccines> vaccinesByUID = new ArrayList<>();
        c = db.query(
                VaccinesTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            vaccinesByUID.add(new Vaccines().Hydrate(c));
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }
        c.close();
        return vaccinesByUID;
    }

    public List<VaccinesData> getSyncedVaccinatedChildBYCardNo(String cardNo, String username) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = TableVaccinesData.COLUMN_VB02 + " = ?  AND " +
                UsersTable.COLUMN_USERNAME + " = ? ";
        String[] whereArgs = {cardNo, username};
        String groupBy = null;
        String having = null;
        String orderBy = TableVaccinesData.COLUMN_ID + " ASC";

        List<VaccinesData> vaccinesByUID = new ArrayList<>();
        c = db.query(
                TableVaccinesData.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            vaccinesByUID.add(new VaccinesData().hydrate(c));
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }
        c.close();
        return vaccinesByUID;
    }


    public List<VaccinesData> getSyncedVaccinatedMembersBYUID(String uid, String cardNo) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = TableVaccinesData.COLUMN_UID + "=? AND " + TableVaccinesData.COLUMN_VB02 + "=?";
        String[] whereArgs = {uid, cardNo};
        String groupBy = null;
        String having = null;
        String orderBy = TableVaccinesData.COLUMN_ID + " ASC";

        List<VaccinesData> vaccinesByUID = new ArrayList<>();
        c = db.query(
                TableVaccinesData.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            vaccinesByUID.add(new VaccinesData().hydrate(c));
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }
        c.close();
        return vaccinesByUID;
    }

    public VaccDueDates getDueVaccinesBYAntigen(String cardNo, String childName, String fatherName, String vaccineName, String doseNumber) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = VaccinesDueTable.COLUMN_VB02 + "=? AND " + VaccinesDueTable.COLUMN_VB04A + "=? AND "
                + VaccinesDueTable.COLUMN_VB04 + "=? AND "
                + VaccinesDueTable.COLUMN_VB08C_CODE + "=? AND " + VaccinesDueTable.COLUMN_VB08C_ANTIGEN + "=?";
        String[] whereArgs = {cardNo, childName, fatherName, vaccineName, doseNumber};
        String groupBy = null;
        String having = null;
        String orderBy = VaccinesDueTable.COLUMN_ID + " ASC";

        VaccDueDates vaccinesByUID = new VaccDueDates();
        c = db.query(
                VaccinesDueTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        if (c != null) {
            while (c.moveToNext()) {
                vaccinesByUID = new VaccDueDates().Hydrate(c);
            }
            if (!c.isClosed()) {
                c.close();
            }
        }
        return vaccinesByUID;
    }


    public List<WomenFollowUP> getSyncedVaccinatedWomenBYUID(String uid, String cardNo) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = TableWomenFollowUP.COLUMN_UID + "=? AND " + TableWomenFollowUP.COLUMN_VB02 + " = ? ";
        String[] whereArgs = {uid, cardNo};
        String groupBy = null;
        String having = null;
        String orderBy = TableWomenFollowUP.COLUMN_ID + " ASC";

        List<WomenFollowUP> vaccinesByUID = new ArrayList<>();
        c = db.query(
                TableWomenFollowUP.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            vaccinesByUID.add(new WomenFollowUP().hydrate(c));
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }
        c.close();
        return vaccinesByUID;
    }

    public WomenFollowUP getFollowupSelectedWoman(String uid, String cardNo) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c;
        String[] columns = null;
        String whereClause = TableWomenFollowUP.COLUMN_UID + " = ?  AND " +
                TableWomenFollowUP.COLUMN_VB02 + " = ? ";
        String[] whereArgs = new String[]{uid, cardNo};
        String groupBy = null;
        String having = null;
        String orderBy = TableWomenFollowUP.COLUMN_ID + " ASC";
        WomenFollowUP vb = new WomenFollowUP();
        c = db.query(
                TableWomenFollowUP.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) vb = new WomenFollowUP().hydrate(c);
        c.close();
        return vb;
    }


    public FormVA getFormByuid(String id) throws JSONException {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;

        Boolean distinct = false;
        String tableName = FormsVATable.TABLE_NAME;
        String[] columns = null;
        String whereClause = FormsVATable.COLUMN_ID + "= ? ";
        String[] whereArgs = {id};
        String groupBy = null;
        String having = null;
        String orderBy = FormsVATable.COLUMN_SYSDATE + " ASC";
        String limitRows = "1";

        c = db.query(
                distinct,       // Distinct values
                tableName,      // The table to query
                columns,        // The columns to return
                whereClause,    // The columns for the WHERE clause
                whereArgs,      // The values for the WHERE clause
                groupBy,        // don't group the rows
                having,         // don't filter by row groups
                orderBy,
                limitRows
        );

        FormVA formVA = new FormVA();
        while (c.moveToNext()) {
            formVA = (new FormVA().Hydrate(c));
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }
        return formVA;

    }




/*    public Collection<UCs> getAllUCs() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {TableUCs.COLUMN_UC_CODE, TableUCs.COLUMN_UC_NAME};

        String orderBy = TableUCs.COLUMN_UC_NAME + " ASC";

        Collection<UCs> allUCs = new ArrayList<>();
        c = db.query(
                true,
                TableUCs.TABLE_NAME,  // The table to query
                columns,
                null,
                null,
                null,
                null,
                orderBy,
                "5000"

                // The sort order
        );
        while (c.moveToNext()) {
            allUCs.add(new UCs().hydrate(c));
        }
        return allUCs;
    }*/

    public ArrayList<UCs> getAllUCsByUCcode(String ucCode) {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = TableUCs.COLUMN_UC_CODE + " = ? ";
        String[] whereArgs = {ucCode};
        String groupBy = null;
        String having = null;

        String orderBy = TableUCs.COLUMN_UC_NAME + " ASC";
        ArrayList<UCs> uc = new ArrayList<>();

        c = db.query(
                TableUCs.TABLE_NAME,  // The table to query
                columns,                   // The columns to return8
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            uc.add(new UCs().hydrate(c));
        }
        return uc;
    }


    public Collection<HealthFacilities> getHealthFacilityByUC(String ucCode) {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = TableHealthFacilities.COLUMN_UC_CODE + " = ? ";

        String[] whereArgs = {ucCode};

        String groupBy = null;
        String having = null;

        String orderBy = TableHealthFacilities.COLUMN_ID + " ASC";

        List<HealthFacilities> healthFacilities = new ArrayList<>();

        c = db.query(
                TableHealthFacilities.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            healthFacilities.add(new HealthFacilities().hydrate(c));
        }
        return healthFacilities;
    }


    public ArrayList<Villages> getAllVillagesByUC(String ucCode) {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = TableVillages.COLUMN_UC_CODE + " = ? ";
        String[] whereArgs = {ucCode};
        String groupBy = null;
        String having = null;

        String orderBy = TableVillages.COLUMN_VILLAGE_NAME + " ASC";
        ArrayList<Villages> vg = new ArrayList<>();

        c = db.query(
                TableVillages.TABLE_NAME,  // The table to query
                columns,                   // The columns to return8
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            vg.add(new Villages().hydrate(c));
        }
        return vg;
    }


    public WorkLocation getCurrentWorkLocation(String uid) throws JSONException {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = WorkLocationTable.COLUMN_UID + " = ? ";

        String[] whereArgs = {uid};

        String orderBy = null;

        WorkLocation workLocation = null;
        c = db.query(
                WorkLocationTable.TABLE_NAME,  // The table to query
                columns,
                whereClause,               // The columns for the WHERE clause
                whereArgs,
                null,
                null,
                orderBy

                // The sort order
        );

        if (c != null) {
            c.moveToFirst();
            workLocation = new WorkLocation().Hydrate(c);
        }
        return workLocation;

    }

    public Attendance getCurrentAttendance(String uid) throws JSONException {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = AttendanceTable.COLUMN_UID + " = ? ";

        String[] whereArgs = {uid};

        String orderBy = null;

        Attendance attendance = null;
        c = db.query(
                AttendanceTable.TABLE_NAME,  // The table to query
                columns,
                whereClause,               // The columns for the WHERE clause
                whereArgs,
                null,
                null,
                orderBy

                // The sort order
        );

        if (c != null) {
            c.moveToFirst();
            attendance = new Attendance().Hydrate(c);
        }
        return attendance;

    }

    public FormVA getCurrentFormVA(String uid) throws JSONException {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = FormsVATable.COLUMN_UID + " = ? ";

        String[] whereArgs = {uid};

        String orderBy = null;

        FormVA formVA = null;
        c = db.query(
                FormsVATable.TABLE_NAME,  // The table to query
                columns,
                whereClause,               // The columns for the WHERE clause
                whereArgs,
                null,
                null,
                orderBy

                // The sort order
        );

        if (c != null) {
            c.moveToFirst();
            formVA = new FormVA().Hydrate(c);
        }
        return formVA;

    }

    /* FOR SUMMARY */
    public List<String> getVaccinesByAntigenCodeAndDate(String antigenCode, String date) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {VaccinesTable.COLUMN_VB08C_CODE,
                VaccinesTable.COLUMN_VB08C_ANTIGEN};
        String whereClause = VaccinesTable.COLUMN_VB08C_CODE + " = ? AND " + VaccinesTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{antigenCode, "%" + date + "%"};
        String groupBy = null;
        String having = null;
        String orderBy = VaccinesTable.COLUMN_ID + " ASC";
        List<String> vaccines = new ArrayList<>();
        c = db.query(
                VaccinesTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            vaccines.add(c.getString(c.getColumnIndexOrThrow(VaccinesTable.COLUMN_VB08C_CODE))
                    + c.getString(c.getColumnIndexOrThrow(VaccinesTable.COLUMN_VB08C_ANTIGEN)));
        }
        return vaccines;
    }

    public int getChildrenVaccinatedByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = VaccinesTable.COLUMN_VB08C_DATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + date + "%"};
        String groupBy = VaccinesTable.COLUMN_VB02 + "," + VaccinesTable.COLUMN_VB04;
        String having = null;
        String orderBy = VaccinesTable.COLUMN_ID + " ASC";
        int count = 0;
        c = db.query(
                VaccinesTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        if (null != c)
            if (c.getCount() > 0) {
                c.moveToFirst();
                count = c.getCount();
            }
        return count;
    }
}