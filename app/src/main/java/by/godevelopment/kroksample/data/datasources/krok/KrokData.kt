package by.godevelopment.kroksample.data.datasources.krok

import by.godevelopment.kroksample.domain.model.Region
import by.godevelopment.kroksample.domain.model.RegionItem

object KrokData {

    const val header = "БЕЛАРУСЬ"

    val regionRU = listOf<RegionItem>(
        RegionItem(Region.Minsk.id,"https://krokapp.by/media/regions/566ea590-c705-416b-9257-b8ceaef47849.png", "Минская область"),
        RegionItem(Region.Vitebsk.id,"https://krokapp.by/media/regions/08dd0030-72b0-474e-abd0-fdc4c30033d3.png", "Витебская область"),
        RegionItem(Region.Gomel.id,"https://krokapp.by/media/regions/95e91be1-c182-49cb-b0aa-a66d30ae0352.png", "Гомельская область"),
        RegionItem(Region.Grodno.id,"https://krokapp.by/media/regions/6aa7e851-2582-4178-9cb0-a874443d5dbe.png", "Гродненская область"),
        RegionItem(Region.Mogilev.id,"https://krokapp.by/media/regions/881a1cae-b16a-4b54-a92e-7d6838d46ef9.png", "Могилевская область"),
        RegionItem(Region.Brest.id,"https://krokapp.by/media/regions/21b19d7c-9774-45b0-986a-c67b9dcd6895.png", "Брестская область")
    )
}
