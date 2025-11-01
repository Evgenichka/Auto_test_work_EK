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



