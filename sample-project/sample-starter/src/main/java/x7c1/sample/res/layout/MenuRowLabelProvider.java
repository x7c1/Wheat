
/**
 * This file is automatically generated by wheat-harvest.
 * Do not modify this file -- YOUR CHANGES WILL BE ERASED!
 */

package x7c1.sample.res.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import x7c1.wheat.ancient.resource.ViewHolderProvider;
import x7c1.wheat.ancient.resource.ViewHolderProviderFactory;
import x7c1.sample.R;
import x7c1.sample.glue.res.layout.MenuRowLabel;

public class MenuRowLabelProvider implements ViewHolderProvider<MenuRowLabel> {

    private final LayoutInflater inflater;

    public MenuRowLabelProvider(Context context){
        this.inflater = LayoutInflater.from(context);
    }

    public MenuRowLabelProvider(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public int layoutId(){
        return R.layout.menu_row__label;
    }

    @Override
    public MenuRowLabel inflateOn(ViewGroup parent){
        return inflate(parent, false);
    }

    @Override
    public MenuRowLabel inflate(ViewGroup parent, boolean attachToRoot){
        View view = inflater.inflate(R.layout.menu_row__label, parent, attachToRoot);
        return factory().createViewHolder(view);
    }

    @Override
    public MenuRowLabel inflate(){
        return inflate(null, false);
    }

    public static ViewHolderProviderFactory<MenuRowLabel> factory(){
        return new ViewHolderProviderFactory<MenuRowLabel>() {
            @Override
            public ViewHolderProvider<MenuRowLabel> create(LayoutInflater inflater){
                return new MenuRowLabelProvider(inflater);
            }
            @Override
            public ViewHolderProvider<MenuRowLabel> create(Context context){
                return new MenuRowLabelProvider(context);
            }
            @Override
            public MenuRowLabel createViewHolder(View view){
                return new MenuRowLabel(
                    view,
                    (TextView) view.findViewById(R.id.menu_row__label__text)
                );
            }
        };
    }
}