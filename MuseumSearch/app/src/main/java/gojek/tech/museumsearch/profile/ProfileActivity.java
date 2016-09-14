package gojek.tech.museumsearch.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import gojek.tech.museumsearch.MuseumApplication;
import gojek.tech.museumsearch.R;
import gojek.tech.museumsearch.model.ProfileData;
import gojek.tech.museumsearch.network.NetworkManager;
import gojek.tech.museumsearch.util.Constant;

public class ProfileActivity extends AppCompatActivity {
    @Inject
    public NetworkManager networkManager;
    private String museumId;
    private ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        if(!bundle.getString(Constant.PREFERENCE_MUSEUM_ID).isEmpty())
            museumId = bundle.getString(Constant.PREFERENCE_MUSEUM_ID);

        ((MuseumApplication) getApplication()).providesAppComponents().inject(this);

        profilePresenter = new ProfilePresenter(this, networkManager);

        profilePresenter.getProfileMuseum(museumId);
    }

    public void displayProfile(ProfileData response)
    {
        TextView nama = (TextView) findViewById(R.id.nama);
        TextView alamat = (TextView) findViewById(R.id.alamat_desc);
        TextView kodePengelola = (TextView) findViewById(R.id.kode_pengelolaan_desc);
        TextView pengelola = (TextView) findViewById(R.id.pengelola_desc);

        nama.setText(response.getNama());
        alamat.setText(response.getAlamat_jalan()+", "+response.getDesa_kelurahan()+", "+response.getKecamatan()+", "+response.getKabupaten_kota()+", "+response.getPropinsil());
        kodePengelola.setText(response.getKode_pengelolaan());
        pengelola.setText(response.getPengelola());
    }

    public void displayError(Throwable e)
    {
        Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
