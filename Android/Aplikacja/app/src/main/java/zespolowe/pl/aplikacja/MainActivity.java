package zespolowe.pl.aplikacja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("MainActivity");

        startActivity(new Intent(MainActivity.this, Log_Rej_Activity.class));
        finish();

        /*if (!isNetworkAvailable()) {
            AlertDialog.Builder Checkbuilder = new AlertDialog.Builder(MainActivity.this,
                    R.style.AppTheme_PopupOverlay);//                    R.style.AlertDialog_BuilderCustom);//

            Checkbuilder.setIcon(R.mipmap.ic_launcher);
            Checkbuilder.setTitle("Błąd!");
            Checkbuilder.setMessage("Sprawdź połączenie z internetem.");
            Checkbuilder.setPositiveButton("Powtórz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            Checkbuilder.setNegativeButton("Zamknij", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alert = Checkbuilder.create();
            alert.show();
        } else {
            if (isNetworkAvailable()) {
                Intent intent = new Intent(this, Log_Rej_Activity.class);
                startActivity(intent);
              *//*  Thread tr=new Thread(){
                    public  void  run(){
                        try {
                            sleep(4500);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            startActivity(new Intent(MainActivity.this,Log_Rej_Activity.class));
                            finish();
                        }
                    }
                };*//*
                // tr.start();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }*/
        //  android.net.wifi.IWifiManager
    }
}
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
//}