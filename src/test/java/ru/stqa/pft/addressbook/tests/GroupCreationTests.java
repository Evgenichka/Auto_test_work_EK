package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void groupCreation() {
        // Навигация на страницу группы и получение списка существующих групп
        appManager.getNavigationHelper().goToGroupPage();
        List<GroupData> oldGroups = appManager.getGroupHelper().getGroupList();

        // Данные для новой группы
        GroupData newGroup = new GroupData("GroupName", "GroupHeader", "GroupFooter");

        // Создание новой группы
        appManager.getGroupHelper().create(newGroup);

        // Получение обновленного списка групп
        List<GroupData> newGroups = appManager.getGroupHelper().getGroupList();

        // Проверка размера списков
        Assertions.assertEquals(oldGroups.size() + 1, newGroups.size());

        // Возвращаемся на главную страницу
        appManager.getNavigationHelper().goToHomePage();

        // Проверка корректности созданных данных
        checkGroupCreation(oldGroups, newGroup, newGroups);
    }

//    // Метод для проверки правильности созданной группы
//    private void checkGroupCreation(List<GroupData> oldGroups, GroupData newGroup, List<GroupData> newGroups) {
//        oldGroups.add(newGroup);
//        Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::id);
//        oldGroups.sort(byId);
//        newGroups.sort(byId);
//        Assertions.assertEquals(oldGroups, newGroups);
//    }
// Метод для проверки правильности созданной группы
private void checkGroupCreation(List<GroupData> oldGroups, GroupData newGroup, List<GroupData> newGroups) {
    oldGroups.add(newGroup);
    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
    oldGroups.sort(byId);
    newGroups.sort(byId);
    Assertions.assertEquals(oldGroups, newGroups);
}
}