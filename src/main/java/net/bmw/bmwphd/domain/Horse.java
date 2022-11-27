package net.bmw.bmwphd.domain;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Horse implements Serializable {
    @Id
    private String id;
    private String name;
   // private String dam;
   // private String sire;
    private String sire1;
    private String dam1;
    private String sire2;
    private String dam2;

    public Horse() {
    }

    public Horse(String id, String name, String sire1, String dam1, String sire2, String dam2){
        this.id = id;
        this.name = name;
        this.sire1 = sire1;
        this.dam1 = dam1;
        this.sire2 = sire2;
        this.dam2 = dam2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSire1() {
        return sire1;
    }

    public void setSire1(String sire1) {
        this.sire1 = sire1;
    }

    public String getDam1() {
        return dam1;
    }

    public void setDam1(String dam1) {
        this.dam1 = dam1;
    }

    public String getSire2() {
        return sire2;
    }

    public void setSire2(String sire2) {
        this.sire2 = sire2;
    }

    public String getDam2() {
        return dam2;
    }

    public void setDam2(String dam2) {
        this.dam2 = dam2;
    }
}
