package ru.stqa.pft.addressbook.model;

import lombok.Data;
import org.openqa.selenium.By;


import java.util.Objects;

import static java.lang.Integer.MAX_VALUE;

public class GroupData {
    private int id = MAX_VALUE;
    private String name;
    private String header;
    private String footer;

    public int getId() {return id;}
    public String getName() {return name;}
    public String getHeader() {return header;}
    public String getFooter() {return footer;}

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        GroupData groupData = (GroupData) o;
        return id == groupData.id && Objects.equals(name, groupData.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(name);
        return result;
    }
}


//public class GroupData {
//
//    private int id;
//    private String name;
//    private String header;
//    private String footer;
//
//    // Конструктор с передачей всех полей
//    public GroupData(Integer id, String name, String header, String footer) {
//        this.id = id;
//        this.name = name;
//        this.header = header;
//        this.footer = footer;
//    }
//
//    // Конструктор без параметров (может понадобиться)
//    public GroupData() {
//    }
//
//    // Второй конструктор с параметрами без id
//    public GroupData(String name, String header, String footer) {
//        this(null, name, header, footer);
//    }
//
//    // Конструктор только с id и name
//    public GroupData(Integer id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    // Builder-style методы для формирования объекта
//    public GroupData withId(Integer id) {
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
//        this.header = footer;
//        return this;
//    }
//
//    // Метод доступа к полю id
//    public int getId() {
//        return id != null ? id : 0; // Для гарантии возвращения числового значения
//    }
//}


