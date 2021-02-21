package ru.rbkn99.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;
import ru.rbkn99.sd.refactoring.product.Product;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

public class AddProductServletTest extends BaseServletTest {

    @Test(expected = Exception.class)
    public void emptyAddTest() throws IOException {
        new AddProductServlet(database).doGet(request, response);
    }

    @Test
    public void simpleAddTest() throws IOException {
        String productName = "bread";
        int productPrice = 52;

        when(request.getParameter("name")).thenReturn(productName);
        when(request.getParameter("price")).thenReturn(Integer.toString(productPrice));
        new AddProductServlet(database).doGet(request, response);
        compareStrings("OK", writer.toString());

        List<Product> all = database.selectAll().collect(Collectors.toList());
        Assert.assertEquals(all.size(), 1);
        Assert.assertEquals(all.get(0).getName(), productName);
        Assert.assertEquals(all.get(0).getPrice(), productPrice);
    }
}