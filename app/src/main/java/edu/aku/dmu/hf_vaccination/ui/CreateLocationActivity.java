package edu.aku.dmu.hf_vaccination.ui;

import static edu.aku.dmu.hf_vaccination.core.MainApp.workLocation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivityCreateLocationBinding;
import edu.aku.dmu.hf_vaccination.models.HealthFacilities;
import edu.aku.dmu.hf_vaccination.models.Villages;
import edu.aku.dmu.hf_vaccination.models.WorkLocation;

public class CreateLocationActivity extends AppCompatActivity {
    private static final String TAG = "CreateLocationActivity";
    ActivityCreateLocationBinding bi;
    private DatabaseHelper db;
    private ArrayList<String> facilityNames, facilityCodes, villageNames, villageCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_create_location);
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;
        workLocation = new WorkLocation();
        bi.setWorkLoc(workLocation);
        setGPS();

        bi.attendat.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.attendat01.getId()) {
                populateFacilitySpinner();
                villageCodes = new ArrayList<>();
                villageNames = new ArrayList<>();

                bi.wlVillageName.setAdapter(null);
            }

            if (i == bi.attendat02.getId()) {
                populateVillageSpinner();

                facilityCodes = new ArrayList<>();
                facilityNames = new ArrayList<>();
                bi.wlFacilityName.setAdapter(null);
            }
        });


    }


    private void populateFacilitySpinner() {

        Collection<HealthFacilities> healthFacility = db.getHealthFacilityByUC(MainApp.user.getUccode());

        facilityNames = new ArrayList<>();
        facilityCodes = new ArrayList<>();
        facilityNames.add("...");
        facilityCodes.add("...");

        for (HealthFacilities hf : healthFacility) {
            facilityNames.add(hf.getHfName());
            facilityCodes.add(hf.getHfCode());
        }

        if (MainApp.user.getUserName().contains("test") || MainApp.user.getUserName().contains("dmu") || MainApp.user.getUserName().contains("user")) {
            facilityNames.add("Test Facility 1");
            facilityNames.add("Test Facility 2");
            facilityNames.add("Test Facility 3");

            facilityCodes.add("001");
            facilityCodes.add("002");
            facilityCodes.add("003");
        }
        // Apply the adapter to the spinner
        //    bi.wlFacilityName.setAdapter(new ArrayAdapter<>(CreateLocationActivity.this, R.layout.custom_spinner, facilityNames));
        bi.wlFacilityName.setAdapter(new ArrayAdapter<>(CreateLocationActivity.this, R.layout.custom_spinner, facilityNames));


        bi.wlFacilityName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    workLocation.setWlFacilityCode(facilityCodes.get(bi.wlFacilityName.getSelectedItemPosition()));
                    workLocation.setWlFacilityName(facilityNames.get(bi.wlFacilityName.getSelectedItemPosition()));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }

    private void populateVillageSpinner() {

        Collection<Villages> villages = db.getAllVillagesByUC(MainApp.user.getUccode());

        villageNames = new ArrayList<>();
        villageCodes = new ArrayList<>();
        villageNames.add("...");
        villageCodes.add("...");

        for (Villages vg : villages) {
            villageNames.add(vg.getVillageName());
            villageCodes.add(vg.getVillageCode());
        }

        if (MainApp.user.getUserName().contains("test") || MainApp.user.getUserName().contains("dmu") || MainApp.user.getUserName().contains("user")) {
            villageNames.add("Test Village 1");
            villageNames.add("Test Village 2");
            villageNames.add("Test Village 3");

            villageCodes.add("001");
            villageCodes.add("002");
            villageCodes.add("003");
        }

        /*// Apply the adapter to the spinner
        bi.wlVillageName.setAdapter(new ArrayAdapter<>(CreateLocationActivity.this, R.layout.custom_spinner, villageNames));

        bi.wlVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    workLocation.setWlVillageCode(villageCodes.get(bi.wlVillageName.getSelectedItemPosition()));
                    workLocation.setWlVillageName(villageNames.get(bi.wlVillageName.getSelectedItemPosition()));

//                    MainApp.selectedVillageCode = (villageCodes.get(bi.wlVillageName.getSelectedItemPosition()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });*/


        // Hide certain element in the spinner

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, villageNames) {

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View v = null;

                if (bi.attendat01.isChecked() && villageCodes.contains("99")) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    v = super.getDropDownView(position, null, parent);
                }


                return v;
            }
        };

        dataAdapter.setDropDownViewResource(R.layout.custom_spinner);


        bi.wlVillageName.setAdapter(dataAdapter);


    }


    private boolean insertNewRecord() {
        if (!workLocation.getUid().equals("") || MainApp.superuser) return true;
        workLocation.populateMeta();

        long rowId = 0;
        try {
            rowId = db.addWorkLocation(workLocation);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        workLocation.setId(String.valueOf(rowId));
        if (rowId > 0) {
            workLocation.setUid(workLocation.getDeviceId() + workLocation.getId());
            db.updatesWorkLocationColumn(TableContracts.WorkLocationTable.COLUMN_UID, workLocation.getUid());
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


/*    private boolean updateDB() {
        if (MainApp.superuser) return true;

        int updcount = 0;
        try {
            updcount = db.updatesAttendanceColumn(TableContracts.WorkLocationTable.COLUMN_SWL, workLocation.aTTtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, R.string.upd_db + e.getMessage());
            Toast.makeText(this, R.string.upd_db + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }*/


    public void btnContinue(View view) {
        if (!formValidation()) return;


        if (insertNewRecord()) {
            setCurrentWorkLocation();
            finish();
            //    startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, R.string.fail_db_upd, Toast.LENGTH_SHORT).show();
        }
    }

    private void setCurrentWorkLocation() {
        MainApp.editor.putString("workLocationUID", workLocation.getUid());
        MainApp.editor.putString("workLocationDate", workLocation.getSysDate());
        MainApp.editor.apply();
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        finish();
        //   startActivity(new Intent(this, LoginActivity.class));
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

            workLocation.setGpsLat(lat);
            workLocation.setGpsLng(lang);
            workLocation.setGpsAcc(acc);
            workLocation.setGpsDT(date); // Timestamp is converted to date above

//            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setPoints: " + e.getMessage());
        }

    }
}