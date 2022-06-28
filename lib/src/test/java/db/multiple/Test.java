package db.multiple;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class Test {
    public static class A<T>{
        T a;
    }

    public static class B{
        A<BigDecimal> b;
    }
    @org.junit.jupiter.api.Test
    public void test() throws NoSuchFieldException {
        final Field b = B.class.getDeclaredField("b");

        final Class<?> type = b.getType();
        final Type genericType = b.getGenericType();


    }
}
