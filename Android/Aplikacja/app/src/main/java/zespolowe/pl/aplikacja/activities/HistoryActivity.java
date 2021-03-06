package zespolowe.pl.aplikacja.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zespolowe.pl.aplikacja.R;
import zespolowe.pl.aplikacja.adapters.HistoryListAdapter;
import zespolowe.pl.aplikacja.functions.SessionManager;
import zespolowe.pl.aplikacja.model.HistoryProduct;
import zespolowe.pl.aplikacja.model.User;
import zespolowe.pl.aplikacja.services.UserService;

/**
 *  Aktywność odpowiedzialna za pobranie i wyświetlenie dokonanych spłat
 */

public class HistoryActivity extends AppCompatActivity {

    private SessionManager session;
    private User user;
    private ListView historyProductListView;
    private List<HistoryProduct> historyList;

    /**
     *  Implementacja metody onCreate z klasy Activity. Wywoływana jest w momencie tworzenia aktywności.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        historyList = new ArrayList<>();
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SessionManager.getAPIURL())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserService userService = retrofit.create(UserService.class);
                Call<List<HistoryProduct>> call = userService.getHistory(user.getId());
                try{
                    Response<List<HistoryProduct>> response = call.execute();
                    historyList = response.body();
                }catch (IOException e){
                    System.out.println(e);
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                HistoryListAdapter adapter = new HistoryListAdapter(HistoryActivity.this,historyList);
                historyProductListView = (ListView) findViewById(R.id.historyListView);
                historyProductListView.setAdapter(adapter);

                historyProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HistoryProduct product = historyList.get(position);
                        Intent intent = new Intent(HistoryActivity.this,ProductHistoryDetails.class);

                        intent.putExtra("product",product);
                        startActivity(intent);
                    }
                });

                System.out.println("History act");
            }
        }.execute();
    }
}
