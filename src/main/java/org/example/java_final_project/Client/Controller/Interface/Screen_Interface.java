package org.example.java_final_project.Client.Controller.Interface;

import java.math.BigDecimal;

public interface Screen_Interface {
    void Check_Last_Password(String message);
    void Change_Password_Success();
    void Xac_Thuc_True() ;
    void Xac_Thuc_False() ;
    void Chuyen_Tien_Thanh_Cong() ;
    void Chuyen_Tien_Khong_Thanh_Cong() ;
    void Lay_Du_Lieu_Thanh_Cong(String fullName , BigDecimal balance) ;
    void UpdateBalance(BigDecimal balance) ;
}
