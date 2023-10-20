package com.example.aplicacionopenhelper

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val codigo = findViewById<EditText>(R.id.codigo)
        val nombre = findViewById<EditText>(R.id.nombre)
        val telefono = findViewById<EditText>(R.id.telefono)

        val btnInsertar = findViewById<Button>(R.id.btnInsertar)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        btnInsertar.setOnClickListener {
            val admin=OpenHelper(this, "bdEstudiante",null,1)
            val baseDeDatos =admin.writableDatabase

            val codigoBd=codigo.text.toString()
            val nombreBd=nombre.text.toString()
            val telefonoBd=telefono.text.toString()

            if (!codigoBd.isEmpty()&&!nombreBd.isEmpty()&&!telefonoBd.isEmpty()){
                val registro=ContentValues()
                registro.put("codigo",codigoBd)
                registro.put("nombre",nombreBd)
                registro.put("telefono",telefonoBd)

                baseDeDatos.insert("estudiante",null,registro)
                baseDeDatos.close()

                Toast.makeText(this,"registro insertado", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"campos vacios", Toast.LENGTH_LONG).show()
            }
        }
        btnBuscar.setOnClickListener {
            val admin=OpenHelper(this, "bdEstudiante",null,1)
            val baseDeDatos =admin.writableDatabase

            val codigoBd=codigo.text.toString()
            if(!codigoBd.isEmpty()){
                val dato:Cursor
                dato=baseDeDatos.rawQuery("select * from estudiante where codigo = $codigoBd",null)
                if(dato.moveToFirst()){
                    codigo.setText(dato.getString(0))
                    nombre.setText(dato.getString(1))
                    telefono.setText(dato.getString(2))

                }else{
                    Toast.makeText(this,"el estudiante no existe", Toast.LENGTH_LONG).show()
                }
                baseDeDatos.close()
            }
            else{
                Toast.makeText(this,"el codigo esta vacio", Toast.LENGTH_LONG).show()
            }

        }

    }


}