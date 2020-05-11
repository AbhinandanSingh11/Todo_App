package com.abhinandan.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TODO> list;
    private RecyclerView recyclerView;
    public static TextView text;
    private FloatingActionButton fab;
    private ScrollView add;
    private EditText title,secondary,primary;
    private MaterialButton save,discard;
    private String TextTitle, TextSecondary, TextPrimary;
    private AdapterRecylerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        text = findViewById(R.id.text);
        fab = findViewById(R.id.fab);
        add = findViewById(R.id.add);
        title = findViewById(R.id.add_title);
        secondary = findViewById(R.id.add_secondary);
        primary = findViewById(R.id.add_primary);
        save = findViewById(R.id.save);
        discard = findViewById(R.id.discard);

        list = new ArrayList<>();
        adapter = new AdapterRecylerView(list,MainActivity.this);

        registerForContextMenu(recyclerView);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();

        int size = sharedPreferences.getInt("size",0);

        for(int i = 0; i<size; i++){
            String json = sharedPreferences.getString("TODO"+i,"");
            TODO todo = gson.fromJson(json,TODO.class);
            list.add(todo);
            adapter.notifyDataSetChanged();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCard();

                if(add.isFocusable()){
                    text.setVisibility(View.GONE);
                }

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextTitle = title.getText().toString().trim();
                        TextSecondary = secondary.getText().toString().trim();
                        TextPrimary = primary.getText().toString().trim();

                        if(TextTitle.length()==0){
                            TextTitle = null;
                        }
                        if(TextSecondary.length()==0){
                            TextSecondary = null;
                        }
                        if(TextPrimary.length()>0){

                            title.getText().clear();
                            secondary.getText().clear();
                            primary.getText().clear();

                            TODO todo = new TODO(TextTitle,TextSecondary,TextPrimary);

                            list.add(todo);

                            hideCard();

                            Toast.makeText(MainActivity.this, "new Task Added", Toast.LENGTH_SHORT).show();

                            adapter.notifyDataSetChanged();
                        }
                        else{

                            Toast.makeText(MainActivity.this, "Please add note", Toast.LENGTH_SHORT).show();
                        }



                    }
                });

                discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideCard();
                        Toast.makeText(MainActivity.this, "Discarded", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //SetUpData();
        SetUpRecyclerView();



    }


    void SetUpRecyclerView(){
        //adapter = new AdapterRecylerView(list,this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    void hideCard(){
        fab.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        add.setVisibility(View.GONE);
        //text.setVisibility(View.VISIBLE);
    }

    void showCard(){
        fab.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        //text.setVisibility(View.GONE);
        add.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("size", list.size());
        editor.commit();
        Gson gson = new Gson();

        for (int i = 0; i < list.size(); i++) {
            String json = gson.toJson(list.get(i));
            editor.putString("TODO" + i, json);
            editor.commit();
        }
    }


}
