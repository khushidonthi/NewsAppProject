package com.example.measdrone_project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.net.Uri;
import android.app.Activity;
import android.content.Intent;
import android.telephony.CellInfoLte;
import android.telephony.PhoneStateListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.telephony.TelephonyManager;
import java.util.Objects;
import android.widget.TextView;
import android.telephony.CellInfo;
import com.opencsv.*;

public class MainActivity extends Activity implements OnClickListener {
    EditText et;
    Button call_btn;
    private LocationManager locationManager = null;
    private EditText editLocation;
    private ProgressBar pb = null;
    private static final String TAG = "Debug";
    SignalStrengthListener signalStrengthListener;
    TextView signalStrengthTextView, signalStrengthTextView2;
    TextView cellIDTextView;
    TextView cellMccTextView;
    TextView cellMncTextView;
    TextView cellPciTextView;
    TextView cellTacTextView;
    List<CellInfo> cellInfoList;
    int cellSig, cellID, cellMcc, cellMnc, cellPci, cellTac = 0, cellRssnr, cellRsrq, cellCqi;
    TelephonyManager tm;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText1);
        call_btn = findViewById(R.id.button1);
        call_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + et.getText().toString()));
                startActivity(i);
            }
        });
        pb = findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);
        editLocation = findViewById(R.id.editTextLocation);
        Button btnGetLocation = findViewById(R.id.btnLocation);
        btnGetLocation.setOnClickListener(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getNetworkClass(getApplicationContext());
        signalStrengthTextView = (TextView) findViewById(R.id.signalStrengthTextView);
        signalStrengthTextView2 = (TextView) findViewById(R.id.signalStrengthTextView2);
        cellIDTextView = (TextView) findViewById(R.id.cellIDTextView);
        cellMccTextView = (TextView) findViewById(R.id.cellMccTextView);
        cellMncTextView = (TextView) findViewById(R.id.cellMncTextView);
        cellPciTextView = (TextView) findViewById(R.id.cellPciTextView);
        cellTacTextView = (TextView) findViewById(R.id.cellTacTextView);
        signalStrengthListener = new SignalStrengthListener();

        ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).listen(signalStrengthListener, SignalStrengthListener.LISTEN_SIGNAL_STRENGTHS);
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        try {
            cellInfoList = tm.getAllCellInfo();
        } catch (Exception e) {
            Log.d("SignalStrength", "+++++++++++++++++++++++++++++++++++++++++ null array spot 1: " + e);
        }
        try {
            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo instanceof CellInfoLte) {
                    // cast to CellInfoLte and call all the CellInfoLte methods you need
                    // gets RSRP cell signal strength:
                    cellSig = ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();

                    // Gets the LTE cell identity: (returns 28-bit Cell Identity, Integer.MAX_VALUE if unknown)
                    cellID = ((CellInfoLte) cellInfo).getCellIdentity().getCi();

                    // Gets the LTE MCC: (returns 3-digit Mobile Country Code, 0..999, Integer.MAX_VALUE if unknown)
                    cellMcc = ((CellInfoLte) cellInfo).getCellIdentity().getMcc();

                    // Gets theLTE MNC: (returns 2 or 3-digit Mobile Network Code, 0..999, Integer.MAX_VALUE if unknown)
                    cellMnc = ((CellInfoLte) cellInfo).getCellIdentity().getMnc();

                    // Gets the LTE PCI: (returns Physical Cell Id 0..503, Integer.MAX_VALUE if unknown)
                    cellPci = ((CellInfoLte) cellInfo).getCellIdentity().getPci();

                    // Gets the LTE TAC: (returns 16-bit Tracking Area Code, Integer.MAX_VALUE if unknown)
                    cellTac = ((CellInfoLte) cellInfo).getCellIdentity().getTac();

                    cellRssnr = ((CellInfoLte) cellInfo).getCellSignalStrength().getRssnr();

                    cellRsrq = ((CellInfoLte) cellInfo).getCellSignalStrength().getRsrq();

                    cellCqi = ((CellInfoLte) cellInfo).getCellSignalStrength().getCqi();
                }

            }
        } catch (Exception e) {
            Log.d("SignalStrength", "++++++++++++++++++++++ null array spot 2: " + e);
        }


        String filePath = "C:\\Users\\Khushi Donthi\\StudioProjects\\MEASDrone_Project\\app\\src\\main\\res\\data.csv";
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "CellDbm", "CellID", "CellMcc", "CellMnc", "CellPCI", "CellTAC", "CellRssnr", "CellRsrq", "CellCqi" };
            writer.writeNext(header);
            // add data to csv
            String[] data1 = {String.valueOf(cellSig), String.valueOf(cellID), String.valueOf(cellMcc), String.valueOf(cellMnc), String.valueOf(cellPci), String.valueOf(cellTac), String.valueOf(cellRssnr), String.valueOf(cellRsrq), String.valueOf(cellCqi)};
            writer.writeNext(data1);
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        try{
            if(signalStrengthListener != null){tm.listen(signalStrengthListener, SignalStrengthListener.LISTEN_NONE);}
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();

        try{
            if(signalStrengthListener != null){tm.listen(signalStrengthListener, SignalStrengthListener.LISTEN_NONE);}
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        Log.v(TAG, "onClick");

        editLocation.setText("Please move your device to see the changes in coordinates.\nWait..");

        pb.setVisibility(View.VISIBLE);
        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            editLocation.setText("");
            pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(), "Location changed : Lat: " +
                            loc.getLatitude() + " Lng: " + loc.getLongitude(),
                    Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v(TAG, latitude);
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String s = longitude + "\n" + latitude +
                    "\n\nMy Current City is: " + cityName;
            editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

    public void getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") int networkType = Objects.requireNonNull(mTelephonyManager).getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN: {
                Toast.makeText(getApplicationContext(), "Connection Available is 2G", Toast.LENGTH_SHORT).show();
            }
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP: {
                Toast.makeText(getApplicationContext(), "Connection Available is 3G",
                        Toast.LENGTH_SHORT).show();
            }
            case TelephonyManager.NETWORK_TYPE_LTE: {
                Toast.makeText(getApplicationContext(), "Connection Available is 4G",
                        Toast.LENGTH_SHORT).show();
            }
            return;
            default:
            case TelephonyManager.NETWORK_TYPE_GSM:
                break;
            case TelephonyManager.NETWORK_TYPE_IWLAN:
                break;
            case TelephonyManager.NETWORK_TYPE_NR:
                break;
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                break;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                break;
        }
    }

    private class SignalStrengthListener extends PhoneStateListener {
        @SuppressLint("MissingPermission")
        @Override
        public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) {

            //++++++++++++++++++++++++++++++++++

            ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).listen(signalStrengthListener, SignalStrengthListener.LISTEN_SIGNAL_STRENGTHS);

            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            String ltestr = signalStrength.toString();
            String[] parts = ltestr.split(" ");
            String cellSig2 = parts[9];

            try {
                cellInfoList = tm.getAllCellInfo();
                for (CellInfo cellInfo : cellInfoList) {
                    if (cellInfo instanceof CellInfoLte) {
                        // cast to CellInfoLte and call all the CellInfoLte methods you need
                        // gets RSRP cell signal strength:
                        cellSig = ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();

                        // Gets the LTE cell identity: (returns 28-bit Cell Identity, Integer.MAX_VALUE if unknown)
                        cellID = ((CellInfoLte) cellInfo).getCellIdentity().getCi();

                        // Gets the LTE MCC: (returns 3-digit Mobile Country Code, 0..999, Integer.MAX_VALUE if unknown)
                        cellMcc = ((CellInfoLte) cellInfo).getCellIdentity().getMcc();

                        // Gets theLTE MNC: (returns 2 or 3-digit Mobile Network Code, 0..999, Integer.MAX_VALUE if unknown)
                        cellMnc = ((CellInfoLte) cellInfo).getCellIdentity().getMnc();

                        // Gets the LTE PCI: (returns Physical Cell Id 0..503, Integer.MAX_VALUE if unknown)
                        cellPci = ((CellInfoLte) cellInfo).getCellIdentity().getPci();

                        // Gets the LTE TAC: (returns 16-bit Tracking Area Code, Integer.MAX_VALUE if unknown)
                        cellTac = ((CellInfoLte) cellInfo).getCellIdentity().getTac();

                        cellRssnr = ((CellInfoLte) cellInfo).getCellSignalStrength().getRssnr();

                        cellRsrq = ((CellInfoLte) cellInfo).getCellSignalStrength().getRsrq();

                        cellCqi = ((CellInfoLte) cellInfo).getCellSignalStrength().getCqi();
                    }
                }
            } catch (Exception e) {
                Log.d("SignalStrength", "+++++++++++++++++++++++++++++++ null array spot 3: " + e);
            }
            signalStrengthTextView.setText(String.valueOf(cellSig));
            signalStrengthTextView2.setText(String.valueOf(cellSig2));
            cellIDTextView.setText(String.valueOf(cellID));
            cellMccTextView.setText(String.valueOf(cellMcc));
            cellMncTextView.setText(String.valueOf(cellMnc));
            cellPciTextView.setText(String.valueOf(cellPci));
            cellTacTextView.setText(String.valueOf(cellTac));
            super.onSignalStrengthsChanged(signalStrength);
        }
    }

}