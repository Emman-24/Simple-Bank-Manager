package org.hyperskill.simplebankmanager.screens.transfer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.R
import org.hyperskill.simplebankmanager.databinding.FragmentTransferFundsBinding
import org.hyperskill.simplebankmanager.screens.user.UserViewModel

class TransferFundsFragment : Fragment() {

    private lateinit var binding: FragmentTransferFundsBinding
    private val sharedViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTransferFundsBinding.inflate(inflater, container, false)

        Log.i("TransferFundsFragment", "onCreateView: ${sharedViewModel.getBalance()}")



        binding.transferFundsButton.setOnClickListener {
            val amountText = binding.transferFundsAmountEditText.text.toString()
            val accountText = binding.transferFundsAccountEditText.text.toString()

            if (validateTextFields(amountText, accountText)) {
                validateAccount(amountText, accountText)
            } else {
                return@setOnClickListener
            }
           // validateTextFields(amountText, accountText)
           // validateAccount(amountText, accountText)
        }

        return binding.root
    }

    private fun validateAccount(amountText: String, accountText: String) {
        if (!isValidAccount(accountText)) {
            binding.transferFundsAccountEditText.error = "Invalid account number"
        } else {
            val amount = amountText.toDouble()
            val formattedAmount = String.format("%.2f", amount)
            val hasSufficientFunds = sharedViewModel.sendMoney(amount)
            if (hasSufficientFunds) {
                sharedViewModel.sendTransfer(amount)
                Toast.makeText(
                    context,
                    "Transferred $$formattedAmount to account $accountText",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_transferFundsFragment_to_userMenuFragment)
                Log.i("TransferFundsFragment", "validateAccount: ${sharedViewModel.getBalance()}")
            } else {
                Toast.makeText(
                    context,
                    "Not enough funds to transfer $$formattedAmount",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateTextFields(amountText: String, accountText: String): Boolean {
        var isValid = true
        if (amountText.trim().isEmpty() || amountText.toString() == "0") {
            binding.transferFundsAmountEditText.error = "Invalid amount"
            isValid = false
        } else {
            binding.transferFundsAmountEditText.error = null
        }
        if (accountText.trim().isEmpty()) {
            binding.transferFundsAccountEditText.error = "Invalid account number"
            isValid = false
        } else {
            binding.transferFundsAccountEditText.error = null
        }
        return isValid
    }


    private fun isValidAccount(account: String): Boolean {
        val pattern = Regex("(sa|ca)\\d{4}")
        return pattern.matches(account)
    }


}