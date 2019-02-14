package com.example.learningapi.activity.main;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.learningapi.R;
import com.example.learningapi.activity.editor.EditorActivity;
import com.example.learningapi.model.Biodata;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView{
    private static final int INTENT_ADD = 100;
    private static final int INTENT_EDIT = 200;
    //membuat variable floating
    FloatingActionButton btnadd;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;

    MainPresenter presenter;
    MainAdapter adapter;
    MainAdapter.ItemClickListener itemClickListener;

    List<Biodata> biodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mengambil id floating dan memasukannya ke variable yang sudah di buat di atas
        btnadd = findViewById(R.id.add);

        recyclerView = findViewById(R.id.recycle_view);
        swipeRefresh = findViewById(R.id.swipe_refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ini functi saat button floatingaction di klik,
        btnadd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  MainActivity.this.startActivityForResult(
                          new Intent(MainActivity.this, EditorActivity.class),
                          INTENT_ADD
                  );
              }
          }
        );

        //presenter adalah variabel untuk menampung objeck MainPresenter dari class MainPresenter
        presenter = new MainPresenter(this);
        //memanggil method getData yang terdapat pada variable objek presenter
        presenter.getData();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getData();
                    }
                }
        );

        //ini adalah function saat datanya akan kita klik, disini haru menampilkan nama
        itemClickListener = (new MainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = biodata.get(position).getId();
                String nama = biodata.get(position).getNama();
                String alamat = biodata.get(position).getAlamat();
//                Toast.makeText(MainActivity.this, nama, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("nama",nama);
                intent.putExtra("alamat", alamat);
                startActivityForResult(intent, INTENT_EDIT);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INTENT_ADD && resultCode == RESULT_OK){
            presenter.getData();//relod data
        }else if(requestCode == INTENT_EDIT && resultCode == RESULT_OK){
            presenter.getData();//relod data
        }
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Biodata> biodataList) {
        adapter = new MainAdapter(this,biodataList,itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        biodata = biodataList;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
