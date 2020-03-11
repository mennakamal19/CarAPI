package com.example.carapi.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carapi.BookItem;
import com.example.carapi.R;
import com.example.carapi.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarFragment extends Fragment
{
    View view;
    String title,author,thum;
    List<BookItem>bookItem;
    BookAdapter bookAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.car_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.progress_circular);
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        bookItem = new ArrayList<>();
        new backgroud().execute("https://www.googleapis.com/books/v1/volumes?q=cars");
    }
    public class backgroud extends AsyncTask<String,Void,List<BookItem>>
    {
        List<BookItem>bk;
        
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected List<BookItem> doInBackground(String... strings)
        {
            try
            {
                bk= Utils.utils(strings[0]);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return bk;
        }

        @Override
        protected void onPostExecute(List<BookItem> bookItems)
        {
            progressBar.setVisibility(View.GONE);
            bookItem.clear();
            bookItem.addAll(bookItems);
            BookAdapter bookAdapter = new BookAdapter(bookItem);
            recyclerView.setAdapter(bookAdapter);
            super.onPostExecute(bookItems);
        }
    }
    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookVH>
    {
        List<BookItem>books;

        public BookAdapter(List<BookItem> books)
        {
            this.books = books;
        }

        @NonNull
        @Override
        public BookVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.book_item,parent,false);
            return new BookVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookVH holder, int position)
        {
            BookItem bookItem = books.get(position);
            holder.title.setText(bookItem.getTitle());
            holder.author.setText(bookItem.getAuthor());
            Picasso.get()
                    .load(bookItem.getThumbnail())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        }
        @Override
        public int getItemCount()
        {
            return books.size();
        }

        private class BookVH extends RecyclerView.ViewHolder
        {
            ImageView imageView;
            TextView title,author;
            public BookVH(@NonNull View itemView)
            {
                super(itemView);
                imageView = itemView.findViewById(R.id.book_image);
                title = itemView.findViewById(R.id.book_title_txt);
                author = itemView.findViewById(R.id.book_author_txt);
            }
        }
    }
}
