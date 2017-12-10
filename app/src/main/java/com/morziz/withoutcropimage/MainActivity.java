package com.morziz.withoutcropimage;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.morziz.withoutcropimage.imagePicker.SanImagePicker;
import com.morziz.withoutcropimage.imagePicker.Sources;

import java.io.File;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    static File imgFile;
    private ImageView imageView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        imageView = (ImageView) findViewById(R.id.imageView);
        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog(activity);
            }
        });
    }


    public void pickImageFromSource(Sources source) {
        SanImagePicker.with(activity).requestImage(source).subscribe(new Consumer<Uri>() {
            @Override
            public void accept(Uri uri) throws Exception {
                onImagePicked(uri);
                String path = SanImagePicker.getPath(activity, uri);
                imgFile = new File(path);
            }
        });
    }

    public void showImagePickerDialog(final Activity activity) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.image_chooser_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView tv_camera = (TextView) dialogView.findViewById(R.id.tv_camera);
        TextView tv_gallery = (TextView) dialogView.findViewById(R.id.tv_gallery);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromSource(Sources.CAMERA);
                alertDialog.dismiss();
            }
        });


        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromSource(Sources.GALLERY);
                alertDialog.dismiss();
            }
        });


    }

    private  void onImagePicked(Object result) {

        if (result instanceof Bitmap) {
            imageView.setImageBitmap((Bitmap) result);
        } else {
            Glide.with(activity)
                    .load(result)
                    .crossFade()
                    .into(imageView);
            Glide.with(activity)
                    .load(result)
                    .crossFade()
                    .into(imageView);

        }
    }
}
