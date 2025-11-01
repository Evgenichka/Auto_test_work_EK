package ru.stqa.pft.addressbook.model;


import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

public class Groups extends ForwardingSet <GroupData> {

    private Set<GroupData> delegate;

    public Groups(Groups groups) {
        this.delegate = new HashSet<>(groups.delegate);
    }

    public Groups() {
        this.delegate = new HashSet<>();
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }

    public Groups withAdded (GroupData group){
        Groups groups = new Groups(this);
        groups.add(group);
        return groups;
    }

    public Groups without(GroupData group){
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }
}



//import com.google.common.collect.ForwardingSet;
//
//import java.util.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Groups extends ForwardingSet<GroupData> {
//
//    private Set<GroupData> delegate;
//
//    public Groups(Groups groups) {
//        this.delegate = new HashSet<GroupData>(groups.delegate);
//    }
//
//    public Groups() {
//        this.delegate = new HashSet<GroupData>();
//    }
//
//    public Groups(Collection<GroupData> groups) {
//        this.delegate = new HashSet<GroupData>(groups);
//    }
//
//    @Override
//    protected Set<GroupData> delegate() {
//        return delegate;
//    }
//
//    public Groups withAdded(GroupData group) {
//        Groups groups = new Groups(this);
//        groups.add(group);
//        return groups;
//    }
//
//    public Groups without(GroupData group) {
//        Groups groups = new Groups(this);
//        groups.remove(group);
//        return groups;
//    }
//
//
//    private final List<GroupData> storage = new ArrayList<>(); // объявляем коллекцию
//
//    // Метод для получения группы по индексу
//    public GroupData get(int index) {
//        return storage.get(index); // Доступ к элементу по индексу
//    }
//}
