package gojek.tech.museumsearch.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import javax.inject.Inject;

import gojek.tech.museumsearch.MuseumApplication;
import gojek.tech.museumsearch.R;
import gojek.tech.museumsearch.network.NetworkManager;
import gojek.tech.museumsearch.profile.ProfileActivity;
import gojek.tech.museumsearch.util.Constant;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    public NetworkManager networkManager;
    private HomePresenter homePresenter;
    private Button buttonCari;
    private EditText textCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((MuseumApplication) getApplication()).providesAppComponents().inject(this);

        homePresenter = new HomePresenter(this, networkManager);
        buttonCari = (Button) findViewById(R.id.button_cari);
        textCari = (EditText) findViewById(R.id.text_cari);

        buttonCari.setOnClickListener(this);
    }

    @Override
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
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_cari)
        {
            String nama = textCari.getText().toString();
            homePresenter.searchByName(nama);
        }
    }

    public void displayResult(final String[] ids, String[] titles, String[] descs)
    {
        HomeListAdapter adapter = new HomeListAdapter(getApplicationContext(), titles, descs);
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.PREFERENCE_MUSEUM_ID, ids[i]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void displayError(Throwable e)
    {
        Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
