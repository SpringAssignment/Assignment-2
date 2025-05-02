
package com.example.textbookmarketplace.model.interfaces;

import com.example.textbookmarketplace.model.Book;
import com.example.textbookmarketplace.model.exceptions.DuplicateBookException;

public interface Listable {
    void listBook(Book book, int quantity, String sellerName, String bankDetails) throws DuplicateBookException;
    boolean isBookListed(Book book);
}