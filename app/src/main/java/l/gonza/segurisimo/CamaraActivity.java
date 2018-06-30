package l.gonza.segurisimo;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import model.Imangen;
import sql.DatabaseHelper;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CamaraActivity extends AppCompatActivity  implements View.OnClickListener {

    private static String APP_DIRECTORY = "Segurisimo";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "/fotos";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView imageViewSeleccionada;
    private TextView textViewCamara,textViewGaleria;
    private DatabaseHelper databaseHelper;
    private AppCompatButton appCompatButtonLogin;

    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);


        initViews();
        initListeners();
        initObjects();
        initFunctions();
    }

    private void initFunctions() {
        if(mayRequestStoragePermission()){
            textViewGaleria.setEnabled(true);
            textViewCamara.setEnabled(true);
        } else {
            textViewCamara.setEnabled(false);
            textViewGaleria.setEnabled(false);
        }

    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
    }


    private void initViews() {
        imageViewSeleccionada = findViewById(R.id.ImageViewSeleccionada);
        textViewCamara = findViewById(R.id.TextViewCamara);
        textViewGaleria = findViewById(R.id.TextViewGaleria);
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
    }

    private void initListeners() {
        textViewCamara.setOnClickListener(this);
        textViewGaleria.setOnClickListener(this);
        appCompatButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TextViewCamara:
                openCamera();
                break;
            case R.id.TextViewGaleria:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                break;
            case R.id.appCompatButtonLogin:
                Imangen imangen = new Imangen();
                imangen.setPath(mPath);
                Integer siniestroId = getIntent().getExtras().getInt("siniestroId");
                if(siniestroId == -1){
                    imangen.setSiniestro_id(1);
                }else{
                    imangen.setSiniestro_id(siniestroId);
                }
                databaseHelper.addImage(imangen);
                Toast.makeText(CamaraActivity.this, mPath, Toast.LENGTH_SHORT).show();
                startActivity( new Intent(getApplicationContext(),HomeActivity.class));
                break;
        }
    }

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(getWindow().getDecorView().getRootView(), "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Uri photoURI;
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
                photoURI = FileProvider.getUriForFile( this, getApplicationContext().getPackageName() + ".provider", newFile);
            }else {
                photoURI =Uri.fromFile(newFile);
            }


            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI );
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });

                    Toast.makeText(CamaraActivity.this, mPath, Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    imageViewSeleccionada.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mPath =getPathFromURI(path);
                    Toast.makeText(CamaraActivity.this, mPath , Toast.LENGTH_SHORT).show();
                    imageViewSeleccionada.setImageURI(path);
                    break;

            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(CamaraActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                textViewGaleria.setEnabled(true);
                textViewCamara.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CamaraActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }
}
