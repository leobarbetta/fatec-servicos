package br.com.fatec.servico.Views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.UUID;

import br.com.fatec.servico.Dominio.Servico;
import br.com.fatec.servico.R;

public class CreateFragment extends Fragment {
    EditText edtTitulo, edtDescricao, edtNomePrestador, edtTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create, container, false);

        Button btnAdicionar = view.findViewById(R.id.btnCreateServico);
        edtTitulo = view.findViewById(R.id.edtTitulo);
        edtDescricao = view.findViewById(R.id.edtDescricao);
        edtNomePrestador = view.findViewById(R.id.edtNomePrestador);
        edtTags = view.findViewById(R.id.edtTags);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Servico servico = new Servico();
                servico.setId(UUID.randomUUID().toString());
                servico.setTitulo(edtTitulo.getText().toString());
                servico.setDescricao(edtDescricao.getText().toString());
                servico.setNomePrestador(edtNomePrestador.getText().toString());
                servico.setTags(Arrays.asList(edtTags.getText().toString().split(" ")));

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Servico").child(servico.getId()).setValue(servico);
                Toast.makeText(view.getContext(), "Serviço adicionado com sucesso!", Toast.LENGTH_LONG).show();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.divFragmentPrincipal, new ListFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
