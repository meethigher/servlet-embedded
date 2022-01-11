package top.meethigher.springbootservlet.dialect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.relational.core.dialect.*;
import org.springframework.data.relational.core.sql.IdentifierProcessing;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

/**
 * 参考文档
 * 1. https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.java-config
 * 2. https://stackoverflow.com/questions/61851491/spring-data-jdbc-firebird-dialect-not-recognized
 *
 * @author chenchuancheng
 * @since 2021/12/30 15:15
 */
public class CustomDialectResolver implements DialectResolver.JdbcDialectProvider {

    private static final Logger log = LoggerFactory.getLogger(CustomDialectResolver.class);

    public CustomDialectResolver() {
    }

    @Override
    public Optional<Dialect> getDialect(JdbcOperations operations) {
        return Optional.ofNullable(operations.execute((ConnectionCallback<Dialect>) con -> getDialect(con)));
    }

    @Nullable
    private static Dialect getDialect(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String name = metaData.getDatabaseProductName().toLowerCase(Locale.ENGLISH);
        if (name.contains("hsql")) {
            return HsqlDbDialect.INSTANCE;
        } else if (name.contains("h2")) {
            return H2Dialect.INSTANCE;
        } else if (!name.contains("mysql") && !name.contains("mariadb")) {
            if (name.contains("postgresql")) {
                return PostgresDialect.INSTANCE;
            } else if (name.contains("microsoft")) {
                return SqlServerDialect.INSTANCE;
            } else if (name.contains("db2")) {
                return Db2Dialect.INSTANCE;
            } else if (name.contains("oracle")) {
                return OracleDialect.INSTANCE;
            } else if (name.contains("sqlite")) {
                return SqliteDialect.INSTANCE;
            } else {
                log.error("Halo guys, your spring-data-jdbc not have {}'s dialect, please config it by yourself", name);
                return null;
            }
        } else {
            return new MySqlDialect(getIdentifierProcessing(metaData));
        }
    }

    private static IdentifierProcessing getIdentifierProcessing(DatabaseMetaData metaData) throws SQLException {
        String quoteString = metaData.getIdentifierQuoteString();
        IdentifierProcessing.Quoting quoting = StringUtils.hasText(quoteString) ? new IdentifierProcessing.Quoting(quoteString) : IdentifierProcessing.Quoting.NONE;
        IdentifierProcessing.LetterCasing letterCasing;
        if (metaData.supportsMixedCaseIdentifiers()) {
            letterCasing = IdentifierProcessing.LetterCasing.AS_IS;
        } else if (metaData.storesUpperCaseIdentifiers()) {
            letterCasing = IdentifierProcessing.LetterCasing.UPPER_CASE;
        } else if (metaData.storesLowerCaseIdentifiers()) {
            letterCasing = IdentifierProcessing.LetterCasing.LOWER_CASE;
        } else {
            letterCasing = IdentifierProcessing.LetterCasing.UPPER_CASE;
        }

        return IdentifierProcessing.create(quoting, letterCasing);
    }
}
