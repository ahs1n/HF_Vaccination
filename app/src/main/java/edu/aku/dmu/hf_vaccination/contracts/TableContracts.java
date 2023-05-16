package edu.aku.dmu.hf_vaccination.contracts;

import android.provider.BaseColumns;

public class TableContracts {

    public static abstract class FormsVATable implements BaseColumns {
        public static final String TABLE_NAME = "FormVA";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_VA = "vA";
        public static final String COLUMN_GPSLAT = "gpslat";
        public static final String COLUMN_GPSLNG = "gpslng";
        public static final String COLUMN_GPSDATE = "gpsdate";
        public static final String COLUMN_GPSACC = "gpsacc";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class FormsVBTable implements BaseColumns {
        public static final String TABLE_NAME = "FormVB";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_WID = "_wid";
        public static final String COLUMN_AID = "_aid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_FACILITY_CODE = "facilityCode";
        public static final String COLUMN_AREA_NAME = "wl_area";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_VB = "vB";
        public static final String COLUMN_VAC = "vAC";
        public static final String COLUMN_GPSLAT = "gpslat";
        public static final String COLUMN_GPSLNG = "gpslng";
        public static final String COLUMN_GPSDATE = "gpsdate";
        public static final String COLUMN_GPSACC = "gpsacc";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_CARD_NO = "cardNo";
        public static final String COLUMN_VB04A_NAME = "vb04aName";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class VaccinesTable implements BaseColumns {
        public static final String TABLE_NAME = "Vaccines";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_AID = "_aid";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_FACILITY_CODE = "facilityCode";
        public static final String COLUMN_AREA_NAME = "wl_area";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_GPSLAT = "gpslat";
        public static final String COLUMN_GPSLNG = "gpslng";
        public static final String COLUMN_GPSDATE = "gpsdate";
        public static final String COLUMN_GPSACC = "gpsacc";
        public static final String COLUMN_VB02 = "vb02";
        public static final String COLUMN_VB04A = "vb04a";
        public static final String COLUMN_VB04 = "vb04";
        public static final String COLUMN_VB07 = "vb07";
        public static final String COLUMN_VB08C_CODE = "vb08cCode";
        public static final String COLUMN_VB08C_ANTIGEN = "vb08cAnt";
        public static final String COLUMN_VB08C_DATE = "vb08cDT";
        public static final String COLUMN_FRONT_FILE_NAME = "cardFrontImage";
        public static final String COLUMN_BACK_FILE_NAME = "cardBackImage";
        public static final String COLUMN_CHILD_FILE_NAME = "childImage";
        public static final String COLUMN_VB08W_CODE = "vb08wCode";
        public static final String COLUMN_VB08W_ANTIGEN = "vb08wAnt";
        public static final String COLUMN_VB08W_DATE = "vb08wDT";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class VaccinesDueTable implements BaseColumns {

        public static final String TABLE_NAME = "VaccDueDates";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_AID = "_aid";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_VILLAGE_CODE = "villageCode";
        public static final String COLUMN_FACILITY_CODE = "facilityCode";
        public static final String COLUMN_AREA_NAME = "wl_area";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_VB02 = "vb02";
        public static final String COLUMN_VB04A = "vb04a";
        public static final String COLUMN_VB04 = "vb04";
        public static final String COLUMN_VB08C_CODE = "vb08cCode";
        public static final String COLUMN_VB08C_ANTIGEN = "vb08cAnt";
        public static final String COLUMN_VB08C_DATE = "vb08cDT";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_APPVERSION = "appversion";
    }

    public static abstract class WorkLocationTable implements BaseColumns {
        public static final String TABLE_NAME = "WorkLocation";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_AID = "_aid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_SWL = "sWL";
        public static final String COLUMN_GPSLAT = "gpslat";
        public static final String COLUMN_GPSLNG = "gpslng";
        public static final String COLUMN_GPSDATE = "gpsdate";
        public static final String COLUMN_GPSACC = "gpsacc";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }

    public static abstract class AttendanceTable implements BaseColumns {
        public static final String TABLE_NAME = "Attendance";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_GPSLAT = "gpslat";
        public static final String COLUMN_GPSLNG = "gpslng";
        public static final String COLUMN_GPSDATE = "gpsdate";
        public static final String COLUMN_GPSACC = "gpsacc";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
    }


    public static abstract class FormCRTable implements BaseColumns {
        public static final String TABLE_NAME = "FormCR";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_CR = "cR";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_START_TIME = "start_time";
    }


    public static abstract class FormWRTable implements BaseColumns {
        public static final String TABLE_NAME = "FormWR";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_WR = "wR";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_START_TIME = "start_time";
    }


    public static abstract class UsersTable implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "passwordEnc";
        public static final String COLUMN_FULLNAME = "full_name";
        public static final String COLUMN_DESIGNATION = "designation";
        public static final String COLUMN_ENABLED = "enabled";
        public static final String COLUMN_ISNEW_USER = "isNewUser";
        public static final String COLUMN_PWD_EXPIRY = "pwdExpiry";
        public static final String COLUMN_UC_CODE = "uccode";
        public static final String COLUMN_DIST_ID = "dist_id";
    }


    public static abstract class VersionTable implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String TABLE_NAME = "versionApp";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_VERSION_PATH = "elements";
        public static final String COLUMN_VERSION_CODE = "versionCode";
        public static final String COLUMN_VERSION_NAME = "versionName";
        public static final String COLUMN_PATH_NAME = "outputFile";
        public static final String SERVER_URI = "output-metadata.json";

    }

    public static abstract class EntryLogTable implements BaseColumns {
        public static final String TABLE_NAME = "EntryLog";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_HHID = "hhid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_ENTRY_DATE = "entryDate";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";
        public static final String COLUMN_ENTRY_TYPE = "entry_type";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_ISTATUS = "istatus";
        public static final String COLUMN_ISTATUS96x = "istatus96x";
    }


    public static abstract class TableUCs implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String TABLE_NAME = "uclist";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UC_CODE = "uccode";
        public static final String COLUMN_UC_NAME = "ucname";
    }


    public static abstract class TableVaccinesData implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String TABLE_NAME = "vaccinesFollowUp";
        public static final String TABLE_NAME_API = "Vaccines";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UC_CODE = "uccode";
        public static final String COLUMN_AID = "_aid";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_FACILITY_CODE = "facilitycode";
        public static final String COLUMN_VB02 = "vb02";
        public static final String COLUMN_VB03 = "vb03";
        public static final String COLUMN_VB04 = "vb04";
        public static final String COLUMN_VB07 = "vb07";
        public static final String COLUMN_VB04A = "vb04a";
        public static final String COLUMN_VB05A = "vb05a";
        public static final String COLUMN_VB05D = "vb05d";
        public static final String COLUMN_VB05M = "vb05m";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_VB05Y = "vb05y";
        public static final String COLUMN_BCG = "bcg";
        public static final String COLUMN_OPV0 = "opv0";
        public static final String COLUMN_OPV1 = "opv1";
        public static final String COLUMN_OPV2 = "opv2";
        public static final String COLUMN_OPV3 = "opv3";
        public static final String COLUMN_HEP_B = "HepB";
        public static final String COLUMN_PENTA1 = "penta1";
        public static final String COLUMN_PENTA2 = "penta2";
        public static final String COLUMN_PENTA3 = "penta3";
        public static final String COLUMN_PCV1 = "pcv1";
        public static final String COLUMN_PCV2 = "pcv2";
        public static final String COLUMN_PCV3 = "pcv3";
        public static final String COLUMN_IPV1 = "ipv1";
        public static final String COLUMN_IPV2 = "ipv2";
        public static final String COLUMN_ROTA1 = "rota1";
        public static final String COLUMN_ROTA2 = "rota2";
        public static final String COLUMN_MEASLES1 = "Measles1";
        public static final String COLUMN_MEASLES2 = "Measles2";
        public static final String COLUMN_TYPHOID = "Typhoid";
        public static final String COLUMN_DPT = "dpt";
    }


    public static abstract class TableWomenFollowUP implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String TABLE_NAME = "womenfollowup";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UC_CODE = "uccode";
        public static final String COLUMN_AID = "_aid";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_VILLAGE_CODE = "villagecode";
        public static final String COLUMN_FACILITY_CODE = "facilityCode";
        public static final String COLUMN_VB02 = "vb02";
        public static final String COLUMN_VB03 = "vb03";
        public static final String COLUMN_VB04 = "vb04";
        public static final String COLUMN_VB04A = "vb04a";
        public static final String COLUMN_VB05A = "vb05a";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_TT1 = "tt1";
        public static final String COLUMN_TT2 = "tt2";
        public static final String COLUMN_TT3 = "tt3";
        public static final String COLUMN_TT4 = "tt4";
        public static final String COLUMN_TT5 = "tt5";
    }

    public static abstract class TableVaccineSchedule implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String TABLE_NAME = "vaccine_schedule";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_VNAME = "vname";
        public static final String COLUMN_VGROUP = "vgroup";
        public static final String COLUMN_BYDOB = "bydob";
        public static final String COLUMN_BYPRVDOZE = "byprvdoze";
        public static final String COLUMN_COMMENTS = "comments";


    }


    public static abstract class TableVillages implements BaseColumns {
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String TABLE_NAME = "Villages";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UC_CODE = "uc_code";
        public static final String COLUMN_VILLAGE_CODE = "village_code";
        public static final String COLUMN_VILLAGE_NAME = "village_name";
    }


    public static abstract class TableHealthFacilities implements BaseColumns {
        public static final String TABLE_NAME = "hf_list";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UC_CODE = "uccode";
        public static final String COLUMN_HF_CODE = "hf_code";
        public static final String COLUMN_HF_NAME = "hf_name";
    }
}

