package erkprog.com.fbstats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by erlan on 10.06.2017.
 */

public class PostAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Post> postsList;

    public PostAdapter(Context context, ArrayList<Post> postsList){
        this.context = context;
        this.postsList = postsList;
    }


    @Override
    public int getCount() {
        return postsList.size();
    }

    @Override
    public Object getItem(int position) {
        return postsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.group_list_item, parent, false);
        }

        return convertView;
    }
}
