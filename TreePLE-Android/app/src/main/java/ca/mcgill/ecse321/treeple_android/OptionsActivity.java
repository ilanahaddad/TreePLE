package ca.mcgill.ecse321.treeple_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by ilanahaddad on 2018-03-27.
 */

public class OptionsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the view from activity_options.xml
        setContentView(R.layout.activity_options);
     }

    public void viewSurveysPage(View view) {

    }
}
