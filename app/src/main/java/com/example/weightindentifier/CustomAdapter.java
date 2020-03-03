package com.example.weightindentifier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<String> arrayListID, arrayListNAME, arrayListPASSWARD, arrayListEMAIL;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity mainActivity, ArrayList<String> arrayListID, ArrayList<String> arrayListNAME, ArrayList<String> arrayListPASSWARD, ArrayList<String> arrayListEMAIL) {
        this.arrayListID = arrayListID;
        this.arrayListNAME = arrayListNAME;
        this.arrayListPASSWARD = arrayListPASSWARD;
        this.arrayListEMAIL = arrayListEMAIL;
        this.context = mainActivity;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayListNAME.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tvId, tvName, tvPassward, tvEmail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View view = inflater.inflate(R.layout.list_item, null);

        holder.tvId = view.findViewById(R.id.textViewID);
        holder.tvName = view.findViewById(R.id.textViewNAME);
        holder.tvPassward = view.findViewById(R.id.textViewPASSWARD);
        holder.tvEmail = view.findViewById(R.id.textViewEMAIL);

        holder.tvId.setText(arrayListID.get(position));
        holder.tvName.setText(arrayListNAME.get(position));
        holder.tvPassward.setText(arrayListPASSWARD.get(position));
        holder.tvEmail.setText(arrayListEMAIL.get(position));

        return view;
    }
}
