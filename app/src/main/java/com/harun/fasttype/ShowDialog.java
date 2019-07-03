package com.harun.fasttype;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harun.fasttype.Adapters.Language;
import com.harun.fasttype.Adapters.SelectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.langselectdialog,container);
        Language language = new Language(R.mipmap.tr,"Türkçe",1);
        Language language2 = new Language(R.mipmap.tr,"English",2);
        Language language3 = new Language(R.mipmap.tr,"English",2);
        List<Language> languageList = new ArrayList<>();
        languageList.add(language);
        languageList.add(language2);
        languageList.add(language3);
        RecyclerView recyclerView = rootview.findViewById(R.id.rv_langselect);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(),3));
        SelectionAdapter adapter = new SelectionAdapter(this.getActivity(),languageList);
        recyclerView.setAdapter(adapter);





        return rootview;
    }
}
