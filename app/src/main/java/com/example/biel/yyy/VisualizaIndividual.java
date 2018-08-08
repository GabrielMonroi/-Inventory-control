package com.example.biel.yyy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class VisualizaIndividual extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;


    String HttpURL = "http://negteam.16mb.com/public_html/FiltroProduto.php";


    String HttpUrlDeleteRecord = "http://negteam.16mb.com/public_html/DeleteProduto.php";

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView NOME,CATEGORIA,QUANTIDADE;
    String NomeProduto, Categoria, Quantidade;
    Button UpdateButton, DeleteButton;
    String TempItem;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_individual);

        NOME = (TextView)findViewById(R.id.textNome);
        CATEGORIA = (TextView)findViewById(R.id.textCategoria);
        QUANTIDADE = (TextView)findViewById(R.id.textQuantidade);


        UpdateButton = (Button)findViewById(R.id.buttonUpdate);
        DeleteButton = (Button)findViewById(R.id.buttonDelete);

        //Recebendo dados na tela.
        TempItem = getIntent().getStringExtra("ListViewValue");

        //Chamando dados do produto selecionado
        HttpWebCall(TempItem);


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(VisualizaIndividual.this,UpdateProduto.class);

                // Enviando dados para serem atualizados
                intent.putExtra("id", TempItem);
                intent.putExtra("nome", NomeProduto);
                intent.putExtra("categoria", Categoria);
                intent.putExtra("quantidade", Quantidade);

                startActivity(intent);

                // Finalizando tela e abrindo nova activity
                finish();

            }
        });

        // Add Click listener para o botao de deletar
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Chamando metodo deletear
                ProdutoDelete(TempItem);

            }
        });

    }

    // Method to Delete Student Record
    public void ProdutoDelete(final String StudentID) {

        class StudentDeleteClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(VisualizaIndividual.this, "Carregando Dados", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(VisualizaIndividual.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                // Enviando material id.
                hashMap.put("id", params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpUrlDeleteRecord);

                return finalResult;
            }
        }

        StudentDeleteClass studentDeleteClass = new StudentDeleteClass();

        studentDeleteClass.execute(StudentID);
    }


    //Method to show current record Current Selected Record
    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(VisualizaIndividual.this,"Carregando Dados",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();


                FinalJSonObject = httpResponseMsg ;


                new GetHttpResponse(VisualizaIndividual.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("id",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }



    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            // Armazzenando dados na variavel
                            NomeProduto = jsonObject.getString("nome").toString() ;
                            Categoria = jsonObject.getString("categoria").toString() ;
                            Quantidade = jsonObject.getString("quantidade").toString() ;

                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {


            NOME.setText(NomeProduto);
            CATEGORIA.setText(Categoria);
            QUANTIDADE.setText(Quantidade);

        }
    }

}

