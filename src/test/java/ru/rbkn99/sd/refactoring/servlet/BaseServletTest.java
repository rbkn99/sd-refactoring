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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.when;

public class BaseServletTest {
    protected static final String TEST_TABLE_NAME = "PRODUCT";
    private static final String TEST_DATABASE_NAME = "jdbc:sqlite:test.db";

    protected static final String sep = "\n";
    protected final StringWriter writer = new StringWriter();

    protected final BaseDatabase<Product> database = new ProductDatabase(TEST_DATABASE_NAME);

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    @Before
    public void createTestDatabase() throws SQLException {
        execSql("CREATE TABLE IF NOT EXISTS " + TEST_TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "NAME TEXT NOT NULL, PRICE INT NOT NULL)");
    }

    @Before
    public void setUpMocks() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @After
    public void dropDatabase() throws SQLException {
        execSql("DROP TABLE IF EXISTS " + TEST_TABLE_NAME);
    }

    protected void execSql(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(TEST_DATABASE_NAME)) {
            Statement statement = c.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    protected void compareStrings(String a, String b) {
        Assert.assertEquals(a.strip(), b.strip());
    }
}