package com.whatsoever.oskar.pizzaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class MainActivity extends AppCompatActivity {

    private Spinner
            spCity,
            spPizza;
    private TextView
            tvPrice;
    private EditText
            etTitle,
            etFName,
            etLName,
            etAddress,
            etZip;
    public ArrayList<String> cities = new ArrayList<String>();
    public ArrayList<Pizza> pizza = new ArrayList<Pizza>();
    public ArrayList<Pizza> pizzasp = new ArrayList<Pizza>();
    public ArrayAdapter <String> adapter1;
    public ArrayAdapter <Pizza> adapter2;
    private Button btnSave,
            btnCancle;
    public String selectedPizza;
    private double price;
    private RadioGroup
            rdgRecipe,
            rdgType,
            rdgPay;
    private RadioButton
            rdbtnEmail,
            rdbtnMail,
            rdbtnVege,
            rdbtnMeat,
            rdbtnFish,
            rdbtnCash,
            rdbtnCheck,
            rdbtnCredit;
    private CheckBox
            cbCheese,
            cbBacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*adding some places*/
        cities.add(new String("Dresden"));
        cities.add(new String("Ober-Nieder-Gummmersberg"));
        cities.add(new String("Leipzig"));
        cities.add(new String("Miami"));

        /*adding some Pizza*/
        pizza.add(new Pizza("Margarita", 4.99, false, false));
        pizza.add(new Pizza("Funghi", 5.49, false, false));
        pizza.add(new Pizza("Quattro Formaggi", 4.99, false, false));
        pizza.add(new Pizza("Tonno", 4.99, false, true));
        pizza.add(new Pizza("Salame", 4.99, true, false));
        pizza.add(new Pizza("Oro Massiccio", 4.99, false, false));
        pizza.add(new Pizza("Prosciutto", 4.99, true, false));
        pizza.add(new Pizza("scarafaggio", 6.99, true, false));
        pizza.add(new Pizza("balena", 14.99, false, true));

        pizzasp = pizza;

        updateSpinner(pizzasp);

        /*
        String[] pizzaArray = new String[ pizza.size() ];
        pizza.toArray( pizzaArray );*/

        InitializeApp();
    }



    private void InitializeApp() {

        spCity = (Spinner) findViewById(R.id.spCity);
        adapter1 = new ArrayAdapter<String> (this, simple_spinner_item, cities);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(adapter1);

        //selectedPizza = pizzasp.get(spPizza.getSelectedItemPosition()).toString();
        rdgType = (RadioGroup) findViewById(R.id.rdgType);
        //rdbtnMeat = (RadioButton) findViewById(R.id.rbtnMeat);/*initalize RadioButton*/
        //rdbtnFish = (RadioButton) findViewById(R.id.rbtnFish);

        rdgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //@Override
            public void onCheckedChanged(RadioGroup rdg, int checkedId) {
                pizzasp.clear();
                for (int i = 0; i < pizza.size(); i++) {
                    if (rdbtnMeat.isChecked() == pizza.get(i).isMeat()) {
                        if (rdbtnFish.isChecked() == pizza.get(i).isFish()) {
                            pizzasp.add(pizza.get(i)); /*adding the pizza to the display arraylist*/
                        }
                    }
                }

                updateSpinner(pizzasp);

            }
        });

        spPizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() /*why do i get null object reference*/ {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!(view instanceof TextView))
                    return;

                TextView item = (TextView) view;
                selectedPizza = item.getText().toString();
                price = (pizzasp.get((int) spPizza.getSelectedItemId()).getPrice());
                if (cbCheese.isChecked()) price += 1;
                if (cbCheese.isChecked()) price += 2;
                tvPrice.setText("" + price);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }



        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paymeth, recmeth, extra = "";

                switch (rdgPay.getCheckedRadioButtonId()){
                    case 0: paymeth = "bar bei Abhohlung";
                    case 1: paymeth = "per Überweisung";
                    case 2: paymeth = "mit Kreditkarte";
                    default: paymeth = "bar bei Abhohlung";
                }
                if (rdgRecipe.getCheckedRadioButtonId() == rdbtnMail.getId()) recmeth = "per Post";
                else recmeth = "per e-Mail";

                if (cbCheese.isChecked() | cbBacon.isChecked()){
                    extra += " mit Zusätzen (";
                    if (cbCheese.isChecked()) extra += "extra Käse";
                    if (cbCheese.isChecked() && cbBacon.isChecked()) extra += " und ";
                    if (cbBacon.isChecked()) extra += "extra Bacon";
                    extra += ")";
                }

                String order = "" + etTitle.getText() + " " + etFName.getText() + " " + etLName.getText()
                        + " (" + etAddress.getText() + ", " + etZip.getText() + " " + spCity.toString() +
                        " ) bestellt eine Pizza " +
                        pizzasp.get((int) spPizza.getSelectedItemId()).getName() + extra +". Der Preis beträgt " +
                        price +
                        " Euro. Sie möchte die Rechnung " + recmeth + "erhalten und " + paymeth;

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, order);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share"));


            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etTitle.setText("Title");
                etFName.setText("First Name");
                etLName.setText("Last Name");
                etAddress.setText("Address");
                etZip.setText("Zip Code");
                rdgType.clearCheck();
                rdgRecipe.clearCheck();
                rdgPay.clearCheck();
                tvPrice.setText("Price");
                cbCheese.setChecked(false);
                cbBacon.setChecked(false);
            }


        });
    }

    private void updateSpinner (ArrayList updatePizza){
/*
        spPizza = (Spinner) findViewById(R.id.spPizza);
        adapter2 = new ArrayAdapter(this, simple_spinner_item, updatePizza);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCity.setAdapter(adapter2);*/

    }


}
