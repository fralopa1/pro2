package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.Category;
import cz.uhk.pro2_d.repository.CategoryRepository;
import cz.uhk.pro2_d.service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId(1L);
        category.setName("Zdraví");
    }

    @Test
    public void testGetAllCategories() {
        List<Category> mockList = Arrays.asList(category);
        when(categoryRepository.findAll()).thenReturn(mockList);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Zdraví", result.get(0).getName());
    }

    @Test
    public void testGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategory(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testCategoryNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Category result = categoryService.getCategory(99L);

        assertNull(result);
    }

    @Test
    public void testSaveCategory() {
        categoryService.saveCategory(category);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategory() {
        categoryService.deleteCategory(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
