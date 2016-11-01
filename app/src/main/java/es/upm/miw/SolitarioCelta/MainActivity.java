package es.upm.miw.SolitarioCelta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import es.upm.miw.SolitarioCelta.model.Result;
import es.upm.miw.SolitarioCelta.model.ResultManager;

public class MainActivity extends Activity {

	JuegoCelta juego;
    private final String GRID_KEY = "GRID_KEY";
    private int fichasCambiadas;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        juego = new JuegoCelta();
        mostrarTablero();
        fichasCambiadas = 0;

    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        juego.jugar(i, j);
        fichasCambiadas++;

        mostrarTablero();
        if (juego.juegoTerminado()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            Result result = new Result(prefs.getString("nombreJugador", "jugador"), juego.numeroFichas());
            ResultManager.getResultManager(getApplicationContext()).saveResult(result);
            new AlertDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + Integer.toString(i) + Integer.toString(j);
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = (RadioButton) findViewById(idBoton);
                    button.setChecked(juego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }
    }

    /**
     * Guarda el estado del tablero (serializado)
     * @param outState Bundle para almacenar el estado del juego
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(GRID_KEY, juego.serializaTablero());
        super.onSaveInstanceState(outState);
    }

    /**
     * Recupera el estado del juego
     * @param savedInstanceState Bundle con el estado del juego almacenado
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString(GRID_KEY);
        juego.deserializaTablero(grid);
        mostrarTablero();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences prefs =this.getSharedPreferences("juego", MODE_APPEND);

        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                break;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                break;
            case R.id.opcReiniciarPartida:
                 new AlertDialog.Builder(this)
                        .setTitle("Reiniciar partida")
                        .setMessage("¿Deseas reiniciar la partida?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                juego.reiniciar();
                                fichasCambiadas = 0;
                                mostrarTablero();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
            case R.id.opcGuardarPartida:
                if(prefs.edit().putString(GRID_KEY, juego.serializaTablero()).commit())
                    mostrarMensaje(getString(R.string.txtPartidaGuardada));
                break;
            case R.id.opcRecuperarPartida:
                boolean recuperar = true;
                if(fichasCambiadas != 0){
                    new AlertDialog.Builder(this)
                            .setTitle("Recuperar partida")
                            .setMessage("¿Está seguro de que desea abandonar la partida actual y recuperar la partida anterior?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences prefs =getApplicationContext().getSharedPreferences("juego", MODE_APPEND);
                                    restoreGame(prefs);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }else {
                    restoreGame(prefs);
                }

                break;
            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, ResultListActivity.class));
                break;
            default:
                mostrarMensaje(getString(R.string.txtSinImplementar));
        }

    return true;
    }
    private void restoreGame(SharedPreferences prefs) {
        if (prefs.contains(GRID_KEY)) {
            String tablero = prefs.getString(GRID_KEY, null);
            if (tablero != null)
                juego.deserializaTablero(tablero);

            mostrarTablero();

        } else {
            mostrarMensaje(getString(R.string.txtSinPartida));
        }
    }

    private void mostrarMensaje(String mensaje){
        Toast.makeText(
                this,
                mensaje,
                Toast.LENGTH_SHORT
        ).show();
    }

}
