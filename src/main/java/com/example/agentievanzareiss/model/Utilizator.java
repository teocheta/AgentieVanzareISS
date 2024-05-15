package com.example.agentievanzareiss.model;
import jakarta.persistence.*;

@Entity
@Table(name = "utilizatori")
public class Utilizator implements Identifiable<Integer>{


    private Integer id;

    private String username;

    private String password;

    public Utilizator(){id = 0;}

    public Utilizator(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Column(name = "username")
    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(generator = "increment")
    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;

    }

}
