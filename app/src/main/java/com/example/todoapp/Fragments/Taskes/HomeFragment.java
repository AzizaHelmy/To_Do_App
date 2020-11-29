package com.example.todoapp.Fragments.Taskes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapters.TaskesAdapter;
import com.example.todoapp.AsyncTask.DeleteAsyncTask;
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


    //RecyclerView
    RecyclerView rvTaskes;
    TaskesAdapter taskesAdapter;
    List<TaskesModel> taskesList = new ArrayList<>();
    Animation rotateOpenAnim, rotateCloseAnim, toBottomAnim, fromBottomAnim;
    FloatingActionButton addTaskButt;
    TextView task;
    ImageView noTaskes;
    ImageButton deleteAll;
    //swapping
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    private TaskesModel taskesModel;
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
        deleteAll = view.findViewById(R.id.delet_all_ib);
        noTaskes=view.findViewById(R.id.empty_iv);

        //RecyclerView
        rvTaskes = view.findViewById(R.id.all_taskes_rv);


        getAllTaskes();
        checkForTaskes();
        setUpRecyclerView();
        //swapping
        setRvItemSwipe();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvTaskes);
    }

    //===========================*Handling Back Button*=============================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();//to close the app
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
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete All Tasks !");
                builder.setMessage("Are you sure you want to delete All Task ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskesList.clear();
                        new DeleteAsyncTask(RoomFactory.getTaskessDb(requireContext()).getTaskesDao()).execute();

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    //=============================================================================
    private void setUpRecyclerView() {
        //rvTaskes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        rvTaskes.setLayoutManager(mLayoutManager);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        // rvTaskes.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        taskesAdapter = new TaskesAdapter(taskesList, new TaskesAdapter.OnItemClick() {
            @Override
            public void onItemClicked(View view, int position) {
//

                TaskesModel clickedTask = taskesList.get(position);
//
                Bundle bundle = new Bundle();
                bundle.putSerializable("sTask", clickedTask);
                bundle.putSerializable("date", clickedTask);

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_taskDetailsFragment, bundle);
            }
        }, new TaskesAdapter.OnViewClick() {
            @Override
            public void onSwitchClick(View view, int position) {

                //   NavController navController=Navigation.findNavController(R.id.homeFragment, ReminderReceiver.class);
                //PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),0,navController,PendingIntent.FLAG_CANCEL_CURRENT);
                //AlarmManager alarmManager=get
            }
        }, new TaskesAdapter.onBoxClick() {
            @Override
            public void onCheackBokClicked(View view, int position) {
                //update the change that happen on the text and cheack box
                //this mean that the state must be saved first??
            }
        }, requireContext());
        rvTaskes.setAdapter(taskesAdapter);
    }

    //==============================================================================
    private void setRvItemSwipe() {
        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(requireContext(), "I'm moving", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                if (swipeDir == ItemTouchHelper.LEFT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Delete Task !");
                    builder.setMessage("Are you sure you want to delete this Task?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TaskesModel productModel = taskesList.get(position);
                            new DeleteAsyncTask(RoomFactory.getTaskessDb(requireContext()).getTaskesDao()).execute(productModel);
                            taskesList.remove(position);
                            taskesAdapter.notifyItemRemoved(position);
                            checkForTaskes();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            taskesAdapter.notifyItemChanged(position);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Edit Task !");
                    builder.setMessage("Are you sure you want to Edit this Task?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            TaskesModel clickedTask = taskesList.get(position);

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Task2Edit", clickedTask);
                            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_editTaskFragment, bundle);
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            taskesAdapter.notifyItemChanged(position);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                Drawable icon;
                ColorDrawable background;
                View itemView = viewHolder.itemView;
                int backgroundcornerOffset = 5;
                if (dX > 0) {
                    icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_edit);
                    background = new ColorDrawable(ContextCompat.getColor(getContext(), R.color.purple_500));
                } else {
                    icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_delete);
                    background = new ColorDrawable(Color.RED);
                }
                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + icon.getIntrinsicHeight();
                int iconBottom = iconTop + icon.getIntrinsicHeight();
                if (dX > 0) {//Swapping to the right
                    int iconLeft = itemView.getLeft() + iconMargin;
                    int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundcornerOffset, itemView.getBottom());
                } else if (dX < 0) {//swapping to the left
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundcornerOffset, itemView.getTop(),
                            itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }
                background.draw(c);
                icon.draw(c);

            }
        };
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
             noTaskes.setVisibility(View.VISIBLE);

        } else {
             noTaskes.setVisibility(View.GONE);
            rvTaskes.setVisibility(View.VISIBLE);
        }
    }
    //========================================================================

}