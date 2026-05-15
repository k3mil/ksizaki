package com.example.ksiki;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etTytul, etAutor, etCena;
    private SwitchCompat swPromocja;
    private LinearLayout llPromocja;
    private SeekBar sbPromocja;
    private TextView tvWartoscPromocji;
    private RadioGroup rgTyp;
    private Button btnDodaj;
    private ListView lvKsiazki;

    private ArrayList<Ksiazka> listaKsiazek;
    private ArrayAdapter<Ksiazka> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTytul = findViewById(R.id.etTytul);
        etAutor = findViewById(R.id.etAutor);
        etCena = findViewById(R.id.etCena);
        swPromocja = findViewById(R.id.swPromocja);
        llPromocja = findViewById(R.id.llPromocja);
        sbPromocja = findViewById(R.id.sbPromocja);
        tvWartoscPromocji = findViewById(R.id.tvWartoscPromocji);
        rgTyp = findViewById(R.id.rgTyp);
        btnDodaj = findViewById(R.id.btnDodaj);
        lvKsiazki = findViewById(R.id.lvKsiazki);

        listaKsiazek = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaKsiazek);
        lvKsiazki.setAdapter(adapter);

        swPromocja.setOnCheckedChangeListener((przycisk, czyZaznaczone) -> {
            if (czyZaznaczone) {
                llPromocja.setVisibility(View.VISIBLE);
            } else {
                llPromocja.setVisibility(View.GONE);
            }
        });

        sbPromocja.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int postep, boolean odUzytkownika) {
                int wartosc = postep + 10;
                tvWartoscPromocji.setText(wartosc + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnDodaj.setOnClickListener(v -> {
            String tytul = etTytul.getText().toString().trim();
            String autor = etAutor.getText().toString().trim();
            String cenaTekst = etCena.getText().toString().trim();

            if (tytul.isEmpty() || autor.isEmpty() || cenaTekst.isEmpty()) {
                Toast.makeText(MainActivity.this, "Proszę wypełnić wszystkie pola!", Toast.LENGTH_SHORT).show();
                return;
            }

            double cena;
            try {
                cena = Double.parseDouble(cenaTekst);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Niepoprawny format ceny!", Toast.LENGTH_SHORT).show();
                return;
            }

            int wybranyId = rgTyp.getCheckedRadioButtonId();
            RadioButton rbWybrany = findViewById(wybranyId);
            String typ = rbWybrany != null ? rbWybrany.getText().toString() : "";

            int wartoscPromocji = 0;
            if (swPromocja.isChecked()) {
                wartoscPromocji = sbPromocja.getProgress() + 10;
            }

            Ksiazka nowaKsiazka = new Ksiazka(tytul, autor, cena, typ, wartoscPromocji);
            listaKsiazek.add(nowaKsiazka);
            adapter.notifyDataSetChanged();

            wyczyscFormularz();
            Toast.makeText(MainActivity.this, "Dodano nową książkę", Toast.LENGTH_SHORT).show();
        });
    }

    private void wyczyscFormularz() {
        etTytul.setText("");
        etAutor.setText("");
        etCena.setText("");
        swPromocja.setChecked(false);
        sbPromocja.setProgress(0);
        tvWartoscPromocji.setText("10%");
        rgTyp.check(R.id.rbPapierowa); // Resetuje na papierową
    }
}
