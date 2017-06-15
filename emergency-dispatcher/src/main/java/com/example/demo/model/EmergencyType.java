package com.example.demo.model;

/**
 * Created by msalatino on 15/06/2017.
 */
public class EmergencyType {
    private String code;
    private String description;

    public EmergencyType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EmergencyType{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
