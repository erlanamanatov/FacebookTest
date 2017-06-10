package erkprog.com.fbstats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_posts);

        postsListview = (ListView) findViewById(R.id.listview);
        postList = new ArrayList<>();
        adapter = new PostAdapter(this, postList);
        postsListview.setAdapter(adapter);
        Log.d(TAG, "onCreated");


        info = (TextView) findViewById(R.id.textView);
        info.setMovementMethod(new ScrollingMovementMethod());
        button = (Button) findViewById(R.id.button2);

        Toast.makeText(this, Boolean.toString(isLoggedIn()), Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //getPostsId();
                getPosts2();
            }
        });

    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void getPostsId(){
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/41383354638/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
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
        parameters.putString("limit", "5");
        request.setParameters(parameters);
        request.executeAsync();
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
                                    Post postt = new Post();
                                    postt.setId(id);
                                    postt.setMessage(message);
                                    postt.setName(name);
                                    postt.setSharesCount(Integer.parseInt(shares));
                                    postt.setUrl(url);
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
        parameters.putString("limit", "3");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
