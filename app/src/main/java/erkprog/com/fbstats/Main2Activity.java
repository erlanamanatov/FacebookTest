package erkprog.com.fbstats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private TextView info;
    private Button button;
    private static final String TAG = "myLogz";
    private ArrayList<Post> postList;
    private ListView postsListview;
    private PostAdapter adapter;
    private String testPostId = "41383354638_10154720073274639";

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
                if (testPostId.equals(post.getId())){
                    Toast.makeText(Main2Activity.this, "Equals", Toast.LENGTH_SHORT).show();
                    post.setMessage("testing message");
                    adapter.notifyDataSetChanged();
                }
            }
        });


        info = (TextView) findViewById(R.id.textView);
        info.setMovementMethod(new ScrollingMovementMethod());
        button = (Button) findViewById(R.id.button2);

        Toast.makeText(this, Boolean.toString(isLoggedIn()), Toast.LENGTH_SHORT).show();

        getPosts2();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //getPostsId();
                //getPosts2();
//                int count = postsListview.getCount();
//                for (int i = 0; i < count; i++){
//                    Post post = (Post) postsListview.getItemAtPosition(i);
//                    if (testPostId.equals(post.getId())){
//                        Toast.makeText(Main2Activity.this, "Equals", Toast.LENGTH_SHORT).show();
//                        post.setMessage("testing message");
//                        adapter.notifyDataSetChanged();
//                    }
//                }

                Log.d(TAG, "adapter count: " + adapter.getCount() + "\n"
                    + "list count: " + postsListview.getCount());
            }
        });

    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void getPosts2(){
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/41383354638/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        info.append("\n");
                        Log.d(TAG, "getPostsId oncompleted");
                        try{
                            JSONObject jsonObject = response.getJSONObject();
                            Log.d(TAG, jsonObject.toString());
                            JSONArray dataArray  = jsonObject.getJSONArray("data");
                            Log.d(TAG, dataArray.toString());
                            if (dataArray.length() > 0){
                                ArrayList<String> posts = new ArrayList<String>();
                                for (int i = 0; i < dataArray.length(); i++){
                                    JSONObject post = dataArray.getJSONObject(i);
                                    String id = post.getString("id");
                                    String url = "";
                                    try {
                                        url = post.getString("link");
                                    } catch (JSONException e){

                                    }
                                    String name = "";
                                    try {
                                        name = post.getString("name");
                                    } catch (JSONException e){

                                    }
                                    String message = post.getString("message");
                                    String shares = post.getJSONObject("shares").getString("count");
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
                                for (String id:posts){
                                    info.append(id + "\n");
                                }
                            } else {
                                Log.d(TAG, "data array is empty");
                            }
                        } catch (JSONException e){
                            Log.d(TAG, "error JSON");
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,message,name,picture,shares,link");
        parameters.putString("limit", "8");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
