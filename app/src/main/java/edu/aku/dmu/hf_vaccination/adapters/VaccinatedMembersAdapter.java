package edu.aku.dmu.hf_vaccination.adapters;

import static edu.aku.dmu.hf_vaccination.core.MainApp.formVBList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.aku.dmu.hf_vaccination.R;
import edu.aku.dmu.hf_vaccination.core.MainApp;
import edu.aku.dmu.hf_vaccination.models.FormVB;


public class VaccinatedMembersAdapter extends RecyclerView.Adapter<VaccinatedMembersAdapter.ViewHolder> {
    private static final String TAG = "VaccinatedMembersAdapter";
    private final Context mContext;
    //private final List<FormVB> member;
    private final List<FormVB> member;
    private final int completeCount;
    private final boolean motherPresent = false;
    private final OnItemClickListener onItemClickListener;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param members List<MembersModel> containing the data to populate views to be used by RecyclerView.
     */
   /* public VaccinatedMembersAdapter(Context mContext, List<FormVB> members) {
        this.member = members;
        this.mContext = mContext;
        completeCount = 0;

    }*/
    public VaccinatedMembersAdapter(Context mContext, List<FormVB> members, OnItemClickListener onItemClickListener) {
        this.member = members;
        this.mContext = mContext;
        completeCount = 0;
        this.onItemClickListener = onItemClickListener;

        Collections.sort(formVBList, new Comparator<FormVB>(){
            @Override
            public int compare(FormVB o1, FormVB o2) {
                return o1.getVb04a().compareTo(o2.getVb04a());
            }
        });

    }


    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        FormVB members = this.member.get(position);        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        TextView fName = viewHolder.mName;
        TextView fAgeY = viewHolder.mAgeY;
        TextView fatherName = viewHolder.fatherName;
        TextView cardNo = viewHolder.cardNo;
        ImageView mainIcon = viewHolder.mainIcon;

        fName.setText(members.getVb04a());
        if (members.getVb03().equals("1")) {
            fAgeY.setText(members.getVb05y() + " Y ");
        } else fAgeY.setText(members.getVb05y() + " Y " + members.getVb05m() + " M ");
        fatherName.setText(members.getVb04());
        cardNo.setText(members.getVb02());


        mainIcon.setImageResource(members.getVb03().equals("2") ? (members.getVb05a().equals("1") ? R.drawable.malebabyicon : R.drawable.femalebabyicon) : R.drawable.mwraicon);


        viewHolder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(member.get(position)));
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
        void onItemClick(FormVB member);
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
