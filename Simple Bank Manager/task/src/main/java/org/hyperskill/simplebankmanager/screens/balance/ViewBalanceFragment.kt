package org.hyperskill.simplebankmanager.screens.balance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.hyperskill.simplebankmanager.R
import org.hyperskill.simplebankmanager.databinding.FragmentViewBalanceBinding
import org.hyperskill.simplebankmanager.screens.user.UserViewModel


class ViewBalanceFragment : Fragment() {
    private lateinit var binding: FragmentViewBalanceBinding
    private val sharedViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentViewBalanceBinding.inflate(inflater, container, false)

        binding.viewBalanceAmountTextView.text = getString(R.string.balance_text, sharedViewModel.getFormattedBalance())

        Log.i("ViewBalanceFragment", "onCreateView: ${sharedViewModel.getBalance()}")
        return binding.root
    }

}