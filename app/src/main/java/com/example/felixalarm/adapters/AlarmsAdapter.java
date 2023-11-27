package com.example.felixalarm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.felixalarm.R;
//import com.example.felixalarm.activities.Alarm;
import com.example.felixalarm.R;
import com.example.felixalarm.activities.AlarmActivity;
//import com.example.felixalarm.entities.Alarm;
import com.example.felixalarm.entities.Alarm;
import com.example.felixalarm.entities.Note;
import com.example.felixalarm.listeners.AlarmsListener;

import java.util.List;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.AlarmViewHolder> {

   private List<Alarm> alarmList;
   private AlarmsListener alarmsListener;
   private Context context;


    public AlarmsAdapter(List<Alarm> alarmList, Context context) {
        this.alarmList=alarmList;
        this.context=context;
    }


    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.name.setText(alarmList.get(position).getName());
        holder.time.setText(alarmList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }


    public  class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView time, name;
        private Switch active;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.alarmSettenTime);
            name = itemView.findViewById(R.id.alarmSettenName);
            active = itemView.findViewById(R.id.switchAlarm);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (alarmsListener!=null)
                alarmsListener.onAlarmClicked(v,getAdapterPosition());

        }
    }
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.uniwersal_alarm,parent,false);
        AlarmViewHolder holder=new AlarmViewHolder(view);
        return holder;
    }


//    @Override
//    public AlarmsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
//        View itemView=mInflater.inflate(R.layout.uniwersal_alarm,parent,false);
//        return new AlarmsViewHolder( itemView  );
//    }

//    @Override
//    public void onBindViewHolder(AlarmsViewHolder holder, final int position) {
//            if (alarms!=null){
//        holder.alarmSettenName.setText(alarms.get(position));
//        holder.alarmSettenTime.setText(times.get(position));
//        holder.switchAlarm.setChecked(alarms.getActive());
//        }
//        else{
//                    holder.alarmSettenName.setText("No Alarms");
//
//                }
////    }
//      void setAlarmClickListener(AlarmsListener listener) {
//            this.alarmClickListener=listener;
//        }
//
//    public List<Switch> getSwitches() {
//        return switches;
//    }
//
//        public TextView getTime() {
//            return time;
//        }
//
//        public TextView getName() {
//            return name;
//        }
//
//        public Switch getActive() {
//            return active;
//        }
//
//        public class AlarmsViewHolder  extends RecyclerView.ViewHolder{
//        TextView alarmSettenTime,alarmSettenName;
//
//        @SuppressLint("UseSwitchCompatOrMaterialCode")
//        Switch switchAlarm;
//        ConstraintLayout layoutAlarm;
//
//        public AlarmsViewHolder(@NonNull View itemView) {
//            super(itemView);
//            alarmSettenTime=itemView.findViewById(R.id.alarmSettenTime);
//            alarmSettenName=itemView.findViewById(R.id.alarmSettenName);
//            switchAlarm=itemView.findViewById(R.id.switchAlarm);
//        }
//    }
//
    }
