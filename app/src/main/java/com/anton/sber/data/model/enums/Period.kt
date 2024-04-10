package com.anton.sber.data.model.enums

enum class Period {
    Day,
    Month;

    companion object {
        fun getByName(name: String?): Period {
            entries.forEach { period -> if (name == period.name) return period }
            return Month
        }

        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            entries.forEach { period -> options.add(period.name) }
            return options
        }

    }
}