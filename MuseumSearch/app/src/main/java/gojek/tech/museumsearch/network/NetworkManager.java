package gojek.tech.museumsearch.network;


import gojek.tech.museumsearch.model.MuseumNameResponse;
import gojek.tech.museumsearch.model.ProfileResponse;
import gojek.tech.museumsearch.util.Constant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NetworkManager {
    private NetworkService networkService;

    public NetworkManager() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }

    public Subscription getMuseumProfile(String id, final MuseumProfileCallback callback)
    {
        return networkService.getProfile(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ProfileResponse>>() {
                    @Override
                    public Observable<? extends ProfileResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ProfileResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.museumProfileError(e);
                    }

                    @Override
                    public void onNext(ProfileResponse response) {
                        callback.museumProfileSuccess(response);
                    }
                });
    }

    public Subscription getMuseumByName(String name, final NamaMuseumCallback callback)
    {
        return networkService.getMuseumByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MuseumNameResponse>>() {
                    @Override
                    public Observable<? extends MuseumNameResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MuseumNameResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.namaMuseumError(e);
                    }

                    @Override
                    public void onNext(MuseumNameResponse response) {
                        callback.namaMuseumSuccess(response);
                    }
                });
    }

    public interface MuseumProfileCallback
    {
        void museumProfileSuccess(ProfileResponse response);
        void museumProfileError(Throwable e);
    }

    public interface NamaMuseumCallback
    {
        void namaMuseumSuccess(MuseumNameResponse response);
        void namaMuseumError(Throwable e);
    }
}
