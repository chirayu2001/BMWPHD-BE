package net.bmw.bmwphd.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "rider")
public class Rider implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String riderId;
    private String name;
//    @ManyToMany
//    private List<Horse> horseList;

    public Rider() {

    }

//    public Rider(String riderId, String name, List<Horse> horseList) {
//        this.riderId = riderId;
//        this.name = name;
//        this.horseList = horseList;
//    }


    public Rider(String riderId, String name) {
        this.riderId = riderId;
        this.name = name;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Horse> getHorseList() {
//        return horseList;
//    }
//
//    public void setHorseList(List<Horse> horseList) {
//        this.horseList = horseList;
//    }
}
