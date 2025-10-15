//package ru.stqa.pft.addressbook.model;
//
//import lombok.Data;
//
//@Data
//public class GroupData {
//
//    private int id;
//    private String name;
//    private String header;
//    private String footer;
//
//    public GroupData(int id, String name, String header, String footer) {
//        this.id = id;
//        this.name = name;
//        this.header = header;
//        this.footer = footer;
//    }
//
//    public GroupData(String name, String header, String footer) {
//        this(Integer.MAX_VALUE, name, header, footer);
//    }
//}


package ru.stqa.pft.addressbook.model;

import lombok.Data;
import org.openqa.selenium.By;

@Data
public class GroupData {

    private Integer id;
    private String name;
    private String header;
    private String footer;

    // Конструктор с передачей всех полей
    public GroupData(Integer id, String name, String header, String footer) {
        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;
    }

    // Конструктор без параметров (может понадобиться)
    public GroupData() {
    }

    // Второй конструктор с параметрами без id
    public GroupData(String name, String header, String footer) {
        this(null, name, header, footer);
    }

    // Конструктор только с id и name
    public GroupData(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Builder-style методы для формирования объекта
    public GroupData withId(Integer id) {
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
        this.header = footer;
        return this;
    }

    // Метод доступа к полю id
    public int getId() {
        return id != null ? id : 0; // Для гарантии возвращения числового значения
    }
}

