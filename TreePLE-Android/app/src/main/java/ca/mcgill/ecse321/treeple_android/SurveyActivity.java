
package ca.mcgill.ecse321.treeple_android;

        import android.app.Activity;
        import android.content.Intent;
        import android.icu.text.SimpleDateFormat;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.annotation.RequiresApi;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.Spinner;
        import android.widget.TextView;

        import com.loopj.android.http.JsonHttpResponseHandler;
        import com.loopj.android.http.RequestParams;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Calendar;
        import java.sql.Date;
        import java.util.List;
        import java.util.Locale;

        import cz.msebera.android.httpclient.Header;


/**
 * Created by Asma Alromaih on 02/04/18.
 */

public class SurveyActivity extends AppCompatActivity {

    private String error = "";
    private List<String> status = new ArrayList<>();
    private ArrayAdapter<String> statusAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the view from activity_options.xml
        setContentView(R.layout.activity_survey);

        // initialize error message text view
        refreshErrorMessage();

        //Back button
        Button btnBack = (Button)findViewById(R.id.back_button);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurveyActivity.this.startActivity(new Intent(SurveyActivity.this, OptionsActivity.class));
            }
        });

        //Setting spinner for municipalities
        Spinner statusSpinner = (Spinner) findViewById(R.id.municipalitySpinner);
        statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
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

    public void refreshLists(View view) {
        refreshList(statusAdapter, status, "statuses");
    }

    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names, String restFunctionName) {
        HttpUtils.get(restFunctionName, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                names.clear();
                names.add("Please select...");
                for( int i = 0; i < response.length(); i++){
                    try {
                        names.add(response.getJSONObject(i).getString("name"));
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    //error += errorResponse.getString("message");
                    error += errorResponse.get("message").toString();

                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    public void createSurvey(View view) {
        error = "";
        RequestParams rp = new RequestParams();

        //Today's date:
        final Calendar today = Calendar.getInstance();
        rp.add("date",today.toString());

        //To get tree ID
        TextView tv = (TextView) findViewById(R.id.treeID);
        String treeID = tv.getText().toString();
        rp.add("tree",treeID); //TODO: int

        //To get surveyor's name
        tv = (TextView) findViewById(R.id.userName);
        String name= tv.getText().toString();
        rp.add("surveyor",name);

        //To get tree status
        Spinner sp = (Spinner) findViewById(R.id.statusSpinner);
        String status = sp.getSelectedItem().toString();
        rp.add("newTreeStatus", status);

        HttpUtils.post("/newSurvey/", rp , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();

                ((TextView) findViewById(R.id.treeID)).setText("");//reset ID

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }


    public void refreshTrees(View view) {
    }
}
