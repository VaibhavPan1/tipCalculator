package com.example.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.tipcalc.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip()  }

        //custom text appear only when checkbox is selected and radio group will be deactivated
        //this is a lambda function and can be used for multiple functionalities as shown below
        binding.customCheckbox.setOnCheckedChangeListener{_, isChecked ->

            //this is for enabling or disabling radio group based on status of checkbox
            // it can only be implemented through loop
            binding.tipOptions.isEnabled = !isChecked
            for (i in 0 until binding.tipOptions.childCount){
                binding.tipOptions.getChildAt(i).isEnabled = !isChecked
            }

            //this is for setting the visibility of Material textInputLayout for custom input
            binding.customText.visibility = if (isChecked) {
//                EditText.VISIBLE  //this was used when editText was used
                TextInputLayout.VISIBLE
            }else{
                TextInputLayout.GONE
//                EditText.GONE
            }
        }
    }
    private fun calculateTip(){
//        val cost = binding.costOfService.text.toString().toDoubleOrNull() // now replaced with material UI input text
        val cost2 = binding.materialInput.text.toString().toDoubleOrNull()
        val customText = binding.customEditText.text.toString().toDoubleOrNull()
        var tipPercentage: Double? = null
        var isCustomChecked = binding.customCheckbox.isChecked




        //if custom percentage is null
        if(binding.customCheckbox.isChecked && customText == null){
            binding.tipResults.text = ""
            return
        }

        //it was used when using editText element
//        if (cost == null){
//            binding.tipResults.text = ""
//            return
//        }
        if (cost2 == null){
            binding.tipResults.text = ""
            return
        }

        //this is called assignment lifting and is only available in kotlin
        tipPercentage = if(isCustomChecked){
            binding.customEditText.text.toString().toDoubleOrNull()?.div(100) //safe call
        } else{
            when(binding.tipOptions.checkedRadioButtonId){
                R.id.eighteen_percent_option -> 0.18
                R.id.twenty_percent_option -> 0.20
                else -> .15
            }
        }

        var tip = cost2 * tipPercentage!! //because it is nullable
        val roundUp = binding.roundUpSwitch.isChecked
        if(roundUp){
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResults.text = getString(R.string.tip_amount, formattedTip)



    }
}