package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.projeto.R;

import java.util.ArrayList;

import modelos.Reserva;

public class ListaReservaAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Reserva> reservas;

    public ListaReservaAdaptador(Context context, ArrayList<Reserva> reservas) {
        this.context = context;
        this.reservas = reservas;
    }

    @Override
    public int getCount() {
        return reservas.size();
    }

    @Override
    public Object getItem(int i) {
        return reservas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return reservas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.item_lista_reserva, null);

    //otimizacao - so faz 1 vez
        ViewHolderLista viewHolder=(ViewHolderLista) view.getTag();
        if(viewHolder==null) {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }
        viewHolder.update(reservas.get(i));
        return view;
    }



    private class ViewHolderLista {
        private EditText etPessoas, etAndar;
        private CheckBox cbsuite , cbmini_bar;

        public ViewHolderLista(View view) {
            etPessoas=view.findViewById(R.id.etPessoas);
            etAndar=view.findViewById(R.id.etAndar);
            cbmini_bar=view.findViewById(R.id.cb_Mini_bar);
            cbsuite=view.findViewById(R.id.cb_Suite);

        }

        public void update(Reserva reserva) {
            etPessoas.setText(reserva.getPessoas());
            etAndar.setText(reserva.getAndar());
            cbsuite.equals((reserva.getSuite()));
            cbmini_bar.equals((reserva.getMini_Bar()));
            
        }
    }

}


