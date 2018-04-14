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

    private String error = null;
    private List<String> municipalities = new ArrayList<>();
    private ArrayAdapter<String> municipalitiesAdapter;
    private List<String> landUse = new ArrayList<String>(){{add("Residential");add("NonResidential");}};
    private ArrayAdapter<String> landUseAdapter;


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

        //Setting spinner for land use
        Spinner landUseSpinner = (Spinner) findViewById(R.id.landUseSpinner);
        landUseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, landUse);
        landUseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        landUseSpinner.setAdapter(landUseAdapter);

        //Retrieving the municipalities & land use lists from backend
        //refreshLists(this.getCurrentFocus());
    }
    public void createTree(View view) {

        error = "";
        RequestParams rp = new RequestParams();


        //To get species from user
        TextView tv = (TextView) findViewById(R.id.speciesName);
        String species = tv.getText().toString();

        //To get height from user
        tv = (TextView) findViewById(R.id.height);
        String heightString= tv.getText().toString();
        double height = Double.parseDouble(heightString);
        //rp.add("height",(findViewById(R.id.height).getText().toString()));
        rp.add("height",heightString); //TODO: double

        //To get diameter from user
        tv = (TextView) findViewById(R.id.diameter);
        String diameterString= tv.getText().toString();
        double diameter = Double.parseDouble(diameterString);
        rp.add("diameter", diameterString);//TODO: double

        //To get municipality from user
        Spinner sp = (Spinner) findViewById(R.id.municipalitySpinner);
        String municipality = sp.getSelectedItem().toString();
        rp.add("municipality", municipality); //TODO: municipalityDTO

        //To get latitude
        tv = (TextView) findViewById(R.id.latitude);
        String latitudeString= tv.getText().toString();
        double latitude = Double.parseDouble(latitudeString);
        rp.add("latitude", latitudeString);//TODO: double


        //To get longitude
        tv = (TextView) findViewById(R.id.longitude);
        String longitudeString= tv.getText().toString();
        double longitude = Double.parseDouble(longitudeString);
        rp.add("longitude", longitudeString);//TODO: double


        //To get owner name
        tv = (TextView) findViewById(R.id.userName);
        String userName= tv.getText().toString();
        rp.add("owner", userName);

        //To get tree age
        tv = (TextView) findViewById(R.id.age);
        String ageString= tv.getText().toString();
        double age = Double.parseDouble(ageString);
        rp.add("age", ageString);//TODO: int

        //To get land use from user
        sp = (Spinner) findViewById(R.id.landUseSpinner);
        RequestParams rpLandUse = new RequestParams();
        String landUse = sp.getSelectedItem().toString();
        rp.add("landuse", landUse); //TODO: municipalityDTO

       HttpUtils.post("/newTree/" +species, rp , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                //Reset all the settings

                ((TextView) findViewById(R.id.speciesName)).setText("");//species
                ((TextView) findViewById(R.id.height)).setText("");//height
                ((TextView) findViewById(R.id.diameter)).setText("");//diameter
                ((TextView) findViewById(R.id.latitude)).setText("");//latitude
                ((TextView) findViewById(R.id.longitude)).setText("");//longitude
                ((TextView) findViewById(R.id.age)).setText("");//age

                //TODO: return tree id
                //response.getJSONObject("")
                ((TextView) findViewById(R.id.printID)).setText("ID should print here");
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
        refreshList(municipalitiesAdapter, municipalities, "municipalities");
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
