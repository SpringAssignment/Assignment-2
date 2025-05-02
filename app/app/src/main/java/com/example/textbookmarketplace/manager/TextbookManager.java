package com.example.textbookmarketplace.manager;

import com.example.textbookmarketplace.model.Book;
import com.example.textbookmarketplace.model.exceptions.DuplicateBookException;
import com.example.textbookmarketplace.model.interfaces.Listable;
import com.example.textbookmarketplace.model.interfaces.Searchable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextbookManager implements Listable, Searchable {
    private static TextbookManager instance;
    private final List<Book> booksListed;

    private final Map<Book, String> bookToSellerMap;
    private final Map<Book, String> bookToBankDetailsMap;

    public TextbookManager() {

        booksListed = new ArrayList<>();
        bookToSellerMap = new HashMap<>();
        bookToBankDetailsMap = new HashMap<>();
    }

    public static synchronized TextbookManager getInstance() {
        if (instance == null) {
            instance = new TextbookManager();
        }
        return instance;
    }

    @Override
    public void listBook(Book book, int quantity, String sellerName, String bankDetails) throws DuplicateBookException {
        if (isBookListed(book)) {
            throw new DuplicateBookException("This book is already listed!");
        }
        booksListed.add(book);
        bookToSellerMap.put(book, sellerName);
        bookToBankDetailsMap.put(book, bankDetails);
    }

    @Override
    public boolean isBookListed(Book book) {
        return booksListed.contains(book);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : booksListed) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    @Override
    public List<Book> searchBySeller(String sellerName) {
        List<Book> results = new ArrayList<>();
        for (Map.Entry<Book, String> entry : bookToSellerMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(sellerName)) {
                results.add(entry.getKey());
            }
        }
        return results;
    }


    public List<Book> getAllBooks() {
        return new ArrayList<>(booksListed);
    }
}