package br.com.fatec.servico.Adapters;

import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.fatec.servico.Dominio.Servico;
import br.com.fatec.servico.R;
import br.com.fatec.servico.Views.ListFragment;
import br.com.fatec.servico.Views.UpdateFragment;

public class ServicoAdapter extends RecyclerView.Adapter<ServicoAdapter.ServicoHolder> {

    private ArrayList<Servico> servicos;
    private Activity act;

    public ServicoAdapter(Activity act, ArrayList<Servico> servicos) {
        this.act = act;
        this.servicos = servicos;
    }

    @NonNull
    @Override
    public ServicoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ServicoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicoHolder servicoHolder, int position) {
        Servico servico = servicos.get(position);

        servicoHolder.txtTitulo.setText(servico.getTitulo());
        servicoHolder.txtDescricao.setText(servico.getDescricao());
        servicoHolder.txtNomePrestador.setText(servico.getNomePrestador());
        if (servico.getTags() != null)
            servicoHolder.txtTags.setText(TextUtils.join(", ", servico.getTags()));
    }

    @Override
    public int getItemCount() {
        return servicos != null ? servicos.size() : 0;
    }

    protected class ServicoHolder extends RecyclerView.ViewHolder {

        public TextView txtTitulo;
        public TextView txtDescricao;
        public TextView txtNomePrestador;
        public TextView txtTags;

        public ServicoHolder(View view) {
            super(view);
            txtTitulo = (TextView) view.findViewById(R.id.txtTitulo);
            txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);
            txtNomePrestador = (TextView) view.findViewById(R.id.txtNomePrestador);
            txtTags = (TextView) view.findViewById(R.id.txtTags);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Servico servico = servicos.get(position);

                    Bundle bundle = new Bundle();
                    bundle.putString("ServicoId", servico.getId());

                    UpdateFragment updateFragment = new UpdateFragment();
                    updateFragment.setArguments(bundle);

                    FragmentManager fragmentManager = act.getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.divFragmentPrincipal, updateFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }
}