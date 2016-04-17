package com.expandablenavdrawer;

import java.io.Serializable;
import java.util.List;

public class Categories implements Serializable{
    public String name;
    public List<String> subcategories;

    public String getName() {
        return name;
    }

    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setName(String name) {
        this.name = name;
    }

}
