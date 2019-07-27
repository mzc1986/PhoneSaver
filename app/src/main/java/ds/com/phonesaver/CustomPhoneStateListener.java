package ds.com.phonesaver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CustomPhoneStateListener extends PhoneStateListener {

    private static final String TAG = "PhoneStateChanged";
    Context context; //Context to make Toast if required
    public CustomPhoneStateListener(Context context) {
        super();
        this.context = context;

        Log.d(TAG, "CustomPhoneStateListener: ");
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                //when Idle i.e no call
                Toast.makeText(context, "Phone state Idle", Toast.LENGTH_LONG).show();

                try {
                    SharedPreferences prefs= context.getApplicationContext().getSharedPreferences("ds.com.phonesaver.ServiceRunning", context.getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("incall", 0);
                    editor.apply();
                    Log.i("MoveMore", "IDLE" + prefs.getInt("incall", 0));
                } catch (NullPointerException e) {
                    Log.e(TAG, "error saving: are you testing?" +e.getMessage());
                }


                break;
            case TelephonyManager.CALL_STATE_RINGING:
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //when Off hook i.e in call
                //Make intent and start your service here
                Toast.makeText(context, "Phone state Off hook or Ringing", Toast.LENGTH_LONG).show();

                try {
                    SharedPreferences prefs= context.getApplicationContext().getSharedPreferences("ds.com.phonesaver.ServiceRunning", context.getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("incall", 1);
                    editor.apply();
                    Log.i("MoveMore", "OFFHOOK" + prefs.getInt("incall", 0));
                } catch (NullPointerException e) {
                    Log.e(TAG, "error saving: are you testing?" +e.getMessage());
                }

                break;
            default:
                break;
        }
    }
}
