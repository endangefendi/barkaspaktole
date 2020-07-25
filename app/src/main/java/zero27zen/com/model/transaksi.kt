package zero27zen.com.model

import java.sql.Timestamp
import kotlin.collections.HashMap

class transaksi {

    var uId:String?=null
    var name:String?=null
    var berat:Number?=null
    var hargabarng:Number?=null
    var date: Timestamp?=null
    var kategori:String?=null
    var jenis:String?=null
    var gbarang:String?=null
    var status :Boolean? = null
    var keterangan:String?=null

    constructor() {}

    constructor(uId: String,name: String,kategori:String,jenis:String,berat:Number,date:Timestamp?, hargabarng:Number,gbarang: String,status:Boolean,keterangan:String) {
        this.uId = uId
        this.name = name
        this.berat=berat
        this.kategori=kategori
        this.jenis=jenis
        this.date=date
        this.hargabarng=hargabarng
        this.gbarang=gbarang
        this.status=status
        this.keterangan=keterangan
    }

    constructor(name: String,berat:Number,kategori:String,jenis:String,date:Timestamp?, hargabarng:Number,keterangan:String,gbarang: String,status:Boolean) {
        this.name = name
        this.berat=berat
        this.kategori=kategori
        this.jenis=jenis
        this.date=date
        this.hargabarng=hargabarng
        this.keterangan=keterangan
        this.gbarang=gbarang
        this.status=status
    }
    constructor(name: String,kategori:String,jenis:String,berat:Number,date:Timestamp?, hargabarng:Number,gbarang: String,keterangan:String) {
        this.name = name
        this.berat=berat
        this.kategori=kategori
        this.jenis=jenis
        this.date=date
        this.hargabarng=hargabarng
        this.keterangan=keterangan
        this.gbarang=gbarang
    }


    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("berat",berat!!)
        result.put("hargabarng",hargabarng!!)
        result.put("jenis",jenis!!)
        result.put("kategori",kategori!!)
        result.put("keterangan",keterangan!!)
        result.put("namabarang",name!!)
        result.put("status",status!!)
        result.put("date",date!!)
        result.put("gbarang",gbarang!!)

        return result
    }
    fun toMaps(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("berat",berat!!)
        result.put("hargabarng",hargabarng!!)
        result.put("jenis",jenis!!)
        result.put("keterangan",keterangan!!)
        result.put("namabarang",name!!)
        result.put("status",status!!)
        result.put("date",date!!)
        result.put("gbarang",gbarang!!)

        return result
    }

}