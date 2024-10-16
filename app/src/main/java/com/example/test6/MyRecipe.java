package com.example.test6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyRecipe extends AppCompatActivity {

    // IM/2021/118 (start)
    // initialize objects
    GridView gridView;
    protected FirebaseAuth mAuth;
    ArrayList<addRecipeClass> addRecipeList;
    Adapter1 adapter1, searchAdapter;
    SearchView searchView;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("recipes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();

        // get the items from the xml
        gridView = findViewById(R.id.gridView);
        addRecipeList = new ArrayList<>();
        adapter1 = new Adapter1(addRecipeList, this);
        gridView.setAdapter(adapter1);
        searchView = findViewById(R.id.my_recipe_searchView);
        searchView.clearFocus();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchList(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchList(s);
                return true;
            }
        });

        // database reference added
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addRecipeList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    addRecipeClass addRecipeClass = dataSnapshot1.getValue(addRecipeClass.class);

                    if (addRecipeClass != null && addRecipeClass.getUserId().equals(currentUserId)) {
                        addRecipeList.add(addRecipeClass);
                    }
                }
                adapter1.notifyDataSetChanged();
                if (addRecipeList.isEmpty()) {
                    // Display a message that no recipes were created
                    Toast.makeText(MyRecipe.this, "No recipes created yet.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // Bottom navigation code
        // Start here
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.btnedit);

        bottomNavigationView.setOnItemSelectedListener(item ->{
            int itemId = item.getItemId();

            if (itemId == R.id.btnhome) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.btnedit) {
                return true;
            } else if (itemId == R.id.fav) {
                startActivity(new Intent(getApplicationContext(), Favourites.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
//            } else if (itemId == R.id.history) {
//                startActivity(new Intent(getApplicationContext(), History.class));
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                finish();
//                return true;
            } else if (itemId == R.id.user) {
                startActivity(new Intent(getApplicationContext(), UserHome.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
        // Ends here
    }

    public  void searchList(String text){
        ArrayList<addRecipeClass> searchList = new ArrayList<>();
        for(addRecipeClass recipe: addRecipeList){
            if(recipe.getName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(recipe);
            }
        }
        adapter1.searchDatalist(searchList);
    }

    public void GoToAddRecipe(View view) {
        startActivity(new Intent(this, AddRecipe.class));
    }
}

// IM/2021/118 (end)