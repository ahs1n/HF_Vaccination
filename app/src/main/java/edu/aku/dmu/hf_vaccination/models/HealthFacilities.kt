package edu.aku.dmu.hf_vaccination.models

import android.database.Cursor
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableHealthFacilities
import edu.aku.dmu.hf_vaccination.core.MainApp._EMPTY_
import org.json.JSONObject

/**
 * Created by hussain.siddiqui on 10/06/2022.
 */

class HealthFacilities {
    var uc_code: String = _EMPTY_
    var hfCode: String = _EMPTY_
    var hfName: String = _EMPTY_

    fun sync(jsonObject: JSONObject): HealthFacilities {
        uc_code = jsonObject.getString(TableHealthFacilities.COLUMN_UC_CODE)
        hfCode = jsonObject.getString(TableHealthFacilities.COLUMN_HF_CODE)
        hfName = jsonObject.getString(TableHealthFacilities.COLUMN_HF_NAME)
        return this
    }

    fun hydrate(cursor: Cursor): HealthFacilities {
        uc_code =
            cursor.getString(cursor.getColumnIndexOrThrow(TableHealthFacilities.COLUMN_UC_CODE))
        hfCode =
            cursor.getString(cursor.getColumnIndexOrThrow(TableHealthFacilities.COLUMN_HF_CODE))
        hfName =
            cursor.getString(cursor.getColumnIndexOrThrow(TableHealthFacilities.COLUMN_HF_NAME))

        return this
    }


}