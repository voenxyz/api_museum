package gojek.tech.museumsearch.deps;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gojek.tech.museumsearch.network.NetworkManager;

@Module
public class AppModule {

    @Provides
    @Singleton
    public NetworkManager providesNetworkManager() {
        return new NetworkManager();
    }
}
