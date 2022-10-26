package com.domain.ecommerce.DAO;

import java.util.List;
/*
interface for basic CRUD operations to be implemented by each DAO
 */
public interface DAO<T,ID> {

    List<T> list();

    T get(T entity, ID id);

    void create(T entity);

    void update(T entity, ID id,String columnName);

    void delete(ID id);




}
