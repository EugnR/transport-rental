package entity;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDataBase {
    private static InMemoryDataBase INSTANCE;

    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Transport> transports = new HashMap<>();
    private final Map<Long, Rent> rents = new HashMap<>();

    private InMemoryDataBase() {
    }


    public static InMemoryDataBase getInstance(){
        if (INSTANCE == null){
            INSTANCE = new InMemoryDataBase();
        }
        return INSTANCE;
    }



}
