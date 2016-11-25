package com.example.jordy.to_dolist2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView todolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find ListView to populate
        todolist = (ListView) findViewById(R.id.todolist);
        showList();
    }

    public class TodoCursorAdapter extends CursorAdapter {

        public TodoCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView tvElement = (TextView) view.findViewById(R.id.todo_element);
            // Extract properties from cursor
            String task = cursor.getString(cursor.getColumnIndexOrThrow("task"));
            // Populate fields with extracted properties
            tvElement.setText(task);
        }
    }

    public void AddToListView(View view){

        DBhelper dbHelper = new DBhelper(this);

        EditText todo_input = (EditText) findViewById(R.id.user_input);
        String task = todo_input.getText().toString();

        if (task.length() != 0) {
            dbHelper.create(task);
            dbHelper.close();
            todo_input.setText("");
        }
        else{
            Toast.makeText(this, "Please enter a task!", Toast.LENGTH_LONG).show();
        }
        showList();
    }


    public void showList(){
        DBhelper dbHelper = new DBhelper(this);
        Cursor cursor = dbHelper.read();

        // Setup cursor adapter using cursor from last step
        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, cursor);
        ListView todolist = (ListView) findViewById(R.id.todolist);
        // Attach cursor adapter to the ListView
        todolist.setAdapter(todoAdapter);

        dbHelper.close();



        todolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView item = (TextView) findViewById(R.id.todo_element);
                item.setPaintFlags(item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });

        todolist.setOnLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DBhelper dbHelper = new DBhelper(DBHelper.class);
                dbHelper.delete(R.id.todo_element);
            }
        });


    }
}