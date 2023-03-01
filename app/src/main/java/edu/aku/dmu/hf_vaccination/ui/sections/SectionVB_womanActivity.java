package edu.aku.dmu.hf_vaccination.ui.sections;

import static edu.aku.dmu.hf_vaccination.core.MainApp.flag;
import static edu.aku.dmu.hf_vaccination.core.MainApp.formVB;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccineCount;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccines;
import static edu.aku.dmu.hf_vaccination.core.MainApp.womenFollowUP;
import static edu.aku.dmu.hf_vaccination.core.MainApp.womenFollowUPList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.util.ArrayList;

import edu.aku.dmu.hf_vaccination.MainActivity;
import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.FormsVBTable;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivitySectionVbWomanBinding;
import edu.aku.dmu.hf_vaccination.models.WomenFollowUP;

public class SectionVB_womanActivity extends AppCompatActivity {
    private static final String TAG = "SectionVBActivity";
    ActivitySectionVbWomanBinding bi;
    boolean btn, group, b, woman;
    private DatabaseHelper db;

    public static char getChar(int i) {
        return i < 0 || i > 25 ? '?' : (char) ('a' + i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeUrdu);
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_vb_woman);
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;

        formVB.setUuid(MainApp.formVA.getUid());
        setupListeners();
        initUi();


    }

    private void initUi() {

        btn = getIntent().getBooleanExtra("btn", true);
        if (btn) bi.btnEnd.setVisibility(View.VISIBLE);

        if (MainApp.flag) {
            bi.pName.setText(formVB.getVb04a());
            bi.hName.setText(formVB.getVb04());
            bi.cardNo.setText(formVB.getVb02());
            bi.vacStatus.setText("TT" + formVB.getVb08w());
            bi.vacDate.setText(formVB.getVb08wdt());
        } else {
            bi.pName.setText(womenFollowUP.getVB04A());
            bi.hName.setText(womenFollowUP.getVB04());
            bi.cardNo.setText(womenFollowUP.getVBO2());
            bi.dob.setText(womenFollowUP.getAge());

        }

        // Show previous dose data in case of followup
        MainApp.womenFollowUPList = new ArrayList<>();

        vaccineCount = 0;
        String baseId = "";
        ArrayList<Boolean> results = new ArrayList<Boolean>();

        Log.d(TAG, "onCreate(womenVaccineList): " + womenFollowUPList.size());


        try {
            womenFollowUPList.clear();
            womenFollowUPList = db.getSyncedVaccinatedWomenBYUID(womenFollowUP.getUID(), womenFollowUP.getVBO2());

            for (WomenFollowUP womenFollowUP : womenFollowUPList) {
                // TT
                baseId = "vb08wa";
                results.clear();
                results.add(showHideDoneCheckWithText(womenFollowUP.getTt1(), baseId, "a"));
                results.add(showHideDoneCheckWithText(womenFollowUP.getTt2(), baseId, "b"));
                results.add(showHideDoneCheckWithText(womenFollowUP.getTt3(), baseId, "c"));
                results.add(showHideDoneCheckWithText(womenFollowUP.getTt4(), baseId, "d"));
                results.add(showHideDoneCheckWithText(womenFollowUP.getTt5(), baseId, "e"));
                verifyCrossTicks(results, baseId);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(FormVB): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        bi.setForm(formVB);


    }

    private void setupListeners() {
        bi.vb08w.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08wdt));

    }

    private View getViewDynamically(String viewId) {
        return findViewById(getResources()
                .getIdentifier(viewId, "id", getPackageName()));
    }

    private void markAsCrossed(String baseId, int index) {
        String letter = String.valueOf(getChar(index));
        if (letter.equals("?"))
            return;

        RadioButton radioButton = (RadioButton) getViewDynamically(baseId + letter);
        ImageView imgCross = (ImageView) getViewDynamically(baseId + letter + "tick");

        if (radioButton != null && imgCross != null) {
            if (imgCross.getVisibility() == View.GONE) {
                radioButton.setVisibility(View.GONE);
                imgCross.setVisibility(View.VISIBLE);
                imgCross.setBackground(getResources().getDrawable(R.drawable.ic_baseline_close_24));
            }
        }
    }

    private void verifyCrossTicks(ArrayList<Boolean> results, String baseId) {
        int firstTrue = results.lastIndexOf(true);
        if (firstTrue > 0) {
            for (int j = 0; j < firstTrue; j++) {
                markAsCrossed(baseId, j);
            }
        }
    }

    private boolean showHideDoneCheckWithText(
            String conditionText,
            String baseId,
            String letter
    ) {

        RadioButton radioButton = (RadioButton) getViewDynamically(baseId + letter);
        ImageView imgDone = (ImageView) getViewDynamically(baseId + letter + "tick");
        TextView txtVaccine = (TextView) getViewDynamically(baseId + letter + "txt");

        if (!conditionText.equals("")) {
            if (txtVaccine != null) {
                txtVaccine.setVisibility(View.VISIBLE);
                txtVaccine.setTextColor(getResources().getColor(R.color.green));
                txtVaccine.setText(conditionText);
            }

            if (radioButton != null)
                radioButton.setVisibility(View.GONE);

            if (imgDone != null)
                imgDone.setVisibility(View.VISIBLE);

            return true;
        }
        return false;
    }


    private boolean insertVaccineRecord(String vaccCode, String antigen, String vaccDate) {

        setGPS();


        if (flag) {

            vaccines.setGpsLat(formVB.getGpsLat());
            vaccines.setGpsLng(formVB.getGpsLng());
            vaccines.setGpsAcc(formVB.getGpsAcc());
            vaccines.setGpsDT(formVB.getGpsDT());
        } else {
            vaccines.setGpsLat(vaccines.getGpsLat());
            vaccines.setGpsLng(vaccines.getGpsLng());
            vaccines.setGpsAcc(vaccines.getGpsAcc());
            vaccines.setGpsDT(vaccines.getGpsDT());
        }

        vaccines.updateAntigenWoman(vaccCode, antigen, vaccDate);
        long rowId = 0;
        try {
            rowId = db.addVaccine(vaccines);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        vaccines.setId(String.valueOf(rowId));
        if (rowId > 0) {
            vaccines.setUid(vaccines.getDeviceId() + vaccines.getId());
            db.updatesVaccineColumn(TableContracts.VaccinesTable.COLUMN_UID, vaccines.getUid());
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private boolean updateDB() {
        if (MainApp.superuser) return true;

        setGPS();

        int updcount = 0;
        try {
            updcount = db.updatesFormVBColumn(FormsVBTable.COLUMN_VAC, formVB.vACtoString())
                    + db.updatesFormVBColumn(FormsVBTable.COLUMN_GPSLAT, formVB.getGpsLat())
                    + db.updatesFormVBColumn(FormsVBTable.COLUMN_GPSLNG, formVB.getGpsLng())
                    + db.updatesFormVBColumn(FormsVBTable.COLUMN_GPSACC, formVB.getGpsAcc())
                    + db.updatesFormVBColumn(FormsVBTable.COLUMN_GPSDATE, formVB.getGpsDT());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, R.string.upd_db + e.getMessage());
            Toast.makeText(this, R.string.upd_db + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount == 5) {
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean updateDBVaccineFUP() {
        if (MainApp.superuser) return true;

        setGPS();

        int updcount = 0;
        updcount = db.updatesVaccineColumn(TableContracts.VaccinesTable.COLUMN_GPSLAT, vaccines.getGpsLat())
                + db.updatesVaccineColumn(TableContracts.VaccinesTable.COLUMN_GPSLNG, vaccines.getGpsLng())
                + db.updatesVaccineColumn(TableContracts.VaccinesTable.COLUMN_GPSACC, vaccines.getGpsAcc())
                + db.updatesVaccineColumn(TableContracts.VaccinesTable.COLUMN_GPSDATE, vaccines.getGpsDT());
        if (updcount == 4) {
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void setGPS() {
        SharedPreferences GPSPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");
            String acc = GPSPref.getString("Accuracy", "0");

            if (lat == "0" && lang == "0") {
                Toast.makeText(this, "Could not obtained points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Points set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            if (flag) {
                formVB.setGpsLat(lat);
                formVB.setGpsLng(lang);
                formVB.setGpsAcc(acc);
                formVB.setGpsDT(date); // Timestamp is converted to date above
            } else {
                vaccines.setGpsLat(lat);
                vaccines.setGpsLng(lang);
                vaccines.setGpsAcc(acc);
                vaccines.setGpsDT(date); // Timestamp is converted to date above
            }

            Toast.makeText(this, "Points set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setPoints: " + e.getMessage());
        }

    }


    public void btnContinue(View view) {
        //vaccines = new Vaccines();
        if (!formValidation()) return;
//        if (b) if (!insertNewRecord()) return;

        if (flag) {
            vaccines.populateMeta();
        } else {
            vaccines.populateMetaFollowUpWoman();
        }

        String waAntigen = null;

        // TT
        if (!formVB.getVb08w().equals("")) {
            waAntigen = bi.vb08waa.isChecked() ? "1"
                    : bi.vb08wab.isChecked() ? "2"
                    : bi.vb08wac.isChecked() ? "3"
                    : bi.vb08wad.isChecked() ? "4"
                    : bi.vb08wae.isChecked() ? "5"
                    : "-1";
            insertVaccineRecord("TT", waAntigen, bi.vb08wdt.getText().toString());
        }

        if (flag) {
            if (updateDB()) {
                MainApp.flag = false;
                finish();
                Toast.makeText(this, "Form saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, R.string.fail_db_upd, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (updateDBVaccineFUP()) {
                finish();
                Toast.makeText(this, "Form saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, R.string.fail_db_upd, Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void btnEnd(View view) {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


    private boolean formValidation() {

        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void onBackPressed() {
//        MainApp.memberCount --;
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }

}