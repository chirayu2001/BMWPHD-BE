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
    private String maneuver_score;
    private String lte;
    private String pe;
    private String show;
    private String horse_class;
    private String level;

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

    public Horse(String id, String name, String sire1, String dam1, String sire2, String dam2, String maneuver_score, String lte, String pe, String show, String horse_class, String level) {
        this.id = id;
        this.name = name;
        this.sire1 = sire1;
        this.dam1 = dam1;
        this.sire2 = sire2;
        this.dam2 = dam2;
        this.maneuver_score = maneuver_score;
        this.lte = lte;
        this.pe = pe;
        this.show = show;
        this.horse_class = horse_class;
        this.level = level;
    }

    public String getManeuver_score() {
        return maneuver_score;
    }

    public void setManeuver_score(String maneuver_score) {
        this.maneuver_score = maneuver_score;
    }

    public String getLte() {
        return lte;
    }

    public void setLte(String lte) {
        this.lte = lte;
    }

    public String getPe() {
        return pe;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getHorse_class() {
        return horse_class;
    }

    public void setHorse_class(String horse_class) {
        this.horse_class = horse_class;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
