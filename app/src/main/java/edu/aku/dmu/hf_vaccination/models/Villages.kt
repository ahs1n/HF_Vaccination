package edu.aku.dmu.hf_vaccination.models

import android.database.Cursor
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.TableVillages
import edu.aku.dmu.hf_vaccination.core.MainApp._EMPTY_
import org.json.JSONObject

/**
 * Created by hussain.siddiqui on 10/06/2022.
 */

class Villages {
    var ucCode: String = _EMPTY_
    var villageCode: String = _EMPTY_
    var villageName: String = _EMPTY_

    fun sync(jsonObject: JSONObject): Villages {
        ucCode = jsonObject.getString(TableVillages.COLUMN_UC_CODE)
        villageCode = jsonObject.getString(TableVillages.COLUMN_VILLAGE_CODE)
        villageName = jsonObject.getString(TableVillages.COLUMN_VILLAGE_NAME)
        return this
    }

    fun hydrate(cursor: Cursor): Villages {
        ucCode =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVillages.COLUMN_UC_CODE))
        villageCode =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVillages.COLUMN_VILLAGE_CODE))
        villageName =
            cursor.getString(cursor.getColumnIndexOrThrow(TableVillages.COLUMN_VILLAGE_NAME))

        return this
    }


}