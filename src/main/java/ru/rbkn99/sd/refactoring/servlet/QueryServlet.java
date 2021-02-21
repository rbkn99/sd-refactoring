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
public class QueryServlet extends HttpServlet {

    private final BaseDatabase<Product> database;

    public QueryServlet(BaseDatabase<Product> database) {
        this.database = database;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        HtmlBuilder htmlBuilder = new HtmlBuilder();
        boolean unknownCommandFound = false;

        if (command == null) {
            response.getWriter().println("Unknown command: null");
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (command) {
            case "max":
                htmlBuilder.appendH1Tag("Product with max price: ");
                Product maxProduct = database.maxPrice();
                if (maxProduct != null) {
                    htmlBuilder.appendText(maxProduct.toHtml());
                }
                break;
            case "min":
                htmlBuilder.appendH1Tag("Product with min price: ");
                Product minProduct = database.minPrice();
                if (minProduct != null) {
                    htmlBuilder.appendText(minProduct.toHtml());
                }
                break;
            case "sum":
                htmlBuilder.appendText("Summary price: ");
                long sum = database.sumPrice();
                htmlBuilder.appendText(String.valueOf(sum));
                break;
            case "count":
                htmlBuilder.appendText("Number of products: ");
                long count = database.count();
                htmlBuilder.appendText(String.valueOf(count));
                break;
            default:
                unknownCommandFound = true;
                htmlBuilder.clear();
                htmlBuilder.appendText("Unknown command: " + command);
        }

        response.getWriter().println(htmlBuilder.toString());
        response.setContentType("text/html");
        if (unknownCommandFound) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}
