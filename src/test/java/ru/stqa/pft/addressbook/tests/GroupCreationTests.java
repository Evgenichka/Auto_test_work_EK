package ru.stqa.pft.addressbook.tests;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @BeforeEach
    public void prepareGroupEditing() { // Переименованный метод
        appManager.goTo().groupPage();
    }

    @Test
    public void testGroupCreation() {
        Groups beforeGroupList = appManager.group().all();
        GroupData groupThatWillBeCreated = new GroupData().withName("GroupName").withHeader("GroupHeader").withFooter("GroupFooter");

        appManager.group().create(groupThatWillBeCreated);

        assertThat(appManager.group().count(), equalTo(beforeGroupList.size() + 1));
        Groups afterGroupList = appManager.group().all();
        appManager.goTo().homePage();

        assertThat(afterGroupList, equalTo(beforeGroupList.withAdded(groupThatWillBeCreated.withId(afterGroupList.stream()
                .mapToInt(GroupData::getId).max().getAsInt()))));
    }

    @Test
    public void testBadGroupCreation() {
        Groups beforeGroupList = appManager.group().all();
        GroupData groupForList = new GroupData().withName("GroupName'").withHeader("GroupHeader").withFooter("GroupFooter");

        appManager.group().create(groupForList);
        assertThat(appManager.group().count(), equalTo(beforeGroupList.size()));
        Groups afterGroupList = appManager.group().all();
        appManager.goTo().homePage();
        assertThat(afterGroupList, equalTo(beforeGroupList));
    }
}



//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.testng.annotations.BeforeMethod;
//import ru.stqa.pft.addressbook.model.GroupData;
//import ru.stqa.pft.addressbook.model.Groups;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Objects;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class GroupCreationTests extends TestBase {
//
//    @BeforeMethod
//    public void groupCreationPreconditionsCheck() {
//        appManager.goTo().groupPage();
//    }
//
//    @Test
//    public void groupCreation() {
//        Groups beforeGroupList = appManager.group().all();
//        GroupData groupThatWillBeCreated = new GroupData().withName("GroupName").withHeader("GroupHeader").withFooter("GroupFooter");
//
//        appManager.group().create(groupThatWillBeCreated);
//
//        assertThat(appManager.group().count(), equalTo(beforeGroupList.size() + 1));
//        Groups afterGroupList = appManager.group().all();
//        appManager.goTo().HomePage();
//
//        assertThat(afterGroupList, equalTo(beforeGroupList.withAdded(groupThatWillBeCreated.withId(afterGroupList.stream().filter(Objects::nonNull)
//                .mapToInt(GroupData::getId).max().getAsInt()))));
//    }
//
//    @Test
//    public void badGroupCreation() {
//        Groups beforeGroupList = appManager.group().all();
//        GroupData groupForList = new GroupData().withName("GroupName'").withHeader("GroupHeader").withFooter("GroupFooter");
//
//        appManager.group().create(groupForList);
//        assertThat(appManager.group().count(), equalTo(beforeGroupList.size()));
//        Groups afterGroupList = appManager.group().all();
//        appManager.goTo().HomePage();
//        assertThat(afterGroupList, equalTo(beforeGroupList));
//    }
//}


//    @Test
//    public void groupCreation() {
//        // Навигация на страницу группы и получение списка существующих групп
//        appManager.getNavigationHelper().goToGroupPage();
//        List<GroupData> oldGroups = appManager.getGroupHelper().getGroupList();
//
//        // Данные для новой группы
//        GroupData newGroup = new GroupData("GroupName", "GroupHeader", "GroupFooter");
//
//        // Создание новой группы
//        appManager.getGroupHelper().create(newGroup);
//
//        // Получение обновленного списка групп
//        List<GroupData> newGroups = appManager.getGroupHelper().getGroupList();
//
//        // Проверка размера списков
//        Assertions.assertEquals(oldGroups.size() + 1, newGroups.size());
//
//        // Возвращаемся на главную страницу
//        appManager.getNavigationHelper().HomePage();
//
//        // Проверка корректности созданных данных
//        checkGroupCreation(oldGroups, newGroup, newGroups);
//    }
//
//
//// Метод для проверки правильности созданной группы
//private void checkGroupCreation(List<GroupData> oldGroups, GroupData newGroup, List<GroupData> newGroups) {
//    oldGroups.add(newGroup);
//    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
//    oldGroups.sort(byId);
//    newGroups.sort(byId);
//    Assertions.assertEquals(oldGroups, newGroups);
//}
//}