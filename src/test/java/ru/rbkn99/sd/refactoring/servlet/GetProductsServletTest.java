package ru.rbkn99.sd.refactoring.servlet;

import org.junit.Test;
import ru.rbkn99.sd.refactoring.product.Product;

import java.io.IOException;
import java.util.List;

public class GetProductsServletTest extends BaseServletTest {
    @Test
    public void emptyGetTest() throws IOException {
        new GetProductsServlet(database).doGet(request, response);
        compareStrings("<html><body>\n" +
                "</body></html>", writer.toString());
    }

    @Test
    public void simpleGetTest() throws IOException {
        database.insert(List.of(new Product("product1", 1),
                new Product("product2", 2)));
        new GetProductsServlet(database).doGet(request, response);
        compareStrings("<html><body>\n" +
                "product1\t1</br>\n" +
                "product2\t2</br>\n" +
                "</body></html>", writer.toString());
    }
}