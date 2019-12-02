package people;

public class Employee extends Person {
    private Position position;
    private CurrentStatus currentStatus;

    public Employee(String email, String password, String name, String surname, long created) {
        super(email, password, name, surname, created);
    }
}
