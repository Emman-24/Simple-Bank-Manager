package org.hyperskill.simplebankmanager.screens.payBills

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import org.hyperskill.simplebankmanager.databinding.FragmentPayBillsBinding
import org.hyperskill.simplebankmanager.screens.user.UserViewModel


class PayBillsFragment : Fragment() {

    private lateinit var binding: FragmentPayBillsBinding
    private val sharedViewModel: UserViewModel by activityViewModels()
    private val viewModel: PayBillsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayBillsBinding.inflate(layoutInflater)

        binding.payBillsShowBillInfoButton.setOnClickListener {

            val textBill = binding.payBillsCodeInputEditText.text.toString()

            if (textBill.isEmpty()) {
                displayAlertDialogError()
                return@setOnClickListener
            }
            if (viewModel.checkBillInfo(textBill)) {
                val billInfo = viewModel.getBillInfo(textBill)
                displayAlertDialogBillInfo(
                    billInfo!!.first,
                    billInfo.second,
                    String.format("%.2f", billInfo.third.toDouble())

                )
            } else {
                displayAlertDialogError()
                return@setOnClickListener
            }

        }
        return binding.root
    }


    private fun displayAlertDialogError() {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage("Wrong code")
            .setPositiveButton(android.R.string.ok) { _, _ ->

            }
            .show()
    }

    private fun displayAlertDialogBillInfo(billName: String, billCode: String, billAmount: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Bill info")
            .setMessage(
                "Name: $billName\n" +
                        "BillCode: $billCode\n" +
                        "Amount: $$billAmount"
            )
            .setPositiveButton("Confirm") { _, _ ->
                val amount = billAmount.toDouble()
                if (sharedViewModel.enoughtBalanceToPayBill(amount)) {
                    sharedViewModel.payBill(amount)
                    Toast.makeText(requireContext(), "Payment for bill $billName, was successful", Toast.LENGTH_SHORT).show()
                    binding.payBillsCodeInputEditText.text.clear()
                } else {
                    displayAlertDialogNotEnought()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun displayAlertDialogNotEnought() {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage("Not enough funds")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                Toast.makeText(requireContext(), "Ok", Toast.LENGTH_SHORT).show()
            }
            .show()
    }


}