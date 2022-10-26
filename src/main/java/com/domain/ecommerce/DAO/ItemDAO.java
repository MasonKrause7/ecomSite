package com.domain.ecommerce.DAO;

import com.domain.ecommerce.models.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ItemDAO implements DAO<Items,Long>{
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Items> rowMapper = (rs,rowNumber) -> {
        Items items = new Items();
        items.setItemID(rs.getLong("item_id"));
        items.setItemName(rs.getString("item_name"));
        items.setItemDescription(rs.getString("item_description"));
        items.setImageUrl(rs.getString("image_url"));
        items.setPrice(rs.getDouble("price"));
        items.setQuantity(rs.getInt("quantity"));
        return items;
    };
    public ItemDAO() {}
    @Autowired
    public ItemDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public List<Items> list() {
        String sqlQuery = "SELECT * FROM items";
        return jdbcTemplate.query(sqlQuery,rowMapper);
    }

    @Override
    public Items get(Items entity) {
        return null;
    }

    @Override
    public void create(Items entity) {
        String sqlQuery = "INSERT INTO items (item_name,item_description,image_url,price,quantity) " +
                "values (?,?,?,?,?)";
        //update returns an int value of how many rows there are
        jdbcTemplate.update(sqlQuery,
                entity.getItemName(),
                entity.getItemDescription(),
                entity.getImageUrl(),
                entity.getPrice(),
                entity.getQuantity());
    }

    @Override
    public void update(Items entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
