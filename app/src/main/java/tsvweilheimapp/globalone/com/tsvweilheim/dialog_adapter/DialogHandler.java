package tsvweilheimapp.globalone.com.tsvweilheim.dialog_adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import tsvweilheimapp.globalone.com.tsvweilheim.R;

public class DialogHandler {
    private Dialog dialog;
    public DialogHandler() {
    }
    private Context context;
    public DialogHandler(Context context) {
        this.context = context;
    }

    public void closeDialog() {
        this.dialog.cancel();
    }

    public void showDialog(){
        this.dialog = new Dialog(this.context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog = this.dialog;
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(true);

        //	dialog.setTitle("Dialog message");
        Log.v("1","1");
        Button btnOpenSPO = dialog.findViewById(R.id.btnOpenSPO);
//        Button btnCopyBIC = (Button)dialog.findViewById(R.id.btnCopyBIC);
//        Button btnDeleteIBAN = (Button)dialog.findViewById(R.id.btnDeleteIBAN);
        Button btnBack = dialog.findViewById(R.id.btnBack);
        FrameLayout frame = dialog.findViewById(R.id.FrameLayout);

        Log.v("1","2"+ this.context);

        btnOpenSPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(v.getContext(), "Spielbericht wurde angezeigt.", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        frame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();

            }
        });
//        btnDeleteIBAN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                dialog.cancel();
//            }
//        });

        dialog.show();

    }
}
