package com.dev.eda.frame.view.model;

public class ItemDetailCheckSelect {

    private String value;

    private String desc;

    private boolean isChecked;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "ItemDetailCheckSelect{" +
                "value='" + value + '\'' +
                ", desc='" + desc + '\'' +
                ", isChecked='" + isChecked + '\'' +
                '}';
    }
}
