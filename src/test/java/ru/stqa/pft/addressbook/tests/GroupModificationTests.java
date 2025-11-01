package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeEach
    public void ensurePreconditions() {
        if (appManager.group().all().isEmpty()) {
            appManager.goTo().groupPage();
            appManager.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void groupEditing() {
        Groups beforeGroupList = appManager.group().all();
        GroupData editedGroup = beforeGroupList.iterator().next();
        GroupData group = new GroupData().withId(editedGroup.getId()).withName("testName").withHeader("testHeader").withFooter("testFooter");
        appManager.group().edit(group);
        assertThat(appManager.group().count(), equalTo(beforeGroupList.size()));
        Groups afterGroupList = appManager.group().all();
        appManager.goTo().homePage();
        assertThat(afterGroupList, equalTo(beforeGroupList.without(editedGroup).withAdded(group)));
    }

}

//    @Test
//    public void testGroupModification() {
//        Groups before = appManager.group().all(); // получаем список групп из UI
//        GroupData modifiedGroup = before.iterator().next();
//        Assertions.assertNotNull(modifiedGroup);
//        GroupData group = new GroupData()
//                .withId(modifiedGroup.getId())
//                .withName("test1")
//                .withHeader("test2")
//                .withFooter("test3");
//        appManager.goTo().groupPage();
//        appManager.group().modify(group); // Передаем объект group в метод modify
//        assertThat(appManager.group().count(), equalTo(before.size()));
//        Groups after = appManager.group().all(); // снова получаем список групп из UI
//        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
//        appManager.verifyGroupListInUI();
//    }
//
//
//
//}

//@Test
//public void testGroupModification1() {
//    appManager.getNavigationHelper().goToGroupPage();
//    if (! appManager.getGroupHelper().isThereAGroup()) {
//        appManager.getGroupHelper().createGroup(new GroupData("test1", null, null));
//    }
//
//    List<GroupData> before = appManager.getGroupHelper().getGroupList();
//    int index = before.size() - 1;
//    GroupData group = new GroupData(before.get(index).getId(), "test1", "test2", "test3");
//    appManager.getGroupHelper().selectGroup(index);
//    appManager.getGroupHelper().initGroupModification();
//    appManager.getGroupHelper().fillGroupForm(group);
//    appManager.getGroupHelper().submitGroupModification();
//    appManager.getGroupHelper().returnToGroupPage();
//    List<GroupData> after = appManager.getGroupHelper().getGroupList();
//    Assertions.assertEquals(after.size(), before.size());
//
//
//    before.remove(index);
//    before.add(group);
//    Comparator<? super GroupData> byId = (g1, g2) ->Integer.compare(g1.getId(), g2.getId());
//    before.sort(byId);
//    after.sort(byId);
//    Assertions.assertEquals(before, after);
//}
//    }