package com.github.ryneal.domain.entity;

public class BasicTestEntity implements Identifiable<Integer>, Categorical<TestCategory>, Related<Integer, ParentTestEntity, String> {

    private Integer id;
    private TestCategory category;
    private ParentTestEntity parent;

    public BasicTestEntity(Integer id, TestCategory category, ParentTestEntity parent) {
        this.id = id;
        this.category = category;
        this.parent = parent;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public ParentTestEntity getParent() {
        return this.parent;
    }

    @Override
    public void setParent(ParentTestEntity parent) {
        this.parent = parent;
    }

    @Override
    public TestCategory getCategory() {
        return this.category;
    }
}
