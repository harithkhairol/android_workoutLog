package net.harithproperties.androidcrudstudy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pika on 9/7/2017.
 */

public class ListAdapterExercise extends ArrayAdapter<Exercise> {

    public ArrayList<Exercise> MainList;

    public ArrayList<Exercise> StudentListTemp;

    public ListAdapterExercise.SubjectDataFilter studentDataFilter;

    public ListAdapterExercise(Context context, int id, ArrayList<Exercise> studentArrayList) {

        super(context, id, studentArrayList);

        this.StudentListTemp = new ArrayList<Exercise>();

        this.StudentListTemp.addAll(studentArrayList);

        this.MainList = new ArrayList<Exercise>();

        this.MainList.addAll(studentArrayList);
    }

    @Override
    public Filter getFilter() {

        if (studentDataFilter == null) {

            studentDataFilter = new ListAdapterExercise.SubjectDataFilter();
        }
        return studentDataFilter;
    }


    public class ViewHolder {

        TextView txtnama;
        TextView txtjbt;
        TextView txtrep;
        TextView txttime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListAdapterExercise.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.searchlistcell, null);

            holder = new ListAdapterExercise.ViewHolder();

            holder.txtnama = (TextView) convertView.findViewById(R.id.txtnama);

            holder.txtjbt = (TextView) convertView.findViewById(R.id.txtjbt);

            holder.txtrep = (TextView) convertView.findViewById(R.id.txtrep);


            holder.txttime = (TextView) convertView.findViewById(R.id.txttime);


            convertView.setTag(holder);

        } else {

            holder = (ListAdapterExercise.ViewHolder) convertView.getTag();
        }

        Exercise exercise = StudentListTemp.get(position);

        holder.txtnama.setText(exercise.getExercise());

        holder.txtjbt.setText(exercise.getWeight());

        holder.txtrep.setText(exercise.getRep());


        holder.txttime.setText(exercise.getTime());



        return convertView;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {

                ArrayList<Exercise> arrayList1 = new ArrayList<Exercise>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    Exercise subject = MainList.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            StudentListTemp = (ArrayList<Exercise>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = StudentListTemp.size(); i < l; i++)
                add(StudentListTemp.get(i));

            notifyDataSetInvalidated();
        }


    }



}
