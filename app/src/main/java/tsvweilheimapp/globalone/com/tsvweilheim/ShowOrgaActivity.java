package tsvweilheimapp.globalone.com.tsvweilheim;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowOrgaActivity extends Activity {

    ImageView image;
    TextView txtString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orga);
        image = findViewById(R.id.imageOrga);
        txtString = findViewById(R.id.txtOrga);
        String receivedParameter = getIntent().getStringExtra("Parameter");

        if (receivedParameter.equals("vorstand")) {
            image.setBackgroundResource(R.drawable.vs600x340);
            txtString.setText(R.string.vorstandtext);
            this.getActionBar().setTitle("TSV Weilheim - Vorstand");
        } else if (receivedParameter.equals("jugendleitung")) {
            image.setBackgroundResource(R.drawable.jl);
            txtString.setText(R.string.jugendleitungtext);
            this.getActionBar().setTitle("TSV Weilheim - Jugendleitung");
        } else if (receivedParameter.equals("wirtschaftsausschuss")) {
            image.setBackgroundResource(R.drawable.wa600x340);
            txtString.setText(R.string.wirtschaftsausschusstext);
            this.getActionBar().setTitle("TSV Weilheim - Wirtschaftsasschuss");
        } else if (receivedParameter.equals("foerderverein")) {
            image.setBackgroundResource(R.drawable.vf);
            txtString.setText(R.string.foerdervereintext);
            this.getActionBar().setTitle("TSV Weilheim - F�rderverein");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
