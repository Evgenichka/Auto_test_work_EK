package ru.stqa.pft.addressbook.tests;

import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

    @BeforeEach
    public void prepareGroupDeletion() { // Переименован метод
        appManager.goTo().groupPage();
        if (appManager.group().all().isEmpty()) {
            appManager.group().create(new GroupData().withName("GroupName").withHeader("GroupHeader").withFooter("GroupFooter"));
        }
    }

    @Test
    public void testGroupDeletion() {
        Groups beforeGroupList = appManager.group().all();
        GroupData deletedGroup = beforeGroupList.iterator().next();

        appManager.group().delete(deletedGroup);
        assertThat(appManager.group().count(), equalTo(beforeGroupList.size() - 1));
        Groups afterGroupList = appManager.group().all();
        appManager.goTo().homePage();
        assertThat(afterGroupList, equalTo(beforeGroupList.without(deletedGroup)));
    }
}


