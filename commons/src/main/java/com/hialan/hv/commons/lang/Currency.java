package com.hialan.hv.commons.lang;

public enum Currency {
    CNY("CNY", "China, Yuan Renminbi", "", ""),
    USD("USD", "United States of America, Dollars", "", ""),
    HKD("HKD", "Hong Kong, Dollars", "", ""),
    EUR("EUR", "Euro Member Countries, Euro", "", ""),
    MYR("MYR", "Malaysia, Ringgits", "", "");

    /*
    GBP("GBP", "United Kingdom, Pounds", "", ""),
    AED("AED", "United Arab Emirates, Dirhams", "", ""),
    AFN("AFN", "Afghanistan, Afghanis", "", ""),
    ALL("ALL", "Albania, Leke", "", ""),
    AMD("AMD", "Armenia, Drams", "", ""),
    ANG("ANG", "Netherlands Antilles, Guilders (also called Florins)", "", ""),
    AOA("AOA", "Angola, Kwanza", "", ""),
    ARS("ARS", "Argentina, Pesos", "", ""),
    AUD("AUD", "Australia, Dollars", "", ""),
    AWG("AWG", "Aruba, Guilders (also called Florins)", "", ""),
    AZN("AZN", "Azerbaijan, New Manats", "", ""),
    BAM("BAM", "Bosnia and Herzegovina, Convertible Marka", "", ""),
    BBD("BBD", "Barbados, Dollars", "", ""),
    BDT("BDT", "Bangladesh, Taka", "", ""),
    BGN("BGN", "Bulgaria, Leva", "", ""),
    BHD("BHD", "Bahrain, Dinars", "", ""),
    BIF("BIF", "Burundi, Francs", "", ""),
    BMD("BMD", "Bermuda, Dollars", "", ""),
    BND("BND", "Brunei Darussalam, Dollars", "", ""),
    BOB("BOB", "Bolivia, Bolivianos", "", ""),
    BRL("BRL", "Brazil, Brazil Real", "", ""),
    BSD("BSD", "Bahamas, Dollars", "", ""),
    BTN("BTN", "Bhutan, Ngultrum", "", ""),
    BWP("BWP", "Botswana, Pulas", "", ""),
    BYR("BYR", "Belarus, Rubles", "", ""),
    BZD("BZD", "Belize, Dollars", "", ""),
    CAD("CAD", "Canada, Dollars", "", ""),
    CDF("CDF", "Congo/Kinshasa, Congolese Francs", "", ""),
    CHF("CHF", "Switzerland, Francs", "", ""),
    CLP("CLP", "Chile, Pesos", "", ""),

    COP("COP", "Colombia, Pesos", "", ""),
    CRC("CRC", "Costa Rica, Colones", "", ""),
    CUP("CUP", "Cuba, Pesos", "", ""),
    CVE("CVE", "Cape Verde, Escudos", "", ""),
    CZK("CZK", "Czech Republic, Koruny", "", ""),
    DJF("DJF", "Djibouti, Francs", "", ""),
    DKK("DKK", "Denmark, Kroner", "", ""),
    DOP("DOP", "Dominican Republic, Pesos", "", ""),
    DZD("DZD", "Algeria, Algeria Dinars", "", ""),
    EEK("EEK", "Estonia, Krooni", "", ""),
    EGP("EGP", "Egypt, Pounds", "", ""),
    ERN("ERN", "Eritrea, Nakfa", "", ""),
    ETB("ETB", "Ethiopia, Birr", "", ""),
    EUR("EUR", "Euro Member Countries, Euro", "", ""),
    FJD("FJD", "Fiji, Dollars", "", ""),
    FKP("FKP", "Falkland Islands (Malvinas), Pounds", "", ""),

    GEL("GEL", "Georgia, Lari", "", ""),
    GGP("GGP", "Guernsey, Pounds", "", ""),
    GHS("GHS", "Ghana, Cedis", "", ""),
    GIP("GIP", "Gibraltar, Pounds", "", ""),
    GMD("GMD", "Gambia, Dalasi", "", ""),
    GNF("GNF", "Guinea, Francs", "", ""),
    GTQ("GTQ", "Guatemala, Quetzales", "", ""),
    GYD("GYD", "Guyana, Dollars", "", ""),
    HKD("HKD", "Hong Kong, Dollars", "", ""),
    HNL("HNL", "Honduras, Lempiras", "", ""),
    HRK("HRK", "Croatia, Kuna", "", ""),
    HTG("HTG", "Haiti, Gourdes", "", ""),
    HUF("HUF", "Hungary, Forint", "", ""),
    IDR("IDR", "Indonesia, Rupiahs", "", ""),
    ILS("ILS", "Israel, New Shekels", "", ""),
    IMP("IMP", "Isle of Man, Pounds", "", ""),
    INR("INR", "India, Rupees", "", ""),
    IQD("IQD", "Iraq, Dinars", "", ""),
    IRR("IRR", "Iran, Rials", "", ""),
    ISK("ISK", "Iceland, Kronur", "", ""),
    JEP("JEP", "Jersey, Pounds", "", ""),
    JMD("JMD", "Jamaica, Dollars", "", ""),
    JOD("JOD", "Jordan, Dinars", "", ""),
    JPY("JPY", "Japan, Yen", "", ""),
    KES("KES", "Kenya, Shillings", "", ""),
    KGS("KGS", "Kyrgyzstan, Soms", "", ""),
    KHR("KHR", "Cambodia, Riels", "", ""),
    KMF("KMF", "Comoros, Francs", "", ""),
    KPW("KPW", "Korea (North), Won", "", ""),
    KRW("KRW", "Korea (South), Won", "", ""),
    KWD("KWD", "Kuwait, Dinars", "", ""),
    KYD("KYD", "Cayman Islands, Dollars", "", ""),
    KZT("KZT", "Kazakhstan, Tenge", "", ""),
    LAK("LAK", "Laos, Kips", "", ""),
    LBP("LBP", "Lebanon, Pounds", "", ""),
    LKR("LKR", "Sri Lanka, Rupees", "", ""),
    LRD("LRD", "Liberia, Dollars", "", ""),
    LSL("LSL", "Lesotho, Maloti", "", ""),
    LTL("LTL", "Lithuania, Litai", "", ""),
    LVL("LVL", "Latvia, Lati", "", ""),
    LYD("LYD", "Libya, Dinars", "", ""),
    MAD("MAD", "Morocco, Dirhams", "", ""),
    MDL("MDL", "Moldova, Lei", "", ""),
    MGA("MGA", "Madagascar, Ariary", "", ""),
    MKD("MKD", "Macedonia, Denars", "", ""),
    MMK("MMK", "Myanmar (Burma), Kyats", "", ""),
    MNT("MNT", "Mongolia, Tugriks", "", ""),
    MOP("MOP", "Macau, Patacas", "", ""),
    MRO("MRO", "Mauritania, Ouguiyas", "", ""),
    MUR("MUR", "Mauritius, Rupees", "", ""),
    MVR("MVR", "Maldives (Maldive Islands), Rufiyaa", "", ""),
    MWK("MWK", "Malawi, Kwachas", "", ""),
    MXN("MXN", "Mexico, Pesos", "", ""),
    MYR("MYR", "Malaysia, Ringgits", "", ""),
    MZN("MZN", "Mozambique, Meticais", "", ""),
    NAD("NAD", "Namibia, Dollars", "", ""),
    NGN("NGN", "Nigeria, Nairas", "", ""),
    NIO("NIO", "Nicaragua, Cordobas", "", ""),
    NOK("NOK", "Norway, Krone", "", ""),
    NPR("NPR", "Nepal, Nepal Rupees", "", ""),
    NTD("NTD", "Taiwan, New Taiwan dollar", "", ""),
    NZD("NZD", "New Zealand, Dollars", "", ""),
    OMR("OMR", "Oman, Rials", "", ""),
    PAB("PAB", "Panama, Balboa", "", ""),
    PEN("PEN", "Peru, Nuevos Soles", "", ""),
    PGK("PGK", "Papua New Guinea, Kina", "", ""),
    PHP("PHP", "Philippines, Pesos", "", ""),
    PKR("PKR", "Pakistan, Rupees", "", ""),
    PLN("PLN", "Poland, Zlotych", "", ""),
    PYG("PYG", "Paraguay, Guarani", "", ""),
    QAR("QAR", "Qatar, Rials", "", ""),
    RON("RON", "Romania, New Lei", "", ""),
    RSD("RSD", "Serbia, Dinars", "", ""),
    RUB("RUB", "Russia, Rubles", "", ""),
    RWF("RWF", "Rwanda, Rwanda Francs", "", ""),
    SAR("SAR", "Saudi Arabia, Riyals", "", ""),
    SBD("SBD", "Solomon Islands, Dollars", "", ""),
    SCR("SCR", "Seychelles, Rupees", "", ""),
    SDG("SDG", "Sudan, Pounds", "", ""),
    SEK("SEK", "Sweden, Kronor", "", ""),
    SGD("SGD", "Singapore, Dollars", "", ""),
    SHP("SHP", "Saint Helena, Pounds", "", ""),
    SKK("SKK", "Slovakia, Koruny", "", ""),
    SLL("SLL", "Sierra Leone, Leones", "", ""),
    SOS("SOS", "Somalia, Shillings", "", ""),
    SPL("SPL", "Seborga, Luigini", "", ""),
    SRD("SRD", "Suriname, Dollars", "", ""),
    STD("STD", "S?o Tome and Principe, Dobras", "", ""),
    SVC("SVC", "El Salvador, Colones", "", ""),
    SYP("SYP", "Syria, Pounds", "", ""),
    SZL("SZL", "Swaziland, Emalangeni", "", ""),
    THB("THB", "Thailand, Baht", "", ""),
    TJS("TJS", "Tajikistan, Somoni", "", ""),
    TMM("TMM", "Turkmenistan, Manats", "", ""),
    TND("TND", "Tunisia, Dinars", "", ""),
    TOP("TOP", "Tonga, Pa'anga", "", ""),
    TRY("TRY", "Turkey, New Lira", "", ""),
    TTD("TTD", "Trinidad and Tobago, Dollars", "", ""),
    TVD("TVD", "Tuvalu, Tuvalu Dollars", "", ""),
    TZS("TZS", "Tanzania, Shillings", "", ""),
    UAH("UAH", "Ukraine, Hryvnia", "", ""),
    UGX("UGX", "Uganda, Shillings", "", ""),

    UYU("UYU", "Uruguay, Pesos", "", ""),
    UZS("UZS", "Uzbekistan, Sums", "", ""),
    VEF("VEF", "Venezuela, Bolivares Fuertes", "", ""),
    VND("VND", "Viet Nam, Dong", "", ""),
    VUV("VUV", "Vanuatu, Vatu", "", ""),
    WST("WST", "Samoa, Tala", "", ""),
    XAF("XAF", "Communauté Financière Africaine BEAC, Francs", "", ""),
    XAG("XAG", "Silver, Ounces", "", ""),
    XAU("XAU", "Gold, Ounces", "", ""),
    XCD("XCD", "East Caribbean Dollars", "", ""),
    XDR("XDR", "International Monetary Fund (IMF) Special Drawing Rights", "", ""),
    XOF("XOF", "Communauté Financière Africaine BCEAO, Francs", "", ""),
    XPD("XPD", "Palladium Ounces", "", ""),
    XPF("XPF", "Comptoirs Fran?ais du Pacifique Francs", "", ""),
    XPT("XPT", "Platinum, Ounces", "", ""),
    YER("YER", "Yemen, Rials", "", ""),
    ZAR("ZAR", "South Africa, Rand", "", ""),
    ZMK("ZMK", "Zambia, Kwacha", "", ""),
    ZWD("ZWD", "Zimbabwe, Zimbabwe Dollars", "", "");*/

    private String code;

    private String description;

    private String name;

    private String suffix;

    private Currency(String code, String description, String name, String suffix) {
        this.code = code;
        this.description = description;
        this.suffix = suffix;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getName() {
        return name;
    }

    public static Currency fromCode(String code) {
        for (Currency currency : Currency.values()) {
            if (currency.code.equals(code)) {
                return currency;
            }
        }
        return null;
    }
}

