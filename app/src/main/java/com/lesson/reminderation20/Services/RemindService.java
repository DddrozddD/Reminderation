package com.lesson.reminderation20.Services;

import android.widget.AdapterView;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lesson.reminderation20.Models.AuthorisedUser;
import com.lesson.reminderation20.Models.Remind;

import java.util.ArrayList;
import java.util.List;

public class RemindService {
    DatabaseReference ref;
    public List<Remind> reminds ;
    public RemindService(){
        ref = FirebaseDatabase.getInstance().getReference("reminds");
        reminds = new ArrayList<Remind>();
    }

    public void AddRemind(String Title, String Date, String Text){



        String remindId = ref.push().getKey();
        Remind remind = new Remind(remindId,Text, Title , AuthorisedUser.Id , Date);
        ref.child(remindId).setValue(remind);
    }

    public void deleteRemind(Remind remind){
        DatabaseReference item = ref.orderByChild("Id").equalTo(remind.Id).getRef();

        item.removeValue();
    }

    public void GetRemindsFromFirebase(final GetCallback callback){

        ref.orderByChild("userId").equalTo(AuthorisedUser.Id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot snapshot:datasnapshot.getChildren()) {
                   Remind remind = snapshot.getValue(Remind.class);
                   reminds.add(remind);
                }
                callback.onSuccess(reminds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public interface GetCallback{
        void onSuccess(List<Remind> reminds);
        void onError(String Massage);
    }
}
