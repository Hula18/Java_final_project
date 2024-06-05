package org.example.java_final_project.Client.Controller.Interface;

public interface LoginCallBack {
    void onLoginSuccess() ;
    void onLoginFailure(String message) ;
    void OnSignUpSuccess() ;
    void OnSignUpFailure(String message) ;
    void logOutSuccess();
}
