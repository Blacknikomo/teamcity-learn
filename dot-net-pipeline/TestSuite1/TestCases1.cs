using SampleDotNetProj;

namespace TestSuite;

[TestClass]
public class TestCases1
{
    [TestMethod]
    public void InvalidLowerCase(){
        int pLength = 2;
        char[] password = "1A!".ToCharArray();

        var expected = 1;
        var actual = PasswordComplexity.minCharNumber(pLength, password);

        Assert.AreEqual(expected, actual, "Expected value: " + expected.ToString() + ", actual value: " + actual.ToString());
    }

    [TestMethod]
    public void InvalidUpperCase()
    {
        int pLength = 2;
        char[] password = "1a!".ToCharArray();

        var expected = 1;
        var actual = PasswordComplexity.minCharNumber(pLength, password);

        Assert.AreEqual(expected, actual, "Expected value: " + expected.ToString() + ", actual value: " + actual.ToString());
    }

    [TestMethod]
    public void InvalidSpecials()
    {
        int pLength = 2;
        char[] password = "1aA".ToCharArray();

        var expected = 1;
        var actual = PasswordComplexity.minCharNumber(pLength, password);

        Assert.AreEqual(expected, actual, "Expected value: " + expected.ToString() + ", actual value: " + actual.ToString());
    }

    [TestMethod]
    public void InvalidNumbers()
    {
        int pLength = 2;
        char[] password = "!aA".ToCharArray();

        var expected = 1;
        var actual = PasswordComplexity.minCharNumber(pLength, password);

        Assert.AreEqual(expected, actual, "Expected value: " + expected.ToString() + ", actual value: " + actual.ToString());
    }

    [TestMethod]
    public void ShortPassword()
    {
        int pLength = 7;
        char[] password = "1Aa!".ToCharArray();

        var expected = 3;
        var actual = PasswordComplexity.minCharNumber(pLength, password);

        Assert.AreEqual(expected, actual, "Expected value: " + expected.ToString() + ", actual value: " + actual.ToString());
    }

    [TestMethod]
    public void ValidPassword()
    {
        int pLength = 10;
        char[] password = "V!(B&%^1aa".ToCharArray();

        var expected = 0;
        var actual = PasswordComplexity.minCharNumber(pLength, password);

        Assert.AreEqual(expected, actual, "Expected value: " + expected.ToString() + ", actual value: " + actual.ToString());
    }
}