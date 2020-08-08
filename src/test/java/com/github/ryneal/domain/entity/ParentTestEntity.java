package com.github.ryneal.domain.entity;

public class ParentTestEntity implements Identifiable<String> {

    private String id;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
