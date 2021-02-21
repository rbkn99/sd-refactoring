package ru.rbkn99.sd.refactoring.servlet;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class QueryServletTest extends BaseServletTest {
    private void testCommand(String command, String result) throws IOException {
        when(request.getParameter("command")).thenReturn(command);
        new QueryServlet().doGet(request, response);
        compareStrings(writer.toString(), result);
    }

    @Test
    public void nullCommandTest() throws IOException {
        new QueryServlet().doGet(request, response);
        compareStrings(writer.toString(), "Unknown command: null");
    }

    @Test
    public void badCommandTest() throws IOException {
        testCommand("blablabla", "Unknown command: blablabla");
    }

    private void maxTest(String result) throws IOException {
        testCommand("max", "<html><body>" + sep +
                "<h1>Product with max price: </h1>" + sep +
                result +
                "</body></html>");
    }

    @Test
    public void emptyMaxTest() throws IOException {
        maxTest("");
    }

    @Test
    public void simpleMaxTest() throws SQLException, IOException {
        execSql("INSERT INTO " + TEST_TABLE_NAME + " (NAME, PRICE) VALUES ('iphone', 111), ('samsung', 95)");
        maxTest("iphone\t111</br>" + sep);
    }

    private void minTest(String result) throws IOException {
        testCommand("min", "<html><body>" + sep +
                "<h1>Product with min price: </h1>" + sep +
                result +
                "</body></html>");
    }

    @Test
    public void emptyMinTest() throws IOException {
        minTest("");
    }

    @Test
    public void simpleMinTest() throws SQLException, IOException {
        execSql("INSERT INTO " + TEST_TABLE_NAME + " (NAME, PRICE) VALUES ('iphone', 111), ('samsung', 95)");
        minTest("samsung\t95</br>" + sep);
    }

    private void sumTest(String result) throws IOException {
        testCommand("sum", "<html><body>" + sep +
                "Summary price: " + sep +
                result +
                "</body></html>");
    }

    @Test
    public void emptySumTest() throws IOException {
        sumTest("0" + sep);
    }

    @Test
    public void simpleSumTest() throws IOException, SQLException {
        execSql("INSERT INTO " + TEST_TABLE_NAME + " (NAME, PRICE) VALUES ('iphone', 111), ('samsung', 95)");
        sumTest("206" + sep);
    }

    private void countTest(String result) throws IOException {
        testCommand("count", "<html><body>" + sep +
                "Number of products: " + sep +
                result +
                "</body></html>");
    }

    @Test
    public void emptyCountTest() throws IOException {
        countTest("0" + sep);
    }

    @Test
    public void simpleCountTest() throws IOException, SQLException {
        execSql("INSERT INTO " + TEST_TABLE_NAME + " (NAME, PRICE) VALUES ('iphone', 111), ('samsung', 95)");
        countTest("2" + sep);
    }
}