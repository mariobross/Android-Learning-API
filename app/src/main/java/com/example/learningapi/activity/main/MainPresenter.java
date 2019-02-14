package com.example.learningapi.activity.main;

import com.example.learningapi.api.ApiClient;
import com.example.learningapi.api.ApiInterface;
import com.example.learningapi.model.Biodata;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    void getData(){
        view.showLoading();
        //Request to server
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Biodata>> call = apiInterface.getBiodata();
        call.enqueue(new Callback<List<Biodata>>() {
            @Override
            public void onResponse(Call<List<Biodata>> call, Response<List<Biodata>> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Biodata>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
