package app.cekongkir.network.responses

data class WaybillResponse(
    val rajaongkir: Rajaongkir
) {
    data class Rajaongkir(
            val query: Query,
            val result: Results,
            val status: Status
    ) {
        data class Query(
                val courier: String,
                val waybill: String
        )
        data class Results(
                val delivered: Boolean,
                val delivery_status: DeliveryStatus,
                val details: Details,
                val manifest: List<Manifest>,
                val summary: Summary
        ) {
            data class DeliveryStatus(
                    val pod_date: String,
                    val pod_receiver: String,
                    val pod_time: String,
                    val status: String
            )
            data class Details(
                    val destination: String,
                    val origin: String,
                    val receiver_address1: String,
                    val receiver_address2: String,
                    val receiver_address3: String,
                    val receiver_city: String,
                    val receiver_name: String,
                    val shipper_address1: String,
                    val shipper_address2: String,
                    val shipper_address3: String,
                    val shipper_city: String,
                    val shippper_name: String,
                    val waybill_date: String,
                    val waybill_number: String,
                    val waybill_time: String,
                    val weight: String
            )
            data class Manifest(
                    val city_name: String,
                    val manifest_date: String,
                    val manifest_description: String,
                    val manifest_time: String
            )
            data class Summary(
                    val courier_code: String,
                    val courier_name: String,
                    val destination: String,
                    val origin: String,
                    val receiver_name: String,
                    val service_code: String,
                    val shipper_name: String,
                    val status: String,
                    val waybill_date: String,
                    val waybill_number: String
            )
        }
        data class Status(
                val code: Int,
                val description: String
        )
    }
}