package com.example.learningapi.activity.editor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learningapi.R;
import com.example.learningapi.api.ApiClient;
import com.example.learningapi.api.ApiInterface;
import com.example.learningapi.model.Biodata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity implements EditorView{

    EditText ed_name,ed_address;
    ProgressDialog pd;

    //variable menampung api interface
    ApiInterface apiInterface;

    EditorPresenter presenter;

    int id;
    String nama, alamat;
    Menu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ed_name = findViewById(R.id.name);
        ed_address = findViewById(R.id.address);

        pd = new ProgressDialog(this);
        pd.setMessage("Mohon Tunggu....");

        presenter = new EditorPresenter(this);

        //membuat object inten
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        nama = intent.getStringExtra("nama");
        alamat= intent.getStringExtra("alamat");

//        panggil function berikut
        setDataFromIntentExtra();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_deitor, menu);
        actionMenu = menu;

        //validasi untuk menu action
        if(id != 0){
            //jika data ada,hanya menampilkan action menu edit dan delete
            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
        }else{
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String nama = ed_name.getText().toString().trim();
        String alamat = ed_address.getText().toString().trim();
        switch (item.getItemId()){
            case R.id.save:
                if(nama.isEmpty()){
                    ed_name.setError("Silahkan isi Nama anda terlebih dahulu");
                }else if(alamat.isEmpty()){
                    ed_address.setError("Silahkan isi Nama anda terlebih dahulu");
                }else{
                    //save
                    //function save
                    presenter.saveData(nama,alamat);
                }
                return true;

            case R.id.edit:
                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.update:
                if(nama.isEmpty()){
                    ed_name.setError("Silahkan isi Nama anda terlebih dahulu");
                }else if(alamat.isEmpty()){
                    ed_address.setError("Silahkan isi Nama anda terlebih dahulu");
                }else{
                    //function update
                    presenter.updateBiodata(id,nama,alamat);

                }
                return true;

            case R.id.delete:
                    //function delete
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirm !");
                alertDialog.setMessage("Yakin Ingin dihapus?");
                alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.deleteBiodata(id);
                    }
                });
                alertDialog.setPositiveButton("Cancel",(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }));
                alertDialog.show();
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void showProgress() {
        pd.show();
    }

    @Override
    public void hideProgress() {
        pd.hide();
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish(); //kembali ke activity.java
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }


    private void setDataFromIntentExtra() {
        if(id != 0){
            ed_name.setText(nama);
            ed_address.setText(alamat);

            getSupportActionBar().setTitle("Update Form");
            readMode();
        }else{
            editMode();
        }
    }

    private void editMode() {
        ed_name.setFocusableInTouchMode(true);
        ed_address.setFocusableInTouchMode(true);
    }

    private void readMode() {
        ed_name.setFocusableInTouchMode(false);
        ed_address.setFocusableInTouchMode(false);
        ed_name.setFocusable(false);
        ed_address.setFocusable(false);
    }
}
