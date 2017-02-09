package demo.example.com.jasonparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://api.androidhive.info/contacts/";
    public String ID = "id";
    public String NAME= "name";
    public String EMAIL = "email";
    public String CONTACTS= "contacts";

    ArrayList<HashMap<String, String>>  arrayList = new ArrayList<>();
    ListView listView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        new GetData().execute();
    }

    public class GetData extends AsyncTask< Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Loading Data");
            progressDialog.setMessage("Please Wait");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this,arrayList,R.layout.customview, new String[]{ID,NAME,EMAIL},
                                        new int[]{R.id.textView,R.id.textView2, R.id.textView3});

            listView.setAdapter(simpleAdapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HttpServicedata servicedata = new HttpServicedata();
            String result = servicedata.getHttpData(URL);
            Log.e("Result" , result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(CONTACTS);

                for (int i=0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String id = object.getString(ID);
                    String name = object.getString(NAME);
                    String email = object.getString(EMAIL);

                    HashMap<String, String> hm = new HashMap<>();
                    hm.put(ID,id);
                    hm.put(NAME,name);
                    hm.put(EMAIL,email);

                    arrayList.add(hm);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
