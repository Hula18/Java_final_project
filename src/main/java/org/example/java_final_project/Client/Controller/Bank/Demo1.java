package org.example.java_final_project.Client.Controller.Bank;

public class Demo1 {
    private static final String[] units = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
    private static final String[] tens = {"", "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};
    private static final String[] hundreds = {"", "một trăm", "hai trăm", "ba trăm", "bốn trăm", "năm trăm", "sáu trăm", "bảy trăm", "tám trăm", "chín trăm"};

    private String convertNumberToWords(double number) {
        if (number == 0) {
            return "Không Việt Nam Đồng";
        }
        StringBuilder words = new StringBuilder();

        if ((number / 1000000000) >= 1) {
            words.append(convertNumberToWords((int) (number / 1000000000))).append(" tỷ ");
            number %= 1000000000;
        }
        if ((number / 1000000) >= 1) {
            words.append(convertNumberToWords((int) (number / 1000000))).append(" triệu ");
            number %= 1000000;
        }
        if ((number / 1000) >= 1) {
            words.append(convertNumberToWords((int) (number / 1000))).append(" nghìn ");
            number %= 1000;
        }
        if ((number / 100) >= 1) {
            words.append(hundreds[(int) (number / 100)]).append(" ");
            number %= 100;
        }
        if (number > 0) {
            if (number < 10) {
                words.append(units[(int) number]);
            } else if (number < 20) {
                words.append("mười ").append(units[(int) (number - 10)]);
            } else {
                words.append(tens[(int) (number / 10)]).append(" ").append(units[(int) (number % 10)]);
            }
        }
        return words.toString().trim();
    }

    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        String balance = demo1.convertNumberToWords(1530000);
        System.out.println(balance + " Việt Nam Đồng");
    }
}
