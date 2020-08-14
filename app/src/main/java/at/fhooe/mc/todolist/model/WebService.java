package at.fhooe.mc.todolist.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WebService {
    // representation of an application object
    Application mApplication;

    /**
     * Constructor
     * @param _application
     */
    public WebService(Application _application){
        this.mApplication = _application;
    }

    /**
     * Provides an OkHttpClientBuilder object
     * @return a httpClient object
     */
    private static OkHttpClient providesOkHttpClientBuilder(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();
    }

    /**
     * initialize a new list with tasks
     */
    List<Task> mWebserviceRespList = new ArrayList<>();

    /**
     * Provides a WebService
     * @return the mutable liveData
     */
    public LiveData<List<Task>> providesWebService() {
        final MutableLiveData<List<Task>> data = new MutableLiveData<>();

        String response = "";
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            //Defining retrofit api service
            APIService service = retrofit.create(APIService.class);
            service.makeRequest().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> _call, Response<String> _response) {
                    Log.d("WebService","Response::::"+_response.body());
                    mWebserviceRespList = parseJson(_response.body());
                    DatabaseClient roomDBRepo = new DatabaseClient(mApplication);
                    roomDBRepo.insertTask(mWebserviceRespList);
                    data.setValue(mWebserviceRespList);

                }

                @Override
                public void onFailure(Call<String> _call, Throwable _t) {
                    Log.d("WebService","Failed:::");
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return  data;
    }

    /**
     * Method to parse a String to Json
     * @param _response the given response
     * @return the list of tasks
     */
    private List<Task> parseJson(String _response) {
        List<Task> apiResults = new ArrayList<>();

        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(_response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                Task mModel= new Task();
                mModel.setId(Integer.parseInt(object.getString("id")));
                mModel.setTask(object.getString("task"));
                mModel.setDesc(object.getString("description"));
                mModel.setFinished(object.getBoolean("finished"));

                apiResults.add(mModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;
    }
}
