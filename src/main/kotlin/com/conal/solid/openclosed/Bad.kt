//package com.conal.solid.openclosed
//
//fun main(){
//
//}
//
//enum class AccountType {
//    Basic, Intermediate, Premium
//}
//
//class User(val id: Int, val fullName: String) {
//    lateinit var accountType: AccountType
//}
//
//class BadCustomer(val id: Int, val fullName: String) {
//    fun generate(user: User) {
//        val newCustomer = Customer(123, user.fullName)
//        when(user.accountType){
//            AccountType.Basic -> GenerateBasicAccount(newCustomer)
//            AccountType.Intermediate -> GenerateIntermediateAccount(newCustomer)
//            AccountType.Premium -> GeneratePremiumAccount(newCustomer)
//        }
//    }
//
//    private fun GeneratePremiumAccount(newCustomer: Customer) {
//        //
//    }
//
//    private fun GenerateIntermediateAccount(newCustomer: Customer) {
//        //
//    }
//
//    private fun GenerateBasicAccount(newCustomer: Customer) {
//        //
//    }
//}
//
