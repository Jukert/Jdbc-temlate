package com.spring.testjdbc.JDBC_Template.service.impls;

import com.spring.testjdbc.JDBC_Template.common.Product;
import com.spring.testjdbc.JDBC_Template.dao.ProductDao;
import com.spring.testjdbc.JDBC_Template.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional
    public void insert(Product product) {
        productDao.insert(product);
    }

    @Override
    public void insertBatch(List<Product> products) {
        productDao.insertBatch(products);
    }

    @Override
    public void loadAllProducts() {
        List<Product> products = productDao.loadAll();
        for (Product prod :
                products) {
            System.out.println(prod.toString());
        }
    }

    @Override
    public void getProductById(long prod_id) {
        Product productById = productDao.findProductById(prod_id);
        System.out.println(productById.toString());
    }

    @Override
    public void getProductNameById(long prod_id) {
        String nameById = productDao.findNameById(prod_id);
        System.out.println(nameById);
    }

    @Override
    public void getTotalNumberProduct() {
        int totalNumberProduct = productDao.getTotalNumberProduct();
        System.out.println("total number products: " + totalNumberProduct);
    }
}
