package com.korniienko.scheduleplus.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.korniienko.scheduleplus.databinding.FragmentHomeBinding;
import com.korniienko.scheduleplus.ui.calendar.CalendarKD;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private CalendarStateAdapter calendarStateAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        calendarStateAdapter = new CalendarStateAdapter(getActivity());
        calendarStateAdapter.feedsList = new ArrayList<>();

        setFeedList();


        binding.viewPager.setAdapter(calendarStateAdapter);
        binding.viewPager.setCurrentItem(3);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onPageSelected(int position) {
                Date date = calendarStateAdapter.feedsList.get(position);
                if (position == calendarStateAdapter.feedsList.size() - 2) {

                    calendarStateAdapter
                            .feedsList
                            .add(new Date(date.getYear(), date.getMonth() + 2, 1));

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            calendarStateAdapter.notifyDataSetChanged();
                        }
                    }, 100);


                }
                if (position == 1) {
                    calendarStateAdapter
                            .feedsList
                            .add(0, new Date(date.getYear(), date.getMonth() - 2, 1));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            calendarStateAdapter.notifyDataSetChanged();
                            binding.viewPager.setCurrentItem(1);
                        }
                    }, 100);
                    date = calendarStateAdapter.feedsList.get(1);
                }
                if(position == 0) {
                    //binding.viewPager.setCurrentItem(1);
                    //calendarStateAdapter.notifyItemChanged(1);
                    date = calendarStateAdapter.feedsList.get(1);
                }

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(setTitle(date.getMonth(), date.getYear()));
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
//        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Calendar cal = myFragmentPageAdapter.feedsList.get(position).getCalendar();
//                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(setTitle(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));
//                if (position == myFragmentPageAdapter.feedsList.size() - 2) {
//                    myFragmentPageAdapter.feedsList.add(CalendarKD.newInstance(cal, 2));
//                    myFragmentPageAdapter.notifyDataSetChanged();
//
//                }
//                if (position == 1) {
//                    ArrayList<CalendarKD> temp = new ArrayList<>();
//                    temp.add(0, CalendarKD.newInstance(cal, -2));
//                    for(CalendarKD calendarKD : myFragmentPageAdapter.feedsList)
//                        temp.add(calendarKD);
//                    myFragmentPageAdapter.feedsList = temp;
//                    myFragmentPageAdapter.notifyDataSetChanged();
//                }
//                if(position == 0)
//                    binding.viewPager.setCurrentItem(1);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });




        return root;
    }

    void setFeedList()
    {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        setFeedListMonth(new Date(date.getYear(), date.getMonth() - 3, 1));
        setFeedListMonth(new Date(date.getYear(), date.getMonth() - 2, 1));
        setFeedListMonth(new Date(date.getYear(), date.getMonth() - 1, 1));
        setFeedListMonth(new Date(date.getYear(), date.getMonth(), 1));
        setFeedListMonth(new Date(date.getYear(), date.getMonth() + 1, 1));
        setFeedListMonth(new Date(date.getYear(), date.getMonth() + 2, 1));
        setFeedListMonth(new Date(date.getYear(), date.getMonth() + 3, 1));

    }

    private void setFeedListMonth(Date date)
    {
        calendarStateAdapter.feedsList.add(date);
        calendarStateAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Date date = calendarStateAdapter.feedsList.get(3);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(setTitle(date.getMonth(), date.getYear()));
    }

    String setTitle(int month, int year)
    {
        switch (month)
        {
            case 0: return "Январь " + String.valueOf(year);
            case 1: return "Февраль " + String.valueOf(year);
            case 2: return "Март " + String.valueOf(year);
            case 3: return "Апрель " + String.valueOf(year);
            case 4: return "Май " + String.valueOf(year);
            case 5: return "Июнь " + String.valueOf(year);
            case 6: return "Июль " + String.valueOf(year);
            case 7: return "Август " + String.valueOf(year);
            case 8: return "Сентябрь " + String.valueOf(year);
            case 9: return "Октябрь " + String.valueOf(year);
            case 10: return "Нобярь " + String.valueOf(year);
            case 11: return "Декабрь " + String.valueOf(year);
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}