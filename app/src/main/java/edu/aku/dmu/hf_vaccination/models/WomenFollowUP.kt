package edu.aku.dmu.hf_vaccination.models

import android.database.Cursor
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableWomenFollowUP
import edu.aku.dmu.hf_vaccination.core.MainApp._EMPTY_
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by hussain.siddiqui on 10/06/2022.
 */

class WomenFollowUP {
    var ucCode: String = _EMPTY_
    var aID: String = _EMPTY_

    //var uID: String = _EMPTY_
    var uID: String = _EMPTY_
    var villageCode: String = _EMPTY_
    var villageName: String = _EMPTY_

    //var facilityCode: String = _EMPTY_
    var vBO2: String = _EMPTY_
    var vBO3: String = _EMPTY_
    var vB04: String = _EMPTY_
    var vB04A: String = _EMPTY_
    var vBO5A: String = _EMPTY_
    var age: String = _EMPTY_
    var facilityCode: String = _EMPTY_
    var tt1: String = _EMPTY_
    var tt2: String = _EMPTY_
    var tt3: String = _EMPTY_
    var tt4: String = _EMPTY_
    var tt5: String = _EMPTY_

    constructor() {
        // Default Constructor
    }

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): WomenFollowUP {
        ucCode = jsonObject.getString(TableWomenFollowUP.COLUMN_UC_CODE)
        aID = jsonObject.getString(TableWomenFollowUP.COLUMN_AID)
        uID = jsonObject.getString(TableWomenFollowUP.COLUMN_UID)
        villageCode = jsonObject.getString(TableWomenFollowUP.COLUMN_VILLAGE_CODE)
        vBO2 = jsonObject.getString(TableWomenFollowUP.COLUMN_VB02)
        vBO3 = jsonObject.getString(TableWomenFollowUP.COLUMN_VB03)
        vB04 = jsonObject.getString(TableWomenFollowUP.COLUMN_VB04)
        vB04A = jsonObject.getString(TableWomenFollowUP.COLUMN_VB04A)
        vBO5A = jsonObject.getString(TableWomenFollowUP.COLUMN_VB05A)
        age = jsonObject.getString(TableWomenFollowUP.COLUMN_AGE)
        facilityCode = jsonObject.getString(TableWomenFollowUP.COLUMN_FACILITY_CODE)
        tt1 = jsonObject.getString(TableWomenFollowUP.COLUMN_TT1)
        tt2 = jsonObject.getString(TableWomenFollowUP.COLUMN_TT2)
        tt3 = jsonObject.getString(TableWomenFollowUP.COLUMN_TT3)
        tt4 = jsonObject.getString(TableWomenFollowUP.COLUMN_TT4)
        tt5 = jsonObject.getString(TableWomenFollowUP.COLUMN_TT5)

        return this
    }

    fun hydrate(cursor: Cursor): WomenFollowUP {
        ucCode =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_UC_CODE))
        aID =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_AID))
        uID =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_UID))
        villageCode =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_VILLAGE_CODE))
        vBO2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_VB02))
        vBO3 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_VB03))
        vB04 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_VB04))
        vB04A =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_VB04A))
        vBO5A =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_VB05A))
        age =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_AGE))
        facilityCode =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_FACILITY_CODE))
        tt1 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_TT1))
        tt2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_TT2))
        tt3 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_TT3))
        tt4 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_TT4))
        tt5 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableWomenFollowUP.COLUMN_TT5))
        return this
    }
}