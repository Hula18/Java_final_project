package org.example.java_final_project.Client.Controller.Interface;

public interface LoginCallBack {
    void onLoginSuccess() ;
    void onLoginFailure(String message) ;
    void OnSignUpSuccess(String fullName, String SDT) ;
    void OnSignUpFailure(String message) ;
    void logOutSuccess();
    void GetUserNameSuccess(String accountName);
    void GetUseNameFail() ;
    void Change_Pin_Success();
    void Chang_Pin_failed();
    void UserIsAlreadyUsing() ;
}
