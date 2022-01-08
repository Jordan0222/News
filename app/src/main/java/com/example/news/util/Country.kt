package com.example.news.util

object Country {
    private val countries = listOf(
        "台灣", "日本", "韓國", "美國"
    )

    private val countriesAbbrev = listOf(
        "tw", "jp", "kr", "us"
    )

    fun getAllCountriesName() : List<String> {
        return countries
    }

    fun getCountryAbbrev(country: String) : String {
        val index = countries.indexOf(country)
        return countriesAbbrev[index]
    }

    fun getCountry(countryAbbrev: String) : String {
        val index = countriesAbbrev.indexOf(countryAbbrev)
        return countries[index]
    }
}