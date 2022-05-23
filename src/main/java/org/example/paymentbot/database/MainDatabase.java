package org.example.paymentbot.database;

import lombok.Getter;
import org.h2.jdbcx.JdbcConnectionPool;

@Getter
public class MainDatabase {
    private final DomainsDadabase domainsDadabase;
    private final UsersDatabase usersDatabase;

    public MainDatabase() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:~/test;AUTO_SERVER=TRUE","","");
        domainsDadabase = new DomainsDadabase(connectionPool);
        domainsDadabase.create();
        usersDatabase = new UsersDatabase(connectionPool);
        usersDatabase.create();
    }
}
