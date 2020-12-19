package id.alin_gotama.volunteer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import id.alin_gotama.volunteer.R;

public class DatePickerFragment extends DialogFragment implements View.OnClickListener {
    private DatePicker datePicker;
    private Button btnSubmit;
    private String Date;
    private Context context;
    private CreateEvent createEvent;
    private TextView tv;

    public DatePickerFragment(Context context, TextView tv) {
        this.context = context;
        this.tv = tv;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Fragment fragment = getParentFragment();

        if(fragment instanceof CreateEvent){
            createEvent = (CreateEvent) fragment;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.datepickerfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.datePicker = view.findViewById(R.id.dpDatePickerFragment);
        this.btnSubmit = view.findViewById(R.id.btnDatePickerFragmentSubmit);

        this.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnDatePickerFragmentSubmit){
            StringBuilder tanggal = new StringBuilder();
            tanggal.append(this.datePicker.getYear());
            tanggal.append("-");
            tanggal.append((this.datePicker.getMonth()+1));
            tanggal.append("-");
            tanggal.append(this.datePicker.getDayOfMonth());

            tv.setText(tanggal.toString());
            dismiss();
        }
    }

    public interface onSelect{
        void onSelectDate(String text);
    }

}
