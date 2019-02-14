package com.example.learningapi.activity.editor;

import android.support.annotation.NonNull;

import com.example.learningapi.api.ApiClient;
import com.example.learningapi.api.ApiInterface;
import com.example.learningapi.model.Biodata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {

    //mengambil isi interface EditorView dan di inisialisasi ke variable view
    private EditorView view;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    //    function save
    void saveData(final String nama, final String alamat) {

        view.showProgress(); //Progress Dialog Show

        // memanggil class ApiClient.dan method getApiClient dan membuat class ApiInterface
        // lalu menampungnya ke variable apiInterface
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Call adalah interface retrofit2 yang akan membuat variable call
        //variable call ini akan menampung hasil execute dari class ApiInterface
        //data yang akan di simpan atau di ambil semua melalui ke file Biodata
        // dengan method saveData(nama,alamat) yang ada di file ApiInterface
        Call<Biodata> call = apiInterface.saveData(nama, alamat);

        call.enqueue(new Callback<Biodata>() {
            @Override
            public void onResponse(@NonNull Call<Biodata> call, @NonNull Response<Biodata> response) {
                view.hideProgress();

//                menampilkan data dengan menggunakan "response.body().data yang mau di ambil"
//                        contohnya response.body().getId atau response.body().getSuccess()

                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        view.onRequestSuccess(response.body().getMessage());
                    }else{
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Biodata> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

    //method update
    void  updateBiodata(int id, String nama, String alamat){
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Biodata> call = apiInterface.updateBiodata(id, nama, alamat);
        call.enqueue(new Callback<Biodata>() {
            @Override
            public void onResponse(@NonNull Call<Biodata> call, @NonNull Response<Biodata> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        view.onRequestSuccess(response.body().getMessage());
                    }else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Biodata> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

    void deleteBiodata(int id){
        view.showProgress();
        final ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Biodata> call = apiInterface.deleteBiodata(id);
        call.enqueue(new Callback<Biodata>() {
            @Override
            public void onResponse(@NonNull Call<Biodata> call, @NonNull Response<Biodata> response) {
                view.hideProgress();

                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        view.onRequestSuccess(response.body().getMessage());
                    }else{
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Biodata> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });

    }
}
