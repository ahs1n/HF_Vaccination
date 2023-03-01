package edu.aku.dmu.hf_vaccination.ui.lists;

import static edu.aku.dmu.hf_vaccination.core.MainApp.formVA;
import static edu.aku.dmu.hf_vaccination.core.MainApp.formVB;
import static edu.aku.dmu.hf_vaccination.core.MainApp.formVBList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONException;

import edu.aku.dmu.hf_vaccination.MainActivity;
import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.adapters.VaccinatedMembersAdapter;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivityRegisteredListWomenBinding;
import edu.aku.dmu.hf_vaccination.models.FormVA;
import edu.aku.dmu.hf_vaccination.ui.sections.MemberInfoActivity;
import edu.aku.dmu.hf_vaccination.ui.sections.SectionVBActivity;


public class RegisteredWomenListActivity extends AppCompatActivity {


    private static final String TAG = "VaccinationActivity";
    ActivityRegisteredListWomenBinding bi;
    DatabaseHelper db;
    ActivityResultLauncher<Intent> MemberInfoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && !MainApp.superuser) {
                        // There are no request codes
                        //Intent data = result.getData();
                        Intent data = result.getData();

                        formVBList.add(MainApp.formVB);

                    }

                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(RegisteredWomenListActivity.this, "No member added.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    private VaccinatedMembersAdapter vaccinatedMembersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_mwra);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_registered_list_women);
        bi.setCallback(this);
        db = MainApp.appInfo.dbHelper;
        formVBList = db.getAllWomens();


        vaccinatedMembersAdapter = new VaccinatedMembersAdapter(this, formVBList, member -> {
            try {
                formVB = db.getSelectedMembers(member.getUid());
                Toast.makeText(RegisteredWomenListActivity.this,
                        "Selected Member\n Line No: "
                                + member.getVb02() + "\nName: "
                                + member.getVb04a(),
                        Toast.LENGTH_LONG).show();
                RegisteredWomenListActivity.this.startActivity(new Intent(RegisteredWomenListActivity.this, SectionVBActivity.class).putExtra("b", false));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        bi.rvMember.setAdapter(vaccinatedMembersAdapter);

        vaccinatedMembersAdapter.notifyDataSetChanged();
        bi.rvMember.setLayoutManager(new LinearLayoutManager(this));

        bi.fab.setOnClickListener(view -> {
            addMoreMember();
        });
    }

/*    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();

    }*/

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();
        //MainApp.formVB.setUuid(MainApp.formVA.getUid());
        formVA = new FormVA();
        if (MainApp.formVA.getUid().equals("")) {
            try {
                MainApp.formVA = db.getFormByuid(MainApp.formVA.getUid());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addMoreMember() {
        Intent intent = new Intent(this, MemberInfoActivity.class).putExtra("group", false);
        finish();
        MemberInfoLauncher.launch(intent);
    }

    public void filterForms(View view) {

        if (bi.searchByName.isChecked()) {
            Toast.makeText(this, "Searched", Toast.LENGTH_SHORT).show();

            formVBList = db.getAllWomensByName(bi.memberId.getText().toString());
            vaccinatedMembersAdapter = new VaccinatedMembersAdapter(this, formVBList, member -> {

                try {
                    formVB = db.getSelectedMembers(member.getUid());
                    Toast.makeText(RegisteredWomenListActivity.this,
                            "Selected Member\n Line No: "
                                    + member.getVb02() + "\nName: "
                                    + member.getVb04a(),
                            Toast.LENGTH_LONG).show();
                    RegisteredWomenListActivity.this.startActivity(new Intent(RegisteredWomenListActivity.this, SectionVBActivity.class).putExtra("b", false));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            vaccinatedMembersAdapter.notifyDataSetChanged();
            bi.rvMember.setAdapter(vaccinatedMembersAdapter);
        } else {
            Toast.makeText(this, "Searched", Toast.LENGTH_SHORT).show();

            formVBList = db.getAllWomensByCardNo(bi.memberId.getText().toString());
            vaccinatedMembersAdapter = new VaccinatedMembersAdapter(this, formVBList, member -> {

                try {
                    formVB = db.getSelectedMembers(member.getUid());
                    Toast.makeText(RegisteredWomenListActivity.this,
                            "Selected Member\n Line No: "
                                    + member.getVb02() + "\nName: "
                                    + member.getVb04a(),
                            Toast.LENGTH_LONG).show();
                    RegisteredWomenListActivity.this.startActivity(new Intent(RegisteredWomenListActivity.this, SectionVBActivity.class).putExtra("b", false));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            vaccinatedMembersAdapter.notifyDataSetChanged();
            bi.rvMember.setAdapter(vaccinatedMembersAdapter);
        }

    }

    public void btnEnd(View view) {

        finish();
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}