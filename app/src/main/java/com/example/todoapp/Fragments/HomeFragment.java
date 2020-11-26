package com.example.todoapp.Fragments;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapters.TaskesAdapter;
import com.example.todoapp.AsyncTask.GetAsyncTask;
import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class HomeFragment extends BaseFragment {

    private static final String CHANNEL_ID = "";
    //RecyclerView
    RecyclerView rvTaskes;
    TaskesAdapter taskesAdapter;
    List<TaskesModel> taskesList = new ArrayList<>();
    TaskesModel reciveddate;
    Animation rotateOpenAnim, rotateCloseAnim, toBottomAnim, fromBottomAnim;
    FloatingActionButton addTaskButt;
    TextView task;
    private int notificationId;

//============================================================
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
//============================================================
    @Override
    public void initializeViews(View view) {
        rotateOpenAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
        rotateCloseAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);
        toBottomAnim = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);
        fromBottomAnim = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);
        addTaskButt = view.findViewById(R.id.fab_butt);
        task = view.findViewById(R.id.task_tv);
        //RecyclerView
        rvTaskes = view.findViewById(R.id.all_taskes_rv);

        getAllTaskes();
        checkForTaskes();
        setUpRecyclerView();
    }
//===========================================================================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
//==============================================================================
    @Override
    public void setListeners() {
        addTaskButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_addTaskeFragment);
            }
        });
    }
//=============================================================================
    private void setUpRecyclerView() {
        rvTaskes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        // rvTaskes.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        taskesAdapter = new TaskesAdapter(taskesList, new TaskesAdapter.OnViewClick() {
            @Override
            public void onSwitchClick(View view, int position) {

                //   NavController navController=Navigation.findNavController(R.id.homeFragment, ReminderBroadcast.class);
                //PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),0,navController,PendingIntent.FLAG_CANCEL_CURRENT);
                //AlarmManager alarmManager=get
            }
        }, new TaskesAdapter.onBoxClick() {
            @Override
            public void onCheackBokClicked() {
             //update the change that happen on the text and cheack box
                //this mean that the state must be saved fiest??
            }
        }, requireContext());
        rvTaskes.setAdapter(taskesAdapter);
    }

  //=======================================================================
    private void getAllTaskes() {
        taskesList.clear();
        try {
            taskesList.addAll(new GetAsyncTask(RoomFactory.getTaskessDb(requireContext()).getTaskesDao()).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //======================================================================
    private void checkForTaskes() {

        if (taskesList.isEmpty()) {
            rvTaskes.setVisibility(View.GONE);
            // notesIv.setVisibility(View.VISIBLE);
        } else {
            // notesIv.setVisibility(View.GONE);
            rvTaskes.setVisibility(View.VISIBLE);
        }
    }
    //========================================================================
    private void showNotification() {
        // Create an explicit intent for an Activity in your app
//      Intent intent = new Intent(this, );
//      PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_to_do_list__2_);
        builder.setWhen(System.currentTimeMillis());
//      builder.setContentTitle("Hi " + email.getText().toString());
//      builder.setContentText("are you sure you will sign in with this email?");
//      builder.addAction(R.drawable.image1, getString(R.string.Yes), pendingIntent);
//      builder.addAction(R.drawable.image1, getString(R.string.no), null);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_to_do_list));

//      builder.setStyle(new NotificationCompat.BigTextStyle()
//              .bigText("you will sign now with this email! " + email.getText().toString()))
//              .setPriority(NotificationCompat.PRIORITY_HIGH);
//      builder.setContentIntent(pendingIntent)
//              .setAutoCancel(false);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(notificationId, builder.build());

    }
    //=====================================================
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
        }
    }
}