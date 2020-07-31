import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class LiquibaseInit {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = org.postgresql.Driver.class.getName();
    private static final String CHANGELOG_PATH = "/db/db-master-changelog.xml";
    private static final String DEFAULT_SCHEMA_NAME = "crashcourse";

    public static void main(String[] args) throws LiquibaseException {
        ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        Database database = DatabaseFactory.getInstance().openDatabase(
                URL,
                USER,
                PASSWORD,
                DRIVER,
                null, null, null, resourceAccessor);
        database.setDefaultSchemaName(DEFAULT_SCHEMA_NAME);
        Liquibase liquibase = new Liquibase(CHANGELOG_PATH, resourceAccessor, database);
        liquibase.clearCheckSums();
        liquibase.update(new Contexts());
    }
}
