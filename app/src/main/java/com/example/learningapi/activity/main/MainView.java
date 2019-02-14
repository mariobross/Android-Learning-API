package com.example.learningapi.activity.main;

import com.example.learningapi.model.Biodata;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Biodata>biodataList);
    void onErrorLoading(String message);
}
