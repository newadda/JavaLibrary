package org.onecell.common.hibernate.finder;

import com.zaxxer.hikari.pool.HikariProxyConnection;
import org.geolatte.geom.codec.db.oracle.ConnectionFinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/*
    HikariCP 는 Apache DBCP 의 setAccessToUnderlyingConnectionAllowed(true); 같은 설정이 없다.
    때문에
    Hibernate 에서 Oracle Spatial 사용시
    Couldn't get at the OracleSpatial Connection object from the PreparedStatement. 해당 오류 때문에 넣었다.

    Hibernate 에 아래와 같이 설정하면 된다.
    hibernate.spatial.connection_finder= org.lib.db.hikari.HikaricpOracleConnectionFinder

    properties.put("hibernate.spatial.connection_finder", HikaricpOracleConnectionFinder.class.getName());

*/
public class HikaricpOracleConnectionFinder implements ConnectionFinder {
    private static final Class<?> ORACLE_CONNECTION_CLASS;
    private static final Class<?> HIKARICP_CONNECTION_CLASS;

    static {
        try {
            ORACLE_CONNECTION_CLASS = Class.forName( "oracle.jdbc.driver.OracleConnection" );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( "Can't find Oracle JDBC Driver on classpath." );
        }

        try {
            HIKARICP_CONNECTION_CLASS = Class.forName( "com.zaxxer.hikari.pool.HikariProxyConnection" );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( "Can't find HikariProxyConnection classpath." );
        }

    }

    @Override
    public Connection find(Connection con) {
        if ( con == null ) {
            return null;
        }


        if ( ORACLE_CONNECTION_CLASS.isInstance( con ) ) {
            return con;
        }


        if ( HIKARICP_CONNECTION_CLASS.isInstance( con ) ) {
            try {
                return (Connection) ((HikariProxyConnection) con).unwrap(ORACLE_CONNECTION_CLASS);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        // try to find the Oracleconnection recursively
        for ( Method method : con.getClass().getMethods() ) {
            if ( Connection.class.isAssignableFrom(
                    method.getReturnType()
            )
                    && method.getParameterTypes().length == 0 ) {

                try {
                    method.setAccessible( true );
                    final Connection oc = find( (Connection) ( method.invoke( con, new Object[] { } ) ) );
                    if ( oc == null ) {
                        throw new RuntimeException(
                                String.format(
                                        "Tried retrieving OracleConnection from %s using method %s, but received null.",
                                        con.getClass().getCanonicalName(),
                                        method.getName()
                                )
                        );
                    }
                    return oc;
                }
                catch ( IllegalAccessException e ) {
                    throw new RuntimeException(
                            String.format(
                                    "Illegal access on executing method %s when finding OracleConnection",
                                    method.getName()
                            )
                    );
                }
                catch ( InvocationTargetException e ) {
                    throw new RuntimeException(
                            String.format(
                                    "Invocation exception on executing method %s when finding OracleConnection",
                                    method.getName()
                            )
                    );
                }


            }
        }
        throw new RuntimeException(
                "Couldn't get at the OracleSpatial Connection object from the PreparedStatement."
        );
    }
}
