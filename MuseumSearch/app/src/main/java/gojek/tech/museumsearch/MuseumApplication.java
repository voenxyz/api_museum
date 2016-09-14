package gojek.tech.museumsearch;

import android.app.Application;

import gojek.tech.museumsearch.deps.AppComponents;
import gojek.tech.museumsearch.deps.AppModule;
import gojek.tech.museumsearch.deps.DaggerAppComponents;

public class MuseumApplication extends Application {
    private AppComponents appComponents;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponents = DaggerAppComponents.builder().appModule(new AppModule()).build();
    }

    public AppComponents providesAppComponents() {
        return appComponents;
    }
}
