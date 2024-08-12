package org.hyperskill.simplebankmanager.screens.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.R
import org.hyperskill.simplebankmanager.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {
    private lateinit var viewBinding: LoginFragmentBinding
    private val sharedViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LoginFragmentBinding.inflate(inflater, container, false)

        val intent = activity?.intent
        val defaultUsername = "Lara"
        val defaultPassword = "1234"
        val defaultAmount = 100.00

        val bundleUsername = intent?.getStringExtra("username") ?: defaultUsername
        val bundlePassword = intent?.getStringExtra("password") ?: defaultPassword
        val bundleBalance = intent?.getDoubleExtra("balance", defaultAmount)

        val context = context

        viewBinding.loginButton.setOnClickListener {
            val inputUsername = viewBinding.loginUsername.text.toString()
            val inputPassword = viewBinding.loginPassword.text.toString()

            if (inputUsername == bundleUsername && inputPassword == bundlePassword) {
                val bundle = Bundle()
                bundle.putString("username", inputUsername)
                sharedViewModel.setBalance(bundleBalance ?: defaultAmount)
                findNavController().navigate(R.id.action_loginFragment_to_userMenuFragment, bundle)
                Toast.makeText(context, "logged in", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }


        return viewBinding.root
    }


}