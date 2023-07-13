package edu.aku.dmu.hf_vaccination;

import static edu.aku.dmu.hf_vaccination.core.MainApp.attendance;
import static edu.aku.dmu.hf_vaccination.core.MainApp.formVA;
import static edu.aku.dmu.hf_vaccination.core.MainApp.sharedPref;
import static edu.aku.dmu.hf_vaccination.core.MainApp.workLocation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.dmu.hf_vaccination.contracts.TableContracts;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.database.AndroidManager;
import edu.aku.dmu.hf_vaccination.database.DatabaseHelper;
import edu.aku.dmu.hf_vaccination.databinding.ActivityMainBinding;
import edu.aku.dmu.hf_vaccination.models.Attendance;
import edu.aku.dmu.hf_vaccination.models.FormVA;
import edu.aku.dmu.hf_vaccination.models.FormVB;
import edu.aku.dmu.hf_vaccination.models.Vaccines;
import edu.aku.dmu.hf_vaccination.models.VaccinesData;
import edu.aku.dmu.hf_vaccination.models.VaccinesSchedule;
import edu.aku.dmu.hf_vaccination.models.WomenFollowUP;
import edu.aku.dmu.hf_vaccination.models.WorkLocation;
import edu.aku.dmu.hf_vaccination.ui.ChangePasswordActivity;
import edu.aku.dmu.hf_vaccination.ui.CreateLocationActivity;
import edu.aku.dmu.hf_vaccination.ui.LoginActivity;
import edu.aku.dmu.hf_vaccination.ui.SyncActivity;
import edu.aku.dmu.hf_vaccination.ui.lists.VaccinatedChildListActivity;
import edu.aku.dmu.hf_vaccination.ui.lists.VaccinatedWomenListActivity;
import edu.aku.dmu.hf_vaccination.ui.sections.SectionVAActivity;
import edu.aku.dmu.hf_vaccination.ui.sections.SectionVBActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ActivityMainBinding bi;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(bi.toolbar);
        bi.toolbar.setSubtitle("Welcome, " + MainApp.user.getFullname() + (MainApp.admin ? " (Admin)" : "") + "!");

        db = MainApp.appInfo.dbHelper;
        bi.setCallback(this);
        bi.adminView.setVisibility(MainApp.admin ? View.VISIBLE : View.GONE);
        invalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bi.location.setText("Your Attendance is not marked. \nPlease mark attendance");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String todayDate = sdf.format(new Date());
        String workLocationDate = sharedPref.getString("workLocationDate", "          ").substring(0, 10);
        String attendanceDate = sharedPref.getString("attendanceDate", "          ").substring(0, 10);
        String batchManagementDate = sharedPref.getString("batchManagementDate", "          ").substring(0, 10);
        bi.toolbar.setSubtitle("Welcome, " + MainApp.user.getFullname() + (MainApp.admin ? " (Admin)" : "") + "! " + (!attendanceDate.equals("          ") ? "     -     M✓" : ""));

        Date strWorkLoctionDate = null;
        Date strAttendanceDate = null;
        Date strBatchManagementDate = null;
        Date strToday = null;

        /*WorkLocation*/
        try {
            strWorkLoctionDate = sdf.parse(workLocationDate);
            strToday = sdf.parse(todayDate);

            if (!strWorkLoctionDate.equals(strToday)) {
                MainApp.editor.putString("workLocationUID", "          ");
                MainApp.editor.putString("workLocationDate", "          ");
                MainApp.editor.apply();
                MainApp.workLocation = new WorkLocation();
                bi.location.setText("Your current location is not set. \nPlease create new work location.");

            } else {
                String workLocationUID = sharedPref.getString("workLocationUID", "");

                try {
                    MainApp.workLocation = db.getCurrentWorkLocation(workLocationUID);
                    bi.location.setText("Current Work Location: " + MainApp.workLocation.getWlFacilityName() + (MainApp.workLocation.getWlVillageName() + " " + MainApp.workLocation.getWlArea()));

                } catch (JSONException e) {
                    Toast.makeText(this, "JSONException(WorkLocation): " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

        } catch (ParseException e) {
            e.printStackTrace();
            bi.location.setText("Your current location is not set. \nPlease create new work location.");
        }

        /*Attendance*/
        try {
            strAttendanceDate = sdf.parse(attendanceDate);
            strToday = sdf.parse(todayDate);


            if (!strAttendanceDate.equals(strToday)) {

                MainApp.editor.putString("attendanceUID", "");
                MainApp.editor.putString("attendanceDate", "          ");
                MainApp.editor.apply();
                MainApp.attendance = null;
                bi.markAttendance.setVisibility(View.VISIBLE);
            } else {
                String attendanceUID = sharedPref.getString("attendanceUID", "");
                bi.markAttendance.setVisibility(View.INVISIBLE);
                Snackbar.make(bi.toolbar, "Your attendance has been marked!", Snackbar.LENGTH_LONG).show();

                try {
                    attendance = db.getCurrentAttendance(attendanceUID);

                } catch (JSONException e) {
                    Toast.makeText(this, "JSONException(Attendance): " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*BatchManagement*/
        try {
            strBatchManagementDate = sdf.parse(batchManagementDate);
            strToday = sdf.parse(todayDate);


            if (!strBatchManagementDate.equals(strToday)) {

                MainApp.editor.putString("batchManagementUID", "");
                MainApp.editor.putString("batchManagementDate", "          ");
                MainApp.editor.apply();
                MainApp.formVA = null;
                //bi.markAttendance.setVisibility(View.VISIBLE);
            } else {
                String batchManagementUID = sharedPref.getString("batchManagementUID", "");
                //bi.markAttendance.setVisibility(View.INVISIBLE);
                //Snackbar.make(bi.toolbar, "Your attendance has been marked!", Snackbar.LENGTH_LONG).show();

                try {
                    MainApp.formVA = db.getCurrentFormVA(batchManagementUID);

                } catch (JSONException e) {
                    Toast.makeText(this, "JSONException(BatchManagement): " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void sectionPress(View view) {
        if (attendance != null && workLocation != null && !workLocation.getId().isEmpty()) {
            switch (view.getId()) {

                case R.id.openFormVA:
                    MainApp.formVA = new FormVA();
                    startActivity(new Intent(this, SectionVAActivity.class));
                    break;

                case R.id.openChildVacForm:
                    MainApp.flag = false;
                    MainApp.formVB = new FormVB();
                    MainApp.vaccinesData = new VaccinesData();
                    MainApp.vaccinesSchedule = new VaccinesSchedule();
                    MainApp.vaccines = new Vaccines();
                    if (formVA != null && !formVA.getId().isEmpty()) {
                        bi.openChildVacForm.setClickable(true);
                        finish();
                        startActivity(new Intent(this, VaccinatedChildListActivity.class));
                    } else
                        Snackbar.make(bi.toolbar, "Please Enter Batch Management Form", Snackbar.LENGTH_LONG).show();

                    break;

                case R.id.openWomenVacForm:
                    MainApp.flag = false;
                    MainApp.formVB = new FormVB();
                    MainApp.womenFollowUP = new WomenFollowUP();
                    MainApp.vaccines = new Vaccines();
                    if (formVA != null && !formVA.getId().isEmpty()) {
                        bi.openWomenVacForm.setClickable(true);
                        finish();
                        startActivity(new Intent(this, VaccinatedWomenListActivity.class));
                    } else
                        Snackbar.make(bi.toolbar, "Please Enter Batch Management Form", Snackbar.LENGTH_LONG).show();
                    break;

                case R.id.secA:
                    MainApp.formVA = new FormVA();
                    startActivity(new Intent(this, SectionVAActivity.class));
                    break;

                case R.id.secB:
                    MainApp.formVB = new FormVB();
                    startActivity(new Intent(this, SectionVBActivity.class));
                    break;

                case R.id.dbm:
                    startActivity(new Intent(this, AndroidManager.class));
                    break;

            }
        } else {
            Snackbar.make(bi.toolbar, "Please create a location.", Snackbar.LENGTH_LONG).show();
            animMenu();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
/*            case R.id.action_mark_attendance:
                intent = new Intent(MainActivity.this, CreateLocationActivity.class);
                startActivity(intent);
                break;*/
            case R.id.action_create_location:
                if (attendance != null) {
                    intent = new Intent(MainActivity.this, CreateLocationActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(bi.toolbar, "Please mark your attendance first!", Snackbar.LENGTH_LONG).show();
                    animBackground();
                }
                break;
            case R.id.action_database:
                intent = new Intent(MainActivity.this, AndroidManager.class);
                startActivity(intent);
                break;

            case R.id.onSync:
                intent = new Intent(MainActivity.this, SyncActivity.class);
                startActivity(intent);
                break;

            case R.id.changePassword:
                intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.summary:
                intent = new Intent(MainActivity.this, DailySummaryActivity.class);
                startActivity(intent);
                break;
            case R.id.sendDB:
                sendEmail();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        MenuItem action_database = menu.findItem(R.id.action_database);

        action_database.setVisible(MainApp.admin);
        return true;
    }


    public void markAttendance(View view) {


        View alertCustomdialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.mark_attendance_dialog, null);

        AlertDialog.Builder dateErrorAlert = new AlertDialog.Builder(this);
        dateErrorAlert.setView(alertCustomdialog);
        TextView txtDia = alertCustomdialog.findViewById(R.id.txtDia);
        Button btnYes = alertCustomdialog.findViewById(R.id.btnYes);
        Button btnNo = alertCustomdialog.findViewById(R.id.btnNo);
        // txtDia.setText("Your device date & time is \n" + deviceTime + "\n\nServer date & time is \n" + serverTime);

        AlertDialog markAttendanceDialog = dateErrorAlert.create();

        markAttendanceDialog.show();
        markAttendanceDialog.setCanceledOnTouchOutside(false);

        btnYes.setOnClickListener(view1 -> {
            markAttendanceDialog.dismiss();

            attendance = new Attendance();
            setGPS();
            if (insertNewRecord()) {
                //  finish();
                //  startActivity(new Intent(this, MainActivity.class));
                bi.markAttendance.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Attendance has been marked.", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(MainActivity.this, R.string.fail_db_upd, Toast.LENGTH_SHORT).show();
            }

            //finish();
        });
        btnNo.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View view) {
                markAttendanceDialog.dismiss();

            }
        });
    }

    private boolean insertNewRecord() {
        if (!attendance.getUid().equals("") || MainApp.superuser) return true;
        attendance.populateMeta();

        long rowId = 0;
        try {
            rowId = db.addAttendance(attendance);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.db_excp_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        attendance.setId(String.valueOf(rowId));
        if (rowId > 0) {
            attendance.setUid(attendance.getDeviceId() + attendance.getId());
            db.updatesAttenColumn(TableContracts.AttendanceTable.COLUMN_UID, attendance.getUid());
            setCurrentAttendance();
            finish();
            startActivity(getIntent());
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
                //Toast.makeText(this, "Could not obtained points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Points set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            attendance.setGpsLat(lat);
            attendance.setGpsLng(lang);
            attendance.setGpsAcc(acc);
            attendance.setGpsDT(date); // Timestamp is converted to date above

//            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setPoints: " + e.getMessage());
        }
    }

    private void setCurrentAttendance() {
        MainApp.editor.putString("attendanceUID", attendance.getUid());
        MainApp.editor.putString("attendanceDate", attendance.getSysDate());
        MainApp.editor.apply();
    }

    private void animBackground() {
        ImageButton ll = findViewById(R.id.markAttendance);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        int vis = ll.getVisibility();
        if (vis == 0) {
            ll.startAnimation(anim);

            // Stop blinking after 7 sec
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ll.clearAnimation();
                }
            }, 7000);
        }
    }


    private void animMenu() {
        Toolbar ll = findViewById(R.id.toolbar);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        int vis = ll.getVisibility();
        if (vis == 0) {
            ll.startAnimation(anim);

            // Stop blinking after 7 sec
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ll.clearAnimation();
                }
            }, 7000);
        }
    }

    // Email database to specified email address as attachment
    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"omar.shoaib@aku.edu","hussain.siddiqui@aku.edu"});
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"khalid.feroz@aku.edu"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "HF Vaccination Database - For Issue Monitoring");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "HF Vaccination database upload from the device which has issues while uploading the data." +
                "This is just for testing/checking purpose.");
        File file = LoginActivity.dbBackup(MainActivity.this);
        if (file == null || !file.exists() || !file.canRead()) {
            Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(this, "edu.aku.dmu.hf_vaccination.fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(emailIntent, "Pick an email provider"));
    }
}