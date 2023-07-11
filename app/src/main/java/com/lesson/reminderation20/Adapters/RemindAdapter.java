package com.lesson.reminderation20.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.lesson.reminderation20.Fragments.ListRemindsFragment;
import com.lesson.reminderation20.Models.AuthorisedUser;
import com.lesson.reminderation20.Models.Remind;
import com.lesson.reminderation20.R;
import com.lesson.reminderation20.Services.RemindService;

import java.util.List;

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.RemindViewHolder> {

    private List<Remind> reminds;
    private ListRemindsFragment.RemindClickListener remindClickListener;
    private Context context;

    public RemindAdapter(List<Remind> reminds, ListRemindsFragment.RemindClickListener remindClickListener, Context context) {
        this.reminds = reminds;
        this.remindClickListener = remindClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RemindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return  new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindViewHolder holder, int position) {
        final  Remind remind = reminds.get(position);

        holder.DescTextView.setText(remind.desc);
        holder.TitleTextView.setText(remind.title);
        holder.DateTextView.setText(remind.date);

    }

    @Override
    public int getItemCount() {
        return reminds.size();
    }
    public class RemindViewHolder extends  RecyclerView.ViewHolder  implements View.OnClickListener{

        private TextView TitleTextView;
        private TextView DescTextView;

        private TextView DateTextView;


        public RemindViewHolder(@NonNull View itemView) {
            super(itemView);

            DateTextView= itemView.findViewById(R.id.dateText);
            TitleTextView = itemView.findViewById(R.id.headerText);
            DescTextView = itemView.findViewById(R.id.descText);
            itemView.setOnClickListener(this::onClick);
        }
        @Override
        public void onClick(View view){
            int position = getAdapterPosition();
            Remind remind = reminds.get(position);

            LayoutInflater inflater = LayoutInflater.from(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view1 = inflater.inflate(R.layout.item_view, null);

            TextView title = (TextView)view1.findViewById(R.id.headerText);
            TextView desc = (TextView)view1.findViewById(R.id.descText);
            TextView date = (TextView) view1.findViewById(R.id.dateText);

            title.setText(remind.title);
            desc.setText(remind.desc);
            date.setText(remind.date);

            builder.setView(view1);
            builder.setTitle("REMOVE");
            builder.setMessage("Are you really want to remove '" + remind.title +"'?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    RemindService rs = new RemindService();
                    rs.deleteRemind(remind);
                    reminds.remove(remind);
                    notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("No", ((dialogInterface, i) -> {
                dialogInterface.dismiss();
            }));

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
