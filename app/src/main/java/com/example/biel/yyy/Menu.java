package com.example.biel.yyy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class Menu extends AppCompatActivity {

    Button  ShowProduto,ShowCadastro,ShowContato,ShowSuport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ShowProduto = (Button) findViewById(R.id.buttonAlt);
        ShowCadastro = (Button) findViewById(R.id.buttonCad);
        ShowContato = (Button) findViewById(R.id.buttonFale);
        ShowSuport = (Button) findViewById(R.id.buttonSup);


        ShowContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(),Contato.class);

                startActivity(intent);

            }
        });

        ShowCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),CadastroProduto.class);

                startActivity(intent);


            }


        });


        ShowProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(),MostrarTodosProduto.class);

                startActivity(intent);

            }
        });
        ShowSuport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(),WebSuport.class);

                startActivity(intent);

            }
        });



    }
}










