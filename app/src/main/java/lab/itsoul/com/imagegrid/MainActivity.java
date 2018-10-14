package lab.itsoul.com.imagegrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

    private GridView imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageContainer = findViewById(R.id.imageContainer);
        JSONArray jsonArray = null;
        List<Image> images = new ArrayList<>();
        try {
            jsonArray = new JSONArray(photos);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String photo = jsonObject.getString("photo");
                String author = jsonObject.getString("author");
                String date = jsonObject.getString("date");
                images.add(new Image(photo, author, date));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
        imageContainer.setAdapter(adapter);

//        getData("https://api.myjson.com/bins/s7200");
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
                    List<Image> images = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String photo = jsonObject.getString("photo");
                        String author = jsonObject.getString("author");
                        String date = jsonObject.getString("date");
                        images.add(new Image(photo, author, date));
                    }


                    ImageAdapter adapter = new ImageAdapter(MainActivity.this, images);
                    imageContainer.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                System.out.println(response.body().string());
            }
        });
    }
}
