package edu.aku.dmu.hf_vaccination.ui;

import static edu.aku.dmu.hf_vaccination.core.MainApp.cr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import edu.aku.dmu.hf_vaccination.MainActivity;
import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivityEndingBinding;


public class EndingActivity extends AppCompatActivity {

    ActivityEndingBinding bi;
    int sectionMainCheck;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending);
//        bi.setFormcr);
        setSupportActionBar(bi.toolbar);
        setSupportActionBar(bi.toolbar);
        //setTitle(R.string.section1_mainheading);

        db = MainApp.appInfo.dbHelper;
        boolean check = getIntent().getBooleanExtra("complete", false);
        //sectionMainCheck = getIntent().getIntExtra("status", 0);


        bi.istatusa.setEnabled(check && !cr.getiStatus().equals("9")); // form is complete and  patient not on hold
        bi.istatusb.setEnabled(!check);
        bi.istatusc.setEnabled(check && cr.getiStatus().equals("9")); // form is complete and  patient not on hold


    }

    private void saveDraft() {
        cr.setiStatus(bi.istatusa.isChecked() ? "1"
                : bi.istatusb.isChecked() ? "2"
                : bi.istatusc.isChecked() ? "9"
                : "-1");
        // form.setEndTime(new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH).format(new Date().getTime()));
    }


    public void BtnEnd(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (UpdateDB()) {

            cleanupProcess();
            finish();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this, "Data has been updated.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error in updating Database.", Toast.LENGTH_SHORT).show();
        }
    }


    private void cleanupProcess() {
        cr = null;
    }


    private boolean UpdateDB() {
        int updcount = db.updateCrColumn(TableContracts.FormCRTable.COLUMN_ISTATUS, cr.getiStatus());
        return updcount > 0;
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpEnd);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
    }

}
