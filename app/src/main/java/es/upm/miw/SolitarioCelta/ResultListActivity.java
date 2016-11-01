package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import es.upm.miw.SolitarioCelta.model.ResultManager;
import es.upm.miw.SolitarioCelta.wrapper.ResultAdapter;

public class ResultListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ResultAdapter adapter = new ResultAdapter(this, R.layout.result , ResultManager.getResultManager(getApplicationContext()).getResults());
        setListAdapter(adapter);
    }



}
