package edu.aku.dmu.hf_vaccination;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivityDailySummaryBinding;

public class DailySummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private static String selectedDate;
    ActivityDailySummaryBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_daily_summary);

        db = MainApp.appInfo.dbHelper;

        initSummaryUI();
    }

    // Initialize summary UI
    private void initSummaryUI() {
        bi.todaysChip.setOnClickListener(this);
        bi.byDateChip.setOnClickListener(this);

        // By default selection
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date().getTime());
        initSummaryData();
    }

    // Initialize summary Data
    private void initSummaryData() {
        int total = 0;

        // Children Vaccinated
        int totalChild = db.getChildrenVaccinatedByDate(selectedDate);
        bi.totalChildAnt.setText(String.valueOf(totalChild));

        // BCG
        List<String> bcg = db.getVaccinesByAntigenCodeAndDate("BCG", selectedDate);
        bi.bcgAnt.setText(String.valueOf(bcg.size()));
        total += bcg.size();

        // OPV
        List<String> opv = db.getVaccinesByAntigenCodeAndDate("OPV", selectedDate);
        bi.opvAnt0.setText(String.valueOf(Collections.frequency(opv, "OPV1")));
        bi.opvAnt1.setText(String.valueOf(Collections.frequency(opv, "OPV2")));
        bi.opvAnt2.setText(String.valueOf(Collections.frequency(opv, "OPV3")));
        bi.opvAnt3.setText(String.valueOf(Collections.frequency(opv, "OPV4")));
        total += opv.size();

        // HepB
        List<String> hepB = db.getVaccinesByAntigenCodeAndDate("HepB", selectedDate);
        bi.hepBAnt.setText(String.valueOf(hepB.size()));
        total += hepB.size();

        // Penta
        List<String> penta = db.getVaccinesByAntigenCodeAndDate("Penta", selectedDate);
        bi.pentaAnt1.setText(String.valueOf(Collections.frequency(penta, "Penta1")));
        bi.pentaAnt2.setText(String.valueOf(Collections.frequency(penta, "Penta2")));
        bi.pentaAnt3.setText(String.valueOf(Collections.frequency(penta, "Penta3")));
        total += penta.size();

        // PCV
        List<String> pcv = db.getVaccinesByAntigenCodeAndDate("PCV", selectedDate);
        bi.pcvAnt1.setText(String.valueOf(Collections.frequency(pcv, "PCV1")));
        bi.pcvAnt2.setText(String.valueOf(Collections.frequency(pcv, "PCV2")));
        bi.pcvAnt3.setText(String.valueOf(Collections.frequency(pcv, "PCV3")));
        total += pcv.size();

        // Rota
        List<String> rota = db.getVaccinesByAntigenCodeAndDate("Rota", selectedDate);
        bi.rotaAnt1.setText(String.valueOf(Collections.frequency(rota, "Rota1")));
        bi.rotaAnt2.setText(String.valueOf(Collections.frequency(rota, "Rota2")));
        total += rota.size();

        // IPV
        List<String> ipv = db.getVaccinesByAntigenCodeAndDate("IPV", selectedDate);
        bi.ipvAnt1.setText(String.valueOf(Collections.frequency(ipv, "IPV1")));
        bi.ipvAnt2.setText(String.valueOf(Collections.frequency(ipv, "IPV2")));
        total += ipv.size();

        // Measles
        List<String> measles = db.getVaccinesByAntigenCodeAndDate("Measles", selectedDate);
        bi.measlesAnt1.setText(String.valueOf(Collections.frequency(measles, "Measles1")));
        bi.measlesAnt2.setText(String.valueOf(Collections.frequency(measles, "Measles2")));
        total += measles.size();

        // Typhoid
        List<String> typhoid = db.getVaccinesByAntigenCodeAndDate("Typhoid", selectedDate);
        bi.typhoidAnt.setText(String.valueOf(typhoid.size()));
        total += typhoid.size();

        // Total
        bi.totalAnt.setText(String.valueOf(total));

    }

    // Show date picker
    private void showDatePicker() {
        Calendar c = Calendar.getInstance();

        // Current date
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.Theme_HF_Vaccination_DatePickerStyle,
                (datePicker, year1, monthOfYear, dayOfMonth) -> {
                    selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
                    initSummaryData();
                    bi.filterTitleTV.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (view instanceof Chip) {
            if (viewId == bi.todaysChip.getId()) {
                // Today's Summary
                bi.todaysChip.setChecked(true);
                bi.byDateChip.setChecked(false);
                selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date().getTime());
                bi.filterTitleTV.setText(getString(R.string.todays_vaccinations));
                initSummaryData();
            } else if (viewId == bi.byDateChip.getId()) {
                // By Date Summary
                bi.todaysChip.setChecked(false);
                bi.byDateChip.setChecked(true);
                showDatePicker();
            }
        }
    }
}