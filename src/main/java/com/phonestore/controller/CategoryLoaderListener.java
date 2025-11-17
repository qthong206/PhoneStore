package com.phonestore.controller;

import com.phonestore.dao.BrandDAO;
import com.phonestore.dao.CategoryDAO;
import com.phonestore.model.Brand;
import com.phonestore.model.Category;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.List;

@WebListener
public class CategoryLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        System.out.println("CategoryLoaderListener: Đang tải danh sách Category...");
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> allCategories = categoryDAO.getAllCategories();
        context.setAttribute("allCategories", allCategories);
        System.out.println("CategoryLoaderListener: Đã tải " + allCategories.size() + " categories.");

        System.out.println("CategoryLoaderListener: Đang tải danh sách Brand...");
        BrandDAO brandDAO = new BrandDAO();
        List<Brand> allBrands = brandDAO.getAllBrands();
        context.setAttribute("allBrands", allBrands);
        System.out.println("CategoryLoaderListener: Đã tải " + allBrands.size() + " brands.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}