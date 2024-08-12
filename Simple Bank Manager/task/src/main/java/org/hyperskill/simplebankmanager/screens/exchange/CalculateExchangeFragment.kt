package org.hyperskill.simplebankmanager.screens.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.hyperskill.simplebankmanager.databinding.FragmentCalculateExchangeBinding

private val defaultMap = mapOf(
    "EUR" to mapOf(
        "GBP" to 0.5,
        "USD" to 2.0
    ),
    "GBP" to mapOf(
        "EUR" to 2.0,
        "USD" to 4.0
    ),
    "USD" to mapOf(
        "EUR" to 0.5,
        "GBP" to 0.25
    )
)

class CalculateExchangeFragment : Fragment() {
    private lateinit var binding: FragmentCalculateExchangeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculateExchangeBinding.inflate(layoutInflater)

        binding.calculateExchangeButton.setOnClickListener {
            val fromCurrency = binding.calculateExchangeFromSpinner.selectedItem.toString()
            val toCurrency = binding.calculateExchangeToSpinner.selectedItem.toString()
            val amountText = binding.calculateExchangeAmountEditText.text.toString()

            if (amountText.isEmpty()) {
                Toast.makeText(requireContext(), "Enter amount", Toast.LENGTH_SHORT).show()
            } else {
                val amount = amountText.toDouble()
                val conversionRate = defaultMap[fromCurrency]?.get(toCurrency)
                val convertedAmount = amount * conversionRate!!
                val currencySymbol = getCurrency(toCurrency)
                val currencySymbolFrom = getCurrency(fromCurrency)
                val formattedAmountTo = String.format("%s%.2f", currencySymbol, convertedAmount)
                val formattedAmountFrom = String.format("%s%.2f", currencySymbolFrom, amount)

                val resultCurrency = "$formattedAmountFrom = $formattedAmountTo"

                binding.calculateExchangeDisplayTextView.text = resultCurrency
            }

        }

        val currencySelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                checkCurrencies()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.calculateExchangeFromSpinner.onItemSelectedListener = currencySelectedListener
        binding.calculateExchangeToSpinner.onItemSelectedListener = currencySelectedListener

        return binding.root
    }

    private fun getCurrency(toCurrency: String): String {
        val currencySymbol = when (toCurrency) {
            "USD" -> "$"
            "EUR" -> "€"
            "GBP" -> "£"
            else -> ""
        }
        return currencySymbol
    }

    fun checkCurrencies() {
        val fromCurrency = binding.calculateExchangeFromSpinner.selectedItem.toString()
        val toCurrency = binding.calculateExchangeToSpinner.selectedItem.toString()
        if (fromCurrency == toCurrency) {
            Toast.makeText(requireContext(), "Cannot convert to same currency", Toast.LENGTH_SHORT)
                .show()
            val adapter = binding.calculateExchangeToSpinner.adapter
            val itemCount = adapter.count
            for (i in 0 until itemCount) {
                val nextCurrency = adapter.getItem(i).toString()
                if (nextCurrency != fromCurrency) {
                    binding.calculateExchangeToSpinner.setSelection(i)
                    break
                }
            }
        }

    }
}