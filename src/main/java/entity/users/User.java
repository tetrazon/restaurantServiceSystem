package entity.users;

import javax.persistence.*;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private long created;

    public User(){

    }

    public User(int id){
        this.id = id;
    }

    public User(String email, String name, String surname, long created){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.created = created;
    }

    public User(int id, String email, String name, String surname, long created){
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.created = created;
    }

    public User(String email, String password, String name, String surname, long created) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getPassword() {
        return password;
    }
}
