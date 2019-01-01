package com.spring.testjdbc.JDBC_Template.dao;

import com.spring.testjdbc.JDBC_Template.common.Product;

import java.util.List;

public interface ProductDao {

    void insert(Product product);

    void insertBatch(List<Product> products);

    List<Product> loadAll();

    Product findProductById(long prod_id);

    String findNameById(long prod_id);

    int getTotalNumberProduct();

}
