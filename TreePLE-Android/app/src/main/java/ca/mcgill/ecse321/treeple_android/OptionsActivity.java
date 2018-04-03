package ca.mcgill.ecse321.treeple_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


/**
 * Created by ilanahaddad on 2018-03-27.
 */

public class OptionsActivity extends AppCompatActivity {

    private String error = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the view from activity_options.xml
        setContentView(R.layout.activity_options);

        // initialize error message text view
        refreshErrorMessage();

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

    public void createSurvey(View view) {
    }
}
