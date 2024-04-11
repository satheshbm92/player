package intuit.player.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private Integer playerId; // Unique ID from the CSV
    private String name;
    private Integer age;
    private String team;
    private String position;

    public Player() {
    }

    public Player(String name, Integer age, String team, String position) {
        this.name = name;
        this.age = age;
        this.team = team;
        this.position = position;
    }

    public Player(Long id, String name, Integer age, String team, String position) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.team = team;
        this.position = position;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    // Constructors, getters, and setters
}
