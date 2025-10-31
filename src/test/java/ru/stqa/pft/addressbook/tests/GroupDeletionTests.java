package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void groupDeletionPreconditionsCheck() {
        appManager.goTo().groupPage();
        if (appManager.group().all().isEmpty()){
            appManager.group().create(new GroupData().withName("GroupName").withHeader("GroupHeader").withFooter("GroupFooter"));
        }
    }

    @Test
    public void groupDeletion() {
        Groups beforeGroupList = appManager.group().all();
        GroupData deletedGroup = beforeGroupList.iterator().next();

        Assertions.assertNotNull(deletedGroup);
        appManager.group().delete(deletedGroup);
        assertThat(appManager.group().count(),equalTo(beforeGroupList.size() - 1));
        Groups afterGroupList = appManager.group().all();
        appManager.goTo().HomePage();
        assertThat(afterGroupList, equalTo(beforeGroupList.without(deletedGroup)));
    }
}


//    @Test
//    public void groupDeletion() {
//        appManager.getNavigationHelper().goToGroupPage();
//        if (!appManager.getGroupHelper().isGroupExist()){
//            appManager.getGroupHelper().create(new GroupData("GroupName","GroupHeader", "GroupFooter"));
//        }
//        List<GroupData> beforeGroupList = appManager.getGroupHelper().getGroupList();
//        appManager.getGroupHelper().selectGroup(beforeGroupList.size() - 1);
//        appManager.getGroupHelper().submitGroupDeletion();
//        appManager.getGroupHelper().returnToGroupPage();
//        List<GroupData> afterGroupList = appManager.getGroupHelper().getGroupList();
//        Assertions.assertEquals(afterGroupList.size(), beforeGroupList.size() - 1);
//        appManager.getNavigationHelper().HomePage();
//
//        beforeGroupList.remove(beforeGroupList.size() - 1);
//        Assertions.assertEquals(beforeGroupList, afterGroupList);
//    }
//}
