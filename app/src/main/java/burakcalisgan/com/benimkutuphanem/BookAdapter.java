package burakcalisgan.com.benimkutuphanem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class BookAdapter extends BaseAdapter{
    private Context context;
    private List<Book> books;
    DatabaseReference databaseReference;
    EditText edtBookName, edtAuthor, edtNumberOfPages;
    LayoutInflater inflater;

    public BookAdapter(Context context, List<Book> books, DatabaseReference databaseReference, EditText edtBookName, EditText edtAuthor, EditText edtNumberOfPages) {
        this.context = context;
        this.books = books;
        this.databaseReference = databaseReference;
        this.edtBookName = edtBookName;
        this.edtAuthor = edtAuthor;
        this.edtNumberOfPages = edtNumberOfPages;
        inflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.layout_book_list,parent,false);

        TextView txtBookName = convertView.findViewById(R.id.txtBookName);
        TextView txtAuthor = convertView.findViewById(R.id.txtAuthor);
        TextView txtNumberOfPages = convertView.findViewById(R.id.txtNumberOfPages);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        Button btnUpdate = convertView.findViewById(R.id.btnUpdate);

        final Book book = books.get(position);
        txtBookName.setText(book.getBookName());
        txtAuthor.setText(book.getAuthor());
        txtNumberOfPages.setText(book.getNumberOfPages());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(book.getId()).removeValue();
                Toast.makeText(context,"Kitap Başarıyla Silindi.",Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtBookName.setText(book.getBookName());
                edtAuthor.setText(book.getAuthor());
                edtNumberOfPages.setText(book.getNumberOfPages());

                MainActivity.bookId = book.getId();

            }
        });

        return convertView;
    }
}
