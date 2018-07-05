package helpers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Save {

    private Context TheThis;
    private String NameOfFolder = "/Segurisimo";
    private String NameOfFile = "fotos";

    public void SaveImage(Context context, Bitmap ImageToSave,Boolean isInternal) {
        TheThis = context;
        String CurrentDateAndTime = getCurrentDateAndTime();
        if(isInternal){
            ContextWrapper cw = new ContextWrapper(context);
            File dirImages = cw.getDir("fotos", Context.MODE_PRIVATE);
            File myPath = new File(dirImages, "foto--"+CurrentDateAndTime + ".png");

            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(myPath);
                ImageToSave.compress(Bitmap.CompressFormat.JPEG, 10, fos);
                fos.flush();
            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }else {

            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;

            File dir = new File(file_path);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, NameOfFile + CurrentDateAndTime + ".jpg");


            Toast.makeText(TheThis, NameOfFile, Toast.LENGTH_SHORT).show();
            file = new File(context.getFilesDir(), NameOfFile + CurrentDateAndTime + ".jpg");


            try {
                FileOutputStream fOut = new FileOutputStream(file);

                ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();
                MakeSureFileWasCreatedThenMakeAvabile(file);
                AbleToSave();
            } catch (FileNotFoundException e) {
                UnableToSave();
            } catch (IOException e) {
                UnableToSave();
            }
        }
    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(TheThis,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave() {
        Toast.makeText(TheThis, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(TheThis, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}