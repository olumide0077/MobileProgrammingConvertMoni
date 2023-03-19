package com.example.convertmoni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    var baseCurrency = "AUD"
    var convertedToCurrency = "NGN"
    private var conversion = 0f
    private val etFirstConversion: EditText by lazy { findViewById(R.id.etFirstConversion) }
    private val etSecondConversion: EditText by lazy { findViewById(R.id.etSecondConversion) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSpinner()
        textChangedStuff()
    }

    private fun textChangedStuff() {
        etFirstConversion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    getApiResult()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Type a value", Toast.LENGTH_SHORT).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("Main", "Before Text Changed")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Main", "OnTextChanged")
            }

        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getApiResult() {
        if (etFirstConversion.text.isNotEmpty() && etFirstConversion.text.isNotBlank()) {
            val amount = etFirstConversion.text.toString()
            val API =
                "https://api.apilayer.com/exchangerates_data/convert?to=$convertedToCurrency&from=$baseCurrency&amount=$amount&apikey=z12op2msHt6PkhRioQetaQYcr4BQRNeu"

            if (baseCurrency == convertedToCurrency) {
                Toast.makeText(
                    applicationContext,
                    "Please pick a currency to convert",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                GlobalScope.launch(Dispatchers.IO) {

                    try {

                        val apiResult = URL(API).readText()
                        val jsonObject = JSONObject(apiResult)
                        conversion = jsonObject.getString("result").toFloat()

                        Log.d("Main", "$conversion")
                        Log.d("Main", apiResult)

                        withContext(Dispatchers.Main) {
                            val text = conversion.toString()
                            etSecondConversion.setText(text)

                        }

                    } catch (e: Exception) {
                        Log.e("Main", "$e")
                    }
                }
            }
        }
    }

    private fun setupSpinner() {
        val spnFrom: Spinner = findViewById(R.id.spnFirstConversion)
        val spnTo: Spinner = findViewById(R.id.spnSecondConversion)

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spnFrom.adapter = adapter

            val fromCurrencyPosition = adapter.getPosition("AUD")
            spnFrom.setSelection(fromCurrencyPosition, true)
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spnTo.adapter = adapter

            val toCurrencyPosition = adapter.getPosition("NGN")
            spnTo.setSelection(toCurrencyPosition, true)
        }

        spnFrom.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

        })

        spnTo.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }
        })
    }
}