package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    List<Category> getAllCategories();
    void saveCategory(Category category);
    Category getCategory(long id);
    void deleteCategory(long id);
}
