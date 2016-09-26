package com.example.scotty.realdistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    public final static String EXTRA_DATA = "com.example.scotty.realdistance.AboutActivity";
    public static double RealDistance = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateDistanceTextField();
    }
    private void updateDistanceTextField() {
        TextView t = (TextView)findViewById(R.id.DistanceTextField);
        t.setText(String.valueOf(RealDistance));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.logworkout:
                Intent logintent = new Intent(this, LogWorkoutActivity.class);
                logintent.putExtra(EXTRA_DATA, "extra data or parameter you want to pass to activity");
                startActivity(logintent);
                return true;
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
