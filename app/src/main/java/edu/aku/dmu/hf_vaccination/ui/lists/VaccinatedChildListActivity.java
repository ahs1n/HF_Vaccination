
package edu.aku.dmu.hf_vaccination.ui.lists;

import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccinesData;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccinesDataList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import edu.aku.dmu.hf_vaccination.adapters.GenericRVAdapter;
import edu.aku.dmu.hf_vaccination.adapters.VaccinatedMembersFollowupsAdapter;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivityVaccinatedListChildBinding;
import edu.aku.dmu.hf_vaccination.models.VaccinesData;
import edu.aku.dmu.hf_vaccination.ui.sections.MemberInfoActivity;
import edu.aku.dmu.hf_vaccination.ui.sections.SectionVBActivity;


public class VaccinatedChildListActivity extends AppCompatActivity {

    private static final String TAG = "VaccinationActivity";
    private final int VACC_CHILD_RV = 102;
    GenericRVAdapter<VaccinesData> genericRVAdapter;
    ActivityVaccinatedListChildBinding bi;
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

                        vaccinesDataList.add(MainApp.vaccinesData);

                    }

                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(VaccinatedChildListActivity.this, "No family member added.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    GenericRVAdapter.IRVOnItemClickListener iRVOnItemClickListener = (recyclerView, obj, index) -> {
        int recyclerViewTag = (int) recyclerView.getTag();
        if (recyclerViewTag == VACC_CHILD_RV) {
            VaccinesData childVacc = (VaccinesData) obj;
            try {
                vaccinesData = db.getFollowupSelectedMembers(childVacc.getUID(), childVacc.getVBO2());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(this, SectionVBActivity.class).putExtra("woman", false));
        }
    };
    private VaccinatedMembersFollowupsAdapter vaccinatedMembersAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_mwra);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_vaccinated_list_child);
        bi.setCallback(this);
        db = MainApp.appInfo.dbHelper;
        vaccinesDataList = db.getAllFollowupChilds();

        initVacChildRV();

        initSearchFilter();

        bi.rvMember.setLayoutManager(new LinearLayoutManager(this));

        bi.fab.setOnClickListener(view -> {
//            MainApp.formVB = new FormVB();
            addMoreMember();
        });


        bi.searchBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.searchByName.isChecked()) {
                    bi.memberId.setHint("Name");
                } else {
                    bi.memberId.setHint("Card No.");
                }
            }
        });

    }

    private void initVacChildRV() {
        genericRVAdapter = new GenericRVAdapter<VaccinesData>(this,
                vaccinesDataList, bi.rvMember, iRVOnItemClickListener, false) {

            @Override
            protected View createView(Activity activity, ViewGroup viewGroup, int viewType) {
                return LayoutInflater.from(activity)
                        .inflate(R.layout.vaccinated_member_row, viewGroup, false);
            }

            @Override
            protected void bindView(VaccinesData item, GenericRVAdapter.ViewHolder viewHolder, int position, boolean isMultiSelect) {
                if (item != null) {
                    viewHolder.itemView.setTag(position);
                    TextView mName = (TextView) viewHolder.getView(R.id.mName);
                    mName.setText(item.getVB04A());
                    mName.setTag(position);
                    TextView ageY = (TextView) viewHolder.getView(R.id.ageY);
                    ageY.setText(String.format("%s DOB", item.getDob()));
                    TextView fName = (TextView) viewHolder.getView(R.id.fName);
                    fName.setText(item.getVB04());
                    TextView cardNo = (TextView) viewHolder.getView(R.id.cardNo);
                    cardNo.setText(item.getVBO2());
                    ImageView iv = (ImageView) viewHolder.getView(R.id.mainIcon);
                    iv.setImageResource(item.getVBO5A().equals("1") ?
                            R.drawable.malebabyicon : R.drawable.femalebabyicon);

                }
            }
        };


        bi.rvMember.setTag(VACC_CHILD_RV);
        bi.rvMember.setAdapter(genericRVAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
        Toast.makeText(this, "Activity Resumed!", Toast.LENGTH_SHORT).show();
        //MainApp.formVB.setUuid(MainApp.formVA.getUid());
//        formVA = new FormVA();
        if (MainApp.formVA.getUid().equals("") || MainApp.formVA.getUid() == null) {
            try {
                MainApp.formVA = db.getFormByuid(MainApp.formVA.getUid());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void addMoreMember() {
//        MainApp.formVB = new FormVB();
        Intent intent = new Intent(this, MemberInfoActivity.class);
        finish();
        MemberInfoLauncher.launch(intent);
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


    // Search Filter
    private void initSearchFilter() {
        bi.memberId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                genericRVAdapter.filter(s.toString(), vaccinesData -> (vaccinesData.getVBO2().toLowerCase().contains(s.toString())
                        || vaccinesData.getVB04A().toLowerCase().contains(s.toString())));
            }
        });

        bi.memberId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //genericRVAdapter.filter(v.getText().toString());
                return true;
            }
        });
    }
}
