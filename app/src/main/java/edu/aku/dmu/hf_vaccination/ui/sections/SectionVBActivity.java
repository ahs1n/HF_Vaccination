package edu.aku.dmu.hf_vaccination.ui.sections;

import static edu.aku.dmu.hf_vaccination.core.MainApp.dueDates;
import static edu.aku.dmu.hf_vaccination.core.MainApp.flag;
import static edu.aku.dmu.hf_vaccination.core.MainApp.formVB;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccDueDates;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccineCount;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccines;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccinesData;
import static edu.aku.dmu.hf_vaccination.core.MainApp.vaccinesDataList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;

import java.util.ArrayList;

import edu.aku.dmu.hf_vaccination.MainActivity;
import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts;
import edu.aku.dmu.hf_vaccination.contracts.TableContracts.FormsVBTable;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivitySectionVbBinding;
import edu.aku.dmu.hf_vaccination.models.VaccDueDates;
import edu.aku.dmu.hf_vaccination.models.VaccinesData;
import edu.aku.dmu.hf_vaccination.models.VaccinesSchedule;
import edu.aku.dmu.hf_vaccination.ui.TakePhoto;

public class SectionVBActivity extends AppCompatActivity {
    private static final String TAG = "SectionVBActivity";
    ActivitySectionVbBinding bi;
    boolean btn, group, b, woman;
    private DatabaseHelper db;
    private int currentSelectedRadioButtonId = -1;

    public static char getChar(int i) {
        return i < 0 || i > 25 ? '?' : (char) ('a' + i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeUrdu);
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_vb);
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;

        formVB.setUuid(MainApp.formVA.getUid());
        MainApp.vaccDueDates = new VaccDueDates();

        setupListeners();
        initUI();


    }

    private void initUI() {

        btn = getIntent().getBooleanExtra("btn", true);
        if (btn) bi.btnEnd.setVisibility(View.VISIBLE);

        if (MainApp.flag) {
            bi.pName.setText(formVB.getVb04a());
            bi.hName.setText(formVB.getVb04());
            bi.cardNo.setText(formVB.getVb02());
            bi.dob.setText(String.format("%s-%s-%s", formVB.getVb04by(), formVB.getVb04bm(), formVB.getVb04bd()));

        } else {
            bi.pName.setText(vaccinesData.getVB04A());
            bi.hName.setText(vaccinesData.getVB04());
            bi.cardNo.setText(vaccinesData.getVBO2());
            bi.dob.setText(vaccinesData.getDob());
        }


        // To get the previous data and calculate next dose
        MainApp.vaccinesDataList = new ArrayList<>();
        MainApp.vaccinesSchedule = new VaccinesSchedule();

        vaccineCount = 0;

        String baseId = "";
        Bundle groupBundle;
        ArrayList<Boolean> results = new ArrayList<Boolean>();

        Log.d(TAG, "onCreate(vaccineList): " + vaccinesDataList.size());
        try {

            vaccinesDataList.clear();
            vaccinesDataList = db.getSyncedVaccinatedMembersBYUID(vaccinesData.getUID(), vaccinesData.getVBO2());

            for (VaccinesData vaccines : vaccinesDataList) {

                //BCG - Group 0
                baseId = "vb08ca";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getBcg(), baseId, "a"));
                verifyCrossTicks(results, baseId);
                calculateNextDoseDate(results, baseId);
                groupBundle = new Bundle();
                groupBundle.putString("date", vaccinesData.getDob());
                groupBundle.putInt("group", 0);


                // OPV
                baseId = "vb08cb";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getOpv0(), baseId, "a")); // Group - 0
                results.add(showHideDoneCheckWithText(vaccines.getOpv1(), baseId, "b"));  // Group 1
                results.add(showHideDoneCheckWithText(vaccines.getOpv2(), baseId, "c"));    // Group 2
                results.add(showHideDoneCheckWithText(vaccines.getOpv3(), baseId, "d"));    // Group 3
                verifyCrossTicks(results, baseId);
                groupBundle = calculateNextDoseDate(results, baseId);


                //Hep B   Group 0
                baseId = "vb08cc";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getHepB(), baseId, "a"));
                verifyCrossTicks(results, baseId);
                calculateNextDoseDate(results, baseId);
                /*groupBundle = new Bundle();
                groupBundle.putString("date", vaccinesData.getDob());
                groupBundle.putInt("group", 0);*/

                // Penta
                baseId = "vb08cd";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getPenta1(), baseId, "a"));  // Group 1
                results.add(showHideDoneCheckWithText(vaccines.getPenta2(), baseId, "b"));  // Group 2
                results.add(showHideDoneCheckWithText(vaccines.getPenta3(), baseId, "c"));  // Group 3
                verifyCrossTicks(results, baseId);
                groupBundle = calculateNextDoseDate(results, baseId, groupBundle);

                // PCV
                baseId = "vb08ce";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getPcv1(), baseId, "a"));    // Group 1
                results.add(showHideDoneCheckWithText(vaccines.getPcv2(), baseId, "b"));    // Group 2
                results.add(showHideDoneCheckWithText(vaccines.getPcv3(), baseId, "c"));    // Group 3
                verifyCrossTicks(results, baseId);
                groupBundle = calculateNextDoseDate(results, baseId, groupBundle);

                // Rota
                baseId = "vb08cf";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getRota1(), baseId, "a"));   // Group 1
                results.add(showHideDoneCheckWithText(vaccines.getRota2(), baseId, "b"));   // Group 2
                verifyCrossTicks(results, baseId);
                groupBundle = calculateNextDoseDate(results, baseId, groupBundle);


                // IPV
                baseId = "vb08cg";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getIpv1(), baseId, "a"));    // Group 3
                results.add(showHideDoneCheckWithText(vaccines.getIpv2(), baseId, "b"));    // Group 4
                verifyCrossTicks(results, baseId);
                groupBundle = calculateNextDoseDate(results, baseId, groupBundle);
                /*groupBundle = new Bundle();
                groupBundle.putInt("group", 2);*/

                // Measles
                baseId = "vb08ch";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getMeasles1(), baseId, "a"));       // Group 4
                results.add(showHideDoneCheckWithText(vaccines.getMeasles2(), baseId, "b"));        // Group 5
                verifyCrossTicks(results, baseId);
                groupBundle = calculateNextDoseDate(results, baseId, groupBundle);

                // Typhoid
                baseId = "vb08ci";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getTyphoid(), baseId, "a"));     // Group 4
                verifyCrossTicks(results, baseId);
                groupBundle = calculateNextDoseDate(results, baseId, groupBundle);

                // Typhoid
                baseId = "vb08cj";
                results.clear();
                results.add(showHideDoneCheckWithText(vaccines.getDpt(), baseId, "a"));     // Group 4
                verifyCrossTicks(results, baseId);
                groupBundle = calculateNextDoseDate(results, baseId, groupBundle);


            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(FormVB): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (vaccinesDataList.size() == 19) {
            bi.vb08ca98.setEnabled(false);
            bi.vb08cb98.setEnabled(false);
            bi.vb08cc98.setEnabled(false);
            bi.vb08cd98.setEnabled(false);
            bi.vb08ce98.setEnabled(false);
            bi.vb08cf98.setEnabled(false);
            bi.vb08cg98.setEnabled(false);
            bi.vb08ch98.setEnabled(false);
            bi.vb08ci98.setEnabled(false);
            bi.btnContinue.setVisibility(View.GONE);
        } else {
            formVB.setFrontfilename("");
            formVB.setBackfilename("");
            formVB.setChildfilename("");
        }
        bi.setForm(formVB);
    }

    private void setupListeners() {

        bi.vb08ca.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08cadt));
        bi.vb08cb.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08cbdt));
        bi.vb08cc.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08ccdt));
        bi.vb08cd.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08cddt));
        bi.vb08ce.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08cedt));
        bi.vb08cf.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08cfdt));
        bi.vb08cg.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08cgdt));
        bi.vb08ch.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08chdt));
        bi.vb08ci.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08cidt));
        bi.vb08cj.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.vb08cjdt));

        // OnCheckChange Listeners
        onCheckChanged(bi.vb08cb, bi.vb08cbdt);
        onCheckChanged(bi.vb08cd, bi.vb08cddt);
        onCheckChanged(bi.vb08ce, bi.vb08cedt);
        onCheckChanged(bi.vb08cf, bi.vb08cfdt);
        onCheckChanged(bi.vb08cg, bi.vb08cgdt);
        onCheckChanged(bi.vb08ch, bi.vb08chdt);

        // Text Watchers

        // BCG date
        bi.vb08cadt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                MainApp.dueDates = new VaccDueDates();

                String baseId = "vb08ca";
                String[] nextBaseId = {"vb08cb", "vb08cd", "vb08ce", "vb08cf"};
                String userSelectedDate = editable.toString();
                TextView nextVaccineDate = null;

                if (bi.vb08caa.isChecked()) {
                    int doseNumber = 0;
                    int previousGroup = 1;
                    int currentGroup = 0;
                    String letter = String.valueOf(getChar(0));

                    for (String s : nextBaseId) {

                        RadioButton radioButton = (RadioButton) getViewDynamically(s + letter);
                        nextVaccineDate = (TextView) getViewDynamically(s + letter + "txt");

                        //if (nextVaccineDate.getText().toString().equals("")) {
                        int[] groupDays = new int[0];

                        if (radioButton != null && nextVaccineDate != null) {
                            groupDays = getDaysAndGroupOfVaccineType(s, -1);
                        }

                        currentGroup = groupDays[1];

                        if (currentGroup == previousGroup) {

                            String prevDateStr = userSelectedDate;

                            if (!prevDateStr.equals("")) {
                                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                                DateTime prevDate = fmt.parseDateTime(prevDateStr);

                                //int days = getDaysOfVaccineType(baseId, doseNumber);
                                int[] days = getDaysAndGroupOfVaccineType(s, -1);
                                DateTime nextDate = prevDate.plusDays(days[0]);
                                nextVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                                nextVaccineDate.setVisibility(View.VISIBLE);

                                /*Saving Vaccines Due Dates*/
                                if (flag) {
                                    vaccDueDates.populateMeta();
                                } else {
                                    vaccDueDates.populateMetaFollowUp();
                                }
                                MainApp.vaccDueDates.setVb08CDueDate(nextVaccineDate.getText().toString());
                                MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(s));
                                MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));

                                try {
                                    MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                            vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (MainApp.dueDates.getUid().equals("")) {
                                    insertDueVaccines();
                                } else {
                                    updateDueVaccines();
                                }
                            }
                        }
                    }
                }
            }
        });

        // OPV date
        bi.vb08cbdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                MainApp.dueDates = new VaccDueDates();
                TextView nextVaccineDate = null;

                if (currentSelectedRadioButtonId != -1) {
                    String baseId = "vb08cb";
                    String[] nextBaseId = {"vb08cd", "vb08ce", "vb08cf"};
                    String userSelectedDate = editable.toString();

                    int doseNumber = 0;
                    int previousGroup = 0;
                    int currentGroup = 0;
                    for (int i = 0; i < bi.vb08cb.getChildCount(); i++) {
                        View currentView = bi.vb08cb.getChildAt(i);
                        if (currentView instanceof RadioButton) {
                            doseNumber++;
                            previousGroup++;
                        }

                        if (currentSelectedRadioButtonId == currentView.getId()) {
                            for (int j = i; j < bi.vb08cbll.getChildCount(); j++) {
                                View possibleTextView = null;
                                if (i == 1) {
                                    possibleTextView = bi.vb08cbll.getChildAt(j + 2);
                                } else {
                                    possibleTextView = bi.vb08cbll.getChildAt(j);
                                }
                                if (possibleTextView != null && possibleTextView instanceof TextView) {
                                    TextView txtVaccineDate = (TextView) possibleTextView;
                                    if (txtVaccineDate != null) {
                                        // This is the next TextView
                                        String prevDateStr = userSelectedDate;

                                        if (!prevDateStr.equals("")) {
                                            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                                            DateTime prevDate = fmt.parseDateTime(prevDateStr);

                                            //int days = getDaysOfVaccineType(baseId, doseNumber);
                                            int[] days = getDaysAndGroupOfVaccineType(baseId, doseNumber);
                                            DateTime nextDate = prevDate.plusDays(days[0]);
                                            if (doseNumber < 4) {
                                                txtVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                                                txtVaccineDate.setVisibility(View.VISIBLE);
                                            } else {
                                                txtVaccineDate.setText("");
                                            }

                                            /*Saving Vaccines Due Dates*/
                                            if (flag) {
                                                vaccDueDates.populateMeta();
                                            } else {
                                                vaccDueDates.populateMetaFollowUp();
                                            }
                                            MainApp.vaccDueDates.setVb08CDueDate(txtVaccineDate.getText().toString());
                                            MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(baseId));
                                            MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                                            try {
                                                MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                        vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if (!txtVaccineDate.getText().toString().equals("")) {
                                                if (dueDates.getUid().equals("")) {
                                                    insertDueVaccines();
                                                } else {
                                                    updateDueVaccines();
                                                }
                                            }

                                            if(bi.vb08cdatxt.getText().toString().equals("") || bi.vb08ceatxt.getText().toString().equals("") || bi.vb08cfatxt.getText().toString().equals("")) {
                                                String letter = String.valueOf(getChar(0));

                                                for (String s : nextBaseId) {

                                                    int currentDose = -1;

                                                    RadioButton radioButton = (RadioButton) getViewDynamically(s + letter);
                                                    nextVaccineDate = (TextView) getViewDynamically(s + letter + "txt");

                                                    //if (nextVaccineDate.getText().toString().equals("")) {
                                                    int[] groupDays = new int[0];

                                                    if (radioButton != null && nextVaccineDate != null) {
                                                        groupDays = getDaysAndGroupOfVaccineType(s, -1);
                                                    }

                                                    currentGroup = groupDays[1];

                                                    if (currentGroup == previousGroup) {
                                                        nextVaccineDate.setText(txtVaccineDate.getText().toString());
                                                        nextVaccineDate.setVisibility(View.VISIBLE);

                                                        /*Saving Vaccines Due Dates*/
                                                        if (flag) {
                                                            vaccDueDates.populateMeta();
                                                        } else {
                                                            vaccDueDates.populateMetaFollowUp();
                                                        }

                                                        MainApp.vaccDueDates.setVb08CDueDate(nextVaccineDate.getText().toString());
                                                        MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(s));
                                                        MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(currentDose + 2));

                                                        try {
                                                            MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                                    vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (!nextVaccineDate.getText().toString().equals("")) {
                                                            if (dueDates.getUid().equals("")) {
                                                                insertDueVaccines();
                                                            } else {
                                                                updateDueVaccines();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        });

        // Penta
        bi.vb08cddt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                MainApp.dueDates = new VaccDueDates();

                if (currentSelectedRadioButtonId != -1) {
                    String userSelectedDate = editable.toString();
                    String baseId = "vb08cd";
                    String[] nextBaseId = {"vb08cg"};
                    int doseNumber = -1;
                    int previousGroup = 0;
                    int currentGroup = 0;
                    TextView nextVaccineDate;
                    for (int i = 0; i < bi.vb08cd.getChildCount(); i++) {
                        View currentView = bi.vb08cd.getChildAt(i);
                        if (currentView instanceof RadioButton) {
                            doseNumber++;
                            previousGroup++;
                        }

                        if (currentSelectedRadioButtonId == currentView.getId()) {
                            for (int j = i; j < bi.vb08cdll.getChildCount(); j++) {
                                View possibleTextView = null;

                                possibleTextView = bi.vb08cdll.getChildAt(j + 1);

                                if (possibleTextView != null && possibleTextView instanceof TextView) {
                                    TextView txtVaccineDate = (TextView) possibleTextView;
                                    if (txtVaccineDate != null) {
                                        // This is the next TextView
                                        String prevDateStr = userSelectedDate;

                                        if (!prevDateStr.equals("")) {
                                            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                                            DateTime prevDate = fmt.parseDateTime(prevDateStr);

                                            int[] days = getDaysAndGroupOfVaccineType(baseId, doseNumber);
                                            DateTime nextDate = prevDate.plusDays(days[0]);
                                            if (doseNumber < 3) {
                                                txtVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                                                txtVaccineDate.setVisibility(View.VISIBLE);
                                            } else {
                                                txtVaccineDate.setText("");
                                            }
                                            /*Saving Vaccines Due Dates*/
                                            if (flag) {
                                                vaccDueDates.populateMeta();
                                            } else {
                                                vaccDueDates.populateMetaFollowUp();
                                            }
                                            MainApp.vaccDueDates.setVb08CDueDate(txtVaccineDate.getText().toString());
                                            MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(baseId));
                                            MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                                            try {
                                                MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                        vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if (!txtVaccineDate.getText().toString().equals("")) {
                                                if (dueDates.getUid().equals("")) {
                                                    insertDueVaccines();
                                                } else {
                                                    updateDueVaccines();
                                                }
                                            }

                                            String letter = String.valueOf(getChar(0));

                                            for (String s : nextBaseId) {

                                                int currentDose = -1;

                                                RadioButton radioButton = (RadioButton) getViewDynamically(s + letter);
                                                nextVaccineDate = (TextView) getViewDynamically(s + letter + "txt");
                                                int[] groupDays = new int[0];

                                                if (radioButton != null && nextVaccineDate != null) {
                                                    groupDays = getDaysAndGroupOfVaccineType(s, -1);
                                                }

                                                currentGroup = groupDays[1];

                                                if (currentGroup == previousGroup) {
                                                    nextVaccineDate.setText(txtVaccineDate.getText().toString());
                                                    nextVaccineDate.setVisibility(View.VISIBLE);

                                                    /*Saving Vaccines Due Dates*/
                                                    if (flag) {
                                                        vaccDueDates.populateMeta();
                                                    } else {
                                                        vaccDueDates.populateMetaFollowUp();
                                                    }
                                                    MainApp.vaccDueDates.setVb08CDueDate(nextVaccineDate.getText().toString());
                                                    MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(s));
                                                    MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(currentDose + 2));
                                                    try {
                                                        MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                                vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    if (!nextVaccineDate.getText().toString().equals("")) {
                                                        if (dueDates.getUid().equals("")) {
                                                            insertDueVaccines();
                                                        } else {
                                                            updateDueVaccines();
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        });

        // PCV
        bi.vb08cedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                MainApp.dueDates = new VaccDueDates();

                if (currentSelectedRadioButtonId != -1) {
                    String userSelectedDate = editable.toString();
                    String baseId = "vb08ce";
                    String[] nextBaseId = {"vb08cg"};
                    int doseNumber = -1;
                    int previousGroup = 0;
                    int currentGroup = 0;
                    TextView nextVaccineDate;
                    for (int i = 0; i < bi.vb08ce.getChildCount(); i++) {
                        View currentView = bi.vb08ce.getChildAt(i);
                        if (currentView instanceof RadioButton) {
                            doseNumber++;
                            previousGroup++;
                        }

                        if (currentSelectedRadioButtonId == currentView.getId()) {
                            for (int j = i; j < bi.vb08cell.getChildCount(); j++) {
                                View possibleTextView = null;

                                possibleTextView = bi.vb08cell.getChildAt(j + 1);

                                if (possibleTextView != null && possibleTextView instanceof TextView) {
                                    TextView txtVaccineDate = (TextView) possibleTextView;
                                    if (txtVaccineDate != null) {
                                        // This is the next TextView
                                        String prevDateStr = userSelectedDate;

                                        if (!prevDateStr.equals("")) {
                                            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                                            DateTime prevDate = fmt.parseDateTime(prevDateStr);

                                            int[] days = getDaysAndGroupOfVaccineType(baseId, doseNumber);
                                            DateTime nextDate = prevDate.plusDays(days[0]);
                                            if (doseNumber < 3) {
                                                txtVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                                                txtVaccineDate.setVisibility(View.VISIBLE);
                                            } else {
                                                txtVaccineDate.setText("");
                                            }

                                            /*Saving Vaccines Due Dates*/
                                            if (flag) {
                                                vaccDueDates.populateMeta();
                                            } else {
                                                vaccDueDates.populateMetaFollowUp();
                                            }
                                            MainApp.vaccDueDates.setVb08CDueDate(txtVaccineDate.getText().toString());
                                            MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(baseId));
                                            MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                                            try {
                                                MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                        vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if (!txtVaccineDate.getText().toString().equals("")) {
                                                if (dueDates.getUid().equals("")) {
                                                    insertDueVaccines();
                                                } else {
                                                    updateDueVaccines();
                                                }
                                            }

                                            String letter = String.valueOf(getChar(0));

                                            for (String s : nextBaseId) {

                                                int currentDose = -1;

                                                RadioButton radioButton = (RadioButton) getViewDynamically(s + letter);
                                                nextVaccineDate = (TextView) getViewDynamically(s + letter + "txt");

                                                //if (nextVaccineDate.getText().toString().equals("")) {
                                                int[] groupDays = new int[0];

                                                if (radioButton != null && nextVaccineDate != null) {
                                                    groupDays = getDaysAndGroupOfVaccineType(s, -1);
                                                }

                                                currentGroup = groupDays[1];

                                                if (currentGroup == previousGroup) {
                                                    nextVaccineDate.setText(txtVaccineDate.getText().toString());
                                                    nextVaccineDate.setVisibility(View.VISIBLE);

                                                    /*Saving Vaccines Due Dates*/
                                                    if (flag) {
                                                        vaccDueDates.populateMeta();
                                                    } else {
                                                        vaccDueDates.populateMetaFollowUp();
                                                    }
                                                    MainApp.vaccDueDates.setVb08CDueDate(nextVaccineDate.getText().toString());
                                                    MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(s));
                                                    MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(currentDose + 2));
                                                    try {
                                                        MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                                vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    if (!nextVaccineDate.getText().toString().equals("")) {
                                                        if (dueDates.getUid().equals("")) {
                                                            insertDueVaccines();
                                                        } else {
                                                            updateDueVaccines();
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        });

        // Rota
        bi.vb08cfdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                MainApp.dueDates = new VaccDueDates();

                if (currentSelectedRadioButtonId != -1) {
                    String userSelectedDate = editable.toString();
                    String baseId = "vb08cf";
                    String[] nextBaseId = {"vb08cg"};
                    int doseNumber = -1;
                    int previousGroup = 0;
                    int currentGroup = 0;
                    TextView nextVaccineDate;
                    for (int i = 0; i < bi.vb08cf.getChildCount(); i++) {
                        View currentView = bi.vb08cf.getChildAt(i);
                        if (currentView instanceof RadioButton) {
                            doseNumber++;
                            previousGroup++;
                        }

                        if (currentSelectedRadioButtonId == currentView.getId()) {
                            for (int j = i; j < bi.vb08cfll.getChildCount(); j++) {
                                View possibleTextView = null;

                                possibleTextView = bi.vb08cfll.getChildAt(j + 1);

                                if (possibleTextView != null && possibleTextView instanceof TextView) {
                                    TextView txtVaccineDate = (TextView) possibleTextView;
                                    if (txtVaccineDate != null) {
                                        // This is the next TextView
                                        String prevDateStr = userSelectedDate;

                                        if (!prevDateStr.equals("")) {
                                            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                                            DateTime prevDate = fmt.parseDateTime(prevDateStr);

                                            int[] days = getDaysAndGroupOfVaccineType(baseId, doseNumber);
                                            DateTime nextDate = prevDate.plusDays(days[0]);
                                            //previousGroup = days[1];
                                            if (doseNumber < 2) {
                                                txtVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                                                txtVaccineDate.setVisibility(View.VISIBLE);
                                            } else {
                                                txtVaccineDate.setText("");
                                            }

                                            /*Saving Vaccines Due Dates*/
                                            if (flag) {
                                                vaccDueDates.populateMeta();
                                            } else {
                                                vaccDueDates.populateMetaFollowUp();
                                            }
                                            MainApp.vaccDueDates.setVb08CDueDate(txtVaccineDate.getText().toString());
                                            MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(baseId));
                                            MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                                            try {
                                                MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                        vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if (!txtVaccineDate.getText().toString().equals("")) {
                                                if (dueDates.getUid().equals("")) {
                                                    insertDueVaccines();
                                                } else {
                                                    updateDueVaccines();
                                                }
                                            }

                                            String letter = String.valueOf(getChar(0));

                                            for (String s : nextBaseId) {

                                                int currentDose = -1;

                                                RadioButton radioButton = (RadioButton) getViewDynamically(s + letter);
                                                nextVaccineDate = (TextView) getViewDynamically(s + letter + "txt");

                                                //if (nextVaccineDate.getText().toString().equals("")) {
                                                int[] groupDays = new int[0];

                                                if (radioButton != null && nextVaccineDate != null) {
                                                    groupDays = getDaysAndGroupOfVaccineType(s, -1);
                                                }

                                                currentGroup = groupDays[1];

                                                if (currentGroup == previousGroup) {
                                                    nextVaccineDate.setText(txtVaccineDate.getText().toString());
                                                    nextVaccineDate.setVisibility(View.VISIBLE);
                                                    /*Saving Vaccines Due Dates*/
                                                    if (flag) {
                                                        vaccDueDates.populateMeta();
                                                    } else {
                                                        vaccDueDates.populateMetaFollowUp();
                                                    }
                                                    MainApp.vaccDueDates.setVb08CDueDate(nextVaccineDate.getText().toString());
                                                    MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(s));
                                                    MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(currentDose + 2));
                                                    try {
                                                        MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                                vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    if (!nextVaccineDate.getText().toString().equals("")) {
                                                        if (dueDates.getUid().equals("")) {
                                                            insertDueVaccines();
                                                        } else {
                                                            updateDueVaccines();
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        });

        // IPV
        bi.vb08cgdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                MainApp.dueDates = new VaccDueDates();

                if (currentSelectedRadioButtonId != -1) {
                    String userSelectedDate = editable.toString();
                    String baseId = "vb08cg";
                    String[] nextBaseId = {"vb08ch", "vb08ci"};
                    int doseNumber = -1;
                    int previousGroup = 2;
                    int currentGroup = 0;
                    TextView nextVaccineDate;
                    for (int i = 0; i < bi.vb08cg.getChildCount(); i++) {
                        View currentView = bi.vb08cg.getChildAt(i);
                        if (currentView instanceof RadioButton) {
                            doseNumber++;
                            previousGroup++;
                        }

                        if (currentSelectedRadioButtonId == currentView.getId()) {
                            for (int j = i; j < bi.vb08cgll.getChildCount(); j++) {
                                View possibleTextView = null;
                                possibleTextView = bi.vb08cgll.getChildAt(j + 1);

                                if (possibleTextView != null && possibleTextView instanceof TextView) {
                                    TextView txtVaccineDate = (TextView) possibleTextView;
                                    if (txtVaccineDate != null) {
                                        // This is the next TextView
                                        String prevDateStr = userSelectedDate;

                                        if (!prevDateStr.equals("")) {
                                            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                                            DateTime prevDate = fmt.parseDateTime(prevDateStr);

                                            int[] days = getDaysAndGroupOfVaccineType(baseId, doseNumber);
                                            DateTime nextDate = prevDate.plusDays(days[0]);
                                            //previousGroup = days[1];
                                            if (doseNumber < 2) {
                                                txtVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                                                txtVaccineDate.setVisibility(View.VISIBLE);
                                            } else {
                                                txtVaccineDate.setText("");
                                            }
                                            /*Saving Vaccines Due Dates*/
                                            if (flag) {
                                                vaccDueDates.populateMeta();
                                            } else {
                                                vaccDueDates.populateMetaFollowUp();
                                            }
                                            MainApp.vaccDueDates.setVb08CDueDate(txtVaccineDate.getText().toString());
                                            MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(baseId));
                                            MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                                            try {
                                                MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                        vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if (!txtVaccineDate.getText().toString().equals("")) {
                                                if (dueDates.getUid().equals("")) {
                                                    insertDueVaccines();
                                                } else {
                                                    updateDueVaccines();
                                                }
                                            }

                                            String letter = String.valueOf(getChar(0));

                                            for (String s : nextBaseId) {

                                                int currentDose = -1;

                                                RadioButton radioButton = (RadioButton) getViewDynamically(s + letter);
                                                nextVaccineDate = (TextView) getViewDynamically(s + letter + "txt");

                                                //if (nextVaccineDate.getText().toString().equals("")) {
                                                int[] groupDays = new int[0];

                                                if (radioButton != null && nextVaccineDate != null) {
                                                    groupDays = getDaysAndGroupOfVaccineType(s, -1);
                                                }

                                                currentGroup = groupDays[1];

                                                if (currentGroup == previousGroup) {
                                                    nextVaccineDate.setText(txtVaccineDate.getText().toString());
                                                    nextVaccineDate.setVisibility(View.VISIBLE);
                                                    /*Saving Vaccines Due Dates*/
                                                    if (flag) {
                                                        vaccDueDates.populateMeta();
                                                    } else {
                                                        vaccDueDates.populateMetaFollowUp();
                                                    }
                                                    MainApp.vaccDueDates.setVb08CDueDate(nextVaccineDate.getText().toString());
                                                    MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(s));
                                                    MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(currentDose + 2));
                                                    try {
                                                        MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                                vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    if (!nextVaccineDate.getText().toString().equals("")) {
                                                        if (dueDates.getUid().equals("")) {
                                                            insertDueVaccines();
                                                        } else {
                                                            updateDueVaccines();
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        });

        // Measles
        bi.vb08chdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                MainApp.dueDates = new VaccDueDates();

                if (currentSelectedRadioButtonId != -1) {
                    String userSelectedDate = editable.toString();
                    String baseId = "vb08ch";
                    String nextBaseId = "vb08cj";
                    int doseNumber = -1;
                    int previousGroup = 3;
                    int currentGroup = 0;
                    TextView nextVaccineDate;
                    for (int i = 0; i < bi.vb08ch.getChildCount(); i++) {
                        View currentView = bi.vb08ch.getChildAt(i);
                        if (currentView instanceof RadioButton) {
                            doseNumber++;
                            previousGroup++;
                        }

                        if (currentSelectedRadioButtonId == currentView.getId()) {
                            for (int j = i; j < bi.vb08chll.getChildCount(); j++) {
                                View possibleTextView = null;

                                possibleTextView = bi.vb08chll.getChildAt(j + 1);

                                if (possibleTextView != null && possibleTextView instanceof TextView) {
                                    TextView txtVaccineDate = (TextView) possibleTextView;
                                    if (txtVaccineDate != null) {
                                        // This is the next TextView
                                        String prevDateStr = userSelectedDate;

                                        if (!prevDateStr.equals("")) {
                                            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                                            DateTime prevDate = fmt.parseDateTime(prevDateStr);

                                            int[] days = getDaysAndGroupOfVaccineType(baseId, doseNumber);
                                            DateTime nextDate = prevDate.plusDays(days[0]);
                                            previousGroup = days[1];
                                            if (doseNumber < 2) {
                                                txtVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                                                txtVaccineDate.setVisibility(View.VISIBLE);
                                            } else {
                                                txtVaccineDate.setText("");
                                            }

                                            /*Saving Vaccines Due Dates*/
                                            if (flag) {
                                                vaccDueDates.populateMeta();
                                            } else {
                                                vaccDueDates.populateMetaFollowUp();
                                            }
                                            MainApp.vaccDueDates.setVb08CDueDate(txtVaccineDate.getText().toString());
                                            MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(baseId));
                                            MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                                            try {
                                                MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                        vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if (!txtVaccineDate.getText().toString().equals("")) {
                                                if (dueDates.getUid().equals("")) {
                                                    insertDueVaccines();
                                                } else {
                                                    updateDueVaccines();
                                                }
                                            }

                                            String letter = String.valueOf(getChar(0));

                                            //for (String s : nextBaseId) {

                                                int currentDose = -1;
                                                DateTime nextDate1 = null;

                                                RadioButton radioButton = (RadioButton) getViewDynamically(nextBaseId + letter);
                                                nextVaccineDate = (TextView) getViewDynamically(nextBaseId + letter + "txt");

                                                //if (nextVaccineDate.getText().toString().equals("")) {
                                                int[] groupDays = new int[0];

                                                if (radioButton != null && nextVaccineDate != null) {
                                                    //groupDays = getDaysAndGroupOfVaccineType(nextBaseId, -1);

                                                    //DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd");
                                                    DateTime prevDate1 = fmt.parseDateTime(prevDateStr);

                                                    groupDays = getDaysAndGroupOfVaccineType(nextBaseId, -1);
                                                    nextDate1 = prevDate1.plusDays(groupDays[0]);
                                                }

                                                currentGroup = groupDays[1];

                                                if (currentGroup == previousGroup) {
                                                    nextVaccineDate.setText(nextDate1.toString("yyyy-MM-dd"));
                                                    nextVaccineDate.setVisibility(View.VISIBLE);
                                                    /*Saving Vaccines Due Dates*/
                                                    if (flag) {
                                                        vaccDueDates.populateMeta();
                                                    } else {
                                                        vaccDueDates.populateMetaFollowUp();
                                                    }
                                                    MainApp.vaccDueDates.setVb08CDueDate(nextVaccineDate.getText().toString());
                                                    MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(nextBaseId));
                                                    MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(currentDose + 2));
                                                    try {
                                                        MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                                                vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    if (!nextVaccineDate.getText().toString().equals("")) {
                                                        if (dueDates.getUid().equals("")) {
                                                            insertDueVaccines();
                                                        } else {
                                                            updateDueVaccines();
                                                        }
                                                   // }
                                                }
                                            }

                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        });
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


    /**
     * This is calculating the next dose of the vaccine (Row in the UI)
     *
     * @param results
     * @param baseId
     */
    private Bundle calculateNextDoseDate(ArrayList<Boolean> results, String baseId, Bundle... previousGroupBundleArr) {
        int firstTrue = results.lastIndexOf(true);
        MainApp.dueDates = new VaccDueDates();

        int group = 0;
        Bundle prevBundle = new Bundle();
        prevBundle.putInt("group", group);
        prevBundle.putString("date", "");

        int previousGroup = 0;
        String previousDate = "";
        if (previousGroupBundleArr.length > 0) {
            prevBundle = previousGroupBundleArr[0];
            previousGroup = previousGroupBundleArr[0].getInt("group");
            previousDate = previousGroupBundleArr[0].getString("date");
        }

        String vaccineType = baseId;
        int doseNumber = firstTrue +1;
        RadioButton radioButton;
        TextView txtVaccineDatePrevious;
        TextView txtVaccineDate;

        if (firstTrue > -1) {
            String prevLetter = String.valueOf(getChar(firstTrue));
            String letter = String.valueOf(getChar(firstTrue + 1));
            if (letter.equals("?") || prevLetter.equals("?"))
                return prevBundle;
            // baseId = opv
            // letter = dose number

            radioButton = (RadioButton) getViewDynamically(baseId + letter);
            txtVaccineDatePrevious = (TextView) getViewDynamically(baseId + prevLetter + "txt");
            txtVaccineDate = (TextView) getViewDynamically(baseId + letter + "txt");

            if (radioButton != null && txtVaccineDate != null && txtVaccineDatePrevious != null) {
                String prevDateStr = txtVaccineDatePrevious.getText().toString();

                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime prevDate = fmt.parseDateTime(prevDateStr);
                int[] daysGroup;
                if (baseId.equals("vb08cb")) {
                    daysGroup = getDaysAndGroupOfVaccineType(vaccineType, doseNumber + 1);
                } else {
                    daysGroup = getDaysAndGroupOfVaccineType(vaccineType, doseNumber);
                }
                DateTime nextDate = prevDate.plusDays(daysGroup[0]);

                txtVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                txtVaccineDate.setVisibility(View.VISIBLE);

                if (!txtVaccineDate.getText().toString().equals("")) {
                    /*Saving Vaccines Due Dates*/
                    if (flag) {
                        vaccDueDates.populateMeta();
                    } else {
                        vaccDueDates.populateMetaFollowUp();
                    }
                    MainApp.vaccDueDates.setVb08CDueDate(txtVaccineDate.getText().toString());
                    MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(baseId));
                    MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                    /*if (doseNumber < 1) {
                        MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 2));
                    } else {
                        MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                    }*/
                }
                try {
                    MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                            vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (MainApp.dueDates.getUid().equals("")) {
                    insertDueVaccines();
                } else {
                    updateDueVaccines();
                }

                prevBundle.putInt("group", daysGroup[1]);
                prevBundle.putString("date", nextDate.toString("yyyy-MM-dd"));
                return prevBundle;
            }
        } else {
            String letter = String.valueOf(getChar(0));
            // baseId = opv
            // letter = dose number

            radioButton = (RadioButton) getViewDynamically(baseId + letter);
            txtVaccineDate = (TextView) getViewDynamically(baseId + letter + "txt");

            if (radioButton != null && txtVaccineDate != null) {
                int[] groupDays;
                if(baseId.equals("vb08cb")){
                    groupDays = getDaysAndGroupOfVaccineType(baseId, 0);
                }else {
                    groupDays = getDaysAndGroupOfVaccineType(baseId, -1);
                }
                group = groupDays[1];

                if (group == previousGroup) {
                    txtVaccineDate.setText(prevBundle.getString("date"));
                    txtVaccineDate.setVisibility(View.VISIBLE);
                    if (!txtVaccineDate.getText().toString().equals("")) {
                        /*Saving Vaccines Due Dates*/
                        if (flag) {
                            vaccDueDates.populateMeta();
                        } else {
                            vaccDueDates.populateMetaFollowUp();
                        }
                        MainApp.vaccDueDates.setVb08CDueDate(txtVaccineDate.getText().toString());
                        MainApp.vaccDueDates.setVb08CDueCode(getVaccineNameFromBaseID(baseId));
                        // As current dose is initially -1 so dosenumber +2 will give 1st dose.
                        MainApp.vaccDueDates.setVb08CDueAntigen(String.valueOf(doseNumber + 1));
                        try {
                            MainApp.dueDates = db.getDueVaccinesBYAntigen(vaccDueDates.getVb02(), vaccDueDates.getVb04a(), vaccDueDates.getVb04(),
                                    vaccDueDates.getVb08CDueCode(), vaccDueDates.getVb08CDueAntigen());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (MainApp.dueDates.getUid().equals("")) {
                            insertDueVaccines();
                        } else {
                            updateDueVaccines();
                        }
                    }
                }
            }
        }
        return prevBundle;
    }

    /**
     * This is calculating the next vaccine (Column in the UI)
     *
     * @param results
     * @param baseId
     */
    private void calculateNextVaccineDate(ArrayList<Boolean> results, String baseId) {
        int firstTrue = results.lastIndexOf(true);

        String vaccineType = baseId;
        int doseNumber = firstTrue;
        RadioButton radioButton;
        TextView txtVaccineDatePrevious;
        TextView txtVaccineDate;

        if (firstTrue > -1) {
            String prevLetter = String.valueOf(getChar(firstTrue));
            String letter = String.valueOf(getChar(firstTrue + 1));
            if (letter.equals("?") || prevLetter.equals("?"))
                return;
            // baseId = opv
            // letter = dose number

            radioButton = (RadioButton) getViewDynamically(baseId + letter);
            txtVaccineDatePrevious = (TextView) getViewDynamically(baseId + prevLetter + "txt");
            txtVaccineDate = (TextView) getViewDynamically(baseId + letter + "txt");

            if (radioButton != null && txtVaccineDate != null && txtVaccineDatePrevious != null) {
                String prevDateStr = txtVaccineDatePrevious.getText().toString();

                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime prevDate = fmt.parseDateTime(prevDateStr);

                int[] daysAndGroup = getDaysAndGroupOfVaccineType(vaccineType, doseNumber);
                int days = daysAndGroup[0];
                DateTime nextDate = prevDate.plusDays(days);
                txtVaccineDate.setText(nextDate.toString("yyyy-MM-dd"));
                txtVaccineDate.setVisibility(View.VISIBLE);
            }
        }
    }

    private int getDaysOfVaccineType(String vaccineType, int currentDose) {
        int days = 0;
        int group = 0;
        switch (vaccineType) {
            case "vb08ca":      // BCG  Group 0
                if (currentDose == -1) {
                    days = 0;
                }
                break;
            case "vb08cb":      // OPV
                // OPV0 at birth    Group 0
                if (currentDose == -1) {
                    days = 0;
                }
                // OPV1 at 6 weeks      Group 1
                else if (currentDose == 0) {
                    days = 42;
                }
                // Opv2 at 10 weeks     Group 2
                else if (currentDose == 1) {
                    days = 28;
                }
                // OPV3 at 14 weeks     Group 3
                else {
                    days = 28;
                }
                break;
            case "vb08cc":      // HEP - B  Group 0
                if (currentDose == -1) {
                    days = 0;
                }
                break;
            case "vb08cd":      // Penta
                // Penta 1 at 6 weeks   Group 1
                if (currentDose == -1) {
                    days = 42;
                }
                // Penta 2 at 10 weeks and Penta 3 at 14 weeks
                else if (currentDose == 0) {
                    days = 28;
                } else {
                    days = 28;

                }
                break;
            case "vb08ce":      // PCV
                // PCV1 at 6 weeks      Group 1
                if (currentDose == -1) {
                    days = 42;
                }
                // PCV 2 and PCV 3 at 10 and 14 weeks respectively
                else if (currentDose == 0) {
                    days = 28;
                } else {
                    days = 28;
                }
                break;
            case "vb08cf":      // ROTA
                // ROTA1 at 6 weeks     Group 1
                if (currentDose == -1) {
                    days = 42;
                }
                // Rota 2 and Rota 3 at 10 and 14 weeks respectively.
                else if (currentDose == 0) {
                    days = 28;
                } else {
                    days = 28;
                }
                break;
            case "vb08cg":      // IPV
                // IPV1 at 14 weeks Group 3
                if (currentDose == -1) {
                    days = 98;
                }
                // IPV 2 at 18 weeks    Group 4
                else {
                    days = 154;
                }
                break;
            case "vb08ch":      // Measles
                // Measles 1 at 9 months        Group 4
                if (currentDose == -1) {
                    days = 252;
                }
                // Measles 2 at 15 months       Group 5
                else {
                    days = 168;
                }
                break;
            case "vb08ci":      // Typhoid
                // Typhoid at 9 months      Group 4
                if (currentDose == -1) {
                    days = 252;
                }
                break;
        }

        return days;
    }

    private String getVaccineNameFromBaseID(String baseId) {
        String vaccineName = "";

        switch (baseId) {
            case "vb08ca":
                vaccineName = "BCG";
                break;
            case "vb08cb":
                vaccineName = "OPV";
                break;
            case "vb08cc":
                vaccineName = "Hep-B";
                break;
            case "vb08cd":
                vaccineName = "Penta";
                break;
            case "vb08ce":
                vaccineName = "PCV";
                break;
            case "vb08cf":
                vaccineName = "ROTA";
                break;
            case "vb08cg":
                vaccineName = "IPV";
                break;
            case "vb08ch":
                vaccineName = "Measles";
                break;
            case "vb08ci":
                vaccineName = "Typhoid";
                break;
            case "vb08cj":
                vaccineName = "DPT";
                break;


        }
        return vaccineName;
    }

    private int[] getDaysAndGroupOfVaccineType(String vaccineType, int currentDose) {
        int days = 0;
        int group = 0;
        switch (vaccineType) {
            case "vb08ca":      // BCG  Group 0
                if (currentDose == -1) {
                    days = 0;
                    group = 0;
                }
                break;
            case "vb08cb":      // OPV
                // OPV0 at birth    Group 0
                if (currentDose == 0) {
                    days = 0;
                    group = 0;
                }
                // OPV1 at 6 weeks      Group 1
                else if (currentDose == 1) {
                    days = 42;
                    group = 1;
                }
                // Opv2 at 10 weeks     Group 2
                else if (currentDose == 2) {
                    days = 28;
                    group = 2;
                }
                // OPV3 at 14 weeks     Group 3
                else {
                    days = 28;
                    group = 3;
                }
                break;
            case "vb08cc":      // HEP - B  Group 0
                if (currentDose == -1) {
                    days = 0;
                    group = 0;
                }
                break;
            case "vb08cd":      // Penta
                // Penta 1 at 6 weeks   Group 1
                if (currentDose == -1) {
                    days = 42;
                    group = 1;
                }
                // Penta 2 at 10 weeks and Penta 3 at 14 weeks
                else if (currentDose == 0) {
                    days = 28;
                    group = 2;
                } else {
                    days = 28;
                    group = 3;
                }
                break;
            case "vb08ce":      // PCV
                // PCV1 at 6 weeks      Group 1
                if (currentDose == -1) {
                    days = 42;
                    group = 1;
                }
                // PCV 2 and PCV 3 at 10 and 14 weeks respectively
                else {
                    days = 28;
                    group = 2;
                }
                break;
            case "vb08cf":      // ROTA
                // ROTA1 at 6 weeks     Group 1
                if (currentDose == -1) {
                    days = 42;
                    group = 1;
                }
                // Rota 2 and Rota 3 at 10 and 14 weeks respectively.
                else {
                    days = 28;
                    group = 2;
                }
                break;
            case "vb08cg":      // IPV
                // IPV1 at 14 weeks Group 3
                if (currentDose == -1) {
                    days = 98;
                    group = 3;
                }
                // IPV 2 at 18 weeks    Group 4
                else {
                    days = 154;
                    group = 4;
                }
                break;
            case "vb08ch":      // Measles
                // Measles 1 at 9 months        Group 4
                if (currentDose == -1) {
                    days = 252;
                    group = 4;
                }
                // Measles 2 at 15 months       Group 5
                else {
                    days = 168;
                    group = 5;
                }
                break;
            case "vb08ci":      // Typhoid
                // Typhoid at 9 months      Group 4
                if (currentDose == -1) {
                    days = 252;
                    group = 4;
                }
                break;

            case "vb08cj":  // DPT
                // DPT at 18 months
                if(currentDose == -1)
                {
                    days = 90;
                    group = 6;
                }
        }

        return new int[]{days, group};
    }

    private boolean showHideDoneCheckWithText(
            String conditionText,
            String baseId,
            String letter
    ) {

        RadioButton radioButton = (RadioButton) getViewDynamically(baseId + letter);
        ImageView imgDone = (ImageView) getViewDynamically(baseId + letter + "tick");
        TextView txtVaccine = (TextView) getViewDynamically(baseId + letter + "txt");
        CheckBox checkBox = (CheckBox) getViewDynamically(baseId + "98");

        if (!conditionText.equals("")) {
            if (txtVaccine != null) {
                txtVaccine.setVisibility(View.VISIBLE);
                txtVaccine.setTextColor(getResources().getColor(R.color.green));
                txtVaccine.setText(conditionText);
            }

            if (radioButton != null) {
                radioButton.setVisibility(View.GONE);
                checkBox.setEnabled(false);
            } else {
                checkBox.setEnabled(true);
            }

            if (imgDone != null)
                imgDone.setVisibility(View.VISIBLE);

            return true;
        }
        return false;
    }


    private boolean insertVaccineRecord(String vaccCode, String antigen, String vaccDate) {
        setGPS();
        if (flag) {
            vaccines.setFrontfilename(formVB.getFrontfilename());
            vaccines.setBackfilename(formVB.getBackfilename());
            vaccines.setChildfilename(formVB.getChildfilename());
            vaccines.setGpsLat(formVB.getGpsLat());
            vaccines.setGpsLng(formVB.getGpsLng());
            vaccines.setGpsAcc(formVB.getGpsAcc());
            vaccines.setGpsDT(formVB.getGpsDT());
        } else {
            vaccines.setFrontfilename(bi.frontFileName.getText().toString());
            vaccines.setBackfilename(bi.backFileName.getText().toString());
            vaccines.setChildfilename(bi.childFileName.getText().toString());
            vaccines.setGpsLat(vaccines.getGpsLat());
            vaccines.setGpsLng(vaccines.getGpsLng());
            vaccines.setGpsAcc(vaccines.getGpsAcc());
            vaccines.setGpsDT(vaccines.getGpsDT());
        }

        vaccines.updateAntigen(vaccCode, antigen, vaccDate);
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
            vaccDueDates.setUuid(vaccines.getUid());
            db.updatesVaccineColumn(TableContracts.VaccinesTable.COLUMN_UID, vaccines.getUid());
            if(!bi.vb08cja.isChecked()) {
                db.updatesDueVaccineColumn(TableContracts.VaccinesDueTable.COLUMN_UUID, vaccDueDates.getUuid());
            }
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private boolean insertDueVaccines() {

        if (flag) {
            vaccDueDates.populateMeta();
        } else {
            vaccDueDates.populateMetaFollowUp();
        }
        long rowId = 0;
        vaccDueDates.updateDueAntigen();
        try {
            rowId = db.addDueVaccine(vaccDueDates);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        vaccDueDates.setId(String.valueOf(rowId));
        if (rowId > 0) {
            vaccDueDates.setUid(vaccDueDates.getDeviceId() + vaccDueDates.getId());
            db.updatesDueVaccineColumn(TableContracts.VaccinesDueTable.COLUMN_UID, vaccDueDates.getUid());
            //vaccDueDates.updateDueAntigen();
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


    private boolean updateDueVaccines() {
        if (MainApp.superuser) return true;

        setGPS();

        int updcount = 0;
        updcount = db.updatesDueVaccineColumn(TableContracts.VaccinesDueTable.COLUMN_VB08C_CODE, vaccDueDates.getVb08CDueCode())
                + db.updatesDueVaccineColumn(TableContracts.VaccinesDueTable.COLUMN_VB08C_ANTIGEN, vaccDueDates.getVb08CDueAntigen())
                + db.updatesDueVaccineColumn(TableContracts.VaccinesDueTable.COLUMN_VB08C_DATE, vaccDueDates.getVb08CDueDate());
        if (updcount > 0) {
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
            vaccines.populateMetaFollowUp();
        }

        String caAntigen = null;

        // BCG
        if (bi.vb08ca98.isChecked()) {
            caAntigen = bi.vb08caa.isChecked() ? "1" : "-1";
            insertVaccineRecord("BCG", caAntigen, bi.vb08cadt.getText().toString());
        }

        // OPV
        if (bi.vb08cb98.isChecked()) {
            caAntigen = bi.vb08cba.isChecked() ? "1"
                    : bi.vb08cbb.isChecked() ? "2"
                    : bi.vb08cbc.isChecked() ? "3"
                    : bi.vb08cbd.isChecked() ? "4"
                    : "-1";
            insertVaccineRecord("OPV", caAntigen, bi.vb08cbdt.getText().toString());
            //insertDueVaccines();
        }

        // Hep B
        if (bi.vb08cc98.isChecked()) {
            caAntigen = bi.vb08cca.isChecked() ? "1"
                    : "-1";
            insertVaccineRecord("HepB", caAntigen, bi.vb08ccdt.getText().toString());
        }

        // Penta
        if (bi.vb08cd98.isChecked()) {
            caAntigen = bi.vb08cda.isChecked() ? "1"
                    : bi.vb08cdb.isChecked() ? "2"
                    : bi.vb08cdc.isChecked() ? "3"
                    : "-1";
            insertVaccineRecord("Penta", caAntigen, bi.vb08cddt.getText().toString());
        }

        // PCV
        if (bi.vb08ce98.isChecked()) {
            caAntigen = bi.vb08cea.isChecked() ? "1"
                    : bi.vb08ceb.isChecked() ? "2"
                    : bi.vb08cec.isChecked() ? "3"
                    : "-1";
            insertVaccineRecord("PCV", caAntigen, bi.vb08cedt.getText().toString());
        }

        // Rota
        if (bi.vb08cf98.isChecked()) {
            caAntigen = bi.vb08cfa.isChecked() ? "1"
                    : bi.vb08cfb.isChecked() ? "2"
                    : "-1";
            insertVaccineRecord("Rota", caAntigen, bi.vb08cfdt.getText().toString());
        }

        // IPV
        if (bi.vb08cg98.isChecked()) {
            caAntigen = bi.vb08cga.isChecked() ? "1"
                    : bi.vb08cgb.isChecked() ? "2"
                    : "-1";
            insertVaccineRecord("IPV", caAntigen, bi.vb08cgdt.getText().toString());
        }

        // Measles
        if (bi.vb08ch98.isChecked()) {
            caAntigen = bi.vb08cha.isChecked() ? "1"
                    : bi.vb08chb.isChecked() ? "2"
                    : "-1";
            insertVaccineRecord("Measles", caAntigen, bi.vb08chdt.getText().toString());
        }

        // Typhoid
        if (bi.vb08ci98.isChecked()) {
            caAntigen = bi.vb08cia.isChecked() ? "1"
                    : "-1";
            insertVaccineRecord("Typhoid", caAntigen, bi.vb08cidt.getText().toString());
        }

        // DPT
        if (bi.vb08cj98.isChecked()) {
            caAntigen = bi.vb08cja.isChecked() ? "1"
                    : "-1";
            insertVaccineRecord("DPT", caAntigen, bi.vb08cjdt.getText().toString());
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
//        MainApp.memberCount --;
        startActivity(new Intent(this, MainActivity.class));
    }

    private void onCheckChanged(RadioGroup rd, EditText ed) {
        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currentSelectedRadioButtonId = checkedId;
                if (checkedId != -1) {
                    ed.setText("");
                }

            }
        });
    }


    private boolean formValidation() {

        if (!Validator.emptyCheckingContainer(this, bi.GrpName)) {
            return false;
        }
        if (flag) {
            if (formVB.getVb03().equals("2") && formVB.getFrontfilename().equals("")) {
                return Validator.emptyCustomTextBox(this, bi.frontFileName, "Please take front photo of Vaccination Card.");
            }

            // Check back photo taken
            if (formVB.getVb03().equals("2") && formVB.getBackfilename().equals("")) {
                return Validator.emptyCustomTextBox(this, bi.backFileName, "Please take back photo of Vaccination Card.");

            }

            // Check child photo taken
/*            if (formVB.getVb03().equals("2") && formVB.getChildfilename().equals("")) {
                return Validator.emptyCustomTextBox(this, bi.childFileName, "Please take photo of Child.");

            }*/
        } else {

        }

        // Check front photo taken
        if (vaccinesData.getVBO3().equals("2") && bi.frontFileName.getText().toString().equals("")) {
            return Validator.emptyCustomTextBox(this, bi.frontFileName, "Please take front photo of Vaccination Card.");
        }

        // Check back photo taken
        if (vaccinesData.getVBO3().equals("2") && bi.backFileName.getText().toString().equals("")) {
            return Validator.emptyCustomTextBox(this, bi.backFileName, "Please take back photo of Vaccination Card.");

        }

        // Check child photo taken
/*        if (vaccinesData.getVBO3().equals("2") && bi.childFileName.getText().toString().equals("")) {
            return Validator.emptyCustomTextBox(this, bi.childFileName, "Please take photo of Child.");
        }*/

        if (bi.vb08ca98.isChecked() && formVB.getVb08ca().equals("") || bi.vb08ca98.isChecked() && formVB.getVb08cadt().equals(""))
//            return Validator.emptyRadioButton(this, bi.vb08ca, bi.vb08caa);
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08ca, false);

        if (bi.vb08cb98.isChecked() && formVB.getVb08cb().equals("") || bi.vb08cb98.isChecked() && formVB.getVb08cbdt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08cb, false);

        if (bi.vb08cc98.isChecked() && formVB.getVb08cc().equals("") || bi.vb08cc98.isChecked() && formVB.getVb08ccdt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08cc, false);

        if (bi.vb08cd98.isChecked() && formVB.getVb08cd().equals("") || bi.vb08cd98.isChecked() && formVB.getVb08cddt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08cd, false);

        if (bi.vb08ce98.isChecked() && formVB.getVb08ce().equals("") || bi.vb08ce98.isChecked() && formVB.getVb08cedt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08ce, false);

        if (bi.vb08cf98.isChecked() && formVB.getVb08cf().equals("") || bi.vb08cf98.isChecked() && formVB.getVb08cfdt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08cf, false);

        if (bi.vb08cg98.isChecked() && formVB.getVb08cg().equals("") || bi.vb08cg98.isChecked() && formVB.getVb08cgdt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08cg, false);

        if (bi.vb08ch98.isChecked() && formVB.getVb08ch().equals("") || bi.vb08ch98.isChecked() && formVB.getVb08chdt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08ch, false);

        if (bi.vb08ci98.isChecked() && formVB.getVb08ci().equals("") || bi.vb08ci98.isChecked() && formVB.getVb08cidt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08ci, false);

        if (bi.vb08cj98.isChecked() && formVB.getVb08cj().equals("") || bi.vb08cj98.isChecked() && formVB.getVb08cjdt().equals(""))
            return Validator.emptyCheckingContainer(this, bi.fldGrpVB08cj, false);


        return true;
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


    public void takePhoto(View view) {

        Intent intent = new Intent(this, TakePhoto.class);

        if (flag) {
            intent.putExtra("picID", formVB.getVb02() + "_" + MainApp.formVB.getVb02() + "_");
            intent.putExtra("Name", formVB.getVb04a());
        } else {
            intent.putExtra("picID", vaccinesData.getVBO2() + "_" + vaccinesData.getVBO2() + "_");
            intent.putExtra("Name", vaccinesData.getVB04A());
        }

        if (view.getId() == R.id.frontPhoto) {
            intent.putExtra("picView", "front".toUpperCase());
            startActivityForResult(intent, 1); // Activity is started with requestCode 1 = Front
        } else if (view.getId() == R.id.backPhoto) {
            intent.putExtra("picView", "back".toUpperCase());
            startActivityForResult(intent, 2); // Activity is started with requestCode 2 = Back
        } else {
            intent.putExtra("picView", "child".toUpperCase());
            startActivityForResult(intent, 3); // Activity is started with requestCode 3 = Child
        }
    }


    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, requestCode + "_" + resultCode, Toast.LENGTH_SHORT).show();

            String fileName = data.getStringExtra("FileName");

            // Check if the requestCode 1 = Front : 2 = Back -- resultCode 1 = Success : 2 = Failure
            // Results received with requestCode 1 = Front

            if (requestCode == 1 && resultCode == RESULT_OK) {
                //Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();

                bi.frontFileName.setText(fileName);
                bi.frontPhoto.setEnabled(false);

            } else if (requestCode == 1 && resultCode != RESULT_CANCELED) {
                Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();

                //TODO: Implement functionality below when photo was not taken
                // ...
                //     bi.frontFileName.setText("Photo not taken.");

            }

            // Results received with requestCode 2 = Back
            if (requestCode == 2 && resultCode == RESULT_OK) {
                //Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();
                bi.backFileName.setText(fileName);
                bi.backPhoto.setEnabled(false);
            } else if (requestCode == 2 && resultCode != RESULT_CANCELED) {

                Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();

                //TODO: Implement functionality below when photo was not taken
                // ...
                //      bi.backFileName.setText("Photo not taken.");

            }

            // Results received with requestCode 2 = child
            if (requestCode == 3 && resultCode == RESULT_OK) {
                //Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();
                bi.childFileName.setText(fileName);
                bi.childPhoto.setEnabled(false);
            } else if (requestCode == 3 && resultCode != RESULT_CANCELED) {

                Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();

                //TODO: Implement functionality below when photo was not taken
                // ...
                //      bi.backFileName.setText("Photo not taken.");

            }
        }
    }
}