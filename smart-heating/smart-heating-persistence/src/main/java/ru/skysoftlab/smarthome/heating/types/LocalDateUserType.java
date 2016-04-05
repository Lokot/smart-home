package ru.skysoftlab.smarthome.heating.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class LocalDateUserType implements EnhancedUserType, Serializable {
 
    private static final long serialVersionUID = -8543100422134320191L;
    
	private static final int[] SQL_TYPES = new int[]{Types.DATE};
 
    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }
 
    @Override
    public Class<?> returnedClass() {
        return LocalDate.class;
    }
 
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        LocalDate dtx = (LocalDate) x;
        LocalDate dty = (LocalDate) y;
        return dtx.equals(dty);
    }
 
    @Override
    public int hashCode(Object object) throws HibernateException {
        return object.hashCode();
    }
 
 
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        Object timestamp = StandardBasicTypes.DATE.nullSafeGet(resultSet, names, session, owner);
        if (timestamp == null) {
            return null;
        }
        return new LocalDateTime(timestamp, DateTimeZone.getDefault()).toLocalDate();
    }
 
    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            StandardBasicTypes.DATE.nullSafeSet(preparedStatement, null, index, session);
        } else {
            LocalDate ld = ((LocalDate) value);
            Instant instant = ld.toDateTimeAtStartOfDay(DateTimeZone.getDefault()).toInstant();
            Date time = instant.toDate();
            StandardBasicTypes.DATE.nullSafeSet(preparedStatement, time, index, session);
        }
    }
 
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
 
    @Override
    public boolean isMutable() {
        return false;
    }
 
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }
 
    @Override
    public Object assemble(Serializable cached, Object value) throws HibernateException {
        return cached;
    }
 
    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
 
    @Override
    public String objectToSQLString(Object object) {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public String toXMLString(Object object) {
        return object.toString();
    }
 
    @Override
    public Object fromXMLString(String string) {
        return new LocalDate(string);
    }

}
