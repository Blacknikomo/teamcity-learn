namespace TestSuite2
{
    [TestClass]
    public class TestCases2
    {
        [TestMethod]
        public void FailingTest() {
            Assert.AreEqual(1, 0, "This test always fails");
        }
    }
}