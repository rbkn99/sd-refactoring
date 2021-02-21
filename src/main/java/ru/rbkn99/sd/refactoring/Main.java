package ru.rbkn99.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.rbkn99.sd.refactoring.servlet.AddProductServlet;
import ru.rbkn99.sd.refactoring.servlet.GetProductsServlet;
import ru.rbkn99.sd.refactoring.servlet.QueryServlet;
import ru.rbkn99.sd.refactoring.db.BaseDatabase;
import ru.rbkn99.sd.refactoring.db.ProductDatabase;
import ru.rbkn99.sd.refactoring.product.Product;


/**
 * @author rbkn99
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BaseDatabase<Product> database = new ProductDatabase("jdbc:sqlite:test.db");
        database.createIfNotExists();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet()), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet()), "/query");

        server.start();
        server.join();
    }
}
