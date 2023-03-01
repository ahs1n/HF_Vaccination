package edu.aku.dmu.hf_vaccination.models

import android.database.Cursor
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableVaccineSchedule
import edu.aku.dmu.hf_vaccination.core.MainApp._EMPTY_
import org.json.JSONObject

/**
 * Created by hussain.siddiqui on 10/06/2022.
 */

class VaccinesSchedule {

    var vname: String = _EMPTY_
    var vgroup: String = _EMPTY_
    var bydob: String = _EMPTY_
    var byprvdoze: String = _EMPTY_
    var comments: String = _EMPTY_


    fun sync(jsonObject: JSONObject): VaccinesSchedule {
        vname = jsonObject.getString(TableVaccineSchedule.COLUMN_VNAME)
        vgroup = jsonObject.getString(TableVaccineSchedule.COLUMN_VGROUP)
        bydob = jsonObject.getString(TableVaccineSchedule.COLUMN_BYDOB)
        byprvdoze = jsonObject.getString(TableVaccineSchedule.COLUMN_BYPRVDOZE)
        comments = jsonObject.getString(TableVaccineSchedule.COLUMN_COMMENTS)

        return this
    }

    fun hydrate(cursor: Cursor): VaccinesSchedule {
        vname =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccineSchedule.COLUMN_VNAME))
        vgroup =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccineSchedule.COLUMN_VGROUP))
        bydob =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccineSchedule.COLUMN_BYDOB))
        byprvdoze =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccineSchedule.COLUMN_BYPRVDOZE))
        comments =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVaccineSchedule.COLUMN_COMMENTS))

        return this
    }


}