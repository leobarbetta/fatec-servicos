package br.com.fatec.servico.Views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;

import br.com.fatec.servico.Adapters.ServicoAdapter;
import br.com.fatec.servico.Dominio.Servico;
import br.com.fatec.servico.R;

public class UpdateFragment extends Fragment {
    EditText edtTitulo, edtDescricao, edtNomePrestador, edtTags;
    Button btnUpdate;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_update, container, false);

        btnUpdate = view.findViewById(R.id.btnUpdateServico);
        edtTitulo = view.findViewById(R.id.edtTitulo);
        edtDescricao = view.findViewById(R.id.edtDescricao);
        edtNomePrestador = view.findViewById(R.id.edtNomePrestador);
        edtTags = view.findViewById(R.id.edtTags);


        Bundle bundle = this.getArguments();
        final String servicoId = bundle.getString("ServicoId");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query servicoQuery = databaseReference.child("Servico").child(servicoId);

        servicoQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Servico servico = dataSnapshot.getValue(Servico.class);

                edtTitulo.setText(servico.getTitulo());
                edtDescricao.setText(servico.getDescricao());
                edtNomePrestador.setText(servico.getNomePrestador());
                edtTags.setText(TextUtils.join(" ", servico.getTags()));

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> servicoToUpdate = new HashMap<>();
                        servicoToUpdate.put("id", servico.getId().toString());
                        servicoToUpdate.put("titulo", edtTitulo.getText().toString());
                        servicoToUpdate.put("descricao", edtDescricao.getText().toString());
                        servicoToUpdate.put("nomePrestador", edtNomePrestador.getText().toString());
                        if (servico.getTags() != null)
                            servicoToUpdate.put("tags", Arrays.asList(edtTags.getText().toString().split(" ")));

                        databaseReference.child("Servico").child(servicoId).updateChildren(servicoToUpdate);

                        Toast.makeText(getActivity().getApplicationContext(), "Item alterado com sucesso", Toast.LENGTH_LONG);
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.divFragmentPrincipal, new ListFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
