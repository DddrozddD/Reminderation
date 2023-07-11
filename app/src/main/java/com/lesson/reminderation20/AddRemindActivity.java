package com.lesson.reminderation20;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.lesson.reminderation20.Services.RemindService;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddRemindActivity extends AppCompatActivity {

    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault());
    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();

    public static String CHANNEL_ID = "channel_id";
    public static final int NOTIFICATION_ID = 1;
    public void setDate(View v) {
        new DatePickerDialog(AddRemindActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    public void setTime(View v) {
        new TimePickerDialog(AddRemindActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remind);

        currentDateTime = findViewById(R.id.currentDateTime);
        setInitialDateTime();


        findViewById(R.id.addBtn2).setOnClickListener(f->{
            TextView exHeadline = (TextView) findViewById(R.id.textExceptionToHeadline);
            TextView exDesc = (TextView) findViewById(R.id.textExceptionToDesc);




            TextView headline= (TextView) findViewById(R.id.RemindTitle);
            TextView desc= (TextView) findViewById(R.id.RemindText);


            currentDateTime = (TextView) findViewById(R.id.currentDateTime);

            //dateAndTime


            if((desc.getText().toString().equals("")) && (headline.getText().toString().equals(""))){
                exHeadline.setText("Error: This line is empty");
                exDesc.setText("Error: This line is empty");
            }
            else if((headline.getText().toString().equals(""))){
                exHeadline.setText("Error: This line is empty");

            }
            else if(desc.getText().toString().equals("")){
                exDesc.setText("Error: This line is empty");

            }
            else {

                createNotificationChannel();

                Intent notificationIntent = new Intent(this, NotificationReceiver.class);

                notificationIntent.putExtra("title", headline.getText().toString());
                notificationIntent.putExtra("desc", desc.getText().toString());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,notificationIntent,PendingIntent.FLAG_IMMUTABLE);


                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                long time = dateAndTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();


                //alarmManager.set(AlarmManager.RTC_WAKEUP, dateAndTime.getTimeInMillis(), pendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY ,pendingIntent);

                RemindService rs = new RemindService();
                rs.AddRemind(headline.getText().toString(),currentDateTime.getText().toString() , desc.getText().toString() );
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createNotificationChannel(){
        TextView headline= (TextView) findViewById(R.id.RemindTitle);
        TextView desc= (TextView) findViewById(R.id.RemindText);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,headline.getText().toString(), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(desc.getText().toString());
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

    }

}