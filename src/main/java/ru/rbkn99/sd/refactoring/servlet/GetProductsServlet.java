package ru.rbkn99.sd.refactoring.servlet;

import ru.rbkn99.sd.refactoring.builder.HtmlBuilder;
import ru.rbkn99.sd.refactoring.db.BaseDatabase;
import ru.rbkn99.sd.refactoring.product.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rbkn99
 */
public class GetProductsServlet extends HttpServlet {
    private final BaseDatabase<Product> database;

    public GetProductsServlet(BaseDatabase<Product> database) {
        this.database = database;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        database.selectAll().forEach(product -> htmlBuilder.appendText(product.toHtml()));
        response.getWriter().println(htmlBuilder.toString());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
