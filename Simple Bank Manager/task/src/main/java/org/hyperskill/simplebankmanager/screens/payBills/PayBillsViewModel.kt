package org.hyperskill.simplebankmanager.screens.payBills

import androidx.lifecycle.ViewModel

class PayBillsViewModel : ViewModel() {
    val defaultBillInfoMap = mapOf(
        "ELEC" to Triple("Electricity", "ELEC", 45.00),
        "GAS" to Triple("Gas", "GAS", 20.00),
        "PHONE" to Triple("Mobile phone", "PHONE", 80.0),
        "CARINSURANCE" to Triple("Car insurance", "CARINSURANCE", 120.0),
        "WTR" to Triple("Water", "WTR", 25.50)
    )

    val billInfoMap = mutableMapOf<String, Triple<String, String, Double>>()

    init {
        billInfoMap.putAll(defaultBillInfoMap)
    }

    fun getBillInfo(billType: String): Triple<String, String, Double>? {
        return billInfoMap[billType]
    }


    fun checkBillInfo(billType: String): Boolean {
        return billInfoMap.containsKey(billType)
    }


}