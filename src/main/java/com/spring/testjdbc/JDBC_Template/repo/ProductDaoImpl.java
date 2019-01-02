package com.spring.testjdbc.JDBC_Template.repo;

import com.spring.testjdbc.JDBC_Template.common.Product;
import com.spring.testjdbc.JDBC_Template.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    @Override
    public void insert(Product product) {
        TransactionDefinition transactionDefinition = new DefaultTransactionAttribute();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            String sql =
                    "INSERT INTO product " +
                            "(id, name, count) VALUES (?,?,?)";
            getJdbcTemplate().update(sql, new Object[]{
                    product.getId(), product.getName(), product.getCount()
            });
            transactionManager.commit(status);
        }catch (DataAccessException e){
            System.out.println("Error");
            transactionManager.rollback(status);
        }
    }

    @Override
    public void insertBatch(List<Product> products) {
        String sql =
                "INSERT INTO product " +
                        "(id, name, count) VALUES (?,?,?)";
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Product product = products.get(i);
                ps.setLong(1, product.getId());
                ps.setString(2, product.getName());
                ps.setInt(3, product.getCount());
            }

            @Override
            public int getBatchSize() {
                return products.size();
            }
        });

    }

    @Override
    public List<Product> loadAll() {
        String sql =
                "SELECT P.id, P.name, P.count FROM product P";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Product> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Product product = new Product();
            product.setId((Long) row.get("id"));
            product.setName((String) row.get("name"));
            product.setCount((Integer) row.get("count"));
        }
        return result;
    }

    @Override
    public Product findProductById(long prod_id) {

        String sql =
                "SELECT P.id, P.name, P.count " +
                        "FROM product P " +
                        "WHERE P.id = ?";

        return  getJdbcTemplate().queryForObject(sql, new Object[]{prod_id}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCount(rs.getInt("count"));
                return product;
            }
        });
    }

    @Override
    public String findNameById(long prod_id) {

        String sql =
                "SELECT P.name FROM product P " +
                        "WHERE id= ?";

        return getJdbcTemplate().queryForObject(sql,new Object[]{prod_id},String.class);
    }

    @Override
    public int getTotalNumberProduct() {

        String sql =
                "SELECT count(P) " +
                        "FROM product P ";

        return getJdbcTemplate().queryForObject(sql,Integer.class);
    }
}
