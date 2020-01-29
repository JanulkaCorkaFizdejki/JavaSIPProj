package sipphone.model;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sipphone.datamodel.DataModelLogin;
import sipphone.settings.SettingsDataNetwork;

import javax.net.ssl.HttpsURLConnection;

public class NetworkDataManager {
    private String url = null;

    public NetworkDataManager(String url) {
        this.url = url;
    }

    public DataModelLogin logIn (String login, String password) {

        DataModelLogin dataModelLogin;

        try {
            URL url = new URL(SettingsDataNetwork.felgApiBaseURL_AUTH);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("login", login);
            params.put("password", password);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();

            String str = "";
            while (null != (str = bufferedReader.readLine())) {
                stringBuilder.append(str);
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            if (jsonObject.getInt("status") == 1) {
                dataModelLogin = new DataModelLogin(jsonObject.getInt("status"), jsonObject.getString("token"), jsonObject.getString("uid"), jsonObject.getString("lang"));
            } else {
                dataModelLogin = new DataModelLogin(jsonObject.getInt("status"), "0", "0", "0");
            }
            return dataModelLogin;
        } catch (MalformedURLException e) {
            System.out.println(e);

        } catch (IOException | JSONException e) {
            System.out.println(e);
        }

        dataModelLogin = new DataModelLogin(-1, "0", "0", "0");

        return dataModelLogin;
    }

    public void getData () {
        try {
            URL url = new URL(this.url);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("uid", "");
            connection.setRequestProperty("token", "");
            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            String str = "";

            while (null != (str = bufferedReader.readLine())) {
                System.out.println(str);
            }

        } catch (MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
 }
