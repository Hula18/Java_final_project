package org.example.java_final_project.Server.Model;
// Class dùng để chứa dữ liệu của người dùng
public class User {
    private int ID ;
    private String username;
    private String email ;
    private String password;
    private boolean IsOnline ;
    private boolean IsPlaying ;
    public User(){

    }
    public User(int ID, String username, String email, String password, boolean isOnline, boolean isPlaying) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        IsOnline = isOnline;
        IsPlaying = isPlaying;
    }
    public User(int ID, String nickname, boolean isOnline, boolean isPlaying) {
        this.ID = ID;

        this.IsOnline = isOnline;
        this.IsPlaying = isPlaying;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isOnline() {
        return IsOnline;
    }
    public void setOnline(boolean online) {
        IsOnline = online;
    }
    public boolean isPlaying() {
        return IsPlaying;
    }
    public void setPlaying(boolean playing) {
        IsPlaying = playing;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", IsOnline=" + IsOnline +
                ", IsPlaying=" + IsPlaying +
                '}';
    }
}
