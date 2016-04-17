package com.expandablenavdrawer;

import java.util.ArrayList;

/**
 * Created by vivek on 24/03/16.
 */
public class NavigationCategory {
    private String category;
    private ArrayList<String> subcategories=new ArrayList<>();

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getSubcategories() {
        return subcategories;
    }
}
