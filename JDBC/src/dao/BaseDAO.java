package dao;

import java.util.List;

public interface BaseDAO {
    public int executeUpd(String sql, Object... o);
    public <T>List<T> select(String sql, Object[] args, Class<T> clazz);
}
