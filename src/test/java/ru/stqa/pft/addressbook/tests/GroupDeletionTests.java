package ru.stqa.pft.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

    @BeforeEach
    public void prepareGroupDeletion() {
        appManager.goTo().groupPage();
        if (appManager.group().all().isEmpty()) {
            appManager.group().create(new GroupData().withName("GroupName").withHeader("GroupHeader").withFooter("GroupFooter"));
        }
    }

    @Test
    public void testGroupDeletion() {
        Groups allGroups = appManager.group().all();

        GroupData deletedGroup;
        try {
            deletedGroup = allGroups.iterator().next();
        } catch (NoSuchElementException e) {
            Assertions.fail("Коллекция групп оказалась пустой, удаление невозможно.");
            return;
        }

        Assertions.assertNotNull(deletedGroup);
        appManager.group().delete(deletedGroup);

        assertThat(appManager.group().count(), equalTo(allGroups.size() - 1));

        Groups afterGroupList = appManager.group().all();
        assertThat(afterGroupList, equalTo(allGroups.without(deletedGroup)));
    }
}
//    @Test
//    public void testGroupDeletion() {
//        Groups beforeGroupList = appManager.group().all();
//        GroupData deletedGroup = beforeGroupList.iterator().next();
//
//        appManager.group().delete(deletedGroup);
//        assertThat(appManager.group().count(), equalTo(beforeGroupList.size() - 1));
//        Groups afterGroupList = appManager.group().all();
//        appManager.goTo().homePage();
//        assertThat(afterGroupList, equalTo(beforeGroupList.without(deletedGroup)));
//    }
//}


