package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pennyjoy.R;

import java.util.ArrayList;
import java.util.List;

import Models.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;
    private int resource;
    private ArrayList<Category> categories;

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Category> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.categories = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= LayoutInflater.from(this.context).inflate(resource,null);

        TextView lblCategoryName = v.findViewById(R.id.itemCategory);

        Category category = this.categories.get(position);

       lblCategoryName.setText(category.getName());

        v.setTag(category.getId());
        return v;
    }
}
