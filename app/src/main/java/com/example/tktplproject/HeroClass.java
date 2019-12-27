package com.example.tktplproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "classes")
public class HeroClass {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "class_name")
    public String className;

    @ColumnInfo(name = "class_description")
    public String classDescription;

    @ColumnInfo(name = "class_power")
    public String classPower;

    @ColumnInfo(name = "class_power_description")
    public String classPowerDescription;

    @ColumnInfo(name = "class_image_URL")
    public String classImageURL;

    public HeroClass(String className, String classDescription, String classPower,
                     String classPowerDescription, String classImageURL) {
        this.className = className;
        this.classDescription = classDescription;
        this.classPower = classPower;
        this.classPowerDescription = classPowerDescription;
        this.classImageURL = classImageURL;
    }
}
