package com.example.textbookmarketplace.model.interfaces;

import com.example.textbookmarketplace.model.Book;
import java.util.List;

public interface Searchable {
    List<Book> searchByTitle(String title);
    List<Book> searchBySeller(String sellerName);
}
