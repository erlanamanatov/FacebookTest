package erkprog.com.fbstats;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    //private TextView info;
    private Button button;
    private static final String TAG = "myLogz";
    private ArrayList<Post> postList;
    private ListView postsListview;
    private PostAdapter adapter;
   // private String testPostId = "41383354638_10154720073274639";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_posts);

        postsListview = (ListView) findViewById(R.id.listview2);
        postList = new ArrayList<>();
        adapter = new PostAdapter(this, postList);
        postsListview.setAdapter(adapter);
        Log.d(TAG, "onCreated");

        postsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post) postsListview.getItemAtPosition(position);
                if (!post.getUrl().equals("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getUrl()));
                    startActivity(browserIntent);
                }
            }
        });


//        info = (TextView) findViewById(R.id.textView);
//        info.setMovementMethod(new ScrollingMovementMethod());
        button = (Button) findViewById(R.id.button2);

        Toast.makeText(this, Boolean.toString(isLoggedIn()), Toast.LENGTH_SHORT).show();

        getPostsInfo();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postList.clear();
                adapter.notifyDataSetChanged();
                getPostsInfo();
            }
        });

    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void getPostsInfo() {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/41383354638/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                       // info.append("\n");
                        Log.d(TAG, "getPostsId oncompleted");
                        try {
                            JSONObject jsonObject = response.getJSONObject();
                            Log.d(TAG, jsonObject.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            Log.d(TAG, dataArray.toString());
                            if (dataArray.length() > 0) {
                                ArrayList<String> posts = new ArrayList<String>();
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject post = dataArray.getJSONObject(i);
                                    String id = post.getString("id");
                                    String url = "";
                                    try {
                                        url = post.getString("link");
                                    } catch (JSONException e) {
                                        Log.d(TAG, "JSON exception url error");
                                    }
                                    String name = "";
                                    try {
                                        name = post.getString("name");
                                    } catch (JSONException e) {
                                        Log.d(TAG, "JSON exception name error");
                                    }
                                    String message = "";
                                    try {
                                        message = post.getString("message");
                                    } catch (JSONException e){
                                        Log.d(TAG, "JSON exception message error");
                                    }
                                    String shares = "0";
                                    try {
                                        shares = post.getJSONObject("shares").getString("count");
                                    } catch (JSONException e){
                                        Log.d(TAG, "JSON exception shares error");
                                    }
                                    Log.d(TAG, "post id: " + id + "\n"
                                            + "url: " + url + "\n"
                                            + "name: " + name + "\n"
                                            + "message: " + message + "\n"
                                            + "shares count: " + shares + "\n"
                                            + "_______________________________________");
                                    Post postt = new Post(name, message, Integer.parseInt(shares), id, url);
                                    postList.add(postt);
                                    adapter.notifyDataSetChanged();
                                    posts.add(id);
                                }
                                for (String id : posts) {
                                    getPostInsights(id);
                                }
                            } else {
                                Log.d(TAG, "data array is empty");
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Main2Activity.this, "JSON exception while getting posts", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "JSON exception while getting posts");
                        } catch (Exception e){
                            Toast.makeText(Main2Activity.this, "Getting posts error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,message,name,picture,shares,link");
        parameters.putString("limit", "20");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getPostInsights(String postId) {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + postId + "/insights/post_impressions,post_impressions_unique,post_reactions_by_type_total",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject();
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            if (dataArray.length() > 0) {
                                JSONObject js = dataArray.getJSONObject(0);
                                String fullId = js.getString("id");
                                String postId = getPostId(fullId);


                                for (int i = 0; i < postsListview.getCount(); i++){
                                    Post post = (Post) postsListview.getItemAtPosition(i);
                                    if (postId.equals(post.getId())){

                                        for (int k = 0; k < dataArray.length(); k++){
                                            JSONObject metric = dataArray.getJSONObject(k);
                                            String metricName = metric.getString("name");
                                            switch (metricName){
                                                case "post_impressions":
                                                    String value = metric.getJSONArray("values").getJSONObject(0).getString("value");
                                                    post.setReached_total(Integer.parseInt(value));
                                                    Log.d(TAG, "REACHED TOTAL : " + value);
                                                    break;
                                                case "post_impressions_unique":
                                                    String reached_unique = metric.getJSONArray("values").getJSONObject(0).getString("value");
                                                    post.setReached_unique(Integer.parseInt(reached_unique));
                                                    break;
                                                case "post_reactions_by_type_total":
                                                    JSONObject reactions = metric.getJSONArray("values").getJSONObject(0).getJSONObject("value");
                                                    post.setLikesCount(Integer.parseInt(reactions.getString("like")));
                                                    post.setLovesCount(Integer.parseInt(reactions.getString("love")));
                                                    post.setHahaCount(Integer.parseInt(reactions.getString("haha")));
                                                    post.setWowCount(Integer.parseInt(reactions.getString("wow")));
                                                    post.setSadCount(Integer.parseInt(reactions.getString("sorry")));
                                                    post.setAngryCount(Integer.parseInt(reactions.getString("anger")));
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }

                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                Log.d(TAG, "post data array is empty");
                            }

                        } catch (JSONException e) {
                            Log.d(TAG, "post parsing json error");
                        }
                    }
                });

        request.executeAsync();
    }

    private String getPostId(String fullId) {
        char[] origin = fullId.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < origin.length; i++) {
            if (origin[i] != '/') {
                stringBuilder.append(origin[i]);
            } else break;

        }
        return stringBuilder.toString();
    }
}
