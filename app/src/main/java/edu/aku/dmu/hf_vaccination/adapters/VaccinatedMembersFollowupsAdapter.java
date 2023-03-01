package edu.aku.dmu.hf_vaccination.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.models.VaccinesData;

public abstract class VaccinatedMembersFollowupsAdapter<T> extends RecyclerView.Adapter<VaccinatedMembersFollowupsAdapter<T>.ViewHolder> {
    private static final String TAG = "VaccinatedMembers2Adapter";
    private final Context mContext;
    private final List<T> member, backupItems = new ArrayList<>();
    private final int completeCount;
    private final boolean motherPresent = false;
    private final OnItemClickListener onItemClickListener;

    public VaccinatedMembersFollowupsAdapter(Context mContext, List<T> members, OnItemClickListener onItemClickListener) {
        this.member = members;
        backupItems.clear();
        backupItems.addAll(members);
        this.mContext = mContext;
        completeCount = 0;
        this.onItemClickListener = onItemClickListener;

    }

    // Add filter
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        if (query.equals("")) {
            // Show original list
            member.clear();
            member.addAll(backupItems);
            notifyDataSetChanged();
        } else {
            /*member.clear();
            for (VaccinesData vaccinesData : backupItems) {
                if (vaccinesData.getVBO2().toLowerCase().contains(query) || vaccinesData.getVB04A().toLowerCase().contains(query)) {
                    member.add(vaccinesData);
                }
            }
            notifyDataSetChanged();*/
        }
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
//        VaccinesData members = this.member.get(position);        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView fName = viewHolder.mName;
        TextView fAgeY = viewHolder.mAgeY;
        TextView fatherName = viewHolder.fatherName;
        TextView cardNo = viewHolder.cardNo;
        ImageView mainIcon = viewHolder.mainIcon;

       /* fName.setText(members.getVB04A());
        if (members.getVBO3().equals("1")) {
            fAgeY.setText(members.getVBO5Y() + " Y ");
        } else fAgeY.setText(members.getVBO5Y() + " Y " + members.getVBO5M() + " M ");
        fatherName.setText(members.getVB04());
        cardNo.setText(members.getVBO2());

        mainIcon.setImageResource(members.getVBO3().equals("2") ? (members.getVBO5A().equals("1") ? R.drawable.malebabyicon : R.drawable.femalebabyicon) : R.drawable.mwraicon);
        viewHolder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(member.get(position)));*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vaccinated_member_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        MainApp.memberCount = member.size();
        return member.size();
    }

    public interface OnItemClickListener {
        void onItemClick(VaccinesData member);
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName;
        private final TextView mAgeY;
        private final TextView fatherName;
        private final TextView cardNo;
        private final ImageView mainIcon;


        public ViewHolder(View v) {
            super(v);
            mName = v.findViewById(R.id.mName);
            mAgeY = v.findViewById(R.id.ageY);
            fatherName = v.findViewById(R.id.fName);
            cardNo = v.findViewById(R.id.cardNo);
            mainIcon = v.findViewById(R.id.mainIcon);
        }

        public TextView getTextView() {
            return mName;
        }
    }
}