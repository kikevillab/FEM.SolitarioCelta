package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import es.upm.miw.SolitarioCelta.model.Result;
import es.upm.miw.SolitarioCelta.model.ResultManager;
import es.upm.miw.SolitarioCelta.wrapper.ResultListAdapter;

public class ResultListActivity extends ListActivity {

    ResultListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Result> resultList = ResultManager.getResultManager(getApplicationContext()).getResults();
        if(resultList.size() == 0)
            mostrarMensaje("No se encontraron resultados");

        adapter = new ResultListAdapter(this, R.layout.result , resultList);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu_resultados, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences prefs =this.getSharedPreferences("juego", MODE_APPEND);

        switch (item.getItemId()) {
            case R.id.opcBorrarResultados:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("¿Borrar?");
                adb.setMessage("¿Quiere borrar todos los resultados?");
                adb.setNegativeButton("Cancelar", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ResultManager.getResultManager(getApplicationContext()).resetResults();
                        adapter.notifyDataSetChanged();
                        mostrarMensaje("Resultados borrados");
                    }});
                adb.show();
                break;
            case R.id.opcInicio:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje){
        Toast.makeText(
                this,
                mensaje,
                Toast.LENGTH_SHORT
        ).show();
    }



}
