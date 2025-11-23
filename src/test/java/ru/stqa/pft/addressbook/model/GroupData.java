package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id", "name"})
public class GroupData {
    @Expose
    private String name;
    @Expose
    private String header;
    @Expose
    private String footer;
    private int id = 0; // Установим id в 0 по умолчанию

    // Конструктор
    public GroupData(String name, String header, String footer) {
        this.name = name;
        this.header = header;
        this.footer = footer;
    }
    public GroupData() {}

    // Строители (chaining methods)
    public GroupData withId(int id) {
        this.id = id;
        return this;
    }

    public GroupData withName(String name) {
        this.name = name;
        return this;
    }

    public GroupData withHeader(String header) {
        this.header = header;
        return this;
    }

    public GroupData withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    // Переопределение методов equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupData)) return false;
        GroupData that = (GroupData) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}



//package ru.stqa.pft.addressbook.model;
//
//import com.google.gson.annotations.Expose;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.openqa.selenium.By;
//
//
//import java.util.Objects;
//
//import static java.lang.Integer.MAX_VALUE;
//import com.thoughtworks.xstream.annotations.XStreamAlias;
//
////@XStreamAlias("GroupData")
//
//
//@Getter
//public class GroupData {
//    // Геттеры и сеттеры
//    @Setter
//    @Expose
//    private String name;
//    @Setter
//    @Expose
//    private String header;
//    @Setter
//    @Expose
//    private String footer;
//
//    private int id = MAX_VALUE;
//
//    public GroupData withId(int id) {
//        this.id = id;
//        return this;
//    }
//
//    public GroupData withName(String name) {
//        this.name = name;
//        return this;
//    }
//
//    public GroupData withHeader(String header) {
//        this.header = header;
//        return this;
//    }
//
//    public GroupData withFooter(String footer) {
//        this.footer = footer;
//        return this;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) return false;
//
//        GroupData groupData = (GroupData) o;
//        return id == groupData.id && Objects.equals(name, groupData.name);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id;
//        result = 31 * result + Objects.hashCode(name);
//        return result;
//    }
//
//    // toString для отладки
//    @Override
//    public String toString() {
//        return "GroupData{" +
//                "name='" + name + '\'' +
//                ", header='" + header + '\'' +
//                ", footer='" + footer + '\'' +
//                '}';
//    }
//
//
//}


