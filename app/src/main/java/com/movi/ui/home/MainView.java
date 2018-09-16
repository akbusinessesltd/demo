package com.movi.ui.home;

import com.movi.global.BaseView;
import com.movi.model.Response;

import java.util.List;

public interface MainView extends BaseView {
    void successResponse(List<Response> list);
}
