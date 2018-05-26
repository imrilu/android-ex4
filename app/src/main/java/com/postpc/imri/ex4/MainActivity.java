package com.postpc.imri.ex4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<JsonElement> {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Button btn;
    GalleryAdapter mAdapter;
    ArrayList<String> mUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView666);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mUrls = new ArrayList<>();
        mAdapter = new GalleryAdapter(mUrls);
        recyclerView.setAdapter(mAdapter);

        btn = findViewById(R.id.getImgButton);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Config.BASE_ADDR).build();
        MyService service = restAdapter.create(MyService.class);
        service.getAlbum(Config.ALBUM_ID, this);
    }

    @Override
    public void success(JsonElement jsonElement, Response response) {
        JsonObject jo = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jo.get("data").getAsJsonObject().get("images").getAsJsonArray();

        mUrls.clear();
        int position = 0;
        for (JsonElement element : jsonArray)
        {
            mUrls.add(element.getAsJsonObject().get("link").getAsString());
            Log.e("URL", mUrls.get(position));
            ++position;
        }
        mAdapter.setmDataSource(mUrls);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
