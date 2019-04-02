package edu.temple.bookcase;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {
    //private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM1 = "b";
    ArrayList<String> al;
    ViewPager viewPager;
    View rootView;


    //private ArrayList<String> books;
    private ArrayList<Book> books;



    public ViewPagerFragment() {
        // Required empty public constructor
    }





    /*public static ViewPagerFragment newInstance(ArrayList<String> param1) {
                    ViewPagerFragment fragment = new ViewPagerFragment();
                    Bundle args = new Bundle();
                    args.putStringArrayList(ARG_PARAM1, param1);
                    fragment.setArguments(args);
                    return fragment;
                }
                */
    public static ViewPagerFragment newInstance(ArrayList<Book> b){
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, b);
        fragment.setArguments(args);
        return fragment;
    }
    //TODO In the onCreate method, need to get parcalable array list from the bundle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books = getArguments().getParcelableArrayList(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new fspa(getChildFragmentManager()));
        return rootView;
    }

    class fspa extends FragmentStatePagerAdapter {
        public fspa(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return BookDetailFragment.newInstance(books.get(i));
        }

        @Override
        public int getCount() {
            return books.size();
        }
    }
}

