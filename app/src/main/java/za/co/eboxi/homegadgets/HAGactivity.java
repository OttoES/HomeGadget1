package za.co.eboxi.homegadgets;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import za.co.eboxi.ble.BluetoothLeScanService2;
import za.co.eboxi.hag.HAGapplication;
import za.co.eboxi.hag.HAGtask;


public class HAGactivity extends Activity {

    private void initBLE()
    {
//        // Use this check to determine whether BLE is supported on the device.  Then you can
//        // selectively disable BLE-related features.
//        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
//        // BluetoothAdapter through BluetoothManager.
//        final BluetoothManager bluetoothManager =
//                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//        mBluetoothAdapter = bluetoothManager.getAdapter();
//
//        // Checks if Bluetooth is supported on the device.
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }

//        startService(new Intent(this, BluetoothLeScanService2.class));

        final Intent intent = new Intent(this, BluetoothLeScanService2.class);
        startService(intent);
        Intent newSer = new Intent(this, HAGaudioService.class);
        startService(newSer);
        // send a a test intent
        Intent i = new Intent(this, HAGaudioService.class);
        i.putExtra("filename","file:///storage/emulated/0/VoiceRecorder/my_sounds/beacon1.3gp");



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hagactivity);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        initBLE();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hagactivity, menu);


        return true;
    }

    //BluetoothLeScanService bleSer;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            startService(new Intent(this, BluetoothLeScanService2.class));

            if (HAGapplication.mTasks == null)
                HAGapplication.mTasks = HAGtask.ReadTasksFromJSONfile(getAssets(), "test.json");
            //bleSer = new BluetoothLeScanService();
            //startService(new Intent(this, BluetoothLeScanService.class));
            //bleSer.startscanning();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_hagactivity, container, false);
            return rootView;
        }
    }
}
