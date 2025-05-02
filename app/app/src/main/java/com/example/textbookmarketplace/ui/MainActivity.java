package com.example.textbookmarketplace.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;
import com.example.textbookmarketplace.R;
import com.example.textbookmarketplace.model.exceptions.DuplicateBookException;
import com.example.textbookmarketplace.manager.TextbookManager;
import com.example.textbookmarketplace.model.Book;
import com.example.textbookmarketplace.model.Textbook;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextbookManager textbookManager;
    private ListView lvBooks;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components
        textbookManager = TextbookManager.getInstance();
        lvBooks = findViewById(R.id.lvBooks);
        etSearch = findViewById(R.id.etSearch);

        // Load sample data
        loadSampleData();

        // Set up search button
        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        // Set up list book button
        findViewById(R.id.btnListBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListBookActivity.class));
            }
        });
    }


    private void loadSampleData() {
        try {
            textbookManager.listBook(
                    new Textbook("Java Programming", "John Doe", "123-456", 29.99, "Computer Science", 5),
                    3, "Alice", "Bank:12345"
            );
            textbookManager.listBook(
                    new Textbook("Android Development", "Jane Smith", "789-012", 39.99, "Mobile Computing", 3),
                    2, "Bob", "Bank:67890"
            );
        } catch (DuplicateBookException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void performSearch() {
        String query = etSearch.getText().toString().trim();
        List<Book> results;

        if (query.isEmpty()) {
            results = textbookManager.getAllBooks();
        } else {
            // Try searching by title first, then by seller if no results
            results = textbookManager.searchByTitle(query);
            if (results.isEmpty()) {
                results = textbookManager.searchBySeller(query);
            }
        }

        updateBookList(results);
    }

    private void updateBookList(List<Book> books) {
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                books
        );
        lvBooks.setAdapter(adapter);
    }
}