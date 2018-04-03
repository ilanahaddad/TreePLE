package ca.mcgill.ecse321.treeple_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Asma Alromaih on 02/04/18.
 */

public class TreeActivity extends AppCompatActivity {

    private String error = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the view from activity_options.xml
        setContentView(R.layout.activity_options);

        // initialize error message text view
        refreshErrorMessage();

        //options button -> takes to option page
        Button btnOption = (Button)findViewById(R.id.btnOption);
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreeActivity.this.startActivity(new Intent(TreeActivity.this, OptionsActivity.class));
            }
        });
        //Login button -> takes to login page
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreeActivity.this.startActivity(new Intent(TreeActivity.this, MainActivity.class));
            }
        });


    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }

    }

    public void createTree(View view) {
    }
}
