package com.example.apputnsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.entities.Autor;
import com.example.entities.Autores;
import com.example.entities.Libro;
import com.example.entities.Libros;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibrosActivity extends AppCompatActivity {
    Autores autoresDB;
    Libros librosDB;
    EditText et_idLibro, et_titulo, et_isbn, et_anio_publicacion, et_revision, et_nro_hojas, et_idAutor;
    TextView tvTitulo2;
    ImageButton btn_crear, btn_leer, btn_actualizar, btn_borrar;
    BottomNavigationView bottom_navigation;
    Menu menu_bottom_navigation;
    MenuItem action_libros, action_autores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_libros);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Instanciar
        et_idLibro = findViewById(R.id.et_idLibro);
        et_titulo = findViewById(R.id.et_titulo);
        et_idAutor = findViewById(R.id.et_idAutor);
        et_isbn = findViewById(R.id.et_isbn);
        et_anio_publicacion = findViewById(R.id.et_anio_publicacion);
        et_revision = findViewById(R.id.et_revision);
        et_nro_hojas = findViewById(R.id.et_nro_hojas);
        tvTitulo2= findViewById(R.id.tv_titulo);

        //Para la BDD
        autoresDB = new Autores(this, "biblioteca.db", 1);
        librosDB = new Libros(this, "biblioteca.db", 1);

        //Manejo de la Barra inferior
        bottom_navigation = findViewById(R.id.bottom_navigation);
        menu_bottom_navigation = bottom_navigation.getMenu();
        action_libros = menu_bottom_navigation.findItem(R.id.action_libros);
        action_autores = menu_bottom_navigation.findItem(R.id.action_autores);

        //Cambio entre la navergación
        action_autores.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Acción para el ítem de autores
                finish();
                return true;
            }
        });

        action_libros.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Acción para el ítem de libros
                Intent intent = new Intent(LibrosActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        });

        //Mantener datos de sesion
        Bundle extra = getIntent().getExtras();
        tvTitulo2.setText(extra.getString("usuario"));
    }

    public void onClick_Crear(android.view.View view) {
        Libro r;
        String titulo, isbn;
        int idLibro, idAutor, anioPublicacion, revision, nroHojas;

        idLibro = Integer.parseInt(et_idLibro.getText().toString());
        titulo =  et_titulo.getText().toString();
        idAutor = Integer.parseInt(et_idAutor.getText().toString());
        isbn = et_isbn.getText().toString();
        anioPublicacion = Integer.parseInt(et_anio_publicacion.getText().toString());
        revision = Integer.parseInt(et_revision.getText().toString());
        nroHojas = Integer.parseInt(et_nro_hojas.getText().toString());

        r = librosDB.Create(idLibro, titulo, idAutor, isbn, anioPublicacion,revision,nroHojas);

        if(r != null)
        {
            Toast.makeText(
                    this,
                    "Libro creado correctamente",
                    Toast.LENGTH_LONG
            ).show();
        }
        else
        {
            Toast.makeText(
                    this, "ERROR AL CREAR LIBRO",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void onClick_Actualizar(android.view.View view) {
        Libro r;
        String titulo, isbn;
        int idLibro, idAutor, anioPublicacion, revision, nroHojas;

        idLibro = Integer.parseInt(et_idLibro.getText().toString());
        titulo =  et_titulo.getText().toString();
        idAutor = Integer.parseInt(et_idAutor.getText().toString());
        isbn = et_isbn.getText().toString();
        anioPublicacion = Integer.parseInt(et_anio_publicacion.getText().toString());
        revision = Integer.parseInt(et_revision.getText().toString());
        nroHojas = Integer.parseInt(et_nro_hojas.getText().toString());

        r = librosDB.Update(idLibro, titulo, idAutor, isbn, anioPublicacion,revision,nroHojas);

        if(r != null)
        {
            Toast.makeText(
                    this,
                    "Libro ACTUALIZADO correctamente",
                    Toast.LENGTH_LONG
            ).show();
        }
        else
        {
            Toast.makeText(
                    this, "ERROR AL ACTUALIZAR Libro",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void onClick_Leer(android.view.View view) {
        int idLibro;

        idLibro = Integer.parseInt(et_idLibro.getText().toString());
        Libro r = librosDB.Read_ById(idLibro);

        if(r != null)
        {

            et_titulo.setText(r.titulo);
            et_idAutor.setText(r.idAutor+ "");
            et_isbn.setText(r.isbn);
            et_anio_publicacion.setText(r.anioPublicacion+ "");
            et_revision.setText(r.revision + "");
            et_nro_hojas.setText(r.nroHojas + "");

            Toast.makeText(
                    this,
                    "Libro ENCONTRADO correctamente",
                    Toast.LENGTH_LONG
            ).show();
        }
        else
        {
            et_titulo.setText("");
            et_idAutor.setText("");
            et_isbn.setText("");
            et_anio_publicacion.setText("");
            et_revision.setText("");
            et_nro_hojas.setText("");

            Toast.makeText(
                    this, "Registo NO ENCONTRADO",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void onClick_Borrar(android.view.View view) {
        int idLibro;

        idLibro = Integer.parseInt(et_idLibro.getText().toString());
        Libro l = librosDB.Read_ById(idLibro);
        boolean r = autoresDB.Delete(idLibro);

        if(r)
        {
            Toast.makeText(
                    this,
                    "Libro "+l.titulo+" BORRADO correctamente",
                    Toast.LENGTH_LONG
            ).show();
        }
        else
        {
            et_titulo.setText("");
            et_idAutor.setText("");
            et_isbn.setText("");
            et_anio_publicacion.setText("");
            et_revision.setText("");
            et_nro_hojas.setText("");


            Toast.makeText(
                    this, "Registo NO ENCONTRADO",
                    Toast.LENGTH_LONG
            ).show();
        }

    }


}