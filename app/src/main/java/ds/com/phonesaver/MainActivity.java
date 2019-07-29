package ds.com.phonesaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;

    private static final int REQUEST_READ_PHONE_STATE = 11;
    private TelephonyManager tMgr;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main);
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());

        try {
            SharedPreferences prefs= getApplicationContext().getSharedPreferences("ds.com.phonesaver.ServiceRunning", getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("counter", 0);
            editor.apply();
            //Long.i("MoveMore", "Saving readings to preferences");
        } catch (NullPointerException e) {
            Log.e("MAINACT", "error saving: are you testing?" +e.getMessage());
        }


        if (!isMyServiceRunning(mSensorService.getClass())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getApplicationContext().startForegroundService(mServiceIntent);
            } else {
                startService(mServiceIntent);
            }
        }

        //CHECK PHONE NUMBER
        tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

        } else {

        }

        //this will move the Activity in the background
        moveTaskToBack(true);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }


    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }

    public void btnOKClicked(View view) {
        this.finish();
    }
}
