package com.example.biel.yyy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MostrarTodosProduto extends AppCompatActivity {

    ListView ProdutoListView;
    ProgressBar progressBar;
    String HttpUrl = "http://negteam.16mb.com/public_html/TodosRegistros.php";
    List<String> IdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mostrar_todos);

        ProdutoListView = (ListView)findViewById(R.id.listview1);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        new GetHttpResponse(MostrarTodosProduto.this).execute();

        //Adicionando ListView
        ProdutoListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(MostrarTodosProduto.this,VisualizaIndividual.class);


                intent.putExtra("ListViewValue", IdList.get(position).toString());

                startActivity(intent);


                finish();

            }
        });
    }

    // JSON parse class comecando aqui
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String JSonResult;

        List<Produto> produtoList;

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
            // Passando HTTP URL para HttpServicesClass Class.
            HttpServicesClass httpServicesClass = new HttpServicesClass(HttpUrl);
            try
            {
                httpServicesClass.ExecutePostRequest();

                if(httpServicesClass.getResponseCode() == 200)
                {
                    JSonResult = httpServicesClass.getResponse();
                    Log.i("dados", JSonResult);

                    if(JSonResult != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(JSonResult);

                            JSONObject jsonObject;

                            Produto produto;

                            produtoList = new ArrayList<Produto>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                produto = new Produto();

                                jsonObject = jsonArray.getJSONObject(i);

                                // Adicionando Id para IdList Array.
                                IdList.add(jsonObject.getString("id").toString());

                                //Adicionando o nome do Produto.
                                produto.ProdutoNome = jsonObject.getString("nome").toString();

                                produtoList.add(produto);

                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
            progressBar.setVisibility(View.GONE);

            ProdutoListView.setVisibility(View.VISIBLE);

            ListAdapterClass adapter = new ListAdapterClass(produtoList, context);

            ProdutoListView.setAdapter(adapter);

        }
    }
}
