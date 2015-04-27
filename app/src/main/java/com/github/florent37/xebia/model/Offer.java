package com.github.florent37.xebia.model;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class Offer {

    public static final String TYPE_PERCENTAGE = "percentage";
    public static final String TYPE_MINUS = "minus";
    public static final String TYPE_SLICE = "slice";

    private String type;
    private int sliceValue;
    private int value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSliceValue() {
        return sliceValue;
    }

    public void setSliceValue(int sliceValue) {
        this.sliceValue = sliceValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "type='" + type + '\'' +
                ", sliceValue=" + sliceValue +
                ", value=" + value +
                '}';
    }
}
