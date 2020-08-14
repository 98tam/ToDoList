package at.fhooe.mc.todolist.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("tasks")
    Call<String> makeRequest();
}
