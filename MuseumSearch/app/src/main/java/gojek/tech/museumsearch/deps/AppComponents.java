package gojek.tech.museumsearch.deps;

import javax.inject.Singleton;

import dagger.Component;
import gojek.tech.museumsearch.home.HomeActivity;
import gojek.tech.museumsearch.profile.ProfileActivity;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponents {
    void inject(HomeActivity homeActivity);
    void inject(ProfileActivity profileActivity);
}
