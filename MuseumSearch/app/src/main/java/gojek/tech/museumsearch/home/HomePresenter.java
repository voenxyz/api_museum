package gojek.tech.museumsearch.home;

import android.util.Log;

import java.util.ArrayList;

import gojek.tech.museumsearch.model.MuseumNameResponse;
import gojek.tech.museumsearch.model.ProfileData;
import gojek.tech.museumsearch.network.NetworkManager;

public class HomePresenter {
    private NetworkManager networkManager;
    private HomeActivity homeActivity;

    public HomePresenter(HomeActivity view, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.homeActivity = view;
    }

    public void searchByName(String nama)
    {
        networkManager.getMuseumByName(nama, new NetworkManager.NamaMuseumCallback() {
            @Override
            public void namaMuseumSuccess(MuseumNameResponse response) {
                ArrayList<ProfileData> profileDatas = response.data;
                final String[] ids = new String[profileDatas.size()];
                String[] titles = new String[profileDatas.size()];
                String[] descs = new String[profileDatas.size()];

                for(int i = 0; i < profileDatas.size(); i++)
                {
                    ids[i] = profileDatas.get(i).getMuseum_id();
                    titles[i] = profileDatas.get(i).getNama();
                    descs[i] = profileDatas.get(i).getAlamat_jalan();
                }

                homeActivity.displayResult(ids, titles, descs);
            }

            @Override
            public void namaMuseumError(Throwable e) {
                Log.i("VOEN","ERROR : "+e.toString());
            }
        });
    }
}
