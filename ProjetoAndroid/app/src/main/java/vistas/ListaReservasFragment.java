package vistas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projeto.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import adaptadores.ListaReservaAdaptador;
import modelos.Reserva;
import modelos.SingletonGestorReservas;


public class ListaReservasFragment extends Fragment {
    private ListView lvlistaReservas;
    private ArrayList<Reserva> listaReservas;
    private FloatingActionButton fabadd;
    private SearchView searchView;
    private static  final int  REQCODE_EDIT =100, REQCODE_ADD = 200;

    public ListaReservasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);
      setHasOptionsMenu(true);
        listaReservas = SingletonGestorReservas.getInstance(getContext()).getLivrosBD();
        lvlistaReservas = view.findViewById(R.id.lvlistaReservas);
        lvlistaReservas.setAdapter((new ListaReservaAdaptador(getContext(), listaReservas)));

        lvlistaReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesReservaActivity.class);
                intent.putExtra("ID_RESERVA", (int) id);
                //startActivity(intent);
                startActivityForResult(intent,REQCODE_EDIT);

            }
        });
        fabadd = view.findViewById(R.id.fabadd);
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalhesReservaActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,REQCODE_ADD);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == REQCODE_EDIT || requestCode == REQCODE_ADD)
            {
                listaReservas = SingletonGestorReservas.getInstance(getContext()).getLivrosBD();
                lvlistaReservas.setAdapter(new ListaReservaAdaptador(getContext(),listaReservas));

                switch  (data.getIntExtra("OPERACAO", 0))
                {
                    case DetalhesReservaActivity.ADD:
                        Toast.makeText(getContext(),"Livro adicionado com sucesso",Toast.LENGTH_LONG);
                        break;
                    case DetalhesReservaActivity.EDIT:
                        Toast.makeText(getContext(),"Livro editado com sucesso",Toast.LENGTH_LONG);
                        break;
                    case DetalhesReservaActivity.DEL:
                        Toast.makeText(getContext(),"Livro eliminado com sucesso",Toast.LENGTH_LONG);
                        break;
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa, menu);
        MenuItem itemPesquisa = menu.findItem(R.id.ItemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Reserva> tempLista = new ArrayList<>();

                for(Reserva r: listaReservas)
                    if(r.getTitulo().toLowerCase().contains(s.toLowerCase()))
                        tempLista.add(l);
                lvlistaReservas.setAdapter(new ListaReservaAdaptador(getContext(),tempLista));
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        // fechar a serachView
        if(searchView != null)
            searchView.onActionViewCollapsed();
        super.onResume();
    }
}