package ru.rbkn99.sd.refactoring.db;

import ru.rbkn99.sd.refactoring.product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDatabase<T> {
    private final String databaseConnection;

    BaseDatabase(String databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public abstract void createIfNotExists();

    public abstract void dropIfExists();

    public int insert(T object) {
        return insert(List.of(object));
    }

    public abstract int insert(List<T> objects);

    public abstract List<Product> selectAll();

    public String getDatabaseConnection() {
        return databaseConnection;
    }

    protected int execSql(String sql) {
        try (Connection c = DriverManager.getConnection(databaseConnection)) {
            try (Statement statement = c.createStatement()) {
                return statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<List<String>> selectSql(String sql, List<String> fields) {
        List<List<String>> table = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(databaseConnection)) {
            try (Statement stmt = c.createStatement()) {
                try (ResultSet result = stmt.executeQuery(sql)) {
                    while (result.next()) {
                        List<String> row = new ArrayList<>();
                        for (String field : fields) {
                            row.add(result.getString(field));
                        }
                        table.add(row);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
}
