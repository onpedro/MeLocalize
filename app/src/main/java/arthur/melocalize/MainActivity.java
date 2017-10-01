package arthur.melocalize;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnGPS;
    TextView txtLatitude, txtLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);

        btnGPS = (Button) findViewById(R.id.btnGPS);
        btnGPS.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                pedirPermissoes();
            }

        });
    }

    private void pedirPermissoes(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            configurarServico();
        }

    }

    public void configurarServico(){
        try{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {

                public void onLocationChanged(Location location) {
                    atualizar(location);
                }
                public void onStatusChanged(String provider, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}

            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void atualizar(Location location){
        Double lagPoint = location.getLatitude();
        Double lngPoint = location.getLongitude();

        txtLatitude.setText(lagPoint.toString());
        txtLongitude.setText(lngPoint.toString());

    }
}
