package com.example.test6;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

// IM/2021/104 Start
public class DeleteAccount extends AppCompatActivity {

    // IM/2021/059 (start)
                                                                                                    // Initialize the objects for the firebase
    protected FirebaseAuth mAuth;
    protected FirebaseUser firebaseUser;


                                                                                                    // oncreate function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

                                                                                                    // delete acc textview get
        TextView deleteAcc = findViewById(R.id.deleteAccount_delete);
        mAuth = FirebaseAuth.getInstance();                                                         // connection for the firebase
        firebaseUser = mAuth.getCurrentUser();

        deleteAcc.setOnClickListener(new View.OnClickListener() {                                   // delete the account from the
            @Override
            public void onClick(View view) {
                                                                                                    // when user clicks the delete btn appear a dialog box to delete the account from the database
                AlertDialog.Builder dialog = new AlertDialog.Builder(DeleteAccount.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will result in completely removing your "+
                        "account from the system and you won't be able to access the app.");

                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {     // when user clicks the positive btn in the dialog box
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog progressDialog = new AlertDialog.Builder(DeleteAccount.this)    // delete the user account from the database
                                .setView(R.layout.dialog_progress)
                                .setCancelable(false)
                                .create();
                        progressDialog.show();                                                      // show dialog box

                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {// delete from the firebase
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();                                           // show the success message
                                if(task.isSuccessful()){
                                    Toast.makeText(DeleteAccount.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(DeleteAccount.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(DeleteAccount.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {    // if task does not succeed show an error message
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }

                                                                                                    // go back to the user profile
    public void GoFromDeleteAccToUserProfile(View view) {
        startActivity(new Intent(this, ProfileSettings.class));
    }
}

// IM/2021/104 End