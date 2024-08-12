package org.hyperskill.simplebankmanager.screens.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private var _balance = MutableLiveData<Double>()
    var balance: LiveData<Double> = _balance

    private var _username = MutableLiveData<String>()
    var username: LiveData<String> = _username

    fun setUsername(name: String) {
        _username.value = name
    }

    fun getUsername(): String {
        return _username.value ?: ""
    }


    fun setBalance(amount: Double) {
        _balance.value = amount
    }

    fun getBalance(): Double {
        return _balance.value ?: 0.0
    }

    fun getFormattedBalance(): String {
        return "%.2f".format(getBalance())
    }

    fun sendMoney(amount: Double): Boolean {
        val currentBalance = getBalance()
        return if (currentBalance >= amount) {
            true
        } else {
            false
        }
    }

    fun sendTransfer(amount: Double) {
        val currentBalance = getBalance()
        _balance.value = currentBalance - amount
    }

    fun payBill(amount: Double) {
        val currentBalance = getBalance()
        _balance.value = currentBalance - amount
    }

    fun enoughtBalanceToPayBill(amount: Double): Boolean {
        val currentBalance = getBalance()
        return if (currentBalance >= amount) {
            true
        } else {
            false
        }
    }
}


