package gojek.tech.museumsearch.network;

import gojek.tech.museumsearch.model.MuseumNameResponse;
import gojek.tech.museumsearch.model.ProfileResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {
    @GET("api/index.php/CcariMuseum/profilGet")
    Observable<ProfileResponse> getProfile(@Query("museum_id") String id);

    @GET("api/index.php/CcariMuseum/searchGET")
    Observable<MuseumNameResponse> getMuseumByName(@Query("nama") String nama);
}
