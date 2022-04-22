package com.example.examencaso3;

import Mod.DbHelper;
import Mod.Producto;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button btneliminar;
    Button btnEditar;
    Button btnGrabar;
    EditText nombre,id,descripcion,costo,precio,stock,fecha;
    ArrayList<String> datos;
    Producto prod = new Producto();
    List<Producto> lista;
    ListView listViewcliente;
    ArrayAdapter arrayAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        ImageButton buttonCamara=findViewById(R.id.buttonCamara);
        progressBar.setVisibility(View.INVISIBLE);
        new Hilo1().start();


        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null) {
            Toast.makeText(getApplicationContext(), "se creo la base de datos", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error de creacion ", Toast.LENGTH_LONG).show();
        }

        listar();

        listViewcliente = (ListView) findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, datos);
        listViewcliente.setAdapter(arrayAdapter);

        buttonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamara= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                if(intentCamara.resolveActivity(getPackageManager())!=null){

                    startActivityForResult(intentCamara,1);
                }
            }
        });

        btnGrabar = findViewById(R.id.btn_grabar);
        btnEditar = findViewById(R.id.btn_editar);
        btneliminar = findViewById(R.id.btn_eliminar);
        nombre=findViewById(R.id.txt_Nombre);
        id=findViewById(R.id.txt_id);
        descripcion=findViewById(R.id.txt_descripcion);
        costo=findViewById(R.id.txt_costo);
        precio=findViewById(R.id.txt_precio);
        stock=findViewById(R.id.txt_stock);
        fecha=findViewById(R.id.txt_fecha);

        btnGrabar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        guardarDatos(1);

                    }
                }

        );

        btnEditar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        guardarDatos(2);

                    }
                }
        );

        btneliminar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eliminar();

                    }
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            Bundle bundle =data.getExtras();
            Bitmap imagen= (Bitmap) bundle.get("data");
            ImageView imageView=findViewById(R.id.imageView);
            imageView.setImageBitmap(imagen);
        }

    }

    class Hilo1 extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void guardarDatos(int op) {
        Producto product = new Producto();

        product.setCodigo(Integer.parseInt(id.getText().toString()));
        product.setNombre(nombre.getText().toString());
        product.setDescripcion(descripcion.getText().toString());
        product.setCosto(Double.parseDouble(costo.getText().toString()));
        product.setPrecio(Double.parseDouble(precio.getText().toString()));
        product.setStock(Integer.parseInt(stock.getText().toString()));
        product.setFecha(fecha.getText().toString());

        if (op == 1) {
            product.guardar(MainActivity.this);
            Toast.makeText(getApplicationContext(), "se creo el producto".toString(), Toast.LENGTH_LONG).show();

        } else if (op == 2) {
            product.Editar(MainActivity.this);
            Toast.makeText(getApplicationContext(), "editando producto".toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void eliminar() {
        Producto product = new Producto();
        product.setCodigo(Integer.parseInt(id.getText().toString()));
        product.eliminar(MainActivity.this);
        Toast.makeText(getApplicationContext(), "se elimino el producto".toString(), Toast.LENGTH_LONG).show();

    }


    public void listar() {
        datos = new ArrayList<String>();
        lista = prod.listar(MainActivity.this);

        for (int i = 0; i < lista.size(); i++) {
            datos.add("CODIGO: "+lista.get(i).getCodigo()+" NOMBRE: "+lista.get(i).getNombre()+" DESCRIPCION: "+lista.get(i).getDescripcion()
                    +" COSTO: "+ lista.get(i).getCosto()+" PRECIO: "+lista.get(i).getPrecio()+" STOCK: "+
                    lista.get(i).getStock()+" FECHA: "+lista.get(i).getFecha());
        }
    }




}