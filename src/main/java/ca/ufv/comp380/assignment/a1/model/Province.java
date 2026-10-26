package ca.ufv.comp380.assignment.a1.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Geographic province/state/region enum representing all provinces across all supported countries.
 *
 * Encapsulates:
 * - provinceSvgMapId: SVG path ID matching the map SVG (e.g., "CA-AB", "US-CA")
 * - provinceName: Canonical human-readable name (e.g., "Alberta", "California")
 * - country: CountryConfig enumeration reference
 * - index: 0-based index matching graph row and SVG path position
 * - adjacencyList: Adjacency list constructed directly with neighboring Province enums
 */
public enum Province {

    // =========================================================================
    // Canada (13 provinces/regions)
    // =========================================================================
    CA_AB("CA-AB", "Alberta", "CANADA", 0),
    CA_BC("CA-BC", "British Columbia", "CANADA", 1),
    CA_MB("CA-MB", "Manitoba", "CANADA", 2),
    CA_NB("CA-NB", "New Brunswick", "CANADA", 3),
    CA_NL("CA-NL", "Newfoundland and Labrador", "CANADA", 4),
    CA_NS("CA-NS", "Nova Scotia", "CANADA", 5),
    CA_NT("CA-NT", "Northwest Territories", "CANADA", 6),
    CA_NU("CA-NU", "Nunavut", "CANADA", 7),
    CA_ON("CA-ON", "Ontario", "CANADA", 8),
    CA_PE("CA-PE", "Prince Edward Island", "CANADA", 9),
    CA_QC("CA-QC", "Quebec", "CANADA", 10),
    CA_SK("CA-SK", "Saskatchewan", "CANADA", 11),
    CA_YT("CA-YT", "Yukon", "CANADA", 12),

    // =========================================================================
    // China (34 provinces/regions)
    // =========================================================================
    CN_34("CN-34", "Anhui", "CHINA", 0),
    CN_11("CN-11", "Beijing", "CHINA", 1),
    CN_50("CN-50", "Chongqing", "CHINA", 2),
    CN_35("CN-35", "Fujian", "CHINA", 3),
    CN_44("CN-44", "Guangdong", "CHINA", 4),
    CN_62("CN-62", "Gansu", "CHINA", 5),
    CN_45("CN-45", "Guangxi Zhuang", "CHINA", 6),
    CN_52("CN-52", "Guizhou", "CHINA", 7),
    CN_46("CN-46", "Hainan", "CHINA", 8),
    CN_13("CN-13", "Hebei", "CHINA", 9),
    CN_41("CN-41", "Henan", "CHINA", 10),
    CN_91("CN-91", "Hong Kong", "CHINA", 11),
    CN_23("CN-23", "Heilongjiang", "CHINA", 12),
    CN_43("CN-43", "Hunan", "CHINA", 13),
    CN_42("CN-42", "Hubei", "CHINA", 14),
    CN_22("CN-22", "Jilin", "CHINA", 15),
    CN_32("CN-32", "Jiangsu", "CHINA", 16),
    CN_36("CN-36", "Jiangxi", "CHINA", 17),
    CN_21("CN-21", "Liaoning", "CHINA", 18),
    CN_92("CN-92", "Macau", "CHINA", 19),
    CN_15("CN-15", "Nei Mongol", "CHINA", 20),
    CN_64("CN-64", "Ningxia Hui", "CHINA", 21),
    CN_63("CN-63", "Quinghai", "CHINA", 22),
    CN_61("CN-61", "Shaanxi", "CHINA", 23),
    CN_51("CN-51", "Sichuan", "CHINA", 24),
    CN_37("CN-37", "Shandong", "CHINA", 25),
    CN_31("CN-31", "Shanghai", "CHINA", 26),
    CN_14("CN-14", "Shanxi", "CHINA", 27),
    CN_12("CN-12", "Tianjin", "CHINA", 28),
    CN_71("CN-71", "Taiwan", "CHINA", 29),
    CN_65("CN-65", "Xinjiang Uygur", "CHINA", 30),
    CN_54("CN-54", "Xizang (Tibet)", "CHINA", 31),
    CN_53("CN-53", "Yunnan", "CHINA", 32),
    CN_33("CN-33", "Zhejiang", "CHINA", 33),

    // =========================================================================
    // India (36 provinces/regions)
    // =========================================================================
    IN_AN("IN-AN", "Andaman and Nicobar Islands", "INDIA", 0),
    IN_AP("IN-AP", "Andhra Pradesh", "INDIA", 1),
    IN_AR("IN-AR", "Arunachal Pradesh", "INDIA", 2),
    IN_AS("IN-AS", "Assam", "INDIA", 3),
    IN_BR("IN-BR", "Bihar", "INDIA", 4),
    IN_CH("IN-CH", "Chandigarh", "INDIA", 5),
    IN_CT("IN-CT", "Chhattisgarh", "INDIA", 6),
    IN_DD("IN-DD", "Daman and Diu", "INDIA", 7),
    IN_DL("IN-DL", "Delhi", "INDIA", 8),
    IN_DN("IN-DN", "Dadra and Nagar Haveli", "INDIA", 9),
    IN_GA("IN-GA", "Goa", "INDIA", 10),
    IN_GJ("IN-GJ", "Gujarat", "INDIA", 11),
    IN_HP("IN-HP", "Himachal Pradesh", "INDIA", 12),
    IN_HR("IN-HR", "Haryana", "INDIA", 13),
    IN_JH("IN-JH", "Jharkhand", "INDIA", 14),
    IN_JK("IN-JK", "Jammu and Kashmir", "INDIA", 15),
    IN_KA("IN-KA", "Karnataka", "INDIA", 16),
    IN_KL("IN-KL", "Kerala", "INDIA", 17),
    IN_LD("IN-LD", "Lakshadweep", "INDIA", 18),
    IN_MH("IN-MH", "Maharashtra", "INDIA", 19),
    IN_ML("IN-ML", "Meghalaya", "INDIA", 20),
    IN_MN("IN-MN", "Manipur", "INDIA", 21),
    IN_MP("IN-MP", "Madhya Pradesh", "INDIA", 22),
    IN_MZ("IN-MZ", "Mizoram", "INDIA", 23),
    IN_NL("IN-NL", "Nagaland", "INDIA", 24),
    IN_OR("IN-OR", "Odisha", "INDIA", 25),
    IN_PB("IN-PB", "Punjab", "INDIA", 26),
    IN_PY("IN-PY", "Puducherry", "INDIA", 27),
    IN_RJ("IN-RJ", "Rajasthan", "INDIA", 28),
    IN_SK("IN-SK", "Sikkim", "INDIA", 29),
    IN_TG("IN-TG", "Telangana", "INDIA", 30),
    IN_TN("IN-TN", "Tamil Nadu", "INDIA", 31),
    IN_TR("IN-TR", "Tripura", "INDIA", 32),
    IN_UP("IN-UP", "Uttar Pradesh", "INDIA", 33),
    IN_UT("IN-UT", "Uttarakhand", "INDIA", 34),
    IN_WB("IN-WB", "West Bengal", "INDIA", 35),

    // =========================================================================
    // Russia (83 provinces/regions)
    // =========================================================================
    RU_AD("RU-AD", "Республика Адыгея", "RUSSIA", 0),
    RU_ALT("RU-ALT", "Республика Алтай", "RUSSIA", 1),
    RU_AMU("RU-AMU", "Амурская область", "RUSSIA", 2),
    RU_ARK("RU-ARK", "Архангельская область", "RUSSIA", 3),
    RU_AST("RU-AST", "Астраханская область", "RUSSIA", 4),
    RU_BA("RU-BA", "Республика Башкортостан", "RUSSIA", 5),
    RU_BEL("RU-BEL", "Белгородская область", "RUSSIA", 6),
    RU_BRY("RU-BRY", "Брянская область", "RUSSIA", 7),
    RU_BU("RU-BU", "Республика Бурятия", "RUSSIA", 8),
    RU_CE("RU-CE", "Чеченская республика", "RUSSIA", 9),
    RU_CHE("RU-CHE", "Челябинская область", "RUSSIA", 10),
    RU_CHU("RU-CHU", "Чукотский автономный округ", "RUSSIA", 11),
    RU_CU("RU-CU", "Чувашская республика", "RUSSIA", 12),
    RU_DA("RU-DA", "Республика Дагестан", "RUSSIA", 13),
    RU_AL("RU-AL", "Республика Алтай", "RUSSIA", 14),
    RU_IN("RU-IN", "Республика Ингушетия", "RUSSIA", 15),
    RU_IRK("RU-IRK", "Иркутская область", "RUSSIA", 16),
    RU_IVA("RU-IVA", "Ивановская область", "RUSSIA", 17),
    RU_KB("RU-KB", "Кабардино-Балкарская республика", "RUSSIA", 18),
    RU_KC("RU-KC", "Карачаево-Черкесская республика", "RUSSIA", 19),
    RU_KDA("RU-KDA", "Краснодарский край", "RUSSIA", 20),
    RU_KEM("RU-KEM", "Кемеровская область", "RUSSIA", 21),
    RU_KLU("RU-KLU", "Калужская область", "RUSSIA", 22),
    RU_KHA("RU-KHA", "Хабаровский край", "RUSSIA", 23),
    RU_KR("RU-KR", "Республика Карелия", "RUSSIA", 24),
    RU_KK("RU-KK", "Республика Хакасия", "RUSSIA", 25),
    RU_KL("RU-KL", "Республика Калмыкия", "RUSSIA", 26),
    RU_KHM("RU-KHM", "Ханты-Мансийский автономный округ", "RUSSIA", 27),
    RU_KGD("RU-KGD", "Калининградская область", "RUSSIA", 28),
    RU_KO("RU-KO", "Республика Коми", "RUSSIA", 29),
    RU_KAM("RU-KAM", "Камчатский край", "RUSSIA", 30),
    RU_KRS("RU-KRS", "Курская область", "RUSSIA", 31),
    RU_KOS("RU-KOS", "Костромская область", "RUSSIA", 32),
    RU_KGN("RU-KGN", "Курганская область", "RUSSIA", 33),
    RU_KIR("RU-KIR", "Кировская область", "RUSSIA", 34),
    RU_KYA("RU-KYA", "Красноярский край", "RUSSIA", 35),
    RU_LEN("RU-LEN", "Ленинградская область", "RUSSIA", 36),
    RU_LIP("RU-LIP", "Липецкая область", "RUSSIA", 37),
    RU_MOW("RU-MOW", "Москва", "RUSSIA", 38),
    RU_ME("RU-ME", "Республика Марий Эл", "RUSSIA", 39),
    RU_MAG("RU-MAG", "Магаданская область", "RUSSIA", 40),
    RU_MUR("RU-MUR", "Мурманская область", "RUSSIA", 41),
    RU_MO("RU-MO", "Республика Мордовия", "RUSSIA", 42),
    RU_MOS("RU-MOS", "Московская область", "RUSSIA", 43),
    RU_NGR("RU-NGR", "Новгородская область", "RUSSIA", 44),
    RU_NEN("RU-NEN", "Ненецкий автономный округ", "RUSSIA", 45),
    RU_SE("RU-SE", "Республика Северная Осетия — Алания", "RUSSIA", 46),
    RU_NVS("RU-NVS", "Новосибирская область", "RUSSIA", 47),
    RU_NIZ("RU-NIZ", "Нижегородская область", "RUSSIA", 48),
    RU_ORE("RU-ORE", "Оренбургская область", "RUSSIA", 49),
    RU_ORL("RU-ORL", "Орловская область", "RUSSIA", 50),
    RU_OMS("RU-OMS", "Омская область", "RUSSIA", 51),
    RU_PER("RU-PER", "Пермский край'", "RUSSIA", 52),
    RU_PRI("RU-PRI", "Приморский край", "RUSSIA", 53),
    RU_PSK("RU-PSK", "Псковская область", "RUSSIA", 54),
    RU_PNZ("RU-PNZ", "Пензенская область", "RUSSIA", 55),
    RU_ROS("RU-ROS", "Ростовская область", "RUSSIA", 56),
    RU_RYA("RU-RYA", "Рязанская область", "RUSSIA", 57),
    RU_SAM("RU-SAM", "Самарская область", "RUSSIA", 58),
    RU_SA("RU-SA", "Республика Саха (Якутия)", "RUSSIA", 59),
    RU_SAK("RU-SAK", "Сахалинская область", "RUSSIA", 60),
    RU_SMO("RU-SMO", "Смоленская область", "RUSSIA", 61),
    RU_SPE("RU-SPE", "Санкт-Петербург", "RUSSIA", 62),
    RU_SAR("RU-SAR", "Саратовская область", "RUSSIA", 63),
    RU_STA("RU-STA", "Ставропольский край", "RUSSIA", 64),
    RU_SVE("RU-SVE", "Свердловская область", "RUSSIA", 65),
    RU_TAM("RU-TAM", "Тамбовская область", "RUSSIA", 66),
    RU_TOM("RU-TOM", "Томская область", "RUSSIA", 67),
    RU_TUL("RU-TUL", "Тульская область", "RUSSIA", 68),
    RU_TA("RU-TA", "Республика Татарстан", "RUSSIA", 69),
    RU_TY("RU-TY", "Республика Тыва", "RUSSIA", 70),
    RU_TVE("RU-TVE", "Тверская область", "RUSSIA", 71),
    RU_TYU("RU-TYU", "Тюменская область", "RUSSIA", 72),
    RU_UD("RU-UD", "Удмуртская республика", "RUSSIA", 73),
    RU_ULY("RU-ULY", "Ульяновская область", "RUSSIA", 74),
    RU_VGG("RU-VGG", "Волгоградская область", "RUSSIA", 75),
    RU_VLA("RU-VLA", "Владимирская область", "RUSSIA", 76),
    RU_YAN("RU-YAN", "Ямало-Ненецкий автономный округ", "RUSSIA", 77),
    RU_VLG("RU-VLG", "Вологодская область", "RUSSIA", 78),
    RU_VOR("RU-VOR", "Воронежская область", "RUSSIA", 79),
    RU_YAR("RU-YAR", "Ярославская область", "RUSSIA", 80),
    RU_YEV("RU-YEV", "Еврейская автономная область", "RUSSIA", 81),
    RU_ZAB("RU-ZAB", "Забайкальский край", "RUSSIA", 82),

    // =========================================================================
    // United Kingdom (16 provinces/regions)
    // =========================================================================
    GB_UKC("GB-UKC", "North East", "UNITED_KINGDOM", 0),
    GB_UKD("GB-UKD", "North West", "UNITED_KINGDOM", 1),
    GB_UKE("GB-UKE", "Yorkshire and the Humber", "UNITED_KINGDOM", 2),
    GB_UKF("GB-UKF", "East Midlands", "UNITED_KINGDOM", 3),
    GB_UKG("GB-UKG", "West Midlands", "UNITED_KINGDOM", 4),
    GB_UKH("GB-UKH", "East of England", "UNITED_KINGDOM", 5),
    GB_UKI("GB-UKI", "Greater London", "UNITED_KINGDOM", 6),
    GB_UKJ("GB-UKJ", "South East", "UNITED_KINGDOM", 7),
    GB_UKK("GB-UKK", "South West", "UNITED_KINGDOM", 8),
    GB_UKL("GB-UKL", "Wales", "UNITED_KINGDOM", 9),
    GB_UKM("GB-UKM", "Scotland", "UNITED_KINGDOM", 10),
    GB_UKN("GB-UKN", "Northern Ireland", "UNITED_KINGDOM", 11),
    GB_GG("GG", "Guernsey", "UNITED_KINGDOM", 12),
    GB_JE("JE", "Jersey", "UNITED_KINGDOM", 13),
    GB_IM("IM", "Isle of Man", "UNITED_KINGDOM", 14),
    GB_IE("IE", "Ireland", "UNITED_KINGDOM", 15),

    // =========================================================================
    // USA (51 provinces/regions)
    // =========================================================================
    US_AK("US-AK", "Alaska", "USA", 0),
    US_AL("US-AL", "Alabama", "USA", 1),
    US_AR("US-AR", "Arkansas", "USA", 2),
    US_AZ("US-AZ", "Arizona", "USA", 3),
    US_CA("US-CA", "California", "USA", 4),
    US_CO("US-CO", "Colorado", "USA", 5),
    US_CT("US-CT", "Connecticut", "USA", 6),
    US_DC("US-DC", "Washington, DC", "USA", 7),
    US_DE("US-DE", "Delaware", "USA", 8),
    US_FL("US-FL", "Florida", "USA", 9),
    US_GA("US-GA", "Georgia", "USA", 10),
    US_HI("US-HI", "Hawaii", "USA", 11),
    US_IA("US-IA", "Iowa", "USA", 12),
    US_ID("US-ID", "Idaho", "USA", 13),
    US_IL("US-IL", "Illinois", "USA", 14),
    US_IN("US-IN", "Indiana", "USA", 15),
    US_KS("US-KS", "Kansas", "USA", 16),
    US_KY("US-KY", "Kentucky", "USA", 17),
    US_LA("US-LA", "Louisiana", "USA", 18),
    US_MA("US-MA", "Massachusetts", "USA", 19),
    US_MD("US-MD", "Maryland", "USA", 20),
    US_ME("US-ME", "Maine", "USA", 21),
    US_MI("US-MI", "Michigan", "USA", 22),
    US_MN("US-MN", "Minnesota", "USA", 23),
    US_MO("US-MO", "Missouri", "USA", 24),
    US_MS("US-MS", "Mississippi", "USA", 25),
    US_MT("US-MT", "Montana", "USA", 26),
    US_NC("US-NC", "North Carolina", "USA", 27),
    US_ND("US-ND", "North Dakota", "USA", 28),
    US_NE("US-NE", "Nebraska", "USA", 29),
    US_NH("US-NH", "New Hampshire", "USA", 30),
    US_NJ("US-NJ", "New Jersey", "USA", 31),
    US_NM("US-NM", "New Mexico", "USA", 32),
    US_NV("US-NV", "Nevada", "USA", 33),
    US_NY("US-NY", "New York", "USA", 34),
    US_OH("US-OH", "Ohio", "USA", 35),
    US_OK("US-OK", "Oklahoma", "USA", 36),
    US_OR("US-OR", "Oregon", "USA", 37),
    US_PA("US-PA", "Pennsylvania", "USA", 38),
    US_RI("US-RI", "Rhode Island", "USA", 39),
    US_SC("US-SC", "South Carolina", "USA", 40),
    US_SD("US-SD", "South Dakota", "USA", 41),
    US_TN("US-TN", "Tennessee", "USA", 42),
    US_TX("US-TX", "Texas", "USA", 43),
    US_UT("US-UT", "Utah", "USA", 44),
    US_VA("US-VA", "Virginia", "USA", 45),
    US_VT("US-VT", "Vermont", "USA", 46),
    US_WA("US-WA", "Washington", "USA", 47),
    US_WI("US-WI", "Wisconsin", "USA", 48),
    US_WV("US-WV", "West Virginia", "USA", 49),
    US_WY("US-WY", "Wyoming", "USA", 50);

    // Pre-grouped unmodifiable lists of provinces per country
    public static final List<Province> CANADA_PROVINCES;
    public static final List<Province> CHINA_PROVINCES;
    public static final List<Province> INDIA_PROVINCES;
    public static final List<Province> RUSSIA_PROVINCES;
    public static final List<Province> UNITED_KINGDOM_PROVINCES;
    public static final List<Province> USA_PROVINCES;

    private final String provinceSvgMapId;
    private final String provinceName;
    private final String countryCode;
    private final int index;
    private List<Province> adjacentProvinces = Collections.emptyList();

    Province(String provinceSvgMapId, String provinceName, String countryCode, int index) {
        this.provinceSvgMapId = provinceSvgMapId;
        this.provinceName = provinceName;
        this.countryCode = countryCode;
        this.index = index;
    }

    /**
     * Returns the SVG map ID matching the SVG path element (e.g. "CA-AB", "US-CA").
     */
    public String getProvinceSvgMapId() {
        return provinceSvgMapId;
    }

    /**
     * Alias for getProvinceSvgMapId().
     */
    public String getId() {
        return provinceSvgMapId;
    }

    /**
     * Returns the canonical province/state name (e.g. "Alberta", "California").
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * Alias for getProvinceName().
     */
    public String getName() {
        return provinceName;
    }

    /**
     * Returns the country configuration this province belongs to.
     */
    public CountryConfig getCountry() {
        return CountryConfig.valueOf(countryCode);
    }

    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Returns the 0-based index of this province within its country.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns this province's adjacency list as an unmodifiable list of neighboring Province enums.
     */
    public List<Province> getAdjacencyList() {
        return adjacentProvinces;
    }

    /**
     * Alias for getAdjacencyList().
     */
    public List<Province> getAdjacentProvinces() {
        return adjacentProvinces;
    }

    /**
     * Alias for getAdjacencyList().
     */
    public List<Province> getNeighbors() {
        return adjacentProvinces;
    }

    /**
     * Returns the array of neighboring province indices within the country graph.
     */
    public int[] getNeighborIndices() {
        int[] indices = new int[adjacentProvinces.size()];
        for (int i = 0; i < adjacentProvinces.size(); i++) {
            indices[i] = adjacentProvinces.get(i).getIndex();
        }
        return indices;
    }

    /**
     * Returns the list of neighboring province SVG IDs.
     */
    public List<String> getNeighborIds() {
        List<String> ids = new ArrayList<>(adjacentProvinces.size());
        for (Province p : adjacentProvinces) {
            ids.add(p.provinceSvgMapId);
        }
        return Collections.unmodifiableList(ids);
    }

    /**
     * Alias for getNeighborIds().
     */
    public List<String> getAdjacentIds() {
        return getNeighborIds();
    }

    /**
     * Returns the list of neighboring province names.
     */
    public List<String> getNeighborNames() {
        List<String> names = new ArrayList<>(adjacentProvinces.size());
        for (Province p : adjacentProvinces) {
            names.add(p.provinceName);
        }
        return Collections.unmodifiableList(names);
    }

    /**
     * Alias for getNeighborNames().
     */
    public List<String> getAdjacentNames() {
        return getNeighborNames();
    }

    /**
     * Returns the degree (number of neighbors) of this province.
     */
    public int getDegree() {
        return adjacentProvinces.size();
    }

    /**
     * Checks if this province is adjacent to another Province.
     */
    public boolean isAdjacentTo(Province other) {
        return other != null && adjacentProvinces.contains(other);
    }

    /**
     * Checks if this province is adjacent to a province with the given SVG map ID.
     */
    public boolean isAdjacentTo(String svgId) {
        if (svgId == null) return false;
        for (Province p : adjacentProvinces) {
            if (svgId.equalsIgnoreCase(p.provinceSvgMapId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if this province is adjacent to a province with the given index in the country.
     */
    public boolean isAdjacentTo(int neighborIndex) {
        for (Province p : adjacentProvinces) {
            if (p.index == neighborIndex) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return provinceSvgMapId + " (" + provinceName + ")";
    }

    private static final Map<String, Province> BY_SVG_ID = new HashMap<>();
    private static final Map<String, Province> BY_NAME = new HashMap<>();

    /**
     * Looks up a Province by its SVG map ID (e.g. "CA-AB", "US-CA").
     */
    public static Province fromSvgMapId(String svgMapId) {
        return svgMapId != null ? BY_SVG_ID.get(svgMapId.toUpperCase()) : null;
    }

    /**
     * Looks up a Province by its canonical name.
     */
    public static Province fromName(String name) {
        return name != null ? BY_NAME.get(name.toLowerCase()) : null;
    }

    /**
     * Returns an unmodifiable list of all provinces belonging to the given country.
     */
    public static List<Province> getProvincesByCountry(CountryConfig country) {
        if (country == null) return Collections.emptyList();
        return switch (country) {
            case CANADA -> CANADA_PROVINCES;
            case CHINA -> CHINA_PROVINCES;
            case INDIA -> INDIA_PROVINCES;
            case RUSSIA -> RUSSIA_PROVINCES;
            case UNITED_KINGDOM -> UNITED_KINGDOM_PROVINCES;
            case USA -> USA_PROVINCES;
        };
    }

    private static void link(Province province, Province... neighbors) {
        province.adjacentProvinces = (neighbors != null && neighbors.length > 0)
                ? List.of(neighbors)
                : Collections.emptyList();
    }

    static {
        List<Province> ca = new ArrayList<>(13);
        List<Province> cn = new ArrayList<>(34);
        List<Province> in = new ArrayList<>(36);
        List<Province> ru = new ArrayList<>(83);
        List<Province> gb = new ArrayList<>(16);
        List<Province> us = new ArrayList<>(51);

        for (Province p : values()) {
            BY_SVG_ID.put(p.provinceSvgMapId.toUpperCase(), p);
            BY_NAME.put(p.provinceName.toLowerCase(), p);
            switch (p.countryCode) {
                case "CANADA" -> ca.add(p);
                case "CHINA" -> cn.add(p);
                case "INDIA" -> in.add(p);
                case "RUSSIA" -> ru.add(p);
                case "UNITED_KINGDOM" -> gb.add(p);
                case "USA" -> us.add(p);
            }
        }

        CANADA_PROVINCES = Collections.unmodifiableList(ca);
        CHINA_PROVINCES = Collections.unmodifiableList(cn);
        INDIA_PROVINCES = Collections.unmodifiableList(in);
        RUSSIA_PROVINCES = Collections.unmodifiableList(ru);
        UNITED_KINGDOM_PROVINCES = Collections.unmodifiableList(gb);
        USA_PROVINCES = Collections.unmodifiableList(us);

        // ---------------------------------------------------------------------
        // Canada Adjacency
        // ---------------------------------------------------------------------
        link(CA_AB, CA_BC, CA_NT, CA_SK);
        link(CA_BC, CA_AB, CA_NT, CA_YT);
        link(CA_MB, CA_NU, CA_ON, CA_SK);
        link(CA_NB, CA_NS, CA_PE, CA_QC);
        link(CA_NL, CA_NU, CA_QC);
        link(CA_NS, CA_NB, CA_PE);
        link(CA_NT, CA_AB, CA_BC, CA_NU, CA_SK, CA_YT);
        link(CA_NU, CA_MB, CA_NL, CA_NT, CA_ON, CA_QC);
        link(CA_ON, CA_MB, CA_NU, CA_QC);
        link(CA_PE, CA_NB, CA_NS);
        link(CA_QC, CA_NB, CA_NL, CA_NU, CA_ON);
        link(CA_SK, CA_AB, CA_MB, CA_NT);
        link(CA_YT, CA_BC, CA_NT);

        // ---------------------------------------------------------------------
        // China Adjacency
        // ---------------------------------------------------------------------
        link(CN_34, CN_41, CN_42, CN_32, CN_36, CN_37, CN_33);
        link(CN_11, CN_13, CN_12);
        link(CN_50, CN_52, CN_43, CN_42, CN_61, CN_51);
        link(CN_35, CN_44, CN_36, CN_71, CN_33);
        link(CN_44, CN_35, CN_45, CN_91, CN_43, CN_36, CN_92);
        link(CN_62, CN_15, CN_64, CN_63, CN_61, CN_51, CN_65);
        link(CN_45, CN_44, CN_52, CN_43, CN_53);
        link(CN_52, CN_50, CN_45, CN_43, CN_51, CN_53);
        link(CN_46);
        link(CN_13, CN_11, CN_41, CN_21, CN_15, CN_37, CN_14, CN_12);
        link(CN_41, CN_34, CN_13, CN_42, CN_32, CN_61, CN_37, CN_14);
        link(CN_91, CN_44);
        link(CN_23, CN_22, CN_15);
        link(CN_43, CN_50, CN_44, CN_45, CN_52, CN_42, CN_36);
        link(CN_42, CN_34, CN_50, CN_41, CN_43, CN_36, CN_61);
        link(CN_22, CN_23, CN_21, CN_15);
        link(CN_32, CN_34, CN_41, CN_37, CN_31, CN_33);
        link(CN_36, CN_34, CN_35, CN_44, CN_43, CN_42, CN_33);
        link(CN_21, CN_13, CN_22, CN_15);
        link(CN_92, CN_44);
        link(CN_15, CN_62, CN_13, CN_23, CN_22, CN_21, CN_64, CN_61, CN_14);
        link(CN_64, CN_62, CN_15, CN_61);
        link(CN_63, CN_62, CN_51, CN_65, CN_54);
        link(CN_61, CN_50, CN_62, CN_41, CN_42, CN_15, CN_64, CN_51, CN_14);
        link(CN_51, CN_50, CN_62, CN_52, CN_63, CN_61, CN_54, CN_53);
        link(CN_37, CN_34, CN_13, CN_41, CN_32);
        link(CN_31, CN_32, CN_33);
        link(CN_14, CN_13, CN_41, CN_15, CN_61);
        link(CN_12, CN_11, CN_13);
        link(CN_71, CN_35);
        link(CN_65, CN_62, CN_63, CN_54);
        link(CN_54, CN_63, CN_51, CN_65, CN_53);
        link(CN_53, CN_45, CN_52, CN_51, CN_54);
        link(CN_33, CN_34, CN_35, CN_32, CN_36, CN_31);

        // ---------------------------------------------------------------------
        // India Adjacency
        // ---------------------------------------------------------------------
        link(IN_AN);
        link(IN_AP, IN_KA, IN_OR, IN_PY, IN_TG, IN_TN);
        link(IN_AR, IN_AS, IN_NL);
        link(IN_AS, IN_AR, IN_ML, IN_MN, IN_MZ, IN_NL, IN_TR, IN_WB);
        link(IN_BR, IN_JH, IN_UP, IN_WB);
        link(IN_CH, IN_HP, IN_HR, IN_PB);
        link(IN_CT, IN_JH, IN_MH, IN_MP, IN_OR, IN_TG, IN_UP);
        link(IN_DD, IN_GJ);
        link(IN_DL, IN_HR, IN_UP);
        link(IN_DN, IN_GJ, IN_MH);
        link(IN_GA, IN_KA, IN_MH);
        link(IN_GJ, IN_DD, IN_DN, IN_MH, IN_MP, IN_RJ);
        link(IN_HP, IN_CH, IN_HR, IN_JK, IN_PB, IN_UP, IN_UT);
        link(IN_HR, IN_CH, IN_DL, IN_HP, IN_PB, IN_RJ, IN_UP, IN_UT);
        link(IN_JH, IN_BR, IN_CT, IN_OR, IN_UP, IN_WB);
        link(IN_JK, IN_HP, IN_PB);
        link(IN_KA, IN_AP, IN_GA, IN_KL, IN_MH, IN_TG, IN_TN);
        link(IN_KL, IN_KA, IN_TN);
        link(IN_LD);
        link(IN_MH, IN_CT, IN_DN, IN_GA, IN_GJ, IN_KA, IN_MP, IN_TG);
        link(IN_ML, IN_AS);
        link(IN_MN, IN_AS, IN_MZ, IN_NL);
        link(IN_MP, IN_CT, IN_GJ, IN_MH, IN_RJ, IN_UP);
        link(IN_MZ, IN_AS, IN_MN, IN_TR);
        link(IN_NL, IN_AR, IN_AS, IN_MN);
        link(IN_OR, IN_AP, IN_CT, IN_JH, IN_TG, IN_WB);
        link(IN_PB, IN_CH, IN_HP, IN_HR, IN_JK, IN_RJ);
        link(IN_PY, IN_AP, IN_TN);
        link(IN_RJ, IN_GJ, IN_HR, IN_MP, IN_PB, IN_UP);
        link(IN_SK, IN_WB);
        link(IN_TG, IN_AP, IN_CT, IN_KA, IN_MH, IN_OR);
        link(IN_TN, IN_AP, IN_KA, IN_KL, IN_PY);
        link(IN_TR, IN_AS, IN_MZ);
        link(IN_UP, IN_BR, IN_CT, IN_DL, IN_HP, IN_HR, IN_JH, IN_MP, IN_RJ, IN_UT);
        link(IN_UT, IN_HP, IN_HR, IN_UP);
        link(IN_WB, IN_AS, IN_BR, IN_JH, IN_OR, IN_SK);

        // ---------------------------------------------------------------------
        // Russia Adjacency
        // ---------------------------------------------------------------------
        link(RU_AD, RU_KDA);
        link(RU_ALT, RU_AL, RU_KEM, RU_NVS);
        link(RU_AMU, RU_KHA, RU_SA, RU_YEV, RU_ZAB);
        link(RU_ARK, RU_KR, RU_KO, RU_KIR, RU_NEN, RU_VLG);
        link(RU_AST, RU_KL, RU_VGG);
        link(RU_BA, RU_CHE, RU_ORE, RU_PER, RU_SVE, RU_TA, RU_UD);
        link(RU_BEL, RU_KRS, RU_VOR);
        link(RU_BRY, RU_KLU, RU_KRS, RU_ORL, RU_SMO);
        link(RU_BU, RU_IRK, RU_TY, RU_ZAB);
        link(RU_CE, RU_DA, RU_IN, RU_KB, RU_SE, RU_STA);
        link(RU_CHE, RU_BA, RU_KGN, RU_ORE, RU_SVE);
        link(RU_CHU, RU_KAM, RU_MAG, RU_SA);
        link(RU_CU, RU_ME, RU_MO, RU_NIZ, RU_TA, RU_ULY);
        link(RU_DA, RU_CE, RU_KL, RU_STA);
        link(RU_AL, RU_ALT, RU_KEM, RU_KK, RU_TY);
        link(RU_IN, RU_CE, RU_KB, RU_SE, RU_STA);
        link(RU_IRK, RU_BU, RU_KYA, RU_SA, RU_TY, RU_ZAB);
        link(RU_IVA, RU_KOS, RU_NIZ, RU_VLA, RU_YAR);
        link(RU_KB, RU_CE, RU_IN, RU_KC, RU_SE, RU_STA);
        link(RU_KC, RU_KB, RU_KDA, RU_STA);
        link(RU_KDA, RU_AD, RU_KC, RU_ROS, RU_STA);
        link(RU_KEM, RU_ALT, RU_AL, RU_KK, RU_KYA, RU_NVS, RU_TOM);
        link(RU_KLU, RU_BRY, RU_MOW, RU_MOS, RU_ORL, RU_SMO, RU_TUL);
        link(RU_KHA, RU_AMU, RU_MAG, RU_PRI, RU_SA, RU_SAK, RU_YEV);
        link(RU_KR, RU_ARK, RU_LEN, RU_MUR, RU_VLG);
        link(RU_KK, RU_AL, RU_KEM, RU_KYA, RU_TY);
        link(RU_KL, RU_AST, RU_DA, RU_ROS, RU_STA, RU_VGG);
        link(RU_KHM, RU_KO, RU_KYA, RU_SVE, RU_TOM, RU_TYU, RU_YAN);
        link(RU_KGD);
        link(RU_KO, RU_ARK, RU_KHM, RU_KIR, RU_NEN, RU_PER, RU_SVE, RU_YAN);
        link(RU_KAM, RU_CHU, RU_MAG);
        link(RU_KRS, RU_BEL, RU_BRY, RU_LIP, RU_ORL, RU_VOR);
        link(RU_KOS, RU_IVA, RU_KIR, RU_NIZ, RU_VLG, RU_YAR);
        link(RU_KGN, RU_CHE, RU_SVE, RU_TYU);
        link(RU_KIR, RU_ARK, RU_KO, RU_KOS, RU_ME, RU_NIZ, RU_PER, RU_TA, RU_UD, RU_VLG);
        link(RU_KYA, RU_IRK, RU_KEM, RU_KK, RU_KHM, RU_SA, RU_TOM, RU_TY, RU_YAN);
        link(RU_LEN, RU_KR, RU_NGR, RU_PSK, RU_SPE, RU_VLG);
        link(RU_LIP, RU_KRS, RU_ORL, RU_RYA, RU_TAM, RU_TUL, RU_VOR);
        link(RU_MOW, RU_KLU, RU_MOS);
        link(RU_ME, RU_CU, RU_KIR, RU_NIZ, RU_TA);
        link(RU_MAG, RU_CHU, RU_KHA, RU_KAM, RU_SA);
        link(RU_MUR, RU_KR);
        link(RU_MO, RU_CU, RU_NIZ, RU_PNZ, RU_RYA, RU_ULY);
        link(RU_MOS, RU_KLU, RU_MOW, RU_RYA, RU_SMO, RU_TUL, RU_TVE, RU_VLA, RU_YAR);
        link(RU_NGR, RU_LEN, RU_PSK, RU_TVE, RU_VLG);
        link(RU_NEN, RU_ARK, RU_KO, RU_YAN);
        link(RU_SE, RU_CE, RU_IN, RU_KB, RU_STA);
        link(RU_NVS, RU_ALT, RU_KEM, RU_OMS, RU_TOM);
        link(RU_NIZ, RU_CU, RU_IVA, RU_KOS, RU_KIR, RU_ME, RU_MO, RU_RYA, RU_VLA);
        link(RU_ORE, RU_BA, RU_CHE, RU_SAM, RU_SAR, RU_TA);
        link(RU_ORL, RU_BRY, RU_KLU, RU_KRS, RU_LIP, RU_TUL);
        link(RU_OMS, RU_NVS, RU_TOM, RU_TYU);
        link(RU_PER, RU_BA, RU_KO, RU_KIR, RU_SVE, RU_UD);
        link(RU_PRI, RU_KHA);
        link(RU_PSK, RU_LEN, RU_NGR, RU_SMO, RU_TVE);
        link(RU_PNZ, RU_MO, RU_RYA, RU_SAR, RU_TAM, RU_ULY);
        link(RU_ROS, RU_KDA, RU_KL, RU_STA, RU_VGG, RU_VOR);
        link(RU_RYA, RU_LIP, RU_MO, RU_MOS, RU_NIZ, RU_PNZ, RU_TAM, RU_TUL, RU_VLA);
        link(RU_SAM, RU_ORE, RU_SAR, RU_TA, RU_ULY);
        link(RU_SA, RU_AMU, RU_CHU, RU_IRK, RU_KHA, RU_KYA, RU_MAG, RU_ZAB);
        link(RU_SAK, RU_KHA);
        link(RU_SMO, RU_BRY, RU_KLU, RU_MOS, RU_PSK, RU_TVE);
        link(RU_SPE, RU_LEN);
        link(RU_SAR, RU_ORE, RU_PNZ, RU_SAM, RU_TAM, RU_ULY, RU_VGG, RU_VOR);
        link(RU_STA, RU_CE, RU_DA, RU_IN, RU_KB, RU_KC, RU_KDA, RU_KL, RU_SE, RU_ROS);
        link(RU_SVE, RU_BA, RU_CHE, RU_KHM, RU_KO, RU_KGN, RU_PER, RU_TYU);
        link(RU_TAM, RU_LIP, RU_PNZ, RU_RYA, RU_SAR, RU_VOR);
        link(RU_TOM, RU_KEM, RU_KHM, RU_KYA, RU_NVS, RU_OMS, RU_TYU);
        link(RU_TUL, RU_KLU, RU_LIP, RU_MOS, RU_ORL, RU_RYA);
        link(RU_TA, RU_BA, RU_CU, RU_KIR, RU_ME, RU_ORE, RU_SAM, RU_UD, RU_ULY);
        link(RU_TY, RU_BU, RU_AL, RU_IRK, RU_KK, RU_KYA);
        link(RU_TVE, RU_MOS, RU_NGR, RU_PSK, RU_SMO, RU_VLA, RU_VLG, RU_YAR);
        link(RU_TYU, RU_KHM, RU_KGN, RU_OMS, RU_SVE, RU_TOM);
        link(RU_UD, RU_BA, RU_KIR, RU_PER, RU_TA);
        link(RU_ULY, RU_CU, RU_MO, RU_PNZ, RU_SAM, RU_SAR, RU_TA);
        link(RU_VGG, RU_AST, RU_KL, RU_ROS, RU_SAR, RU_VOR);
        link(RU_VLA, RU_IVA, RU_MOS, RU_NIZ, RU_RYA, RU_TVE, RU_YAR);
        link(RU_YAN, RU_KHM, RU_KO, RU_KYA, RU_NEN);
        link(RU_VLG, RU_ARK, RU_KR, RU_KOS, RU_KIR, RU_LEN, RU_NGR, RU_TVE, RU_YAR);
        link(RU_VOR, RU_BEL, RU_KRS, RU_LIP, RU_ROS, RU_SAR, RU_TAM, RU_VGG);
        link(RU_YAR, RU_IVA, RU_KOS, RU_MOS, RU_TVE, RU_VLA, RU_VLG);
        link(RU_YEV, RU_AMU, RU_KHA);
        link(RU_ZAB, RU_AMU, RU_BU, RU_IRK, RU_SA);

        // ---------------------------------------------------------------------
        // United Kingdom Adjacency
        // ---------------------------------------------------------------------
        link(GB_UKC, GB_UKD, GB_UKE, GB_UKM);
        link(GB_UKD, GB_UKC, GB_UKE, GB_UKF, GB_UKG, GB_UKL, GB_UKM);
        link(GB_UKE, GB_UKC, GB_UKD, GB_UKF);
        link(GB_UKF, GB_UKD, GB_UKE, GB_UKG, GB_UKH, GB_UKJ);
        link(GB_UKG, GB_UKD, GB_UKF, GB_UKJ, GB_UKK, GB_UKL);
        link(GB_UKH, GB_UKF, GB_UKI, GB_UKJ);
        link(GB_UKI, GB_UKH, GB_UKJ);
        link(GB_UKJ, GB_UKF, GB_UKG, GB_UKH, GB_UKI, GB_UKK);
        link(GB_UKK, GB_UKG, GB_UKJ, GB_UKL);
        link(GB_UKL, GB_UKD, GB_UKG, GB_UKK);
        link(GB_UKM, GB_UKC, GB_UKD);
        link(GB_UKN, GB_IE);
        link(GB_GG);
        link(GB_JE);
        link(GB_IM);
        link(GB_IE, GB_UKN);

        // ---------------------------------------------------------------------
        // USA Adjacency
        // ---------------------------------------------------------------------
        link(US_AK);
        link(US_AL, US_FL, US_GA, US_MS, US_TN);
        link(US_AR, US_LA, US_MO, US_MS, US_OK, US_TN, US_TX);
        link(US_AZ, US_CA, US_NM, US_NV, US_UT);
        link(US_CA, US_AZ, US_NV, US_OR);
        link(US_CO, US_KS, US_NE, US_NM, US_OK, US_UT, US_WY);
        link(US_CT, US_MA, US_NJ, US_NY, US_RI);
        link(US_DC, US_MD, US_VA);
        link(US_DE, US_MD, US_NJ, US_PA);
        link(US_FL, US_AL, US_GA);
        link(US_GA, US_AL, US_FL, US_NC, US_SC, US_TN);
        link(US_HI);
        link(US_IA, US_IL, US_MN, US_MO, US_NE, US_SD, US_WI);
        link(US_ID, US_MT, US_NV, US_OR, US_UT, US_WA, US_WY);
        link(US_IL, US_IA, US_IN, US_KY, US_MO, US_WI);
        link(US_IN, US_IL, US_KY, US_MI, US_OH);
        link(US_KS, US_CO, US_MO, US_NE, US_OK);
        link(US_KY, US_IL, US_IN, US_MO, US_OH, US_TN, US_VA, US_WV);
        link(US_LA, US_AR, US_MS, US_TX);
        link(US_MA, US_CT, US_ME, US_NH, US_NY, US_RI, US_VT);
        link(US_MD, US_DC, US_DE, US_PA, US_VA, US_WV);
        link(US_ME, US_MA, US_NH);
        link(US_MI, US_IN, US_OH, US_WI);
        link(US_MN, US_IA, US_ND, US_SD, US_WI);
        link(US_MO, US_AR, US_IA, US_IL, US_KS, US_KY, US_NE, US_OK, US_TN);
        link(US_MS, US_AL, US_AR, US_LA, US_TN);
        link(US_MT, US_ID, US_ND, US_SD, US_WY);
        link(US_NC, US_GA, US_SC, US_TN, US_VA);
        link(US_ND, US_MN, US_MT, US_SD);
        link(US_NE, US_CO, US_IA, US_KS, US_MO, US_SD, US_WY);
        link(US_NH, US_MA, US_ME, US_VT);
        link(US_NJ, US_CT, US_DE, US_NY, US_PA);
        link(US_NM, US_AZ, US_CO, US_OK, US_TX);
        link(US_NV, US_AZ, US_CA, US_ID, US_OR, US_UT);
        link(US_NY, US_CT, US_MA, US_NJ, US_PA, US_VT);
        link(US_OH, US_IN, US_KY, US_MI, US_PA, US_WV);
        link(US_OK, US_AR, US_CO, US_KS, US_MO, US_NM, US_TX);
        link(US_OR, US_CA, US_ID, US_NV, US_WA);
        link(US_PA, US_DE, US_MD, US_NJ, US_NY, US_OH, US_WV);
        link(US_RI, US_CT, US_MA);
        link(US_SC, US_GA, US_NC);
        link(US_SD, US_IA, US_MN, US_MT, US_ND, US_NE, US_WY);
        link(US_TN, US_AL, US_AR, US_GA, US_KY, US_MO, US_MS, US_NC, US_VA);
        link(US_TX, US_AR, US_LA, US_NM, US_OK);
        link(US_UT, US_AZ, US_CO, US_ID, US_NV, US_WY);
        link(US_VA, US_DC, US_KY, US_MD, US_NC, US_TN, US_WV);
        link(US_VT, US_MA, US_NH, US_NY);
        link(US_WA, US_ID, US_OR);
        link(US_WI, US_IA, US_IL, US_MI, US_MN);
        link(US_WV, US_KY, US_MD, US_OH, US_PA, US_VA);
        link(US_WY, US_CO, US_ID, US_MT, US_NE, US_SD, US_UT);

    }
}
