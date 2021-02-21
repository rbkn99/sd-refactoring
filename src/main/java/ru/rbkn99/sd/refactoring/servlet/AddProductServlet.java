package ru.rbkn99.sd.refactoring.servlet;

import ru.rbkn99.sd.refactoring.db.BaseDatabase;
import ru.rbkn99.sd.refactoring.product.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rbkn99
 */
public class AddProductServlet extends HttpServlet {
    private final BaseDatabase<Product> database;

    public AddProductServlet(BaseDatabase<Product> database) {
        this.database = database;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        database.insert(new Product(name, price));

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
