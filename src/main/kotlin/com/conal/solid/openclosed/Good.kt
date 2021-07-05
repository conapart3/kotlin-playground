//package com.conal.solid.openclosed
//
//fun main(){
//
//}
//
//interface IAccount{
//
//}
//
//class BasicAccount : IAccount
//class IntermediateAccount : IAccount
//class PremiumAccount : IAccount
//
//interface IUser {
//    val id: Int
//    val fullName: String
//    var account: IAccount
//}
//
//class BasicUser(override val id: Int, override val fullName: String) : IUser {
//    override var account = BasicAccount()
//}
//
//class IntermediateUser(override val id: Int, override val fullName: String) : IUser {
//    override var account = IntermediateAccount()
//}
//
//class PremiumUser(override val id: Int, override val fullName: String) : IUser {
//    override var account = PremiumAccount()
//}
//
//class Customer(val id: Int, val fullName: String) {
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
