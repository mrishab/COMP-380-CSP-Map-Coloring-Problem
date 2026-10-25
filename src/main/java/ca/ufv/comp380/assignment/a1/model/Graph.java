package ca.ufv.comp380.assignment.a1.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Graph representation of a country divided into provinces/states/regions.
 * The internal 2-D array represents an adjacency list where row index is the province
 * and columns contain indices of all provinces adjacent to that given province.
 */
public class Graph {

    // Canada Adjacency Array (13 provinces/regions)
    public static final int[][] CANADA_ADJACENCY = new int[][] {
        { 1, 6, 11 }, // 0: CA-AB (Alberta)
        { 0, 6, 12 }, // 1: CA-BC (British Columbia)
        { 7, 8, 11 }, // 2: CA-MB (Manitoba)
        { 5, 9, 10 }, // 3: CA-NB (New Brunswick)
        { 7, 10 }, // 4: CA-NL (Newfoundland and Labrador)
        { 3, 9 }, // 5: CA-NS (Nova Scotia)
        { 0, 1, 7, 11, 12 }, // 6: CA-NT (Northwest Territories)
        { 2, 4, 6, 8, 10 }, // 7: CA-NU (Nunavut)
        { 2, 7, 10 }, // 8: CA-ON (Ontario)
        { 3, 5 }, // 9: CA-PE (Prince Edward Island)
        { 3, 4, 7, 8 }, // 10: CA-QC (Quebec)
        { 0, 2, 6 }, // 11: CA-SK (Saskatchewan)
        { 1, 6 }, // 12: CA-YT (Yukon)
    };

    // China Adjacency Array (34 provinces/regions)
    public static final int[][] CHINA_ADJACENCY = new int[][] {
        { 10, 14, 16, 17, 25, 33 }, // 0: CN-34 (Anhui)
        { 9, 28 }, // 1: CN-11 (Beijing)
        { 7, 13, 14, 23, 24 }, // 2: CN-50 (Chongqing)
        { 4, 17, 29, 33 }, // 3: CN-35 (Fujian)
        { 3, 6, 11, 13, 17, 19 }, // 4: CN-44 (Guangdong)
        { 20, 21, 22, 23, 24, 30 }, // 5: CN-62 (Gansu)
        { 4, 7, 13, 32 }, // 6: CN-45 (Guangxi Zhuang)
        { 2, 6, 13, 24, 32 }, // 7: CN-52 (Guizhou)
        {  }, // 8: CN-46 (Hainan)
        { 1, 10, 18, 20, 25, 27, 28 }, // 9: CN-13 (Hebei)
        { 0, 9, 14, 16, 23, 25, 27 }, // 10: CN-41 (Henan)
        { 4 }, // 11: CN-91 (Hong Kong)
        { 15, 20 }, // 12: CN-23 (Heilongjiang)
        { 2, 4, 6, 7, 14, 17 }, // 13: CN-43 (Hunan)
        { 0, 2, 10, 13, 17, 23 }, // 14: CN-42 (Hubei)
        { 12, 18, 20 }, // 15: CN-22 (Jilin)
        { 0, 10, 25, 26, 33 }, // 16: CN-32 (Jiangsu)
        { 0, 3, 4, 13, 14, 33 }, // 17: CN-36 (Jiangxi)
        { 9, 15, 20 }, // 18: CN-21 (Liaoning)
        { 4 }, // 19: CN-92 (Macau)
        { 5, 9, 12, 15, 18, 21, 23, 27 }, // 20: CN-15 (Nei Mongol)
        { 5, 20, 23 }, // 21: CN-64 (Ningxia Hui)
        { 5, 24, 30, 31 }, // 22: CN-63 (Quinghai)
        { 2, 5, 10, 14, 20, 21, 24, 27 }, // 23: CN-61 (Shaanxi)
        { 2, 5, 7, 22, 23, 31, 32 }, // 24: CN-51 (Sichuan)
        { 0, 9, 10, 16 }, // 25: CN-37 (Shandong)
        { 16, 33 }, // 26: CN-31 (Shanghai)
        { 9, 10, 20, 23 }, // 27: CN-14 (Shanxi)
        { 1, 9 }, // 28: CN-12 (Tianjin)
        { 3 }, // 29: CN-71 (Taiwan)
        { 5, 22, 31 }, // 30: CN-65 (Xinjiang Uygur)
        { 22, 24, 30, 32 }, // 31: CN-54 (Xizang (Tibet))
        { 6, 7, 24, 31 }, // 32: CN-53 (Yunnan)
        { 0, 3, 16, 17, 26 }, // 33: CN-33 (Zhejiang)
    };

    // India Adjacency Array (36 provinces/regions)
    public static final int[][] INDIA_ADJACENCY = new int[][] {
        {  }, // 0: IN-AN (Andaman and Nicobar Islands)
        { 16, 25, 27, 30, 31 }, // 1: IN-AP (Andhra Pradesh)
        { 3, 24 }, // 2: IN-AR (Arunachal Pradesh)
        { 2, 20, 21, 23, 24, 32, 35 }, // 3: IN-AS (Assam)
        { 14, 33, 35 }, // 4: IN-BR (Bihar)
        { 12, 13, 26 }, // 5: IN-CH (Chandigarh)
        { 14, 19, 22, 25, 30, 33 }, // 6: IN-CT (Chhattisgarh)
        { 11 }, // 7: IN-DD (Daman and Diu)
        { 13, 33 }, // 8: IN-DL (Delhi)
        { 11, 19 }, // 9: IN-DN (Dadra and Nagar Haveli)
        { 16, 19 }, // 10: IN-GA (Goa)
        { 7, 9, 19, 22, 28 }, // 11: IN-GJ (Gujarat)
        { 5, 13, 15, 26, 33, 34 }, // 12: IN-HP (Himachal Pradesh)
        { 5, 8, 12, 26, 28, 33, 34 }, // 13: IN-HR (Haryana)
        { 4, 6, 25, 33, 35 }, // 14: IN-JH (Jharkhand)
        { 12, 26 }, // 15: IN-JK (Jammu and Kashmir)
        { 1, 10, 17, 19, 30, 31 }, // 16: IN-KA (Karnataka)
        { 16, 31 }, // 17: IN-KL (Kerala)
        {  }, // 18: IN-LD (Lakshadweep)
        { 6, 9, 10, 11, 16, 22, 30 }, // 19: IN-MH (Maharashtra)
        { 3 }, // 20: IN-ML (Meghalaya)
        { 3, 23, 24 }, // 21: IN-MN (Manipur)
        { 6, 11, 19, 28, 33 }, // 22: IN-MP (Madhya Pradesh)
        { 3, 21, 32 }, // 23: IN-MZ (Mizoram)
        { 2, 3, 21 }, // 24: IN-NL (Nagaland)
        { 1, 6, 14, 30, 35 }, // 25: IN-OR (Odisha)
        { 5, 12, 13, 15, 28 }, // 26: IN-PB (Punjab)
        { 1, 31 }, // 27: IN-PY (Puducherry)
        { 11, 13, 22, 26, 33 }, // 28: IN-RJ (Rajasthan)
        { 35 }, // 29: IN-SK (Sikkim)
        { 1, 6, 16, 19, 25 }, // 30: IN-TG (Telangana)
        { 1, 16, 17, 27 }, // 31: IN-TN (Tamil Nadu)
        { 3, 23 }, // 32: IN-TR (Tripura)
        { 4, 6, 8, 12, 13, 14, 22, 28, 34 }, // 33: IN-UP (Uttar Pradesh)
        { 12, 13, 33 }, // 34: IN-UT (Uttarakhand)
        { 3, 4, 14, 25, 29 }, // 35: IN-WB (West Bengal)
    };

    // Russia Adjacency Array (83 provinces/regions)
    public static final int[][] RUSSIA_ADJACENCY = new int[][] {
        { 20 }, // 0: RU-AD (Республика Адыгея)
        { 14, 21, 47 }, // 1: RU-ALT (Республика Алтай)
        { 23, 59, 81, 82 }, // 2: RU-AMU (Амурская область)
        { 24, 29, 34, 45, 78 }, // 3: RU-ARK (Архангельская область)
        { 26, 75 }, // 4: RU-AST (Астраханская область)
        { 10, 49, 52, 65, 69, 73 }, // 5: RU-BA (Республика Башкортостан)
        { 31, 79 }, // 6: RU-BEL (Белгородская область)
        { 22, 31, 50, 61 }, // 7: RU-BRY (Брянская область)
        { 16, 70, 82 }, // 8: RU-BU (Республика Бурятия)
        { 13, 15, 18, 46, 64 }, // 9: RU-CE (Чеченская республика)
        { 5, 33, 49, 65 }, // 10: RU-CHE (Челябинская область)
        { 30, 40, 59 }, // 11: RU-CHU (Чукотский автономный округ)
        { 39, 42, 48, 69, 74 }, // 12: RU-CU (Чувашская республика)
        { 9, 26, 64 }, // 13: RU-DA (Республика Дагестан)
        { 1, 21, 25, 70 }, // 14: RU-AL (Республика Алтай)
        { 9, 18, 46, 64 }, // 15: RU-IN (Республика Ингушетия)
        { 8, 35, 59, 70, 82 }, // 16: RU-IRK (Иркутская область)
        { 32, 48, 76, 80 }, // 17: RU-IVA (Ивановская область)
        { 9, 15, 19, 46, 64 }, // 18: RU-KB (Кабардино-Балкарская республика)
        { 18, 20, 64 }, // 19: RU-KC (Карачаево-Черкесская республика)
        { 0, 19, 56, 64 }, // 20: RU-KDA (Краснодарский край)
        { 1, 14, 25, 35, 47, 67 }, // 21: RU-KEM (Кемеровская область)
        { 7, 38, 43, 50, 61, 68 }, // 22: RU-KLU (Калужская область)
        { 2, 40, 53, 59, 60, 81 }, // 23: RU-KHA (Хабаровский край)
        { 3, 36, 41, 78 }, // 24: RU-KR (Республика Карелия)
        { 14, 21, 35, 70 }, // 25: RU-KK (Республика Хакасия)
        { 4, 13, 56, 64, 75 }, // 26: RU-KL (Республика Калмыкия)
        { 29, 35, 65, 67, 72, 77 }, // 27: RU-KHM (Ханты-Мансийский автономный округ)
        {  }, // 28: RU-KGD (Калининградская область)
        { 3, 27, 34, 45, 52, 65, 77 }, // 29: RU-KO (Республика Коми)
        { 11, 40 }, // 30: RU-KAM (Камчатский край)
        { 6, 7, 37, 50, 79 }, // 31: RU-KRS (Курская область)
        { 17, 34, 48, 78, 80 }, // 32: RU-KOS (Костромская область)
        { 10, 65, 72 }, // 33: RU-KGN (Курганская область)
        { 3, 29, 32, 39, 48, 52, 69, 73, 78 }, // 34: RU-KIR (Кировская область)
        { 16, 21, 25, 27, 59, 67, 70, 77 }, // 35: RU-KYA (Красноярский край)
        { 24, 44, 54, 62, 78 }, // 36: RU-LEN (Ленинградская область)
        { 31, 50, 57, 66, 68, 79 }, // 37: RU-LIP (Липецкая область)
        { 22, 43 }, // 38: RU-MOW (Москва)
        { 12, 34, 48, 69 }, // 39: RU-ME (Республика Марий Эл)
        { 11, 23, 30, 59 }, // 40: RU-MAG (Магаданская область)
        { 24 }, // 41: RU-MUR (Мурманская область)
        { 12, 48, 55, 57, 74 }, // 42: RU-MO (Республика Мордовия)
        { 22, 38, 57, 61, 68, 71, 76, 80 }, // 43: RU-MOS (Московская область)
        { 36, 54, 71, 78 }, // 44: RU-NGR (Новгородская область)
        { 3, 29, 77 }, // 45: RU-NEN (Ненецкий автономный округ)
        { 9, 15, 18, 64 }, // 46: RU-SE (Республика Северная Осетия — Алания)
        { 1, 21, 51, 67 }, // 47: RU-NVS (Новосибирская область)
        { 12, 17, 32, 34, 39, 42, 57, 76 }, // 48: RU-NIZ (Нижегородская область)
        { 5, 10, 58, 63, 69 }, // 49: RU-ORE (Оренбургская область)
        { 7, 22, 31, 37, 68 }, // 50: RU-ORL (Орловская область)
        { 47, 67, 72 }, // 51: RU-OMS (Омская область)
        { 5, 29, 34, 65, 73 }, // 52: RU-PER (Пермский край')
        { 23 }, // 53: RU-PRI (Приморский край)
        { 36, 44, 61, 71 }, // 54: RU-PSK (Псковская область)
        { 42, 57, 63, 66, 74 }, // 55: RU-PNZ (Пензенская область)
        { 20, 26, 64, 75, 79 }, // 56: RU-ROS (Ростовская область)
        { 37, 42, 43, 48, 55, 66, 68, 76 }, // 57: RU-RYA (Рязанская область)
        { 49, 63, 69, 74 }, // 58: RU-SAM (Самарская область)
        { 2, 11, 16, 23, 35, 40, 82 }, // 59: RU-SA (Республика Саха (Якутия))
        { 23 }, // 60: RU-SAK (Сахалинская область)
        { 7, 22, 43, 54, 71 }, // 61: RU-SMO (Смоленская область)
        { 36 }, // 62: RU-SPE (Санкт-Петербург)
        { 49, 55, 58, 66, 74, 75, 79 }, // 63: RU-SAR (Саратовская область)
        { 9, 13, 15, 18, 19, 20, 26, 46, 56 }, // 64: RU-STA (Ставропольский край)
        { 5, 10, 27, 29, 33, 52, 72 }, // 65: RU-SVE (Свердловская область)
        { 37, 55, 57, 63, 79 }, // 66: RU-TAM (Тамбовская область)
        { 21, 27, 35, 47, 51, 72 }, // 67: RU-TOM (Томская область)
        { 22, 37, 43, 50, 57 }, // 68: RU-TUL (Тульская область)
        { 5, 12, 34, 39, 49, 58, 73, 74 }, // 69: RU-TA (Республика Татарстан)
        { 8, 14, 16, 25, 35 }, // 70: RU-TY (Республика Тыва)
        { 43, 44, 54, 61, 76, 78, 80 }, // 71: RU-TVE (Тверская область)
        { 27, 33, 51, 65, 67 }, // 72: RU-TYU (Тюменская область)
        { 5, 34, 52, 69 }, // 73: RU-UD (Удмуртская республика)
        { 12, 42, 55, 58, 63, 69 }, // 74: RU-ULY (Ульяновская область)
        { 4, 26, 56, 63, 79 }, // 75: RU-VGG (Волгоградская область)
        { 17, 43, 48, 57, 71, 80 }, // 76: RU-VLA (Владимирская область)
        { 27, 29, 35, 45 }, // 77: RU-YAN (Ямало-Ненецкий автономный округ)
        { 3, 24, 32, 34, 36, 44, 71, 80 }, // 78: RU-VLG (Вологодская область)
        { 6, 31, 37, 56, 63, 66, 75 }, // 79: RU-VOR (Воронежская область)
        { 17, 32, 43, 71, 76, 78 }, // 80: RU-YAR (Ярославская область)
        { 2, 23 }, // 81: RU-YEV (Еврейская автономная область)
        { 2, 8, 16, 59 }, // 82: RU-ZAB (Забайкальский край)
    };

    // United Kingdom Adjacency Array (16 provinces/regions)
    public static final int[][] UNITED_KINGDOM_ADJACENCY = new int[][] {
        { 1, 2, 10 }, // 0: GB-UKC (North East)
        { 0, 2, 3, 4, 9, 10 }, // 1: GB-UKD (North West)
        { 0, 1, 3 }, // 2: GB-UKE (Yorkshire and the Humber)
        { 1, 2, 4, 5, 7 }, // 3: GB-UKF (East Midlands)
        { 1, 3, 7, 8, 9 }, // 4: GB-UKG (West Midlands)
        { 3, 6, 7 }, // 5: GB-UKH (East of England)
        { 5, 7 }, // 6: GB-UKI (Greater London)
        { 3, 4, 5, 6, 8 }, // 7: GB-UKJ (South East)
        { 4, 7, 9 }, // 8: GB-UKK (South West)
        { 1, 4, 8 }, // 9: GB-UKL (Wales)
        { 0, 1 }, // 10: GB-UKM (Scotland)
        { 15 }, // 11: GB-UKN (Northern Ireland)
        {  }, // 12: GG (Guernsey)
        {  }, // 13: JE (Jersey)
        {  }, // 14: IM (Isle of Man)
        { 11 }, // 15: IE (Ireland)
    };

    // USA Adjacency Array (51 provinces/regions)
    public static final int[][] USA_ADJACENCY = new int[][] {
        {  }, // 0: US-AK (Alaska)
        { 9, 10, 25, 42 }, // 1: US-AL (Alabama)
        { 18, 24, 25, 36, 42, 43 }, // 2: US-AR (Arkansas)
        { 4, 32, 33, 44 }, // 3: US-AZ (Arizona)
        { 3, 33, 37 }, // 4: US-CA (California)
        { 16, 29, 32, 36, 44, 50 }, // 5: US-CO (Colorado)
        { 19, 31, 34, 39 }, // 6: US-CT (Connecticut)
        { 20, 45 }, // 7: US-DC (Washington, DC)
        { 20, 31, 38 }, // 8: US-DE (Delaware)
        { 1, 10 }, // 9: US-FL (Florida)
        { 1, 9, 27, 40, 42 }, // 10: US-GA (Georgia)
        {  }, // 11: US-HI (Hawaii)
        { 14, 23, 24, 29, 41, 48 }, // 12: US-IA (Iowa)
        { 26, 33, 37, 44, 47, 50 }, // 13: US-ID (Idaho)
        { 12, 15, 17, 24, 48 }, // 14: US-IL (Illinois)
        { 14, 17, 22, 35 }, // 15: US-IN (Indiana)
        { 5, 24, 29, 36 }, // 16: US-KS (Kansas)
        { 14, 15, 24, 35, 42, 45, 49 }, // 17: US-KY (Kentucky)
        { 2, 25, 43 }, // 18: US-LA (Louisiana)
        { 6, 21, 30, 34, 39, 46 }, // 19: US-MA (Massachusetts)
        { 7, 8, 38, 45, 49 }, // 20: US-MD (Maryland)
        { 19, 30 }, // 21: US-ME (Maine)
        { 15, 35, 48 }, // 22: US-MI (Michigan)
        { 12, 28, 41, 48 }, // 23: US-MN (Minnesota)
        { 2, 12, 14, 16, 17, 29, 36, 42 }, // 24: US-MO (Missouri)
        { 1, 2, 18, 42 }, // 25: US-MS (Mississippi)
        { 13, 28, 41, 50 }, // 26: US-MT (Montana)
        { 10, 40, 42, 45 }, // 27: US-NC (North Carolina)
        { 23, 26, 41 }, // 28: US-ND (North Dakota)
        { 5, 12, 16, 24, 41, 50 }, // 29: US-NE (Nebraska)
        { 19, 21, 46 }, // 30: US-NH (New Hampshire)
        { 6, 8, 34, 38 }, // 31: US-NJ (New Jersey)
        { 3, 5, 36, 43 }, // 32: US-NM (New Mexico)
        { 3, 4, 13, 37, 44 }, // 33: US-NV (Nevada)
        { 6, 19, 31, 38, 46 }, // 34: US-NY (New York)
        { 15, 17, 22, 38, 49 }, // 35: US-OH (Ohio)
        { 2, 5, 16, 24, 32, 43 }, // 36: US-OK (Oklahoma)
        { 4, 13, 33, 47 }, // 37: US-OR (Oregon)
        { 8, 20, 31, 34, 35, 49 }, // 38: US-PA (Pennsylvania)
        { 6, 19 }, // 39: US-RI (Rhode Island)
        { 10, 27 }, // 40: US-SC (South Carolina)
        { 12, 23, 26, 28, 29, 50 }, // 41: US-SD (South Dakota)
        { 1, 2, 10, 17, 24, 25, 27, 45 }, // 42: US-TN (Tennessee)
        { 2, 18, 32, 36 }, // 43: US-TX (Texas)
        { 3, 5, 13, 33, 50 }, // 44: US-UT (Utah)
        { 7, 17, 20, 27, 42, 49 }, // 45: US-VA (Virginia)
        { 19, 30, 34 }, // 46: US-VT (Vermont)
        { 13, 37 }, // 47: US-WA (Washington)
        { 12, 14, 22, 23 }, // 48: US-WI (Wisconsin)
        { 17, 20, 35, 38, 45 }, // 49: US-WV (West Virginia)
        { 5, 13, 26, 29, 41, 44 }, // 50: US-WY (Wyoming)
    };

    public static final String[] CANADA_PROVINCE_IDS = new String[] {
        "CA-AB",
        "CA-BC",
        "CA-MB",
        "CA-NB",
        "CA-NL",
        "CA-NS",
        "CA-NT",
        "CA-NU",
        "CA-ON",
        "CA-PE",
        "CA-QC",
        "CA-SK",
        "CA-YT",
    };

    public static final String[] CHINA_PROVINCE_IDS = new String[] {
        "CN-34",
        "CN-11",
        "CN-50",
        "CN-35",
        "CN-44",
        "CN-62",
        "CN-45",
        "CN-52",
        "CN-46",
        "CN-13",
        "CN-41",
        "CN-91",
        "CN-23",
        "CN-43",
        "CN-42",
        "CN-22",
        "CN-32",
        "CN-36",
        "CN-21",
        "CN-92",
        "CN-15",
        "CN-64",
        "CN-63",
        "CN-61",
        "CN-51",
        "CN-37",
        "CN-31",
        "CN-14",
        "CN-12",
        "CN-71",
        "CN-65",
        "CN-54",
        "CN-53",
        "CN-33",
    };

    public static final String[] INDIA_PROVINCE_IDS = new String[] {
        "IN-AN",
        "IN-AP",
        "IN-AR",
        "IN-AS",
        "IN-BR",
        "IN-CH",
        "IN-CT",
        "IN-DD",
        "IN-DL",
        "IN-DN",
        "IN-GA",
        "IN-GJ",
        "IN-HP",
        "IN-HR",
        "IN-JH",
        "IN-JK",
        "IN-KA",
        "IN-KL",
        "IN-LD",
        "IN-MH",
        "IN-ML",
        "IN-MN",
        "IN-MP",
        "IN-MZ",
        "IN-NL",
        "IN-OR",
        "IN-PB",
        "IN-PY",
        "IN-RJ",
        "IN-SK",
        "IN-TG",
        "IN-TN",
        "IN-TR",
        "IN-UP",
        "IN-UT",
        "IN-WB",
    };

    public static final String[] RUSSIA_PROVINCE_IDS = new String[] {
        "RU-AD",
        "RU-ALT",
        "RU-AMU",
        "RU-ARK",
        "RU-AST",
        "RU-BA",
        "RU-BEL",
        "RU-BRY",
        "RU-BU",
        "RU-CE",
        "RU-CHE",
        "RU-CHU",
        "RU-CU",
        "RU-DA",
        "RU-AL",
        "RU-IN",
        "RU-IRK",
        "RU-IVA",
        "RU-KB",
        "RU-KC",
        "RU-KDA",
        "RU-KEM",
        "RU-KLU",
        "RU-KHA",
        "RU-KR",
        "RU-KK",
        "RU-KL",
        "RU-KHM",
        "RU-KGD",
        "RU-KO",
        "RU-KAM",
        "RU-KRS",
        "RU-KOS",
        "RU-KGN",
        "RU-KIR",
        "RU-KYA",
        "RU-LEN",
        "RU-LIP",
        "RU-MOW",
        "RU-ME",
        "RU-MAG",
        "RU-MUR",
        "RU-MO",
        "RU-MOS",
        "RU-NGR",
        "RU-NEN",
        "RU-SE",
        "RU-NVS",
        "RU-NIZ",
        "RU-ORE",
        "RU-ORL",
        "RU-OMS",
        "RU-PER",
        "RU-PRI",
        "RU-PSK",
        "RU-PNZ",
        "RU-ROS",
        "RU-RYA",
        "RU-SAM",
        "RU-SA",
        "RU-SAK",
        "RU-SMO",
        "RU-SPE",
        "RU-SAR",
        "RU-STA",
        "RU-SVE",
        "RU-TAM",
        "RU-TOM",
        "RU-TUL",
        "RU-TA",
        "RU-TY",
        "RU-TVE",
        "RU-TYU",
        "RU-UD",
        "RU-ULY",
        "RU-VGG",
        "RU-VLA",
        "RU-YAN",
        "RU-VLG",
        "RU-VOR",
        "RU-YAR",
        "RU-YEV",
        "RU-ZAB",
    };

    public static final String[] UNITED_KINGDOM_PROVINCE_IDS = new String[] {
        "GB-UKC",
        "GB-UKD",
        "GB-UKE",
        "GB-UKF",
        "GB-UKG",
        "GB-UKH",
        "GB-UKI",
        "GB-UKJ",
        "GB-UKK",
        "GB-UKL",
        "GB-UKM",
        "GB-UKN",
        "GG",
        "JE",
        "IM",
        "IE",
    };

    public static final String[] USA_PROVINCE_IDS = new String[] {
        "US-AK",
        "US-AL",
        "US-AR",
        "US-AZ",
        "US-CA",
        "US-CO",
        "US-CT",
        "US-DC",
        "US-DE",
        "US-FL",
        "US-GA",
        "US-HI",
        "US-IA",
        "US-ID",
        "US-IL",
        "US-IN",
        "US-KS",
        "US-KY",
        "US-LA",
        "US-MA",
        "US-MD",
        "US-ME",
        "US-MI",
        "US-MN",
        "US-MO",
        "US-MS",
        "US-MT",
        "US-NC",
        "US-ND",
        "US-NE",
        "US-NH",
        "US-NJ",
        "US-NM",
        "US-NV",
        "US-NY",
        "US-OH",
        "US-OK",
        "US-OR",
        "US-PA",
        "US-RI",
        "US-SC",
        "US-SD",
        "US-TN",
        "US-TX",
        "US-UT",
        "US-VA",
        "US-VT",
        "US-WA",
        "US-WI",
        "US-WV",
        "US-WY",
    };

    public static final String[] CANADA_PROVINCE_NAMES = new String[] {
        "Alberta",
        "British Columbia",
        "Manitoba",
        "New Brunswick",
        "Newfoundland and Labrador",
        "Nova Scotia",
        "Northwest Territories",
        "Nunavut",
        "Ontario",
        "Prince Edward Island",
        "Quebec",
        "Saskatchewan",
        "Yukon",
    };

    public static final String[] CHINA_PROVINCE_NAMES = new String[] {
        "Anhui",
        "Beijing",
        "Chongqing",
        "Fujian",
        "Guangdong",
        "Gansu",
        "Guangxi Zhuang",
        "Guizhou",
        "Hainan",
        "Hebei",
        "Henan",
        "Hong Kong",
        "Heilongjiang",
        "Hunan",
        "Hubei",
        "Jilin",
        "Jiangsu",
        "Jiangxi",
        "Liaoning",
        "Macau",
        "Nei Mongol",
        "Ningxia Hui",
        "Quinghai",
        "Shaanxi",
        "Sichuan",
        "Shandong",
        "Shanghai",
        "Shanxi",
        "Tianjin",
        "Taiwan",
        "Xinjiang Uygur",
        "Xizang (Tibet)",
        "Yunnan",
        "Zhejiang",
    };

    public static final String[] INDIA_PROVINCE_NAMES = new String[] {
        "Andaman and Nicobar Islands",
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chandigarh",
        "Chhattisgarh",
        "Daman and Diu",
        "Delhi",
        "Dadra and Nagar Haveli",
        "Goa",
        "Gujarat",
        "Himachal Pradesh",
        "Haryana",
        "Jharkhand",
        "Jammu and Kashmir",
        "Karnataka",
        "Kerala",
        "Lakshadweep",
        "Maharashtra",
        "Meghalaya",
        "Manipur",
        "Madhya Pradesh",
        "Mizoram",
        "Nagaland",
        "Odisha",
        "Punjab",
        "Puducherry",
        "Rajasthan",
        "Sikkim",
        "Telangana",
        "Tamil Nadu",
        "Tripura",
        "Uttar Pradesh",
        "Uttarakhand",
        "West Bengal",
    };

    public static final String[] RUSSIA_PROVINCE_NAMES = new String[] {
        "Республика Адыгея",
        "Республика Алтай",
        "Амурская область",
        "Архангельская область",
        "Астраханская область",
        "Республика Башкортостан",
        "Белгородская область",
        "Брянская область",
        "Республика Бурятия",
        "Чеченская республика",
        "Челябинская область",
        "Чукотский автономный округ",
        "Чувашская республика",
        "Республика Дагестан",
        "Республика Алтай",
        "Республика Ингушетия",
        "Иркутская область",
        "Ивановская область",
        "Кабардино-Балкарская республика",
        "Карачаево-Черкесская республика",
        "Краснодарский край",
        "Кемеровская область",
        "Калужская область",
        "Хабаровский край",
        "Республика Карелия",
        "Республика Хакасия",
        "Республика Калмыкия",
        "Ханты-Мансийский автономный округ",
        "Калининградская область",
        "Республика Коми",
        "Камчатский край",
        "Курская область",
        "Костромская область",
        "Курганская область",
        "Кировская область",
        "Красноярский край",
        "Ленинградская область",
        "Липецкая область",
        "Москва",
        "Республика Марий Эл",
        "Магаданская область",
        "Мурманская область",
        "Республика Мордовия",
        "Московская область",
        "Новгородская область",
        "Ненецкий автономный округ",
        "Республика Северная Осетия — Алания",
        "Новосибирская область",
        "Нижегородская область",
        "Оренбургская область",
        "Орловская область",
        "Омская область",
        "Пермский край'",
        "Приморский край",
        "Псковская область",
        "Пензенская область",
        "Ростовская область",
        "Рязанская область",
        "Самарская область",
        "Республика Саха (Якутия)",
        "Сахалинская область",
        "Смоленская область",
        "Санкт-Петербург",
        "Саратовская область",
        "Ставропольский край",
        "Свердловская область",
        "Тамбовская область",
        "Томская область",
        "Тульская область",
        "Республика Татарстан",
        "Республика Тыва",
        "Тверская область",
        "Тюменская область",
        "Удмуртская республика",
        "Ульяновская область",
        "Волгоградская область",
        "Владимирская область",
        "Ямало-Ненецкий автономный округ",
        "Вологодская область",
        "Воронежская область",
        "Ярославская область",
        "Еврейская автономная область",
        "Забайкальский край",
    };

    public static final String[] UNITED_KINGDOM_PROVINCE_NAMES = new String[] {
        "North East",
        "North West",
        "Yorkshire and the Humber",
        "East Midlands",
        "West Midlands",
        "East of England",
        "Greater London",
        "South East",
        "South West",
        "Wales",
        "Scotland",
        "Northern Ireland",
        "Guernsey",
        "Jersey",
        "Isle of Man",
        "Ireland",
    };

    public static final String[] USA_PROVINCE_NAMES = new String[] {
        "Alaska",
        "Alabama",
        "Arkansas",
        "Arizona",
        "California",
        "Colorado",
        "Connecticut",
        "Washington, DC",
        "Delaware",
        "Florida",
        "Georgia",
        "Hawaii",
        "Iowa",
        "Idaho",
        "Illinois",
        "Indiana",
        "Kansas",
        "Kentucky",
        "Louisiana",
        "Massachusetts",
        "Maryland",
        "Maine",
        "Michigan",
        "Minnesota",
        "Missouri",
        "Mississippi",
        "Montana",
        "North Carolina",
        "North Dakota",
        "Nebraska",
        "New Hampshire",
        "New Jersey",
        "New Mexico",
        "Nevada",
        "New York",
        "Ohio",
        "Oklahoma",
        "Oregon",
        "Pennsylvania",
        "Rhode Island",
        "South Carolina",
        "South Dakota",
        "Tennessee",
        "Texas",
        "Utah",
        "Virginia",
        "Vermont",
        "Washington",
        "Wisconsin",
        "West Virginia",
        "Wyoming",
    };

    public static final Graph CANADA = new Graph(
        "Canada",
        CANADA_PROVINCE_NAMES,
        CANADA_PROVINCE_IDS,
        CANADA_ADJACENCY
    );

    public static final Graph CHINA = new Graph(
        "China",
        CHINA_PROVINCE_NAMES,
        CHINA_PROVINCE_IDS,
        CHINA_ADJACENCY
    );

    public static final Graph INDIA = new Graph(
        "India",
        INDIA_PROVINCE_NAMES,
        INDIA_PROVINCE_IDS,
        INDIA_ADJACENCY
    );

    public static final Graph RUSSIA = new Graph(
        "Russia",
        RUSSIA_PROVINCE_NAMES,
        RUSSIA_PROVINCE_IDS,
        RUSSIA_ADJACENCY
    );

    public static final Graph UNITED_KINGDOM = new Graph(
        "United Kingdom",
        UNITED_KINGDOM_PROVINCE_NAMES,
        UNITED_KINGDOM_PROVINCE_IDS,
        UNITED_KINGDOM_ADJACENCY
    );

    public static final Graph USA = new Graph(
        "USA",
        USA_PROVINCE_NAMES,
        USA_PROVINCE_IDS,
        USA_ADJACENCY
    );

    private final String countryName;
    private final String[] provinceNames;
    private final String[] provinceIds;
    private final int[][] adjacencyList;

    public Graph(String countryName, String[] provinceNames, String[] provinceIds, int[][] adjacencyList) {
        this.countryName = countryName;
        this.provinceNames = provinceNames != null ? provinceNames.clone() : new String[0];
        this.provinceIds = provinceIds != null ? provinceIds.clone() : new String[0];
        if (adjacencyList != null) {
            this.adjacencyList = new int[adjacencyList.length][];
            for (int i = 0; i < adjacencyList.length; i++) {
                this.adjacencyList[i] = adjacencyList[i] != null ? adjacencyList[i].clone() : new int[0];
            }
        } else {
            this.adjacencyList = new int[0][0];
        }
    }

    public Graph(int[][] adjacencyList) {
        this("Unknown", null, null, adjacencyList);
    }

    public String getCountryName() {
        return countryName;
    }

    public int[][] getAdjacencyList() {
        return adjacencyList;
    }

    public int[] getNeighbors(int provinceIndex) {
        if (provinceIndex < 0 || provinceIndex >= adjacencyList.length) {
            return new int[0];
        }
        return adjacencyList[provinceIndex];
    }

    public int getNumVertices() {
        return adjacencyList.length;
    }

    public int size() {
        return adjacencyList.length;
    }

    public boolean isAdjacent(int p1, int p2) {
        if (p1 < 0 || p1 >= adjacencyList.length) return false;
        for (int neighbor : adjacencyList[p1]) {
            if (neighbor == p2) return true;
        }
        return false;
    }

    public String[] getProvinceNames() {
        return provinceNames;
    }

    public String getProvinceName(int index) {
        if (index >= 0 && index < provinceNames.length) {
            return provinceNames[index];
        }
        return String.valueOf(index);
    }

    public String[] getProvinceIds() {
        return provinceIds;
    }

    public String getProvinceId(int index) {
        if (index >= 0 && index < provinceIds.length) {
            return provinceIds[index];
        }
        return "";
    }

    public int getProvinceIndexById(String id) {
        if (id == null) return -1;
        for (int i = 0; i < provinceIds.length; i++) {
            if (id.equalsIgnoreCase(provinceIds[i])) return i;
        }
        return -1;
    }

    public int getProvinceIndexByName(String name) {
        if (name == null) return -1;
        for (int i = 0; i < provinceNames.length; i++) {
            if (name.equalsIgnoreCase(provinceNames[i])) return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return countryName + " Graph (" + adjacencyList.length + " vertices)";
    }
}
