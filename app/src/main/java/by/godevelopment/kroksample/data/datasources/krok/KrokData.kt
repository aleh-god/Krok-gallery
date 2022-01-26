package by.godevelopment.kroksample.data.datasources.krok

import by.godevelopment.kroksample.domain.model.RegionItem

object KrokData {

    val header = hashMapOf<Int, String>(
        1 to "Беларусь",
        2 to "Belarus",
        3 to "Белоруссия"
    )

    val regionStringKey = hashMapOf<Int, String>(
        1 to "Minsk region",
        2 to "Vitebsk region",
        3 to "Gomel region",
        4 to "Grodno region",
        5 to "Mogilev region",
        6 to "Brest region"
    )

    val regionRU = listOf(
        RegionItem(1,"https://krokapp.by/media/regions/566ea590-c705-416b-9257-b8ceaef47849.png", hashMapOf(
            1 to "Менская вобласьць",
            2 to "Minsk region",
            3 to "Минская область"
        )),
        RegionItem(2,"https://krokapp.by/media/regions/08dd0030-72b0-474e-abd0-fdc4c30033d3.png", hashMapOf(
            1 to "Віцебская вобласьць",
            2 to "Vitebsk region",
            3 to "Витебская область"
        )),
        RegionItem(3,"https://krokapp.by/media/regions/95e91be1-c182-49cb-b0aa-a66d30ae0352.png", hashMapOf(
            1 to "Гомельская вобласьць",
            2 to "Gomel region",
            3 to "Гомельская область"
        )),
        RegionItem(4,"https://krokapp.by/media/regions/6aa7e851-2582-4178-9cb0-a874443d5dbe.png", hashMapOf(
            1 to "Гарадзенская вобласьць",
            2 to "Grodno region",
            3 to "Гродненская область"
        )),
        RegionItem(5,"https://krokapp.by/media/regions/881a1cae-b16a-4b54-a92e-7d6838d46ef9.png", hashMapOf(
            1 to "Магілёўская вобласьць",
            2 to "Mogilev region",
            3 to "Могилевская область"
        )),
        RegionItem(6,"https://krokapp.by/media/regions/21b19d7c-9774-45b0-986a-c67b9dcd6895.png", hashMapOf(
            1 to "Берасьцейская вобласьць",
            2 to "Brest region",
            3 to "Брестская область"
        ))
    )
}
