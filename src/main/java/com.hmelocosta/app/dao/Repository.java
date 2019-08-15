package com.hmelocosta.app.dao;

import java.util.List;

public interface Repository<T> {
    public List<T> fetchAll();
    public T fetchOne(String id);
    public void upsert(T object);
    public void delete(String id);
}
