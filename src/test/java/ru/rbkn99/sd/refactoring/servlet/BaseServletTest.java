package ru.rbkn99.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.rbkn99.sd.refactoring.db.BaseDatabase;
import ru.rbkn99.sd.refactoring.db.ProductDatabase;
import ru.rbkn99.sd.refactoring.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class BaseServletTest {
    protected static final String TEST_TABLE_NAME = "PRODUCT";
    private static final String TEST_DATABASE_NAME = "jdbc:sqlite:test.db";

    protected final StringWriter writer = new StringWriter();
    protected final BaseDatabase<Product> database = new ProductDatabase(TEST_DATABASE_NAME);

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    @Before
    public void createTestDatabase() {
        database.createIfNotExists();
    }

    @Before
    public void setUpMocks() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @After
    public void dropTestDatabase() {
        database.dropIfExists();
    }

    protected void compareStrings(String a, String b) {
        Assert.assertEquals(a.strip(), b.strip());
    }
}