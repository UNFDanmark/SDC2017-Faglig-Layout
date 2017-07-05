package software.unf.dk.activitiesexercise;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Recipient extends Activity {
    private TextView recipient, subject, message;
    private EditText replyMessage;
    private Button back, reply;
    private ImageView photoIV;
    private Bitmap photoBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipient);

        // Brug findViewById() til at koble variablerne sammen med deres elementer.





        // Alt det følgende SKAL være EFTER I har koblet jeres variabler

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            recipient.setText(extras.getString("recipient"));
            subject.setText(extras.getString("subject"));
            message.setText(extras.getString("message"));

            byte[] byteArray = extras.getByteArray("bitmap");
            if (byteArray != null) {
                photoBM = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                photoIV.setImageBitmap(photoBM);
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reply();
            }
        });
    }

    private void back() {
        finish();
    }

    private void reply(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("reply", replyMessage.getText().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
