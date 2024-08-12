package org.hyperskill.simplebankmanager.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.R
import org.hyperskill.simplebankmanager.databinding.UserMenuFragmentBinding

class UserMenuFragment : Fragment() {

    private lateinit var viewBinding: UserMenuFragmentBinding
    private val sharedViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = UserMenuFragmentBinding.inflate(inflater, container, false)

        val username = arguments?.getString("username")
        if (username == null) {
            sharedViewModel.getUsername()
        } else {
            sharedViewModel.setUsername(username.toString())
        }


        viewBinding.userMenuWelcomeTextView.text = "Welcome ${sharedViewModel.getUsername()}"

        viewBinding.userMenuViewBalanceButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment)
        }

        viewBinding.userMenuTransferFundsButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_transferFundsFragment)
        }

        viewBinding.userMenuExchangeCalculatorButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_calculateExchangeFragment)
        }
        viewBinding.userMenuPayBillsButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_payBillsFragment)
        }

        return viewBinding.root
    }

}