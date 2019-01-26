package com.example.victoriasheng.fotogram;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfiloFragment extends Fragment {


    public ProfiloFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDataForPage();
        return inflater.inflate(R.layout.fragment_profilo, container, false);
    }

    public void  getDataForPage(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://ewserver.di.unimi.it/mobicomp/fotogram/profile";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Holo_Dialog_NoActionBar);
                        builder.setTitle("test")
                                .setMessage(response)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();*/
                        try {
                            String imgb64 = "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AAAAAAABADUAAAABAC0AAAAGAAAAAAABOEJJTQP4AAAAAABwAAD/////////////////////////////A+gAAAAA/////////////////////////////wPoAAAAAP////////////////////////////8D6AAAAAD/////////////////////////////A+gAADhCSU0EAAAAAAAAAgAAOEJJTQQCAAAAAAACAAA4QklNBDAAAAAAAAEBADhCSU0ELQAAAAAABgABAAAAAjhCSU0ECAAAAAAAEAAAAAEAAAJAAAACQAAAAAA4QklNBB4AAAAAAAQAAAAAOEJJTQQaAAAAAANRAAAABgAAAAAAAAAAAAAAKwAAACsAAAAOAFMAZQBuAHoAYQAgAHQAaQB0AG8AbABvAC0AMQAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAKwAAACsAAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAQAAAAAAAG51bGwAAAACAAAABmJvdW5kc09iamMAAAABAAAAAAAAUmN0MQAAAAQAAAAAVG9wIGxvbmcAAAAAAAAAAExlZnRsb25nAAAAAAAAAABCdG9tbG9uZwAAACsAAAAAUmdodGxvbmcAAAArAAAABnNsaWNlc1ZsTHMAAAABT2JqYwAAAAEAAAAAAAVzbGljZQAAABIAAAAHc2xpY2VJRGxvbmcAAAAAAAAAB2dyb3VwSURsb25nAAAAAAAAAAZvcmlnaW5lbnVtAAAADEVTbGljZU9yaWdpbgAAAA1hdXRvR2VuZXJhdGVkAAAAAFR5cGVlbnVtAAAACkVTbGljZVR5cGUAAAAASW1nIAAAAAZib3VuZHNPYmpjAAAAAQAAAAAAAFJjdDEAAAAEAAAAAFRvcCBsb25nAAAAAAAAAABMZWZ0bG9uZwAAAAAAAAAAQnRvbWxvbmcAAAArAAAAAFJnaHRsb25nAAAAKwAAAAN1cmxURVhUAAAAAQAAAAAAAG51bGxURVhUAAAAAQAAAAAAAE1zZ2VURVhUAAAAAQAAAAAABmFsdFRhZ1RFWFQAAAABAAAAAAAOY2VsbFRleHRJc0hUTUxib29sAQAAAAhjZWxsVGV4dFRFWFQAAAABAAAAAAAJaG9yekFsaWduZW51bQAA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cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDIgNzkuMTYwOTI0LCAyMDE3LzA3LzEzLTAxOjA2OjM5ICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlRXZlbnQjIiB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOCAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDE5LTAxLTEyVDExOjA0OjI4KzAxOjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDE5LTAxLTEyVDExOjA0OjI4KzAxOjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAxOS0wMS0xMlQxMTowNDoyOCswMTowMCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoxNzk3N2ZlZS1hZGJhLTY0NDUtODg1Ni0wYzEzMzQ1NzYxMTUiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDpjMmZlNWVmZS00ZTljLTdhNGUtYjEyYi04YTUyODQzNjVmNjAiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpiN2VkZmY4ZS02MjE5LWFlNDUtOTNlZS1mNzFkYzA3NmIxMjMiIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIGRjOmZvcm1hdD0iaW1hZ2UvanBlZyI+IDx4bXBNTTpIaXN0b3J5PiA8cmRmOlNlcT4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNyZWF0ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6YjdlZGZmOGUtNjIxOS1hZTQ1LTkzZWUtZjcxZGMwNzZiMTIzIiBzdEV2dDp3aGVuPSIyMDE5LTAxLTEyVDExOjA0OjI4KzAxOjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOCAoV2luZG93cykiLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjE3OTc3ZmVlLWFkYmEtNjQ0NS04ODU2LTBjMTMzNDU3NjExNSIgc3RFdnQ6d2hlbj0iMjAxOS0wMS0xMlQxMTowNDoyOCswMTowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTggKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDwvcmRmOlNlcT4gPC94bXBNTTpIaXN0b3J5PiA8cGhvdG9zaG9wOkRvY3VtZW50QW5jZXN0b3JzPiA8cmRmOkJhZz4gPHJkZjpsaT5FQjhDQjcxRkY5M0QxMzhEOTFBOUU4RjI1RjZGRUZFMjwvcmRmOmxpPiA8L3JkZjpCYWc+IDwvcGhvdG9zaG9wOkRvY3VtZW50QW5jZXN0b3JzPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8P3hwYWNrZXQgZW5kPSJ3Ij8+/+4ADkFkb2JlAGSAAAAAAf/bAIQAICEhMyQzUTAwUUIvLy9CJxwcHBwnIhcXFxcXIhEMDAwMDAwRDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAEiMzM0JjQiGBgiFA4ODhQUDg4ODhQRDAwMDAwREQwMDAwMDBEMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwM/8AAEQgAKwArAwEiAAIRAQMRAf/dAAQAA//EARsAAAMBAQEBAQEBAQEAAAAAAAEAAgMEBQYHCAkKCwEBAQEBAQEBAQEBAQEAAAAAAAECAwQFBgcICQoLEAACAgEDAgMEBwYDAwYCATUBAAIRAyESMQRBUSITYXEygZGxQqEF0cEU8FIjcjNi4YLxQzSSorIV0lMkc8JjBoOT4vKjRFRkJTVFFiZ0NlVls4TD03Xj80aUpIW0lcTU5PSltcXV5fVWZnaGlqa2xtbm9hEAAgIABQEGBgEDAQMFAwYvAAERAiEDMUESUWFxgZEiEzLwobEEwdHh8UJSI2JyFJIzgkMkorI0U0Rjc8LSg5OjVOLyBRUlBhYmNWRFVTZ0ZbOEw9N14/NGlKSFtJXE1OT0pbXF1eX1VmZ2hv/aAAwDAQACEQMRAD8A8yXPfmSIY5ZL28D7f7/3f/MTRxmU64Ej8X+D/vX1IQAFAfCP5cP8P/wX/vP+8yMbg0kcQ6Wu5kf+TD/xf/euOTDKGoNj3/D+/wD3r7IHBuv4/wDEzsvXgi9v7/7xwrMsHh7pjuU75Vz++516nH6chWkZfD/X/vv/ADF/vMP/AHbjWny/8k9ZUcjJ/9DkxG8vuj/8SfQGoJD52PTKPaJPdLJsFnl521Non1DvEBo6gkDx2SeUQO6+fil5f8cXWM7jsI81f8thSOqA007/APkXhrT5f+Sevq50Yj+qX/vt5P3+91/iQ//R4ZeUiXgR/wDFHfLKi45Ph/8APkz4+X2tyZo7MZiZeXUV6e5y4lZ53bd32PPJHT/F8o/2L9T9/wB86ftfP/F+/rf+/XmUw6g7pn2eT9//ADI5dv3/AIm51Zqu/wDH/wC/v5rH7/ad7EP/2Q==";
                            String testPost = "{\"username\" : \"sergio\", \"picture\" : \"" + imgb64 + "\", \"posts\": [{\"msg\": \"ciao\", \"img\" : \"" + imgb64 + "\", \"timestamp\" : \"2018-11-19 14:28:04.3189\"},{\"msg\": \"hola\", \"img\" : \"1234\", \"timestamp\" : \"2018-11-18 14:28:04.3189\"}]}";
                            //JSONObject jobj = new JSONObject(response);
                            JSONObject jobj = new JSONObject(testPost);
                            String username = jobj.getString("username");
                            String immagine = jobj.getString("picture");
                            JSONArray jarpost = jobj.getJSONArray("posts");
                            ArrayList<JSONObject> arrayList = new ArrayList(jarpost.length());
                            for(int i=0;i < jarpost.length();i++){
                                arrayList.add(jarpost.getJSONObject(i));
                            }

                            TextView myAwesomeTextView = (TextView) getView().findViewById(R.id.user);
                            myAwesomeTextView.setText(username);
                            byte[] decodedString = Base64.decode(immagine, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            ImageView image = (ImageView) getView().findViewById(R.id.imageView);
                            image.setImageBitmap(decodedByte);
                            ListView posts = (ListView) getView().findViewById(R.id.postlistview);
                            PostProfiloAdapter adapter = new PostProfiloAdapter(getContext(),android.R.layout.list_content, arrayList);
                            posts.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Holo_Dialog_NoActionBar);
                        builder.setTitle("Error on login")
                                .setMessage(error.toString())
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", ActivityForVar.getUsername());
                params.put("session_id", ActivityForVar.getSessionId());

                return params;
            }
        };
        queue.add(postRequest);
    }

}
