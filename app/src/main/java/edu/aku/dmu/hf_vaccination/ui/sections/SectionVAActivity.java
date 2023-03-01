package edu.aku.dmu.hf_vaccination.ui.sections;

import static edu.aku.dmu.hf_vaccination.core.MainApp.formVA;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

import edu.aku.dmu.hf_vaccination.MainActivity;
import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivitySectionVaBinding;
import edu.aku.dmu.hf_vaccination.models.HealthFacilities;
import edu.aku.dmu.hf_vaccination.models.UCs;

public class SectionVAActivity extends AppCompatActivity {
    private static final String TAG = "SectionVAActivity";
    ActivitySectionVaBinding bi;
    private DatabaseHelper db;
    private ArrayList<String> ucNames, ucCodes, healthFacilityNames, healthFacilityCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_va);
        bi.setForm(formVA);
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;
        populateSpinner();
        setGPS();

/*        if (bi.va05acheck.isChecked()) {
            bi.va05ax.setText(MainApp.previousPage);
        } else {
            bi.va05ax.setText("");
        }*/
        formVA.setVa03(MainApp.user.getFullname());
    }

    private void populateSpinner() {

        Collection<UCs> unionCouncil = db.getAllUCsByUCcode(MainApp.user.getUccode());

        ucNames = new ArrayList<>();
        ucCodes = new ArrayList<>();
        ucNames.clear();
        ucCodes.clear();
        ucNames.add("...");
        ucCodes.add("...");

        for (UCs uc : unionCouncil) {
            ucNames.add(uc.getUcName());
            ucCodes.add(uc.getUcCode());
        }

        if (MainApp.user.getUserName().contains("test") || MainApp.user.getUserName().contains("dmu") || MainApp.user.getUserName().contains("user")) {
            ucNames.add("Test UC 1");
            ucCodes.add("001");
            ucNames.add("Test UC 2");
            ucCodes.add("002");
            ucNames.add("Test UC 3");
            ucCodes.add("003");

        }
        // Apply the adapter to the spinner
        bi.va02.setAdapter(new ArrayAdapter<>(SectionVAActivity.this, R.layout.custom_spinner, ucNames));

        bi.va02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                /*if (position != 0) {
                    MainApp.selectedUCName = (ucNames.get(bi.va02.getSelectedItemPosition()));
                    formVA.setVa02(MainApp.selectedUCName);
                }*/

                if (position == 0) return;
                MainApp.selectedUCCode = (ucCodes.get(bi.va02.getSelectedItemPosition()));
                formVA.setVa02(ucCodes.get(bi.va02.getSelectedItemPosition()));
                Collection<HealthFacilities> healthFacilities = db.getHealthFacilityByUC(MainApp.user.getUccode());
                healthFacilityNames = new ArrayList<>();
                healthFacilityCodes = new ArrayList<>();
                healthFacilityNames.add("...");
                healthFacilityCodes.add("...");

                for (HealthFacilities hf : healthFacilities) {
                    healthFacilityNames.add(hf.getHfName());
                    healthFacilityCodes.add(hf.getHfCode());
                }
                if (MainApp.user.getUserName().contains("test") || MainApp.user.getUserName().contains("dmu")) {

                    healthFacilityNames.add("Test Facility 1 " + ucNames.get(position));
                    healthFacilityNames.add("Test Facility 2 " + ucNames.get(position));
                    healthFacilityNames.add("Test Facility 3 " + ucNames.get(position));
                    healthFacilityCodes.add(ucCodes.get(position) + "001");
                    healthFacilityCodes.add(ucCodes.get(position) + "002");
                    healthFacilityCodes.add(ucCodes.get(position) + "003");
                }
                // Apply the adapter to the spinner
                bi.va02a.setAdapter(new ArrayAdapter<>(SectionVAActivity.this, R.layout.custom_spinner, healthFacilityNames));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void previousPage(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(bi.va04.getText()))
            return;

        /*if (bi.va05acheck.isChecked()){
            bi.va05ax.setText(form.getVa04());
        }*/

    }


    private boolean insertNewRecord() {
        if (!formVA.getUid().equals("") || MainApp.superuser) return true;

        formVA.populateMeta();

        long rowId = 0;
        try {
            rowId = db.addFormVA(formVA);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        formVA.setId(String.valueOf(rowId));
        if (rowId > 0) {
            formVA.setUid(formVA.getDeviceId() + formVA.getId());
            db.updatesFormVAColumn(TableContracts.FormsVATable.COLUMN_UID, formVA.getUid());
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private boolean updateDB() {
        if (MainApp.superuser) return true;

        int updcount = 0;
        try {
            updcount = db.updatesFormVAColumn(TableContracts.FormsVATable.COLUMN_VA, formVA.vAtoString());

        } catch (JSONException e) {
            Toast.makeText(this, R.string.upd_db + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void btnContinue(View view) {
        //MainApp.flagVA = true;
        if (!formValidation()) return;
        if (!insertNewRecord()) return;

        if (insertNewRecord()) setCurrentDate();
        setCurrentDate();
        if (updateDB()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, R.string.fail_db_upd, Toast.LENGTH_SHORT).show();
        }
    }


    public void btnEnd(View view) {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
        /*if (!Validator.emptyCheckingContainer(this, bi.GrpName))
            return false;

        if (bi.va05a.isChecked() && bi.va05ax.getText().toString().isEmpty()) {
            return Validator.emptyCustomTextBox(this, bi.va05ax, "Empty");
        }

        if (bi.va05b.isChecked() && bi.va05bx.getText().toString().isEmpty()) {
            return Validator.emptyCustomTextBox(this, bi.va05bx, "Empty");
        }

        return true;*/
    }

    private void setCurrentDate() {
        MainApp.editor.putString("batchManagementUID", formVA.getUid());
        MainApp.editor.putString("batchManagementDate", formVA.getSysDate());
        MainApp.editor.apply();

    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
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

            formVA.setGpsLat(lat);
            formVA.setGpsLng(lang);
            formVA.setGpsAcc(acc);
            formVA.setGpsDT(date); // Timestamp is converted to date above

//            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setGPS: " + e.getMessage());
        }

    }

}