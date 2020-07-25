package zero27zen.com.model

class log{

    var uId:String?=null
    var namabarang:String?=null
    var kategori:String?=null
    var hargabarng:Int?=null
    var jenis:String?=null
    var berat:Float?=null
    var date: com.google.firebase.Timestamp? =null
    var gbarang:String?=null
    var status :Boolean? = null
    var keterangan:String?=null
    var statusjadi=false
    constructor() {

    }

    constructor(
        uId: String,
        kategori: String,
        date: com.google.firebase.Timestamp,
        berat:Float,
        hargabarng:Int,
        jenis: String,
        keterangan: String,
        namabarang: String,
        status: Boolean,
        gbarang: String,statusjadi:Boolean) {
        this.uId = uId
        this.berat=berat
        this.kategori=kategori
        this.date=date
        this.hargabarng=hargabarng
        this.jenis=jenis
        this.keterangan=keterangan
        this.namabarang=namabarang
        this.status=status
        this.gbarang=gbarang
        this.statusjadi=statusjadi
    }

    constructor(kategori: String,
                date: com.google.firebase.Timestamp,
                hargabarng:Int,
                berat:Float,
                jenis: String,
                keterangan: String,
                namabarang: String,
                status: Boolean,
                gbarang: String,statusjadi: Boolean) {
        this.kategori=kategori
        this.date=date
        this.hargabarng=hargabarng
        this.berat=berat
        this.jenis=jenis
        this.keterangan=keterangan
        this.namabarang=namabarang
        this.status=status
        this.gbarang=gbarang
        this.statusjadi=statusjadi
    }
    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("jenis",jenis!!)
        result.put("berat",berat!!)
        result.put("hargabarng",hargabarng!!)
        result.put("kategori",kategori!!)
        result.put("keterangan",keterangan!!)
        result.put("namabarang",namabarang!!)
        result.put("status",status!!)
        result.put("date",date!!)
        result.put("gbarang",gbarang!!)
        result.put("statusjadi",statusjadi!!)

        return result
    }

}