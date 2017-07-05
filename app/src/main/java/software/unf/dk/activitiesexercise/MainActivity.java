package software.unf.dk.activitiesexercise;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EMAIL_REPLY = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;


    private EditText recipient, subject, message;
    private Button send, addPhoto;
    private TextView reply;
    private ImageView photoIV;
    private Bitmap photoBM;
    private Boolean photoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Super Mail App 9001");

        // Brug findViewById() til at koble variablerne sammen med deres elementer.



    }

    public void addPhoto(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Image..."), REQUEST_IMAGE_CAPTURE); // REQUEST_IMAGE_CAPTURE er en konstant vi selv har defineret i starten af filen
        }

    }

    public void send(View view) {
        Intent intent = new Intent(this, Recipient.class);
        intent.putExtra("recipient", recipient.getText().toString());
        intent.putExtra("subject", subject.getText().toString());
        intent.putExtra("message", message.getText().toString());
        if (photoTaken) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photoBM.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            intent.putExtra("bitmap", byteArray);
        }
        startActivityForResult(intent, REQUEST_EMAIL_REPLY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EMAIL_REPLY){
            handleEmailReply(resultCode, data);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            handleImageCapture(resultCode, data);
        }
    }

    private void handleImageCapture(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            photoBM = (Bitmap) data.getExtras().get("data");
            photoIV.setImageBitmap(photoBM);

            photoTaken = true;
        }
    }

    private void handleEmailReply(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            Log.i("hej", "result ok");
            String reply = data.getExtras().getString("reply");
            if (!reply.equals("")) {
                this.reply.setText("Reply: " + reply);
            } else {
                this.reply.setText("");
            }


            // Tømmer felternes indhold for at undgå spam
            this.recipient.setText("");
            this.subject.setText("");
            this.message.setText("");

            clearBitmap();

            this.recipient.requestFocus();
        }
    }

    private void clearBitmap() {
        photoIV.setImageResource(android.R.color.transparent);
        photoTaken = false;
    }
}
