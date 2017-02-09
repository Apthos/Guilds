package net.apthos.guilds.database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class GuildsDatabase {

    final String DBN = "Guilds";

    private String HOSTNAME, PORT, USER, PASS;
    private Connection connection;
    private HikariDataSource hikari;

    public GuildsDatabase(String host, String port, String user, String password) {
        HOSTNAME = host; PORT = port; USER = user; PASS = password;

        if (!databaseExists(DBN)) {
            createDatabase();
        }

        setupHikari(DBN);
    }

    private void setupHikari(String name) {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("serverName", HOSTNAME);
        config.addDataSourceProperty("port", PORT);
        config.addDataSourceProperty("databaseName", name);
        config.addDataSourceProperty("user", USER);
        config.addDataSourceProperty("password", PASS);
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "1024");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikari = new HikariDataSource(config);
    }

    private Boolean databaseExists(String name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + HOSTNAME + ":"
                    + PORT + "/mysql?zeroDateTimeBehavior=convertToNull", USER, PASS);

            ResultSet resultSet = connection.getMetaData().getCatalogs();

            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equalsIgnoreCase(name)) {
                    return true;
                }
            }
            resultSet.close();
            connection.close();
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private void createDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + HOSTNAME + ":" +
                    PORT + "/mysql?zeroDateTimeBehavior=convertToNull", USER, PASS);

            PreparedStatement statement = connection.prepareStatement("" +
                    "CREATE DATABASE Guilds;"
            );
            statement.execute();

            statement = connection.prepareStatement("" +
                    "CREATE TABLE Guilds.`Guilds`(`guid` VARCHAR(64), `name` VARCHAR(64)," +
                    " `leader` VARCHAR(64),`date` DATETIME);");
            statement.execute();

            statement = connection.prepareStatement("" +
                    "CREATE TABLE Guilds.`Players`(`UUID` VARCHAR(82), `name` VARCHAR(64)," +
                    " `guid` VARCHAR(64));"
            );
            statement.execute();

            statement = connection.prepareStatement("" +
                    "CREATE TABLE Guilds.`Claims`(`x` INTEGER NOT NULL, `y` INTEGER NOT " +
                    "NULL, `world` VARCHAR(64) NOT NULL, `guid` VARCHAR(64) NOT NULL, " +
                    "`block_protection` BOOLEAN, `mob_protection` BOOLEAN, " +
                    "`block_interaction` BOOLEAN, `mob_interaction` BOOLEAN, `pvp` " +
                    "BOOLEAN, `mob_spawning` BOOLEAN, `hostile_mob_spawning` BOOLEAN);"
            );
            statement.execute();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropDatabase(String name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + HOSTNAME + ":" +
                    PORT + "/mysql?zeroDateTimeBehavior=convertToNull", USER, PASS);

            PreparedStatement dropDB = connection.prepareStatement
                    ("DROP DATABASE " + name + ";");

            dropDB.executeUpdate();

            dropDB.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        try { return hikari.getConnection(); } catch (SQLException e)
        { e.printStackTrace(); }
        return null;
    }

    public void close(){
        hikari.close();
    }


}
