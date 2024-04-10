package com.anton.sber.data.model.enums

enum class Type {
    RegularTransaction,
    Cafe, Erip,
    OnlineDelivery,
    Store, Pharmacy,
    Beauty;

    companion object {
        fun getByName(name: String?): Type {
            entries.forEach { type -> if (name == type.name) return type }
            return RegularTransaction
        }

        fun getOption(): List<String> {
            val options = mutableListOf<String>()
            entries.forEach { type -> options.add(type.name) }
            return options
        }
    }


}