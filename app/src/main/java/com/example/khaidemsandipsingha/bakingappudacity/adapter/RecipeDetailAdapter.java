package com.example.khaidemsandipsingha.bakingappudacity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.khaidemsandipsingha.bakingappudacity.R;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Recipe;
import com.example.khaidemsandipsingha.bakingappudacity.RecipeIntiliaze.Step;
import java.util.List;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecyclerViewHolder> {

    private List<Step> listSteps;
    private  String recipeName;

    final private ListItemClickListener listItemOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> stepsOut,int clickedItemIndex,String recipeName);
    }

    public RecipeDetailAdapter(ListItemClickListener listener) {
        listItemOnClickListener =listener;
    }


    public void setMasterRecipeData(List<Recipe> recipesIn, Context context) {
        listSteps = recipesIn.get(0).getSteps();
        recipeName=recipesIn.get(0).getName();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.recipe_detail_cardview_items;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
       holder.textRecyclerView.setText(listSteps.get(position).getId()+". "+ listSteps.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {

        return listSteps !=null ? listSteps.size():0 ;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textRecyclerView;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            textRecyclerView = itemView.findViewById(R.id.shortDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemOnClickListener.onListItemClick(listSteps,clickedPosition,recipeName);
        }

    }
}
