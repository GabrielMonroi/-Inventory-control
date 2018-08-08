package com.example.biel.yyy;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

public class CadastroProduto extends AppCompatActivity {
    EditText nome, fornecedor, quantidade;
    Button RegistroProduto;
    String ProdutoNome, Categoria, Quantidade;
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
        String HttpURL = "http://negteam.16mb.com/public_html/CadastroProduto.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto2);

        nome = (EditText) findViewById(R.id.editNome);
        fornecedor = (EditText) findViewById(R.id.editCategoria);
        quantidade = (EditText) findViewById(R.id.editQuantidade);

        RegistroProduto = (Button) findViewById(R.id.buttonSubmit);


        RegistroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //verifica se todos os campos estao em branco
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    //verifica se todos os campos foram preenchidos

                    StudentRegistration(ProdutoNome, Categoria, Quantidade);

                } else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(CadastroProduto.this, "Por favor preencha todos os campos!.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void StudentRegistration(final String S_Name, final String S_Categoria, final String S_Quantidade){

        class StudentRegistrationClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(CadastroProduto.this,"Inserindo no Banco de Dados",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(CadastroProduto.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("nome",params[0]);

                hashMap.put("categoria",params[1]);

                hashMap.put("quantidade",params[2]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        StudentRegistrationClass studentRegistrationClass = new StudentRegistrationClass();

        studentRegistrationClass.execute(S_Name,S_Categoria,S_Quantidade);
    }


    public void CheckEditTextIsEmptyOrNot(){

        ProdutoNome = nome.getText().toString();
        Categoria = fornecedor.getText().toString();
        Quantidade = quantidade.getText().toString();

        if(TextUtils.isEmpty(ProdutoNome) || TextUtils.isEmpty(Categoria) || TextUtils.isEmpty(Quantidade))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
}
