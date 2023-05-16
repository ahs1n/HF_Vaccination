package edu.aku.dmu.hf_vaccination.database

import edu.aku.dmu.hf_vaccination.contracts.TableContracts.*
import edu.aku.dmu.hf_vaccination.core.MainApp.PROJECT_NAME

object CreateTable {

    const val DATABASE_NAME = "$PROJECT_NAME.db"
    const val DATABASE_COPY = "${PROJECT_NAME}_copy.db"
    const val DATABASE_VERSION = 4

    const val SQL_CREATE_FORMSVA = ("CREATE TABLE "
            + FormsVATable.TABLE_NAME + "("
            + FormsVATable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormsVATable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormsVATable.COLUMN_UID + " TEXT,"
            + FormsVATable.COLUMN_SNO + " TEXT,"
            + FormsVATable.COLUMN_USERNAME + " TEXT,"
            + FormsVATable.COLUMN_SYSDATE + " TEXT,"
            + FormsVATable.COLUMN_ISTATUS + " TEXT,"
            + FormsVATable.COLUMN_DEVICEID + " TEXT,"
            + FormsVATable.COLUMN_DEVICETAGID + " TEXT,"
            + FormsVATable.COLUMN_SYNCED + " TEXT,"
            + FormsVATable.COLUMN_SYNC_DATE + " TEXT,"
            + FormsVATable.COLUMN_APPVERSION + " TEXT,"
            + FormsVATable.COLUMN_GPSLAT + " TEXT,"
            + FormsVATable.COLUMN_GPSLNG + " TEXT,"
            + FormsVATable.COLUMN_GPSDATE + " TEXT,"
            + FormsVATable.COLUMN_GPSACC + " TEXT,"
            + FormsVATable.COLUMN_VA + " TEXT"
            + " );"
            )

    const val SQL_CREATE_FORMSVB = ("CREATE TABLE "
            + FormsVBTable.TABLE_NAME + "("
            + FormsVBTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormsVBTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormsVBTable.COLUMN_UID + " TEXT,"
            + FormsVBTable.COLUMN_WID + " TEXT,"
            + FormsVBTable.COLUMN_AID + " TEXT,"
            + FormsVBTable.COLUMN_UUID + " TEXT,"
            + FormsVBTable.COLUMN_SNO + " TEXT,"
            + FormsVBTable.COLUMN_USERNAME + " TEXT,"
            + FormsVBTable.COLUMN_UC_CODE + " TEXT,"
            + FormsVBTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + FormsVBTable.COLUMN_FACILITY_CODE + " TEXT,"
            + FormsVBTable.COLUMN_AREA_NAME + " TEXT,"
            + FormsVBTable.COLUMN_SYSDATE + " TEXT,"
            + FormsVBTable.COLUMN_ISTATUS + " TEXT,"
            + FormsVBTable.COLUMN_DEVICEID + " TEXT,"
            + FormsVBTable.COLUMN_DEVICETAGID + " TEXT,"
            + FormsVBTable.COLUMN_CARD_NO + " TEXT,"
            + FormsVBTable.COLUMN_VB04A_NAME + " TEXT,"
            + FormsVBTable.COLUMN_SYNCED + " TEXT,"
            + FormsVBTable.COLUMN_SYNC_DATE + " TEXT,"
            + FormsVBTable.COLUMN_APPVERSION + " TEXT,"
            + FormsVBTable.COLUMN_GPSLAT + " TEXT,"
            + FormsVBTable.COLUMN_GPSLNG + " TEXT,"
            + FormsVBTable.COLUMN_GPSDATE + " TEXT,"
            + FormsVBTable.COLUMN_GPSACC + " TEXT,"
            + FormsVBTable.COLUMN_VB + " TEXT,"
            + FormsVBTable.COLUMN_VAC + " TEXT"
            + " );"
            )

    const val SQL_CREATE_VACCINE = ("CREATE TABLE "
            + VaccinesTable.TABLE_NAME + "("
            + VaccinesTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + VaccinesTable.COLUMN_PROJECT_NAME + " TEXT,"
            + VaccinesTable.COLUMN_UID + " TEXT,"
            + VaccinesTable.COLUMN_UUID + " TEXT,"
            + VaccinesTable.COLUMN_AID + " TEXT,"
            + VaccinesTable.COLUMN_SNO + " TEXT,"
            + VaccinesTable.COLUMN_USERNAME + " TEXT,"
            + VaccinesTable.COLUMN_UC_CODE + " TEXT,"
            + VaccinesTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + VaccinesTable.COLUMN_FACILITY_CODE + " TEXT,"
            + VaccinesTable.COLUMN_AREA_NAME + " TEXT,"
            + VaccinesTable.COLUMN_SYSDATE + " TEXT,"
            + VaccinesTable.COLUMN_ISTATUS + " TEXT,"
            + VaccinesTable.COLUMN_DEVICEID + " TEXT,"
            + VaccinesTable.COLUMN_DEVICETAGID + " TEXT,"
            + VaccinesTable.COLUMN_SYNCED + " TEXT,"
            + VaccinesTable.COLUMN_SYNC_DATE + " TEXT,"
            + VaccinesTable.COLUMN_APPVERSION + " TEXT,"
            + VaccinesTable.COLUMN_VB02 + " TEXT,"
            + VaccinesTable.COLUMN_VB04A + " TEXT,"
            + VaccinesTable.COLUMN_VB04 + " TEXT,"
            + VaccinesTable.COLUMN_VB07 + " TEXT,"
            + VaccinesTable.COLUMN_VB08C_CODE + " TEXT,"
            + VaccinesTable.COLUMN_VB08C_ANTIGEN + " TEXT,"
            + VaccinesTable.COLUMN_VB08C_DATE + " TEXT,"
            + VaccinesTable.COLUMN_FRONT_FILE_NAME + " TEXT,"
            + VaccinesTable.COLUMN_BACK_FILE_NAME + " TEXT,"
            + VaccinesTable.COLUMN_CHILD_FILE_NAME + " TEXT,"
            + VaccinesTable.COLUMN_VB08W_CODE + " TEXT,"
            + VaccinesTable.COLUMN_VB08W_ANTIGEN + " TEXT,"
            + VaccinesTable.COLUMN_VB08W_DATE + " TEXT,"
            + VaccinesTable.COLUMN_GPSLAT + " TEXT,"
            + VaccinesTable.COLUMN_GPSLNG + " TEXT,"
            + VaccinesTable.COLUMN_GPSACC + " TEXT,"
            + VaccinesTable.COLUMN_GPSDATE + " TEXT"
            + " );"
            )

    const val SQL_CREATE_DUE_VACCINE = ("CREATE TABLE "
            + VaccinesDueTable.TABLE_NAME + "("
            + VaccinesDueTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + VaccinesDueTable.COLUMN_PROJECT_NAME + " TEXT,"
            + VaccinesDueTable.COLUMN_UID + " TEXT,"
            + VaccinesDueTable.COLUMN_UUID + " TEXT,"
            + VaccinesDueTable.COLUMN_AID + " TEXT,"
            + VaccinesDueTable.COLUMN_SNO + " TEXT,"
            + VaccinesDueTable.COLUMN_USERNAME + " TEXT,"
            + VaccinesDueTable.COLUMN_UC_CODE + " TEXT,"
            + VaccinesDueTable.COLUMN_VILLAGE_CODE + " TEXT,"
            + VaccinesDueTable.COLUMN_FACILITY_CODE + " TEXT,"
            + VaccinesDueTable.COLUMN_AREA_NAME + " TEXT,"
            + VaccinesDueTable.COLUMN_SYSDATE + " TEXT,"
            + VaccinesDueTable.COLUMN_DEVICEID + " TEXT,"
            + VaccinesDueTable.COLUMN_SYNCED + " TEXT,"
            + VaccinesDueTable.COLUMN_SYNC_DATE + " TEXT,"
            + VaccinesDueTable.COLUMN_APPVERSION + " TEXT,"
            + VaccinesDueTable.COLUMN_VB02 + " TEXT,"
            + VaccinesDueTable.COLUMN_VB04A + " TEXT,"
            + VaccinesDueTable.COLUMN_VB04 + " TEXT,"
            + VaccinesDueTable.COLUMN_VB08C_CODE + " TEXT,"
            + VaccinesDueTable.COLUMN_VB08C_ANTIGEN + " TEXT,"
            + VaccinesDueTable.COLUMN_VB08C_DATE + " TEXT"
            + " );"
            )

    const val SQL_CREATE_WORK_LOCATION = ("CREATE TABLE "
            + WorkLocationTable.TABLE_NAME + "("
            + WorkLocationTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + WorkLocationTable.COLUMN_PROJECT_NAME + " TEXT,"
            + WorkLocationTable.COLUMN_UID + " TEXT,"
            + WorkLocationTable.COLUMN_AID + " TEXT,"
            + WorkLocationTable.COLUMN_USERNAME + " TEXT,"
            + WorkLocationTable.COLUMN_UC_CODE + " TEXT,"
            + WorkLocationTable.COLUMN_SYSDATE + " TEXT,"
            + WorkLocationTable.COLUMN_ISTATUS + " TEXT,"
            + WorkLocationTable.COLUMN_DEVICEID + " TEXT,"
            + WorkLocationTable.COLUMN_DEVICETAGID + " TEXT,"
            + WorkLocationTable.COLUMN_SYNCED + " TEXT,"
            + WorkLocationTable.COLUMN_SYNC_DATE + " TEXT,"
            + WorkLocationTable.COLUMN_APPVERSION + " TEXT,"
            + WorkLocationTable.COLUMN_GPSLAT + " TEXT,"
            + WorkLocationTable.COLUMN_GPSLNG + " TEXT,"
            + WorkLocationTable.COLUMN_GPSDATE + " TEXT,"
            + WorkLocationTable.COLUMN_GPSACC + " TEXT,"
            + WorkLocationTable.COLUMN_SWL + " TEXT"
            + " );"
            )

    const val SQL_CREATE_ATTENDANCE = ("CREATE TABLE "
            + AttendanceTable.TABLE_NAME + "("
            + AttendanceTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AttendanceTable.COLUMN_PROJECT_NAME + " TEXT,"
            + AttendanceTable.COLUMN_UID + " TEXT,"
            + AttendanceTable.COLUMN_USERNAME + " TEXT,"
            + AttendanceTable.COLUMN_UC_CODE + " TEXT,"
            + AttendanceTable.COLUMN_SYSDATE + " TEXT,"
            + AttendanceTable.COLUMN_ISTATUS + " TEXT,"
            + AttendanceTable.COLUMN_DEVICEID + " TEXT,"
            + AttendanceTable.COLUMN_DEVICETAGID + " TEXT,"
            + AttendanceTable.COLUMN_SYNCED + " TEXT,"
            + AttendanceTable.COLUMN_SYNC_DATE + " TEXT,"
            + AttendanceTable.COLUMN_APPVERSION + " TEXT,"
            + AttendanceTable.COLUMN_GPSLAT + " TEXT,"
            + AttendanceTable.COLUMN_GPSLNG + " TEXT,"
            + AttendanceTable.COLUMN_GPSDATE + " TEXT,"
            + AttendanceTable.COLUMN_GPSACC + " TEXT"
            + " );"
            )

    const val SQL_CREATE_VACCINESCHEDULE = ("CREATE TABLE "
            + TableVaccineSchedule.TABLE_NAME + "("
            + TableVaccineSchedule.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableVaccineSchedule.COLUMN_VNAME + " TEXT,"
            + TableVaccineSchedule.COLUMN_VGROUP + " TEXT,"
            + TableVaccineSchedule.COLUMN_BYDOB + " TEXT,"
            + TableVaccineSchedule.COLUMN_BYPRVDOZE + " TEXT,"
            + TableVaccineSchedule.COLUMN_COMMENTS + " TEXT"
            + " );"
            )

    const val SQL_CREATE_FORMCR = ("CREATE TABLE "
            + FormCRTable.TABLE_NAME + "("
            + FormCRTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormCRTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormCRTable.COLUMN_UID + " TEXT,"
            + FormCRTable.COLUMN_USERNAME + " TEXT,"
            + FormCRTable.COLUMN_SYSDATE + " TEXT,"
            + FormCRTable.COLUMN_START_TIME + " TEXT,"
            + FormCRTable.COLUMN_END_TIME + " TEXT,"
            + FormCRTable.COLUMN_ISTATUS + " TEXT,"
            + FormCRTable.COLUMN_DEVICEID + " TEXT,"
            + FormCRTable.COLUMN_DEVICETAGID + " TEXT,"
            + FormCRTable.COLUMN_SYNCED + " TEXT,"
            + FormCRTable.COLUMN_SYNCED_DATE + " TEXT,"
            + FormCRTable.COLUMN_APPVERSION + " TEXT,"
            + FormCRTable.COLUMN_CR + " TEXT"
            + " );"
            )

    const val SQL_CREATE_FORMWR = ("CREATE TABLE "
            + FormWRTable.TABLE_NAME + "("
            + FormWRTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormWRTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormWRTable.COLUMN_UID + " TEXT,"
            + FormWRTable.COLUMN_USERNAME + " TEXT,"
            + FormWRTable.COLUMN_SYSDATE + " TEXT,"
            + FormWRTable.COLUMN_START_TIME + " TEXT,"
            + FormWRTable.COLUMN_END_TIME + " TEXT,"
            + FormWRTable.COLUMN_ISTATUS + " TEXT,"
            + FormWRTable.COLUMN_DEVICEID + " TEXT,"
            + FormWRTable.COLUMN_DEVICETAGID + " TEXT,"
            + FormWRTable.COLUMN_SYNCED + " TEXT,"
            + FormWRTable.COLUMN_SYNCED_DATE + " TEXT,"
            + FormWRTable.COLUMN_APPVERSION + " TEXT,"
            + FormWRTable.COLUMN_WR + " TEXT"
            + " );"
            )

    const val SQL_CREATE_USERS = ("CREATE TABLE "
            + UsersTable.TABLE_NAME + "("
            + UsersTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersTable.COLUMN_USERNAME + " TEXT,"
            + UsersTable.COLUMN_PASSWORD + " TEXT,"
            + UsersTable.COLUMN_FULLNAME + " TEXT,"
            + UsersTable.COLUMN_DIST_ID + " TEXT,"
            + UsersTable.COLUMN_UC_CODE + " TEXT,"
            + UsersTable.COLUMN_ENABLED + " TEXT,"
            + UsersTable.COLUMN_ISNEW_USER + " TEXT,"
            + UsersTable.COLUMN_PWD_EXPIRY + " TEXT,"
            + UsersTable.COLUMN_DESIGNATION + " TEXT"
            + " );"
            )


    const val SQL_CREATE_VERSIONAPP = ("CREATE TABLE "
            + VersionTable.TABLE_NAME + " ("
            + VersionTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + VersionTable.COLUMN_VERSION_CODE + " TEXT, "
            + VersionTable.COLUMN_VERSION_NAME + " TEXT, "
            + VersionTable.COLUMN_PATH_NAME + " TEXT "
            + ");"
            )

    const val SQL_CREATE_ENTRYLOGS = ("CREATE TABLE "
            + EntryLogTable.TABLE_NAME + "("
            + EntryLogTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EntryLogTable.COLUMN_PROJECT_NAME + " TEXT,"
            + EntryLogTable.COLUMN_UID + " TEXT,"
            + EntryLogTable.COLUMN_UUID + " TEXT,"
            + EntryLogTable.COLUMN_HHID + " TEXT,"
            + EntryLogTable.COLUMN_USERNAME + " TEXT,"
            + EntryLogTable.COLUMN_SYSDATE + " TEXT,"
            + EntryLogTable.COLUMN_DEVICEID + " TEXT,"
            + EntryLogTable.COLUMN_ENTRY_DATE + " TEXT,"
            + EntryLogTable.COLUMN_ISTATUS + " TEXT,"
            + EntryLogTable.COLUMN_ISTATUS96x + " TEXT,"
            + EntryLogTable.COLUMN_ENTRY_TYPE + " TEXT,"
            + EntryLogTable.COLUMN_SYNCED + " TEXT,"
            + EntryLogTable.COLUMN_SYNC_DATE + " TEXT,"
            + EntryLogTable.COLUMN_APPVERSION + " TEXT"
            + " );"
            )

    const val SQL_CREATE_HF = ("CREATE TABLE " + TableHealthFacilities.TABLE_NAME + "("
            + TableHealthFacilities.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableHealthFacilities.COLUMN_HF_CODE + " TEXT,"
            + TableHealthFacilities.COLUMN_HF_NAME + " TEXT,"
            + TableHealthFacilities.COLUMN_UC_CODE + " TEXT"
            + " );")

    const val SQL_CREATE_UC = ("CREATE TABLE " + TableUCs.TABLE_NAME + "("
            + TableUCs.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableUCs.COLUMN_UC_CODE + " TEXT,"
            + TableUCs.COLUMN_UC_NAME + " TEXT"
            + " );")

    const val SQL_CREATE_VACCINESDATA = ("CREATE TABLE " + TableVaccinesData.TABLE_NAME + "("
            + TableVaccinesData.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableVaccinesData.COLUMN_UC_CODE + " TEXT,"
            + TableVaccinesData.COLUMN_AID + " TEXT,"
            + TableVaccinesData.COLUMN_UID + " TEXT,"
            + TableVaccinesData.COLUMN_UUID + " TEXT,"
            + TableVaccinesData.COLUMN_FACILITY_CODE + " TEXT,"
            + TableVaccinesData.COLUMN_USERNAME + " TEXT,"
            + TableVaccinesData.COLUMN_VB02 + " TEXT,"
            + TableVaccinesData.COLUMN_VB03 + " TEXT,"
            + TableVaccinesData.COLUMN_VB04 + " TEXT,"
            + TableVaccinesData.COLUMN_VB07 + " TEXT,"
            + TableVaccinesData.COLUMN_VB04A + " TEXT,"
            + TableVaccinesData.COLUMN_VB05A + " TEXT,"
            + TableVaccinesData.COLUMN_VB05D + " TEXT,"
            + TableVaccinesData.COLUMN_VB05M + " TEXT,"
            + TableVaccinesData.COLUMN_VB05Y + " TEXT,"
            + TableVaccinesData.COLUMN_DOB + " TEXT,"
            + TableVaccinesData.COLUMN_BCG + " TEXT,"
            + TableVaccinesData.COLUMN_OPV0 + " TEXT,"
            + TableVaccinesData.COLUMN_OPV1 + " TEXT,"
            + TableVaccinesData.COLUMN_OPV2 + " TEXT,"
            + TableVaccinesData.COLUMN_OPV3 + " TEXT,"
            + TableVaccinesData.COLUMN_HEP_B + " TEXT,"
            + TableVaccinesData.COLUMN_PENTA1 + " TEXT,"
            + TableVaccinesData.COLUMN_PENTA2 + " TEXT,"
            + TableVaccinesData.COLUMN_PENTA3 + " TEXT,"
            + TableVaccinesData.COLUMN_PCV1 + " TEXT,"
            + TableVaccinesData.COLUMN_PCV2 + " TEXT,"
            + TableVaccinesData.COLUMN_PCV3 + " TEXT,"
            + TableVaccinesData.COLUMN_IPV1 + " TEXT,"
            + TableVaccinesData.COLUMN_IPV2 + " TEXT,"
            + TableVaccinesData.COLUMN_ROTA1 + " TEXT,"
            + TableVaccinesData.COLUMN_ROTA2 + " TEXT,"
            + TableVaccinesData.COLUMN_MEASLES1 + " TEXT,"
            + TableVaccinesData.COLUMN_MEASLES2 + " TEXT,"
            + TableVaccinesData.COLUMN_TYPHOID + " TEXT,"
            + TableVaccinesData.COLUMN_DPT + " TEXT"
            /*+ TableVaccinesData.COLUMN_VB08C_CODE + " TEXT,"
            + TableVaccinesData.COLUMN_VB08C_ANT + " TEXT,"
            + TableVaccinesData.COLUMN_VB08C_DT + " TEXT,"
            + TableVaccinesData.COLUMN_VB08W_CODE + " TEXT,"
            + TableVaccinesData.COLUMN_VB08W_ANT + " TEXT,"
            + TableVaccinesData.COLUMN_VB08W_DT + " TEXT"*/
            + " );")

    const val SQL_CREATE_WOMENFOLLOWUP = ("CREATE TABLE " + TableWomenFollowUP.TABLE_NAME + "("
            + TableWomenFollowUP.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableWomenFollowUP.COLUMN_UC_CODE + " TEXT,"
            + TableWomenFollowUP.COLUMN_AID + " TEXT,"
            + TableWomenFollowUP.COLUMN_UID + " TEXT,"
            + TableWomenFollowUP.COLUMN_VILLAGE_CODE + " TEXT,"
            + TableWomenFollowUP.COLUMN_FACILITY_CODE + " TEXT,"
            + TableWomenFollowUP.COLUMN_VB02 + " TEXT,"
            + TableWomenFollowUP.COLUMN_VB03 + " TEXT,"
            + TableWomenFollowUP.COLUMN_VB04 + " TEXT,"
            + TableWomenFollowUP.COLUMN_VB04A + " TEXT,"
            + TableWomenFollowUP.COLUMN_VB05A + " TEXT,"
            + TableWomenFollowUP.COLUMN_AGE + " TEXT,"
            + TableWomenFollowUP.COLUMN_TT1 + " TEXT,"
            + TableWomenFollowUP.COLUMN_TT2 + " TEXT,"
            + TableWomenFollowUP.COLUMN_TT3 + " TEXT,"
            + TableWomenFollowUP.COLUMN_TT4 + " TEXT,"
            + TableWomenFollowUP.COLUMN_TT5 + " TEXT"
            + " );")

    const val SQL_CREATE_VILLAGES = ("CREATE TABLE " + TableVillages.TABLE_NAME + "("
            + TableVillages.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableVillages.COLUMN_UC_CODE + " TEXT,"
            + TableVillages.COLUMN_VILLAGE_CODE + " TEXT,"
            + TableVillages.COLUMN_VILLAGE_NAME + " TEXT"
            + " );")

    const val SQL_ALTER_ADD_DOB = ("ALTER TABLE "
            + TableVaccinesData.TABLE_NAME + " ADD COLUMN "
            + TableVaccinesData.COLUMN_DOB + " TEXT;")

    const val SQL_ALTER_ADD_DPT = ("ALTER TABLE "
            + TableVaccinesData.TABLE_NAME + " ADD COLUMN "
            + TableVaccinesData.COLUMN_DPT + " TEXT;")

    const val SQL_ALTER_ADD_USERNAME = ("ALTER TABLE "
            + TableVaccinesData.TABLE_NAME + " ADD COLUMN "
            + TableVaccinesData.COLUMN_USERNAME + " TEXT;")

    const val SQL_ALTER_ADD_VB07 = ("ALTER TABLE "
            + VaccinesTable.TABLE_NAME + " ADD COLUMN "
            + VaccinesTable.COLUMN_VB07 + " TEXT;")

    const val SQL_ALTER_ADD_vB07 = ("ALTER TABLE "
            + TableVaccinesData.TABLE_NAME + " ADD COLUMN "
            + TableVaccinesData.COLUMN_VB07 + " TEXT;")

}