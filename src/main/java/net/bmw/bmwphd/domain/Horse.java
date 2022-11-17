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
    private String dam;
    private String sire;

    public Horse() {
    }

    public Horse(String id, String name, String sire, String dam){
        this.id = id;
        this.name = name;
        this.sire = sire;
        this.dam = dam;
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

    public String getDam() {
        return dam;
    }

    public void setDam(String dam) {
        this.dam = dam;
    }

    public String getSire() {
        return sire;
    }

    public void setSire(String sire) {
        this.sire = sire;
    }
}
