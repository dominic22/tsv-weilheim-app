package tsvweilheimapp.globalone.com.tsvweilheim.dialog_adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import tsvweilheimapp.globalone.com.tsvweilheim.R;

public class DialogHandler {
    private Dialog dialog;
    private String SPO_URL;
    public DialogHandler() {
    }
    private Context context;
    public DialogHandler(Context context) {
        this.context = context;
    }

    public void closeDialog() {
        this.dialog.cancel();
    }

    public void showDialog(String url){
        SPO_URL = url;
        this.dialog = new Dialog(this.context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog = this.dialog;
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(true);

        //	dialog.setTitle("Dialog message");
        Button btnOpenSPO = dialog.findViewById(R.id.btnOpenSPO);
        Button btnBack = dialog.findViewById(R.id.btnBack);
        FrameLayout frame = dialog.findViewById(R.id.FrameLayout);

        btnOpenSPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPO_URL != "") {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SPO_URL));
                    v.getContext().startActivity(browserIntent);
                }
                //Toast.makeText(v.getContext(), "URL: "+ SPO_URL, Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
}
