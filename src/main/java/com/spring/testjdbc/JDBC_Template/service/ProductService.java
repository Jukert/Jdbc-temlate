package com.spring.testjdbc.JDBC_Template.service;

import com.spring.testjdbc.JDBC_Template.common.Product;

import java.util.List;

public interface ProductService {

    void insert(Product product);

    void insertBatch(List<Product> products);

    void loadAllProducts();

    void getProductById(long prod_id);

    void getProductNameById(long prod_id);

    void getTotalNumberProduct();

}
