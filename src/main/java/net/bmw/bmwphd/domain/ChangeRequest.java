package net.bmw.bmwphd.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 * Class for ChangeRequest Entity
 * </p>
 *
 * @author Chirayu Jain
 */
@Entity(name = "change_requests")
public class ChangeRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String attribute;
    private String suggestedChange;
    private Integer ownerId;
    private String status;
    private String horseId;

    public ChangeRequest() {

    }

    public ChangeRequest(Integer id, String attribute, String suggestedChange, String status, Integer ownerId, String horseId) {
        this.id = id;
        this.attribute = attribute;
        this.suggestedChange = suggestedChange;
        this.status = status;
        this.ownerId = ownerId;
        this.horseId = horseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String columnName) {
        this.attribute = columnName;
    }

    public String getSuggestedChange() {
        return suggestedChange;
    }

    public void setSuggestedChange(String suggestedChange) {
        this.suggestedChange = suggestedChange;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHorseId() {
        return horseId;
    }

    public void setHorseId(String horseId) {
        this.horseId = horseId;
    }
}
