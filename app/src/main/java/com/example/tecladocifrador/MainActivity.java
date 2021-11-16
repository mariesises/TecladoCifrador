package com.example.tecladocifrador;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnLongClickListener {
    /**
     * Creo las variables que voy a usar en el teclado
     * Los valores que van en el teclado, el textview de salida de datos, los botones y un spinner donde se va a pedir cuanto desplazar
     */

    //String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    String salida = "";
    TextView salidatexto;
    Button btn1, btnEncriptar, btnDesencript, btnteclas;
    Spinner spDesplazar;
    String abcesar = "abcdefghijklmnñopqrstuvwxyz";
    GridLayout glbotones;
    public boolean maysc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Llamo al metodo agregarbotones para que cree el teclado
        agregarbotones();

        //Inicializo y meto los valores al spinner, llamo a los botones para que se escuchen al clickar en ellos
        String[] codigos = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        spDesplazar = findViewById(R.id.spCodigo);
        spDesplazar.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, codigos));
        spDesplazar.setOnItemSelectedListener(this);

        //por aqui se va a mostrar la salida
        salidatexto = (TextView) findViewById(R.id.txtSalida);

        //boton para las mayusculas
        btn1 = findViewById(R.id.btnMayus);
        btn1.setOnClickListener(this);
        btn1.setOnLongClickListener(this);

        //botones para encriptar y desencriptar
        btnEncriptar = findViewById(R.id.btnEncrip);
        btnEncriptar.setOnClickListener(this);

        btnDesencript = findViewById(R.id.btnDesenc);
        btnDesencript.setOnClickListener(this);

    }

    /**
     * Mediante este metodo creo un bucle que vaya añadiendo a un grid layout los botones siguiendo las siguientes caracteristicas
     * Como el numero de columnas en las que van a salir y el tamaño y color que tienen los botones
     */
    public void agregarbotones() {
        glbotones = (GridLayout) findViewById(R.id.glbotones);
        glbotones.setUseDefaultMargins(true);
        glbotones.setColumnCount(6);


        for (int i = 0; i < abc.length; i++) {
            btnteclas = new Button(getApplicationContext());
            btnteclas.setText(abc[i].toLowerCase(Locale.ROOT));
            btnteclas.setBackgroundColor(getResources().getColor(R.color.purple_700));
            btnteclas.setTextColor(Color.WHITE);
            btnteclas.setWidth(60);
            btnteclas.setHeight(40);
            btnteclas.setMinimumHeight(60);
            btnteclas.setMinimumWidth(40);


            //Por ultimo hago que se puedan escuchar los botones y que se añadan a la vista
            btnteclas.setOnClickListener(this);
            glbotones.addView(btnteclas);
        }
    }

    //Metodo para crear los botones en mayuscula
    public void botonesmay(Button teclas) {
        glbotones = (GridLayout) findViewById(R.id.glbotones);
        glbotones.setUseDefaultMargins(true);
        glbotones.setColumnCount(6);


        for (int i = 0; i < abc.length; i++) {
            btnteclas = new Button(getApplicationContext());
            btnteclas.setText(abc[i]);
            btnteclas.setBackgroundColor(getResources().getColor(R.color.purple_700));
            btnteclas.setTextColor(Color.WHITE);
            btnteclas.setWidth(60);
            btnteclas.setHeight(40);
            btnteclas.setMinimumHeight(60);
            btnteclas.setMinimumWidth(40);


            //Por ultimo hago que se puedan escuchar los botones y que se añadan a la vista
            teclas = btnteclas;
            btnteclas.setOnClickListener(this);
            glbotones.addView(teclas);
        }
    }

    //Metodo para el boton de mayusculas
    public boolean tecladomay() {
        if (maysc) {
            glbotones.removeAllViews();
            agregarbotones();
        } else{
            glbotones.removeAllViews();
            botonesmay(btnteclas);
        }
        return maysc;
    }

    /**
     * En el metodo on click que recibe por parametro la vista vamos a realizar un switch para que haga diferentes acciones dependiendo del boton que pulse
     * Tambien seleccionamos los valores del spinner
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        //Cada vez que pulses el boton dentro de una cadena letra se añade el valor que se ha pulsado
        btnteclas = (Button) v;
        String letra = btnteclas.getText().toString();

        //Selecciono los datos del spinner y los parseo a entero para despues pasarlo como parametro a la hora de codificarlo
        String ccodigo = spDesplazar.getSelectedItem().toString();
        int cod = Integer.parseInt(ccodigo);
        int codigo = 0;

        switch (v.getId()) {
            case R.id.btnMayus:
                //Toast.makeText(getApplicationContext(), "Siguiente letra mayuscula", Toast.LENGTH_SHORT).show();
                //btn1.setBackgroundColor(Color.MAGENTA);
                tecladomay();
                break;
            case R.id.btnEncrip:
                //paso los parametros para codificar, el texto y el numero que se desplaza mediante el spinner
                codificar(salida, cod);
                break;
            case R.id.btnDesenc:
                //llamo al metodo descodificar y le añado el numero para que vuelva a la normalidad
                decodificar(salida, codigo);
                break;
            default:
                //saca por pantalla el texto, va añadiendo las letras cada vez que se pulse una tecla
                salida = salidatexto.getText() + letra;
                salidatexto.setText(salida);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adp, View view, int pos, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //metodo para codificar
    public void codificar(String salidacod, int cod) {
        String salidacodif = "";
        for (int i = 0; i < salidacod.length(); i++) {
            for (int j = 0; j < abcesar.length(); j++) {
                if (salidacod.charAt(i) == abcesar.charAt(j)) {
                    salidacodif += abcesar.charAt((j + cod) % abcesar.length());
                }
            }
        }
        salidacod = salidacodif;
        salidatexto.setText(salidacod);
    }

    //metodo para decodificar
    public void decodificar(String entradacod, int codigo) {
        String salidadecod = "";
        for (int i = 0; i < entradacod.length(); i++) {
            for (int j = 0; j < abcesar.length(); j++) {
                if (entradacod.charAt(i) == abcesar.charAt(j)) {
                    salidadecod += abcesar.charAt((j - codigo) % abcesar.length());
                }
            }
        }
        entradacod = salidadecod;
        salidatexto.setText(entradacod);
    }

    private boolean mantener = false;

    //metodo para que al mantener pulsado el boton cambie el color
    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.btnMayus) {
            Toast.makeText(getApplicationContext(), "Teclado en mayusculas", Toast.LENGTH_SHORT).show();
            btn1.setBackgroundColor(Color.GRAY);
            tecladomay();
        }
        return true;
    }
}