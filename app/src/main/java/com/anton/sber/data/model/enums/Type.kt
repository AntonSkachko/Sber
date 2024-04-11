package com.anton.sber.data.model.enums

enum class Type(
    public val russianName: String
) {


    RegularTransaction("Обычная транзакция"),
    Cafe("Кафе"), Erip("Ерип"),
    OnlineDelivery("Онлайн доставка"),
    Store("Магазин"), Pharmacy("Аптека"),
    Beauty("Красота и здоровье");

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