package curatetechnologies.com.curate_consumer.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuSectionModel;
import curatetechnologies.com.curate_consumer.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate_consumer.presentation.ui.views.subclasses.RoundedCornerTransformation;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MenuSection extends StatelessSection {
    private SectionedRecyclerViewAdapter mSectionAdapter;
    private MenuSectionModel mMenuSection;
    private RecyclerViewClickListener mListener;
    private boolean expanded = false;

    public MenuSection(MenuSectionModel menuSectionModel,
                       SectionedRecyclerViewAdapter sectionAdapter,
                       RecyclerViewClickListener listener) {
        super(new SectionParameters.Builder(R.layout.fragment_menu_item_view_holder)
                .headerResourceId(R.layout.fragment_menu_section_header)
                .build());
        mMenuSection = menuSectionModel;
        mSectionAdapter = sectionAdapter;
        mListener = listener;
    }

    @Override
    public int getContentItemsTotal() {
        return expanded ? mMenuSection.getItems().size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view, mListener);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;

        // bind your view here
        itemHolder.bindData(mMenuSection.getItems().get(position));
    }
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        final HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
        headerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                headerViewHolder.ivHeaderExpanded.setImageResource(
                        expanded ?  R.drawable.ic_expanded_black_24dp : R.drawable.ic_expand_black_24dp
                );
                if (mMenuSection.getItems().get(0).getName() != null) {
                    mSectionAdapter.notifyDataSetChanged();
                }
            }
        });
        return headerViewHolder;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.bindData(mMenuSection);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        View mView;

        @BindView(R.id.menu_section_header_name)
        TextView tvSectionHeaderName;
        @BindView(R.id.menu_section_header_expanded_image)
        ImageView ivHeaderExpanded;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }

        public void bindData(MenuSectionModel menuSectionModel){
            tvSectionHeaderName.setText(menuSectionModel.getName());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.menu_item_view_holder_item_name)
        TextView tvItemName;
        @BindView(R.id.menu_item_view_holder_item_picture)
        ImageView ivItemImage;
        @BindView(R.id.menu_item_view_holder_item_description)
        TextView tvItemDescription;
        @BindView(R.id.menu_item_view_holder_item_price)
        TextView tvItemPrice;

        private RecyclerViewClickListener mListener;

        View view;

        public ItemViewHolder(View menuRow, RecyclerViewClickListener listener){
            super(menuRow);
            ButterKnife.bind(this, menuRow);
            this.view = menuRow;
            mListener = listener;
            menuRow.setOnClickListener(this);
        }

        public void bindData(ItemModel item){
            tvItemName.setText(item.getName());
            Glide.with(view)
                    .load(item.getImageURL())
                    .apply(bitmapTransform(new MultiTransformation(
                            new CenterCrop(), new RoundedCornerTransformation(45, 0,
                            RoundedCornerTransformation.CornerType.ALL))))
                    .into(ivItemImage);
            tvItemDescription.setText(item.getDescription());
            tvItemPrice.setText(item.getPrice());
        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods
    }
}
