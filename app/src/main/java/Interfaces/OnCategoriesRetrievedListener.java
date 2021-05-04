package Interfaces;

import java.util.ArrayList;

import Models.Category;
import Models.CategoryList;

public interface OnCategoriesRetrievedListener {
    void OnCategoriesRetrieved(ArrayList<Category> categoryList);
}
