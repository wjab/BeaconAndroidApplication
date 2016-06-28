package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Dialog;
import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;


/**
 * Created by Centauro on 28/06/2016.
 */
public class PreferencesDialogFragment extends DialogFragment {
    public PreferencesDialogFragment() {
    }
    Switch notification,email;
    Button btn_save;
    Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        // Get the layout inflater
        dialog.setTitle("Preferencias");
        dialog.setContentView(R.layout.layout_dialog);
        notification=(Switch)dialog.findViewById(R.id.switch3);
        email=(Switch)dialog.findViewById(R.id.switch4);
        btn_save=(Button)dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePreferences();
            }
            });
        return dialog;
    }

    public void capturePreferences(){
        if(notification.isChecked()){
            Toast.makeText(getActivity().getApplicationContext(), "Noticaciones activadas", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(), "Noticaciones desactivadas", Toast.LENGTH_SHORT).show();
        }

        if(email.isChecked()){
            Toast.makeText(getActivity().getApplicationContext(), "Email activado", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(),"Email desactivado", Toast.LENGTH_SHORT).show();
        }
        dialog.cancel();
    }


}