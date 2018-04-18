package com.example.leove.beeramide;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by leove on 17/04/2018.
 */

public class Rule implements Parcelable{

    private String rule;
    private boolean repeatable;
    private ruleType type;

    //CONSTRUCTEUR
    public Rule(String rul, boolean rep, ruleType typ){
        this.rule = rul;
        this.repeatable = rep;
        this.type = typ;
    }

    //SETTERS
    public void setRule(String r){
     this.rule = r;
    }

    public void setRepeatable(boolean b){
        this.repeatable = b;
    }

    public void setType(ruleType r){
        this.type = r;
    }


    //GETTERS
    public String getRule(){
        return this.rule;
    }

    public boolean getRepeatable(){
        return this.repeatable;
    }

    public ruleType getType(){
        return this.type;
    }

    //METHODES
    public String toString(){
        return "Rule: " +this.rule +" repeatable: " +this.repeatable +" type: " +this.type;
    }

    // Parcelling part
    public Rule (Parcel in){
        this.rule = in.readString();
        this.repeatable= in.readByte() != 0;
        this.type = ruleType.valueOf(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rule);
        dest.writeByte((byte) (repeatable ? 1 : 0));
        dest.writeString(this.type.name());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Rule createFromParcel(Parcel in) {
            return new Rule(in);
        }

        public Rule[] newArray(int size) {
            return new Rule[size];
        }
    };


    public enum ruleType {
        CUSTOM,
        BOTTOMSUP
    }
}
