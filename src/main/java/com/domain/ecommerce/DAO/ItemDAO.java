package com.domain.ecommerce.DAO;

import com.domain.ecommerce.models.Items;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ItemDAO implements DAO<Items,Long>{
    private JdbcTemplate jdbcTemplate;
    public ItemDAO() {}
    public ItemDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Items> list() {
        return null;
    }

    @Override
    public Items get(Items entity) {
        return null;
    }

    @Override
    public void create(Items entity) {

    }

    @Override
    public void update(Items entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
