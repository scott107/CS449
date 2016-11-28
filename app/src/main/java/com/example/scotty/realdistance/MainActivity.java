package com.example.scotty.realdistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.util.Calendar;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

import static com.example.scotty.realdistance.R.id.YesterdayTextField;

public class MainActivity extends AppCompatActivity implements LocationListener{

    public final static String EXTRA_DATA = "com.example.scotty.realdistance.AboutActivity";
    public static double RealDistance = 0.00;
    private double tempDist;
    private String provider;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 11;
    private LocationManager locationManager;
    AlarmManager alrm;
    private PendingIntent pi;
    long timeinnanos;
    double changetimeinhours;
    private TextView latituteField;
    private TextView longitudeField;
    private double lon = 0;
    private double lati = 0;
    private Location location;
    private int MPH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latituteField = (TextView) findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        }

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        timeinnanos = location.getElapsedRealtimeNanos();
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }

        lati = (double) (location.getLatitude());
        lon = (double) (location.getLongitude());

        updateDistanceTextField();

        DatabaseModel model = DatabaseModel.instance(getApplicationContext());
        model = DatabaseModel.instance(this);
        TextView y = (TextView) findViewById(R.id.YesterdayTextField);

        try {
            double yester = model.YesterdayDaily();
            y.setText(truncateDecimalstoString(yester, 3));
        }
        catch( Exception e ){
            y.setText(e.getMessage());
        }

    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        }

        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        float[] results = new float[1];
        Location.distanceBetween(
                lon,lati,
                lng, lat, results);
        // placeholder until persistent storage
        if (RealDistance > 1000){
            RealDistance = 0;
        }
        changetimeinhours = (location.getElapsedRealtimeNanos() - timeinnanos) * (0.000277777777778/1000000000);
        timeinnanos = location.getElapsedRealtimeNanos();
        tempDist = results[0];
        // convert to miles
        tempDist *= 0.000621371;
        // milomiter  more than 1 MPH, and less than 15 to avoid car travel
        if (((tempDist/changetimeinhours) > 1 && (tempDist/changetimeinhours) < 15)|| lon == 0 || lati == 0) {
            RealDistance = RealDistance + tempDist;
            lon = lng;
            lati = lat;
        }
        latituteField.setText(truncateDecimalstoString(lat, 2));
        longitudeField.setText(truncateDecimalstoString(lng, 2));
        updateDistanceTextField();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    private void updateDistanceTextField() {
        TextView t = (TextView)findViewById(R.id.DistanceTextField);
        t.setText(truncateDecimalstoString(RealDistance, 3));
        if (RealDistance != 100 && RealDistance > 0){
            DatabaseModel model = DatabaseModel.instance(this);
            model.insertDaily(RealDistance);
/*
            TextView y = (TextView) findViewById(R.id.YesterdayTextField);
            try {
                double yester = model.YesterdayDaily();
                y.setText(truncateDecimalstoString(yester, 3));
            }
            catch( Exception e ){
                y.setText(truncateDecimalstoString(RealDistance, 3));
            }
*/
        }
    }

    private String truncateDecimalstoString(double oldnumber, int decilength){
        DecimalFormat ProperDisplay = new DecimalFormat();
        ProperDisplay.setMaximumFractionDigits(decilength);
        ProperDisplay.setMinimumFractionDigits(0);
        return (ProperDisplay.format(oldnumber));
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
