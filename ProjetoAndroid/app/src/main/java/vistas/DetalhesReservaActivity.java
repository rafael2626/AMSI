package vistas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Listeners.ReservaListener;
import modelos.Reserva;
import modelos.SingletonGestorReservas;

public class DetalhesReservaActivity extends AppCompatActivity implements ReservaListener {

    public static final int ADD = 10, EDIT = 20, REMOVE = 30;
    public static final String DEFAULT_IMG_URL = "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";
    private EditText etPessoa, etAndar;
    private CheckBox cbsuite , cbminibar;
    private Reserva reserva;
    private FloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_reserva);

        fabSave = findViewById(R.id.fabSave);

        etAndar = findViewById(R.id.etAndar);
        etPessoa = findViewById(R.id.etPessoas);
        cbminibar = findViewById(R.id.cbMinibar);
        cbsuite = findViewById(R.id.cbSuite);


        int id = getIntent().getIntExtra("ID_LIVRO", 0);

        reserva = SingletonGestorReservas.getInstance(getApplicationContext()).getReserva(id);

        SingletonGestorReservas.getInstance(getApplicationContext()).setReservaListener(this);

        if (reserva != null) { ;
            etPessoa.setText(reserva.getPessoas()+ "");
            etAndar.setText(reserva.getAndar()+ "");
            cbsuite.setText(reserva.getSuite());
            cbminibar.equals(reserva.getMini_Bar());

            //imgCapa.setImageResource(livro.getCapa());
           /* Glide.with(getApplicationContext())
                    .load(livro.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);*/

            fabSave.setImageResource(R.drawable.ic_action_save_foreground);
        } else {
            setTitle("Adicionar Reserva");

            fabSave.setImageResource(R.drawable.ic_action_add_foreground);
        }

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reserva != null) {
                    //Edição
                    if (isReservaValid(reserva)) {
                        reserva.setPessoas(Integer.parseInt(etPessoa.getText().toString()));
                        reserva.setAndar(Integer.parseInt(etAndar.getText().toString()));
                        reserva.setSuite(Boolean.parseBoolean(cbsuite.getText().toString()));
                        reserva.setMini_bar(Boolean.parseBoolean(cbminibar.getText().toString()));
                        SingletonGestorReservas.getInstance(getApplicationContext()).editarLivroAPI(reserva, getApplicationContext());
                    }
                } else if (isReservaValid(reserva)) {
                    //Inserção
                  //  reserva = new Reserva(0, Integer.parseInt(etPessoa.getText().toString()), DEFAULT_IMG_URL, etPessoa.getText().toString(), cbsuite.(), cbminibar.getText().toString());

                    SingletonGestorReservas.getInstance(getApplicationContext()).adicionarReservaAPI(reserva, getApplicationContext());
                }
            }
        });
    }

    private boolean isReservaValid(Reserva r) {
        String pessoa = etPessoa.getText().toString();
        String andar = etAndar.getText().toString();
        Boolean suite = cbsuite.getText().equals(true);
        Boolean minibar = cbminibar.getText().equals(true);

        if (pessoa.length() < 5) {
            etPessoa.setError("Numero de pessoas Inválido!");
            return false;
        }
        if (andar.length() < 5) {
            etAndar.setError("Andar Inválido!");
            return false;
        }
        if (suite.equals(cbsuite)) {
            cbsuite.setError("Invalido");
            return false;
        }
        if (minibar.equals(cbminibar)) {
            cbminibar.setError("Invalido");
            return false;
        }

        return true;
    }

    //Injetar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (reserva != null) {
            getMenuInflater().inflate(R.menu.menu_detalhes_reserva, menu);

            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

    //Código do Click dos Itens do Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemRemover:
                dialogRemover();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Remover Reserva?")
                .setMessage("Pretende mesmo remover a Rserva?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorReservas.getInstance(getApplicationContext()).removerLivroAPI(reserva, getApplicationContext());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Não faz nada
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    @Override
    public void onRefreshDetalhes(int op) {

        Intent intent = new Intent();
        intent.putExtra("OPERACAO", op);
        setResult(Activity.RESULT_OK, intent);

        finish();

    }
}