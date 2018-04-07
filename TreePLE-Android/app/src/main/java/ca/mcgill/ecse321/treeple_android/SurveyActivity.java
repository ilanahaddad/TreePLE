
package ca.mcgill.ecse321.treeple_android;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;


/**
 * Created by Asma Alromaih on 02/04/18.
 */

public class SurveyActivity extends AppCompatActivity {

    private String error = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the view from activity_options.xml
        setContentView(R.layout.activity_survey);

        // initialize error message text view
        refreshErrorMessage();

        Button btnBack = (Button)findViewById(R.id.back_button);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurveyActivity.this.startActivity(new Intent(SurveyActivity.this, OptionsActivity.class));
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



    public void createSurvey(View view) {
    }


    public void refreshLists(View view) {
    }
}
