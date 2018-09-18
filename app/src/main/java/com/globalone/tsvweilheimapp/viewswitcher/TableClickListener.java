package com.globalone.tsvweilheimapp.viewswitcher;

import android.view.View;

import com.globalone.tsvweilheimapp.dialog_adapter.DialogHandler;

public class TableClickListener implements View.OnClickListener {

        String spielBerichtUrl;
        DialogHandler dialogHandler;
        public TableClickListener(DialogHandler dialogHandler, String spielBerichtUrl) {
            this.dialogHandler = dialogHandler;
            this.spielBerichtUrl = spielBerichtUrl;
        }

        @Override
        public void onClick(View v)
        {
            dialogHandler.showDialog(spielBerichtUrl);
        }
}
