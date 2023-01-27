package net.bmw.bmwphd.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ChangeRequest {
    @Id
    private Integer id;
    private String columnName;
    private String suggestedChange;
    private Integer ownerId;

    public ChangeRequest(){

    }

    public ChangeRequest(Integer id, String columnName, String suggestedChange, Integer ownerId) {
        this.id = id;
        this.columnName = columnName;
        this.suggestedChange = suggestedChange;
        this.ownerId = ownerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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
}
