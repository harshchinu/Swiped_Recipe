package com.example.swipedrecipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swipedrecipe.Models.Recipe;
import com.example.swipedrecipe.R;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<Recipe> {
    Context context;

    public arrayAdapter(Context context, int resourceId, List<Recipe> items) {
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe card_item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_view_recipe, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.recipeName);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView label = (TextView) convertView.findViewById(R.id.label);
        TextView cateogry = (TextView) convertView.findViewById(R.id.category);

        ImageView image = (ImageView) convertView.findViewById(R.id.ancImage);


        name.setText(card_item.getName());
        price.setText(card_item.getPrice());
        description.setText(card_item.getDescription());
        label.setText(card_item.getLabel());
        cateogry.setText(card_item.getCategory());
        switch (card_item.getImage()) {
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            case "":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            default:
                //Glide.clear(image);
                if(card_item.getImage()!=null) {
                    Glide.with(convertView.getContext()).load(card_item.getImage()).into(image);
                }
                break;
        }


        return convertView;
    }
}
