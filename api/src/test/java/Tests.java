import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {
    SharedMethods sharedMethods = new SharedMethods();

    @Before
    public void Setup() {
        sharedMethods.GetBaseURL();
    }

    @Test
    public void ListUsers() {
        sharedMethods.CheckListUsersHasInputParameter(Constants.CORRINE);
    }

    @Test
    public void UserNotFound() {
        sharedMethods.GetMissingUser(1111);
    }

    @Test
    public void ReturnSpecificUser() {
        sharedMethods.GetSpecificUserAndVerifyName(2, Constants.CORRINE);
    }

    @Test
    public void UpdateUser() {
        sharedMethods.VerifyUpdateUserWithNewName(7, Constants.IVAN);
    }

    @Test
    public void AlreadyDeletedUser() {
        sharedMethods.CheckStatusCodeOnAlreadyDeletedUser(1);
    }

    @Test
    public void PageLimit() {
        sharedMethods.CheckPageLimitIsEqualToBodySize(13);
    }

    @Test
    public void SortAndOrder() {
        sharedMethods.GetFirstValueOnSortAndOrder("name", "asc");
        Assert.assertEquals(sharedMethods.GetFirstValueOnSortAndOrder("name", "asc"), Constants.ADRIANNA);
    }
}