package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;



public class BookListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView listView;
    View rootView;
    Context parent;

    ArrayList<String> bookList;

    // TODO: Rename and change types of parameters
    private ArrayList<String> mParam1;


    private BookClickedInterface mListener;

    public BookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment BookListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(ArrayList<String> param1) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getStringArrayList(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_book_list, container, false);
        listView = rootView.findViewById(R.id.listOfBooksView);

        //initialize arrayList of book names
        /*bookList = new ArrayList<String>();
        bookList.add("Cat in the Hat");
        bookList.add("Green Eggs and Ham");
        bookList.add("Fox in Socks");
        bookList.add("Horton Hears a Who");
        bookList.add("The Foot Book");
        bookList.add("The Lorax");
        bookList.add("One Fish Two Fish");
        */
        //set array adapter to listView with bookList as the ArrayList to populate the ListView object
        listView.setAdapter(new ArrayAdapter<>(parent, android.R.layout.simple_list_item_1, mParam1));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                ((BookClickedInterface) parent).bookClicked(mParam1.get(position));
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.BookClicked(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = context;
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface BookClickedInterface {
        // TODO: Update argument type and name
        void bookClicked(String title);
    }
}
