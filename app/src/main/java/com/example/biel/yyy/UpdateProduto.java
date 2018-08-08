package com.example.biel.yyy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateProduto extends AppCompatActivity {

    String HttpURL = "http://negteam.16mb.com/public_html/UpdateProduto.php";
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText nome, fornecedor, quantidade;
    Button UpdateStudent;
    String IdProduto, NomeProduto, Categoria, Quantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_produto);

        nome = (EditText)findViewById(R.id.editNome);
        fornecedor = (EditText)findViewById(R.id.editCategoria);
        quantidade = (EditText)findViewById(R.id.editQuantidade);

        UpdateStudent = (Button)findViewById(R.id.UpdateButton);

        //recebe os nomes
        IdProduto = getIntent().getStringExtra("id");
        NomeProduto = getIntent().getStringExtra("nome");
        Categoria= getIntent().getStringExtra("categoria");
        Quantidade = getIntent().getStringExtra("quantidade");

        // Setts nome recebido na class
        nome.setText(NomeProduto);
        fornecedor.setText(Categoria);
        quantidade.setText(Quantidade);

        // funcao no botao update
        UpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // recebendo dados do campo de texto
                GetDataFromEditText();

                // Enviando dados p
                ProdutoRecordUpdate(IdProduto,NomeProduto,Categoria, Quantidade);

            }
        });


    }

    // Method to get existing data from EditText.
    public void GetDataFromEditText(){

       NomeProduto = nome.getText().toString();
        Categoria = fornecedor.getText().toString();
        Quantidade= quantidade.getText().toString();

    }

    // Metodo para atualizar os dados
    public void ProdutoRecordUpdate(final String ID, final String S_Nome, final String S_Categoria, final String S_Quantidade){

        class ProdutoRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UpdateProduto.this,"Carregando Dados",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(UpdateProduto.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);

                hashMap.put("nome",params[1]);

                hashMap.put("categoria",params[2]);

                hashMap.put("quantidade",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        ProdutoRecordUpdateClass produtoRecordUpdateClass = new ProdutoRecordUpdateClass();

        produtoRecordUpdateClass.execute(ID,S_Nome,S_Categoria,S_Quantidade);
    }
}
