package lab.itsoul.com.imagegrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private Executor executor = Executors.newSingleThreadExecutor();
    private String photos = "[  \n" +
            "   {  \n" +
            "      \"photo\":\"https://static.pexels.com/photos/36764/marguerite-daisy-beautiful-beauty.jpg\",\n" +
            "      \"author\":\"Toby Keller\",\n" +
            "      \"date\":\"2018-05-27T08:09:05.979Z\"\n" +
            "   },\n" +
            "   {  \n" +
            "      \"photo\":\"https://static.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg\",\n" +
            "      \"author\":\"Louis Vest\",\n" +
            "      \"date\":\"2018-05-22T08:17:43.250Z\"\n" +
            "   },\n" +
            "   {  \n" +
            "      \"photo\":\"https://static.pexels.com/photos/56866/garden-rose-red-pink-56866.jpeg\",\n" +
            "      \"author\":\"Mike Behnken\",\n" +
            "      \"date\":\"2018-05-22T08:05:06.121Z\"\n" +
            "   },\n" +
            "   {  \n" +
            "      \"photo\":\"https://static.pexels.com/photos/36753/flower-purple-lical-blosso.jpg\",\n" +
            "      \"author\":\"Luigi Alesi\",\n" +
            "      \"date\":\"2018-05-22T08:20:43.134Z\"\n" +
            "   },\n" +
            "   {  \n" +
            "      \"photo\":\"https://static.pexels.com/photos/60597/dahlia-red-blossom-bloom-60597.jpeg\",\n" +
            "      \"author\":\"Eleder Jimenez Hermoso\",\n" +
            "      \"date\":\"2018-05-22T08:09:43.300Z\"\n" +
            "   }\n" +
            "]";

    List<Image> images;

    private GridView imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageContainer = findViewById(R.id.imageContainer);
        JSONArray jsonArray;
//        images = new ArrayList<>();
//        try {
//            jsonArray = new JSONArray(photos);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String photo = jsonObject.getString("photo");
//                String author = jsonObject.getString("author");
//                String date = jsonObject.getString("date");
//                images.add(new Image(photo, author, stringToDate(date)));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
//        imageContainer.setAdapter(adapter);

        getData("https://api.myjson.com/bins/s7200");
    }

    private void getData(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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

                    loadData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                System.out.println(response.body().string());
            }
        });
    }

    private void loadData() {
        System.out.println(images);
//        ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
//        imageContainer.setAdapter(adapter);
    }

    public void sortAscending(View view) {
        images.sort(Comparator.comparing(Image::getAuthor));
        ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
        imageContainer.setAdapter(adapter);
    }

    public void sortDescending(View view) {
        images.sort(Comparator.comparing(Image::getAuthor).reversed());
        ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
        imageContainer.setAdapter(adapter);
    }

    public void sortDate(View view) {
        images.sort(Comparator.comparing(Image::getDate).reversed());
        ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
        imageContainer.setAdapter(adapter);
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
