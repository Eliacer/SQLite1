package com.example.eliacerfernandez.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    private EditText et1, et2, et3, et4;
    private Cursor fila;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.id_persona);
        et2 = (EditText) findViewById(R.id.nombres);
        et3 = (EditText) findViewById(R.id.apellidos);
        et4 = (EditText) findViewById(R.id.dni);


    }


    public void Registrar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id_alumno = et1.getText().toString();
        String nombres = et2.getText().toString();
        String apellidos = et3.getText().toString();
        String dni = et4.getText().toString();

        ContentValues registro = new ContentValues();  //es una clase para guardar datos
        registro.put("id_alumno", id_alumno);
        registro.put("nombres", nombres);
        registro.put("apellidos", apellidos);
        registro.put("dni", dni);
        bd.insert("alumno", null, registro);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        Toast.makeText(this, "Se cargaron los datos de la persona",
                Toast.LENGTH_SHORT).show();
    }

    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.
        String id_alumno = et1.getText().toString();
        Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                "select id_alumno,nombres,apellidos,dni  from alumno where id_alumno=" + id_alumno, null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            et1.setText(fila.getString(0));
            et2.setText(fila.getString(1));
            et3.setText(fila.getString(2));
            et4.setText(fila.getString(3));
        } else
            Toast.makeText(this, "No existe una persona con dicho dni" ,
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }

    public void Eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id_alumno = et1.getText().toString();
        int cant = bd.delete("alumno", "id_alumno=" + id_alumno, null); // (votantes es la nombre de la tabla, condición)
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró la persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe una persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }

    public void Actualizar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id_alumno = et1.getText().toString();
        String nombre = et2.getText().toString();
        String apellidos = et3.getText().toString();
        String dni = et4.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombres", nombre);
        registro.put("apellidos", apellidos);
        registro.put("dni", dni);
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        int cant = bd.update("alumno", registro, "id_alumno=" + id_alumno, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe una persona con dicho identificador",
                    Toast.LENGTH_SHORT).show();
    }
}
