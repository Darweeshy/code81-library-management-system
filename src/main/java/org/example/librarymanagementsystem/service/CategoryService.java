package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.CategoryDto;
import org.example.librarymanagementsystem.dto.CategoryTreeDto;
import org.example.librarymanagementsystem.model.Category;
import org.example.librarymanagementsystem.repo.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @CacheEvict(value = "categoryTree", allEntries = true)
    public CategoryDto create(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());

        if (dto.getParentId() != null) {
            Optional<Category> parentOpt = categoryRepository.findById(dto.getParentId());
            parentOpt.ifPresent(category::setParent);
        }

        Category saved = categoryRepository.save(category);
        return mapToDto(saved);
    }

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToDto(category);
    }

    @CacheEvict(value = "categoryTree", allEntries = true)
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.getName());

        if (dto.getParentId() != null) {
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        Category updated = categoryRepository.save(category);
        return mapToDto(updated);
    }

    @CacheEvict(value = "categoryTree", allEntries = true)
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    @Cacheable("categoryTree")
    public List<CategoryTreeDto> getCategoryTree() {
        List<Category> topCategories = categoryRepository.findAll().stream()
                .filter(c -> c.getParent() == null)
                .collect(Collectors.toList());

        return topCategories.stream()
                .map(this::mapToTreeDto)
                .collect(Collectors.toList());
    }

    private CategoryTreeDto buildTree(List<Category> categories) {
        CategoryTreeDto root = new CategoryTreeDto();
        root.setName("Root");
        root.setSubcategories(
                categories.stream()
                        .map(this::mapToTreeDto)
                        .collect(Collectors.toList())
        );
        return root;
    }

    private CategoryTreeDto mapToTreeDto(Category category) {
        CategoryTreeDto dto = new CategoryTreeDto();
        dto.setId(category.getId());
        dto.setName(category.getName());

        List<Category> children = categoryRepository.findAll().stream()
                .filter(c -> c.getParent() != null && c.getParent().getId().equals(category.getId()))
                .collect(Collectors.toList());

        dto.setSubcategories(
                children.stream()
                        .map(this::mapToTreeDto)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    private CategoryDto mapToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        return dto;
    }
}