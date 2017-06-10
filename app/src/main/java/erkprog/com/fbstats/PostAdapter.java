package erkprog.com.fbstats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static erkprog.com.fbstats.R.layout.post;

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

    public String getPostId(int position){
        Post post = (Post) getItem(position);
        return post.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(post, parent, false);
        }
        TextView postName = (TextView) convertView.findViewById(R.id.post_name);
        TextView postMessage = (TextView) convertView.findViewById(R.id.post_message);
        TextView postReachedTotal = (TextView) convertView.findViewById(R.id.post_reached_total);
        TextView postReachedUnique = (TextView) convertView.findViewById(R.id.post_reached_unique);
        TextView postLikes = (TextView) convertView.findViewById(R.id.post_likes);
        TextView postLoves = (TextView) convertView.findViewById(R.id.post_loves);
        TextView postHaha = (TextView) convertView.findViewById(R.id.post_haha);
        TextView postWow = (TextView) convertView.findViewById(R.id.post_wow);
        TextView postSad = (TextView) convertView.findViewById(R.id.post_sad);
        TextView postAngry = (TextView) convertView.findViewById(R.id.post_angry);
        TextView postShares = (TextView) convertView.findViewById(R.id.post_shares);


        Post post = postsList.get(position);
        postName.setText(post.getName());
        postMessage.setText(post.getMessage());
        postReachedTotal.setText("Reached Total: " + Integer.toString(post.getReached_total()));
        postReachedUnique.setText("Reached Unique: " + Integer.toString(post.getReached_unique()));
        postLikes.setText(Integer.toString(post.getLikesCount()));
        postLoves.setText(Integer.toString(post.getLovesCount()));
        postHaha.setText(Integer.toString(post.getHahaCount()));
        postWow.setText(Integer.toString(post.getWowCount()));
        postSad.setText(Integer.toString(post.getSadCount()));
        postAngry.setText(Integer.toString(post.getAngryCount()));
        postShares.setText("Shares: " + Integer.toString(post.getSharesCount()));

        return convertView;
    }
}
