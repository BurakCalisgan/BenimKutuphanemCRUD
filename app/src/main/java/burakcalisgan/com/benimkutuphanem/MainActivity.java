package burakcalisgan.com.benimkutuphanem;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnSave;
    EditText edtBookName, edtAuthor, edtNumberOfPages;
    DatabaseReference databaseReference;
    ListView listViewBooks;
    BookAdapter bookAdapter;
    ArrayList<Book> books;
    public static String bookId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        books = new ArrayList<Book>();
        btnSave = findViewById(R.id.btnSave);
        edtBookName = findViewById(R.id.edtBookName);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtNumberOfPages = findViewById(R.id.edtNumberOfPages);
        listViewBooks = findViewById(R.id.lvBookList);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = edtBookName.getText().toString();
                String author = edtAuthor.getText().toString();
                String numberOfPages = edtNumberOfPages.getText().toString();

                if (TextUtils.isEmpty(bookId)){
                    //Save
                    String id = databaseReference.push().getKey();
                    Book book = new Book(id,bookName,author,numberOfPages);
                    databaseReference.child(id).setValue(book);

                    Toast.makeText(getApplicationContext(),"Kitap Başarıyla Eklendi.", Toast.LENGTH_SHORT).show();
                }
                else{
                    //update
                    databaseReference.child(bookId).child("bookName").setValue(bookName);
                    databaseReference.child(bookId).child("author").setValue(author);
                    databaseReference.child(bookId).child("numberOfPages").setValue(numberOfPages);

                    Toast.makeText(getApplicationContext(),"Kitap Başarıyla Güncellendi.",Toast.LENGTH_SHORT).show();
                    bookId = null;

                }
                edtBookName.setText(null);
                edtAuthor.setText(null);
                edtNumberOfPages.setText(null);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Read
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();
                Book book;
                String id ,bookName, author, numberOfPages;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Map<String,String> map = (Map<String,String>) postSnapshot.getValue();
                    id = map.get("id");
                    bookName = map.get("bookName");
                    author = map.get("author");
                    numberOfPages = map.get("numberOfPages");


                    book =  new Book(id,bookName,author,numberOfPages);
                    books.add(book);
                }

                bookAdapter = new BookAdapter(getApplicationContext(),books,databaseReference,edtBookName,edtAuthor,edtNumberOfPages);
                listViewBooks.setAdapter(bookAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Veri Okuma Hatası :" + databaseError.toException().toString(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}
