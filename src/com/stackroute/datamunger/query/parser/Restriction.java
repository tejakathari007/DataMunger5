package com.stackroute.datamunger.query.parser;

/*
* This class is used for storing name of field, condition and value for
* each conditions
* generate getter and setter for this class,
* Also override toString method
* */

public class Restriction {
    private String name;
    private String value;
    private String condition;

    // Write logic for constructor
    public Restriction(String name, String value, String condition) {
        this.name = name;
        this.value = value;
        this.condition = condition;

    }

    public String getPropertyName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        return;
    }

    public String getPropertyValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        return;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
        return;
    }

    public String toString() {
        return name+ " " +condition+ " " +value;
    }

}