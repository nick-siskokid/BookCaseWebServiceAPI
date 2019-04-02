package edu.temple.bookcase;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookClickedInterface{

    boolean landscape;
    FragmentManager fm;
    BookListFragment bookListFragment;
    BookDetailFragment bookDetailFragment;
    ViewPagerFragment viewPagerFragment;
    ArrayList<String> bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = new ArrayList<String>();
        bookList.add("Cat in the Hat");
        bookList.add("Green Eggs and Ham");
        bookList.add("Fox in Socks");
        bookList.add("Horton Hears a Who");
        bookList.add("The Foot Book");
        bookList.add("The Lorax");
        bookList.add("One Fish Two Fish");

        //landscape tells us if we are in landscape mode or portrait mode
        landscape = findViewById(R.id.container2) == null;


        //retrieve JSON data from source
        getJSONData();

        bookDetailFragment = new BookDetailFragment();

        bookListFragment = bookListFragment.newInstance(bookList);

        fm = getSupportFragmentManager();



        if(!landscape){
            fm.beginTransaction()
                    .add(R.id.container1, bookListFragment)
                    .commit();

            fm.beginTransaction()
                    .replace(R.id.container2, bookDetailFragment)
                    .commit();
        }
        else{
            fm.beginTransaction()
                    .replace(R.id.container1, viewPagerFragment.newInstance(bookList))
                    .commit();
        }

    }

    public void bookClicked(String title){
        if(!landscape){
            bookDetailFragment.displayBook(title);
        }
    }

    private void getJSONData(){
        new Thread(){
            public void run() {
                try {
                    //URL object to parse data
                    URL url = new URL("https://kamorris.com/lab/audlib/booksearch.php");
                    //bufferedReader object declaration to read url stream into
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    //StringBuilder object declaration
                    StringBuilder builder = new StringBuilder();
                    String tmpString;
                    //feed stream line by line and append to the builder object
                    while((tmpString = reader.readLine()) != null){
                        builder.append(tmpString);
                    }
                    //Message object declaration
                    Message msg = Message.obtain();
                    //add the builder to the message
                    msg.obj = builder.toString();
                    //pass the message to the handler

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }//end public void run()
        }.start(); //end Thread

        Handler jsonHandler = new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg){
                return false;
            }
        });
    }//end getJSONData()
}
