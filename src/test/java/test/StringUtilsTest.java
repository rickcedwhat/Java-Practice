package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.example.StringUtils;

public class StringUtilsTest {
    @DataProvider
    public Object[][] data() {
        return new String[][] {
            {"hello", "olleh"},
            {"world", "dlrow"},
            {"TestNG", "GNtseT"}
        };
    }

    @Test(dataProvider = "data")
    public void testReverse(String original, String reversed) {
        Assert.assertEquals(StringUtils.reverse(original), reversed);
    }
}