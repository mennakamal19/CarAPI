package com.example.carapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.carapi.fragments.CarFragment;
import com.example.carapi.fragments.FootballFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
{
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
     {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         viewPager = findViewById(R.id.viewpager);
         tabLayout = findViewById(R.id.tabs);

         fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
         {
             Fragment[] fragments = new Fragment[]
                     {
                             new CarFragment(),
                             new FootballFragment()
                     };

             String [] names = new String[]
                     {
                             "CARS",
                             "FOOTBALL"
                     };

             @Override
             public Fragment getItem(int position)
             {
                 return fragments[position];
             }

             @Override
             public int getCount()
             {
                 return fragments.length;
             }

             @Nullable
             @Override
             public CharSequence getPageTitle(int position)
             {
                 return names[position];
             }
         };

         viewPager.setAdapter(fragmentPagerAdapter);
         tabLayout.setupWithViewPager(viewPager);
     }
}
