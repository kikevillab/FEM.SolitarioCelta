package es.upm.miw.SolitarioCelta.wrapper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.SolitarioCelta.R;
import es.upm.miw.SolitarioCelta.model.Result;

/**
 * Created by Enrique on 30/10/2016.
 */

public class ResultListAdapter extends ArrayAdapter {

    Context context;
    int resourceId;
    List<Result> data;

    public ResultListAdapter(Context context, int resource, List<Result> objects) {
        super(context, resource, objects.toArray());
        this.context = context;
        this.resourceId = resource;
        this.data = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout vista;

        if(convertView != null){
            vista = (LinearLayout) convertView;
        } else {
            LayoutInflater linf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = (LinearLayout) linf.inflate(resourceId, parent, false);

        }

        if (position % 2 == 1) {
            vista.setBackgroundColor(Color.parseColor("#EAEAEA"));
        }

        ((TextView) vista.findViewById(R.id.playerName)).setText(data.get(position).getPlayerName());
        ((TextView) vista.findViewById(R.id.dateTime)).setText("Fecha: "+data.get(position).getDateTime());
        ((TextView) vista.findViewById(R.id.scorePlayer)).setText(String.valueOf(data.get(position).getScore())+" fichas");

        return vista;
    }
}
