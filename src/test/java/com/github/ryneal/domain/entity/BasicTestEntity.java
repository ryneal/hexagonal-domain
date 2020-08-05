package com.github.ryneal.domain.entity;

public class BasicTestEntity implements Identifiable<Integer>, Categorized<Long>, Related<Integer, ParentTestEntity, String> {

    private Long category;
    private Integer id;

    @Override
    public boolean isCategory(Long aLong) {
        return this.category.equals(aLong);
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    @Override
    public ParentTestEntity getParent() {
        return null;
    }

    @Override
    public void setParent(ParentTestEntity parentTestEntity) {

    }
}
