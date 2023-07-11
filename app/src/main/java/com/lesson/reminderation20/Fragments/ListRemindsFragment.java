package com.lesson.reminderation20.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.lesson.reminderation20.Adapters.RemindAdapter;
import com.lesson.reminderation20.Models.Remind;
import com.lesson.reminderation20.R;
import com.lesson.reminderation20.Services.RemindService;

import java.util.List;

public class ListRemindsFragment extends Fragment {

    private RemindAdapter remindAdapter;
    private RecyclerView recyclerView;

    private  RemindClickListener remindClickListener;

    public  interface  RemindClickListener{
        void onRemindCLick(Remind remind);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof  RemindClickListener){
            this.remindClickListener = (RemindClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        remindClickListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_reminds, container, false);

        recyclerView =view.findViewById(R.id.remindsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RemindService rs = new RemindService();
        rs.GetRemindsFromFirebase(new RemindService.GetCallback() {
            @Override
            public void onSuccess(List<Remind> reminds) {
                remindAdapter = new RemindAdapter(reminds, remindClickListener, getContext());

                recyclerView.setAdapter(remindAdapter);
            }

            @Override
            public void onError(String Messsage) {

            }

        });


        return  view;

    }

}
