package com.example.scotty.realdistance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by scotty on 9/24/2016.
 */

public class LogWorkoutActivity extends AppCompatActivity {

        public final static String EXTRA_DATA = "com.example.scotty.realdistance.AboutActivity";
        public static double RealDistanceWorkout = 0.00;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_logworkout);

            updateDistanceTextField();
        }
        private void updateDistanceTextField() {
            TextView t = (TextView)findViewById(R.id.DistanceTextField);
            t.setText(String.valueOf(RealDistanceWorkout));
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            getMenuInflater().inflate(R.menu.menu_logworkout, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            switch (item.getItemId()) {
                case R.id.Home:
                    finish();
                case R.id.about:
                    Intent intent = new Intent(this, AboutActivity.class);
                    intent.putExtra(EXTRA_DATA, "extra data or parameter you want to pass to activity");
                    startActivity(intent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }


    }
