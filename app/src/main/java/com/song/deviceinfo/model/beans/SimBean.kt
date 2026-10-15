package com.song.deviceinfo.model.beans

/**
 * Created by chensongsong on 2020/9/11.
 */
class SimBean(
    var id: Int,
    var simId: Int,
    var iccId: String?,
    var carrierName: String?,
    var displayName: String?,
    var number: String?,
    var mcc: String?,
    var mnc: String?
)
