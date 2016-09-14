package gojek.tech.museumsearch.profile;

import gojek.tech.museumsearch.model.ProfileResponse;
import gojek.tech.museumsearch.network.NetworkManager;

public class ProfilePresenter {
    private NetworkManager networkManager;
    private ProfileActivity profileActivity;


    public ProfilePresenter(ProfileActivity profileActivity, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.profileActivity = profileActivity;
    }

    public void getProfileMuseum(String id)
    {
        networkManager.getMuseumProfile(id, new NetworkManager.MuseumProfileCallback() {
            @Override
            public void museumProfileSuccess(ProfileResponse response) {
                profileActivity.displayProfile(response.data.get(0));
            }

            @Override
            public void museumProfileError(Throwable e) {
                profileActivity.displayError(e);
            }
        });
    }
}
