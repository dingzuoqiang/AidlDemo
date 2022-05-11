package com.example.aidldemo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in.readString(), in.readInt());
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", age='" + age + '\'' +
                '}';
    }
}
