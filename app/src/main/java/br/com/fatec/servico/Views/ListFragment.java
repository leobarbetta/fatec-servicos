package br.com.fatec.servico.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.fatec.servico.Adapters.ServicoAdapter;
import br.com.fatec.servico.Dominio.Servico;
import br.com.fatec.servico.R;

public class ListFragment extends Fragment {

    RecyclerView recyclerViewServicos;
    private ServicoAdapter servicoAdapter;
    ArrayList<Servico> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerViewServicos = view.findViewById(R.id.recyclerViewServicos);
        GridLayoutManager layoutManagerGrid = new GridLayoutManager(view.getContext(), 2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewServicos.setLayoutManager(layoutManager);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Query servicoQuery = databaseReference.child("Servico");

        servicoQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Servico servico = child.getValue(Servico.class);
                    if (servico != null) {
                        list.add(servico);
                    }
                }

                servicoAdapter = new ServicoAdapter(getActivity(), list);
                recyclerViewServicos.setAdapter(servicoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
