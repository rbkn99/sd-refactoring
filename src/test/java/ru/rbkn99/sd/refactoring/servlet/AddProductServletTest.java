package ru.rbkn99.sd.refactoring.servlet;

import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class AddProductServletTest extends BaseServletTest {

    @Test
    public void notEmptyTest() throws IOException {
        when(request.getParameter("name")).thenReturn("my_name");
        when(request.getParameter("price")).thenReturn("23");
        new AddProductServlet(database).doGet(request, response);
        compareStrings("OK", writer.toString());
    }
}