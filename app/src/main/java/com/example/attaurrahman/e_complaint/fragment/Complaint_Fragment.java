package com.example.attaurrahman.e_complaint.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.attaurrahman.e_complaint.R;
import com.example.attaurrahman.e_complaint.activity.LoginSignUpActivity;
import com.example.attaurrahman.e_complaint.configuration.CheckNetwork;
import com.example.attaurrahman.e_complaint.configuration.Config;
import com.example.attaurrahman.e_complaint.genralUtils.AppRepository;
import com.example.attaurrahman.e_complaint.genralUtils.HTTPMultiPartEntity;
import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.facebook.login.widget.LoginButton;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.tapadoo.alerter.Alerter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dmax.dialog.SpotsDialog;
import fr.ganfra.materialspinner.MaterialSpinner;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class Complaint_Fragment extends Fragment implements View.OnClickListener {

    MaterialSpinner spinner;
    ImageView iv_pic_cam, iv_title_image, iv_pic_cross, iv_get_pic_cam, iv_logout;
    Uri image_uri;
    EditText et_description;
    MediaController mc;
    Button btn_complaint_submit;
    FrameLayout fl_image, fl_video;
    int stopPosition;
    String complaint_image, complaint_video;
    File comlaint_file;
    Boolean flag_image;
    public static final int SUCCESS = 11;
    LoginButton loginButton;

    LinearLayout rootView;

    AlertDialog alertDialogSpot;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View view = inflater.inflate(R.layout.fragment_complaint_, container, false);

        rootView = view.findViewById(R.id.ll_layout);
        alertDialogSpot = new SpotsDialog(getActivity(), R.style.CustomUploading);
        iv_logout = view.findViewById(R.id.iv_logout);
        sharedPreferences = getActivity().getSharedPreferences("com.ecomp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        flag_image = true;


        et_description = view.findViewById(R.id.et_description);
        iv_pic_cam = view.findViewById(R.id.iv_pic_cam);
        iv_title_image = view.findViewById(R.id.iv_title_image);
        iv_pic_cross = view.findViewById(R.id.iv_pic_cross);
        iv_get_pic_cam = view.findViewById(R.id.iv_get_pic_cam);
        btn_complaint_submit = view.findViewById(R.id.btn_complaint_submit);


        fl_image = view.findViewById(R.id.fl_image);


        String[] ITEMS = {"Complaint Type", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        et_description.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_description.setFocusableInTouchMode(true);
                return false;
            }
        });


        fl_image.setOnClickListener(this);
        iv_pic_cam.setOnClickListener(this);
        iv_pic_cross.setOnClickListener(this);
        btn_complaint_submit.setOnClickListener(this);
        iv_logout.setOnClickListener(this);


        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pic_cam:


                if (flag_image == true) {

                    new FancyAlertDialog.Builder(getActivity())
                            .setTitle(getString(R.string.complaint))
                            .setMessage("Choose Image From ")
                            .setNegativeBtnText("Gallery")
                            .setPositiveBtnText("Camera")
                            .setPositiveBtnBackground(Color.parseColor("#F6C569"))
                            .setNegativeBtnBackground(Color.parseColor("#F6C569"))
                            .setAnimation(Animation.POP)
                            .setBackgroundColor(Color.parseColor("#50B8A6"))
                            .setIcon(R.drawable.imageicon, Icon.Visible)
                            .isCancellable(true)
                            .OnPositiveClicked(new FancyAlertDialogListener() {
                                @Override
                                public void OnClick() {

                                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                                }
                            })
                            .OnNegativeClicked(new FancyAlertDialogListener() {
                                @Override
                                public void OnClick() {
                                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                                }
                            })
                            .build();

                } else {

                    Alerter.create(getActivity())
                            .setTitle(getString(R.string.complaint))
                            .setText("Already Video Select")
                            .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                            .show();
                    //Toast.makeText(getActivity(), "Already Video Select", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_pic_cross:

                iv_pic_cross.setVisibility(View.GONE);
                iv_pic_cam.setImageResource(R.mipmap.cam);
                iv_get_pic_cam.setImageResource(0);
                iv_pic_cam.setEnabled(true);
                iv_pic_cam.setVisibility(View.VISIBLE);
                flag_image = true;


                break;
            case R.id.btn_complaint_submit:
                if (!CheckNetwork.isInternetAvailable(getActivity())) {
                    new CDialog(getActivity()).createAlert("No Internet Connection",
                            CDConstants.WARNING,   // Type of dialog
                            CDConstants.LARGE)    //  size of dialog
                            .setAnimation(CDConstants.SCALE_TO_TOP_FROM_BOTTOM)     //  Animation for enter/exit
                            .setDuration(5000)   // in milliseconds
                            .setTextSize(CDConstants.LARGE_TEXT_SIZE)  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                            .show();
                    // Toast.makeText(getActivity(), "Check Network Connection", Toast.LENGTH_SHORT).show();
                } else {

                    if (spinner.getSelectedItem() == null) {
                        //Toast.makeText(getActivity(), "Select Complaint Type", Toast.LENGTH_SHORT).show();

                        Alerter.create(getActivity())
                                .setTitle(getString(R.string.complaint))
                                .setText("Select Complaint Type...")
                                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                                .show();


                    } else if (et_description.length() <= 1) {

                        Alerter.create(getActivity())
                                .setTitle(getString(R.string.complaint))
                                .setText("Enter Desription")
                                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                                .show();

                        //Toast.makeText(getActivity(), "Enter Desription", Toast.LENGTH_SHORT).show();

                    } else {
                        if (flag_image == true) {
                            comlaint_file = new File(complaint_image);

                        }
                        new UploadFileToServer().execute();
                        alertDialogSpot.show();
                    }

                }
                break;
            case R.id.iv_logout:
                AppRepository.mPutValue(getActivity()).putBoolean("loggedIn", false).commit();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginSignUpActivity.class));


        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {


            case 0:
                if (resultCode == RESULT_OK) {


                    Bitmap bm = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                    File sourceFile = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".jpg");
                    FileOutputStream fo;
                    try {
                        sourceFile.createNewFile();
                        fo = new FileOutputStream(sourceFile);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_get_pic_cam.setImageBitmap(bm);

                    complaint_image = sourceFile.getAbsolutePath().toString();
                    iv_pic_cross.setVisibility(View.VISIBLE);
                    iv_pic_cam.setVisibility(View.GONE);
                    iv_get_pic_cam.setEnabled(false);


                } else {
                    Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    image_uri = imageReturnedIntent.getData();
                    iv_get_pic_cam.setImageURI(image_uri);

                    complaint_image = getImagePath(image_uri);
                    iv_pic_cross.setVisibility(View.VISIBLE);
                    iv_pic_cam.setVisibility(View.GONE);
                    iv_get_pic_cam.setEnabled(false);

                } else {
                    Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    public String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }


    @Override
    public void onPause() {
        Log.d(TAG, "onPause called");
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.BASE_URL + "generate_post_card");
            try {
                HTTPMultiPartEntity entity = new HTTPMultiPartEntity(
                        new HTTPMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) 100) * 100));


                            }
                        });
                // Adding file data to http body
                // Extra parameters if you want to pass to server
                entity.addPart("post_card_signature", new FileBody(comlaint_file));
                entity.addPart("post_card_img", new FileBody(comlaint_file));
                Looper.prepare();
                entity.addPart("location", new StringBody(spinner.getSelectedItem().toString()));
                entity.addPart("post_card_text", new StringBody(et_description.getText().toString()));
                entity.addPart("user_id", new StringBody("58"));
//                     pDialog.dismiss();
                Bundle args = new Bundle();

                httppost.setEntity(entity);
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                responseString = EntityUtils.toString(r_entity);

                Log.d("response string", responseString.toString());

                Toast.makeText(getActivity(), "sskdfjlsdf", Toast.LENGTH_SHORT).show();
                if (responseString.contains("true")) {
                    alertDialogSpot.dismiss();
                    Toast.makeText(getActivity(), "Successful", Toast.LENGTH_LONG).show();

                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
//                    pDialog.dismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                responseString = e.toString();
//                    pDialog.dismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }

            Log.d("zma return string", responseString);
            return responseString;

        }
    }

}
