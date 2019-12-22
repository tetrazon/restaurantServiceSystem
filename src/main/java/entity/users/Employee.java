package entity.users;

import entity.enumeration.Position;

public class Employee extends User {
    private Position position;
    private int loadFactor; //0-5, 5 means busy

    public Employee(){}

    public Employee(String position){
       setPosition(position);

    }

    public Employee(String email, String password, String name, String surname, long created) {
        super(email, password, name, surname, created);
    }

    public String getPosition() {
        return position.name();
    }

    public void setPosition(String position) {
        this.position = Position.valueOf(position.toUpperCase());
    }

    public int getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }

    public void processOrder() throws InterruptedException {//imitation of processing
        if(position.toString().equalsIgnoreCase("COOK")){
            Thread.sleep(500);
        } else {
            Thread.sleep(200);
        }
    }
}
