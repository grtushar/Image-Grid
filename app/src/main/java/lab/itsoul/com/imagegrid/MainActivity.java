package lab.itsoul.com.imagegrid;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private List<Image> images;


    private GridView imageContainer;
    private EditText urlET;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageContainer = findViewById(R.id.imageContainer);
        urlET = findViewById(R.id.txtUrl);
        spinner = findViewById(R.id.progressBar);
    }

    private void getData(String url, String op) {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Error occured while fetching data!", Toast.LENGTH_SHORT).show();
                    imageContainer.setVisibility(View.GONE);
                    spinner.setVisibility(View.GONE);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(() -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());

                        images = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String photo = jsonObject.getString("photo");
                            String author = jsonObject.getString("author");
                            String date = jsonObject.getString("date");

                            images.add(new Image(photo, author, stringToDate(date)));
                        }
                        spinner.setVisibility(View.GONE);
                        imageContainer.setVisibility(View.VISIBLE);
                        loadData(op);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }


    private void loadData(String op) {
        if(op.equals("asc")) images.sort(Comparator.comparing(Image::getAuthor));
        else if(op.equals("des")) images.sort(Comparator.comparing(Image::getAuthor).reversed());
        else images.sort(Comparator.comparing(Image::getDate).reversed());

        ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
        imageContainer.setAdapter(adapter);
    }

    public void sortAscending(View view) {
        imageContainer.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        getData(urlET.getText().toString(), "asc");
    }

    public void sortDescending(View view) {
        imageContainer.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        getData(urlET.getText().toString(), "des");
    }

    public void sortDate(View view) {
        imageContainer.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        getData(urlET.getText().toString(), "recent");
    }

    private Date stringToDate(String input ) {

        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );

        //this is zero time so we need to add that TZ indicator for
        if ( input.endsWith( "Z" ) ) {
            input = input.substring( 0, input.length() - 1) + "GMT-00:00";
        } else {
            int inset = 6;

            String s0 = input.substring( 0, input.length() - inset );
            String s1 = input.substring( input.length() - inset, input.length() );

            input = s0 + "GMT" + s1;
        }

        try {
            return df.parse( input );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
