package com.example.foodspace;

public class viewPagerTabModel {
    private String tab_name;
    private String label;
private String title;

    public viewPagerTabModel(String tab_name, String label,String title) {
        this.tab_name = tab_name;
        this.label = label;
        this.title=title;

    }

    public String getTab_name() {
        return tab_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
