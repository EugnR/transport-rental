package entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class User {
//    private long userId; //Нужно ли поле, если при хранении в мапе у объекста и так будет ключ?
    private String userName;
    private String password;
    private boolean isAdmin;
    private double balance;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }


}
