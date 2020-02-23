package id.oratakashi.pekalonganstore.ui.region

interface SubdistrictInterface {
    interface Dialog{
        fun onDismis()
    }
    interface Activity{
        fun onSelect(
            subdiscrict_id : String,
            provinsi : String,
            kota : String,
            kecamatan : String
        )
    }
}