package com.phonestore.controller;

import com.phonestore.dao.BrandDAO;
import com.phonestore.dao.CategoryDAO;
import com.phonestore.model.Brand;
import com.phonestore.model.Category;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class CategoryLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // 1. Khởi tạo DAO
        CategoryDAO categoryDAO = new CategoryDAO();
        BrandDAO brandDAO = new BrandDAO();

        try {
            // 2. Tải danh sách tất cả Category
            System.out.println("CategoryLoaderListener: Đang tải danh sách Category...");
            List<Category> allCategories = categoryDAO.getAllCategories();
            context.setAttribute("allCategories", allCategories);
            System.out.println("CategoryLoaderListener: Đã tải " + allCategories.size() + " categories.");

            // 3. Tải danh sách tất cả Brand
            System.out.println("CategoryLoaderListener: Đang tải danh sách Brand...");
            List<Brand> allBrands = brandDAO.getAllBrands();
            context.setAttribute("allBrands", allBrands);
            System.out.println("CategoryLoaderListener: Đã tải " + allBrands.size() + " brands.");

            // 4. Tạo Map liên kết: Category ID -> List<Brand>
            System.out.println("CategoryLoaderListener: Đang map Brand theo Category...");
            Map<Integer, List<Brand>> brandsByCategory = new HashMap<>();

            for (Category cat : allCategories) {
                // Gọi hàm getBrandsByCategoryId mà chúng ta vừa thêm vào BrandDAO
                List<Brand> brandsInCat = brandDAO.getBrandsByCategoryId(cat.getId());
                brandsByCategory.put(cat.getId(), brandsInCat);
            }

            // Đẩy Map này vào Application Scope để Header.jsp sử dụng
            context.setAttribute("brandsByCategory", brandsByCategory);
            System.out.println("CategoryLoaderListener: Đã map xong dữ liệu Menu.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("CategoryLoaderListener: Lỗi khi tải dữ liệu khởi động!");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}