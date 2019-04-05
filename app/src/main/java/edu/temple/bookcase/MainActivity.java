package edu.temple.bookcase;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

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

    ArrayList<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //landscape tells us if we are in landscape mode or portrait mode
        landscape = findViewById(R.id.container2) == null;

        books = new ArrayList<Book>();

        //retrieve JSON data from source
        getJSONData();


        bookDetailFragment = new BookDetailFragment();

        bookListFragment = bookListFragment.newInstance(books);

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
                    .replace(R.id.container1, viewPagerFragment.newInstance(books))
                    .commit();
        }

    }

    public void bookClicked(Book book){
        if(!landscape){
            bookDetailFragment.displayBook(book);
        }
    }

    private void getJSONData(){
        new Thread(){
            public void run() {
                try {
                    //URL object to parse data
                    URL url2 = new URL("https://kamorris.com/lab/audlib/booksearch.php");
                    URL url = new URL("https://kamorris.com/lab/audlib/booksearch.php");
                    //bufferedReader object declaration to read url stream into
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
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

                    //Testing purposes: This print statement confirms that we have read in the JSONarray to the message
                    System.out.println("This is what is contained in message: " + msg.obj);

                    //pass the message to the handler
                    jsonHandler.handleMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }//end public void run()

        }.start();//end Thread

    }//end getJSONData()
    Handler jsonHandler = new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg){
                JSONArray JSONbooks = null;
                try{
                    JSONbooks = new JSONArray((String) msg.obj);
                    if(books != null){
                        for(int iterator = 0; iterator < JSONbooks.length(); iterator++){
                            Log.d("The book object", JSONbooks.getJSONObject(iterator).toString());
                            books.add(new Book(JSONbooks.getJSONObject(iterator)));


                        }//end for loop
                    } else{
                        Log.d("The book is NULL", "not good");
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }
                if (landscape) {
                    fm.beginTransaction()
                            .replace(R.id.container1, viewPagerFragment.newInstance(books))
                            .commit();
                }
                else{
                    bookListFragment.updateBookData(books);
                }
                return false;
            }
        });
}
