package ru.rbkn99.sd.refactoring.servlet;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class GetProductsServletTest extends BaseServletTest {
    @Test
    public void emptyGetTest() throws IOException {
        new GetProductsServlet().doGet(request, response);
        compareStrings("<html><body>" + sep +
                "</body></html>", writer.toString());
    }

    @Test
    public void notEmptyGetTest() throws SQLException, IOException {
        execSql("INSERT INTO " + BaseServletTest.TEST_TABLE_NAME + "(NAME, PRICE) VALUES " +
                "('product1', 1), ('product2', 2)");
        new GetProductsServlet().doGet(request, response);
        compareStrings("<html><body>" + sep +
                "product1\t1</br>" + sep +
                "product2\t2</br>" + sep +
                "</body></html>", writer.toString());
    }
}