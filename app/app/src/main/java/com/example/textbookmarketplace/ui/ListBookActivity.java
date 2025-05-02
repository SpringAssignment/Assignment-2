package com.example.textbookmarketplace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.textbookmarketplace.R;
import com.example.textbookmarketplace.model.exceptions.DuplicateBookException;
import com.example.textbookmarketplace.manager.TextbookManager;
import com.example.textbookmarketplace.model.Textbook;

public class ListBookActivity extends AppCompatActivity {
    private TextbookManager textbookManager;
    private EditText etTitle, etAuthor, etIsbn, etPrice, etSubject, etEdition, etSellerName, etBankDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        // Initialize views
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etIsbn = findViewById(R.id.etIsbn);
        etPrice = findViewById(R.id.etPrice);
        etSubject = findViewById(R.id.etSubject);
        etEdition = findViewById(R.id.etEdition);
        etSellerName = findViewById(R.id.etSellerName);
        etBankDetails = findViewById(R.id.etBankDetails);

        textbookManager = new TextbookManager();

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBookListing();
            }
        });
        Intent returnIntent = new Intent();
        returnIntent.putExtra("newBookAdded", true);
        setResult(RESULT_OK, returnIntent);
        finish();
    }



    private void submitBookListing() {
        try {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String isbn = etIsbn.getText().toString().trim();
            double price = Double.parseDouble(etPrice.getText().toString().trim());
            String subject = etSubject.getText().toString().trim();
            int edition = Integer.parseInt(etEdition.getText().toString().trim());
            String sellerName = etSellerName.getText().toString().trim();
            String bankDetails = etBankDetails.getText().toString().trim();

            // Validate required fields
            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || subject.isEmpty() || sellerName.isEmpty() || bankDetails.isEmpty()) {
                throw new Exception("Please fill all required fields");
            }

            Textbook newBook = new Textbook(title, author, isbn, price, subject, edition);
            textbookManager.listBook(newBook, 1, sellerName, bankDetails);

            Toast.makeText(this, "Book listed successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (DuplicateBookException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price or edition format!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}