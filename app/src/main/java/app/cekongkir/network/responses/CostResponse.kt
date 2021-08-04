package app.cekongkir.network.responses

data class CostResponse(
    val rajaongkir: Rajaongkir
) {

    data class Rajaongkir (
            val destination_details: DestinationDetails,
            val origin_details: OriginDetails,
            val query: Query,
            val results: List<Results>,
            val status: Status
    ) {

        data class DestinationDetails (
                val city: String,
                val city_id: String,
                val province: String,
                val province_id: String,
                val subdistrict_id: String,
                val subdistrict_name: String,
                val type: String
        )
        data class OriginDetails(
                val city_id: String,
                val city_name: String,
                val postal_code: String,
                val province: String,
                val province_id: String,
                val type: String
        )
        data class Query(
                val courier: String,
                val destination: String,
                val destinationType: String,
                val origin: String,
                val originType: String,
                val weight: Int
        )

        data class Results(
                val code: String,
                val costs: List<Cost>,
                val name: String
        ) {
            data class Cost(
                    val cost: List<CostX>,
                    val description: String,
                    val service: String
            ) {
                data class CostX(
                        val etd: String,
                        val note: String,
                        val value: Int
                )
            }

        }

        data class Status(
                val code: Int,
                val description: String
        )
    }
}