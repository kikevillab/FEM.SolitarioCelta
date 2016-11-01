package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import es.upm.miw.SolitarioCelta.model.Result;
import es.upm.miw.SolitarioCelta.model.ResultManager;

/**
 * Created by Enrique on 30/10/2016.
 */
public class SaveDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.player_save, null));

        builder
                .setTitle(R.string.txtDialogoGuardarTitulo)
                .setMessage(R.string.txtDialogoGuardarPregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoFinalAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String playerName = ((EditText) main.findViewById(R.id.username)).getText().toString();
                                Result result = new Result(playerName, main.juego.numeroFichas());
                                ResultManager.getResultManager(getActivity().getApplicationContext()).saveResult(result);
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoFinalNegativo),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SaveDialogFragment.this.getDialog().cancel();
                            }
                        }
                );

        return builder.create();
    }
}
