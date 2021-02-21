package ru.rbkn99.sd.refactoring.db;

import ru.rbkn99.sd.refactoring.product.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ProductDatabase extends BaseDatabase<Product> {

    private static final String TABLE_NAME = "PRODUCT";

    public ProductDatabase(String databaseConnection) {
        super(databaseConnection);
    }

    @Override
    public void createIfNotExists() {
        execSql("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME TEXT NOT NULL, PRICE INT NOT NULL)");
    }

    @Override
    public void dropIfExists() {
        execSql("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    @Override
    public int insert(List<Product> objects) {
        StringBuilder builder = new StringBuilder("INSERT INTO " + TABLE_NAME + " (NAME, PRICE) VALUES");
        for (int i = 0; i < objects.size(); i++) {
            builder.append(objects.get(i).toSql());
            if (i != objects.size() - 1) {
                builder.append(", ");
            }
        }
        return execSql(builder.toString());
    }

    private Product parseProduct(List<String> obj) {
        return new Product(obj.get(0), Long.parseLong(obj.get(1)));
    }

    @Override
    public Stream<Product> selectAll() {
        return selectSql("SELECT NAME, PRICE FROM " + TABLE_NAME, List.of("NAME", "PRICE"))
                .stream()
                .map(this::parseProduct);
    }

    @Override
    public Product maxPrice() {
        return selectAll()
                .max(Comparator.comparing(Product::getPrice))
                .orElse(null);
    }

    @Override
    public Product minPrice() {
        return selectAll()
                .min(Comparator.comparing(Product::getPrice))
                .orElse(null);
    }

    @Override
    public long sumPrice() {
        return selectAll()
                .mapToLong(Product::getPrice)
                .sum();
    }

    @Override
    public long count() {
        return selectAll().count();
    }
}
