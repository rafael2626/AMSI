package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

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
        private TextView tvPessoas, tvAndar;
        private CheckBox cbsuite , cbmini_bar;

        public ViewHolderLista(View view) {
            tvPessoas=view.findViewById(R.id.etPessoas);
            tvAndar=view.findViewById(R.id.etAndar);
            cbmini_bar=view.findViewById(R.id.cb_Mini_bar);
            cbsuite=view.findViewById(R.id.cb_Suite);

        }

        public void update(Reserva reserva) {
            tvPessoas.setText(reserva.getTitulo());
            tvSerie.setText(livro.getSerie());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(livro.getAno()+"");
            imgCapa.setImageResource(livro.getCapa());
        }
    }

}


