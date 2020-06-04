package id.oratakashi.pekalonganstore.ui.region.villages

import id.oratakashi.pekalonganstore.data.model.region.villages.DataSearchVillage

interface VillagesInterface {
    interface Dialog{
        fun onDismis()
    }
    interface Activity{
        fun onSelect(data : DataSearchVillage)
    }
}