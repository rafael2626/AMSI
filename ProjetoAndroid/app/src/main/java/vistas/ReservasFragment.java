package vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.projeto.R;

import java.util.ArrayList;

import modelos.Reserva;
import modelos.SingletonGestorReservas;


public class ReservasFragment extends Fragment {
    private EditText etPessoas, etAndar;
    private CheckBox cbSuite , cbMini_Bar;

    public ReservasFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_reservas, container, false);
        etPessoas =view.findViewById(R.id.etPessoas);
        etAndar = view.findViewById(R.id.etAndar);
        cbMini_Bar = view.findViewById(R.id.cb_Mini_bar);
        cbSuite = view.findViewById(R.id.cb_Suite);

        carregarLivro();
        return view;
    }

    public void carregarLivro() {
        ArrayList<Reserva> reservas = SingletonGestorReservas.getInstance(getContext()).get();
        if (reservas.size() > 0) {
            Reserva r = reservas.get(0);
            etPessoas.setText(r.getPessoas());
            etAndar.setText(r.getAndar());
            cbMini_Bar.setText(r.getMini_Bar());

        }
    }
}