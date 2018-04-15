package ca.mcgill.ecse321.treeple_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by ilanahaddad on 2018-03-27.
 */

public class OptionsActivity extends AppCompatActivity {

    private String error = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the view from activity_options.xml
        setContentView(R.layout.activity_options);

        // initialize error message text view
        refreshErrorMessage();

        //Back button -> takes to login page
        Button btnBack = (Button)findViewById(R.id.back_button);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsActivity.this.startActivity(new Intent(OptionsActivity.this, MainActivity.class));
            }
        });

        //Tree button -> takes to tree page
        Button btnTree = (Button)findViewById(R.id.btnTree);
        btnTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsActivity.this.startActivity(new Intent(OptionsActivity.this, TreeActivity.class));
            }
        });


        //Survey button -> takes to survey page
        Button btnSurvey = (Button)findViewById(R.id.btnSurvey);
        btnSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsActivity.this.startActivity(new Intent(OptionsActivity.this, SurveyActivity.class));
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

}
