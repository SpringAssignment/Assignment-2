package com.example.textbookmarketplace.model;

public class Textbook extends Book {
    private String subject;
    private int edition;
    public Textbook(String title, String author, String isbn, double price, String subject, int edition) {
        super(title, author, isbn, price);
        this.subject = subject;
        this.edition = edition;
    }
    @Override
    public String getBookType() {
        return "Textbook";
    }
    public String getSubject() { return subject; }
    public int getEdition() { return edition; }
}
