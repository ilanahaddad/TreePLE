package ca.mcgill.ecse321.treeple_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Asma Alromaih on 02/04/18.
 */

public class TreeActivity extends AppCompatActivity {

    private String error = "";
    private List<String> municipalities = new ArrayList<>();
    private ArrayAdapter<String> municipalitiesAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the view from activity_options.xml
        setContentView(R.layout.activity_tree);

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

        //Setting spinner for municipalities
        Spinner municipalitiesSpinner = (Spinner) findViewById(R.id.municipalitySpinner);
        municipalitiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, municipalities);
        municipalitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        municipalitiesSpinner.setAdapter(municipalitiesAdapter);

        //Retrieving the municipalities from backend
        refreshLists(this.getCurrentFocus());
    }

    public void createTree(View view) {
        error = "";

        //To get species from user
        EditText et = (EditText) findViewById(R.id.speciesName);
        String species = et.getText().toString();

        //To get height from user
        et = (EditText) findViewById(R.id.height);
        String heightString= et.getText().toString();
        double height = Double.parseDouble(heightString);

        //To get diameter from user
        et = (EditText) findViewById(R.id.diameter);
        String diameterString= et.getText().toString();
        double diameter = Double.parseDouble(diameterString);

        //To get municipality from user
        Spinner sp = (Spinner) findViewById(R.id.municipalitySpinner);
        RequestParams rpMunicipality = new RequestParams();
        rpMunicipality.add("municipality", sp.getSelectedItem().toString()); //TODO: municipalityDTO

        //To get latitude
        et = (EditText) findViewById(R.id.latitude);
        String latitudeString= et.getText().toString();
        double latitude = Double.parseDouble(latitudeString);

        //To get longitude
        et = (EditText) findViewById(R.id.longitude);
        String longitudeString= et.getText().toString();
        double longitude = Double.parseDouble(longitudeString);

        //To get owner name
        et = (EditText) findViewById(R.id.userName);
        String userName= et.getText().toString();

        //To get tree age
        et = (EditText) findViewById(R.id.age);
        String ageString= et.getText().toString();
        double age = Double.parseDouble(ageString);

        //TODO: land use

       HttpUtils.post("/newTree/" +species+height+diameter+rpMunicipality+latitude+longitude+userName+age, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                //tv.setText("");
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

    public void refreshLists(View view) {
        refreshList(municipalitiesAdapter, municipalities, "municipalities");//TODO: maybe /municipalities
    }

    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names, String restFunctionName) {
        HttpUtils.get(restFunctionName, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                names.clear();
                names.add("Please select...");
                TextView tvError = (TextView) findViewById(R.id.error);
                tvError.setText(error);
                for( int i = 0; i < response.length(); i++){
                    try {
                        names.add(response.getJSONObject(i).getString("name")); //TODO: maybe munName
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
                    error += errorResponse.getString("message");
                    //error += errorResponse.get("message").toString();

                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
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
