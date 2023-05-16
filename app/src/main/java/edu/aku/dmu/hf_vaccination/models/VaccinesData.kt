package edu.aku.dmu.hf_vaccination.models

import android.database.Cursor
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableVaccinesData
import edu.aku.dmu.hf_vaccination.core.MainApp._EMPTY_
import org.json.JSONObject

/**
 * Created by hussain.siddiqui on 10/06/2022.
 */

class VaccinesData {
    var ucCode: String = _EMPTY_
    var aID: String = _EMPTY_

    //var uID: String = _EMPTY_
    var uID: String = _EMPTY_
    var villageCode: String = _EMPTY_
    var villageName: String = _EMPTY_
    var username: String = _EMPTY_

    //var facilityCode: String = _EMPTY_
    var vBO2: String = _EMPTY_
    var vBO3: String = _EMPTY_
    var vB04: String = _EMPTY_
    var vB07: String = _EMPTY_
    var vB04A: String = _EMPTY_
    var vBO5A: String = _EMPTY_
    var vBO5D: String = _EMPTY_
    var vBO5M: String = _EMPTY_
    var vBO5Y: String = _EMPTY_
    var dob: String = _EMPTY_
    var bcg: String = _EMPTY_
    var opv0: String = _EMPTY_
    var opv1: String = _EMPTY_
    var opv2: String = _EMPTY_
    var opv3: String = _EMPTY_
    var HepB: String = _EMPTY_
    var penta1: String = _EMPTY_
    var penta2: String = _EMPTY_
    var penta3: String = _EMPTY_
    var pcv1: String = _EMPTY_
    var pcv2: String = _EMPTY_
    var pcv3: String = _EMPTY_
    var ipv1: String = _EMPTY_
    var ipv2: String = _EMPTY_
    var rota1: String = _EMPTY_
    var rota2: String = _EMPTY_
    var Measles1: String = _EMPTY_
    var Measles2: String = _EMPTY_
    var Typhoid: String = _EMPTY_
    var dpt: String = _EMPTY_


    fun sync(jsonObject: JSONObject): VaccinesData {
        ucCode = jsonObject.getString(TableVaccinesData.COLUMN_UC_CODE)
        aID = jsonObject.getString(TableVaccinesData.COLUMN_AID)
        uID = jsonObject.getString(TableVaccinesData.COLUMN_UID)
        username = jsonObject.getString(TableVaccinesData.COLUMN_USERNAME)
        vBO2 = jsonObject.getString(TableVaccinesData.COLUMN_VB02)
        vBO3 = jsonObject.getString(TableVaccinesData.COLUMN_VB03)
        vB04 = jsonObject.getString(TableVaccinesData.COLUMN_VB04)
        vB04 = jsonObject.getString(TableVaccinesData.COLUMN_VB07)
        vB04A = jsonObject.getString(TableVaccinesData.COLUMN_VB04A)
        vBO5A = jsonObject.getString(TableVaccinesData.COLUMN_VB05A)
        vBO5D = jsonObject.getString(TableVaccinesData.COLUMN_VB05D)
        vBO5M = jsonObject.getString(TableVaccinesData.COLUMN_VB05M)
        vBO5Y = jsonObject.getString(TableVaccinesData.COLUMN_VB05Y)
        dob = jsonObject.getString(TableVaccinesData.COLUMN_DOB)
        bcg = jsonObject.getString(TableVaccinesData.COLUMN_BCG)
        opv0 = jsonObject.getString(TableVaccinesData.COLUMN_OPV0)
        opv1 = jsonObject.getString(TableVaccinesData.COLUMN_OPV1)
        opv2 = jsonObject.getString(TableVaccinesData.COLUMN_OPV2)
        opv3 = jsonObject.getString(TableVaccinesData.COLUMN_OPV3)
        HepB = jsonObject.getString(TableVaccinesData.COLUMN_HEP_B)
        penta1 = jsonObject.getString(TableVaccinesData.COLUMN_PENTA1)
        penta2 = jsonObject.getString(TableVaccinesData.COLUMN_PENTA2)
        penta3 = jsonObject.getString(TableVaccinesData.COLUMN_PENTA3)
        pcv1 = jsonObject.getString(TableVaccinesData.COLUMN_PCV1)
        pcv2 = jsonObject.getString(TableVaccinesData.COLUMN_PCV2)
        pcv3 = jsonObject.getString(TableVaccinesData.COLUMN_PCV3)
        ipv1 = jsonObject.getString(TableVaccinesData.COLUMN_IPV1)
        ipv2 = jsonObject.getString(TableVaccinesData.COLUMN_IPV2)
        rota1 = jsonObject.getString(TableVaccinesData.COLUMN_ROTA1)
        rota2 = jsonObject.getString(TableVaccinesData.COLUMN_ROTA2)
        Measles1 = jsonObject.getString(TableVaccinesData.COLUMN_MEASLES1)
        Measles2 = jsonObject.getString(TableVaccinesData.COLUMN_MEASLES2)
        Typhoid = jsonObject.getString(TableVaccinesData.COLUMN_TYPHOID)
        dpt = jsonObject.getString(TableVaccinesData.COLUMN_DPT)

        return this
    }

    fun hydrate(cursor: Cursor): VaccinesData {
        ucCode =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_UC_CODE))
        aID =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_AID))
        uID =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_UID))
        username =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_USERNAME))
        vBO2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB02))
        vBO3 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB03))
        vB04 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB04))
        vB07 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB07))
        vB04A =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB04A))
        vBO5A =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB05A))
        vBO5D =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB05D))
        vBO5M =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB05M))
        vBO5Y =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_VB05Y))
        dob =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_DOB))
        bcg =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_BCG))
        opv0 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_OPV0))
        opv1 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_OPV1))
        opv2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_OPV2))
        opv3 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_OPV3))
        HepB =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_HEP_B))
        penta1 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_PENTA1))
        penta2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_PENTA2))
        penta3 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_PENTA3))
        pcv1 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_PCV1))
        pcv2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_PCV2))
        pcv3 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_PCV3))
        ipv1 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_IPV1))
        ipv2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_IPV2))
        rota1 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_ROTA1))
        rota2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_ROTA2))
        Measles1 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_MEASLES1))
        Measles2 =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_MEASLES2))
        Typhoid =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_TYPHOID))
        dpt =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccinesData.COLUMN_DPT))
        return this
    }
}